package etude05;

public class Coord {
    private double x, y;

    public Coord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }
    public double getX() {
        return this.x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getY() {
        return this.y;
    }

    public String toString() {
        return ("("+this.x+", "+this.y+")");
    }
}