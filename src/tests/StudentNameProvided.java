package tests;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import controller.Log;

public class StudentNameProvided implements tests.SourcecodeTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		Log.flushToFile();
	}

	@Test
	public void test(String sourceCode) {
		//Get student name
		Pattern regex = Pattern.compile("([A-Z])\\w+\\s([A-Z])\\w+",0);
		Matcher matcher = regex.matcher(sourceCode);
		matcher.find();
		Log.write("\tName: "+matcher.group().trim());
		assertEquals("","");
		fail("Not yet implemented");
	}

}
