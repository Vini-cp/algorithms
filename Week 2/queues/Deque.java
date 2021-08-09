import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Deque first;
    private Deque last;
    private int size = 0;

    private class Deque {
        Item item;
        Deque next;
        Deque before;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        Deque oldFirst = first;
        first = new Deque();
        first.item = item;
        first.next = oldFirst;
        first.before = null;
        oldFirst.before = first;
        if (oldFirst.next == null)
            last = oldFirst;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        Deque oldLast = last;
        last = new Deque();
        last.item = item;
        last.next = null;
        last.before = oldLast;
        oldLast.next = last;
        if (oldLast.before == null)
            first = oldLast;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        Item item = first.item;
        first = first.next;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        Item item = last.item;
        last = last.before;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {

    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}
