import java.util.Iterator;

/**
 * Created by lb_k on 2/8/14.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N, size;

    private void resize(int capacity) {
        size = capacity;
        Item[] b = (Item[]) new Object[size];
        for (int i = 0; i < N; i++)
            b[i] = a[i];
        a = b;
    }

    public RandomizedQueue() { N = 0; resize(1); }

    public boolean isEmpty() { return N == 0; }
    public int size() { return N; }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if ( N == size )
            resize(size * 2);
        a[N++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int n = (int) (Math.random() * N);
        Item item = a[n];
        a[n] = a[N-1];
        a[N-1] = null;
        N--;
        if (N < size/4)
            resize(size / 4);

        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        return a[((int) (N * Math.random()))];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>(a);
    }

    private class RandomizedQueueIterator<Item> implements Iterator {
     	private Item[] queueCopy;
        private int iteratorIndex = 0;
        
        public RandomizedQueueIterator(Item[] a) {
                queueCopy = (Item[]) new Object[size];
                
                for (int i = 0; i < N; i++)
                        queueCopy[i] = a[i];

                for (int x = 1; x < N; x++) {
                        int swapIndex = StdRandom.uniform(x);
                        
                        Item temp = queueCopy[x];
                        queueCopy[x] = queueCopy[swapIndex];
                        queueCopy[swapIndex] = temp;
                }
        }
        
        public boolean hasNext() {
                return iteratorIndex < N;
        }

        public Item next() {
                if (iteratorIndex == N)
                        throw new java.util.NoSuchElementException();
                
                return queueCopy[iteratorIndex++];
        }

        public void remove() {
                throw new java.lang.UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(10);
        rq.enqueue(20);
        rq.enqueue(30);

        Iterator i1 = rq.iterator(), i2 = rq.iterator();

        while (i1.hasNext()) 
        	StdOut.println(i1.next());

        while (i2.hasNext())
        	StdOut.println(i2.next());

        StdOut.println();

        for (Integer i : rq)
            StdOut.println(i+" ");
    }
}
