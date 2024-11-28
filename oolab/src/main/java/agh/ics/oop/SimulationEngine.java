package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulationsList;
    private final List<Thread> simulationsThreads = new ArrayList<>();
    private ExecutorService executorService;

    public SimulationEngine(List<Simulation> simulationsList){
        this.simulationsList = simulationsList;
    }

    public void runSync(){
        for (Simulation simulation : simulationsList){
            simulation.run();
        }
    }

    public void runAsync(){
        for (Simulation simulation: simulationsList){
            Thread thread = new Thread(simulation);
            simulationsThreads.add(thread);
            thread.start();
        }
        try {
            awaitSimulationsEnd();
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    public void runAsyncInThreadPool(){
        executorService = Executors.newFixedThreadPool(8);
        for (Simulation simulation : simulationsList){
            executorService.submit(simulation);
        }
        executorService.shutdown();
        try {
            awaitSimulationsEnd();
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException {
        if (!simulationsThreads.isEmpty()) {
            for (Thread thread : simulationsThreads) {
                thread.join();
            }
        }
        if (executorService != null && !executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
    }

}


