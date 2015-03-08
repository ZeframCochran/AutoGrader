package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
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
			if (runProcess("javac " + file).contains("error")) {
				System.out.println("Error compiling: " + file);
				System.exit(-1);
			}
			else{
				System.out.println("\tCompiled without error\n");
			}
			
			//Open the file
			File input = new File(file);
			if(!input.canRead()){
				System.out.println("Could not read file.");
			}
			Scanner fileContent = new Scanner(input);
			String content = "";
			while(fileContent.hasNextLine()){
				content += fileContent.nextLine()+"\n";
			}
			fileContent.close();
			
			//Get student name
			System.out.println("Searching for student name..");
			
			Pattern regex = Pattern.compile("([A-Z])\\w+\\s([A-Z])\\w+",0);
			Matcher matcher = regex.matcher(content);
			matcher.find();
			System.out.println("\tName: "+matcher.group()+"\n");
			
			//Check for comments before each method
			System.out.println("Checking for comments before each method..");
			regex = Pattern.compile("(\\/\\/|\\*\\/).*\\n+.*\\n*.*public static");
			matcher = regex.matcher(content);
			int commentCount = 0;
			while(matcher.find()){
				commentCount++;
			}
			
			regex = Pattern.compile("public static \\w+\\s\\w+\\(.*\\)");
			matcher = regex.matcher(content);
			int methodCount = 0;
			while(matcher.find()){
				methodCount++;
			}
			
			if(methodCount!=commentCount){
				System.out.println("Methods without comments detected.");
			}
			
			//Check variable names
			//(boolean|byte|char|short|int|long|float|double|Scanner)\s[A-Z]
			//Check class names
			//class\s[a-z]
			//diff output

			// runProcess("java Main");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
}
