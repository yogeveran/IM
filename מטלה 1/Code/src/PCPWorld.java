import java.util.HashSet;
import java.util.Set;


public class PCPWorld {

	private static final State InitialState = new State(false, 0, Disease.Unknown, 9);
	private static Set<State>  states = new HashSet<State>();
	
	public PCPWorld()
	{
		states.add(InitialState);
	}
	
	public State getInitialState() {
		return InitialState;
	}

	public Set<State> getStates() {	
		return states;				
	}


}
