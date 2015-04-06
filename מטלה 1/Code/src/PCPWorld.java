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
	
	public static void main(String [ ] args){
		generateStates();
		String fileName = "states.txt";
		if(saveStates(fileName))
			System.out.println("Success Saving");
		else
			System.out.println("Failure Saving");
		
		
		states = null;
		if(loadStates(fileName))
			System.out.println("Success Loading");
		else
			System.out.println("Failure Loading");
		
		for(State s: states){
			System.out.println(s);
		}
		
//		waitForKey();
	}

	private static void waitForKey() {
		try {
			System.in.read();
		} catch (IOException e) {
		}
	}
	
	public static void generateStates(){
		for(int time_left = 0;time_left<3;time_left++){
			for(Disease d :Disease.values()){
				for(int time = 9;time<15;time++){
					states.add(new State(true, time_left, d, time));
					states.add(new State(false, time_left, d, time));
				}
			}
		}
	}
	
	public PCPWorld() throws IOException
	{
		List<String> lines=Files.readAllLines(Paths.get("states.txt"), Charset.forName("UTF-8"));
		String[] data;
		boolean didSurvive;
		int timeInHospital, hour;
		Disease disease;
		for(String line:lines){
			data = line.split(",");
			if (data[0]=="false")
				didSurvive= false;
			else
				didSurvive= true;
			timeInHospital = Integer.parseInt(data[1]);
			disease = getDisease(data[2]);
			hour = Integer.parseInt(data[3]);
			State newState = new State (didSurvive, timeInHospital, disease, hour);
			states.add(newState);
		}
	}
	
	private Disease getDisease (String disease)
	{
		switch(disease)
		{
		case "flu":
			return Disease.Flu;
		case "cough":
			return Disease.Cough;
		case "ebole":
			return Disease.Ebola;
		default:
			return Disease.Flu;
		}
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
