
import java.util.GregorianCalendar;

public class Person {
	private String firstName;
	private String lastName;
	private GregorianCalendar birthDate;
	
	public Person(String firstName, String lastName, GregorianCalendar gregorianCalendar) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = gregorianCalendar;
		if (!gregorianCalendar.after(GregorianCalendar.getInstance())){
			new Exception("Birthyear cannot be in the future!");
		}
	}
	
	public int age() {
		GregorianCalendar today = new GregorianCalendar();
		int age =  today.get(GregorianCalendar.YEAR) - this.birthDate.get(GregorianCalendar.YEAR);
		if (today.get(GregorianCalendar.DATE) < birthDate.get(GregorianCalendar.DATE)){
			age--;
		}
		return age;
	}
	
	public String getFullName(){
		firstName.toLowerCase();
		Character.toUpperCase(firstName.charAt(0));
		lastName.toLowerCase();
		Character.toUpperCase(lastName.charAt(0));
		return firstName + " " + lastName;
		
	}
	@Override
	public String toString(){
		return getFullName();
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public GregorianCalendar getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(GregorianCalendar birthDate) {
		this.birthDate = birthDate;
	}

	
}
