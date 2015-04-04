package runTimeTests;

import static view.Log.write;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.Log;

public class ExampleInputHW1 extends tests.RuntimeTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		Log.flushToFile();
	}

	@Test
	public void test(String result) {
		String fakeInput = "1\nJerod\nEwert\n1000\n25\n6.5\n100\n";
		String out = RuntimeChecks.runMain(fakeInput);
		
		if(result.contains("")){
			write("Passed test case 1");
		} else {
			write("Failed test case 1: output of your program did not include the correct reponse.");
		}
	}

}
