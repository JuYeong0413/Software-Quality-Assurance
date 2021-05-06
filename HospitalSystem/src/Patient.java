
import java.util.GregorianCalendar;
import java.util.Random;

public class Patient extends Person{
	private Specialty specialtyNeeded;
	private boolean checkedIn;

	public Patient(String firstName, String lastName, GregorianCalendar gregorianCalendar, Specialty specialtyNeeded) {
		super(firstName, lastName, gregorianCalendar);
		this.specialtyNeeded = specialtyNeeded;
		this.checkedIn = false;
	}
	public int generatePriority(){
		Random random = new Random();
		int n = 5;
		return (int)(random.nextDouble()*n);
	}
	public Specialty getSpecialtyNeeded() {
		return specialtyNeeded;
	}
	public void setSpecialtyNeeded(Specialty specialtyNeeded) {
		this.specialtyNeeded = specialtyNeeded;
	}
	public boolean isCheckedIn() {
		return checkedIn;
	}
	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}
	
}
