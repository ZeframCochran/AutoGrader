package sourceTests;

import static view.Log.write;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.Log;

public class IdentifierPrint extends tests.SourcecodeTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		Log.flushToFile();
	}

	@Test
	public void test(String sourceCode) {
		//Currently using this to check identifier quality manually until I find a way to automate it
		write("Identifiers: ");
		Pattern regex = Pattern.compile("\\s(boolean|byte|char|short|int|long|float|double|Scanner|class)\\s\\w+");
		Matcher matcher = regex.matcher(sourceCode);
		while(matcher.find()){
			write("\t"+matcher.group().trim());
		}
		
	}

}
