package original;

public class TestMe
{
	private Converter converter;

	public TestMe()
	{
		converter = new Converter();
	}
	
	public Double convert(Double temperature, ConversionType direction)
	{
		Double result = null;
		
		if (converter != null)
		{
			switch (direction)
			{
				case C_TO_F:
				{
					result = converter.convertCtoF(temperature);
					break;
				}
				case F_TO_C:
				{
					result = converter.convertFtoC(temperature);
					break;
				}
			}
		}
		
		return result;
	}
}
