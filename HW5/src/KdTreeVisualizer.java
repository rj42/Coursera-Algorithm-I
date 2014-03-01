/*************************************************************************
 *  Compilation:  javac KdTreeVisualizer.java
 *  Execution:    java KdTreeVisualizer
 *  Dependencies: StdDraw.java Point2D.java KdTree.java
 *
 *  Add the points that the user clicks in the standard draw window
 *  to a kd-tree and draw the resulting kd-tree.
 *
 *************************************************************************/

public class KdTreeVisualizer {

    public static void main(String[] args) {
        In in = new In("circle10.txt");

        StdDraw.show(0);
        KdTree kdtree = new KdTree();

        while (!in.isEmpty()) {
            kdtree.insert(new Point2D(in.readDouble(), in.readDouble()));
        }

        StdOut.println(kdtree.nearest(new Point2D(0.81, 0.3)));

        //kdtree.draw();

        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                System.out.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                kdtree.insert(p);
                StdDraw.clear();
                kdtree.draw();
            }
            StdDraw.show(50);
        }

    }
}
