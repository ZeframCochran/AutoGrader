package controller;
import java.lang.reflect.Method;

public class GradingController {
	/*
	Load the student work
		For each solution:
			File level:
				Compile
				Get student name
				Check Variable names
				Check file for comments before each method
			
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
	
	//Creates a list of student java files
	//Attempts to compile and reports the result
	
	
}
