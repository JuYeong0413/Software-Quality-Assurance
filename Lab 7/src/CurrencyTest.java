// 2016111540 ¿Ã¡÷øµ

import org.easymock.EasyMock;
import junit.framework.TestCase;
import java.io.IOException;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class CurrencyTest extends TestCase {
	
	private Currency koreaCurrency;
	private Currency europeCurrency;
	private ExchangeRate mock;
	
	@Before
	public void setUp() {
		koreaCurrency = new Currency(4500, "KRW");
		europeCurrency = new Currency(3.35, "EUR");
		mock = EasyMock.createMock(ExchangeRate.class);
	}

	@Test
    public void testToEuros() throws IOException {
        EasyMock.expect(mock.getRate("KRW", "EUR")).andReturn(0.00074447);
        EasyMock.replay(mock);
        Currency actual = koreaCurrency.toEuros(mock);
        assertEquals(europeCurrency, actual);
    }
	
	@After
	public void tearDown() {
		EasyMock.verify(mock);
	}
}
