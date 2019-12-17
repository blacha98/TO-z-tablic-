public class Point {
    private int X;
    private int Y;

    public Point(int x, int y) {
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public double distance(Point A, Point B){
        return(Math.round((Math.sqrt(Math.pow((A.getX() - B.getX()),2) + Math.pow((A.getY() - B.getY()), 2)))*100.0) / 100.0);
    }
}
