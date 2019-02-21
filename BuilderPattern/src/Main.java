public class Main {

    /**
     * 建造者模式优势在于可以提取公共的构造逻辑到Director中，对于不同的feature由Builder来设置。
     * 就像去饭馆吃饭，我们不用知道厨师（Director）是怎么炒菜的，只用提交一份菜单（Builder）给厨师就可以了
     * */
    public static void main(String[] args) {
        Director director = new Director();
        IBuilder builder;
        Product product;

        builder = new BuilderImplA();
        director.construct(builder);
        product = builder.getProduct();
        product.show();

        builder = new BuilderImplB();
        director.construct(builder);
        product = builder.getProduct();
        product.show();
    }
}
