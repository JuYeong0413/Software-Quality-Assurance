import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author   Juyeong Lee
 */
public class MyVectorTest {
	
	protected final static int  DEFCAPACITY  = 100; // Default init, size>0
    protected final static int  DEFEXTENSION =  20; // Default ext,  size>0
    protected final static char DEFEXTMETHOD = 'F'; // Default ext. method

	/**
	 * Default constructor test
	 * 
	 * Expected: 100, 20, 'F'
	 * Actual: 100, 20, 'F'
	 * Result: Pass
	 */
	@Test
	public void constructor_test1()
	{
		MyVector mv = new MyVector();
		
		assertEquals(DEFCAPACITY, mv.capacityOrig);
		assertEquals(DEFEXTENSION, mv.extensionOrig);
		assertEquals(DEFEXTMETHOD, mv.extensionMeth);
	}
	
	/**
	 * Constructor test
	 * 
	 * Expected: 50, 20, 'F'
	 * Actual:  50, 20, 'F'
	 * Result: Pass
	 */
	@Test
	public void constructor_test2()
	{
		MyVector mv = new MyVector(50);
		
		assertEquals(50, mv.capacityOrig);
		assertEquals(DEFEXTENSION, mv.extensionOrig);
		assertEquals(DEFEXTMETHOD, mv.extensionMeth);
	}
	
	/**
	 * Constructor test
	 * 
	 * Expected: 50, 10, 'F'
	 * Actual:  50, 10, 'F'
	 * Result: Pass
	 */
	@Test
	public void constructor_test3()
	{
		MyVector mv = new MyVector(50, 10);
		
		assertEquals(50, mv.capacityOrig);
		assertEquals(10, mv.extensionOrig);
		assertEquals(DEFEXTMETHOD, mv.extensionMeth);
	}
	
	/**
	 * Constructor test
	 * 
	 * Expected: 50, 20, 'A'
	 * Actual:  50, 20, 'A'
	 * Result: Pass
	 */
	@Test
	public void constructor_test4()
	{
		MyVector mv = new MyVector(50, 'A');
		
		assertEquals(50, mv.capacityOrig);
		assertEquals(DEFEXTENSION, mv.extensionOrig);
		assertEquals('A', mv.extensionMeth);
	}
	
	/**
	 * Constructor test
	 * 
	 * Expected: 50, 10, 'A'
	 * Actual:  50, 10, 'A'
	 * Result: Pass
	 */
	@Test
	public void constructor_test5()
	{
		MyVector mv = new MyVector(50, 10, 'A');
		
		assertEquals(50, mv.capacityOrig);
		assertEquals(10, mv.extensionOrig);
		assertEquals('A', mv.extensionMeth);
	}
	
	/**
	 * trimToSize test
	 * 
	 * Expected: 2, 2, 2
	 * Actual: 2, 2, 2
	 * Result: Pass
	 */
	@Test
	public void trimToSize_test()
	{
		MyVector mv = new MyVector();
		mv.addElement('1');
		mv.addElement('2');
		mv.trimToSize();
		
		assertEquals(2, mv.elementCount);
		assertEquals(2, mv.size());
		assertEquals(2, mv.capacity());
	}
	
	/**
	 * ensureCapacity test (F)
	 * 
	 * Expected: 200
	 * Actual: 200
	 * Result: Pass
	 */
	@Test
	public void ensureCapacity_test1()
	{
		MyVector mv = new MyVector();
		mv.addElement('1');
		mv.ensureCapacity(200);
		
		assertEquals(200, mv.capacity());
		assertEquals('1', mv.elementAt(0));
	}
	
	/**
	 * ensureCapacity test (D)
	 * 
	 * Expected: 240
	 * Actual: 240
	 * Result: Pass
	 */
	@Test
	public void ensureCapacity_test2()
	{
		MyVector mv = new MyVector(DEFCAPACITY, 'D');
		mv.ensureCapacity(200);
		
		assertEquals(240, mv.capacity());
	}
	
	/**
	 * ensureCapacity test (O)
	 * 
	 * Expected: 200
	 * Actual: 200
	 * Result: Pass
	 */
	@Test
	public void ensureCapacity_test3()
	{
		MyVector mv = new MyVector(DEFCAPACITY, 'O');
		mv.ensureCapacity(200);
		
		assertEquals(200, mv.capacity());
	}
	
