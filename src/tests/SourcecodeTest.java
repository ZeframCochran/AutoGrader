package tests;

public abstract class SourcecodeTest {
	public abstract void setUp() throws Exception;
	public abstract void tearDown() throws Exception;
	public abstract void test(String sourceCode);
}
