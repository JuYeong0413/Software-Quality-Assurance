import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

/* Contains methods for checking people in and out */
public class Hospital {

private ArrayList<Doctor> doctors;
private ArrayList<Patient> patients;

public Hospital(ArrayList<Doctor> doctors, ArrayList<Patient> patients) {
	super();
	this.doctors = doctors;
	this.patients = patients;
}

private static ArrayList<ExaminationRoom> examinationRooms;	
	public void initializeHospital() throws Exception{
		// Initialize variables
		
		examinationRooms = new ArrayList<ExaminationRoom>();
		doctors = new ArrayList<Doctor>();
		patients = new ArrayList<Patient>();
		
		// Add some examination rooms
		ExaminationRoom room100 = new ExaminationRoom(100);
		ExaminationRoom room101 = new ExaminationRoom(101);
		ExaminationRoom room102 = new ExaminationRoom(102);
		ExaminationRoom room103 = new ExaminationRoom(103);
		ExaminationRoom room104 = new ExaminationRoom(104);
		examinationRooms.add(room100);
		examinationRooms.add(room101);
		examinationRooms.add(room102);
		examinationRooms.add(room103);
		examinationRooms.add(room104);
		
		// Add some doctors
		doctors.add(new Doctor("Albus", "Dumbeldore", new GregorianCalendar(1860, 3, 12), Specialty.PED, room100));
		doctors.add(new Doctor("Severus", "Snape", new GregorianCalendar(1967, 6, 2), Specialty.GEN, room100));
		doctors.add(new Doctor("Harry", "Potter", new GregorianCalendar(1986, 11, 21), Specialty.CAR, room101));
		doctors.add(new Doctor("Hermione", "Granger", new GregorianCalendar(1988, 12, 10), Specialty.PSY, room102));
		
	
		// Check in doctors
		for (int i = 0; i < doctors.size(); i++) {
			try {
				checkInDoctor(doctors.get(i));
			} catch (Exception e) {
				System.out.print(e.getMessage() + " - ");
				System.out.println("Couldn't check in doctor " + doctors.get(i).getFirstName() + " " + doctors.get(i).getLastName());
			}
		}
		
		// Add some patients
		checkInPatient(new Patient("Mary", "Jane", new GregorianCalendar(1860, 3, 12), Specialty.CAR));
		checkInPatient(new Patient("Sirius", "Black", new GregorianCalendar(1960, 3, 1), Specialty.PSY));
		checkInPatient(new Patient("Tom Marvolo", "Riddle", new GregorianCalendar(1978, 1, 12), Specialty.ENT));
		checkInPatient(new Patient("Draco", "Malfoy", new GregorianCalendar(1860, 3, 12), Specialty.ENT));
		updateWaitingLists();
	}
	
	public void addExaminationRoom(ExaminationRoom room) throws Exception{
		String thisNumber = Integer.toString(room.getNumber());
		for (int i = 0; i < examinationRooms.size(); i++) {
			String roomNumber = Integer.toString(examinationRooms.get(i).getNumber());
			if (thisNumber == roomNumber){
				throw new Exception("Room number already in use!");
			}
		}
		examinationRooms.add(room);
	}

	public void checkInDoctor(Doctor doctor) throws Exception{
		
		if (doctor.isCheckedIn()){
			throw new Exception("Already checked in");
		}
		if (!doctors.contains(doctor)){
			doctors.add(doctor);
		}

		ExaminationRoom examinationRoom = null;
		
		if (doctor.getFavoriteRoom().isRunning()){
			boolean foundRoom = false;
			for (int i = 0; i < examinationRooms.size(); i++) {
				if (!examinationRooms.get(i).isRunning()){
					examinationRoom = examinationRooms.get(i);
					foundRoom = true;
					break;
				}
			}
			if (!foundRoom){
				throw new Exception("No rooms available!");
			}
		}
		else if (!doctor.getFavoriteRoom().isRunning()) {
			examinationRoom = doctor.getFavoriteRoom();
		}
		
		// Occupy the examination room
		examinationRoom.setRunning(true);
		examinationRoom.setOccupyingDoctor(doctor);
		examinationRoom.setWaitingPatients(new ArrayList<Patient>());
		
		// Update the doctor's variables
		doctor.setCheckedIn(true);		
		
	}
	
