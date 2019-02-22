public class HardwareDriver implements IAdapter {

    private Hardware mHardware;

    public HardwareDriver(Hardware hardware) {
        mHardware = hardware;
    }

    @Override
    public void osOperation() {
        mHardware.busOperation();
    }
}
