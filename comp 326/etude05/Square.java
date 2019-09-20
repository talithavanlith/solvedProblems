package etude05;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class Square {
    double x, y;
    double len;
    Color c;

    public Square(double x, double y, double len, Color c) {
        this.x = x;
        this.y = y;
        this.len = len;
        this.c = c;
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.x = y;
    }

    public double getLen() {
        return len;
    }
    public void setLen(double len) {
        this.len = len;
    }

    public Color getColor() {
        return c;
    }
    public void setColor(int r, int g, int b) {
        try {
            c = new Color(r, g, b);
        } catch (IllegalArgumentException ex) {
            System.err.println("Color arguments must be between 0 and 255 inclusive.");
            ex.printStackTrace();
        }
    }
    
    // public String toString() {
    //     String r = "";
    //     r.concat("\n" + Double.toString(x));
    //     r.concat("\n" + Double.toString(y));
    //     r.concat("\n" + Double.toString(len));
    //     return r;
    // }
    
}