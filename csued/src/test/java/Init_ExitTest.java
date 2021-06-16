import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author   Juyeong Lee
 */
public class Init_ExitTest {
	
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
	 * Start_Failed test
	 * 
	 * Expected: false
	 * Actual: false
	 * Result: Pass
	 */
	@Test
	public void Start_Failed_test() throws IOException
	{
		String[] args = {"C:\\Users\\juyeong\\Desktop\\Softwre-Quality-Assurance\\csued\\init_exit_test.txt"};
		File_Buffer fb = new File_Buffer();
		Init_Exit ie = new Init_Exit(args, fb);
		
		assertEquals(false, ie.Start_Failed());
	}
	
	/**
	 * Init_Exit test (no argument)
	 * 
	 * Expected: "PROGRAM INVOCATION ARGUMENT ERROR(S): Terminating the program..."
	 * Actual: "PROGRAM INVOCATION ARGUMENT ERROR(S): Terminating the program..."
	 * Result: Pass
	 */
	@Test
	public void Init_Exit_test1() throws IOException
	{
		String[] args = {};
		File_Buffer fb = new File_Buffer();
		Init_Exit ie = new Init_Exit(args, fb);
		
		assertEquals("PROGRAM INVOCATION ARGUMENT ERROR(S): Terminating the program..." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * Init_Exit test (new file)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created."
	 * Result: Pass
	 */
	@Test
	public void Init_Exit_test2() throws IOException
	{
		String[] args = {"C:\\Users\\juyeong\\Desktop\\Softwre-Quality-Assurance\\csued\\init_exit_test.txt"};
		File_Buffer fb = new File_Buffer();
		Init_Exit ie = new Init_Exit(args, fb);
		
		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * Init_Exit test
	 * 
	 * Expected: ""
	 * Actual: ""
	 * Result: Pass
	 */
	@Test
	public void Init_Exit_test3() throws IOException
	{
		String[] args = {"C:\\Users\\juyeong\\Desktop\\Softwre-Quality-Assurance\\csued\\lines.txt"};
		File_Buffer fb = new File_Buffer();
		Init_Exit ie = new Init_Exit(args, fb);
		
		assertEquals("", outContent.toString());
	}
	
	/**
	 * Do_Update test (empty file)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  File written but it will be an empty file."
	 * Actual: "USER EDIT FILE EMPTY:  File written but it will be an empty file."
	 * Result: Pass
	 */
	@Test
	public void Do_Update_test1() throws IOException
	{
		String[] args = {"C:\\Users\\juyeong\\Desktop\\Softwre-Quality-Assurance\\csued\\empty.txt"};
		File_Buffer fb = new File_Buffer();
		Init_Exit ie = new Init_Exit(args, fb);
		ie.Do_Update(fb);
		
		assertEquals("USER EDIT FILE EMPTY:  File written but it will be an empty file." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * Do_Update test
	 * 
	 * Expected: "init_exit_1
				  init_exit_2
				  init_exit_3
				  init_exit_4
				  init_exit_5
				  init_exit_6"
	 * Actual: "init_exit_1
			    init_exit_2
		  	    init_exit_3
			    init_exit_4
			    init_exit_5"
	 * Result: Fail
	 */
	@Test
	public void Do_Update_test2() throws IOException
	{
		Path path = Paths.get("C:\\Users\\juyeong\\Desktop\\Softwre-Quality-Assurance\\csued\\init_exit.txt");
		byte[] bytes = Files.readAllBytes(path);
		String originalStr = new String(bytes, StandardCharsets.UTF_8);
		
		String[] args = {"C:\\Users\\juyeong\\Desktop\\Softwre-Quality-Assurance\\csued\\init_exit.txt"};
		File_Buffer fb = new File_Buffer();
		Init_Exit ie = new Init_Exit(args, fb);
		ie.Do_Update(fb);
		
		bytes = Files.readAllBytes(path);
		String afterStr = new String(bytes, StandardCharsets.UTF_8);
		
		assertEquals(originalStr, afterStr);
	}
	
}
