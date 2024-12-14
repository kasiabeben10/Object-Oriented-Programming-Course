package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {

    @FXML
    private Label movesDescriptionLabel;
    @FXML
    private TextField movesTextField;
    private WorldMap map;

    @FXML
    private Label infoLabel;

    public void setMap(WorldMap map) {
        this.map = map;
    }

    public WorldMap getMap() {
        return map;
    }

    public void drawMap(){
        infoLabel.setText(map.toString());
    }

    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message){
        Platform.runLater(this::drawMap);
    }

    @FXML
    private void onSimulationStartClicked() {
        String moves = movesTextField.getText();
        List<MoveDirection> directions = OptionsParser.parse(moves.split(" "));
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
        AbstractWorldMap simulationMap = new GrassField((10));
        this.setMap(simulationMap);
        simulationMap.addObserver(this);
        Simulation simulation = new Simulation(positions, directions, simulationMap);
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        movesDescriptionLabel.setText("Simulation started with params: " + moves);
        Thread simulationThread = new Thread(engine::runAsync);
        simulationThread.start();


    }
}
