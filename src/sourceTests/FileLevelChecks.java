package sourceTests;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tests.SourcecodeTest;

public class FileLevelChecks {


	public String check(String sourceCode) {
		System.out.println("Beginning file checks..");
		try {
			
			ArrayList<SourcecodeTest> tests = new ArrayList<SourcecodeTest>();			
			tests.add(new StudentNameProvided());
			tests.add(new CommentDensity());
			tests.add(new CheckIdentifiers());
			
			//tests.add(new IndentCheck());

			for(SourcecodeTest t:tests){
				t.setUp();
				t.test(sourceCode);
				t.tearDown();
			}
			

			//Get class name for reflection
			String className = "";
			Pattern regex = Pattern.compile("public\\s+class\\s\\w+");
			Matcher matcher = regex.matcher(sourceCode);
			if(matcher.find()){
				className = (matcher.group().replace("public class ", ""));
				className = className.trim();
			}
			
			//Get package name for reflection
			regex = Pattern.compile("(package)\\s\\w+");
			matcher = regex.matcher(sourceCode);
			if(matcher.find()){
				className = (matcher.group().replace("package ", "") + "." + className);
			}
			System.out.println("Finished File checks");
			return className;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
