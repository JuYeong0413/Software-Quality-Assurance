/*
 * ValueImpl.java Created on December 21, 2005, 12:25 PM
 */

package lab2;

/**
 * Implements the functionality of a test configuration value.
 * <p>
 * At present, a value has only a name as an attribute.
 * 
 * @author Alan Williams
 */

public class Value
{
	/**
	 * The name associated with the value.
	 */
	private String name;

	/**
	 * Default value constructor.
	 * <p>
	 * The name is set to the empty string.
	 */
	public Value( )
	{
		name = "";
	}

	/**
	 * Value constructor that initializes the name.
	 * <p>
	 * 
	 * @param myName
	 *            The name for the new value.
	 */
	public Value( String myName )
	{
		name = myName;
	}

	/**
	 * Creates a new Value object that is a duplicate of this instance.
	 * 
	 * @return The new duplicate Value object.
	 */
	public Value copy( )
	{
		Value newValue = new Value( );
		newValue.setName( getName( ) );
		return newValue;
	}

	/**
	 * Returns the name of the Value.
	 * 
	 * @return The value's name.
	 */
	public String getName( )
	{
		return this.name;
	}

	/**
	 * Sets the name of the Value.
	 * 
	 * @param newName
	 *            The new name for the value.
	 */

	public void setName( String newName )
	{
		this.name = newName;
	}

	/**
	 * Returns a string representation of the value.
	 * 
	 * @return The name of the parameter value.
	 */
	@Override
	public String toString( )
	{
		return getName( );
	}

	/**
	 * Compares this Value object with another.
	 * <p>
	 * If the two objects are identical, or have the same name, they are
	 * considered to be equal.
	 * 
	 * @return true if the values are identical or have the same name, and false
	 *         otherwise.
	 */
	public boolean equals( Value operand )
	{
		boolean result = false;

		if ( this == operand )
		{
			result = true;
		}
		else if ( operand == null )
		{
			result = false;
		}
		else
		{
			result = name.equals( operand.name );
		}
		return result;
	}

}