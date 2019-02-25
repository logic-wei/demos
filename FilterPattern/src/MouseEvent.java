public class MouseEvent implements IEvent {

    private int mKeyCode;
    private boolean mDoubleClick;

    public MouseEvent(int keyCode, boolean doubleClick) {
        mKeyCode = keyCode;
        mDoubleClick = doubleClick;
    }

    @Override
    public int getType() {
        return TYPE_MOUSE;
    }

    public int getKeyCode() {
        return mKeyCode;
    }

    public boolean getDoubleClick() {
        return mDoubleClick;
    }
}
