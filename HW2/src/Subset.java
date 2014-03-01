/**
 * Created by lb_k on 2/8/14.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        double i = 1;

        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
        	if ( (k / i) > Math.random()) {
        		if (rq.size() == k)
        			rq.dequeue();
            	rq.enqueue(StdIn.readString());
            }
            else
            	StdIn.readString();
        	i++;
        }

        for (i = 0; i < k; i++)
            StdOut.println(rq.dequeue());
    }
}
