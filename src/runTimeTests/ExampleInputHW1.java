package runTimeTests;
import static view.Log.deductPoints;
import static view.Log.write;
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
	public void test() {
		String fakeInput = "1\nJerod\nEwert\n1000\n25\n6.5\n100\n";
		String out = RuntimeChecks.runMain(fakeInput);

		if(out.contains("10713.0")){
			write("Passed exampleInput test case");
		} else {
			write("Failed test case 1: output of your program did not include the correct reponse.");
			deductPoints(5);
		}
	}

}
