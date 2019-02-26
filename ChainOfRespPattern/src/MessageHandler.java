public abstract class MessageHandler {

    private MessageHandler mNext;

    public void setNext(MessageHandler next) {
        mNext = next;
    }

    protected void handleMessage(String msg) {
        if (!onHandlerMessage(msg) && mNext != null)
            mNext.handleMessage(msg);
    }

    /**
     * 返回true对消息进行拦截
     */
    public abstract boolean onHandlerMessage(String msg);
}
