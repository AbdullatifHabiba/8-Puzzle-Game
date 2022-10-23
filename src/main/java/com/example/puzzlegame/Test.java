package com.example.puzzlegame;

public class Test {
    public static void main(String[] args) {
       /* DFS obj = new DFS();
        Asearch ON=new Asearch();
        long G = System.currentTimeMillis();
        obj.DepthFirstSearch(new StateNode("142658730",-1,0), "012345678");
        long G2 = System.currentTimeMillis();
        System.out.println("first :"+(G2-G)+"secs");

         G = System.currentTimeMillis();
        obj.DepthFirstSearch(new StateNode("102754863",-1,0), "012345678");

         G2 = System.currentTimeMillis();
        System.out.println("second :"+(G2-G)+"secs");

        G = System.currentTimeMillis();
        obj.DepthFirstSearch(new StateNode("712345608",-1,0), "012345678");
         G2=System.currentTimeMillis();

        System.out.println("third :"+(G2-G)+"secs");
        */
        Asearch obj = new Asearch();
        ResultState resultState=new ResultState();
        long G = System.currentTimeMillis();
        obj.AStarSearch(new StateNode("142658730",-1,0,"manhattan"), "012345678");
        long G2 = System.currentTimeMillis();
        System.out.println(obj.get_goal().getState());
        resultState.FinalStates(obj.get_goal());
      //  resultState.getNodes_expanded().forEach(System.out::println);
        System.out.println("first :"+(G2-G)+"secs");

//        G = System.currentTimeMillis();
//        obj.BreadthFirstSearch(new StateNode("102754863",-1,0), "012345678");
//
//        G2 = System.currentTimeMillis();
//        System.out.println("second :"+(G2-G)+"secs");
//
//        G = System.currentTimeMillis();
//        obj.BreadthFirstSearch(new StateNode("712345608",-1,0), "012345678");
//        G2=System.currentTimeMillis();
//
//        System.out.println("third :"+(G2-G)+"secs");
    }
}
