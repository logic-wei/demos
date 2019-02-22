public class Main {

    public static void main(String[] args) {
        PrototypeImplA prototypeImplA;

        PrototypeCache.loadCache();
        prototypeImplA = (PrototypeImplA) PrototypeCache.getPrototype(PrototypeCache.PROTOTYPE_IMPL_A);

        System.out.println(prototypeImplA.getName());
    }
}
