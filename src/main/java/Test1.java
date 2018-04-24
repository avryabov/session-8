import java.util.concurrent.atomic.AtomicInteger;

public class Test1 extends AbstractTest {
    private static final AtomicInteger val = new AtomicInteger();

    public static void main(String... args) throws InterruptedException {
        for (int i = 0; i < 10_000; i++) {
            assert size() == 0;

            Thread th1 = th();
            Thread th2 = th();
            Thread th3 = th();

            th1.start();
            th2.start();
            th3.start();

            th1.join();
            th2.join();
            th3.join();

            assert size() == 3;
            assert containsKey(1);
            assert containsKey(2);
            assert containsKey(3);
            assert contains(th1);
            assert contains(th2);
            assert contains(th3);

            clear();

            System.out.println("Checked " + i);
        }
    }

    private static Thread th() {
        return new Thread(() -> {
            // Правки можно внисить от этой линии
            val.compareAndSet(0, 1);
            put(val.getAndIncrement());
            val.compareAndSet(4, 1);
            // До этой
        });
    }

}
