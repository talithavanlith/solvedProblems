package etude8;

import java.math.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Random;
import java.util.Collections;

public class Patch {

    public static double sheetX, sheetY, patchSize, left, right, top, bottom;
    public static ArrayList<Circle> holes = new ArrayList<Circle>();
    public static ArrayList<Circle> patches = new ArrayList<Circle>();
    public static ArrayList<String> overlapSides = new ArrayList<String>();

    public static ArrayList<ArrayList<Circle>> groups = new ArrayList<ArrayList<Circle>>();
    public static boolean impossible = true;

    public static ArrayList<Circle> finalPatches = new ArrayList<Circle>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] sheetSize = sc.nextLine().split(" ");
        String[] patchLine = sc.nextLine().split(" ");
        
            // calculate the sheet size & patch size.
        sheetX = Double.parseDouble(sheetSize[2]);
        sheetY = Double.parseDouble(sheetSize[3]);
        patchSize = Double.parseDouble(patchLine[2]);

        // while data is being read in:
        while (sc.hasNext() && !(sc.nextLine().equals("ENDFILE"))) {
            // while co-ordinates & radius are being read in:
            while (sc.hasNext()) {
                String next = sc.next();
                // if end of numbers is reached
                if (next.equals("END")) {
                    if (sc.hasNext()){
                        // because it reads the '\n' after END as a whole new line for some reason
                        sc.nextLine();
                    }
                    break;
                }
                // create a new circle using the input values & add it to the arraylist
                double xin = Double.parseDouble(next);
                double yin = Double.parseDouble(sc.next());
                double din = Double.parseDouble(sc.next());
                if (din > 0){
                    Circle c = new Circle(xin, yin, din);
                    holes.add(c);
                }
            }// holes have now been read into the arraylist, patchsize read in & sheet size calculated.

            // immediate fail cases
            if (!checkCases()) {
                continue;
            }

            //initialise groups
            for (Circle hole : holes) {
                ArrayList<Circle> temp = new ArrayList<Circle>();
                temp.add(hole);
                groups.add(temp);
            } 
            check();

            while(findGroups()){
                findGroups();
            }

            patches.clear();
            for (ArrayList<Circle> group : groups) {
                Circle center = findCenterOfGroup(group);
                patches.add(center);
            }

            int c = 0;

            while (c < 150) {
                moveOnSheet();
                movePatchesToFit();
                c++;
            }

            if (calcWeight(patches) != 0){
                System.out.println("Impossible " + patches);
                System.out.println("weight " + calcWeight(patches));
            } else {
                System.out.println("Positions of patches");
                if (finalPatches.size() == 0){
                    for (Circle patch : patches){
                        System.out.println(patch);
                    }
                } else {
                    for (Circle patch : finalPatches){
                        System.out.println(patch);
                    }
                }
                System.out.println("END");
            }

            holes.clear();
            patches.clear();
            finalPatches.clear();
            groups.clear();
        }
        System.out.println("ENDFILE");
    }

    public static boolean movePatchesToFit(){

        for (int i = 0; i < patches.size(); i++){
            for(int j = i+1; j < patches.size(); j++){

                if (distBetweenEdges(patches.get(i), patches.get(j)) < 0){
                    double oGWeight = calcWeight(patches);
                    //System.err.println("ogweight: " + oGWeight);

                    ArrayList<Circle> tryIMoved = new ArrayList<Circle>(patches);
                    ArrayList<Circle> tryJMoved = new ArrayList<Circle>(patches);
                    ArrayList<Circle> oGPatches = new ArrayList<Circle>(patches);

                    double overlapVal = Math.abs(distBetweenEdges(patches.get(i), patches.get(j))) - 0.1;

                    // new i position
                    double fromJ = calcAngle(patches.get(j), patches.get(i));
                    if (fromJ == 180 || fromJ == 0) {
                        fromJ-=180;
                    }
                    double angleJ = fromJ * (Math.PI / 180); // degrees to rads
                    double InewX = overlapVal * Math.sin(angleJ) + patches.get(j).getX();
                    double InewY = overlapVal * Math.cos(angleJ) + patches.get(j).getY();
                    InewX = round(InewX, 2);
                    InewY = round(InewY, 2);
                    Circle circleJ = new Circle(InewX, InewY, patchSize);

                    //new j position
                    double fromI = calcAngle(patches.get(i), patches.get(j));
                    if (fromI == 180 || fromI == 0) {
                        fromI-=180;
                    }
                    double angleI = fromI * (Math.PI / 180); // degrees to rads
                    double JnewX = overlapVal * Math.sin(angleI) + patches.get(i).getX();
                    double JnewY = overlapVal * Math.cos(angleI) + patches.get(i).getY();
                    JnewX = round(JnewX, 2);
                    JnewY = round(JnewY, 2);
                    Circle circleI = new Circle(JnewX, JnewY, patchSize);

                    tryIMoved.set(i, circleI);
                    double tryIWeight = calcWeight(tryIMoved);

                    double JnewX_small = 0.11 * Math.sin(angleJ) + patches.get(j).getX();
                    double JnewY_small = 0.11 * Math.cos(angleJ) + patches.get(j).getY();
                    JnewX_small = round(JnewX_small, 2);
                    JnewY_small = round(JnewY_small, 2);
                    Circle smallJCircle = new Circle(JnewX_small, JnewY_small, patchSize);

                    tryIMoved.set(j, smallJCircle);


                    tryJMoved.set(j, circleJ);
                    double InewX_small = 0.11 * Math.sin(angleI) + patches.get(i).getX();
                    double InewY_small = 0.11 * Math.cos(angleI) + patches.get(i).getY();
                    InewX_small = round(InewX_small, 2);
                    InewY_small = round(InewY_small, 2);

                    Circle smallICircle = new Circle(InewX_small, InewY_small, patchSize);
                    tryJMoved.set(i, smallICircle);

                    double tryJWeight = calcWeight(tryJMoved);

                    //System.err.println("TryI: " + tryIWeight);
                    //System.err.println("TryJ: " + tryJWeight);


                    if (tryIWeight < oGWeight) {
                        patches = tryIMoved;                      
                    } else if (tryJWeight < oGWeight){
                        patches = tryJMoved;
                    }

                    if (calcWeight(patches) == 0) {
                        finalPatches = patches;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void moveOnSheet(){
        for (int i = 0; i < patches.size(); i++){
            if ((touchEdge(patches.get(i), 0)) > 0){
                for(int j = 0; j < overlapSides.size(); j++){

                    double x = patches.get(i).getX();
                    double y = patches.get(i).getY();
                    if (overlapSides.get(j).equals("right")){
                        // move left decrease x
                        patches.get(i).setX(x - right);

                    } else if (overlapSides.get(j).equals("left")){
                        // move right increase x
                        patches.get(i).setX(x + left);

                    } else if (overlapSides.get(j).equals("bottom")){
                        // move up decrease y
                        patches.get(i).setY(y - bottom);

                    } else {
                        // move down increase y
                        patches.get(i).setY(y + top);

                    }
                }
            }
        }
    }

    public static double calcAngle(Circle a, Circle b){
        double theta = Math.atan2(a.getY() - b.getY(), a.getX() - b.getX());
        theta += Math.PI/2.0;
        double angle = Math.toDegrees(theta);

        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }   
    
    public static boolean withinPatch(Circle i, Circle j) {
        double x = Math.pow(i.getX()-j.getX(), 2);
        double y = Math.pow(i.getY()-j.getY(), 2);
        double rad = Math.abs(Math.sqrt(x+y)) + i.getRad() + j.getRad();
        if (rad + 0.2 <= patchSize) {
            return true;
        } else {
            return false;
        }
    }

    // check if all holes are covered by a patch or not.
    public static boolean check() {
        patches.clear();
        for (ArrayList<Circle> group : groups) {
            Circle center = findCenterOfGroup(group);
            patches.add(center);
        }

        double weight = calcWeight(patches);
        if (weight == 0) {
            return true;
        } else {
            return false;
        }
    }

    // find the center of the group no matter how many circles there are in the group.
    public static Circle findCenterOfGroup(ArrayList<Circle> group) {
        int count = group.size();
        double x=0.0, y=0.0;
        for (Circle hole : group) {
            x += hole.getX();
            y += hole.getY();
        }
        x /= count;
        y /= count;
        return new Circle(x, y, patchSize);
    }

    // find a plausible group and replace old groups with new group.
    public static boolean findGroups() {
        // compare 2 groups
        for (int i=0; i<groups.size(); i++) {
            for (int j=i+1; j<groups.size(); j++) {
                // arraylist for adding combined holes to.
                ArrayList<Circle> temp = new ArrayList<Circle>();
                for (Circle a : groups.get(i)) {
                    temp.add(a); // add group a to temp
                }
                for (Circle a : groups.get(j)) {
                    temp.add(a); // add group b to temp
                }
                int count=0;
                Circle center = findCenterOfGroup(temp); // of patch
                for (Circle c : temp) {
                    if (withinPatch(center, c)) {
                        count++; // if hole is covered by patch, increment
                    }
                }
                if (count == temp.size()) { // if all holes covered by a patch
                    groups.remove(j);
                    groups.remove(i); // remove i and j from groups
                    groups.add(temp); // add the new combined group
                    return true;
                }
            }
        }
        return false;
    }

    // checks if the patch is smaller than any hole. 
    public static boolean checkCases(){
        for(Circle hole : holes){
            // Check if any of the holes are larger than patch size
            if (hole.getDiameter() + 0.2 > patchSize){
                System.out.println("This sheet cannot be patched - a hole is greater than a patch.");
                return false;
            }
            // check if the hole overlaps the verticle/horizontal sides of the sheet            
            if (touchEdge(hole, 0.1) > 0.0){
                System.out.println("This sheet cannot be patched as patches will overflow edges.");
                return false;
            }
        }
        return true;
    }

    // check if a a hole plus the border touches the edge.
    public static double touchEdge(Circle hole, double border){      
        double total = 0.0; 
        right = 0;
        left = 0;
        top = 0;
        bottom = 0;
        if (overlapSides.size() > 0){ 
            overlapSides.clear();
        }

        if (hole.getRad()+hole.getX() > sheetX - border) { // over the right side
            right = ((hole.getX()+hole.getRad() - sheetX) - border);
            overlapSides.add("right");
        } 

        if (hole.getX()-hole.getRad() < 0 + border) { // over the left side
            left = (hole.getRad() - hole.getX() - border);
            overlapSides.add("left");
        } 

        if (hole.getRad()+hole.getY() > sheetY - border) { // over the bottom
            bottom = ((hole.getY()+hole.getRad()-sheetY)-border);
            overlapSides.add("bottom");
        } 

        if (hole.getY()-hole.getRad() < 0 + border) { // over the top
            top = (hole.getRad() - hole.getY() + border);
            overlapSides.add("top");
        } 

        total += left + right + top + bottom;
        return total;
    }

    // WORKS
    public static double calcWeight(ArrayList<Circle> list){
        double weight = 0.0;
        // check if the patches overlap.
        for (int i=0; i<list.size(); i++) {
            for (int j=i+1; j<list.size(); j++) {
                // checking if 2 patches overlap
                if (distBetweenEdges(list.get(i), list.get(j)) < 0) {
                    weight += Math.abs(distBetweenEdges(list.get(i), list.get(j)));
                }                
            }
            if (touchEdge(list.get(i), 0) > 0.0) { // patch overlaps edge of sheet
                weight += touchEdge(list.get(i), 0); 

            }                   
        }
        
        // check that all holes are covered, update weight if not
        int c = 0;
        double dist = Double.POSITIVE_INFINITY;
        for (Circle hole : holes) {
            for (Circle patch : list) {
                if (withinPatch(hole, patch)) {
                    c++;
                } else {
                    double x = distBetweenEdges(hole, patch);
                    if (x < dist) {
                        dist = x;
                    }
                }
            }
            if (c == 0) {
                weight += dist;

                dist = Double.POSITIVE_INFINITY;
            }
        }
        return weight;
    }

    // WORKS
    public static double distBetweenEdges(Circle i, Circle j) {
        double sumX = Math.pow(i.getX()-j.getX(), 2);
        double sumY = Math.pow(i.getY()-j.getY(), 2);
        return (Math.sqrt(sumX+sumY)) - i.getRad() - j.getRad();
    }

    public static double round(double value, int places) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}