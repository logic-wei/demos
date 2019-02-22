public class PrototypeImplA extends Prototype {

    private String mName = "default";

    public PrototypeImplA(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
