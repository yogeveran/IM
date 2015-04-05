/**
 * @author eranyogev
 *
 */
public class State  {

	/**
	 * 
	 */
	int hour;
	Disease patient_status_at_doctor; 
	int patient_time_left_at_hospital;
	boolean did_survive;
	
	public State(boolean did_survive, int patient_time_left_at_hospital,
			Disease patient_status_at_doctor, int hour) {
		this.hour = hour;
		this.patient_status_at_doctor = patient_status_at_doctor;
		this.patient_time_left_at_hospital = patient_time_left_at_hospital;
		this.did_survive = did_survive;
	}

	public int getHour() {
		return hour;
	}

	public Disease getPatient_status_at_doctor() {
		return patient_status_at_doctor;
	}

	public int getPatient_time_left_at_hospital() {
		return patient_time_left_at_hospital;
	}

	public boolean getDid_survive() {
		return did_survive;
	}

	
	

}
