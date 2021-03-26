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
public class ParameterTest
{

	/**
	 * Test method for {@link lab02.Parameter#Parameter()}.
	 */
	@Test
	public void testCreateParameter( )
	{
		Parameter aParameter = new Parameter();
		
		int expected = 0;
		int actual = aParameter.getNumberOfValues( );
		assertEquals( expected, actual );
	}
	
	/**
	 * Test method for {@link lab02.Parameter#Parameter()}.
	 */
	@Test
	public void testAddValue( )
	{
		Parameter aParameter = new Parameter();
		Value aValue = new Value( "Get me" );
		
		aParameter.addValue( aValue );
		
		Value expected = aValue;
		Value actual = aParameter.getValue( 0 );
		assertSame( expected, actual );
		
	}
	
	

}
