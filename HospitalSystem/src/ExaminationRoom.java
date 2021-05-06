import java.util.ArrayList;

public class ExaminationRoom implements Comparable<ExaminationRoom>{
	private int number;
	private boolean occupied; // Is occupied by a patient
	private boolean running; // Is occupied by a doctor
	private Doctor occupyingDoctor;
	private Patient occupyingPatient;
	private ArrayList<Patient> waitingPatients;
	
	
		// Compares sizes of waiting lists
		@Override
		public int compareTo(ExaminationRoom o) {
			if (this.getWaitingPatients().size() > o.getWaitingPatients().size()){
				return 1;
			}
			else if (this.getWaitingPatients().size() < o.getWaitingPatients().size()){
				return -1;
			}
			return 0;
		}
		
		public boolean equals(ExaminationRoom room){
			if (this.getWaitingPatients().size() == room.getWaitingPatients().size()){
				return true;
			}
			else {
				return false;
			}
		}
		
		public void resetExaminationRoom(){
			this.setOccupied(false);
			this.setOccupyingDoctor(null);
			this.setOccupyingPatient(null);
			this.setRunning(false);
			this.setWaitingPatients(new ArrayList<Patient>());
		}
	
	@Override
	public String toString(){
		return Integer.toString(number);
	}
		
	public ExaminationRoom(int number, boolean occupied, boolean running, Doctor occupyingDoctor, Patient occupyingPatient,
			ArrayList<Patient> waitingPatients) {
		super();
		this.number = number;
		this.occupied = occupied;
		this.running = running;
		this.occupyingDoctor = occupyingDoctor;
		this.occupyingPatient = occupyingPatient;
		this.waitingPatients = waitingPatients;
	}
	
	public ExaminationRoom(int number) {
		super();
		this.number = number;
		this.occupied = false;
		this.running = false;
		this.occupyingDoctor = null;
		this.occupyingPatient = null;
		this.waitingPatients = new ArrayList<Patient>();
	}
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public boolean isOccupied() {
		return occupied;
	}
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	public Doctor getOccupyingDoctor() {
		return occupyingDoctor;
	}
	public void setOccupyingDoctor(Doctor occupyingDoctor) {
		this.occupyingDoctor = occupyingDoctor;
	}
	public Patient getOccupyingPatient() {
		return occupyingPatient;
	}
	public void setOccupyingPatient(Patient occupyingPatient) {
		this.occupyingPatient = occupyingPatient;
	}
	public ArrayList<Patient> getWaitingPatients() {
		return waitingPatients;
	}
	public void setWaitingPatients(ArrayList<Patient> waitingPatients) {
		this.waitingPatients = waitingPatients;
	}

	public void addWaitingPatient(Patient patient){
		waitingPatients.add(patient);
	}
	
}
