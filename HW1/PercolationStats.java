public class PercolationStats {
	private double[] stat;
	private int N;
	private int T;

	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0)
			throw new IllegalArgumentException("Check your arguments.");
		
		this.N = N;
		this.T = T;
		stat = new double[T];

		for (int p = 0; p < T; p++) {
			Percolation pc = new Percolation(N);
			int count = 0;

			while (!pc.percolates()) {
				int i = 1 + (int) (Math.random() * N);
				int j = 1 + (int) (Math.random() * N);

				while (pc.isOpen(i,   j)) {
					i = 1 + (int) (Math.random() * N);
					j = 1 + (int) (Math.random() * N);
				}
				pc.open(i,  j);
				count++;
			}
			
			stat[p] = ((double) count)/(N*N);
			//StdOut.println(count + " " + stat[p]);
		}
	}

	public double mean() {
		double count = 0;
		for (double val : stat) 
			count += val;
		return count/T; 
	}

	public double stddev() {
		double s2 = 0;
		double m = mean();

		for (double val : stat)
			s2 += (val-m) * (val-m);

		return Math.sqrt(s2/(T-1));
	}

	public double confidenceLo() {
		return mean() - 1.96*stddev()/Math.sqrt(T);
	}

	public double confidenceHi() {
		return mean() + 1.96*stddev()/Math.sqrt(T);
	}

	public static void main(String[] args) {
		if (args.length < 2) {
			StdOut.println("Invalid number of parametrs");
			return;
		}
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		
		PercolationStats ps = new PercolationStats(N, T);

		StdOut.println("mean \t\t\t= " + ps.mean());
		StdOut.println("stddev \t\t\t= " + ps.stddev());
		StdOut.println("95% confidence interval = " + ps.confidenceLo() + ",   " + ps.confidenceHi());
	}

 }