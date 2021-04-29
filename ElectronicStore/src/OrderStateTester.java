import junit.framework.TestCase;


public class OrderStateTester extends TestCase { 
	private static String DIET_COKE = "Diet Coke"; 
	private static String SPRITE = "Sprite"; 
	Warehouse warehouse;


	protected void setUp() throws Exception { 
		//Fixture with secondary object(s)
		warehouse = new WarehouseImpl(); 
		warehouse.add(DIET_COKE,5); 
		warehouse.add(SPRITE,10); 
	}
	
	public void testOrderIsFilledIfEnoughInWarehouse(){ 
		Order order = new Order(DIET_COKE,5); 
		order.fill(warehouse); 
		// Primary object test 
		assertTrue(order.filled()); 
		// Secondary object test(s) 
		assertEquals(0,warehouse.getInventory(DIET_COKE)); 
	} 
	
	public void testOrderDoesNotRemoveIfNotEnough(){ 
		Order order = new Order(SPRITE,11); 
		order.fill(warehouse); 
		// Primary object test 
		assertFalse(order.filled()); 
		// Secondary object test(s) 
		assertEquals(10, warehouse.getInventory(SPRITE)); 
	}
	

}
