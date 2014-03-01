/**
 * Created by lb_k on 2/28/14.
 */
public class PointSET {
    private SET<Point2D> set;
    // construct an empty set of points
    public PointSET() {
        set = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        set.add(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        for (Point2D p : set)
            StdDraw.point(p.x(), p.y());
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p))
                q.enqueue(p);
        }

        return q;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        Point2D q = null;
        double tmp;
        double d = Double.POSITIVE_INFINITY;
        for (Point2D r : set) {
            tmp = r.distanceSquaredTo(p);
            if (d > tmp) {
                d = tmp;
                q = r;
            }
        }

        return q;
    }

    public static void main(String[] args) {

    }
}