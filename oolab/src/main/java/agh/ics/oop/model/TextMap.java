package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class TextMap implements WorldNumberPositionMap<String, Integer>{
    private final List<String> strings = new ArrayList<>();
    public boolean place(String string){
        strings.add(string);
        return true;
    }

    public List<String> getStrings() {
        return strings;
    }

    @Override
    public boolean canMoveTo(Integer position) {
        return position>=0 && position<strings.size();
    }

    @Override
    public void move(String string, MoveDirection direction) {
        Integer nextPos = switch (direction){
            case FORWARD, RIGHT -> strings.indexOf(string)+1;
            case BACKWARD, LEFT -> strings.indexOf(string)-1;
        };
        if(canMoveTo(nextPos)){
            String neighbour = objectAt(nextPos);
            strings.set(strings.indexOf(string),neighbour);
            strings.set(nextPos,string);
        }
    }

    @Override
    public String objectAt(Integer position) {
        return strings.get(position);
    }

    @Override
    public boolean isOccupied(Integer position) {
        return objectAt(position)!=null;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i=0; i<strings.size(); i++) {
            stringBuilder.append("\"").append(strings.get(i)).append("\"");
            if (i < strings.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
