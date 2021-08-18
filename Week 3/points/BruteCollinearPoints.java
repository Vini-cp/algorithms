import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] firstPoint = new Point[5];
    private Point[] secndPoint = new Point[5];
    private int numberOfSegments;

    /**
     * Finds all line segments containing 4 points.
     * Examines 4 points at a time and checks whether
     * they all lie on the same line segment,
     * returning all such line segments;
     * <p>
     * To check whether the 4 points p, q, r, and s are collinear,
     * check whether the three slopes between p and q,
     * between p and r, and between p and s are all equal;
     */
    public BruteCollinearPoints(Point[] points) {
        isNull(points);
        repeatedPoints(points);
        findSegments(points);
    }

    /**
     * @return the number of line segments.
     */
    public int numberOfSegments() {
        return numberOfSegments;
    }

    /**
     * @return the line segments.
     */
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            segments[i] = new LineSegment(firstPoint[i], secndPoint[i]);
        }
        return segments;
    }

    private void findSegments(Point[] p) {
        Point[] points = p.clone();
        Arrays.sort(points);
        int nbLines = 0;
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                                && points[i].slopeTo(points[j]) == points[i]
                                .slopeTo(points[m])) {
                            if (nbLines == firstPoint.length - 1)
                                duplicateSize();
                            if (validePoints(points[i], points[m], nbLines))
                                nbLines++;
                        }
                    }
                }
            }
        }
        numberOfSegments = nbLines;
    }

    private boolean validePoints(Point a, Point b, int size) {
        for (int i = 0; i < size; i++) {
            if (firstPoint[i].slopeTo(secndPoint[i]) == a.slopeTo(b)
                    && firstPoint[i].slopeTo(secndPoint[i]) == firstPoint[i].slopeTo(b)) {
                if (firstPoint[i].compareTo(a) > 0) {
                    firstPoint[i] = a;
                    secndPoint[i] = b;
                }
                else if (secndPoint[i].compareTo(b) < 0) {
                    firstPoint[i] = a;
                    secndPoint[i] = b;
                }
                return false;
            }
        }
        firstPoint[size] = a;
        secndPoint[size] = b;
        return true;
    }

    private void duplicateSize() {
        Point[] fPointCopy = new Point[firstPoint.length * 2];
        Point[] sPointCopy = new Point[secndPoint.length * 2];
        for (int i = 0; i < firstPoint.length; i++) {
            fPointCopy[i] = firstPoint[i];
            sPointCopy[i] = secndPoint[i];
        }
        firstPoint = fPointCopy;
        secndPoint = sPointCopy;
    }

    private void isNull(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Array could not be null");
        for (Point point : points) {
            if (point == null)
                throw new IllegalArgumentException("Point could not be null");
        }
    }

    private void repeatedPoints(Point[] points) {
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        for (int i = 1; i < sortedPoints.length; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i - 1]) == 0)
                throw new IllegalArgumentException("Array could not have repeated points");
        }
    }
}
