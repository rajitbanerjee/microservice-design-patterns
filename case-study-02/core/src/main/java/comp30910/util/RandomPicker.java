package comp30910.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPicker {
    public static <T> T pickFromCollection(Collection<T> collection) {
        List<T> items = new ArrayList<>();
        items.addAll(collection);
        int size = items.size();
        int randomElementIndex = ThreadLocalRandom.current().nextInt(size) % size;
        return items.get(randomElementIndex);
    }
}
