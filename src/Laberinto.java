import java.util.ArrayList;

public class Laberinto { // Creo que numX y numY no se usan

    private ArrayList<ArrayList<Cuadrado>> matrix;
    private double minotauroX;
    private double minotauroY;

    public Laberinto(ArrayList<ArrayList<Cuadrado>> matrix) {
        this.matrix = matrix;
    }

    public Cuadrado getCuadrado(int x, int y) {
        try {
            return matrix.get(x).get(y);
        } catch (IndexOutOfBoundsException e) {
            try {
                matrix.get(x).add(new Cuadrado(x, y));
                return matrix.get(x).get(y);
            } catch (IndexOutOfBoundsException e1) {
                matrix.add(new ArrayList<>());
                int tempY = 0;
                while (tempY < y) {
                    tempY++;
                    matrix.get(x).add(new Cuadrado(x, tempY));
                }
                return matrix.get(x).get(y);
            }
        }
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