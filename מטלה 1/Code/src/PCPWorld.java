import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PCPWorld {

	private static final State InitialState = new State(false, 0, Disease.Unknown, 9);
	private static Set<State>  states = new HashSet<State>();
	
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


}
