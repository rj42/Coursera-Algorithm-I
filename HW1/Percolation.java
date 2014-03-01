public class Percolation {
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF ufbw;

	private boolean[] opened;
	private int N = 0;

	public Percolation(int N) {
		this.N = N;
		uf 		= new WeightedQuickUnionUF(N*N+2);
		ufbw 	= new WeightedQuickUnionUF(N*N+1);
		opened 	= new boolean[N*N+2];
	}

	private boolean inRange(int i,  int j) {
		if (i < 1 || i > N || j < 1 || j > N) 
			return false;
		return true;
	}

	private int makeId(int i,  int j) {
		if (!inRange(i, j))
			throw new IndexOutOfBoundsException("Index of out range!");
		return i + (j-1) * N;
	}

	public void open(int i,  int j) {
		int id = makeId(i, j);
		opened[id] = true;
		if (inRange(i-1, j) && isOpen(i-1, j)) {
			uf.union(id,  makeId(i-1, j));
			ufbw.union(id,  makeId(i-1, j));
		}

		if (inRange(i+1, j) && isOpen(i+1, j)) {
			uf.union(id,  makeId(i+1, j));
			ufbw.union(id,  makeId(i+1, j));
		}

		if (inRange(i, j-1) && isOpen(i, j-1)) {
			uf.union(id,  makeId(i, j-1));
			ufbw.union(id,  makeId(i, j-1));
		}

		if (inRange(i, j+1) && isOpen(i, j+1)) {
			uf.union(id,  makeId(i, j+1));
			ufbw.union(id,  makeId(i, j+1));
		}

		if (i == 1) {
			uf.union(id,  0);
			ufbw.union(id,  0);
		}

		if (i == N)
			uf.union(id,  N*N+1);
	}

	public boolean isOpen(int i,  int j) {
		return opened[makeId(i, j)];
	}

	public boolean isFull(int i,  int j) {
		return ufbw.connected(0,  makeId(i, j));
	}

	public boolean percolates() {
		return uf.connected(0, N*N+1);
	}

	private static void main(String[] args) 
	{
		Percolation pc = new Percolation(10);
		pc.open(1, 7);
		StdOut.println(pc.isFull(1, 7));
	}
}