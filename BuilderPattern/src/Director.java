public class Director {

    public void construct(IBuilder builder) {
        builder.buildFeatureA();
        builder.buildFeatureB();
        builder.buildFeatureC();
    }
}
