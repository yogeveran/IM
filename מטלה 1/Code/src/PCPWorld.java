import java.util.Collection;
import java.util.Set;


public class PCPWorld {



	public State getInitialState() {
		// TODO Auto-generated method stub
		return new State(9, Disease.Unknown, 0, Disease.None, 0);
	}

	public Set<State> getStates() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<? extends State> getTerminalStates() {
		// TODO Auto-generated method stub
		return null;
	}

}
