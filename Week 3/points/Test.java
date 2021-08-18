import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Point a = new Point(1, 1);
        Point b = new Point(3, 3);
        Point c = new Point(7, 7);
        Point d = new Point(5, 5);
        Point e = new Point(10, 10);
        Point f = new Point(4, 6);
        Point g = new Point(-5, 2);
        Point h = new Point(-3, 2);
        Point i = new Point(0, 6);
        Point[] points = { a, b, c, d, e, f, g, h, i };
        Arrays.sort(points);
        double[] slopes = new double[points.length];
        for (int j = 0; j < points.length; j++) {
            slopes[j] = points[0].slopeTo(points[j]);
        }
        StdOut.println(Arrays.toString(points));
        StdOut.println(Arrays.toString(slopes));
        Arrays.sort(points, points[0].slopeOrder());
        Arrays.sort(slopes);
        StdOut.println(Arrays.toString(points));
        StdOut.println(Arrays.toString(slopes));


    }
}
