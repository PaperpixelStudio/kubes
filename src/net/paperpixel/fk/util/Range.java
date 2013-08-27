package net.paperpixel.fk.util;


public class Range<T> {
    private T min;
    private T max;

    public Range(T min, T max) {
        this.max = max;
        this.min = min;
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public T getMax() {
        return max;
    }
}
