package assignment1;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PCPWorld {

	private static final State InitialState = new State(false, 0, Disease.Unknown, 9);
	private static Set<State>  states = new HashSet<State>();
	private int closingTime = 14;
	public  void createStates(){
		String fileName = "states.txt";
		states = null;
		if(!loadStates(fileName))
			generateStates();
	}

	public void generateStates(){
		states = new HashSet<State>();
		states.add(InitialState);
		generateSons(InitialState);
	}
	
	private void generateSons(State s) {
		if(isTerminal(s))
			return;
		if(isDiagnose(s)){
			Set<State> statesList = new HashSet<State>();
			statesList.add(new State(false, Math.max(0, s.patient_time_left_at_hospital-1), Disease.Flu, s.hour+1));
			statesList.add(new State(false, Math.max(0, s.patient_time_left_at_hospital-1), Disease.Ebola, s.hour+1));
			statesList.add(new State(false, Math.max(0, s.patient_time_left_at_hospital-1), Disease.Cough, s.hour+1));
			states.addAll(statesList);
			for(State son:statesList)
				generateSons(son);
		}else{
			if(isSendToHospital(s)){
				Set<State> statesList = new HashSet<State>();

				statesList.add(new State(false, 2, Disease.Unknown, s.hour));
				statesList.add(new State(false, 1, Disease.Unknown, s.hour));

				statesList.add(new State(true, 2, Disease.Unknown, s.hour));
				statesList.add(new State(true, 1, Disease.Unknown, s.hour));
				
				states.addAll(statesList);
				for(State son:statesList)
					generateSons(son);	
			}
			
			//Send To Home
			Set<State> statesList = new HashSet<State>();

			statesList.add(new State(false, s.patient_time_left_at_hospital, Disease.Unknown, s.hour));
			statesList.add(new State(true, s.patient_time_left_at_hospital, Disease.Unknown, s.hour));
			states.addAll(statesList);
			for(State son:statesList)
				generateSons(son);	
			return;
			
		}
		
		
	}

	private static boolean isSendToHospital(State s) {
		return s.patient_time_left_at_hospital==0;
	}

	private static boolean isDiagnose(State s) {
		return s.getPatient_status_at_doctor().equals(Disease.Unknown);
	}


	private  boolean isTerminal(State s) {
		return s.getHour()==closingTime&&s.getPatient_status_at_doctor().equals(Disease.Unknown);
	}

	public PCPWorld(int i) 
	{
		this.closingTime = i;
		createStates();
		states.remove(InitialState);
		states.add(InitialState);
	}
	
	
	public State getInitialState() 
	{
		return InitialState;
	}

	public Set<State> getStates() 
	{	
		return states;				
	}

	public static boolean saveStates(String fileName){
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fileName));
			oos.writeObject(states);
			oos.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

public static boolean loadStates(String fileName){
	try {
	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
	states = (HashSet<State>) ois.readObject();
	ois.close();
	} catch (IOException | ClassNotFoundException e) {
		return false;
	}
	
	return true;
}


}
