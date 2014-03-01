public class Solver {
    private class Node implements Comparable<Node> {
        int moves = 0;
        int distance = 0;
        int pr = 0;
        Node prev = null;
        Board board;

        public Node(Board board, Node prev, int moves) {
            this.board = board;
            this.moves = moves;
            this.prev  = prev;
            this.distance = board.manhattan();
            pr = moves + distance;
        }

        @Override
        public int compareTo(Node node) {
            if (pr < node.pr) return -1;
            if (pr > node.pr) return 1;
            return 0;
        }
    }
    private MinPQ<Node> tree    = new MinPQ<Node>();
    private MinPQ<Node> treeTW  = new MinPQ<Node>();
    private Node last = null;
    private Stack<Board> solution = new Stack<Board>();

    private boolean solvable = false;

    public Solver(Board initial) {
        tree.insert(new Node(initial, null, 0));
        treeTW.insert(new Node(initial.twin(), null, 0));

        MinPQ<Node> now = tree;
        int s = 0;

        do {
            if (s == 10000) {
                if (now == tree)
                    now = treeTW;
                else
                    now = tree;
                s = 0;
            }
            s++;
            Node n  = now.delMin();

            if (n.distance == 0) {
                last = n;
                break;
            }

            outerloop:
            for (Board b : n.board.neighbors()) {
                if (n.prev != null && b.equals(n.prev.board)) continue;
                now.insert(new Node(b, n, n.moves + 1));
            }

        } while (true);
        solvable = (now == tree);

        if (solvable) {
            Node tmp = last;
            while (tmp != null) {
                solution.push(tmp.board);
                tmp = tmp.prev;
            }
        }
    }

    public boolean isSolvable() {return solvable;};
    public int moves() {
        if (solvable)
            return solution.size() - 1;
        return -1;
    }

    public Iterable<Board> solution() {
        if (!solvable)
            return null;
        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
