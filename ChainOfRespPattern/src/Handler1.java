public class Handler1 extends MessageHandler {

    @Override
    public boolean onHandlerMessage(String msg) {
        if (msg.matches("msg1:.*")) {
            System.out.println("handler1:"+msg);
            return true;
        }
        return false;
    }
}
