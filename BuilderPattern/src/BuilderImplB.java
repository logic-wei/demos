public class BuilderImplB implements IBuilder {

    Product mProduct = new Product();

    @Override
    public void buildFeatureA() {
        mProduct.addFeature("featureA", "aa");
    }

    @Override
    public void buildFeatureB() {
        mProduct.addFeature("featureB", "bb");
    }

    @Override
    public void buildFeatureC() {
        mProduct.addFeature("featureC", "cc");
    }

    @Override
    public Product getProduct() {
        return mProduct;
    }
}
