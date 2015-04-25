import mdp, util

from learningAgents import ValueEstimationAgent
from test.test_typechecks import Integer

class ValueIterationAgent(ValueEstimationAgent):
  """
      * Please read learningAgents.py before reading this.*

      A ValueIterationAgent takes a Markov decision process
      (see mdp.py) on initialization and runs value iteration
      for a given number of iterations using the supplied
      discount factor.
  """
  def __init__(self, mdp, discount = 0.9, iterations = 100):
    """
      Your value iteration agent should take an mdp on
      construction, run the indicated number of iterations
      and then act according to the resulting policy.
    
      Some useful mdp methods you will use:
          mdp.getStates()
          mdp.getPossibleActions(state)
          mdp.getTransitionStatesAndProbs(state, action)
          mdp.getReward(state, action, nextState)
    """
    self.mdp = mdp
    self.discount = discount
    self.iterations = iterations
    self.values = util.Counter() # A Counter is a dict with default 0
     
    "*** YOUR CODE HERE ***"
    self.policy = util.Counter() # 0 policy should not be possible
    states = mdp.getStates()
    for _ in range(self.iterations):
        oldValues = self.values.copy()
        for state in states:
            aMaxValue = 0
            aMax = None
            actions  = self.mdp.getPossibleActions(state)
            if(len(actions)>0):
                aMaxValue = float('-inf')
            for action in actions:
                aSum = 0
                for (nextState,TransferProbability) in mdp.getTransitionStatesAndProbs(state, action):
                    aSum+= TransferProbability* (self.mdp.getReward(state, action, nextState) + self.discount * oldValues[nextState])
                if aSum>aMaxValue:
                    aMaxValue = aSum
                    aMax = action
                    
            self.policy[state] = aMax
            self.values[state] = aMaxValue
                    
                    
  def getValue(self, state):
    """
      Return the value of the state (computed in __init__).
    """
    return self.values[state]


  def getQValue(self, state, action):
    """
      The q-value of the state action pair
      (after the indicated number of value iteration
      passes).  Note that value iteration does not
      necessarily create this quantity and you may have
      to derive it on the fly.
    """
    "*** YOUR CODE HERE ***"
    util.raiseNotDefined()

  def getPolicy(self, state):
    """
      The policy is the best action in the given state
      according to the values computed by value iteration.
      You may break ties any way you see fit.  Note that if
      there are no legal actions, which is the case at the
      terminal state, you should return None.
    """
    "*** YOUR CODE HERE ***"
    return self.policy[state] 

  def getAction(self, state):
    "Returns the policy at the state (no exploration)."
    return self.getPolicy(state)
  
