package Problem2;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiFunction;

/**
 */
public class MetricManagerImpl implements MetricManager {

    private ConcurrentHashMap<String, Double> metricStore = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, LinkedBlockingQueue> eventStore = new ConcurrentHashMap<>();

    public MetricManagerImpl() {
        new Thread(new CalcMetric()).start();
    }

    @Override
    public void addEvent(Event event) {
        if (eventStore.putIfAbsent(event.getType(), new LinkedBlockingQueue(Arrays.asList(Double.valueOf(event.getValue())))) != null) {
            eventStore.compute(event.getType(), new BiFunction<String, LinkedBlockingQueue, LinkedBlockingQueue>() {
                @Override
                public LinkedBlockingQueue apply(String s, LinkedBlockingQueue linkedBlockingQueue) {
                    try {
                        linkedBlockingQueue.put(event.getValue());
                    } catch (InterruptedException e) {
                        new RuntimeException(e);
                    }
                    return linkedBlockingQueue;
                }
            });
        }
    }

    @Override
    public Double getMetric(String type) {
        return metricStore.get(type);
    }

    private class CalcMetric implements Runnable {
        @Override
        public void run() {
            while (true) {
                for (Map.Entry<String, LinkedBlockingQueue> entry : eventStore.entrySet()) {
                    metricStore.put(entry.getKey(), calcMetric(entry.getValue()));
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    new RuntimeException(e);
                }
            }
        }
    }

    private Double calcMetric(LinkedBlockingQueue<Double> queue) {
        double sum = 0;
        int cnt = 0;
        for (Double value : queue) {
            sum = sum + value;
            cnt++;
        }
        return sum / cnt;
    }

}
