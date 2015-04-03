package sourceTests;

import static view.Log.deductPoints;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.Log;

public class StudentNameProvided extends tests.SourcecodeTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		Log.flushToFile();
	}

	@Test
	public void test(String sourceCode) {
		//Get student name
		Pattern regex = Pattern.compile("([A-Z])\\w+\\s([A-Z])\\w+",0);
		Matcher matcher = regex.matcher(sourceCode);
		matcher.find();
		if(matcher.group() == null){
			Log.write("\t-10% Name not capitalized or not present.");
			deductPoints(10);
		}
		String name = matcher.group().trim();
		Log.setLogPath(name.replace(" ", "_")+".log");
		Log.write("\n\tName: " + name);
		Log.setName(name);
	}

}
