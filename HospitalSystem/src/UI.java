import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UI extends Application{

	public static void main(String[] args) throws Exception {
		
		launch(args);

	}


	public void createCheckOutPatientTab(Tab checkOutPatientTab, Hospital hospital){
		GridPane checkOutPatientGrid = new GridPane();
		checkOutPatientGrid.setAlignment(Pos.CENTER);
		checkOutPatientGrid.setHgap(10);
		checkOutPatientGrid.setVgap(10);
		checkOutPatientGrid.setPadding(new Insets(25, 25, 25, 25));
		
		// Label for the picker
		Label patientPickerLabel = new Label("Select patient: ");
		checkOutPatientGrid.add(patientPickerLabel, 0, 0);
	
		// The picker itself
		ComboBox<Patient> patientPicker= new ComboBox<>();
		patientPicker.getItems().addAll(hospital.getPatients());
		checkOutPatientGrid.add(patientPicker, 1, 0);
		
		// Confirm button
		Button confirmButton = new Button("Confirm");
		confirmButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				try {
					hospital.checkOutPatient(patientPicker.getValue());
					createCheckOutPatientTab(checkOutPatientTab, hospital);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		});
		checkOutPatientGrid.add(confirmButton, 1, 2);
		
		checkOutPatientTab.setContent(checkOutPatientGrid);
		
	}
	
	public void createAddPatientTab(Tab addPatientTab, Hospital hospital){
		// Add patient Tab

		GridPane addPatientGrid = new GridPane();
		addPatientGrid.setAlignment(Pos.CENTER);
        addPatientGrid.setHgap(10);
        addPatientGrid.setVgap(10);
        addPatientGrid.setPadding(new Insets(25, 25, 25, 25));
        
        
		Label patientFirstName = new Label("First name");
		addPatientGrid.add(patientFirstName, 0, 0);
		Label patientLastName = new Label("Last name");
		addPatientGrid.add(patientLastName, 0, 1);
		Label patientBirthdate = new Label("Brithdate");
		addPatientGrid.add(patientBirthdate, 0, 2);
		Label patientSpecialtyNeeded = new Label("Specialty needed");
		addPatientGrid.add(patientSpecialtyNeeded, 0, 3);
		
		
		TextField patientFirstNameTextField = new TextField();
		addPatientGrid.add(patientFirstNameTextField, 1, 0);
		TextField patientLastNameTextField = new TextField();
		addPatientGrid.add(patientLastNameTextField, 1, 1);	
		DatePicker patientBrithdatePicker = new DatePicker();
		addPatientGrid.add(patientBrithdatePicker, 1, 2);
		ComboBox<Specialty> patientSpecialtyNeededPicker = new ComboBox<>();
		patientSpecialtyNeededPicker.getItems().setAll(FXCollections.observableArrayList(Specialty.values()));
		addPatientGrid.add(patientSpecialtyNeededPicker, 1, 3);
		
		
		Button enterButton = new Button("Enter");
		addPatientGrid.add(enterButton, 1, 4);
		
		enterButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				try {
					
					hospital.checkInPatient(new Patient(patientFirstNameTextField.getText(), patientLastNameTextField.getText(),
							GregorianCalendar.from(patientBrithdatePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault())), 
							patientSpecialtyNeededPicker.getValue()));
					hospital.updateWaitingLists();
					
					Stage dialog = new Stage();
					dialog.initStyle(StageStyle.UTILITY);
					Text text = new Text(25, 25, "Patient succesfully added!");
					text.setTextAlignment(TextAlignment.CENTER);
					Scene scene = new Scene(new Group(text), 200, 75);
					dialog.setScene(scene);
					dialog.show();
					
		
				} catch (Exception e) {
					e.printStackTrace();
					Stage dialog = new Stage();
					dialog.initStyle(StageStyle.UTILITY);
					Text text = new Text(25, 25, "Something went wrong,\nplease try again!");
					text.setTextAlignment(TextAlignment.CENTER);
					Scene scene = new Scene(new Group(text), 200, 75);
					dialog.setScene(scene);
					dialog.show();
				}

			}
			
		});
		
		addPatientTab.setContent(addPatientGrid);
		
	}
	
	
	public void createHospitalTab(Tab hospitalTab, Hospital hospital){
		BorderPane hospitalGrid = new BorderPane();
		
		
		
		TabPane runningRoomsTabPane = new TabPane();
		
		for (int i = 0; i < hospital.getRunningRooms().size(); i++) {
			ExaminationRoom room = hospital.getRunningRooms().get(i);
			Tab roomTab = new Tab();
			roomTab.setText(Integer.toString(room.getNumber()));
			runningRoomsTabPane.getTabs().add(roomTab);
			
		}
		
		hospitalGrid.setCenter(runningRoomsTabPane);
		for (int i = 0; i < runningRoomsTabPane.getTabs().size(); i++) {
			
			ExaminationRoom room = hospital.getRunningRooms().get(i);
			
			GridPane roomGrid = new GridPane();
			roomGrid.setAlignment(Pos.CENTER);
			roomGrid.setHgap(10);
			roomGrid.setVgap(10);
			roomGrid.setPadding(new Insets(25, 25, 25, 25));
			
			Tab roomTab = runningRoomsTabPane.getTabs().get(i);
			roomTab.setText(Integer.toString(room.getNumber()));
			roomTab.setContent(roomGrid);
			
			
			Label roomDoctorLabel = new Label("Doctor: " + room.getOccupyingDoctor().getFullName());
			roomGrid.add(roomDoctorLabel, 0, 0);
			
			Label roomSpecialtyLabel = new Label("Specialty: " + room.getOccupyingDoctor().getSpecialty());
			roomGrid.add(roomSpecialtyLabel, 0, 1);
			
			Label roomPatientLabel;
			if (room.isOccupied()){
				roomPatientLabel = new Label("Patient: " + room.getOccupyingPatient().getFullName());
			}
			else {
				roomPatientLabel = new Label("Room not occupied");
			}
			roomGrid.add(roomPatientLabel, 0, 2);
			
			Label waitingPatientsLabel = new Label("Waiting patients: " + room.getWaitingPatients().size());
			roomGrid.add(waitingPatientsLabel, 0, 3);
		
		}
		hospitalTab.setContent(hospitalGrid);
	}
	
	public void createAddDoctorTab(Tab addDoctorTab, Hospital hospital){
		// Add doctor Tab

				GridPane addDoctorGrid = new GridPane();
				addDoctorGrid.setAlignment(Pos.CENTER);
		        addDoctorGrid.setHgap(10);
		        addDoctorGrid.setVgap(10);
		        addDoctorGrid.setPadding(new Insets(25, 25, 25, 25));
		        
		        
				Label doctorFirstName = new Label("First name");
				addDoctorGrid.add(doctorFirstName, 0, 0);
				Label doctorLastName = new Label("Last name");
				addDoctorGrid.add(doctorLastName, 0, 1);
				Label doctorBirthdate = new Label("Brithdate");
				addDoctorGrid.add(doctorBirthdate, 0, 2);
				Label doctorSpecialty = new Label("Specialty");
				addDoctorGrid.add(doctorSpecialty, 0, 3);
				Label doctorFavoriteRoom = new Label("Favorite room");
				addDoctorGrid.add(doctorFavoriteRoom, 0, 4);
				
				
				TextField doctorFirstNameTextField = new TextField();
				addDoctorGrid.add(doctorFirstNameTextField, 1, 0);
				TextField doctorLastNameTextField = new TextField();
				addDoctorGrid.add(doctorLastNameTextField, 1, 1);	
				DatePicker doctorBirthdatePicker = new DatePicker();
				addDoctorGrid.add(doctorBirthdatePicker, 1, 2);
				ComboBox<Specialty> doctorSpecialtyPicker = new ComboBox<>();
				doctorSpecialtyPicker.getItems().setAll(FXCollections.observableArrayList(Specialty.values()));
				addDoctorGrid.add(doctorSpecialtyPicker, 1, 3);
				ComboBox<ExaminationRoom> doctorFavoriteRoomPicker = new ComboBox<>();
				doctorFavoriteRoomPicker.getItems().addAll(hospital.getExaminationRooms());
				addDoctorGrid.add(doctorFavoriteRoomPicker, 1, 4);
				
				Button enterButton = new Button("Enter");
				addDoctorGrid.add(enterButton, 1, 5);
				
				enterButton.setOnAction(new EventHandler<ActionEvent>(){

					@Override
					public void handle(ActionEvent event) {
						try {
							hospital.checkInDoctor(new Doctor(doctorFirstNameTextField.getText(), doctorLastNameTextField.getText(),
									GregorianCalendar.from(doctorBirthdatePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault())), 
									doctorSpecialtyPicker.getValue(), doctorFavoriteRoomPicker.getValue()));
							hospital.updateWaitingLists();
							
							Stage dialog = new Stage();
							dialog.initStyle(StageStyle.UTILITY);
							Text text = new Text(25, 25, "Doctor succesfully added!");
							text.setTextAlignment(TextAlignment.CENTER);
							Scene scene = new Scene(new Group(text), 200, 75);
							dialog.setScene(scene);
							dialog.show();
				
						} catch (Exception e) {
							Stage dialog = new Stage();
							dialog.initStyle(StageStyle.UTILITY);
							Text text = new Text(25, 25, "Something went wrong,\nplease try again!\n" + e.getMessage());
							text.setTextAlignment(TextAlignment.CENTER);
							Scene scene = new Scene(new Group(text), 200, 75);
							dialog.setScene(scene);
							dialog.show();
						}

					}
					
				});
				
				addDoctorTab.setContent(addDoctorGrid);
				
	}

	public void createCheckOutDoctorTab(Tab checkOutDoctorTab, Hospital hospital) {
		GridPane checkOutDoctorGrid = new GridPane();
		checkOutDoctorGrid.setAlignment(Pos.CENTER);
		checkOutDoctorGrid.setHgap(10);
		checkOutDoctorGrid.setVgap(10);
		checkOutDoctorGrid.setPadding(new Insets(25, 25, 25, 25));
		
		// Label for the picker
		Label doctorPickerLabel = new Label("Select doctor: ");
		checkOutDoctorGrid.add(doctorPickerLabel, 0, 0);
	
		// The picker itself
		ComboBox<Doctor> doctorPicker= new ComboBox<>();
		doctorPicker.getItems().addAll(hospital.getDoctors());
		checkOutDoctorGrid.add(doctorPicker, 1, 0);
		
		// Confirm button
		Button confirmButton = new Button("Confirm");
		confirmButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				try {
					hospital.checkOutDoctor(doctorPicker.getValue());
					createCheckOutDoctorTab(checkOutDoctorTab, hospital);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
			
		});
		checkOutDoctorGrid.add(confirmButton, 1, 2);
		
		checkOutDoctorTab.setContent(checkOutDoctorGrid);
		
	}
	
	public void createOverviewTab(Tab overviewTab, Hospital hospital){
		GridPane overviewGrid = new GridPane();
		overviewGrid.setAlignment(Pos.CENTER);
		overviewGrid.setHgap(10);
		overviewGrid.setVgap(10);
		overviewGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Label patientsLabel = new Label("Patients: ");
		overviewGrid.add(patientsLabel, 0, 0);
		String patients = new String();
		for (int i = 0; i < hospital.getPatients().size(); i++) {
			patients += Integer.toString(i+1) + ". " +  hospital.getPatients().get(i) + "\n";
		}
		Label patientsInfoLabel = new Label(patients);
		overviewGrid.add(patientsInfoLabel, 1, 1);
		
		Label doctorsLabel = new Label("Doctors: ");
		overviewGrid.add(doctorsLabel, 2, 0);
		String doctors = new String();
		for (int i = 0; i < hospital.getDoctors().size(); i++) {
			doctors += Integer.toString(i+1) + ". " + hospital.getDoctors().get(i) + "\n";
		}
		Label doctorsInfoLabel = new Label(doctors);
		overviewGrid.add(doctorsInfoLabel, 3, 1);
		
		overviewTab.setContent(overviewGrid);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Hospital hospital = new Hospital(new ArrayList<Doctor>(), new ArrayList<Patient>());
		hospital.initializeHospital();
		
		
		TabPane tabPane = new TabPane();
		// Overview tab
		
		Tab overviewTab = new Tab();
		overviewTab.setText("Overview");
		createOverviewTab(overviewTab, hospital);
		tabPane.getTabs().add(overviewTab);
		
		// Hospital running rooms tab
		
		Tab hospitalTab = new Tab();
		hospitalTab.setText("Running rooms");
		createHospitalTab(hospitalTab, hospital);
		tabPane.getTabs().add(hospitalTab);
		
		// Add Doctor Tab
		
		Tab addDoctorTab = new Tab();
		addDoctorTab.setText("Add Doctor");
		createAddDoctorTab(addDoctorTab, hospital);
		tabPane.getTabs().add(addDoctorTab);
		
		// Add Patient Tab
		
		Tab addPatientTab = new Tab();
		addPatientTab.setText("Add Patient");
		createAddPatientTab(addPatientTab, hospital);
		tabPane.getTabs().add(addPatientTab);
		
		// Check out doctor Tab
		
		Tab checkOutDoctorTab = new Tab();
		checkOutDoctorTab.setText("Check out doctor");
		createCheckOutDoctorTab(checkOutDoctorTab, hospital);
		tabPane.getTabs().add(checkOutDoctorTab);

		// Check out patient Tab
		
		Tab checkOutPatientTab = new Tab();
		checkOutPatientTab.setText("Check out patient");
		createCheckOutPatientTab(checkOutPatientTab, hospital);
		tabPane.getTabs().add(checkOutPatientTab);
		

		
	
		

		// If tab is changed, update values
		
		tabPane.getSelectionModel().selectedItemProperty().addListener(
			    new ChangeListener<Tab>() {
			        @Override
			        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
			        	if (tabPane.getSelectionModel().getSelectedItem() == hospitalTab){
			        		createHospitalTab(hospitalTab, hospital);
			        	}
			        	else if(tabPane.getSelectionModel().getSelectedItem() == checkOutPatientTab){
			        		createCheckOutPatientTab(checkOutPatientTab, hospital);
			        	}
			        	else if (tabPane.getSelectionModel().getSelectedItem() == checkOutDoctorTab){
			        		createCheckOutDoctorTab(checkOutDoctorTab, hospital);
			        	}
			        	else if(tabPane.getSelectionModel().getSelectedItem() == addPatientTab){
			        		createAddPatientTab(addPatientTab, hospital);
			        	}
			        	else if (tabPane.getSelectionModel().getSelectedItem() == addDoctorTab){
			        		createAddDoctorTab(addDoctorTab, hospital);
			        	}
			        	else if (tabPane.getSelectionModel().getSelectedItem() == overviewTab){
			        		createOverviewTab(overviewTab, hospital);
			        	}
			            
			        }
			    }
			);
		
		
		
		Group root = new Group();
		root.getChildren().add(tabPane);
		Scene scene = new Scene(root, 550, 275);
        primaryStage.setScene(scene);
		primaryStage.show();		
	}




}
