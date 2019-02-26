public class Handler2 extends MessageHandler {

    @Override
    public boolean onHandlerMessage(String msg) {
        if (msg.matches("msg2:.*")) {
            System.out.println("handler2:"+msg);
            return true;
        }
        return false;
    }
}
