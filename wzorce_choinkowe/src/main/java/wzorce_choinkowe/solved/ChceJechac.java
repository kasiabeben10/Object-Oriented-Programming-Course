package wzorce_choinkowe.solved;

public class ChceJechac {
    public static void main(String[] args) {
        OldCar manualCar = new ManualCar();
        NewCar adaptedCar = new CarAdapter(manualCar);
        Driver driver = new Driver();
//        System.out.println("Driver nie umie jeszcze jeździć manualem!");
        System.out.println("Driver: Jazda z zaadaptowanym samochodem");
//        driver.drive(manualCar);
        driver.drive(adaptedCar);
    }
}
