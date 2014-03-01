public class Brute {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        In in = new In(args[0]);
        int n = in.readInt();
        Point[] pt = new Point[n];
        n = 0;
        while (!in.isEmpty()) {
            pt[n++] = new Point(in.readInt(), in.readInt());
            pt[n-1].draw();
        }
        
        Quick.sort(pt);

        for (int a = 0; a < pt.length; a++) {
            for (int b = a+1; b < pt.length; b++) {
                for (int c = b + 1; c < pt.length; c++) {
                    if (pt[a].slopeTo(pt[b]) != pt[b].slopeTo(pt[c]))
                        continue;
                    for (int d = c + 1; d < pt.length; d++) {
                        if (pt[b].slopeTo(pt[c]) == pt[c].slopeTo(pt[d])) {
                            pt[a].drawTo(pt[d]);
                            StdOut.println(pt[a] + "  -> " + pt[b] + " -> " + pt[c] + " -> " + pt[d]);
                        }
                    }
                }
            }
        }
    }
}
