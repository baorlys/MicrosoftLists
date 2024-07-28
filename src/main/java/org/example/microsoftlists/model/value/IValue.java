package org.example.microsoftlists.model.value;


public interface IValue {

    Object get();

    void set(Object... object);

    void set(IValue value);


}
