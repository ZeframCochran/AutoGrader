package tests;

public interface RuntimeTest {
	public void setUp() throws Exception;
	public void tearDown() throws Exception;
	public void test(String className);
}
