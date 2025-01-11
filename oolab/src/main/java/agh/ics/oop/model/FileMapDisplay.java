package agh.ics.oop.model;

import java.io.FileWriter;
import java.io.IOException;

public class FileMapDisplay implements MapChangeListener {

    @Override
    public void mapChanged(WorldMap worldMap, String message) {

        String fileName = "./simulation_logs/map_%s.log".formatted(worldMap.getId());

        try (FileWriter writer = new FileWriter(fileName,true)) {
            writer.write(message + System.lineSeparator());
            writer.write(worldMap.toString());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
