import java.util.Hashtable;

public class PrototypeCache {

    public static final String PROTOTYPE_IMPL_A = "prototype_impl_a";

    private static Hashtable<String, Prototype> mPrototypeMap = new Hashtable<>();

    public static void loadCache() {
        mPrototypeMap.put(PROTOTYPE_IMPL_A, new PrototypeImplA("default"));
    }

    public static Prototype getPrototype(String prototype) {
        return (Prototype) mPrototypeMap.get(prototype).clone();
    }
}
