package Problem2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertTrue;

/**
 */
public class MetricManagerImplTest {

    private final static int COUNT = 30;

    CountDownLatch countDownLatch = new CountDownLatch(COUNT);
    MetricManagerImpl metricManager = new MetricManagerImpl();
    ExecutorService executorService = Executors.newFixedThreadPool(COUNT);

    @Test
    public void Test() throws InterruptedException {

        List<Double> dataListOne = new ArrayList(Arrays.asList(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0));
        List<Double> dataListTwo = new ArrayList(Arrays.asList(0.0, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8, 9.9));
        List<Double> dataListThree = new ArrayList(Arrays.asList(0.12, 1.23, 2.34, 3.45, 4.56, 5.67, 6.78, 7.89, 8.91, 9.101));

        double metricOne = createEvents("One", dataListOne);
        double metricTwo = createEvents("Two", dataListTwo);
        double metricTree = createEvents("Three", dataListThree);


        Thread.sleep(5000);

        System.out.println(metricManager.getMetric("One"));
        System.out.println(metricManager.getMetric("Two"));
        System.out.println(metricManager.getMetric("Three"));

        assertTrue(Double.compare(metricOne, metricManager.getMetric("One")) == 0);
        assertTrue(Double.compare(metricTwo, metricManager.getMetric("Two")) == 0);
        assertTrue(Double.compare(metricTree, metricManager.getMetric("Three")) == 0);
    }

    private double createEvents(String type, List<Double> list) {
        double metric = 0;
        for (Double d : list) {
            executorService.submit(new EventThread(new Event(type, d)));
            metric += d;
        }
        return metric / list.size();
    }

    private class EventThread extends Thread {
        Event event;

        public EventThread(Event event) {
            this.event = event;
        }

        @Override
        public void run() {
            try {
                countDownLatch.countDown();
                countDownLatch.await();
                metricManager.addEvent(event);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
