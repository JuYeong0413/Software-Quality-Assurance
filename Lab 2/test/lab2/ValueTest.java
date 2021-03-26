/**
 * 
 */
package lab2;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Alan Williams
 *
 */
public class ValueTest
{

	/**
	 * Test method for {@link lab02.Value#getName()} and (@link lab02.Value#setName()}.
	 * <p>
	 * Purpose:  use setName() to store a name in the value, and then getName() to see if
	 * the same name is retrieved.
	 */
	@Test
	public void testGetNameAndSetName( )
	{
		Value aValue = new Value( );
		aValue.setName( "Hello!" );
		
		String expected = "Hello!";
		String actual = aValue.getName( );
		assertEquals( expected, actual );	
	}
	
	/**
	 * Test method for {@link lab02.Value#equals(Value)}.
	 * <p>
	 * Purpose:  Create two Values with the same name, and verify that they are considered as equal.
	 */
	@Test
	public void testEqualsSameName( )
	{
		Value val1 = new Value( );
		val1.setName( "Hello!" );
		Value val2 = new Value( );
		val2.setName( "Hello!" );
		
		boolean expected = true;
		boolean actual = val1.equals( val2 );
		assertEquals( expected, actual );
	}

	@Test
	public void testEqualsNull()
	{
		Value val1 = new Value();
		val1.setName("Hello!");
		
		Value val2 = null;
		
		boolean expected = false;
		boolean actual = val1.equals(val2);
		
		assertEquals(expected, actual);
	}
	
}
