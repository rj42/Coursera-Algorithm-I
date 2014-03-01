import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private static class Node<Item> {
        private Item item;
        private Node<Item> next, last;
    }

    private Node<Item> first, last;
    private int N = 0;

    public Deque() { 
    	first = null; 
    	last = null; 
    	N = 0;
    }

    public boolean isEmpty() {
        return first == null;
    };

    public int size() {return N;};
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node<Item> node = new Node<Item>();
        node.item = item;
        node.next = first;
        if (first != null)
            first.last = node;
        else
            last = node;
        first = node;
        N++;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node<Item> node = new Node<Item>();
        node.item = item;
        node.last = last;
        if (last != null)
            last.next = node;
        else
            first = node;
        last = node;
        N++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (first != null) {
        	first.last.next = null;
            first.last = null;
        }
        else
            last = null;
        N--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = last.item;
        last = last.last;
        if (last != null) {
        	last.next.last = null;
            last.next = null;
        }
        else
            first = null;
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(first);
    };

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
            current = first;
        }

        public void remove()     { throw new UnsupportedOperationException(); };
        public boolean hasNext() { return current != null; };
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("No next");
            Item item = current.item;
            current = current.next;
            return item;
        };
    }

    public static void main(String[] args) {
        Deque<Integer> deq = new Deque<Integer>();
        deq.addFirst(10);
        deq.addLast(20);
        deq.addFirst(30);
        deq.addLast(40);
        deq.removeFirst();
        deq.removeFirst();
        //deq.removeFirst();
        deq.removeLast();
        StdOut.println(deq.size());

        for (int a : deq)
            StdOut.print(a+" ");
    }
}
