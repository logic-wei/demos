public class Handler3 extends MessageHandler {

    @Override
    public boolean onHandlerMessage(String msg) {
        if (msg.matches("msg3:.*")) {
            System.out.println("handler3:"+msg);
            return true;
        }
        return false;
    }
}
