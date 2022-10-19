package com.example.puzzlegame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class GameGui extends Application {
    int index = 0;
    ArrayList<String> states;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("8 Puzzle");
        Pane pane = new Pane();
        GridPane gridPane = new GridPane();
        GridPane Table8 = new GridPane();
        Table8.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setPadding(new Insets(20, 10, 20, 400));
        Label IntialState = new Label("IntialState");
        TextField inStateval = new TextField();
        inStateval.setPromptText("Enter like: 012345678");
        Label GoalState = new Label("GoalState");
        TextField goal = new TextField();
        goal.setPromptText("Enter like:012345678");
        Label path = new Label("Path to goal");


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(100);
                rectangle.setHeight(100);
                rectangle.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");
                rectangle.setId("rectangle");
                StackPane stack = new StackPane();
                stack.getChildren().add(rectangle);
                Table8.add(stack, i, j);
                Table8.setHgap(3);
                Table8.setVgap(3);
            }
        }


        ChoiceBox chooseAlgorithm = new ChoiceBox<String>(
                FXCollections.observableArrayList("BFS", "DFS", "A*"));
        chooseAlgorithm.setValue("");
        Platform.runLater(() -> {
            SkinBase<ChoiceBox<String>> skin = (SkinBase<ChoiceBox<String>>) chooseAlgorithm.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    if (label.getText().isEmpty()) {
                        label.setText("Choose Algorithm");
                    }
                    return;
                }
            }
        });

        ChoiceBox chooseHeuristic = new ChoiceBox<String>(
                FXCollections.observableArrayList("manhattan", "euclidean"));
        chooseHeuristic.setValue("");
        Platform.runLater(() -> {
            SkinBase<ChoiceBox<String>> skin = (SkinBase<ChoiceBox<String>>) chooseHeuristic.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    if (label.getText().isEmpty()) {
                        label.setText("Choose Heuristic");
                    }
                    return;
                }
            }
        });
        chooseHeuristic.setDisable(true);

        Label nodesExpand = new Label("Nodes Expanded:");
        Label nodesExpandVal = new Label("0");
        nodesExpandVal.setStyle("-fx-text-fill:rgb(17, 186, 216);");

        Label pathCost = new Label("Path Cost:");
        Label pathCostVal = new Label("0");
        pathCostVal.setStyle("-fx-text-fill:rgb(17, 186, 216);");


        Label searchDepth = new Label("Search Depth:");
        Label searchDepthVal = new Label("0");
        searchDepthVal.setStyle("-fx-text-fill:rgb(17, 186, 216);");


        Label runningTime = new Label("Running Time:");
        Label runningTimeVal = new Label("0");
        runningTimeVal.setStyle("-fx-text-fill:rgb(17, 186, 216);");


        Button run = new Button("Run");
        Button reset = new Button("Reset");
        Button next = new Button("Next");
        Button prev = new Button("Previous");

        reset.setDisable(true);
        next.setDisable(true);
        prev.setDisable(true);


        run.setOnAction(event -> {
            index = 0;
            prev.setDisable(true);

            String method = chooseAlgorithm.getValue().toString();
            String heuristic = chooseHeuristic.getValue().toString();

            String intState = inStateval.getText();
            String goalState = goal.getText();
            ResultState resultState = solver(method, heuristic, intState, goalState);
            states = resultState.getPath_to_goal();

            nodesExpandVal.setText(String.valueOf(resultState.nodes_expanded.size()));
            pathCostVal.setText(String.valueOf(resultState.search_depth));
            searchDepthVal.setText(String.valueOf(resultState.search_depth));
            runningTimeVal.setText(String.valueOf(resultState.getRunning_time()));

            resultState.getPath_to_goal().forEach(System.out::println);

            Draw(Table8, states.get(index));

            next.setDisable(false);
            reset.setDisable(false);
            run.setDisable(true);

        });
        next.setOnAction(event -> {

            ++index;
            System.out.println(index);
            Draw(Table8, states.get(index));
            prev.setDisable(false);
            if (index == states.size() - 1)
                next.setDisable(true);


        });

        prev.setOnAction(event -> {
            --index;
            System.out.println(index);

            Draw(Table8, states.get(index));
            next.setDisable(false);
            if (index == 0)
                prev.setDisable(true);
        });

        reset.setOnAction(event -> {

            index = 0;

            nodesExpandVal.setText("0");
            pathCostVal.setText("0");
            searchDepthVal.setText("0");
            runningTimeVal.setText("0");
            prev.setDisable(true);
            next.setDisable(true);
            run.setDisable(false);


        });


        chooseAlgorithm.setOnAction(actionEvent -> {
            if (chooseAlgorithm.getSelectionModel().getSelectedItem().equals("A*")) {
                chooseHeuristic.setDisable(false);
                chooseHeuristic.setValue("manhattan");
            } else {
                chooseHeuristic.setDisable(true);
                chooseHeuristic.setValue("");
            }
        });
        gridPane.add(IntialState, 0, 4);
        gridPane.add(inStateval, 0, 5);
        gridPane.setHgap(5);
        gridPane.add(GoalState, 1, 4);
        gridPane.add(goal, 1, 5);
        gridPane.setVgap(8);

        gridPane.add(chooseAlgorithm, 0, 6);
        gridPane.setHgap(5);
        gridPane.add(chooseHeuristic, 1, 6);
        gridPane.add(run, 1, 14);
        gridPane.setVgap(8);

        gridPane.add(nodesExpand, 0, 7);
        gridPane.add(nodesExpandVal, 1, 7);
        gridPane.add(searchDepth, 0, 8);
        gridPane.add(searchDepthVal, 1, 8);
        gridPane.add(pathCost, 0, 9);
        gridPane.add(pathCostVal, 1, 9);
        gridPane.add(runningTime, 0, 10);
        gridPane.add(runningTimeVal, 1, 10);

        Table8.add(next, 0, 3);
        Table8.add(prev, 2, 3);
        gridPane.add(reset, 0, 14);

        Table8.add(path, 1, 3);

        Table8.setMinSize(500, 400);
        pane.getChildren().addAll(Table8, gridPane);
        stage.setScene(new Scene(pane));
        stage.show();
    }

    private ResultState solver(String method, String heurstic, String intstate, String goal) {
        ResultState resultState = new ResultState();
        switch (method) {
            case "A*":
                Asearch AS = new Asearch();
                long start = System.currentTimeMillis();
                AS.AStarSearch(new StateNode(intstate, -1, 0, heurstic), goal);
                long end = System.currentTimeMillis();
                resultState.FinalStates(AS.get_goal());
                resultState.setNodes_expanded(AS.get_expanded());
                resultState.setRunning_time(end - start);
            case "DFS":
                DFS dfs = new DFS();
                start = System.currentTimeMillis();
                dfs.DepthFirstSearch(new StateNode(intstate, -1, 0), goal);
                end = System.currentTimeMillis();
                resultState.setRunning_time(end - start);
                resultState.FinalStates(dfs.get_goal());
                resultState.setNodes_expanded(dfs.get_expanded());
            case "BFS":
                BFS bfs = new BFS();
                start = System.currentTimeMillis();
                bfs.BreadthFirstSearch(new StateNode(intstate, -1, 0), goal);
                end = System.currentTimeMillis();
                resultState.setRunning_time(end - start);
                resultState.FinalStates(bfs.get_goal());
                resultState.setNodes_expanded(bfs.get_expanded());

        }

        return resultState;
    }

    private void Draw(GridPane gridPane, String state) {

        int letter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Label label = new Label(String.valueOf(state.toCharArray()[letter]).replace("0", " "));
                if(index==states.size()-1)
                {
                    label.setStyle("-fx-font-weight : bold; -fx-font-family:\"Arial Narrow\";-fx-font-size:40;-fx-text-fill: red;-fx-border-fill: red");

                }else
                {
                    label.setStyle("-fx-font-weight : bold; -fx-font-family:\"Arial Narrow\";-fx-font-size:40; ");
                }
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(100);
                rectangle.setHeight(100);
                rectangle.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");
                rectangle.setId("rectangle");
                StackPane stack = new StackPane();
                stack.getChildren().addAll(rectangle, label);
                gridPane.add(stack, j, i);
                gridPane.setHgap(3);
                gridPane.setVgap(3);
                letter++;
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }


}