	public void checkOutDoctor(Doctor doctor) throws Exception{
		ArrayList<ExaminationRoom> runningRooms = getRunningRooms();
		if(!doctor.isCheckedIn()){
			throw new Exception("Doctor is not yet checked in!");
		}
		
		// Free examination room and send patients to other rooms
		for (int i = 0; i < runningRooms.size(); i++) {
			ExaminationRoom room = runningRooms.get(i);
			if (room.getOccupyingDoctor() == doctor){	
				if (room.isOccupied() && room.getOccupyingPatient().isCheckedIn()){
					checkOutPatient(room.getOccupyingPatient());
				}
				
				// Find room with shortest waiting list				
				ExaminationRoom minRoom = Collections.min(runningRooms);
			
				// Add people to this list
				minRoom.getWaitingPatients().addAll(room.getWaitingPatients());		
				updateWaitingLists();
				room.resetExaminationRoom();
				break;
			}
		}
		doctor.setCheckedIn(false);
		doctors.remove(doctor);
		
	}

	public void checkInPatient(Patient patient) throws Exception{
		ArrayList<ExaminationRoom> runningRooms = getRunningRooms();
		if (patient.isCheckedIn()){
			throw new Exception("Patient already checked in!");
		}
		else if (!patients.contains(patient)){
			patients.add(patient);
		}
		
		// Find appropriate doctor

		// Lists for rooms with needed specialists and general practitioners
		ArrayList<ExaminationRoom> specialistRooms = new ArrayList<ExaminationRoom>();
		ArrayList<ExaminationRoom> generalRooms = new ArrayList<ExaminationRoom>();
		ArrayList<ExaminationRoom> pediatricianRooms = new ArrayList<ExaminationRoom>();
		
		ExaminationRoom minRoom;
		for (int i = 0; i < runningRooms.size(); i++) {
			ExaminationRoom room = runningRooms.get(i);
			if (patient.getSpecialtyNeeded() == room.getOccupyingDoctor().getSpecialty()){
				specialistRooms.add(room);
			}
			else if (room.getOccupyingDoctor().getSpecialty() == Specialty.GEN){
				generalRooms.add(room);
			}
			else if (room.getOccupyingDoctor().getSpecialty() == Specialty.PED)
				pediatricianRooms.add(room);
		}
		// First, if the patient is younger than 16, he/she is sent to a pediatrician
		if (pediatricianRooms.size() > 0 && patient.age() <= 16){
			minRoom = Collections.min(pediatricianRooms);
		}
		// If specialist was found, add patient to the one with the shortest queue
		else if (specialistRooms.size() > 0){
			minRoom = Collections.min(specialistRooms);
		}
		// Else if a general practitioner was found, add patient to the one with the shortest queue
		else if (generalRooms.size() > 0){
			minRoom = Collections.min(generalRooms);	
		}
		// Else add patient to a random doctor with the shortest queue
		else {
			minRoom = Collections.min(runningRooms);
		}
		minRoom.addWaitingPatient(patient);
		patient.setCheckedIn(true);
		
	}
	
	public void checkOutPatient(Patient patient) throws Exception {
		ArrayList<ExaminationRoom> runningRooms = getRunningRooms();
		if (!patient.isCheckedIn()){
			throw new Exception("Patient is not yet checked in!");
		}
		
		// Remove patient from examination room or waiting list
		for (int i = 0; i < runningRooms.size(); i++) {
			ExaminationRoom room = runningRooms.get(i);
			if (room.getOccupyingPatient() == patient){
				room.setOccupyingPatient(null);
				room.setOccupied(false);
			}
			else if (room.getWaitingPatients().contains(patient)){
				room.getWaitingPatients().remove(patient);
			}
		}
		
		patient.setCheckedIn(false);
		patients.remove(patient);
		updateWaitingLists();
		
	}
	
	public void updateWaitingLists(){
		ArrayList<ExaminationRoom> runningRooms = getRunningRooms();
		for (int i = 0; i < runningRooms.size(); i++) {
			ExaminationRoom room = runningRooms.get(i);
			// If room is free, try to let a patient enter
			if (room.isRunning() && !room.isOccupied() && room.getWaitingPatients().size() > 0){
				room.setOccupyingPatient(room.getWaitingPatients().remove(0));
				room.setOccupied(true);
			}
		}
	}

	public ArrayList<ExaminationRoom> getRunningRooms(){
		ArrayList<ExaminationRoom> runningRooms = new ArrayList<ExaminationRoom>();
		for (int i = 0; i < examinationRooms.size(); i++) {
			if (examinationRooms.get(i).isRunning()){
				runningRooms.add(examinationRooms.get(i));
			}
		}
		return runningRooms;
	}
	
	public ArrayList<ExaminationRoom> getExaminationRooms() {
		return examinationRooms;
	}

	public void setExaminationRooms(ArrayList<ExaminationRoom> examinationRooms) {
		Hospital.examinationRooms = examinationRooms;
	}

	public ArrayList<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(ArrayList<Doctor> doctors) {
		this.doctors = doctors;
	}

	public ArrayList<Patient> getPatients() {
		return patients;
	}

	public void setPatients(ArrayList<Patient> patients) {
		this.patients = patients;
	}
	
}