	/**
	 * ensureCapacity test (C)
	 * 
	 * Expected: 200
	 * Actual: 200
	 * Result: Pass
	 */
	@Test
	public void ensureCapacity_test4()
	{
		MyVector mv = new MyVector(DEFCAPACITY, 'C');
		mv.ensureCapacity(200);
		
		assertEquals(200, mv.capacity());
	}
	
	/**
	 * isEmpty test
	 * 
	 * Expected: true
	 * Actual: true
	 * Result: Pass
	 */
	@Test
	public void isEmpty_test1()
	{
		MyVector mv = new MyVector();
		
		assertEquals(true, mv.isEmpty());
	}
	
	/**
	 * isEmpty test
	 * 
	 * Expected: false
	 * Actual: false
	 * Result: Pass
	 */
	@Test
	public void isEmpty_test2()
	{
		MyVector mv = new MyVector();
		mv.addElement('1');
		
		assertEquals(false, mv.isEmpty());
	}
	
	/**
	 * addElement test
	 * 
	 * Expected: false, '1', true
	 * Actual: false, '1', true
	 * Result: Pass
	 */
	@Test
	public void addElement_test()
	{
		MyVector mv = new MyVector();
		mv.addElement('1');
		
		assertEquals(false, mv.isEmpty());
		assertEquals('1', mv.elementAt(0));
		assertEquals(true, mv.contains('1'));
	}
	
	/**
	 * insertElementAt test
	 * 
	 * Expected: false, '2', true
	 * Actual: false, '2', true
	 * Result: Pass
	 */
	@Test
	public void insertElement_test()
	{
		MyVector mv = new MyVector();
		mv.addElement('1');
		mv.insertElementAt('2', 0);
		
		assertEquals(false, mv.isEmpty());
		assertEquals('2', mv.elementAt(0));
		assertEquals(true, mv.contains('2'));
	}
	
	/**
	 * setElementAt test
	 * 
	 * Expected: false, '2', true
	 * Actual: false, '2', true
	 * Result: Pass
	 */
	@Test
	public void setElementAt_test()
	{
		MyVector mv = new MyVector();
		mv.addElement('1');
		mv.setElementAt('2', 0);
		
		assertEquals(false, mv.isEmpty());
		assertEquals('2', mv.elementAt(0));
		assertEquals(true, mv.contains('2'));
	}
	
	/**
	 * removeElement test
	 * 
	 * Expected: true, false, 2
	 * Actual: true, false, 2
	 * Result: Pass
	 */
	@Test
	public void removeElement_test1()
	{
		MyVector mv = new MyVector();
		mv.addElement('1');
		mv.addElement('2');
		mv.addElement('3');
		
		assertEquals(true, mv.removeElement('3'));
		assertEquals(false, mv.contains('3'));
		assertEquals(2, mv.elementCount);
	}
	
	/**
	 * removeElement test
	 * 
	 * Expected: false
	 * Actual: false
	 * Result: Pass
	 */
	@Test
	public void removeElement_test2()
	{
		MyVector mv = new MyVector();
		mv.addElement('1');
		mv.addElement('2');
		
		assertEquals(false, mv.removeElement('3'));
	}
	
	/**
	 * removeElementAt test
	 * 
	 * Expected: false, 1
	 * Actual: false, 1
	 * Result: Pass
	 */
	@Test
	public void removeElementAt_test()
	{
		MyVector mv = new MyVector();
		mv.addElement('1');
		mv.addElement('2');
		mv.addElement('3');
		mv.addElement('4');
		mv.addElement('5');
		mv.removeElementAt(1);
		
		assertEquals(false, mv.contains('2'));
		assertEquals(4, mv.elementCount);
		assertEquals('3', mv.elementAt(1));
	}
	
	/**
	 * removeElementAt test
	 * 
	 * Expected: false, 0, true
	 * Actual: false, 0, true
	 * Result: Pass
	 */
	@Test
	public void removeAllElements_test()
	{
		MyVector mv = new MyVector();
		mv.addElement('1');
		mv.addElement('2');
		mv.removeAllElements();
		
		assertEquals(false, mv.contains('2'));
		assertEquals(0, mv.elementCount);
		assertEquals(true, mv.isEmpty());
	}
	
}
