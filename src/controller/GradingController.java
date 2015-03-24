package controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import runTimeTests.RuntimeChecks;
import sourceTests.FileLevelChecks;
import view.Log;
import static view.Log.*;

public class GradingController {
	private static boolean debug = true;
	/*Instructor's Criteria:(Checking method)
	 	1. Your program’s output must match the sample output provided (whitespace agnostic)
		2. Proper logic (Check for method call in main?)
		3. Proper variable naming (regex)
		4. Proper indentation (not sure.)
			Regex to check indent level of any line ending in { or if()END\n!{ or for()END\n!{
			Regex to check indent of the next non-blank line
			Must be more than the previous line
		5. Proper use of methods (uses printtable method?)
		6. Proper use of comments (Good comment density)
		7. Must provide comments at the beginning of your program with the following information
		Name
		Date created
		 HW#
		 Program description
		8. You are not allowed to change any of the method’s headers
		9. Programs written without (at least 2) methods will receive zero points
		*/
	/*
	 * Main task: Support instructor's criteria
	 * 		Break unit tests into separate classes.
	Load the student work
		For each solution:
			File level:
				Done: Check for file existence at the top level instead of every lower place.
				Done: Compile
				Done: Get student name
				Done: Check file for comments before each method
				Done: Check case of variable and class names
				Done: List variable type and name for human grading
			
			Java level:
				Done: Detect package and class name.
				Unit test methods/Diff output
					show result.
				Verify at least 1 additional method exists with reflection
					Call the printTable method and compare output to expected
						Several tests would be nice.
						Instructor's example first:
							"25	648.0		100.0	10713.0"
					Call main method and
			misc:
				Generate a report. maybe learn l4j.
				Create unit tests instead of current structure.
	*/
	public static void main(String[] args){
		
		if(debug){
			System.out.println("DEBUG MODE: passing constant file to test against");
			String[] temp = {"/home/anony/javaFiles/ShowInterestShell.java"};
			args = temp;
		}
		
		//Get filename from args
		if(args.length < 1){
			System.out.println("Usage:\n"
					+ "java GradingController [File path]\n"
					+ "Too few arguments provided.");
			System.exit(-1);
		}
		
		
		for(int i = 0 ; i < args.length; i++){
			String filename = args[i];
			FileLevelChecks flc = new FileLevelChecks(); 
			RuntimeChecks rtc = new RuntimeChecks();
			
			String content = getFileContent(filename);
			String className = flc.check(content);
			
			write("Runtime check result: " + rtc.checkFile(filename, className));

			Log.flushToFile();
		}
	}
	public static String getFileContent(String file){
		//Open the file
		File input = new File(file);
		if(!input.canRead()){
			System.out.println("Could not read file: "+file);
			System.exit(-1);
		}
		
		Scanner fileContent = null;
		try {
			fileContent = new Scanner(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String content = "";
		while(fileContent.hasNextLine()){
			content += fileContent.nextLine()+"\n";
		}
		fileContent.close();
		return(content);
	}
}
