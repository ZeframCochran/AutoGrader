package model;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileLevelChecks {
	private static String printLines(String name, InputStream ins) throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		String s = "";
		while ((line = in.readLine()) != null) {
			s += (name + " " + line);
		}
		return s;
	}

	private static String runProcess(String command) throws Exception {
		Process process = Runtime.getRuntime().exec(command);
		
		String result = printLines(command + " stdout:", process.getInputStream());
		result += printLines(command + " stderr:", process.getErrorStream());
		
		process.waitFor();
		result += (command + " exitValue() " + process.exitValue());
		return result;
	}

	public static String check(String file) {
		try {
			// Check for compilation
			System.out.println("Testing compilation..");
			String result = runProcess("javac " + file);
			if (result.contains("error")) {
				System.out.println("Error compiling: " + file+"\n"+result);
			}
			else{
				System.out.println("\tCompiled without error\n");
			}
			
			//Open the file
			File input = new File(file);
			if(!input.canRead()){
				System.out.println("Could not read file.");
				System.exit(-1);
			}
			Scanner fileContent = new Scanner(input);
			String content = "";
			while(fileContent.hasNextLine()){
				content += fileContent.nextLine()+"\n";
			}
			fileContent.close();
			
			//Get student name
			Pattern regex = Pattern.compile("([A-Z])\\w+\\s([A-Z])\\w+",0);
			Matcher matcher = regex.matcher(content);
			matcher.find();
			System.out.println("\tName: "+matcher.group().trim());
			
			//Check for methods
			regex = Pattern.compile("(\\/\\/|\\*\\/)");
			matcher = regex.matcher(content);
			int commentCount = 0;
			while(matcher.find()){
				commentCount++;
			}
			System.out.println("\tHow many comments: " + commentCount);
			
			
			//Check comment density, at least 1 per method 
			regex = Pattern.compile("public static \\w+\\s\\w+\\(.*\\)");
			matcher = regex.matcher(content);
			int methodCount = 0;
			while(matcher.find()){
				methodCount++;
			}
			System.out.println("\tHow many methods: " + methodCount);
			
			if(methodCount>commentCount){
				System.out.println("\tMethods without comments detected.");
			}
			
			//Check variable names
			regex = Pattern.compile("\\s(boolean|byte|char|short|int|long|float|double|Scanner)\\s[A-Z]\\w+");
			matcher = regex.matcher(content);
			while(matcher.find()){
				System.out.println("\tVariables with bad capitalization detected: "+matcher.group());
			}
			
			//Check class names
			regex = Pattern.compile("(class)\\s[a-z]\\w+");
			matcher = regex.matcher(content);
			while(matcher.find()){
				System.out.println("\tClass with bad capitalization detected."+(matcher.group()));
			}

			System.out.println("Identifiers: ");
			regex = Pattern.compile("\\s(boolean|byte|char|short|int|long|float|double|Scanner|class)\\s\\w+");
			matcher = regex.matcher(content);
			while(matcher.find()){
				System.out.println("\t"+matcher.group().trim());
			}
			
			regex = Pattern.compile("(.+\\/)(?!\\/\\w+.java)");
			matcher = regex.matcher(file);
			matcher.find();
			//diff output
			String folder = matcher.group();
			String clazz = "model.ShowInterestShell";
			ByteArrayOutputStream printlnOutput = new ByteArrayOutputStream();
			
			
			File f = new File(folder);
			Class<?> shell = null;
			try {
			    // Convert File to a URI
			    @SuppressWarnings("deprecation")
				URL url = f.toURL();
			    URL[] urls = new URL[]{url};
			    // Create a new class loader with the directory
			    ClassLoader cl = new URLClassLoader(urls);
    
			    shell = cl.loadClass(clazz);
			    ((Closeable) cl).close();
			} catch (MalformedURLException e) {
				System.out.println("bad url "+folder);
			} catch (ClassNotFoundException e) {
				System.out.println("Class not found"+folder+clazz);
			}
			
			//Setup fake input
			String fakeInput = "";
			System.setIn(new ByteArrayInputStream(fakeInput.getBytes(StandardCharsets.UTF_8)));
			Object instance = shell.newInstance();
			//double yearlyDeposit, int years, double rate,double balance
			Method[] methods = shell.getDeclaredMethods();
			for(Method m: methods){
				if("printTable".equals(m.getName())){
					PrintStream oldPs = System.out;
					PrintStream ps = new PrintStream(printlnOutput);
					System.setOut(ps);
					m.invoke(null, 100,25,6.5,1000);
					System.setOut(oldPs);
					String output = printlnOutput.toString(""+StandardCharsets.UTF_8);
					System.out.println(output);
					if(output.contains("25	648.0		100.0	10713.0")){
						System.out.println("\tPassed first unit test..");
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
}
