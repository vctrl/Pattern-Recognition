import java.util.ArrayList;
import java.util.Arrays;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> collinear = new ArrayList<LineSegment>();
    private Point firstEndPointOfLine = new Point(0, 0);
    private Point secondEndPointOfLine = new Point(0, 0);
    ArrayList<Point>  firstEndPointsOfLine = new ArrayList<>();
    ArrayList<Point>  secondEndPointsOfLine = new ArrayList<>();
    private Point currentPoint;
    private boolean checkRepeatingSegments = false;
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        for (int i = 0; i < points.length; i++) {
            currentPoint = points[i];
            for(int j = i + 1; j < points.length; j++) {
                if (points[j].compareTo(currentPoint) == 0) throw new IllegalArgumentException();
            }
        }
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        for(int i = 0; i < pointsCopy.length; i++)
            for (int j = i + 1; j < pointsCopy.length; j++)
                for (int k = j + 1; k < pointsCopy.length; k++) {
                    for (int l = k + 1; l < pointsCopy.length; l++) {
                        if (pointsCopy[i].slopeTo(pointsCopy[j]) != pointsCopy[j].slopeTo(pointsCopy[k])) break;
                        if (pointsCopy[j].slopeTo(pointsCopy[k]) == pointsCopy[k].slopeTo(pointsCopy[l])) {
                            if (pointsCopy[i].compareTo(pointsCopy[j]) > 0) {
                                secondEndPointOfLine = pointsCopy[i];
                                firstEndPointOfLine = pointsCopy[j];
                            }
                            else {
                                secondEndPointOfLine = pointsCopy[j];
                                firstEndPointOfLine = pointsCopy[i];
                            }
                            if (pointsCopy[k].compareTo(secondEndPointOfLine) > 0)
                                secondEndPointOfLine = pointsCopy[k];
                            if (pointsCopy[k].compareTo(firstEndPointOfLine) < 0)
                                firstEndPointOfLine = pointsCopy[k];
                            if (pointsCopy[l].compareTo(secondEndPointOfLine) > 0)
                                secondEndPointOfLine = pointsCopy[l];
                            if (pointsCopy[l].compareTo(firstEndPointOfLine) < 0)
                                firstEndPointOfLine = pointsCopy[l];
                            LineSegment candidate = new LineSegment(firstEndPointOfLine, secondEndPointOfLine);
                            if(collinear.isEmpty()) {
                                collinear.add(candidate);
                                firstEndPointsOfLine.add(firstEndPointOfLine);
                                secondEndPointsOfLine.add(secondEndPointOfLine);
                            }
                            else {
                                for(int m = 0; k < collinear.size(); k++)
                                    if(firstEndPointOfLine.compareTo(firstEndPointsOfLine.get(m)) == 0
                                            && secondEndPointOfLine.compareTo(secondEndPointsOfLine.get(m)) == 0)
                                        checkRepeatingSegments = true;
                                if (checkRepeatingSegments == false)
                                    collinear.add(candidate);
                            }
                        }
                    }
                }
    }
    public int numberOfSegments() { return collinear.size(); } // the number of line segments
    public LineSegment[] segments(){ return collinear.toArray(new LineSegment[collinear.size()]); } // the line segments
}

