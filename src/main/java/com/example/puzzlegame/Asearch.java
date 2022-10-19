package com.example.puzzlegame;

import java.util.*;

class StateComparator implements Comparator<StateNode> {
    public int compare(StateNode s1, StateNode s2) {
        if (s1.getCost() > s2.getCost())
            return 1;
        else if (s1.getCost() < s2.getCost())
            return -1;
        return 0;
    }
}

public class Asearch {
    HashSet<String> Explored;
public HashSet<String> get_expanded(){
    return Explored;
}
private StateNode Goal;
public StateNode get_goal(){
    return Goal;
}

    public boolean AStarSearch(StateNode initState, String goal) {
        PriorityQueue<StateNode> stateHeap = new PriorityQueue<StateNode>(new StateComparator());
        HashSet<String> stateValue = new HashSet<>();
        stateHeap.add(initState);
        stateValue.add(initState.getState());
         Explored = new HashSet<>();
        while (!stateHeap.isEmpty()) {
            StateNode state = stateHeap.poll();
            Explored.add(state.getState());
            if (state.getState().equals(goal)) {
               Goal=state;
                return true;
            }
            List<StateNode> neighbors = state.getNeighbors();
            for (StateNode neighbor : neighbors) {
                if (!(Explored.contains(neighbor.getState()) && stateValue.contains(neighbor.getState()))) {
                    stateHeap.add(neighbor);
                    stateValue.add(neighbor.getState());
                } else if (stateValue.contains(neighbor.getState())) {
                    Iterator<StateNode> value = stateHeap.iterator();
                    while (value.hasNext()) {
                        StateNode st = value.next();
                        if (st.getState().equals(neighbor.getState())) {
                            st.setCost(neighbor.getCost());
                        }
                    }
                }
            }
        }
        return false;
    }
}