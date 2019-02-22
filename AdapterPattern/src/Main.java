public class Main {

    /**
     * 操作系统的驱动框架完全符合适配器的例子，规定操作系统需要的接口，让所有不同功能的硬件来实现这套接口，就实现了硬件的适配
     */
    public static void main(String[] args) {
        HardwareDriver driver = new HardwareDriver(new Hardware());

        driver.osOperation();
    }
}
