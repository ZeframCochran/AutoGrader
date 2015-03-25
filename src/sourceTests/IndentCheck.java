package sourceTests;

import static view.Log.write;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.Log;

public class IndentCheck extends tests.SourcecodeTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		Log.flushToFile();
	}

	@Test
	public void test(String sourceCode) {
		/*
		 * This finds lines ending in { and determines how indented that line was
		 * Then it finds the next non-blank line and checks that it is indented further
		 */
		String[] lines = sourceCode.split("\\n");
		Pattern braceRegex = Pattern.compile("^.*\\{\\n");
		Matcher matcher;
		for(int line = 0; line < lines.length; line++){
			matcher = braceRegex.matcher(lines[line]);
			if(matcher.find() && lines[line+1] != null){
				write(""+lines[line]);
				int outside = getIndentLevel(lines[line]);
				int inside =  getIndentLevel(lines[line+1]);
				if(outside >= inside){
					write("Bad indent detected: \n"+lines[line]+"\n"+lines[line+1]+"\n");
				}
			}
		}
		
	}

	private int getIndentLevel(String line) {
		int totalLength = line.length();
		int trimmedLength = line.trim().length();
		return(totalLength - trimmedLength);
	}

}
