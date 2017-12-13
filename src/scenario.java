import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static java.lang.Double.max;
import static java.lang.Math.min;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;


class Point {
    double x, y;
};

class Polygon {
    Point vertices[];
    int number_of_vertices;
    double cost;
};



class scenario {


    Polygon my_furniture[];
    Polygon my_room;

    ArrayList<Polygon> total_room=new ArrayList<>();
    HashMap<Integer, Polygon[]> total_furniture=new HashMap<>();

    int number_of_rooms;
    int number_of_furniture;



    //rotate a polygon by angle degrees around a point p
    Polygon rotate_by_angle(double angle, Polygon p) {
        float s = (float) sin(angle);
        float c = (float) cos(angle);

        for (int i = 0; i < p.number_of_vertices; i++) {
            p.vertices[i].x = p.vertices[i].x * cos(angle) + p.vertices[i].y * sin(angle);
            p.vertices[i].y = -p.vertices[i].x * sin(angle) + p.vertices[i].y * cos(angle);
        }
        return p;
    }

    Polygon translate_polygon(float nx, float ny, Polygon p) {

        for (int i = 0; i < p.number_of_vertices; i++) {
            p.vertices[i].x = p.vertices[i].x + nx;
            p.vertices[i].y = p.vertices[i].y + ny;
        }

        return p;

    }



