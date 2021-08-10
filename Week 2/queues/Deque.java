import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node before;
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
        addCheck(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.before = null;
        if (oldFirst != null) {
            oldFirst.before = first;
            if (oldFirst.next == null)
                last = oldFirst;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        addCheck(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.before = oldLast;
        if (oldLast != null) {
            oldLast.next = last;
            if (oldLast.before == null)
                first = oldLast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        removeCheck();
        Item item = first.item;
        first = first.next;
        first.before = null;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        removeCheck();
        Item item = last.item;
        last = last.before;
        last.next = null;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private void addCheck(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Argument could not be null.");
    }

    private void removeCheck() {
        if (isEmpty())
            throw new NoSuchElementException("There is no element to remove.");
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Invalid operation for Deques.");
        }

        public Item next() {
            if (current == null)
                throw new NoSuchElementException("There is no more elements.");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> str = new Deque<String>();
        StdOut.println(str.isEmpty());
        // str.removeFirst();
        str.addFirst("Test1");
        str.addFirst("Test2");
        StdOut.println(str.size());
        str.addFirst("Test3");
        // str.addLast(null);
        str.addLast("Test0");
        StdOut.println(str.isEmpty());
        StdOut.println(str.size());

        StdOut.println();
        Iterator<String> it = str.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }

        StdOut.println();
        StdOut.println(str.removeFirst());
        StdOut.println(str.size());
        StdOut.println(str.removeLast());
        StdOut.println(str.size());
        StdOut.println();

        it = str.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }
    }
}
