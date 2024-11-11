package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation<T,P> {
    private List<T> mapObjects;
    private List<MoveDirection> directions;
    private final WorldMap<T,P> map;

    public Simulation(List<T> mapObjects, List<MoveDirection> directions, WorldMap<T,P> map){
        this.directions = directions;
        this.mapObjects = new ArrayList<>();
        this.map = map;
        for(int i=0; i<mapObjects.size();i++){
            if(map.place(mapObjects.get(i))){
                this.mapObjects.add(mapObjects.get(i));
            };
        }
    }

    public List<T> getMapObjects() {
        return mapObjects;
    }

    public List<MoveDirection> getDirections() {
        return directions;
    }

    public void run(){
        if (mapObjects.isEmpty()){
            return;
        }
        for (int i=0; i<directions.size();i++){
            int object_no = i % mapObjects.size();
            map.move(mapObjects.get(object_no),directions.get(i));
            System.out.println(map);
        }
    }
}
