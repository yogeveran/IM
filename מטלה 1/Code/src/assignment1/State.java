package assignment1;
import java.io.Serializable;

/**
 * @author eranyogev
 *
 */
public class State implements Serializable  {

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

	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof State))return false;
	    State otherMyClass = (State)other;
	    return (this.hour == otherMyClass.hour) 
	    		&& (this.did_survive == otherMyClass.did_survive)
	    		&& (this.patient_status_at_doctor == otherMyClass.patient_status_at_doctor)
	    		&& (this.patient_time_left_at_hospital == otherMyClass.patient_time_left_at_hospital);
	}

	@Override
	public String toString() {
		return "State [hour=" + hour + ", patient_status_at_doctor="
				+ patient_status_at_doctor + ", patient_time_left_at_hospital="
				+ patient_time_left_at_hospital + ", did_survive="
				+ did_survive + "]";
	}
	
	@Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public int compareTo(State otherState) {
        
        if(otherState == null)
        	return -1;
        
        if(this.hour<otherState.hour)
        	return -1;
        if(this.hour>otherState.hour)
        	return 1;
        
        if(!this.patient_status_at_doctor.equals(otherState.patient_status_at_doctor))
        	return orderDisease(this.patient_status_at_doctor,otherState.patient_status_at_doctor);
        
        if(this.patient_time_left_at_hospital<otherState.patient_time_left_at_hospital)
        	return -1;
        if(otherState.patient_time_left_at_hospital<this.patient_time_left_at_hospital)
        	return 1;
        
        return this.did_survive ? (otherState.did_survive ? 0: -1) : (otherState.did_survive ? 1: 0); 
        
        
     }

	private int orderDisease(Disease patient_status_at_doctor2,
			Disease patient_status_at_doctor3) {
		return patient_status_at_doctor2.ordinal()<patient_status_at_doctor3.ordinal() ? -1 : 1;
	} 
}
