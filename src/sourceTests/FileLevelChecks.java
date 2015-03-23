package sourceTests;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tests.SourcecodeTest;
import static controller.Log.*;

public class FileLevelChecks {


	public String check(String sourceCode) {
		try {
			
			ArrayList<SourcecodeTest> tests = new ArrayList<SourcecodeTest>();//[tests.SourcecodeTest] StudentNameProvided;
			
			tests.add(new StudentNameProvided());
			tests.add(new CommentCount());
			tests.add(new MethodCount());
			tests.add(new CommentDensity());
			tests.add(new CheckIdentifiers());

			//Currently using this to check identifier quality until I find a way to automate it
			write("Identifiers: ");
			Pattern regex = Pattern.compile("\\s(boolean|byte|char|short|int|long|float|double|Scanner|class)\\s\\w+");
			Matcher matcher = regex.matcher(sourceCode);
			while(matcher.find()){
				write("\t"+matcher.group().trim());
			}

			//Get class name for reflection
			String className = "";
			regex = Pattern.compile("(class)\\s\\w+");
			matcher = regex.matcher(sourceCode);
			if(matcher.find()){
				className += (matcher.group().replace("class ", ""));
			}
			
			//Get package name for reflection
			regex = Pattern.compile("(package)\\s\\w+");
			matcher = regex.matcher(sourceCode);
			if(matcher.find()){
				className = (matcher.group().replace("package ", "") + "." + className);
			}
			
			return className;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
