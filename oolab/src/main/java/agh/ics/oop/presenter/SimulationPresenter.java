package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.Boundary;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap map;
    private static final int CELL_WIDTH = 35;
    private static final int CELL_HEIGHT = 35;
    @FXML
    private Button startButton;
    @FXML
    private Label infoLabel;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Label movesDescriptionLabel;
    @FXML
    private TextField movesTextField;

    public void setWorldMap(WorldMap map) {
        this.map = map;
    }

    public void drawMap(){
        clearGrid();
        Boundary boundary = map.getCurrentBounds();
        int left = boundary.lowerLeft().getX();
        int right = boundary.upperRight().getX();
        int bottom = boundary.lowerLeft().getY();
        int top = boundary.upperRight().getY();
        drawAxes(left, right,bottom,top);
        drawMapElements(left, right, bottom, top);
        for (Node label : mapGrid.getChildren()){
            GridPane.setHalignment(label, HPos.CENTER);
        }
    }

    private void drawMapElements(int left, int right, int bottom, int top) {
        for (int x = left; x <= right; x++) {
            for (int y = bottom; y <= top; y++) {
                Vector2d position = new Vector2d(x, y);
                map.objectAt(position).ifPresent(worldElement ->
                        mapGrid.add(new WorldElementBox(worldElement), position.getX() - left + 1, top - position.getY() + 1));
//                        mapGrid.add(new Label(worldElement.toString()), position.getX() - left + 1, top - position.getY() + 1));
            }
        }
    }

    private void drawAxes(int left, int right, int bottom, int top) {
        mapGrid.add(new Label("y\\x"),0,0);
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        for (int x = left; x <= right; x++){
            mapGrid.add(new Label("%d".formatted(x)), x - left + 1, 0);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }
        for (int y = bottom; y <= top; y++){
            mapGrid.add(new Label("%d".formatted(y)), 0,  top - y + 1);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst()); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
    @Override
    public void mapChanged(WorldMap worldMap, String message){
        Platform.runLater(()->{
            drawMap();
            movesDescriptionLabel.setText(message);
        });

    }

    @FXML
    private void onSimulationStartClicked() {
        String moves = movesTextField.getText();
        List<MoveDirection> directions = OptionsParser.parse(moves.split(" "));
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
        AbstractWorldMap simulationMap = new GrassField((10));
        Simulation simulation = new Simulation(positions, directions, simulationMap);
        this.setWorldMap(simulationMap);
        simulationMap.addObserver(this);
        simulationMap.addObserver((worldMap, message)->{
            LocalDateTime currentTime = LocalDateTime.now();
            System.out.println(currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s")) + ' ' + message);
        });
        simulationMap.addObserver(new FileMapDisplay());
        infoLabel.setManaged(false);
        infoLabel.setVisible(false);
        movesDescriptionLabel.setText("Simulation started with params: " + moves);
        Thread simulationThread = new Thread(simulation);
        simulationThread.start();
        new Thread(() -> {
            try {
                while (simulationThread.isAlive()) {
                    Thread.sleep(1000);
                }
                Platform.runLater(() -> {
                    movesDescriptionLabel.setText("Simulation finished.");
                    startButton.setDisable(false);
                });
            } catch (InterruptedException e) {
                throw(new RuntimeException(e));
            }
        }).start();
        startButton.setDisable(true);
    }
}
