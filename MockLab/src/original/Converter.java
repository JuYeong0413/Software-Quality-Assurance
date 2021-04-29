package original;

public class Converter implements IConverter
{
	public Converter()
	{
		
	}

	public double convertCtoF(double temperature)
	{
		return (double)(temperature * 9.0 / 5.0 + 32.0);
	}

	public double convertFtoC(double temperature)
	{
		return (double)((temperature - 32.0) * 5.0 / 9.0);
	}
}