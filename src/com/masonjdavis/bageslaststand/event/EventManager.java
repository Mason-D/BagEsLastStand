package com.masonjdavis.bageslaststand.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class EventManager {

    private List<Object> listeners;

    public EventManager() {
        this.listeners = new ArrayList<>();
    }

    public void registerListener(Object listener) {
        this.listeners.add(listener);
    }

    public void unregisterListener(Object listener) {
        this.listeners.remove(listener);
    }

    public void dispatchEvent(Event event) {
        this.listeners.stream().forEach(listener -> this.callEvent(listener, event));
    }

    private void callEvent(Object listener, Event event) {
        List<Method> handlers = findHandlers(listener, event.getName());
        handlers.stream().forEach(method -> {
            try {
                method.setAccessible(true);

                if (method.getParameterTypes().length == 0)
                    method.invoke(listener);
                if (method.getParameterTypes().length == 1)
                    method.invoke(listener, event);
                if (method.getParameterTypes().length == 2)
                    method.invoke(listener, this, event);
            } catch (Exception e) {
                System.err.println("Could not invoke event handler!");
                e.printStackTrace(System.err);
            }
        });
    }

    private List<Method> findHandlers(Object listener, String eventName) {
        Method[] methods = listener.getClass().getMethods();
        return Arrays.asList(methods).stream().filter(method -> canHandleEvent(method, eventName)).collect(toList());
    }

    private boolean canHandleEvent(Method method, String eventName) {
        EventHandler eventAnnotation = method.getAnnotation(EventHandler.class);
        if (eventAnnotation != null) {
            String[] events = eventAnnotation.value();
            return Arrays.asList(events).contains(eventName);
        }
        return false;
    }
}
