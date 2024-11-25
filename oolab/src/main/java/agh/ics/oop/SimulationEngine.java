package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final List<Simulation> simulationList;
    private final List<Thread> simulationsThreads = new ArrayList<>();
    public SimulationEngine(List<Simulation> simulationsList){
        this.simulationList = simulationsList;
    }

    public void runSync(){
        for (Simulation simulation : simulationList){
            simulation.run();
        }
    }
    public void runAsync(){
        for (Simulation simulation: simulationList){
            Thread thread = new Thread(simulation);
            simulationsThreads.add(thread);
            thread.start();
        }
        awaitSimulationsEnd();
    }
    public void awaitSimulationsEnd(){
        for (Thread thread: simulationsThreads){
            try{
                thread.join();
            }
            catch (InterruptedException e){
                System.out.println("Exception: "+ e.getMessage());
            }
        }
    }
}
