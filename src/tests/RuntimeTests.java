package tests;

public interface RuntimeTests {
	public void setUp() throws Exception;
	public void tearDown() throws Exception;
	public void test(String className);
}
