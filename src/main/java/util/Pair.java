package util;

public class Pair<T, K> {
    private T first;
    private K second;

    public Pair(T first, K second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public K getSecond() {
        return second;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(K second) {
        this.second = second;
    }

    public static <T, K> Pair<T, K> of(T first, K second) {
        return new Pair<>(first, second);
    }
}
