/**
 * 
 */

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
/**
 * @author eranyogev
 *
 */
public class PCPAction implements aima.core.agent.Action {
	ActionType type;
	public PCPAction(ActionType type) {
		super();
		this.type = type;
	}



	@Override
	public boolean isNoOp() {
		return this.type.equals(ActionType.NoOp);
	}
}
