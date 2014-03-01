import java.util.Comparator;

public class Point implements Comparable<Point> {
    public final Comparator<Point> SLOPE_ORDER = new BySlope();

    private class BySlope implements Comparator<Point> {
        @Override
        public int compare(Point pt1, Point pt2) {
            if (slopeTo(pt1) < slopeTo(pt2)) return -1;
            if (slopeTo(pt1) > slopeTo(pt2)) return 1;
            return 0;
        }
    }

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if (that.x == this.x) {
            if (that.y == this.y)	return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        if (that.y == this.y) return +0;
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;
        return 0;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {

    }
}
