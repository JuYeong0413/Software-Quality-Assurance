/*
 * ParameterImpl.java Created on December 21, 2005, 12:35 PM
 */
package lab2;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the functionality of a test configuration parameter.
 * <p>
 * A parameter contains a name, and an ordered list of parameter values each of
 * type {@link Value}.
 * 
 * @author Alan Williams
 * @see Value
 */
public class Parameter
{

	/**
	 * The name of the parameter.
	 */
	private String name;

	/**
	 * An ordered list of Value objects contained in the Parameter.
	 */
	private List<Value> contents;

	/**
	 * Default parameter constructor.
	 */
	public Parameter( )
	{
		contents = new ArrayList<Value>( );
		name = "";
	}

	/**
	 * Adds a new value to the parameter, as the last value.
	 * 
	 * @param newValue
	 *            The value to be added to this parameter.
	 */
	public void addValue( Value newValue )
	{
		contents.add( newValue );
	}

	/**
	 * Insert the specified Value to the parameter at the specified index.
	 * 
	 * @param index
	 *            The index at which the Value will be added.
	 * @param newValue
	 *            The value to be added to this parameter.
	 */
	public void insertValue( int index, Value newValue )
	{
		if ( index > getNumberOfValues( ) || index < 0 )
		{
			String exceptionMessage = "Index <" + index
					+ "> of value to be inserted is out of range 0 to "
					+ ( this.getNumberOfValues( ) - 1 );
			throw new IndexOutOfBoundsException( exceptionMessage );
		}
		contents.add( index, newValue );

	}

	/**
	 * Creates a new object that is a duplicate of this instance of
	 * Parameter.
	 * <p>
	 * The duplication is a "deep copy" in that all values contained in the
	 * Parameter are themselves duplicated.
	 * 
	 * @return The new duplicate Parameter object.
	 */
	public Parameter copy( )
	{
		Parameter result = new Parameter( );
		result.setName( getName( ) );
		for ( int index = 0; index < getNumberOfValues( ); index++ )
		{
			result.addValue( getValue( index ).copy( ) );
		}
		return result;
	}

	/**
	 * Returns the name of the Parameter.
	 * 
	 * @return The parameter name.
	 */
	public String getName( )
	{
		return this.name;
	}

	/**
	 * Returns the number of values contained within this parameter.
	 * 
	 * @return The number of parameter values.
	 */
	public int getNumberOfValues( )
	{
		return contents.size( );
	}

	/**
	 * Returns the Value at the specified index for this Parameter.
	 * 
	 * @return The Value at position <CODE>index</CODE>
	 * @param index
	 *            Index number of desired value.
	 */
	public Value getValue( int index )
	{
		if ( index >= getNumberOfValues( ) || index < 0 )
		{
			String exceptionMessage = "Index <" + index
					+ "> of value to be obtained is out of range 0 to "
					+ ( this.getNumberOfValues( ) - 1 );
			throw new IndexOutOfBoundsException( exceptionMessage );
		}
		return contents.get( index );
	}

	/**
	 * Removes the Value at the specified index for this Parameter.
	 * 
	 * @param index
	 *            Index number of Value to remove.
	 * @return The value that has been removed.
	 */
	public Value removeValue( int index )
	{
		if ( index >= getNumberOfValues( ) || index < 0 )
		{
			String exceptionMessage = "Index <" + index
					+ "> of value to be removed is out of range 0 to "
					+ ( this.getNumberOfValues( ) - 1 );
			throw new IndexOutOfBoundsException( exceptionMessage );
		}
		Value oldValue = contents.remove( index );
		return oldValue;
	}

	/**
	 * Replaces the Value at the specified index for this Parameter.
	 * 
	 * @param newValue
	 *            The new value.
	 * @param index
	 *            Index number of value to replace.
	 * @return The value that has been replaced.
	 */
	public Value replaceValue( int index, Value newValue )
	{
		if ( index >= getNumberOfValues( ) || index < 0 )
		{
			String exceptionMessage = "Index <" + index
					+ "> of value to be replaced is out of range 0 to "
					+ ( this.getNumberOfValues( ) - 1 );
			throw new IndexOutOfBoundsException( exceptionMessage );
		}

		Value oldValue = removeValue( index );
		insertValue( index, newValue );
		return oldValue;
	}

	/**
	 * Sets the name of the Parameter.
	 * 
	 * @param newName
	 *            The new name for the parameter.
	 */
	public void setName( String newName )
	{
		this.name = newName;
	}

	/**
	 * Initializer for a parameter with a Value list of specified size containing
	 * Values are given names in a numbered sequence.
	 * <p>
	 * This initializer creates a Parameter, and gives it the specified name. It
	 * also sets up a list of values of indicated size. Each of the values are
	 * given names that are the strings "1", "2", and so on up to the number of
	 * values.
	 * 
	 * @param parmName
	 *            Index of this parameter in the containing parameter set.
	 * @param numValues
	 *            Number of values for this parameter.
	 */
	public void initialize( String parmName, int numValues )
	{
		setName( parmName );
		for ( int index = 0; index < numValues; index++ )
		{
			Value newValue = new Value( );
			newValue.setName( Integer.toString( index + 1 ) );
			addValue( newValue );
		}
	}

	/**
	 * Returns a string representation of the parameter.
	 * 
	 * @return The parameter name.
	 */
	@Override
	public String toString( )
	{
		return getName( );
	}

}
