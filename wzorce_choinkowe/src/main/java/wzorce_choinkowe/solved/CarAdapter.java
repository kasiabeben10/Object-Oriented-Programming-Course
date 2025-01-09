package wzorce_choinkowe.solved;

public class CarAdapter implements NewCar {
    private final OldCar oldCar;

    public CarAdapter(OldCar oldCar) {
        this.oldCar = oldCar;
    }

    @Override
    public void drive() {
        oldCar.driveManual();
    }
}
