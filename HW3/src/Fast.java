import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class Fast {
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
        HashMap<Double, List<Point>> lines = new HashMap<Double, List<Point>>();

        for (int i = 0; i < pt.length; i++) {
            Point o = pt[i];

            Point[] npt = new Point[pt.length - i];
            for (int j = i; j < pt.length; j++)
                npt[j-i] = pt[j];
            Arrays.sort(npt, o.SLOPE_ORDER);

            double slope = 0, nowSlope;
            int c = 0, fr = 1;
            for (int j = 1; j < npt.length; j++) {
                nowSlope = o.slopeTo(npt[j]);

            	if (c == 0)
            		slope = nowSlope;

                if (nowSlope == slope)
                    c++;

                if (nowSlope != slope || j == npt.length - 1) {
                	if (j == npt.length - 1 && nowSlope == slope) j++; //last point
                    if (c >= 3) {
		                boolean once = true;
		                if (lines.containsKey(slope)) {
		                    for (Point p : lines.get(slope)) {
		                        if (npt[j-1].compareTo(p) == 0) //or by slope
		                        {
		                            once = false;
		                            break;
		                        }
		                     }
		                }

		                if (once) {//first mentioned about this line
		                    List<Point> l = lines.get(slope);
		                    if (l == null) {
		                        l = new ArrayList<Point>();
		                        lines.put(slope, l);
		                    }
		                    l.add(npt[j-1]);
		                    StdOut.print(o);
		                    for (int k = fr; k < fr + c; k++) {
                                StdOut.print(" -> " + npt[k]);
                            }
		                    StdOut.println();
		                    o.drawTo(npt[fr + c - 1]);
		                }
		            }

                    c = 1;
                    fr = j;
                    slope = nowSlope;
                }
            }
        }
    }
}
