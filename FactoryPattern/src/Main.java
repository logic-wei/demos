import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> brandList = new ArrayList<>();

        brandList.add(PhoneFactory.BRAND_XIAOMI);
        brandList.add(PhoneFactory.BRAND_HUAWEI);
        brandList.add(PhoneFactory.BRAND_MEIZU);
        brandList.add(PhoneFactory.BRAND_OV);

        for (String brand: brandList) {
            Phone phone = PhoneFactory.makePhone(brand);

            System.out.println(brand + ":" + phone.getFeature());
        }
    }
}
