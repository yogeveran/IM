package assignment1;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
				dw.getStates(), //Done
				dw.getInitialState(),//Done
				createActionsFunction(dw),//Done
				createTransitionProbabilityFunction(dw), //Done
				createRewardFunction()); //Done
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
				return s.patient_time_left_at_hospital==0;
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
				switch (a.getType())
				{
				case Diagnose:
					if (isPossibleFollowingDiagnoseState(s, sDelta))
					{
						switch (sDelta.getPatient_status_at_doctor())
						{
						case Flu:
							return 0.8;
						case Unknown:
							return 0;
						case Cough:
							return 0.1;
						case Ebola:
							return 0.1;
						default:
							return 0;
						}
					}
					break;
				case SendHome:
					if (isPossibleFollowingSendHomeState(s, sDelta))
					{
						switch(s.getPatient_status_at_doctor())
						{
						case Cough:
							return 0.5;
						case Flu:
							if (sDelta.getDid_survive())
								return 1.0;
							return 0;
						case Ebola:
							if (!sDelta.getDid_survive())
								return 1.0;
							return 0;
						case Unknown:
							return 0;
						default:
							return 0;
						}
					}
					break;
				case SendToHospital:
					if (isPossibleFollowingSendToHospitalState(s, sDelta))
					{
						switch(s.getPatient_status_at_doctor())
						{
						case Ebola:
							if (sDelta.getDid_survive())
								return 0.125;
							return 0.375;
						case Flu:
							if (sDelta.getDid_survive())
								return 1.0;
							return 0;
							
						case Cough:
							if (sDelta.getDid_survive())
								return 0.5;
						case Unknown:
							return 0;
						default:	
							return 0;
						}
					}
					break;
				default:
					break;
				}
				return 0;
			
			}

			private boolean isPossibleFollowingSendToHospitalState(State s,
					State sDelta) {
				List<Disease> lst = new LinkedList<Disease>();
				lst.add(Disease.Cough);
				lst.add(Disease.Ebola);
				lst.add(Disease.Flu);
				boolean disease = lst.contains(s.getPatient_status_at_doctor());
				return disease&&sDelta.getPatient_status_at_doctor()==Disease.Unknown &&
						sDelta.getHour()==s.getHour() &&
						s.getPatient_time_left_at_hospital()==0&&
						sDelta.getPatient_time_left_at_hospital()>0;
			}

			private boolean isPossibleFollowingSendHomeState(State s,
					State sDelta) {
				
				Set<State> homeOutcomes = new HashSet<State>();
				boolean diagnosed = s.getPatient_status_at_doctor().equals(Disease.Unknown);
				if(diagnosed)
					return false;
				
				boolean newPatient = sDelta.getPatient_status_at_doctor().equals(Disease.Unknown);
				boolean sameHour = sDelta.getHour()==s.getHour();
				boolean noTimeChange = s.getPatient_time_left_at_hospital()==sDelta.getPatient_time_left_at_hospital();
				return newPatient &&
						sameHour &&
						noTimeChange&&
						diagnosed;
			}

			private boolean isPossibleFollowingDiagnoseState(State s, State sDelta) {
				return isDiagnosisNeeded(s) &&
					is_First_OneHour_Before_Second(s, sDelta) && 
					didTimePassForHospitalPatient(s, sDelta);
			}

			private boolean didTimePassForHospitalPatient(State s, State sDelta) {
				return sDelta.getPatient_time_left_at_hospital()==
				Math.max(s.getPatient_time_left_at_hospital()-1, 0);
			}

			private boolean is_First_OneHour_Before_Second(State s, State sDelta) {
				return s.getHour()+1==sDelta.getHour();
			}

			private boolean isDiagnosisNeeded(State s) {
				return s.getPatient_status_at_doctor().equals(Disease.Unknown);
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
		return new RewardFunction<State>() {
			@Override
			public double reward(State s) {
				return s.did_survive ? 1 :  0;
			}
		};
	}
}
