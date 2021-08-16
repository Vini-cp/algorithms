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
        } else
            last = first;
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
        } else
            first = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        removeCheck();
        Item item = first.item;
        if (first == last) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.before = null;
        }
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        removeCheck();
        Item item = last.item;
        if (first == last) {
            first = null;
            last = null;
        } else {
            last = last.before;
            last.next = null;
        }
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
            throw new UnsupportedOperationException("Invalid operation for Dequeues.");
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

        StdOut.println("Test isEmpty function");
        StdOut.println("Expected: true");
        StdOut.println("Received: " + str.isEmpty());
        StdOut.println();

        StdOut.println("Test addLast function");
        StdOut.println("Adding Test0 to the end of the queue");
        str.addLast("Test0");
        StdOut.println();

        StdOut.println("Test isEmpty function");
        StdOut.println("Expected: false");
        StdOut.println("Received: " + str.isEmpty());
        StdOut.println();

        StdOut.println("Test size function");
        StdOut.println("Expected: 1");
        StdOut.println("Received: " + str.size());
        StdOut.println();

        StdOut.println("Test addFirst function");
        StdOut.println("Adding Test1 & Test2 to the beginning of the queue");
        str.addFirst("Test1");
        str.addFirst("Test2");
        StdOut.println();

        StdOut.println("Test isEmpty function");
        StdOut.println("Expected: false");
        StdOut.println("Received: " + str.isEmpty());
        StdOut.println();

        StdOut.println("Test size function");
        StdOut.println("Expected: 3");
        StdOut.println("Received: " + str.size());
        StdOut.println();

        StdOut.println("Test addFirst function");
        StdOut.println("Adding Test3 to the beginning of the queue");
        str.addFirst("Test3");
        StdOut.println();

        StdOut.println("Test isEmpty function");
        StdOut.println("Expected: false");
        StdOut.println("Received: " + str.isEmpty());
        StdOut.println();

        StdOut.println("Test size function");
        StdOut.println("Expected: 4");
        StdOut.println("Received: " + str.size());
        StdOut.println();

        StdOut.println("Test iterator hasNext and next functions");
        StdOut.println("Expected: Test3 Test2 Test1 Test0");
        StdOut.print("Received: ");
        Iterator<String> it = str.iterator();
        while (it.hasNext()) {
            StdOut.print(it.next() + " ");
        }
        StdOut.println();
        StdOut.println();

        StdOut.println("Test removeFirst function");
        StdOut.println("Removing Test3 of the beginning of the queue");
        StdOut.println(str.removeFirst());
        StdOut.println();

        StdOut.println("Test isEmpty function");
        StdOut.println("Expected: false");
        StdOut.println("Received: " + str.isEmpty());
        StdOut.println();

        StdOut.println("Test size function");
        StdOut.println("Expected: 3");
        StdOut.println("Received: " + str.size());
        StdOut.println();

        StdOut.println("Test if all items that should be in the list are there yet");
        StdOut.println("Expected: Test2 Test1 Test0");
        StdOut.print("Received: ");
        Iterator<String> it1 = str.iterator();
        while (it1.hasNext()) {
            StdOut.print(it1.next() + " ");
        }
        StdOut.println();
        StdOut.println();

        StdOut.println("Test removeLast function");
        StdOut.println("Removing Test0 of the end of the queue");
        StdOut.println(str.removeLast());
        StdOut.println();

        StdOut.println("Test isEmpty function");
        StdOut.println("Expected: false");
        StdOut.println("Received: " + str.isEmpty());
        StdOut.println();

        StdOut.println("Test size function");
        StdOut.println("Expected: 2");
        StdOut.println("Received: " + str.size());
        StdOut.println();

        StdOut.println("Test if all items that should be in the list are there yet");
        StdOut.println("Expected: Test2 Test1");
        StdOut.print("Received: ");
        Iterator<String> it2 = str.iterator();
        while (it2.hasNext()) {
            StdOut.print(it2.next() + " ");
        }
        StdOut.println();
        StdOut.println();

        StdOut.println("Test removeFirst function");
        StdOut.println("Removing Test2 of the end of the queue");
        StdOut.println(str.removeFirst());
        StdOut.println();

        StdOut.println("Test isEmpty function");
        StdOut.println("Expected: false");
        StdOut.println("Received: " + str.isEmpty());
        StdOut.println();

        StdOut.println("Test size function");
        StdOut.println("Expected: 1");
        StdOut.println("Received: " + str.size());
        StdOut.println();

        StdOut.println("Test if all items that should be in the list are there yet");
        StdOut.println("Expected: Test1");
        StdOut.print("Received: ");
        Iterator<String> it3 = str.iterator();
        while (it3.hasNext()) {
            StdOut.print(it3.next() + " ");
        }
        StdOut.println();
        StdOut.println();

        StdOut.println("Test removeLast function");
        StdOut.println("Removing Test1 of the end of the queue");
        StdOut.println(str.removeLast());
        StdOut.println();

        StdOut.println("Test isEmpty function");
        StdOut.println("Expected: true");
        StdOut.println("Received: " + str.isEmpty());
        StdOut.println();

        StdOut.println("Test size function");
        StdOut.println("Expected: 0");
        StdOut.println("Received: " + str.size());
        StdOut.println();

        StdOut.println("Test if all items that should be in the list are there yet");
        StdOut.println("Expected: ");
        StdOut.print("Received: ");
        Iterator<String> it4 = str.iterator();
        while (it4.hasNext()) {
            StdOut.print(it4.next() + " ");
        }
        StdOut.println();
        StdOut.println();
    }
}
