/**
 * Created by lb_k on 2/15/14.
 */
public class RandomGen {
    public static void main(String[] args) {
    	for (int s = 0; s < 2000; s++){
		    Out out = new Out("random.txt");
		    int x = 10, y = 10, N = 10;
		    out.println(N);
		    int M[][] = new int[x][y];
		    int k = 0;
		    while (k < N) {
		        int i = StdRandom.uniform(0,x);
		        int j = StdRandom.uniform(0,y);
		        if (M[i][j] == 0) {
		            M[i][j] = 1;
		            k++;

		            out.println(i+" "+j);
		        }
		    }

		    String[] str = {"random.txt"};
		    Fast.main(str);
		    StdOut.println("--------------");
            //StdIn.readByte();
    	}
    }
}
