/**
 * 
 */

/**
 * @author eranyogev
 *
 */
public class State {

	/**
	 * 
	 */
	int hour;
	Disease patient_status_at_doctor; 
	int patient_time_left_at_hospital;
	Disease patient_status_at_hospital; 
	int healed;
	
	public State(int hour, Disease patient_status_at_doctor,
			int patient_time_left_at_hospital,
			Disease patient_status_at_hospital, int healed) {
		super();
		this.hour = hour;
		this.patient_status_at_doctor = patient_status_at_doctor;
		this.patient_time_left_at_hospital = patient_time_left_at_hospital;
		this.patient_status_at_hospital = patient_status_at_hospital;
		this.healed = healed;
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

	public Disease getPatient_status_at_hospital() {
		return patient_status_at_hospital;
	}

	public int getHealed() {
		return healed;
	}

	
	

}
