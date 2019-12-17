import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int pop = 20;
    public static void main(String[] args) {
        ArrayList<ArrayList<Double>> aMatrix = new ArrayList<ArrayList<Double>>();
        Island isl = new Island(pop, 4, 4);
        ArrayList<Point> list = new ArrayList<Point>();
        list = isl.getLocations();
        list.forEach(point -> System.out.println(point.toString()));
        Point A = list.get(0);
        Point B = list.get(1);
        double x = A.distance(A, B);
        System.out.println(x);
        for(int i = 0; i<isl.getPopulation(); i++){
            for(int j=0; j<isl.getPopulation(); j++){
                System.out.print(isl.getAdjMatrix()[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }

        int[] shortestPath = isl.Algorithm(7000, 0.9995, 10000, isl.getAdjMatrix());
        for(int i=0; i<shortestPath.length; i++){
            System.out.print(shortestPath[i] + " ");
        }

    }
}
