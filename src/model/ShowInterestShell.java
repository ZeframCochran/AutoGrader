package model;

import java.util.Scanner;

//include required comments
//Jerod Ewert
//11pm Lecture
//import appropriate classes
public class ShowInterestShell {
	static Scanner kb = new Scanner(System.in);
    public static void main(String[] args) 
	{
	    //ask the required info, 
    	out("How many times do you want to run the program?");
    	int iterations = Integer.parseInt(kb.nextLine());
		//create a for loop, 
		prompt();    	
		for(int line = 1; line < iterations; line++ ){
			out("\n***************************************");
			prompt();
		}
    }
    
    //Ask the questions and print the table
    private static void prompt(){
    	
    	double initialDeposit;
    	int years;
    	double interestRate;
    	String fName;
    	String lName;
    	double yearlyDepositAmount;
    	
    	out("Enter your name:");
    	//fName = kb.nextLine();
    	
    	out("Enter your last name:");
    	//lName = kb.nextLine();
    	
    	out("Enter the initial deposit:");
    	initialDeposit = 1000;//kb.nextDouble();
    	
    	out("Enter the number of years you want to calculate the interest:");
    	years = 25;//kb.nextInt();
    	
    	out("Enter the annual interest rate:");
    	interestRate = 6.5;//kb.nextDouble();
    	
    	out("Enter the amount of your yearly deposit:");
    	yearlyDepositAmount = 100;//kb.nextDouble();
    	
    	printTable(yearlyDepositAmount, years, interestRate, initialDeposit);
    	
    }
    
	//This method prints a table of our projected bank account with interest. 
	public static void printTable(double yearlyDeposit, int years, double rate,double balance) 
	{
		double interestRate = rate/100;
		double interest;
		
		out("Year\tInterest\tDeposit\tNew Balance");
		out("start\t\t\t\t"+balance);
		
		for(int year = 1; year <= years; year++){
			interest = Math.round(interestRate * balance);
			balance +=(interest+yearlyDeposit);
			out(year+"\t"+interest+"\t\t"+(double)Math.round(yearlyDeposit)+"\t"+(double)Math.round(balance));
		}
    }

	public static void out(String s){
		System.out.println(s);
	}
	
}