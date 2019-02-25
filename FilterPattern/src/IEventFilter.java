import java.util.List;

public interface IEventFilter {

    List<IEvent> filter(List<IEvent> src);
}
