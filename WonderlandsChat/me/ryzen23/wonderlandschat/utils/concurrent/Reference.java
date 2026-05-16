package me.ryzen23.wonderlandschat.utils.concurrent;

public class Reference<T> {
    private T value;

    public Reference(T value) {
        this.value = value;
    }

    public Reference() {
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }

    public String toString() {
        return this.value.toString();
    }
}
