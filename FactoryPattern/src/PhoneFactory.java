public class PhoneFactory {

    public static final String BRAND_XIAOMI = "xiaomi";
    public static final String BRAND_HUAWEI = "huawei";
    public static final String BRAND_OV = "ov";
    public static final String BRAND_MEIZU = "meizu";

    static public Phone makePhone(String brand) {
        if (brand == null)
            return null;

        if (brand.equals(BRAND_XIAOMI)) {
            return new Xiaomi();
        } else if (brand.equals(BRAND_HUAWEI)) {
            return new Huawei();
        } else if (brand.equals(BRAND_MEIZU)) {
            return new Meizu();
        } else if (brand.equals(BRAND_OV)) {
            return new OV();
        }

        return null;
    }
}
