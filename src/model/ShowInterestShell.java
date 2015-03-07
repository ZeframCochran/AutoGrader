package model;

import java.util.Scanner;

//include required comments
//import appropriate classes
public class ShowInterestShell {
	static Scanner kb = new Scanner(System.in);
    public static void main(String[] args) 
	{
	    //ask the required info, 
    	out("How many times do you want to run the program?");
    	int iterations = kb.nextInt();
			//create a for loop, 
    		for(int line = 1; line <= iterations; line++ ){
    			prompt();
    		}
    }
    
    //Ask the questions and print the table
    private static void prompt(){
    	out("Enter your name:");
    	String fName = kb.nextLine();
    	out("Enter your last name:");
    	String lName = kb.nextLine();
    	out("Enter the initial deposit:");
    	double deposit = kb.nextDouble();
    	out("Enter the number of years you want to calculate the interest:");
    	int years = kb.nextInt();
    	out("Enter the annual interest rate:");
    	double rate = kb.nextDouble();
    	out("Enter the amount of your monthly deposit:");
    	double amount = kb.nextDouble();
    	
    	int periods = years;
    	printTable(amount, periods, rate, deposit);
    }
	//include comments describing the method 
	public static void printTable(double amount, int periods, double rate,double deposit) 
	{
      //create a for loop, 
		for(int )
		//calculate the amount of interest for each year,
		// do other calculations
		//print the info           	   
    }

	public static void out(String s){
		System.out.println(s);
	}
	
	public static void printBackwards(String s){
		if(s.length() > 1){
			printBackwards(s.substring(1, s.length()));
		}
		System.out.print(s.charAt(0));
	}

}
