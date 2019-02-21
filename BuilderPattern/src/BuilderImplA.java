public class BuilderImplA implements IBuilder {

    Product mProduct = new Product();

    @Override
    public void buildFeatureA() {
        mProduct.addFeature("featureA", "a");
    }

    @Override
    public void buildFeatureB() {
        mProduct.addFeature("featureB", "b");
    }

    @Override
    public void buildFeatureC() {
        mProduct.addFeature("featureC", "c");
    }

    @Override
    public Product getProduct() {
        return mProduct;
    }
}
