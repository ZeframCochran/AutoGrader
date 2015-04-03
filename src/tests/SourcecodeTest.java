package tests;

public interface SourcecodeTest {
	public void setUp() throws Exception;
	public void tearDown() throws Exception;
	public void test(String sourceCode);
}
