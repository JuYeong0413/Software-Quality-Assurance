import static org.easymock.EasyMock.*;
//import mocks.WarehouseMock;
import junit.framework.TestCase;

public class OrderInteractionMockTester extends TestCase { 
	private static String DIET_COKE = "Diet Coke";
	private static String SPRITE = "Sprite";
	WarehouseMock warehouse;
	
	protected void setUp() throws Exception { 
		//Fixture with secondary object(s) 
		warehouse = new WarehouseMock();
	}
	

	public void testOrderIsFilledIfEnoughInWarehouse(){ 
		//Expectations 
		warehouse.setHasInventoryResult(true); 
		warehouse.setRemoveInvoked();

		Order order = new Order(DIET_COKE,5); 
		order.fill(warehouse); 
		// Primary object test 
		assertTrue(order.filled()); 
		// Secondary object test(s) 
		assertTrue(warehouse.verify());
	} 
	public void testOrderDoesNotRemoveIfNotEnough(){ 
		//Expectations 
		warehouse.setHasInventoryResult(false);

		Order order = new Order(SPRITE,11); 
		order.fill(warehouse); 
		// Primary object test 
		assertFalse(order.filled()); 
		// Secondary object test(s) 
		assertTrue(warehouse.verify());
	}

}
