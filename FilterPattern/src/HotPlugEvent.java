public class HotPlugEvent implements IEvent {

    private String mDeviceName;

    public HotPlugEvent(String deviceName) {
        mDeviceName = deviceName;
    }

    @Override
    public int getType() {
        return TYPE_HOTPLUG;
    }

    public String getDeviceName() {
        return mDeviceName;
    }
}
