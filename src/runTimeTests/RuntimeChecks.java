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
import tests.RuntimeTest;
import static view.Log.*;

public class RuntimeChecks {
	private static Method main = null;
	public String checkFile(String file, String className){
		System.out.println("Beginning runtime checks.");
		Pattern regex = Pattern.compile("(.+\\/)(?!\\/\\w+.java)");
		Matcher matcher = regex.matcher(file);
		matcher.find();
		String folder = matcher.group();
		
		File f = new File(folder);
		Class<?> shell = null;
		ClassLoader cl = null;
		try {
		    @SuppressWarnings("deprecation")
		    URL[] urls = new URL[]{f.toURL()};
		    cl = new URLClassLoader(urls);
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
		
		try {
			Method[] methods = shell.getDeclaredMethods();
			for(Method m: methods){
				if("main".equals(m.getName())){
					main = m;
					runTests();
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
		ArrayList<RuntimeTest> tests = new ArrayList<RuntimeTest>();			
		tests.add(new ExampleInputHW1());
		for(RuntimeTest r: tests){
			r.test();
		}
	}

	public static String runMain(String keyboardInput){
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
		return output;
	}
}
