package e4;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class cordlessPhones {
    public static ArrayList<Coord> points = new ArrayList<Coord>();
    public static ArrayList<Double> largest = new ArrayList<Double>();
    public static ArrayList<Double> largest10 = new ArrayList<Double>();
    private static double result = Double.POSITIVE_INFINITY;

    public static void main(String[] args) {
        // read in the x and y values from std input
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        while(sc.hasNext()){
            Double siteX = Double.parseDouble(sc.next());
            Double siteY = Double.parseDouble(sc.next());
            points.add(new Coord(siteX, siteY));
        }

        // if there are less than 12 points, result is infinite
        if(comparePoints() == 1) {
            for (int i=0;i<points.size();i++) {
                for (int j=i+1;j<points.size();j++) {
                    double yi = points.get(i).getY();
                    double yj = points.get(j).getY();

                    lineRadius(yi, yj, 2);
                }
            }
        // if all the y values are in a line
        } else if(comparePoints() == 2) {
            // y has all points in a line
            for (int i=0;i<points.size();i++) {
                for (int j=i+1;j<points.size();j++) {
                    double xi = points.get(i).getX();
                    double xj = points.get(j).getX();

                    lineRadius(xi, xj, 1);
                }
            }
        // if there are 13 points
        } else if (points.size() == 13) {
            int count;
            for (int i=0; i<points.size(); i++) {
                for (int j=i+1; j<points.size(); j++) {
                    count = 0;
                    // calculate center point of two points
                    double x = (points.get(i).getX() + points.get(j).getX()) /2;
                    double y = (points.get(i).getY() + points.get(j).getY()) /2;
                    Coord c = new Coord(x, y); // create new center point
                    double rad = calculateRadius(c, points.get(i)); // calculate distance between new point and i.

                    for (int k=0; k<points.size(); k++) {
                        if (isWithin(c, points.get(k), rad)) {
                            count++;
                        }
                    }
                    if (count == 10){
                        largest10.add(rad);
                    } else if (count < 10){
                        largest.add(rad);
                    }
                }
            }
            // find largest rad in largest.
        } else {
            // take all possible 3 point combinations
            for (int a = 0; a < points.size(); a++) {
                for (int b = a + 1; b < points.size(); b++) {
                    for (int c = b + 1; c < points.size(); c++) {
                        // if all the x's or y's are in a line, skip it. IS THIS NEEDED? We already check this above in the comparePoints method...
                        if ((points.get(a).getX() == points.get(b).getX() && points.get(a).getX() == points.get(c).getX()) ||
                                 (points.get(a).getY() == points.get(b).getY() && points.get(a).getY() == points.get(c).getY())){
                            continue;
                        // check for < 90 degrees?

                        // calculate a center point of the 3 points, then calculate the radius using that center point.
                        } else {
                            Coord center = calculateCenter(points.get(a), points.get(b), points.get(c));
                            double radius = calculateRadius(points.get(a), center);
                            int count = 0;
                            // check that no more than 11 points are within the new circle
                            for (Coord point : points) {
                                if (isWithin(center, point, radius)) {
                                    count++;
                                }
                                if (count > 11) {
                                    break;
                                }
                            }
                            // if 11 points are within the new circle, add the radius to a list
                            if (count == 11) {
                                largest.add(radius);
                            }
                        }
                    }
                }
            } 
        }
        // if there have been values added to the largest radius lists, get the smallest

        if (points.size() == 13) {
            if (largest10.size() > 0) {
                result = Collections.min(largest10);            
            } else if (largest.size() > 0) {
                result = 0.0;
                for (Double x : largest) {
                    if (x > result && x != Double.POSITIVE_INFINITY) {
                        result = x;
                    }
                }
                // result = Collections.min(largest); 
            }
        } else {
            if (largest10.size() > 0) {
                result = Collections.min(largest10);            
            } else if (largest.size() > 0) {
                // result = 0.0;
                // for (Double x : largest) {
                //     if (x > result && x != Double.POSITIVE_INFINITY) {
                //         result = x;
                //     }
                result = Collections.min(largest); 
            }
        }
        // print the result
        System.out.println(result);
    }

    // calculate the largets radius if the values are in a line.
    public static void lineRadius(double a, double b, int c) {
        double larger, smaller;
        if (a > b) {
            larger = a;
            smaller = b;
        } else {
            larger = b;
            smaller = a;
        }
        int count = 0;

        // x values are in a line
        if (c == 1) {
            for (Coord point : points) {
                if (point.getX() > smaller && point.getX() < larger) {
                    count++;
                }
                if (count > 10) {
                    break;
                }
            }
            if (count == 10) {
                double radius = (larger - smaller) / 2;
                largest.add(radius);
            }
        // y values are in a line
        } else if (c == 2) {
            for (Coord point : points) {
                if (point.getY() > smaller && point.getY() < larger) {
                    count++;
                }
                if (count > 10) {
                    break;
                }
            }
            if (count == 10) {
                double radius = (larger - smaller) / 2;
                largest.add(radius);
            }
        }
    }

    // calculate if the x values, y values or neither are in a line.
    public static int comparePoints() {
        int xCount = 1;
        int yCount = 1;
        
        for (int j=1;j<points.size();j++) {
            if (points.get(0).getX() == points.get(j).getX()) {
                xCount++;
                continue;
            }
            if (points.get(0).getY() == points.get(j).getY()) {
                yCount++;
                continue;
            }
        }
        if (xCount == points.size()) {
            return 1; // x values are in a line
        } else if (yCount == points.size()) {
            return 2; // y values are in a line
        } else {
            return 0; // neither
        }
    }

    // calculate the center of 3 points. MATHS
    public static Coord calculateCenter(Coord i, Coord j, Coord k) {
        double yDelta_1 = j.getY() - i.getY();
        double xDelta_1 = j.getX() - i.getX();

        double yDelta_2 = k.getY() - j.getY();
        double xDelta_2 = k.getX() - j.getX();

        double slope1 = yDelta_1 / xDelta_1;
        double slope2 = yDelta_2 / xDelta_2;

        double x_center = (slope1 * slope2 * (i.getY() - k.getY()) + slope2 * (i.getX() + j.getX()) - slope1 * (j.getX() + k.getX())) / (2 * (slope2 - slope1) );

        double y_center = -1 * (x_center - (i.getX() + j.getX()) / 2) / slope1 + (i.getY() + j.getY()) / 2;

        Coord center = new Coord(x_center, y_center);
        return center;
    }
    
    // calculate the radius of two points
    public static double calculateRadius(Coord i, Coord center) {
        // basic maths to calc the hypotenuse of two points.
        double deltaX = Math.pow((i.getX() - center.getX()), 2);
        double deltaY = Math.pow((i.getY() - center.getY()), 2);

        double radius = Math.sqrt(deltaX + deltaY);

        return radius;
    }

    // calculate if two points are within the radius.
    public static boolean isWithin(Coord center, Coord test, double radius) {
        // basic maths to calc hypotenuse of two points and check it is less than the radius
        double distance = calculateRadius(center, test);
        if (distance < radius) {
            return true;
        }
        return false;
    }
}