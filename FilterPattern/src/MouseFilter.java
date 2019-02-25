import java.util.ArrayList;
import java.util.List;

public class MouseFilter implements IEventFilter {

    @Override
    public List<IEvent> filter(List<IEvent> src) {
        List<IEvent> out = new ArrayList<>();

        for (IEvent e: src) {
            if (e.getType() == IEvent.TYPE_MOUSE) {
                out.add(e);
            }
        }

        return out;
    }
}
