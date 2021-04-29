import static org.easymock.EasyMock.*;

//import mocks.WarehouseMock;

import junit.framework.TestCase;

public class OrderInteractionMockTester2 extends TestCase {
	private static String DIET_COKE = "Diet Coke";
	//private static String SPRITE = "Sprite";
	private static Warehouse mock;
	protected void setUp() throws Exception { 
		//Fixture with secondary object(s) 
		mock = createMock(Warehouse.class);
	}
	
	public void testOrderIsFilledIfEnoughInWarehouse(){ 
		//Expectations 
		expect(mock.hasInventory(DIET_COKE,5)).andReturn(true); 
		mock.remove(DIET_COKE,5); 
		replay(mock);

		Order order = new Order(DIET_COKE,5); 
		order.fill(mock); 
		// Primary object test 
		assertFalse(order.filled()); 
		// Secondary object test(s) 
		verify(mock);
	} 
	public void testDemo(){ 
		mock.remove("cola",2); 
		replay(mock);
		verify(mock);
	}

}
