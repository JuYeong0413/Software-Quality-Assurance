import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author   Juyeong Lee
 */
public class ParserTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	@Before
	public void setUp()
	{
		System.setOut(new PrintStream(outContent));
	}
	
	@After
	public void tearDown()
	{
		System.setOut(originalOut);
	}
	
	/**
	 * easter egg test
	 * 
	 * Expected: "EditCmd> 
	 * 			  You find Easter Egg!!"
	 * Actual: "EditCmd> 
	 * 			You find Easter Egg!!"
	 * Result: Pass
	 */
	@Test
	public void easter_egg_test() throws IOException
	{
		UserCmd cmd;
		String easterEgg = "dgu2021";
		ByteArrayInputStream stream = new ByteArrayInputStream(easterEgg.getBytes());
		System.setIn(stream);
		cmd = Parser.parseCmdLine();
		
		assertEquals("EditCmd> \nYou find Easter Egg!!\n" + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * Commands with one integer number of lines argument.
	 * 
	 * Expected: "EditCmd> ", true, "N 1"
	 * Actual: "EditCmd> ", true, "N 1"
	 * Result: Pass
	 */
	@Test
	public void cmd_test1() throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream("N 1".getBytes());
		System.setIn(stream);
		UserCmd cmd = Parser.parseCmdLine();
		
		assertEquals("EditCmd> ", outContent.toString());
		assertEquals(true, cmd.getOkSyntax());
		assertEquals("N 1", cmd.getCmdLine());
	}
	
	/**
	 * Commands with one integer number of lines argument. (negative)
	 * 
	 * Expected: false
	 * Actual: false
	 * Result: Pass
	 */
	@Test
	public void cmd_test2() throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream("Q 1".getBytes());
		System.setIn(stream);
		UserCmd cmd = Parser.parseCmdLine();
		
		assertEquals(false, cmd.getOkSyntax());
	}
	
	/**
	 * Commands with two integer # lines arguments.
	 * 
	 * Expected: "EditCmd> ", true, "M 1 80"
	 * Actual: "EditCmd> ", true, "M 1 80"
	 * Result: Pass
	 */
	@Test
	public void cmd_test3() throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream("M 1 80".getBytes());
		System.setIn(stream);
		UserCmd cmd = Parser.parseCmdLine();
		
		assertEquals("EditCmd> ", outContent.toString());
		assertEquals(true, cmd.getOkSyntax());
		assertEquals("M 1 80", cmd.getCmdLine());
	}
	
	/**
	 * Commands with two integer # lines arguments. (negative)
	 * 
	 * Expected: false
	 * Actual: false
	 * Result: Pass
	 */
	@Test
	public void cmd_test4() throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream("X 1 80".getBytes());
		System.setIn(stream);
		UserCmd cmd = Parser.parseCmdLine();
		
		assertEquals(false, cmd.getOkSyntax());
	}
	
	/**
	 * Commands with one delimited string argument
	 * 
	 * Expected: "EditCmd> ", true, "K K K"
	 * Actual:  "EditCmd> ", true, "K K K"
	 * Result: Pass
	 */
	@Test
	public void cmd_test5() throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream("K K K".getBytes());
		System.setIn(stream);
		UserCmd cmd = Parser.parseCmdLine();
		
		assertEquals("EditCmd> ", outContent.toString());
		assertEquals(true, cmd.getOkSyntax());
		assertEquals("K K K", cmd.getCmdLine());
	}
	
	/**
	 * Commands with one delimited string argument (negative)
	 * 
	 * Expected: false
	 * Actual: false
	 * Result: Pass
	 */
	@Test
	public void cmd_test6() throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream("V V V".getBytes());
		System.setIn(stream);
		UserCmd cmd = Parser.parseCmdLine();
		
		assertEquals(false, cmd.getOkSyntax());
	}
	
	/**
	 * Commands with one integer # lines and one delimited string argument.
	 * 
	 * Expected: "EditCmd> ", true, "F 3 xFx"
	 * Actual:  "EditCmd> ", true, "F 3 xFx"
	 * Result: Pass
	 */
	@Test
	public void cmd_test7() throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream("F 3 xFx".getBytes());
		System.setIn(stream);
		UserCmd cmd = Parser.parseCmdLine();
		
		assertEquals("EditCmd> ", outContent.toString());
		assertEquals(true, cmd.getOkSyntax());
		assertEquals("F 3 xFx", cmd.getCmdLine());
	}
	
	/**
	 * Commands with one integer # lines and one delimited string argument. (negative)
	 * 
	 * Expected: false
	 * Actual: false
	 * Result: Pass
	 */
	@Test
	public void cmd_test8() throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream("F 3 F".getBytes());
		System.setIn(stream);
		UserCmd cmd = Parser.parseCmdLine();
		
		assertEquals(false, cmd.getOkSyntax());
	}
	
	/**
	 * Commands with one integer # lines and two delimited string arguments.
	 * 
	 * Expected: "EditCmd> ", true, "R 2 xAx xFx"
	 * Actual:  "EditCmd> ", true, "R 2 xAx xFx"
	 * Result: Pass
	 */
	@Test
	public void cmd_test9() throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream("R 2 xAx xFx".getBytes());
		System.setIn(stream);
		UserCmd cmd = Parser.parseCmdLine();
		
		assertEquals("EditCmd> ", outContent.toString());
		assertEquals(true, cmd.getOkSyntax());
		assertEquals("R 2 xAx xFx", cmd.getCmdLine());
	}
	
	/**
	 * Commands with one integer # lines and two delimited string arguments. (negative)
	 * 
	 * Expected: false
	 * Actual: false
	 * Result: Pass
	 */
	@Test
	public void cmd_test10() throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream("R 2 A F".getBytes());
		System.setIn(stream);
		UserCmd cmd = Parser.parseCmdLine();
		
		assertEquals(false, cmd.getOkSyntax());
	}
	
	/**
	 * Commands with one optional letter argument.
	 * 
	 * Expected: "EditCmd> ", true, "H Q"
	 * Actual:  "EditCmd> ", true, "H Q"
	 * Result: Pass
	 */
	@Test
	public void cmd_test11() throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream("H Q".getBytes());
		System.setIn(stream);
		UserCmd cmd = Parser.parseCmdLine();
		
		assertEquals("EditCmd> ", outContent.toString());
		assertEquals(true, cmd.getOkSyntax());
		assertEquals("H Q", cmd.getCmdLine());
	}
	
	/**
	 * Commands with one optional letter argument. (negative)
	 * 
	 * Expected: false
	 * Actual: false
	 * Result: Pass
	 */
	@Test
	public void cmd_test12() throws IOException
	{
		ByteArrayInputStream stream = new ByteArrayInputStream("H ABC".getBytes());
		System.setIn(stream);
		UserCmd cmd = Parser.parseCmdLine();
		
		assertEquals(false, cmd.getOkSyntax());
	}
	
}
