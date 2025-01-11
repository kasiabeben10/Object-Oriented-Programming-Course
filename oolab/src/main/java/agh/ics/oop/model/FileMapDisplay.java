package agh.ics.oop.model;

import java.io.FileWriter;
import java.io.IOException;

public class FileMapDisplay implements MapChangeListener {

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        try (FileWriter writer = new FileWriter("map_id.log",true)) {
            writer.write(message + System.lineSeparator());
            writer.write(worldMap.toString());
        } catch (IOException e){
            e.printStackTrace();
            System.err.println("Failed to write to file: " + e.getMessage());
        }
    }
}
