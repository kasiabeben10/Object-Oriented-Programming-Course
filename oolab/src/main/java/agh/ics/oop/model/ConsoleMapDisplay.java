package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{
    private int changesCounter = 0;

    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message){
        System.out.println("Change number " + (++changesCounter)+ " - "+ message);
        System.out.println("MapId: " + worldMap.getId());
        System.out.println(worldMap);
        System.out.println("--------------------------------------------");
    }
}
