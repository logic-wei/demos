public class KeyBoardEvent implements IEvent {

    private int mKeyCode = 0;

    public KeyBoardEvent(int keycode) {
        mKeyCode = keycode;
    }

    @Override
    public int getType() {
        return TYPE_KEYBOARD;
    }

    public int getKeyCode() {
        return mKeyCode;
    }
}
