package runTimeTests;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.Log.*;

public class RuntimeChecks {
	public String checkFile(String file, String className){
		
		Pattern regex = Pattern.compile("(.+\\/)(?!\\/\\w+.java)");
		Matcher matcher = regex.matcher(file);
		matcher.find();
		
		String folder = matcher.group();
		ByteArrayOutputStream printlnOutput = new ByteArrayOutputStream();
		
		// Check for compilation: Could not get javac to escape the paths involved with backslashes or quotes. Ignoring for now, will do this with a bash script.
		/*
		System.out.println("Compile: "+file.replace(" ", "\\ "));
		String result = runProcess("javac -classpath \"" + folder + "\" \"" + file+"\"");
		if (result.contains("stderr")) {
			write("\t-50% Error compiling: " + file+"\n"+result);
			deductPoints(50);
		}
		else{
			write("\tCompiled without error\n");
			write(result);
		}
		*/

		
		File f = new File(folder);
		Class<?> shell = null;
		try {
		    // Convert File to a URI
		    @SuppressWarnings("deprecation")
			URL url = f.toURL();
		    URL[] urls = new URL[]{url};
		    // Create a new class loader with the directory
		    ClassLoader cl = new URLClassLoader(urls);

		    shell = cl.loadClass(className);
		    ((Closeable) cl).close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			write("Could not understand the file path: "+folder);
		} catch (ClassNotFoundException e) {
			write("Class not found in "+folder+"\n\t"+className);
		} catch (IOException e) {
			write("Failed to close the classloader. Cause unknown: ");
			e.printStackTrace();
		}
		
		//Setup fake input
		String fakeInput = "1\nJerod\nEwert\n1000\n25\n6.5\n100\n";
		
		try {

			Method[] methods = shell.getDeclaredMethods();
			String output = "";
			PrintStream oldPs = System.out;
			for(Method m: methods){
				if("main".equals(m.getName())){
					PrintStream ps = new PrintStream(printlnOutput);
					InputStream in = System.in;
					
					System.setOut(ps);
					System.setIn(new ByteArrayInputStream(fakeInput.getBytes(StandardCharsets.UTF_8)));
					
					m.invoke(null, "Unused");
					
					System.setOut(oldPs);
					System.setIn(in);
					
					output = printlnOutput.toString(""+StandardCharsets.UTF_8);
					if(output.contains("10713.0")){
						System.out.println("\tPassed: Example Input Gives correct output");
					}
				}
				
			}
			System.setOut(oldPs);
			output = printlnOutput.toString(""+StandardCharsets.UTF_8);
			if(!output.contains("10713.0")){
				write("\t-20% Due to: Example Input (null, 100,25,6.5,1000) Gives wrong output"
						+"\n\tShould contain: 10713.0");
				deductPoints(20);
			}
		} catch(Exception e){
			write("The standard script caused an exception.");
		}

		return file;
	}
	private static String printLines(String name, InputStream ins) throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		String s = "";
		while ((line = in.readLine()) != null) {
			s += (name + " " + line);
		}
		return s;
	}

	private static String runProcess(String command){
		try{
		Process process = Runtime.getRuntime().exec(command);
		
		String result = printLines(command + " stdout:", process.getInputStream());
		result += printLines(command + " stderr:", process.getErrorStream());
		
		process.waitFor();
		result += (command + " exitValue() " + process.exitValue());
		return result;
		}
		catch(Exception e){
			e.printStackTrace();
			return "Failed to run process: "+command;
		}
	}

}
