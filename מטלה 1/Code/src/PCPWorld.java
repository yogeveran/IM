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
	
	public PCPWorld() 
	{
		if(!loadStates("states.txt"))
			generateStates();
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
