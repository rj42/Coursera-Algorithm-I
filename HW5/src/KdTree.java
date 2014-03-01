import java.awt.Color;

/**
 * Created by lb_k on 2/28/14.
 */
public class KdTree {
    private static class Node {
        private Node left, right;
        private int size = 0;
        private Point2D p;
        private RectHV rect;

        Node(Point2D p, int size, RectHV rect) {
            this.p = p;
            this.size = size;
            this.left = null;
            this.right = null;
            this.rect = rect;
        }
    }

    private Node root;
    private double distance;
    private Point2D nearest;

    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        if (root == null)
            return 0;
        else
            return root.size;
    }
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (contains(p))
            return;

        Node node = this.root;
        int level = 0;

        if (node == null) { //first root
            this.root = new Node(p, 1, new RectHV(0., 0., 1., 1.));
            return;
        }

        RectHV rect;

        while (true) {
            node.size++;
            if (level % 2 == 0) {
                if (p.x() < node.p.x()) {
                    if (node.left != null)
                        node = node.left;
                    else {
                        rect = node.rect;
                        node.left = new Node(p, 1, new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax()));
                        break;
                    }
                }
                else {
                    if (node.right != null)
                        node = node.right;
                    else {
                        rect = node.rect;
                        node.right = new Node(p, 1, new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax()));
                        break;
                    }
                }
            }
            else if (level % 2 == 1) {
                if (p.y() < node.p.y()) {
                    if (node.left != null)
                        node = node.left;
                    else {
                        rect = node.rect;
                        node.left = new Node(p, 1, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y()));
                        break;
                    }
                }
                else {
                    if (node.right != null)
                        node = node.right;
                    else {
                        rect = node.rect;
                        node.right = new Node(p, 1, new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax()));
                        break;
                    }
                }
            }
            level++;
        }
        //StdOut.println(level);
    }
    // does the set contain the point p?
    public boolean contains(Point2D p) {
        Node node = this.root;
        int level = 0;

        if (node == null) { //first root
            return false;
        }

        while (true) {
            if (node.p.equals(p))
                return true;

            if ((level % 2 == 0 && p.x() < node.p.x()) || (level % 2 == 1 && p.y() < node.p.y())) {
                    if (node.left != null)
                        node = node.left;
                    else return false;
                }
                else {
                    if (node.right != null)
                        node = node.right;
                    else return false;
                }
            level++;
        }
    }
    // draw all of the points to standard draw
    public void draw() {
        draw(root, 0);
    }
    private void draw(Node node, int level) {
        if (node != null) {
            StdDraw.point(node.p.x(), node.p.y());
            StdDraw.setPenRadius(0.001);

            if (level % 2 == 0) {
                StdDraw.setPenColor(Color.red);
                StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
            }
            else {
                StdDraw.setPenColor(Color.blue);
                StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
            }

            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(Color.black);
            draw(node.left, level + 1);
            draw(node.right, level + 1);
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> st = new Stack<Point2D>();
        range(rect, root, st);
        return st;
    }
    private void range(RectHV rect, Node node, Stack<Point2D> st) {
        if (node == null) return;
        if (node.rect.intersects(rect)) {
            if (rect.contains(node.p))
                st.push(node.p);
            range(rect, node.left, st);
            range(rect, node.right, st);
        }
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        nearest = null;
        distance = Double.POSITIVE_INFINITY;
        nearest(root, p, 0);
        return nearest;
    }

    private void nearest(Node node, Point2D p, int level) {
        if (node == null) return;
        if (distance < node.rect.distanceSquaredTo(p)) return;

        double tmp = node.p.distanceSquaredTo(p);
        if (tmp < distance) {
            nearest = node.p;
            distance = tmp;
        }

        if ((level % 2 == 0 && p.x() < node.p.x()) || (level % 2 == 1 && p.y() < node.p.y())) {
            nearest(node.left , p, level + 1);
            nearest(node.right, p, level + 1);
        }
        else {
            nearest(node.right, p, level + 1);
            nearest(node.left , p, level + 1);
        }
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenRadius(0.01);

        KdTree tr = new KdTree();
        tr.insert(new Point2D(0.7, 0.2));
        tr.insert(new Point2D(0.5, 0.4));
        tr.insert(new Point2D(0.2, 0.3));
        tr.insert(new Point2D(0.4, 0.7));
        tr.insert(new Point2D(0.9, 0.6));
        StdOut.println(tr.nearest(new Point2D(0.5, 0.5)));
        StdOut.println(tr.size());
        StdOut.println(tr.contains(new Point2D(0.2, 0.3)));

        tr.draw();
    }
}
