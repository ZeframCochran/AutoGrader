package controller;
import java.lang.reflect.Method;

import model.FileLevelChecks;

public class GradingController {
	private static boolean debug = true;
	/*
	 * Main task: Refactor this ugly thing.
	Load the student work
		For each solution:
			File level:
				Check for file existence at the top level instead of every lower place.
				Done: Compile
				Done: Get student name
				Done: Check file for comments before each method
				Done: Check case of variable and class names
				Done: List variable type and name for human grading
			
			Java level:
				Detect package and class name.
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
		
		FileLevelChecks flc = new FileLevelChecks(); 
		System.out.println("File check result:" + flc.check(args[0]));
		
		
	
	//Attempts to compile and reports the result
	
	}	
}
