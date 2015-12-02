import java.util.*;

public class FastCollinearPoints {
    private boolean isRepeatingSegments = false;
    private List<LineSegment> segments = new ArrayList<LineSegment>(); //use arraylist for this
    private List<Double> slopes = new ArrayList<>();
    private List<Point> endPoints = new ArrayList<>();
    private Point currentPoint;
    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points
        checkForEntryDuplicates(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        for (Point startPoint : points) {
            Arrays.sort(pointsCopy, startPoint.slopeOrder());
            List<Point> slopePoints = new ArrayList<>();
            double currentSlope = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;
            Arrays.sort(pointsCopy, startPoint.slopeOrder());
            for (int i = 1; i < pointsCopy.length; i++) { // checking adjacent point
                currentSlope = pointsCopy[i].slopeTo(startPoint);
                if (currentSlope == previousSlope) {
                    slopePoints.add(pointsCopy[i]);
                } else {
                    if (slopePoints.size() >= 3) {
                        slopePoints.add(startPoint);
                        addSegmentIfNew(slopePoints, previousSlope);
                    }
                    slopePoints.clear();
                    slopePoints.add(pointsCopy[i]);
                }
                previousSlope = currentSlope;
            }
            if (slopePoints.size() >= 3) {
                slopePoints.add(startPoint);
                addSegmentIfNew(slopePoints, currentSlope);
            }
        }
    }
    private void addSegmentIfNew(List<Point> slopePoints, double slope) {
        Collections.sort(slopePoints);
        Point startPoint = slopePoints.get(0);
        Point endPoint = slopePoints.get(slopePoints.size() - 1);
        if (segments.isEmpty()) {
            LineSegment lineSegment = new LineSegment(startPoint, endPoint);
            slopes.add(slope);
            endPoints.add(endPoint);
            segments.add(lineSegment);
        } else {
            for (int i = 0; i < segments.size(); i++) {
                if (slope == slopes.get(i) && endPoint == endPoints.get(i)) isRepeatingSegments = true;
            }
            if ((isRepeatingSegments) == false) {
                LineSegment lineSegment = new LineSegment(startPoint, endPoint);
                slopes.add(slope);
                endPoints.add(endPoint);
                segments.add(lineSegment);
            }
            isRepeatingSegments = false;
        }
    }
    private void checkForEntryDuplicates(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            currentPoint = points[i];
            for (int j = i + 1; j < points.length; j++) {
                if (points[j].compareTo(currentPoint) == 0) throw new IllegalArgumentException("Duplicates input error");
            }
        }
    }
    public int numberOfSegments() {
        return segments.size();
    } // the number of line segments
    public LineSegment[] segments() { return segments.toArray(new LineSegment[segments.size()]); } // the line segments
}
