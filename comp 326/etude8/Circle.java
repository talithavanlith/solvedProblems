package etude8;

public class Circle {
    private double x, y, rad, diameter;

    public Circle(double x, double y, double diameter) {
        this.x = x;
        this.y = y;
        this.rad = diameter/2;
        this.diameter = diameter;
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
    
    public void setRad(double rad) {
        this.rad = rad;
    }
    public double getRad() {
        return this.rad;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }
    public double getDiameter() {
        return this.diameter;
    }

    public String toString() {
        return (this.x+" "+this.y);
    }
}