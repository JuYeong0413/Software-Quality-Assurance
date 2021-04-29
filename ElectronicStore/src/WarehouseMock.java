public class WarehouseMock implements Warehouse {
	int inventoryResult; 
	boolean hasInventoryResult; 
	int expectedCalls,actualCalls; 

	public int getInventory(String product) { 
		actualCalls++; 
		return inventoryResult;
	}
	public void setGetInventoryResult(int result) { 
		this.inventoryResult = result; 
		expectedCalls++; 
	}
	public boolean verify(){ 
		return expectedCalls == actualCalls; 
	}
	@Override
	public void add(String product, int i) {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean hasInventory(String product, int i) {
		// TODO Auto-generated method stub
		return hasInventoryResult;
	}
	@Override
	public void remove(String product, int i) {
		// TODO Auto-generated method stub
	}
	public void setHasInventoryResult(boolean b) {
		// TODO Auto-generated method stub
		hasInventoryResult = b;
	}
	public void setRemoveInvoked() {
		// TODO Auto-generated method stub
	}
}
