package controller;
import java.lang.reflect.Method;

import model.FileLevelChecks;

public class GradingController {
	private static boolean debug = true;
	/*
	Load the student work
		For each solution:
			File level:
				Check for file existance at the top level instead of every lower place.
				Done: Compile
				Done: Get student name
				Done: Check file for comments before each method
				Check variable names
					Capitalization only, others require human interaction
			
			Java level:
				Unit test methods/Diff output
					show result.
				Verify at least 1 additional method exists with reflection
					ShowInterest show = new ShowInterest();
					Class solution = show.getClass(); 
					solution.getDeclaredMethods();
					Method m = solution.getDeclaredMethod("",null);
					m.invoke(show, null);
	*/
	public static void main(String[] args){
		
		if(debug){
			System.out.println("DEBUG MODE: passing constant file to test against");
			String[] temp = {"/home/anony/Spring 2015/Projects/AutoGrader/src/model/ShowInterestShell.java"};
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
