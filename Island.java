import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;

import java.util.*;

public class Island {

    private int id;
    private int population;
    private int migrationSize;
    private int migrationFrequency;
    //added
    ArrayList<Point> locations;
    //ArrayList<ArrayList<Double>> adjMatrix;
    private double[][] adjMatrix;


    public Island() {
    }


    public double[][] getAdjMatrix() {
        return adjMatrix;
    }

    public Island(int population, int migrationSize, int migrationFrequency) {
        this.population = population;
        this.migrationSize = migrationSize;
        this.migrationFrequency = migrationFrequency;
        this.locations = new ArrayList<>();
        quater(population, this.locations);
        this.adjMatrix = new double[Main.pop][Main.pop];
        distanceMatrix(this.locations);

    }

    public Island(int population) {
        this.population = population;
        this.migrationSize = 1;
        this.migrationFrequency = 1;
    }

    public int getId() {
        return id;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }


    @Override
    public String toString() {
        return "Island{" +
                "id='" + id +
                ", population=" + population +
                '}';
    }

    public ArrayList<Point> getLocations() {
        return locations;
    }

    //public ArrayList<ArrayList<Double>> getAdjMatrix() {
    //    return adjMatrix;
    //}

    //metoda do kwaterowania mieszkańców na wyspie
    private void quater(int population, ArrayList<Point> list) {
        Random randInt = new Random();
        for (int i = 0; i < population; i++) {
            Point location = new Point(randInt.nextInt(100), randInt.nextInt(100));
            list.add(location);
        }
    }

    //tworzenie macierzy adjascencji
//    private void distanceMatrix(ArrayList<Point> list, List<ArrayList<Double>> matrix) {
//        ArrayList<Double> tmp = new ArrayList();
//
//        for (int i = 0; i < list.size(); i++) {
//            for (int j = 0; j < list.size(); j++) {
//                tmp.add(list.get(i).distance(list.get(i), list.get(j)));
//            }
//            //System.out.println("tmp: " + tmp.size());
//            matrix.add(tmp);
//            //System.out.println("matrix: " + matrix.size());
//            tmp.clear();
//            //System.out.println("tmp2: " + tmp.size());
//        }
//        //System.out.println(matrix.get(0).get(0));
//        //System.out.println(matrix.);
//    }

    private void distanceMatrix(ArrayList<Point> list) {

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                this.adjMatrix[i][j] = list.get(i).distance(list.get(i), list.get(j));
            }
        }
    }

    private int[] FindNewWayAS(int[] list){
        Random rand = new Random();
        int a = rand.nextInt(Main.pop-1);
        int b = rand.nextInt(Main.pop-2);
        if(a == b){
            b = b + 1;
        }
        int tmp;
        tmp = list[a];
        list[a] = list[b];
        list[b] = tmp;
        return list;
    }

    private double Energy(int[] list, double[][] matrix){
        double energy = 0;
        for(int i=0; i<list.length-1; i++){
            energy += matrix[list[i]][list[i+1]];
        }
        energy += matrix[list[(list.length-1)]][list[0]];
        return energy;
    }

    private int[] prepareState(){
        int[] order = new int[Main.pop];
        for(int i=0; i<Main.pop; i++) {
            order[i] = i;
        }
        return order;
    }

    private int[] copyArray(int[] array){
        int[] result = new int[array.length];
        for(int i=0; i<array.length; i++){
            result[i] = array[i];
        }
        return result;
    }


    public int[] Algorithm (int temp, double coolingRate, int iterations, double[][] aMAtrix){
        int[] currentState = prepareState();
        System.out.println(Energy(currentState, aMAtrix));
        int[] bestState = copyArray(currentState);
        int currentTemperature = temp;
        ArrayList<Dot> annealProgres = new ArrayList<Dot>();
        int[] nextState;
        double currentEnergy = 0;
        double nextEnergy = 0;
        double probability = 0;
        Random generator = new Random();
        for(int i=0; i<iterations; i++) {
            nextState = FindNewWayAS(currentState);
            currentEnergy = Energy(currentState, aMAtrix);
            nextEnergy = Energy(nextState, aMAtrix);
            probability = Math.exp(((Math.sqrt(temp) * (currentEnergy - nextEnergy)) / currentTemperature));
            if (currentEnergy > nextEnergy || probability > generator.nextDouble()) {
                currentState = copyArray(nextState);
                annealProgres.add(new Dot(i, Energy(currentState, aMAtrix)));
                if (Energy(bestState, aMAtrix) > nextEnergy){
                    bestState = copyArray(nextState);
                }
            }
            currentTemperature *= coolingRate;

        }
        annealProgres.add(new Dot(iterations, Energy(bestState, aMAtrix)));
        //final LineChart<Number,Number> lineChart = new LineChart<Number,Number>();
        System.out.println(Energy(bestState, aMAtrix));
        return bestState;
    }
}
