// 2016111540 ¿Ã¡÷øµ

import java.io.IOException;

public class Currency {
	private String units;
	private long amount;
	private int cents;
	
	public Currency(double amount, String code) {
		this.units = code;
		setAmount(amount);
	}
	
	private void setAmount(double amount) {
		this.amount = new Double(amount).longValue();
		this.cents = (int)((amount * 100.0) % 100);
	}
	
	public Currency toEuros(ExchangeRate converter) {
		if ("EUR".equals(units)) return this;
		else {
			double input = amount + cents/100.0;
			double rate;
			
			try {
				rate = converter.getRate(units, "EUR");
				double output = input * rate;
				return new Currency(output, "EUR");
			} catch (IOException ex) {
				return null;
			}
		}
	}
	
	public boolean equals(Object o) {
		if (o instanceof Currency) {
			Currency other = (Currency) o;
			return this.units.equals(other.units)
					&& this.amount == other.amount
					&& this.cents == other.cents;
		}
		return false;
	}
	
	public String toString() {
		return amount + "." + Math.abs(cents) + " " + units;
	}
}