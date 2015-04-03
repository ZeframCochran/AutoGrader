package view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class Log {
	private static String path = "runLog.txt";
	private static int score = 100;
	private static String name = "";

	private static String volatileLog = "";

	public static String getScore(){
		return score+"/100";
	}
	public static void write(String s){
		System.out.println(s);
		volatileLog += s+"\n";
	}
	
	public static void deductPoints(int points){
		if(score >= 0 && points >= 0){
			score = score - points;
		}
	}
	
	public static void setLogPath(String newPath){
		path = newPath; 
	}
	public static boolean flushToFile(){
		try{
			File file = new File(path);
			file.createNewFile();
			
			if(file.canWrite()){
				writeToFile(file);
				volatileLog = "";
			}
			else {
				System.err.print("Could not write to: " + file.getAbsolutePath());
				return false;
			}
		} catch(IOException f){
			f.printStackTrace();
			return false;			
		}
		return true;
	}
	
	private static boolean writeToFile(File file) throws IOException{
		Writer writer;
		FileOutputStream outStream = new FileOutputStream(file, true);
		OutputStreamWriter outputWriter = new OutputStreamWriter(outStream);
	    writer = new BufferedWriter(outputWriter);
	    writer.write(volatileLog);
	    writer.close();
		return false;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		Log.name = name;
	}

}
