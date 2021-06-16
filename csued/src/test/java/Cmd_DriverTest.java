import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
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
public class Cmd_DriverTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	public Cmd_Driver cd = new Cmd_Driver();
	public File_Buffer fb;
	public Init_Exit ie;
	
	private void readFile(String fileName) throws IOException
	{
		String[] args = {"C:\\Users\\juyeong\\Desktop\\Softwre-Quality-Assurance\\csued\\" + fileName};
		fb = new File_Buffer();
		ie = new Init_Exit(args, fb);
	}
	
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
	 * Q command test
	 * 
	 * Expected: "Hello World!"
	 * Actual: "Hello World!"
	 * Result: Pass
	 */
	@Test
	public void Cmd_Q_test() throws IOException
	{
		Path path = Paths.get("C:\\Users\\juyeong\\Desktop\\Softwre-Quality-Assurance\\csued\\hello_world.txt");
		byte[] bytes = Files.readAllBytes(path);
		String originalStr = new String(bytes, StandardCharsets.UTF_8);
		
		readFile("hello_world.txt");
		String newStr = System.getProperty("line.separator");
		ByteArrayInputStream stream = new ByteArrayInputStream(newStr.getBytes());
		System.setIn(stream);
		cd.Cmd_A(fb);
		
		// Quit & Update File
		UserCmd cmd;
		String quit = "Q";
		stream = new ByteArrayInputStream(quit.getBytes());
		System.setIn(stream);
		cmd = Parser.parseCmdLine();
		cd.RunCmd(fb, cmd);
		
		bytes = Files.readAllBytes(path);
		String updatedStr = new String(bytes, StandardCharsets.UTF_8);
		
		assertEquals(originalStr, updatedStr);
	}
	
	/**
	 * X command test
	 * 
	 * Expected: "Hello World!"
	 * Actual: "Hello World!"
	 * Result: Pass
	 */
	@Test
	public void Cmd_X_test() throws IOException
	{
		Path path = Paths.get("C:\\Users\\juyeong\\Desktop\\Softwre-Quality-Assurance\\csued\\hello_world.txt");
		byte[] bytes = Files.readAllBytes(path);
		String originalStr = new String(bytes, StandardCharsets.UTF_8);
		
		readFile("hello_world.txt");
		
		// Exit & DO NOT Update File
		UserCmd cmd;
		String exit = "X";
		ByteArrayInputStream stream = new ByteArrayInputStream(exit.getBytes());
		System.setIn(stream);
		cmd = Parser.parseCmdLine();
		cd.RunCmd(fb, cmd);
		
		bytes = Files.readAllBytes(path);
		String updatedStr = new String(bytes, StandardCharsets.UTF_8);
		
		assertEquals(originalStr, updatedStr);
	}
	
	/**
	 * T command test
	 * 
	 * Expected: 1
	 * Actual: 1
	 * Result: Pass
	 */
	@Test
	public void Cmd_T_test() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_T(fb);
		
		assertEquals(1, fb.GetCLN());
	}
	
	/**
	 * E command test
	 * 
	 * Expected: 3
	 * Actual: 3
	 * Result: Pass
	 */
	@Test
	public void Cmd_E_test() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_E(fb);
		
		assertEquals(3, fb.GetCLN());
	}
	
	/**
	 * N command test
	 * 
	 * Expected: 2
	 * Actual: 2
	 * Result: Pass
	 */
	@Test
	public void Cmd_N_test1() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_N(fb, 1);
		
		assertEquals(2, fb.GetCLN());
	}
	
	/**
	 * N command test (negative)
	 * 
	 * Expected: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Actual: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_N_test2() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_N(fb, 0);
		
		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken." +
					 System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * N command test (negative)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			  ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_N_test3() throws IOException
	{
		readFile("cmd_n_test3.txt");
		cd.Cmd_N(fb, 5);
		
		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator") +
					 "ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * B command test
	 * 
	 * Expected: 2
	 * Actual: 2
	 * Result: Pass
	 */
	@Test
	public void Cmd_B_test1() throws IOException
	{
		readFile("lines.txt");
		fb.SetCLN(3);
		cd.Cmd_B(fb, 1);
		
		assertEquals(2, fb.GetCLN());
	}
	
	/**
	 * B command test (negative)
	 * 
	 * Expected: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Actual: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_B_test2() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_B(fb, 0);
		
		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken." +
					 System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * B command test (negative)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			  ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_B_test3() throws IOException
	{
		readFile("cmd_b_test3.txt");
		cd.Cmd_B(fb, 5);
		
		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator") +
					 "ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * W command test
	 * 
	 * Expected: "At Edit File Line 1"
	 * Actual: "At Edit File Line 1"
	 * Result: Pass
	 */
	@Test
	public void Cmd_W_test() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_W(fb);
		
		assertEquals("At Edit File Line " + fb.GetCLN() + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * C command test
	 * 
	 * Expected: "Total Edit File Lines: 3"
	 * Actual: "Total Edit File Lines: 3"
	 * Result: Pass
	 */
	@Test
	public void Cmd_C_test() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_C(fb);
		
		assertEquals("Total Edit File Lines: " + fb.NumLins() + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * L command test
	 * 
	 * Expected: "First line
	 * 			  Second line", 2
	 * Actual: "", 2
	 * Result: Fail
	 */
	@Test
	public void Cmd_L_test1() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_L(fb, 2);
		
		assertEquals(2, fb.GetCLN());
		assertEquals("First line" + System.getProperty("line.separator") +
					 "Second line" + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * L command test (negative)
	 * 
	 * Expected: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Actual: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_L_test2() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_L(fb, 0);
		
		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken." +
					 System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * L command test (negative)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			  ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_L_test3() throws IOException
	{
		readFile("cmd_l_test3.txt");
		cd.Cmd_L(fb, 5);
		
		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator") +
					 "ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * S command test
	 * 
	 * Expected: "First line
	 * 			  Second line", 1
	 * Actual: "", 1
	 * Result: Fail
	 */
	@Test
	public void Cmd_S_test1() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_S(fb, 2);
		
		assertEquals(1, fb.GetCLN());
		assertEquals("First line" + System.getProperty("line.separator") +
					 "Second line" + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * S command test (negative)
	 * 
	 * Expected: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Actual: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_S_test2() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_S(fb, 0);
		
		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken." +
					 System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * S command test (negative)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			  ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_S_test3() throws IOException
	{
		readFile("cmd_s_test3.txt");
		cd.Cmd_S(fb, 5);
		
		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator") +
					 "ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * D command test
	 * 
	 * Expected: 2, 2
	 * Actual: 2, 2
	 * Result: Pass
	 */
	@Test
	public void Cmd_D_test1() throws IOException
	{
		readFile("lines.txt");
		int lines = fb.NumLins();
		fb.SetCLN(2);
		cd.Cmd_D(fb, 1);
		
		assertEquals(2, fb.GetCLN());
		assertEquals(lines - 1, fb.NumLins());
	}
	
	/**
	 * D command test (negative)
	 * 
	 * Expected: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Actual: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_D_test2() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_D(fb, 0);
		
		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken." +
					 System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * D command test (negative)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			  ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_D_test3() throws IOException
	{
		readFile("cmd_d_test3.txt");
		cd.Cmd_D(fb, 5);
		
		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator") +
					 "ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * A command test
	 * 
	 * Expected: 3, 1
	 * Actual: 3, 1
	 * Result: Pass
	 */
	@Test
	public void Cmd_A_test() throws IOException
	{
		readFile("lines.txt");
		int lines = fb.NumLins();
		String newStr = System.getProperty("line.separator");
		ByteArrayInputStream stream = new ByteArrayInputStream(newStr.getBytes());
		System.setIn(stream);
		cd.Cmd_A(fb);

		assertEquals(lines, fb.NumLins());
		assertEquals(1, fb.GetCLN());
	}
	
	/**
	 * F command test
	 * 
	 * Expected: "1: First line", 1
	 * Actual: "1: First line
	 * 			2: Second line", 2
	 * Result: Fail
	 */
	@Test
	public void Cmd_F_test1() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_F(fb, 1, "l");

		assertEquals("1: First line" + System.getProperty("line.separator"), outContent.toString());
		assertEquals(1, fb.GetCLN());
	}
	
	/**
	 * F command test (negative)
	 * 
	 * Expected: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Actual: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_F_test2() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_F(fb, 0, "l");

		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken." +
					 System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * F command test (negative)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			  ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_F_test3() throws IOException
	{
		readFile("cmd_f_test3.txt");
		cd.Cmd_F(fb, 1, "l");

		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator") +
					 "ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * F command test (negative)
	 * 
	 * Expected: "A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken."
	 * Actual: "A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_F_test4() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_F(fb, 1, "");

		assertEquals("A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken." +
					 System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * F command test (not matching)
	 * 
	 * Expected: ""
	 * Actual: ""
	 * Result: Pass
	 */
	@Test
	public void Cmd_F_test5() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_F(fb, 1, "abcd");

		assertEquals("", outContent.toString());
	}
	
	/**
	 * R command test (single lines)
	 * 
	 * Expected: "First Line", 1
	 * Actual: "", 1
	 * Result: Fail
	 */
	@Test
	public void Cmd_R_test1() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_R(fb, 1, "l", "L");

		assertEquals("First Line" + System.getProperty("line.separator"), outContent.toString());
		assertEquals(1, fb.GetCLN());
	}
	
	/**
	 * R command test (multiple lines)
	 * 
	 * Expected: "First Line
	 * 			  Second Line
	 * 			  Third Line", 3
	 * Actual: "", 3
	 * Result: Fail
	 */
	@Test
	public void Cmd_R_test2() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_R(fb, 3, "l", "L");

		assertEquals("First Line" + System.getProperty("line.separator") +
					 "Second Line" + System.getProperty("line.separator") +
					 "Third Line" + System.getProperty("line.separator"), outContent.toString());
		assertEquals(3, fb.GetCLN());
	}
	
	/**
	 * R command test (negative)
	 * 
	 * Expected: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Actual: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_R_test3() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_R(fb, 0, "l", "L");

		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken." +
					 System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * R command test (negative)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			  ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_R_test4() throws IOException
	{
		readFile("cmd_r_test3.txt");
		cd.Cmd_R(fb, 1, "l", "L");

		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator") +
					 "ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * R command test (negative)
	 * 
	 * Expected: "A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken."
	 * Actual: "A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_R_test5() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_R(fb, 1, "", "L");

		assertEquals("A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken." +
					 System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * R command test (not matching)
	 * 
	 * Expected: ""
	 * Actual: Error(java.lang.StringIndexOutOfBoundsException)
	 * Result: Error
	 */
	@Test
	public void Cmd_R_test6() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_R(fb, 1, "abcd", "L");

		assertEquals("", outContent.toString());
	}
	
	/**
	 * Y command test
	 * 
	 * Expected: 3
	 * Actual: 3
	 * Result: Pass
	 */
	@Test
	public void Cmd_Y_test1() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_Y(fb, 2);

		assertEquals(2, fb.GetCLN());
	}
	
	/**
	 * Y command test (negative)
	 * 
	 * Expected: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Actual: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_Y_test2() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_Y(fb, 0);

		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken." +
					 System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * Y command test (negative)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			  ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_Y_test3() throws IOException
	{
		readFile("cmd_y_test3.txt");
		cd.Cmd_Y(fb, 3);

		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator") +
					 "ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * Z command test
	 * 
	 * Expected: 1, 1
	 * Actual: 1, 1
	 * Result: Pass
	 */
	@Test
	public void Cmd_Z_test1() throws IOException
	{
		readFile("lines.txt");
		int lines = fb.NumLins();
		cd.Cmd_Z(fb, 2);

		assertEquals(1, fb.GetCLN());
		assertEquals(lines - 2, fb.NumLins());
	}
	
	/**
	 * Z command test (negative)
	 * 
	 * Expected: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.
	 * 			  NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Actual: "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.
	 * 			NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_Z_test2() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_Z(fb, 0);

		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken." + System.getProperty("line.separator") +
					 "NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * Z command test (negative)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			  ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.
	 * 			  ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.
	 * 			ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_Z_test3() throws IOException
	{
		readFile("cmd_z_test3.txt");
		cd.Cmd_Z(fb, 3);

		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator") +
					 "ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken." + System.getProperty("line.separator") +
					 "ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * P command test
	 * 
	 * Expected: 1, 1
	 * Actual: 1, 1
	 * Result: Pass
	 */
	@Test
	public void Cmd_P_test1() throws IOException
	{
		readFile("lines.txt");
		int lines = fb.NumLins();
		cd.Cmd_Y(fb, 3);
		cd.Cmd_P(fb);

		assertEquals(lines + 3, fb.GetCLN());
		assertEquals(lines + 3, fb.NumLins());
	}
	
	/**
	 * P command test (negative)
	 * 
	 * Expected: "NO LINES IN YANK BUFFER TO PUT:  No action taken."
	 * Actual: "NO LINES IN YANK BUFFER TO PUT:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_P_test2() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_P(fb);

		assertEquals("NO LINES IN YANK BUFFER TO PUT:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * I command test
	 * 
	 * Expected: ""
	 * Actual: ""
	 * Result: Pass
	 */
	@Test
	public void Cmd_I_test1() throws IOException
	{
		readFile("keywords.txt");
		cd.Cmd_I(fb);

		assertEquals("", outContent.toString());
	}
	
	/**
	 * I command test (At sign with no keyword)
	 * 
	 * Expected: ""
	 * Actual: ""
	 * Result: Pass
	 */
	@Test
	public void Cmd_I_test2() throws IOException
	{
		readFile("keywords2.txt");
		cd.Cmd_I(fb);

		assertEquals("", outContent.toString());
	}
	
	/**
	 * I command test (negative)
	 * 
	 * Expected: "THERE ARE NO KEYWORDS AT TOP OF FILE TO INDEX:  No action taken."
	 * Actual: "THERE ARE NO KEYWORDS AT TOP OF FILE TO INDEX:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_I_test3() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_I(fb);

		assertEquals("THERE ARE NO KEYWORDS AT TOP OF FILE TO INDEX:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * I command test (negative)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			  THERE ARE NO KEYWORDS AT TOP OF FILE TO INDEX:  No action taken."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			THERE ARE NO KEYWORDS AT TOP OF FILE TO INDEX:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_I_test4() throws IOException
	{
		readFile("cmd_i_test4.txt");
		cd.Cmd_I(fb);

		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator") +
					 "THERE ARE NO KEYWORDS AT TOP OF FILE TO INDEX:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * K command test
	 * 
	 * Expected: "1  "
	 * Actual: "1  "
	 * Result: Pass
	 */
	@Test
	public void Cmd_K_test1() throws IOException
	{
		readFile("keywords.txt");
		cd.Cmd_I(fb);
		cd.Cmd_K("keyword1");

		assertEquals("1  " + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * K command test (negative)
	 * 
	 * Expected: "THIS KEYWORD DOES NOT EXIST:  No action taken."
	 * Actual: "THIS KEYWORD DOES NOT EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_K_test2() throws IOException
	{
		readFile("keywords.txt");
		cd.Cmd_K("keyword");

		assertEquals("THIS KEYWORD DOES NOT EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * K command test (negative)
	 * 
	 * Expected: "THIS KEYWORD DOES NOT EXIST:  No action taken."
	 * Actual: "THIS KEYWORD DOES NOT EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_K_test3() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_K("keyword3");

		assertEquals("THIS KEYWORD DOES NOT EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * K command test (negative)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			  THIS KEYWORD DOES NOT EXIST:  No action taken."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			THIS KEYWORD DOES NOT EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_K_test4() throws IOException
	{
		readFile("cmd_i_test4.txt");
		cd.Cmd_K("keyword");

		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator") +
					 "THIS KEYWORD DOES NOT EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * K command test (no match)
	 * 
	 * Expected: "THIS KEYWORD DOES NOT EXIST:  No action taken."
	 * Actual: "THIS KEYWORD DOES NOT EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_K_test5() throws IOException
	{
		readFile("keywords.txt");
		cd.Cmd_I(fb);
		cd.Cmd_K("something");

		assertEquals("THIS KEYWORD DOES NOT EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * K command test (no match)
	 * 
	 * Expected: "A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken."
	 * Actual: "A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_K_test6() throws IOException
	{
		readFile("keywords.txt");
		cd.Cmd_I(fb);
		cd.Cmd_K("");

		assertEquals("A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * O command test
	 * 
	 * Expected: "Banana
	 * 
	 * 			  Apple
	 * 			  Doughnut
	 * 			  Cherry"
	 * Actual: "Banana
	 * 
	 * 			Apple
	 * 		    Doughnut
	 * 			Cherry"
	 * Result: Pass
	 */
	@Test
	public void Cmd_O_test1() throws IOException
	{
		readFile("lines_with_empty_line.txt");
		cd.Cmd_O(fb, 1);
		cd.Cmd_S(fb, 10);
		
		assertEquals("Banana" + System.getProperty("line.separator") + System.getProperty("line.separator") +
					 "Apple" + System.getProperty("line.separator") + "Doughnut" + System.getProperty("line.separator") +
					 "Cherry" + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * O command test
	 * 
	 * Expected: "
	 * 			  Apple
	 * 			  Banana
	 * 			  Doughnut
	 * 			  Cherry"
	 * Actual: "
	 * 			Apple
	 * 			Banana
	 * 			Doughnut
	 * 			Cherry"
	 * Result: Pass
	 */
	@Test
	public void Cmd_O_test2() throws IOException
	{
		readFile("lines_with_empty_line.txt");
		cd.Cmd_O(fb, 3);
		fb.SetCLN(1);
		cd.Cmd_S(fb, 10);
		
		assertEquals(System.getProperty("line.separator") + "Apple" + System.getProperty("line.separator") +
					 "Banana" + System.getProperty("line.separator") + "Doughnut" + System.getProperty("line.separator") +
					 "Cherry" + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * O command test (negative)
	 * 
	 * Expected: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			  ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Actual: "USER EDIT FILE EMPTY:  No information read - file created.
	 * 			ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken."
	 * Result: Pass
	 */
	@Test
	public void Cmd_O_test3() throws IOException
	{
		readFile("cmd_o_test3.txt");
		cd.Cmd_O(fb, 3);
		
		assertEquals("USER EDIT FILE EMPTY:  No information read - file created." + System.getProperty("line.separator") +
					 "ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * M command test
	 * 
	 * Expected: "COMMAND NOT IMPLEMENTED (for F, R, O) YET."
	 * Actual: "COMMAND NOT IMPLEMENTED (for F, R, O) YET."
	 * Result: Pass
	 */
	@Test
	public void Cmd_M_test1() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_M(1, 80);
		
		assertEquals("COMMAND NOT IMPLEMENTED (for F, R, O) YET." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * M command test
	 * 
	 * Expected: "REVERSED OR BACKWARDS COLUMN RANGES ARE ILLEGAL:  No action taken.
	 * 			  COMMAND NOT IMPLEMENTED (for F, R, O) YET."
	 * Actual: "REVERSED OR BACKWARDS COLUMN RANGES ARE ILLEGAL:  No action taken.
	 * 			COMMAND NOT IMPLEMENTED (for F, R, O) YET."
	 * Result: Pass
	 */
	@Test
	public void Cmd_M_test2() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_M(80, 1);
		
		assertEquals("REVERSED OR BACKWARDS COLUMN RANGES ARE ILLEGAL:  No action taken." + System.getProperty("line.separator") +
					 "COMMAND NOT IMPLEMENTED (for F, R, O) YET." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * M command test
	 * 
	 * Expected: "COLUMN VALUES MUST BE POSITIVE & NONZERO:  No action taken.
	 * 			  COMMAND NOT IMPLEMENTED (for F, R, O) YET."
	 * Actual: "COLUMN VALUES MUST BE POSITIVE & NONZERO:  No action taken.
	 * 			COMMAND NOT IMPLEMENTED (for F, R, O) YET."
	 * Result: Pass
	 */
	@Test
	public void Cmd_M_test3() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_M(0, 0);
		
		assertEquals("COLUMN VALUES MUST BE POSITIVE & NONZERO:  No action taken." + System.getProperty("line.separator") +
					 "COMMAND NOT IMPLEMENTED (for F, R, O) YET." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * M command test
	 * 
	 * Expected: "COLUMN VALUES MUST BE POSITIVE & NONZERO:  No action taken.
	 * 			  COMMAND NOT IMPLEMENTED (for F, R, O) YET."
	 * Actual: "COMMAND NOT IMPLEMENTED (for F, R, O) YET."
	 * Result: Fail
	 */
	@Test
	public void Cmd_M_test4() throws IOException
	{
		readFile("lines.txt");
		cd.Cmd_M(0, 80);
		
		assertEquals("COLUMN VALUES MUST BE POSITIVE & NONZERO:  No action taken." + System.getProperty("line.separator") +
				"COMMAND NOT IMPLEMENTED (for F, R, O) YET." + System.getProperty("line.separator"), outContent.toString());
	}
	
	/**
	 * H command test (no argument)
	 * 
	 * Expected: not ""
	 * Actual: ""
	 * Result: Fail
	 */
	@Test
	public void Cmd_H_test1()
	{
		cd.Cmd_H(null);
		
		assertNotEquals("", outContent.toString());
	}
	
	/**
	 * H command test (with argument)
	 * 
	 * Expected: not ""
	 * Actual: ""
	 * Result: Fail
	 */
	@Test
	public void Cmd_H_test2()
	{
		cd.Cmd_H("Q");
		
		assertNotEquals("", outContent.toString());
	}
	
	/**
	 * Illegal command test
	 * 
	 * Expected: "EditCmd> INTERNAL SYSTEM ERROR:    Cmd_Driver: Illegal edit cmd name"
	 * Actual: "EditCmd> INTERNAL SYSTEM ERROR:    Cmd_Driver: Illegal edit cmd name"
	 * Result: Pass
	 */
	@Test
	public void illegal_command_test() throws IOException
	{
		UserCmd cmd;
		String cmdStr = "TT";
		ByteArrayInputStream stream = new ByteArrayInputStream(cmdStr.getBytes());
		System.setIn(stream);
		cmd = Parser.parseCmdLine();
		
		readFile("hello_world.txt");
		cd.RunCmd(fb, cmd);
		
		assertEquals("EditCmd> INTERNAL SYSTEM ERROR:    Cmd_Driver: Illegal edit cmd name" + System.getProperty("line.separator"), outContent.toString());
	}
	
}
