import java.util.ArrayList;

public class Laberinto { // Creo que numX y numY no se usan

    private Cuadrado[][] matrix;
    private double minotauroX;
    private double minotauroY;

    public Laberinto(Cuadrado[][] matrix) {
        this.matrix = matrix;
    }

    public Cuadrado getCuadrado(int x, int y) {
        return matrix[x][y];
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