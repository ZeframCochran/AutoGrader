package sourceTests;

import static view.Log.deductPoints;
import static view.Log.write;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.Log;

public class CommentDensity extends tests.SourcecodeTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		Log.flushToFile();
	}

	@Test
	public void test(String sourceCode) {
		Pattern regex = Pattern.compile("(\\/\\/|\\*\\/)");
		Matcher matcher = regex.matcher(sourceCode);
		int commentCount = 0;
		while(matcher.find()){
			commentCount++;
		}
		
		regex = Pattern.compile("public static \\w+\\s\\w+\\(.*\\)");
		matcher = regex.matcher(sourceCode);
		int methodCount = 0;
		while(matcher.find()){
			methodCount++;
		}
		
		if(methodCount>commentCount){
			write("\t-20% Methods without comments detected");
			deductPoints(20);
		}
		
	}

}
