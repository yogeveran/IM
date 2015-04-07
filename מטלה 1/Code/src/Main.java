import java.util.Map;

import aima.core.probability.mdp.MarkovDecisionProcess;
import aima.core.probability.mdp.search.ValueIteration;




public class Main {

	public static void main(String[] args) {
		PCPWorld.createStates();
		PCPWorld dw = new PCPWorld();
		int gamma = 1;
		ValueIteration<State, PCPAction> vi = new ValueIteration<State, PCPAction>(gamma);
		MarkovDecisionProcess<State, PCPAction> mdp = MDPBuilder.createMDP(dw);
		double epsilon=0;
		Map<State, Double> mp = vi.valueIteration(mdp, epsilon);
		for(State s:dw.getStates())
			System.out.println(s+" | " + vi.getOptimalActionForState(s));
		
	}

}
