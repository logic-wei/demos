import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> msgList = new ArrayList<>();
        MessageHandler msgHandler1 = new Handler1();
        MessageHandler msgHandler2 = new Handler2();
        MessageHandler msgHandler3 = new Handler3();

        msgList.add("msg4:hello world!");
        msgList.add("msg3:nice to meet you!");
        msgList.add("msg2:how are you?");
        msgList.add("msg1:I'm fine.");

        // chain of handlers:h1->h2->h3
        msgHandler1.setNext(msgHandler2);
        msgHandler2.setNext(msgHandler3);

        for (String msg: msgList) {
            msgHandler1.handleMessage(msg);
        }

    }
}
