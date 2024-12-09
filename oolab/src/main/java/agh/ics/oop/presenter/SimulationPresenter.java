package agh.ics.oop.presenter;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SimulationPresenter implements MapChangeListener {
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
        drawMap();
    }


}
