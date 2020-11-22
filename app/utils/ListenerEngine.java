package utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ListenerEngine <T> {
    // Listeners
    @JsonIgnore
    private ElementList<T> listeners = new ElementList<T>();

    public ElementList<T> getListeners() {
        return listeners;
    }

// Listener
    /**
     * Add listener.
     *
     * @param listener the listener
     */
    public void addListener(T listener)
    {
        listeners.add(listener);
    }

    /**
     * Remove listener.
     *
     * @param listener the listener
     */
    public void removeListener(T listener)
    {
        listeners.remove(listener);
    }


}
