package utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ElementList<T> extends HashSet<T> {


    public ElementList() {
        super();
    }

    public ElementList (List<T> list)
    {
        super();
        addAll(new HashSet<T> (list));
    }

    public ElementList(Collection<? extends T> c) {
        super(c);
    }

    public static <T> ElementList<T> from (T element)
    {
        ElementList<T> list = new ElementList<T>();
        if (element != null) {
            list.add(element);
        }
        return list ;
    }

    public static <T> ElementList<T> from (List<T> elements)
    {
        ElementList<T> list = new ElementList<T>();
        if (elements != null) {
            list.addAll(elements);
        }
        return list ;
    }

    public static <T> ElementList<T> from (Set<T> elements)
    {
        ElementList<T> list = new ElementList<T>();
        if (elements != null) {
            list.addAll(elements);
        }
        return list ;
    }

    public static <T> ElementList<T> from (Stream<T> elements)
    {
        return new ElementList<T>().join(elements);
    }

    public static <T> ElementList<T> from(T[] elements) {
        ElementList<T> list = new ElementList<T>();
        if (elements != null) {
            for (T element : elements) {
                list.add(element);
            }
        }
        return list;
    }

    public ElementList<T> join(Stream<T> stream) {
        if (stream != null) {
            stream.forEach(this::add);
        }
        return this;
    }

    public ElementList<T> join(ElementList<T> elements) {
        if (elements != null) {
            elements.forEach(this::add);
        }
        return this;
    }

    public <B> ElementList<T> filter(Class<B> tType) {
        return ElementList.from(this.stream().filter(e -> e.getClass().isInstance(tType)));
    }
}
