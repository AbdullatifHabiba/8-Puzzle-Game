package com.example.puzzlegame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class ResultState {


    private ArrayList<String> path_to_goal;
    public ArrayList<String> getPath_to_goal() {
        return path_to_goal;
    }
  public int cost_of_path;

    public HashSet getNodes_expanded() {
        return nodes_expanded;
    }

    public void setNodes_expanded(HashSet nodes_expanded) {
        this.nodes_expanded = nodes_expanded;
    }

    public HashSet nodes_expanded;
  public int search_depth;

    public long getRunning_time() {
        return running_time;
    }

    public void setRunning_time(long running_time) {
        this.running_time = running_time;
    }

    public long running_time;



    public void FinalStates(StateNode state) {
         path_to_goal = new ArrayList<>();
         Stack<String> stack=new Stack<>();
        while (state != null) {
            stack.push(state.getState());
            state = state.getParent();
        }
        while (!stack.empty())path_to_goal.add(stack.pop());
        search_depth=path_to_goal.size();
        }

}
