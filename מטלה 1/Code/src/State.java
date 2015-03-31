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
	boolean patients_at_pcp; 
	int patient_time_left_at_hospital;
	int healed;

	public int getHour() {
		return hour;
	}

	public boolean isPatients_at_pcp() {
		return patients_at_pcp;
	}

	public int getPatient_time_left_at_hospital() {
		return patient_time_left_at_hospital;
	}

	public int getHealed() {
		return healed;
	}

	public State(int hour, boolean patients_at_pcp, int patient_time_left_at_hospital, int healed) {
		 this.hour = hour;
		 this.patients_at_pcp = patients_at_pcp; 
		 this.patient_time_left_at_hospital = patient_time_left_at_hospital;
		 this.healed = healed;
	}

}
