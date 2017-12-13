import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;


class Point {
    double x, y;
};

class Polygon1 {
    Point vertices[];
    int number_of_vertices;
    double cost;
};



class scenario {


    Polygon1 my_furniture[];
    Polygon1 my_room;
    double room_min;
    double room_max;

    ArrayList<Polygon1> total_room=new ArrayList<>();
    HashMap<Integer, Polygon1[]> total_furniture=new HashMap<>();

    int number_of_rooms;
    int number_of_furniture;




    //rotate a polygon by angle degrees around a point p
    Polygon1 rotate_by_angle(double angle, Polygon1 p) {
        float s = (float) sin(angle);
        float c = (float) cos(angle);

        for (int i = 0; i < p.number_of_vertices; i++) {
            p.vertices[i].x = p.vertices[i].x * cos(angle) + p.vertices[i].y * sin(angle);
            p.vertices[i].y = -p.vertices[i].x * sin(angle) + p.vertices[i].y * cos(angle);
        }
        return p;
    }

    Polygon1 translate_polygon(double nx, double ny, Polygon1 p) {

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
            my_room = new Polygon1();
            int room_number=0;
            for (String s : lines) {
                String parts_room[] = s.split("#");
                room = parts_room[0];
                System.out.println("Room = " + room);
                String room_vertices[] = room.split(",");
                Polygon1 p = new Polygon1();
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
                Polygon1 fin=new Polygon1();
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
                my_furniture= new Polygon1[10000000];
                for (String furn : new1) {
                    Polygon1 pol = new Polygon1();
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
                    Polygon1 finalp= new Polygon1();
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
                Polygon1 final_furn[]=new Polygon1[nr_of_furniture];

                for(int i=0;i<nr_of_furniture;i++)
                {
                    final_furn[i]=my_furniture[i];
                }
                for(Polygon1 poli:final_furn)
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

    

    public Coordinate[] transformCoordinates(Polygon1 f){
        int i;
        ArrayList<Point> points = new ArrayList<>();
        for(i=0;i<f.number_of_vertices;i++)
        {
            Point p=new Point();
            p.x=f.vertices[i].x;
            p.y=f.vertices[i].y;
            points.add(p);
        }
        Coordinate [] coordinates = new Coordinate[points.size() + 1];
        for(i  = 0; i < points.size(); i++){
            coordinates[i] =  (new Coordinate(points.get(i).x, points.get(i).y));
            //System.out.println(coordinates[i].x + "vs " + points.get(i).x_coordinate + " " + coordinates[i].y + "vs " + points.get(i).y_coordinate);
        }
        coordinates[i] =  (new Coordinate(points.get(0).x, points.get(0).y));

        return coordinates;
    }

    public  ArrayList<Polygon> transforList(ArrayList<Polygon1> figures){
        ArrayList<Polygon> result = new ArrayList<>();
        GeometryFactory geometryFactory = new GeometryFactory();
        for(Polygon1 f : figures){

            LinearRing ring = geometryFactory.createLinearRing(transformCoordinates(f));
            LinearRing holes[] = null; // use LinearRing[] to represent holes
            Polygon polygon = geometryFactory.createPolygon(ring, holes);
            result.add(polygon);
        }
        return result;
    }




    public void afisare()
    {
        for(Polygon1 p: total_room)
        {
            System.out.println("Room number: " +(total_room.indexOf(p)+1));
            System.out.println("Room number of vertices: "+ p.number_of_vertices);
            for(int i=0;i<p.number_of_vertices;i++)
            System.out.println("Room vertices" +p.vertices[i].x+ " "+ p.vertices[i].y);
        }


        for(Map.Entry<Integer, Polygon1[]> entry: total_furniture.entrySet()) {
            Polygon1 p[]=entry.getValue();
            for(Polygon1 poli:p) {
                System.out.println("Number of vertices: "+ (poli.number_of_vertices));
                for(Point a:poli.vertices)
                {
                    System.out.println("Coordinates: "+a.x+" "+a.y);
                }
            }
        }
    }


    Polygon1 random_positionate(Polygon1 p, Polygon1 my_room)
    {
        double xmax=0.0;
        double xmin=10000000000.0;
        double ymax=0.0;
        double ymin=10000000000.0;
        for(int i=0;i<my_room.number_of_vertices;i++)
        {
            if(my_room.vertices[i].x>xmax)
                xmax=my_room.vertices[i].x;
            if(my_room.vertices[i].x<xmin)
                xmin=my_room.vertices[i].x;
            if(my_room.vertices[i].y>ymax)
                ymax=my_room.vertices[i].y;
            if(my_room.vertices[i].y<ymin)
                ymin=my_room.vertices[i].y;
        }

        Random r = new Random();
        double low = xmin;
        double high = xmax;
        double xrandom = low + (high - low) * r.nextDouble();

        r = new Random();
        low = ymin;
        high = ymax;
        double yrandom = low + (high - low) * r.nextDouble();



        return poli;

    }

    boolean check_can_positionate(Polygon1 p, Polygon1 my_room)
    {
        return checkIfFigureIsInsideAnotherFigure(p, my_room);
    }

    void start_positionating(Polygon1 furniture[], Polygon1 room)
    {
        for(int i)
    }

    public static void main(String[] args) {
        scenario scenario = new scenario();

        Polygon1 dummy=new Polygon1();
        Polygon1 dummy1=new Polygon1();
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
        dummy1.vertices[0].x=6;
        dummy1.vertices[0].y=7;
        dummy1.vertices[1]=new Point();
        dummy1.vertices[1].x=7;
        dummy1.vertices[1].y=7.5;
        dummy1.vertices[2]=new Point();
        dummy1.vertices[2].x=8;
        dummy1.vertices[2].y=7;
        scenario.parse_room();
        scenario.afisare();
        System.out.print(scenario.checkIfFigureIsInsideAnotherFigure(dummy1,dummy));
     //   scenario.parse_room();

    }
}



