import javax.naming.OperationNotSupportedException;
import java.util.Iterator;

public class Board {
    private int[][] blocks;
    private int N;

    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                this.blocks[i][j] = blocks[i][j];
        }
    }
    public int dimension() {
        return N;
    }
    public int hamming() {
        int h = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0 || blocks[i][j] == i*N + j + 1)
                    continue;
                h++;
            }
        }

        return h;
    }
    public int manhattan() {
        int m = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0)
                    continue;
                int I = (blocks[i][j] - 1) / N;
                int J = (blocks[i][j] - 1) % N;
                m += Math.abs(I-i) + Math.abs(J-j);
            }
        }

        return m;
    }
    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        Board b = new Board(blocks);
        outerloop:
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (blocks[i][j] != 0 && blocks[i][j+1] != 0) {
                    int tmp = b.blocks[i][j];
                    b.blocks[i][j] = b.blocks[i][j+1];
                    b.blocks[i][j+1] = tmp;
                    break outerloop;
                }
            }
        }

        return b;
    }
    public boolean equals(Object y) {
        if (this == y) return true;

        if (y == null) return false;

        if (this.getClass() != y.getClass())
            return false;

        if (dimension() != ((Board) y).dimension())
            return false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ( ((Board) y).blocks[i][j] != blocks[i][j] )
                    return false;
            }
        }

        return true;
    }
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<Board>();

        int move = 0;
        int posX = 0, posY = 0; //position of zero

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    posX = j;
                    posY = i;
                    break;
                }
            }
        }

        while (move < 4) {
            int pX = posX, pY = posY;
            switch (move){
                case 0: pX++; break;
                case 1: pY++; break;
                case 2: pX--; break;
                case 3: pY--; break;
            }

            move++;
            if (pX < 0 || pX >= N || pY < 0 || pY >= N)
                continue;

            Board tmp = new Board(blocks);
            tmp.blocks[posY][posX] = tmp.blocks[pY][pX];
            tmp.blocks[pY][pX] = 0;
            boards.push(tmp);
        }

        return boards;
    }


    public String toString() {
        String str = N + "\n";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                str += " " + blocks[i][j];
            }
            str += "\n";
        }

        return str;
    }
}
