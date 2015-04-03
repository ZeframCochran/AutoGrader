package sourceTests;

import static view.Log.*;

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
		Pattern regex = Pattern.compile("\\s*(boolean|byte|char|short|int|long|float|double|Scanner|public class)\\s\\w+");
		Matcher matcher = regex.matcher(sourceCode);
		String def = "";
		String name = "";
		while(matcher.find()){
			def = matcher.group().trim();
			int space = def.indexOf(' ');
			if(space >= 0){
				name = def.substring(0,def.indexOf(' '));
				if(name.length() < 2){
					write("-2% Short identifier detected. What does "+name+" hold?");
					deductPoints(2);
				}
			}
			else {
				write("Weird definition: "+def+" human attention needed.");
				flushToFile();
				System.exit(0);
			}
		}
		
	}

}
