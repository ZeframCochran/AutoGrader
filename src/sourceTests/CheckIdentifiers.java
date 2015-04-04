package sourceTests;

import static view.Log.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.Log;

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
			write("\t-2% These should not be capitalized: "+matcher.group());
			deductPoints(2);
			
			String def = matcher.group().trim();
			int space = def.indexOf(' ');
			if(space >= 0){
				String name = def.substring(0,def.indexOf(' '));
				if(name.length() < 2){
					write("\t-2% Short identifier detected. What does "+name+" hold?");
					deductPoints(2);
				}
			}
			else {
				write("Weird definition: "+def+" human attention needed.");
				flushToFile();
				System.exit(0);
			}
		}
		
		//Check class name format
		regex = Pattern.compile("(class)\\s[a-z]\\w+");
		matcher = regex.matcher(sourceCode);
		while(matcher.find()){
			write("\t-2% This must be capitalized: "+(matcher.group()));
			deductPoints(2);
		}
		System.out.println("\tFinished Checking Identifiers");
	}

}
