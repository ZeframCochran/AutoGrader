package runTimeTests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sourceTests.StudentNameProvided;
import tests.RuntimeTest;
import tests.SourcecodeTest;
import static view.Log.*;

public class RuntimeChecks {
	public String checkFile(String file, String className){
		System.out.println("Beginning runtime checks.");
		Pattern regex = Pattern.compile("(.+\\/)(?!\\/\\w+.java)");
		Matcher matcher = regex.matcher(file);
		matcher.find();
		Method main = null;
		String folder = matcher.group();
		
		
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
			
			
			for(Method m: methods){
				if("main".equals(m.getName())){
					main = m;
					runTests();
					//TODO: refactor
					ArrayList<RuntimeTest> tests = new ArrayList<RuntimeTest>();			
					tests.add(new ExampleInputHW1());
				} 
			}
			
		} catch(Exception e){
			write("The standard script caused an exception."+e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Finished runtime checks");
		return file;
	}
	
	private void runTests() {
		
	}

	public String runMain(Method main, String keyboardInput){
		
		ByteArrayOutputStream printlnOutput = new ByteArrayOutputStream();
		PrintStream newPS = new PrintStream(printlnOutput);
		InputStream oldIn = System.in;
		PrintStream oldPs = System.out;

		System.setIn(new ByteArrayInputStream(keyboardInput.getBytes(StandardCharsets.UTF_8)));
		
		System.setOut(newPS);
		String[] param = new String[1];
		Object[] parameters ={param};
		
		try{
			main.invoke( null, parameters );
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.setOut(oldPs);
		System.setIn(oldIn);

		String output = "";
		try {
			output = printlnOutput.toString(""+StandardCharsets.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if(output.contains("10713.0")){
			System.out.println("\tPassed: Example Input Gives correct output");
		}else{
			write("\t-20% Due to: Example Input (null, 100,25,6.5,1000) Gives wrong output"
					+"\n\tShould contain: 10713.0");
			deductPoints(20);
		}

		
		return output;
	}
}
