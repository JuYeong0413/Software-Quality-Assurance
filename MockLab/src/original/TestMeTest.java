package original;

import junit.framework.TestCase;
import java.io.IOException;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
//import original.IConverter;
//import original.ConversionType;
//import original.TestMe;


public class TestMeTest extends TestCase {

	private TestMe testMe;
	private IConverter converter;
	private static double tolerance = 0.0001;

	@Before
	public void setUp()
	{
		 testMe = new TestMe();
		 converter = new Converter();
	//	 testMe.setConverter (converter);
	}
	@Test
	public void testCtoF_Freezing()
	{
		 double input = 0.0;
		 double expected = 32.0;
		 double actual = testMe.convert( input, ConversionType.C_TO_F );
		 assertEquals( expected, actual, tolerance );
	}
	
	public void testMe() throws IOException {
	
		IConverter converter = EasyMock.createMock(IConverter.class);
		EasyMock.expect(converter.convertFtoC((double)(32.0))).andReturn((double)(0.0)); 
		EasyMock.replay(converter);
		converter.convertFtoC((double)32.0);
		EasyMock.verify(converter);
	
	}
}
