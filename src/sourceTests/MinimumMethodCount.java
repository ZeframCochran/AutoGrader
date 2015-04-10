package sourceTests;

import static view.Log.deductPoints;
import static view.Log.write;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.Log;

public class MinimumMethodCount extends tests.SourcecodeTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		Log.flushToFile();
	}

	@Test
	public void test(String sourceCode) {
		Pattern regex = Pattern.compile("(void|boolean|byte|char|short|int|long|float|double|Scanner)\\s+\\w+\\s*\\(");
		Matcher matcher = regex.matcher(sourceCode);
		int methodCount = 0;
		
		while(matcher.find()){
			methodCount++;
		}
		
		if(methodCount < 2){
			write("\t-100% You MUST have at least 2 methods including main.");
			deductPoints(100);
		}
		else{
			write("Passed test: MinimumMethodCount");
		}
		
	}

}
