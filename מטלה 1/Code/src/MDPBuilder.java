

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import aima.core.environment.cellworld.State;
//import aima.core.environment.cellworld.CellWorld;
//import aima.core.environment.cellworld.DoctorAction;
import aima.core.probability.mdp.ActionsFunction;
import aima.core.probability.mdp.MarkovDecisionProcess;
import aima.core.probability.mdp.RewardFunction;
import aima.core.probability.mdp.TransitionProbabilityFunction;
import aima.core.probability.mdp.impl.MDP;

 

/**
 * 
 * @author Eran Yogev 
 * 
 * Built on MDP Factory Example
 */
public class MDPBuilder {

	/**
	 * Constructs an MDP that can be used to generate the utility values
	 * 
	 * 
	 * @param dw
	 *            the State world
	 * @return an MDP that can be used to generate the utility values detailed
	 *         
	 */
	public static MarkovDecisionProcess<State, DoctorAction> createMDP(
			final PCPWorld dw) {

		return new MDP<State, DoctorAction>(dw.getStates(),
				dw.getInitialState(), createActionsFunction(dw),
				createTransitionProbabilityFunction(dw),
				createRewardFunction());
	}

	/**
	 * Returns the allowed actions from a specified State within the State world
	 * described in Fig 17.1.
	 * 
	 * @param dw
	 *            the State world from figure 17.1.
	 * @return the set of actions allowed at a particular State. This set will be
	 *         empty if at a terminal state.
	 */
	public static ActionsFunction<State, DoctorAction> createActionsFunction(
			final PCPWorld dw) {
		final Set<State> terminals = new HashSet<State>();
		terminals.addAll(dw.getTerminalStates());

		ActionsFunction<State, DoctorAction> af = new ActionsFunction<State, DoctorAction>() {

			@Override
			public Set<DoctorAction> actions(State s) {
				// All actions can be performed in each State
				// (except terminal states)
				if (terminals.contains(s)) {
					return Collections.emptySet();
				}
				return DoctorAction.actions();
			}
		};
		return af;
	}

	/**
	 * Figure 17.1 (b) Illustration of the transition model of the environment:
	 * the 'intended' outcome occurs with probability 0.8, but with probability
	 * 0.2 the agent moves at right angles to the intended direction. A
	 * collision with a wall results in no movement.
	 * 
	 * @param cw
	 *            the State world from figure 17.1.
	 * @return the transition probability function as described in figure 17.1.
	 */
	public static TransitionProbabilityFunction<State, DoctorAction> createTransitionProbabilityFunction(
			final PCPWorld cw) {
		TransitionProbabilityFunction<State, DoctorAction> tf = new TransitionProbabilityFunction<State, DoctorAction>() {
			private double[] distribution = new double[] { 0.8, 0.1, 0.1 };

			@Override
			public double probability(State sDelta, State s,
					DoctorAction a) {
				double prob = 0;

				List<State> outcomes = possibleOutcomes(s, a);
				for (int i = 0; i < outcomes.size(); i++) {
					if (sDelta.equals(outcomes.get(i))) {
						// Note: You have to sum the matches to
						// sDelta as the different actions
						// could have the same effect (i.e.
						// staying in place due to there being
						// no adjacent cells), which increases
						// the probability of the transition for
						// that state.
						prob += distribution[i];
					}
				}

				return prob;
			}
			
			private List<State> possibleOutcomes(State c,
					DoctorAction a) {
				// There can be three possible outcomes for the planned action
				List<State> outcomes = new ArrayList<State>();

				outcomes.add(cw.result(c, a));
				outcomes.add(cw.result(c, a.getFirstRightAngledAction()));
				outcomes.add(cw.result(c, a.getSecondRightAngledAction()));

				return outcomes;
			}
		};

		return tf;
	}

	/**
	 * 
	 * @return the reward function which takes the content of the State as being
	 *         the reward value.
	 */
	public static RewardFunction<State> createRewardFunction() {
		RewardFunction<State> rf = new RewardFunction<State>() {
			@Override
			public double reward(State s) {
				return s.getContent();
			}
		};
		return rf;
	}
}
