import java.util.ArrayList;

public class Laberinto {

    private final ArrayList<ArrayList<Cuadrado>> matrix;
    private final int numX;
    private final int[] numY;
    private double minotauroX;
    private double minotauroY;

    public Laberinto(ArrayList<ArrayList<Cuadrado>> matrix) {
        this.matrix = matrix;
        numX = matrix.size();
        numY = new int[numX];
        for (int i = 0; i < numX; i++) {
            numY[i] = matrix.get(i).size();
        }

    }

    public Cuadrado getCuadrado(int x, int y) {
        return matrix.get(x).get(y);
    }

    public int getNumX() {
        return numX;
    }

    public int[] getNumY() {
        return numY;
    }

    public double getMinotauroX() {
        return minotauroX;
    }

    public double getMinotauroY() {
        return minotauroY;
    }

    public void setMinotauro(double newMinotauroX, double newMinotauroY) {
        minotauroX = newMinotauroX;
        minotauroY = newMinotauroY;
    }

}