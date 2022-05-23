package observer;


import events.EventInterface;

public interface Observer<E extends EventInterface> {
    void update(E e);
}