    public void parse_room() {
        try {
            String room;
            String furniture;
            File file = new File("E:\\Year2Coursework\\scenario2\\src\\input.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();
            System.out.println("Contents of file:");
            System.out.println(stringBuffer.toString());
            String[] lines = stringBuffer.toString().split("\\n");
            my_room = new Polygon();
            int room_number=0;
            for (String s : lines) {
                String parts_room[] = s.split("#");
                room = parts_room[0];
                System.out.println("Room = " + room);
                String room_vertices[] = room.split(",");
                Polygon p = new Polygon();
                p.vertices=new Point[10000];
                int nr_of_vert = 0;
                for (String f : room_vertices) {
                    double nr = 0;
                    if (f == room_vertices[0]) {
                        String new1[] = f.split(":");
                        if (new1[1].charAt(0) == '(' || new1[1].charAt(1) == '(') {
                            String overlook_parant[] = new1[1].split("\\(");
                            nr = Double.valueOf(overlook_parant[1]);
                            p.vertices[nr_of_vert] = new Point();
                            p.vertices[nr_of_vert].x = nr;
                            System.out.println(p.vertices[nr_of_vert].x);
                        }

                        if (new1[1].charAt(new1[1].length() - 1) == ')') {
                            String overlook_parant[] = new1[1].split("\\)");
                            nr = Double.valueOf(overlook_parant[0]);
                            p.vertices[nr_of_vert++].y = nr;
                        }
                    } else {
                        if (f.charAt(0) == '(' || f.charAt(1) == '(') {
                            String overlook_parant[] = f.split("\\(");
                            nr = Double.valueOf(overlook_parant[1]);
                            p.vertices[nr_of_vert] = new Point();
                            p.vertices[nr_of_vert].x = nr;
                            System.out.println(p.vertices[nr_of_vert].x);
                        }

                        if (f.charAt(f.length() - 1) == ')' || f.charAt(f.length() - 2) == ')') {
                            String overlook_parant[] = f.split("\\)");
                            nr = Double.valueOf(overlook_parant[0]);
                            p.vertices[nr_of_vert].y = nr;
                            System.out.println(p.vertices[nr_of_vert].y);
                            nr_of_vert++;
                        }
                    }

                }
               // nr_of_vert--;
                p.number_of_vertices=nr_of_vert;
                Polygon fin=new Polygon();
                fin.number_of_vertices=nr_of_vert;
                fin.vertices= new Point[nr_of_vert];
                for (int i = 0; i < nr_of_vert; i++) {
                    System.out.println(p.vertices[i].x + " " + p.vertices[i].y);
                    fin.vertices[i]=p.vertices[i];
                }
                my_room = fin;
                total_room.add(my_room);
                furniture = parts_room[1];
                System.out.println("Furniture = " + furniture);
                int nr_of_furniture=0;
                //parse furniture
                String new1[] = furniture.split(";");
                my_furniture= new Polygon[10000000];
                for (String furn : new1) {
                    Polygon pol = new Polygon();
                    pol.vertices=new Point[10000];
                    nr_of_vert=0;
                    String new2[] = furn.split(":");
                    pol.cost = Double.valueOf(new2[0]);
                    System.out.println(pol.cost);
                    System.out.println(new2[1]);
                    String furn_coord[] = new2[1].split(",");
                    for (String coord : furn_coord) {
                        double nr=0;
                        if (coord.charAt(0) == '(' || coord.charAt(1) == '(') {
                            String overlook_parant[] = coord.split("\\(");
                            nr = Double.valueOf(overlook_parant[1]);
                            pol.vertices[nr_of_vert] = new Point();
                            pol.vertices[nr_of_vert].x = nr;
                            System.out.println(pol.vertices[nr_of_vert].x);
                        }

                        if (coord.charAt(coord.length() - 1) == ')' || coord.charAt(coord.length() - 2) == ')') {
                            String overlook_parant[] = coord.split("\\)");
                            nr = Double.valueOf(overlook_parant[0]);
                            pol.vertices[nr_of_vert].y = nr;
                            System.out.println(pol.vertices[nr_of_vert].y);
                            nr_of_vert++;
                        }

                    }
                  //  nr_of_vert--;
                    pol.number_of_vertices=nr_of_vert;
                    Polygon finalp= new Polygon();
                    finalp.number_of_vertices=nr_of_vert;
                    finalp.vertices=new Point[nr_of_vert];
                    finalp.cost=pol.cost;
                    for (int i = 0; i < nr_of_vert; i++) {
                        System.out.println(pol.vertices[i].x + " " + pol.vertices[i].y);
                        finalp.vertices[i]=pol.vertices[i];
                    }
                    my_furniture[nr_of_furniture++]=finalp;

                }
                nr_of_furniture--;
                Polygon final_furn[]=new Polygon[nr_of_furniture];

                for(int i=0;i<nr_of_furniture;i++)
                {
                    final_furn[i]=my_furniture[i];
                }
                for(Polygon poli:final_furn)
                {
                    System.out.println("Cost: "+poli.cost);
                    for(Point pi:poli.vertices)
                    {
                        System.out.print("x coord: " +pi.x+ "y coord " + pi.y);
                    }
                }

                total_furniture.put(room_number,final_furn);
                room_number++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  /*  boolean onSegment(Point p, Point q, Point r)
    {
        if (q.x <= max(p.x, r.x) && q.x >= min(p.x, r.x) &&
                q.y <= max(p.y, r.y) && q.y >= min(p.y, r.y))
            return true;
        return false;
    }

    // To find orientation of ordered triplet (p, q, r).
// The function returns following values
// 0 --> p, q and r are colinear
// 1 --> Clockwise
// 2 --> Counterclockwise
    int orientation(Point p, Point q, Point r)
    {
        double val = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;  // colinear
        return (val > 0)? 1: 2; // clock or counterclock wise
    }

    // The function that returns true if line segment 'p1q1'
// and 'p2q2' intersect.
    boolean doIntersect(Point p1, Point q1, Point p2, Point q2)
    {
        // Find the four orientations needed for general and
        // special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and p2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases
    }

    // Returns true if the point p lies inside the polygon[] with n vertices
    boolean isInside(Point polygon[], int n, Point p)
    {
        // There must be at least 3 vertices in polygon[]
        if (n < 3)  return false;

        // Create a point for line segment from p to infinite
        Point extreme = {Double.POSITIVE_INFINITY, p.y};

        // Count intersections of the above line with sides of polygon
        int count = 0, i = 0;
        do
        {
            int next = (i+1)%n;

            // Check if the line segment from 'p' to 'extreme' intersects
            // with the line segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon[i], polygon[next], p, extreme))
            {
                // If the point 'p' is colinear with line segment 'i-next',
                // then check if it lies on segment. If it lies, return true,
                // otherwise false
                if (orientation(polygon[i], p, polygon[next]) == 0)
                    return onSegment(polygon[i], p, polygon[next]);

                count++;
            }
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        return count&1;  // Same as (count%2 == 1)
    }
*/
    public boolean check_with_objects_placed(Polygon p)
    {
        return true;
    }


    public void afisare()
    {
        for(Polygon p: total_room)
        {
            System.out.println("Room number: " +(total_room.indexOf(p)+1));
            System.out.println("Room number of vertices: "+ p.number_of_vertices);
            for(int i=0;i<p.number_of_vertices;i++)
            System.out.println("Room vertices" +p.vertices[i].x+ " "+ p.vertices[i].y);
        }


        for(Map.Entry<Integer, Polygon[]> entry: total_furniture.entrySet()) {
            Polygon p[]=entry.getValue();
            for(Polygon poli:p) {
                System.out.println("Number of vertices: "+ (poli.number_of_vertices));
                for(Point a:poli.vertices)
                {
                    System.out.println("Coordinates: "+a.x+" "+a.y);
                }
            }
        }
    }

    public static void main(String[] args) {
        scenario scenario = new scenario();

        Polygon dummy=new Polygon();
        Polygon dummy1=new Polygon();
        dummy.number_of_vertices=3;
        dummy.vertices=new Point[3];
        dummy.vertices[0]=new Point();
        dummy.vertices[0].x=2;
        dummy.vertices[0].y=5;
        dummy.vertices[1]=new Point();
        dummy.vertices[1].x=6;
        dummy.vertices[1].y=8;
        dummy.vertices[2]=new Point();
        dummy.vertices[2].x=10;
        dummy.vertices[2].y=7;
        dummy1.number_of_vertices=3;
        dummy1.vertices=new Point[3];
        dummy1.vertices[0]=new Point();
        dummy1.vertices[0].x=7;
        dummy1.vertices[0].y=6;
        dummy1.vertices[1]=new Point();
        dummy1.vertices[1].x=7;
        dummy1.vertices[1].y=7;
        dummy1.vertices[2]=new Point();
        dummy1.vertices[2].x=7;
        dummy1.vertices[2].y=9;
        scenario.parse_room();
        scenario.afisare();
        System.out.print(scenario.isInsidePolygon(dummy,dummy1));
     //   scenario.parse_room();
       // for(Polygon pol: total)
    }
}



