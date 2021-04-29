
public interface Warehouse {
	void add(String product, int i); 
	int getInventory(String product); 
	boolean hasInventory(String product, int amount); 
	void remove(String product, int i);
}
