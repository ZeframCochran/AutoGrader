package view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class Log {
	private static String path = "runLog.txt";
	private static String volatileLog = "";
	public static void write(String s){
		System.out.println(s);
		volatileLog += s+"\n";
	}
	public static boolean flushToFile(){
		try{
			File file = new File(path);
			file.createNewFile();
			
			if(file.canWrite()){
				writeToFile(file);
				System.out.print("Wrote to: " + file.getAbsolutePath());
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

}
