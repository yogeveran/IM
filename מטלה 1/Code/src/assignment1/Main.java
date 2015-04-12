package assignment1;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aima.core.probability.mdp.MarkovDecisionProcess;
import aima.core.probability.mdp.search.ValueIteration;




public class Main {

	public static void main(String[] args) {
		int gamma = 1;
		double epsilon=0;
		//printRange(gamma, epsilon,14,14);
		printPolicy(gamma, epsilon,14);
	}


	private static void printPolicy(int gamma, double epsilon,int closing) {
		PCPWorld dw = new PCPWorld(closing);
		ValueIteration<State, PCPAction> vi = new ValueIteration<State, PCPAction>(gamma);
		MarkovDecisionProcess<State, PCPAction> mdp = MDPBuilder.createMDP(dw,closing);
		
		Map<State, Double> mp = vi.valueIteration(mdp, epsilon);
		
		LinkedList<State> states = new LinkedList<State>();
		states.addAll(dw.getStates());
		sortStates(states);
		printStates(vi, mp, states);
		
		System.out.println("Avg Patients: " + mp.get(dw.getInitialState()));
	}


	private static void printRange(int gamma, double epsilon,int start, int end) {
		List<Double> results = new LinkedList<Double>();
		for(int closingTime = start; closingTime<=end;closingTime++){
			double avgHealed = runValueIteration(gamma, epsilon, closingTime);
			System.out.print("Close:"+closingTime+":00  ,"+avgHealed+"|");
			results.add( avgHealed);	
		}
	}


	private static double runValueIteration(int gamma, double epsilon,
			int closingTime) {
		PCPWorld dw = new PCPWorld(closingTime);
		ValueIteration<State, PCPAction> vi = new ValueIteration<State, PCPAction>(gamma);
		MarkovDecisionProcess<State, PCPAction> mdp = MDPBuilder.createMDP(dw,closingTime);
		
		Map<State, Double> mp = vi.valueIteration(mdp, epsilon);
		
		return mp.get(dw.getInitialState());
	}


	private static void sortStates(LinkedList<State> states) {
		states.sort(new Comparator<State>() {
			@Override
			public int compare(State o1, State o2) {
				if(o1 == null)
		        	return 1;
				if(o2 == null)
		        	return -1;
		        
		        if(o1.hour<o2.hour)
		        	return -1;
		        if(o1.hour>o2.hour)
		        	return 1;
		        
		        if(!o1.patient_status_at_doctor.equals(o2.patient_status_at_doctor))
		        	return orderDisease(o1.patient_status_at_doctor,o2.patient_status_at_doctor);
		        
		        if(o1.patient_time_left_at_hospital<o2.patient_time_left_at_hospital)
		        	return -1;
		        if(o2.patient_time_left_at_hospital<o1.patient_time_left_at_hospital)
		        	return 1;
		        
		        return o1.did_survive ? (o2.did_survive ? 0: -1) : (o2.did_survive ? 1: 0); 
		        
			}
			private int orderDisease(Disease patient_status_at_doctor2,
					Disease patient_status_at_doctor3) {
				return patient_status_at_doctor2.ordinal()<patient_status_at_doctor3.ordinal() ? -1 : 1;
			} 
		});
	}

	private static void printStates(ValueIteration<State, PCPAction> vi,Map<State, Double> mp, LinkedList<State> states) {
		for(State s:states)
			System.out.println(s+" | " + vi.getOptimalActionForState(s) + " | "+mp.get(s));
	}


}
