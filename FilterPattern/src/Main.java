import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<IEvent> src = new ArrayList<>();
        List<IEvent> ret;

        src.add(new MouseEvent(0, true));
        src.add(new KeyBoardEvent(1));
        src.add(new HotPlugEvent("new dev"));
        src.add(new MouseEvent(0, false));
        src.add(new KeyBoardEvent(2));
        src.add(new HotPlugEvent("new dev2"));
        src.add(new MouseEvent(1, true));
        src.add(new KeyBoardEvent(4));
        src.add(new HotPlugEvent("new dev3"));

        ret = new MouseFilter().filter(src);

        for (IEvent e: ret) {
            MouseEvent mouseEvent = (MouseEvent) e;
            System.out.println("mouse event: keyCode="+mouseEvent.getKeyCode()+" doubleClick="+mouseEvent.getDoubleClick());
        }
    }
}
