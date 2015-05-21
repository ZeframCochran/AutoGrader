package runTimeTests;
import static view.Log.deductPoints;
import static view.Log.write;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.Log;

public class ExampleInputHW4 extends tests.RuntimeTest {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
		Log.flushToFile();
	}

	@Test
	public void test() {
		String fakeInput = "Mom\nDad\n5\n4\n0\n2\n5\n8\n3\n1\n7\nn\n";
		String out = RuntimeChecks.runMain(fakeInput);

		Pattern regex = Pattern.compile("mom.*won",Pattern.CASE_INSENSITIVE);
		Matcher matcher = regex.matcher(out);
		matcher.find();
		if(matcher.group() == null){
			Log.write("\t-10% Mom should have won this test game (identical to the example on the assignment)");
			deductPoints(10);
		}else{
			write("Passed test: Mom wins");
		}
	}

}
