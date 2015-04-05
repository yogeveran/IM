import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
	public static MarkovDecisionProcess<State, PCPAction> createMDP(
			final PCPWorld dw) {

		return new MDP<State, PCPAction>(
				dw.getStates(), //Too Many States to List.
				dw.getInitialState(), //Done
				createActionsFunction(dw),
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
	public static ActionsFunction<State, PCPAction> createActionsFunction(
			final PCPWorld dw) {
		

		ActionsFunction<State, PCPAction> af = new ActionsFunction<State, PCPAction>() {

			@Override
			public Set<PCPAction> actions(State s) {
				// All actions can be performed in each State
				// (except terminal states)
				HashSet<PCPAction> result = new HashSet<PCPAction>();
				
				if (isTerminal(s)) {
					return Collections.emptySet();
				}
				if(needToDiagnose(s)){
					result.add(new PCPAction(ActionType.Diagnose));
					return  result;
				}
				if(isHospitalFree(s))
					result.add(new PCPAction(ActionType.SendToHospital));
				result.add(new PCPAction(ActionType.SendHome));
				return result;
			}

			private boolean isHospitalFree(State s) {
				return s.patient_time_left_at_hospital>0;
			}

			private boolean needToDiagnose(State s) {
				return s.getPatient_status_at_doctor().equals(Disease.Unknown);
			}

			private boolean isTerminal(State s) {
				return s.getHour() == 14 && s.getPatient_status_at_doctor().equals(Disease.Unknown);
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
	public static TransitionProbabilityFunction<State, PCPAction> createTransitionProbabilityFunction(
			final PCPWorld cw) {
		TransitionProbabilityFunction<State, PCPAction> tf = new TransitionProbabilityFunction<State, PCPAction>() {
			//private double[] distribution = new double[] { 0.8, 0.1, 0.1 };

			
			/**
			 * Return the probability of going from state s using action a to s' based
			 * on the underlying transition model P(s' | s, a).
			 * 
			 * @param sDelta
			 *            the state s' being transitioned to.
			 * @param s
			 *            the state s being transitions from.
			 * @param a
			 *            the action used to move from state s to s'.
			 * @return the probability of going from state s using action a to s'.
			 */
			@Override
			public double probability(State sDelta, State s,
					PCPAction a) {
				//double prob = 0;
				String actionName = a.getActionName();
				switch (actionName)
				{
				case "Diagnose":
					if (s.getPatient_status_at_doctor()==Disease.Unknown &&
						s.getHour()+1==sDelta.getHour() && sDelta.getPatient_time_left_at_hospital()==
						Math.max(s.getPatient_time_left_at_hospital()-1, 0))
					{
						switch (sDelta.getPatient_status_at_doctor())
						{
						case Flu:
							return 0.8;
						case Unknown:
							return 0;
						default:
							return 0.1;
						}
					}
					break;
				case "SendHome":
					if (sDelta.getPatient_status_at_doctor()==Disease.Unknown &&
							sDelta.getHour()==s.getHour() &&
							s.getPatient_time_left_at_hospital()==sDelta.getPatient_time_left_at_hospital())
					{
						switch(s.getPatient_status_at_doctor())
						{
						case Cough:
							return 0.5;
						case Flu:
							if (sDelta.getDid_survive())
								return 1.0;
							return 0;
						default:
							if (!sDelta.getDid_survive())
								return 1.0;
							return 0;
						}
					}
					break;
				case "SendToHospital":
					if (sDelta.getPatient_status_at_doctor()==Disease.Unknown &&
							sDelta.getHour()==s.getHour() &&
							s.getPatient_time_left_at_hospital()==0)
					{
						switch(s.getPatient_status_at_doctor())
						{
						case Ebola:
							if (sDelta.getDid_survive())
								return 0.125;
							return 0.375;
						case Unknown:
							return 0;
						default:
							if (sDelta.getDid_survive())
								return 0.5;	
							return 0;
						}
					}
					break;
				default:
					break;
				}
				return 0;
					
				/*List<State> outcomes = possibleOutcomes(s, a);
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

				return prob;*/
			
			}
			
			/*private List<State> possibleOutcomes(State c,
					PCPAction a) {
				// There can be three possible outcomes for the planned action
				List<State> outcomes = new ArrayList<State>();

				outcomes.add(cw.result(c, a));
				outcomes.add(cw.result(c, a.getFirstRightAngledAction()));
				outcomes.add(cw.result(c, a.getSecondRightAngledAction()));

				return outcomes;
			}*/
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
				if (s.did_survive)
					return 1;
				else
					return 0;
			}
		};
		return rf;
	}
}
