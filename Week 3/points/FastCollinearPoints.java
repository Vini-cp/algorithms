import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] firstPoint = new Point[5];
    private Point[] secndPoint = new Point[5];
    private int numberOfSegments = 0;

    /**
     * Finds all line segments containing 4 (or more) points exactly once.
     * Examines 4 points at a time and checks whether
     * they all lie on the same line segment,
     * returning all such line segments;
     * <p>
     * To check whether the 4 points p, q, r, and s are collinear,
     * check whether the three slopes between p and q,
     * between p and r, and between p and s are all equal;
     */
    public FastCollinearPoints(Point[] points) {
        isNull(points);
        Arrays.sort(points);
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

    private void findSegments(Point[] points) {
        Point[] sortedPoints;
        double[] slopes = new double[points.length];
        for (int i = 0; i < points.length - 3; i++) {
            sortedPoints = points.clone();
            for (int j = 0; j < points.length; j++) {
                slopes[j] = points[i].slopeTo(points[j]);
            }
            Arrays.sort(sortedPoints, points[i].slopeOrder());
            analyzeSlopes(slopes, sortedPoints);
        }
    }

    private void analyzeSlopes(double[] slopes, Point[] sortedPoints) {
        int counter = 1;
        Arrays.sort(slopes);
        double value = slopes[0];
        for (int j = 1; j < slopes.length; j++) {
            if (slopes[j] == value) {
                counter++;
                if (counter >= 3 && j == slopes.length - 1)
                    if (validatePoints(sortedPoints[0], sortedPoints[j]))
                        numberOfSegments++;
            }
            else {
                if (counter >= 3) {
                    if (validatePoints(sortedPoints[0], sortedPoints[j - 1]))
                        numberOfSegments++;
                }
                counter = 1;
                value = slopes[j];
            }
        }
    }

    private boolean validatePoints(Point a, Point b) {
        if (numberOfSegments == firstPoint.length - 1)
            duplicateSize();
        for (int i = 0; i < numberOfSegments; i++) {
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
        firstPoint[numberOfSegments] = a;
        secndPoint[numberOfSegments] = b;
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
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0)
                throw new IllegalArgumentException("Array could not have repeated points");
        }
    }
}
