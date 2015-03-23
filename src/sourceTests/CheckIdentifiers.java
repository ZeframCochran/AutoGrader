package sourceTests;

import static controller.Log.write;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.Log;

public class CheckIdentifiers extends tests.SourcecodeTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		Log.flushToFile();
	}

	@Test
	public void test(String sourceCode) {
		//Check variable names
		Pattern regex = Pattern.compile("\\s(boolean|byte|char|short|int|long|float|double|Scanner)\\s[A-Z]\\w+");
		Matcher matcher = regex.matcher(sourceCode);
		while(matcher.find()){
			write("\tVariables with bad capitalization detected: "+matcher.group());
		}
		
		//Check class name format
		regex = Pattern.compile("(class)\\s[a-z]\\w+");
		matcher = regex.matcher(sourceCode);
		while(matcher.find()){
			write("\tClass with bad capitalization detected."+(matcher.group()));
		}
	}

}
