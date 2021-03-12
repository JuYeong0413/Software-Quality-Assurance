package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;

/**
 * @author   Juyeong Lee
 */

public class CoffeeMakerTest {
	
	private CoffeeMaker coffeeMaker;
	private Recipe r1;
	private Recipe r2;
	private Recipe r3;
	private Recipe r4;
	private Recipe r5;


	@Before
	public void setUp() throws Exception {
		coffeeMaker = new CoffeeMaker();
		r1 = new Recipe();
		r2 = new Recipe();
		r3 = new Recipe();
		r4 = new Recipe();
		r5 = new Recipe();
		
		r1.setName("Espresso");
		r1.setPrice("2900");
		r1.setAmtCoffee("1");
		
		r2.setName("Americano");
		r2.setPrice("3200");
		r2.setAmtCoffee("2");
		
		r3.setName("Latte");
		r3.setPrice("3700");
		r3.setAmtCoffee("2");
		r3.setAmtMilk("4");
		
		r4.setName("Cappucino");
		r4.setPrice("3700");
		r4.setAmtCoffee("2");
		r4.setAmtMilk("5");
		
		r5.setName("Mocha");
		r5.setPrice("3900");
		r5.setAmtCoffee("2");
		r5.setAmtMilk("4");
		r5.setAmtChocolate("2");
		
		coffeeMaker.addRecipe(r1);
		coffeeMaker.addRecipe(r2);
		
	}

	@Test
	public void testAddRecipe() {
		// Test adding existing recipe
		assertEquals(false, coffeeMaker.addRecipe(r1));
		assertEquals(false, coffeeMaker.addRecipe(r2));
		
		// Test adding new recipe
		assertEquals(true, coffeeMaker.addRecipe(r3));
		assertEquals(true, coffeeMaker.addRecipe(r4));
	}
	
	@Test
	public void testDeleteRecipe() {
		// Test delete existing recipe
		assertEquals("Espresso", coffeeMaker.deleteRecipe(0));
		assertEquals("Americano", coffeeMaker.deleteRecipe(1));
		
		// Test delete non-existing recipe
		assertEquals(null, coffeeMaker.deleteRecipe(0));
		assertEquals(null, coffeeMaker.deleteRecipe(1));
	}
	
	@Test
	public void testEditRecipe() {
		// Test editing existing recipe
		assertEquals("Espresso", coffeeMaker.editRecipe(0, r5));
		assertEquals("Americano", coffeeMaker.editRecipe(1, r5));
		assertEquals("Mocha", coffeeMaker.editRecipe(1, r2));
		
		// Test editing non-existing recipe
		assertEquals(null, coffeeMaker.editRecipe(2, r5));
	}

	@Test
	public void testAddInventory() throws InventoryException {
		// Test inventory is correctly added
		coffeeMaker.addInventory("10", "10", "10", "10");
		
		String inventoryStatus = "Coffee: 25\nMilk: 25\nSugar: 25\nChocolate: 25\n";
		assertEquals(inventoryStatus, coffeeMaker.checkInventory());
		
		// Test exception is correctly thrown
		Exception exception = assertThrows(InventoryException.class, () -> {
			coffeeMaker.addInventory("10", "-10", "10", "10");
		});
		
		String expectedMessage = "Units of milk must be a positive integer";
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testMakeCoffee() {
		// Test purchasing beverage successfully with correct change
		assertEquals(1800, coffeeMaker.makeCoffee(1, 5000));
		
		// Test less money paid than beverage price
		assertEquals(3000, coffeeMaker.makeCoffee(1, 3000));
		
		// Test purchasing non-existing beverage
		assertEquals(10000, coffeeMaker.makeCoffee(3, 10000));
	}

}
