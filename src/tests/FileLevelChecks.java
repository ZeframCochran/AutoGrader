package tests;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static controller.Log.*;

public class FileLevelChecks {


	public String check(String content) {
		try {
			
			//Get student name
			Pattern regex = Pattern.compile("([A-Z])\\w+\\s([A-Z])\\w+",0);
			Matcher matcher = regex.matcher(content);
			matcher.find();
			write("\tName: "+matcher.group().trim());
			
			//Check for methods
			regex = Pattern.compile("(\\/\\/|\\*\\/)");
			matcher = regex.matcher(content);
			int commentCount = 0;
			while(matcher.find()){
				commentCount++;
			}
			write("\tHow many comments: " + commentCount);
			
			
			//Check comment density, at least 1 per method 
			regex = Pattern.compile("public static \\w+\\s\\w+\\(.*\\)");
			matcher = regex.matcher(content);
			int methodCount = 0;
			while(matcher.find()){
				methodCount++;
			}
			write("\tHow many methods: " + methodCount);
			
			if(methodCount>commentCount){
				write("\tMethods without comments detected.");
			}
			
			//Check variable names
			regex = Pattern.compile("\\s(boolean|byte|char|short|int|long|float|double|Scanner)\\s[A-Z]\\w+");
			matcher = regex.matcher(content);
			while(matcher.find()){
				write("\tVariables with bad capitalization detected: "+matcher.group());
			}
			
			//Check class name format
			regex = Pattern.compile("(class)\\s[a-z]\\w+");
			matcher = regex.matcher(content);
			while(matcher.find()){
				write("\tClass with bad capitalization detected."+(matcher.group()));
			}

			//Get class name for reflection
			String className = "";
			regex = Pattern.compile("(class)\\s\\w+");
			matcher = regex.matcher(content);
			if(matcher.find()){
				className += (matcher.group().replace("class ", ""));
			}
			
			//Get package name for reflection
			regex = Pattern.compile("(package)\\s\\w+");
			matcher = regex.matcher(content);
			if(matcher.find()){
				className = (matcher.group().replace("package ", "") + "." + className);
			}
			
			write("Identifiers: ");
			regex = Pattern.compile("\\s(boolean|byte|char|short|int|long|float|double|Scanner|class)\\s\\w+");
			matcher = regex.matcher(content);
			while(matcher.find()){
				write("\t"+matcher.group().trim());
			}
			return className;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
