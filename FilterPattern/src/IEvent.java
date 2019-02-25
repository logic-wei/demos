public interface IEvent {

    int TYPE_MOUSE = 0;
    int TYPE_KEYBOARD = 1;
    int TYPE_HOTPLUG = 2;

    int getType();
}
