package Problem2;

/**
 */
public class Event {
    private String type;
    private double value;

    public Event(String type, double value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
