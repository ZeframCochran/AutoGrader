package sourceTests;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tests.SourcecodeTest;
import static view.Log.*;

public class FileLevelChecks {


	public String check(String sourceCode) {
		try {
			
			ArrayList<SourcecodeTest> tests = new ArrayList<SourcecodeTest>();//[tests.SourcecodeTest] StudentNameProvided;
			
			tests.add(new StudentNameProvided());
			tests.add(new CommentCount());
			tests.add(new MethodCount());
			tests.add(new CommentDensity());
			tests.add(new CheckIdentifiers());
			tests.add(new IdentifierPrint());
			tests.add(new IndentCheck());

			for(SourcecodeTest t:tests){
				t.setUp();
				t.test(sourceCode);
				t.tearDown();
			}
			

			//Get class name for reflection
			String className = "";
			Pattern regex = Pattern.compile("(class)\\s\\w+");
			Matcher matcher = regex.matcher(sourceCode);
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
