import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class PCPWorld {



	private static final State InitialState = new State(9, Disease.Unknown, 0, Disease.None, 0);
	private static final Set<State>  states = new HashSet<State>();

	public State getInitialState() {
		// TODO Auto-generated method stub
		return InitialState;
	}

	public Set<State> getStates() {
		// TODO Auto-generated method stub
		 states.add(InitialState);
		return states;
				
	}


}
