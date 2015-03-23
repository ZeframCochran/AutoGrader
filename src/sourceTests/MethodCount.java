package sourceTests;

import static controller.Log.write;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.Log;

public class MethodCount extends tests.SourcecodeTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		Log.flushToFile();
	}

	@Test
	public void test(String sourceCode) {
		Pattern regex = Pattern.compile("public static \\w+\\s\\w+\\(.*\\)");
		Matcher matcher = regex.matcher(sourceCode);
		int methodCount = 0;
		while(matcher.find()){
			methodCount++;
		}
		write("\tMethod count: " + methodCount);
	}

}
