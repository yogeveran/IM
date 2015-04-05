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

	public String getActionName ()
	{
		return type.toString();
	}

	@Override
	public boolean isNoOp() {
		return this.type.equals(ActionType.NoOp);
	}
}
