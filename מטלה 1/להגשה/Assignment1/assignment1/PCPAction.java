package assignment1;
/**
 * 
 */


import aima.core.agent.Action;
/**
 * @author eranyogev
 *
 */
public class PCPAction implements Action {
	ActionType type;
	public PCPAction(ActionType type) {
		super();
		this.type = type;
	}

	@Override
	public boolean isNoOp() {
		return this.type.equals(ActionType.NoOp);
	}

	public ActionType getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	@Override
	public String toString() {
		return "Action [type=" + type + "]";
	}
}
