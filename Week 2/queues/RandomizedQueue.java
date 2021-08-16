import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        addCheck(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        removeCheck();
        int element = StdRandom.uniform(size);
        if (element == 0)
            return removeFirst();
        else if (element == (size - 1))
            return removeLast();
        else
            return removeMiddle(element);
    }

    // return a random item (but do not remove it)
    public Item sample() {
        removeCheck();
        Node node = first;
        Item item;
        int element = StdRandom.uniform(size);
        while (element > 0) {
            first = first.next;
            element--;
        }
        item = first.item;
        first = node;
        return item;
    }

    // return an independent iterator over items in random order
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

    private Item removeFirst() {
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    private Item removeLast() {
        Node node = first;
        Item item;
        int i = 1;
        while (i < size - 1) {
            node = node.next;
            i++;
        }
        item = node.next.item;
        node.next = null;
        size--;
        return item;
    }

    private Item removeMiddle(int element) {
        Node node = first;
        Item item;
        int i = 1;
        while (i <= element - 1) {
            node = node.next;
            i++;
        }
        item = node.next.item;
        node.next = node.next.next;
        size--;
        return item;
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
        RandomizedQueue<String> str = new RandomizedQueue<String>();
        StdOut.println("Test isEmpty function");
        StdOut.println("Expected: true");
        StdOut.println("Received: " + str.isEmpty());
        StdOut.println();

        StdOut.println("Add two members to the queue 0 & 1");
        str.enqueue("Test0");
        str.enqueue("Test1");
        StdOut.println();

        StdOut.println("Test size function");
        StdOut.println("Expected: 2");
        StdOut.println("Received: " + str.size());
        StdOut.println();

        StdOut.println("Add two members to the queue 2 & 3");
        str.enqueue("Test2");
        str.enqueue("Test3");
        StdOut.println();

        StdOut.println("Test isEmpty function");
        StdOut.println("Expected: false");
        StdOut.println("Received: " + str.isEmpty());
        StdOut.println();

        StdOut.println("Test size function");
        StdOut.println("Expected: 4");
        StdOut.println("Received: " + str.size());
        StdOut.println();

        String expItems = "Test3 Test2 Test1 Test0";
        StdOut.println("Test iterator hasNext and next functions");
        StdOut.println("Expected: " + expItems);
        StdOut.print("Received: ");
        Iterator<String> it = str.iterator();
        while (it.hasNext()) {
            StdOut.print(it.next() + " ");
        }
        StdOut.println();
        StdOut.println();

        StdOut.println("Test sample function: return a random item, but do not remove it");
        StdOut.print(str.sample() + " ");
        StdOut.print(str.sample() + " ");
        StdOut.print(str.sample() + " ");
        StdOut.print(str.sample() + " ");
        StdOut.print(str.sample() + " ");
        StdOut.println(str.sample());
        StdOut.println();

        StdOut.println("Test size function");
        StdOut.println("Expected: 4");
        StdOut.println("Received: " + str.size());
        StdOut.println();

        StdOut.println("Test if all items are in the list yet");
        StdOut.println("Expected: " + expItems);
        StdOut.print("Received: ");
        Iterator<String> it1 = str.iterator();
        while (it1.hasNext()) {
            StdOut.print(it1.next() + " ");
        }
        StdOut.println();
        StdOut.println();

        StdOut.println("Test dequeue function: return a random item, and remove it");
        StdOut.println("2 Items removed:");
        StdOut.println(str.dequeue());
        StdOut.println(str.dequeue());
        StdOut.println();

        StdOut.println("Test if all items that should be in the list are there yet");
        StdOut.println("Expected: " + expItems + " - items excluded above");
        StdOut.print("Received: ");
        Iterator<String> it2 = str.iterator();
        while (it2.hasNext()) {
            StdOut.print(it2.next() + " ");
        }
        StdOut.println();
        StdOut.println();

        StdOut.println("Test size function");
        StdOut.println("Expected: 2");
        StdOut.println("Received: " + str.size());
        StdOut.println();

        StdOut.println("Test dequeue function: return a random item, and remove it");
        StdOut.println("1 Item removed:");
        StdOut.println(str.dequeue());
        StdOut.println();

        StdOut.println("Test if all items that should be in the list are there yet");
        StdOut.println("Expected: " + expItems + " - items excluded above");
        StdOut.print("Received: ");
        Iterator<String> it3 = str.iterator();
        while (it3.hasNext()) {
            StdOut.print(it3.next() + " ");
        }
        StdOut.println();
        StdOut.println();

        StdOut.println("Test size function");
        StdOut.println("Expected: 1");
        StdOut.println("Received: " + str.size());
        StdOut.println();

        StdOut.println("Test dequeue function: return a random item, and remove it");
        StdOut.println("1 Item removed:");
        StdOut.println(str.dequeue());
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

        StdOut.println("Test size function");
        StdOut.println("Expected: 0");
        StdOut.println("Received: " + str.size());
        StdOut.println();

    }
}
