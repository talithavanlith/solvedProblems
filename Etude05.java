package etude05;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.*;

/**
 * Etude05.java, design a quilt
 * 
 * Talitha van Lith
 */
public class Etude05 extends Canvas {
  public static int MAX_LAYER = -1;
  private static ArrayList<Square> squares = new ArrayList<Square>();
  private static ArrayList<Double> scales = new ArrayList<Double>();
  private static ArrayList<String> lines = new ArrayList<String>();
  public static ArrayList<Color> colors = new ArrayList<Color>();
  private static int SIZE;
  private static int windowSize;
  private static double scale_total;
  private static double proportion;

  /** creates instances of Etude05 */
  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    while (sc.hasNextLine()) {
      lines.add(sc.nextLine());
    }

    sc.close();

    for (String line : lines) {
      double scale;
      String[] splited = line.split(" ");
      scale = Double.parseDouble(splited[0]);
      scales.add(scale);
      colors.add(new Color(Integer.parseInt(splited[1]), Integer.parseInt(splited[2]), Integer.parseInt(splited[3])));
      scale_total += scale;

    }

    MAX_LAYER = lines.size() - 1;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    SIZE = screenSize.height-250;
    windowSize = screenSize.height-245;

    if (scale_total< 1.0) {
      proportion = SIZE * scale_total;
    } else {
      proportion = SIZE / scale_total;
    }

    double length = scales.get(0) * proportion;

    // call recursive method
    findCorners(0, new Coord(windowSize / 2, windowSize / 2), proportion);

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        drawQuilt();
      }
    });

  }

  private static void drawQuilt() {
    Etude05 mainPanel = new Etude05();

    JFrame frame = new JFrame("Quilt");
    Canvas canvas = new Etude05();
    canvas.setSize(windowSize, windowSize);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setSize(new Dimension(windowSize, windowSize));
    frame.getContentPane().add(mainPanel);
    frame.add(canvas);
    frame.pack();
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
  }

  // draw squares onto GUI
  public void paint(Graphics g) {
    super.paint(g);

    // for each square in the list
    for (Square q : squares) {
      g.setColor(q.getColor());
      g.fillRect((int) Math.floor(q.getX()), (int) Math.floor(q.getY()), (int) Math.floor(q.getLen()),
          (int) Math.floor(q.getLen()));
    }
  }

  // recursive method
  public static void findCorners(int layer, Coord centre, double length) {

    if (layer > MAX_LAYER) {
      return;


    } else {
      double newLength = scales.get(layer) * length;
      double shift = newLength / 2;

      Coord tl = new Coord(centre.getX() - shift, centre.getY() - shift);
      Coord bl = new Coord(centre.getX() - shift, centre.getY() + shift);
      Coord tr = new Coord(centre.getX() + shift, centre.getY() - shift);
      Coord br = new Coord(centre.getX() + shift, centre.getY() + shift);

      Square sq = new Square(tl.getX(), tl.getY(), newLength, colors.get(layer));
      squares.add(sq);
  
      findCorners(layer + 1, tl, length);
      findCorners(layer + 1, bl, length);
      findCorners(layer + 1, tr, length);
      findCorners(layer + 1, br, length);
    }
  }

}// end class