import java.util.GregorianCalendar;

public class Doctor extends Person{
	private Specialty specialty;
	private ExaminationRoom favoriteRoom;
	private boolean checkedIn;
	

	public Doctor(String firstName, String lastName, GregorianCalendar birthDate, Specialty specialty, ExaminationRoom favoriteRoom,
			boolean checkedIn) {
		super(firstName, lastName, birthDate);
		this.specialty = specialty;
		this.favoriteRoom = favoriteRoom;
		this.checkedIn = checkedIn;
	}
	
	public Doctor(String firstName, String lastName, GregorianCalendar gregorianCalendar, Specialty speciality, ExaminationRoom favoriteRoom) {
		super(firstName, lastName, gregorianCalendar);
		this.specialty = speciality;
		this.favoriteRoom = favoriteRoom;
		this.checkedIn = false;
	}
	
	public Specialty getSpecialty() {
		return specialty;
	}
	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}
	public ExaminationRoom getFavoriteRoom() {
		return favoriteRoom;
	}
	public void setFavoriteRoom(ExaminationRoom favoriteRoom) {
		this.favoriteRoom = favoriteRoom;
	}
	public boolean isCheckedIn() {
		return checkedIn;
	}
	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}

	
}
