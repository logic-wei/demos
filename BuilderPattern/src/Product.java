import java.util.HashMap;

public class Product {

    private HashMap<String, String> mFeatures = new HashMap<>();

    public void addFeature(String feature, String value) {
        mFeatures.put(feature, value);
    }

    public void show() {
        for (String feature: mFeatures.keySet()) {
            System.out.println(feature + ":" + mFeatures.get(feature));
        }
    }
}
