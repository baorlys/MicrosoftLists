package model.microsoft.list.value;

public interface IValue {
    Object get();

    void set(Object... object);
    boolean isMultiple();
}
