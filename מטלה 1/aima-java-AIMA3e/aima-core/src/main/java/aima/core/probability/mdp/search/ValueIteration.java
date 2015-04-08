package aima.core.probability.mdp.search;

import java.util.Map;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.probability.mdp.MarkovDecisionProcess;
import aima.core.probability.mdp.impl.LookupPolicy;
import aima.core.util.Util;
import assignment1.*;


/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 653.<br>
 * <br>
 * 
 * <pre>
 * function VALUE-ITERATION(mdp, &epsilon;) returns a utility function
 *   inputs: mdp, an MDP with states S, actions A(s), transition model P(s' | s, a),
 *             rewards R(s), discount &gamma;
 *           &epsilon; the maximum error allowed in the utility of any state
 *   local variables: U, U', vectors of utilities for states in S, initially zero
 *                    &delta; the maximum change in the utility of any state in an iteration
 *                    
 *   repeat
 *       U <- U'; &delta; <- 0
 *       for each state s in S do
 *           U'[s] <- R(s) + &gamma;  max<sub>a &isin; A(s)</sub> &Sigma;<sub>s'</sub>P(s' | s, a) U[s']
 *           if |U'[s] - U[s]| > &delta; then &delta; <- |U'[s] - U[s]|
 *   until &delta; < &epsilon;(1 - &gamma;)/&gamma;
 *   return U
 * </pre>
 * 
 * Figure 17.4 The value iteration algorithm for calculating utilities of
 * states. The termination condition is from Equation (17.8):<br>
 * 
 * <pre>
 * if ||U<sub>i+1</sub> - U<sub>i</sub>|| < &epsilon;(1 - &gamma;)/&gamma; then ||U<sub>i+1</sub> - U|| < &epsilon;
 * </pre>
 * 
 * @param <S>
 *            the state type.
 * @param <A>
 *            the action type.
 * 
 * @author Ciaran O'Reilly
 * @author Ravi Mohan
 * 
 */
public class ValueIteration<S, A extends Action> {
	// discount &gamma; to be used.
	private double gamma = 0;
	private LookupPolicy<S, A> opt_pi;

	/**
	 * Constructor.
	 * 
	 * @param gamma
	 *            discount &gamma; to be used.
	 */
	public ValueIteration(double gamma) {
		if (gamma > 1.0 || gamma <= 0.0) {
			throw new IllegalArgumentException("Gamma must be > 0 and <= 1.0");
		}
		this.gamma = gamma;
		opt_pi = new LookupPolicy<S, A>();
	}

	// function VALUE-ITERATION(mdp, &epsilon;) returns a utility function
	/**
	 * The value iteration algorithm for calculating the utility of states.
	 * 
	 * @param mdp
	 *            an MDP with states S, actions A(s), <br>
	 *            transition model P(s' | s, a), rewards R(s)
	 * @param epsilon
	 *            the maximum error allowed in the utility of any state
	 * @return a vector of utilities for states in S
	 */
	public Map<S, Double> valueIteration(MarkovDecisionProcess<S, A> mdp,
			double epsilon) {
		//
		// local variables: U, U', vectors of utilities for states in S,
		// initially zero
		Map<S, Double> U = Util.create(mdp.states(), new Double(0));
		Map<S, Double> Udelta = Util.create(mdp.states(), new Double(0));
		// &delta; the maximum change in the utility of any state in an
		// iteration
		double delta = 0;
		// Note: Just calculate this once for efficiency purposes:
		// &epsilon;(1 - &gamma;)/&gamma;
		double minDelta = epsilon * (1 - gamma) / gamma;

		// repeat
		do {
			// U <- U'; &delta; <- 0
			U.putAll(Udelta);
			delta = 0;
			// for each state s in S do
			for (S s : mdp.states()) {
				// max<sub>a &isin; A(s)</sub>
				Set<A> actions = mdp.actions(s);
				// Handle terminal states (i.e. no actions).
				double aMax = 0;
				A optAction= null;
				if (actions.size() > 0) {
					aMax = Double.NEGATIVE_INFINITY;
				}
				for (A a : actions) {
					// &Sigma;<sub>s'</sub>P(s' | s, a) U[s']
					double aSum = 0;
					for (S sDelta : mdp.states()) {
						if(debugStates(s,sDelta))
							System.out.print("");
						aSum += mdp.transitionProbability(sDelta, s, a)
								* U.get(sDelta);
					}
					if(((State)s).getPatient_status_at_doctor().equals(Disease.Flu)&&aSum>0)
						System.out.println("");
					if (aSum > aMax) {
						aMax = aSum;
						optAction= a;
					}
				}
				opt_pi.addToPolicy(s, optAction);
				// U'[s] <- R(s) + &gamma;
				// max<sub>a &isin; A(s)</sub>
				Udelta.put(s, mdp.reward(s) + gamma * aMax);
				// if |U'[s] - U[s]| > &delta; then &delta; <- |U'[s] - U[s]|
				double aDiff = Math.abs(Udelta.get(s) - U.get(s));
				if (aDiff > delta) {
					delta = aDiff;
				}
			}
			// until &delta; < &epsilon;(1 - &gamma;)/&gamma;
		} while (delta > minDelta);

		// return U
		return U;
	}
	
	private boolean debugStates(S s, S sDelta) {
		if(!(s instanceof State && sDelta instanceof State))
			return false;
		State stS = (State) s;
		State stsDelta = (State) sDelta;
		
		return (stS.getPatient_status_at_doctor().equals(Disease.Flu)&&stsDelta.getPatient_status_at_doctor().equals(Disease.Unknown)&&(stS.getPatient_time_left_at_hospital()==stsDelta.getPatient_time_left_at_hospital())&&(stS.getHour()==stsDelta.getHour()));
			

	}

	public A getOptimalActionForState(S curr_state)
	{
		return (A) opt_pi.action(curr_state);
	}
}
