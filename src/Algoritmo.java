import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Algoritmo {

    private static Cuadrado teseoPos;
    private static Laberinto laberinto;
    private static ArrayList<Cuadrado> recorridos;
    private static ArrayList<Cuadrado> backtracked;
    private static int puertasCont;

    public static int run(Laberinto newLaberinto) {
        laberinto = newLaberinto;
        teseoPos = laberinto.getCuadrado(0, 0);
        recorridos = new ArrayList<>();
        backtracked = new ArrayList<>();
        puertasCont = 0;
        int temp = 0;
        while(true) {
            // System.out.println("posX: " + teseoPos.getPivotX() + " posY: " + teseoPos.getPivotY() + " ||| difX: " + (laberinto.getMinotauroX() - teseoPos.getPivotX()) + " difY: " + (laberinto.getMinotauroY() - teseoPos.getPivotY()));
            if (!selectMove(teseoPos)) break;
            else if (teseoPos.isInRange(laberinto.getMinotauroX(), laberinto.getMinotauroY())) return puertasCont;
            temp ++;
            // System.out.println();
        }
        return -1;
    }

    private static boolean selectMove(Cuadrado currentPos) {
        double[] modMoves = new double[4];
        double[] sortedMoves = new double[4];
        double difX = currentPos.getPivotX() - laberinto.getMinotauroX();
        double difY = currentPos.getPivotY() - laberinto.getMinotauroY();
        modMoves[0] = Math.sqrt(Math.pow(difX, 2) + Math.pow(difY + 1, 2));
        modMoves[1] = Math.sqrt(Math.pow(difX + 1, 2) + Math.pow(difY, 2));
        modMoves[2] = Math.sqrt(Math.pow(difX, 2) + Math.pow(difY - 1, 2));
        modMoves[3] = Math.sqrt(Math.pow(difX - 1, 2) + Math.pow(difY, 2));
        sortedMoves = modMoves.clone();
        for (int i = 1; i < 4; ++i) {
            double k = sortedMoves[i];
            int j = i - 1;
            while (j >= 0 && sortedMoves[j] > k) {
                sortedMoves[j + 1] = sortedMoves[j];
                j = j - 1;
            }
            sortedMoves[j + 1] = k;
        }
        // System.out.println("Moves: " + modMoves[0] + " " + modMoves[1] + " " + modMoves[2] + " " + modMoves[3]);
        // System.out.println("sortedMoves: " + sortedMoves[0] + " " + sortedMoves[1] + " " + sortedMoves[2] + " " + sortedMoves[3]);
        for (int i = 0; i < 4; ++i) {
            if (sortedMoves[i] == modMoves[0]) {
                if (moveUp()) break;
                modMoves[0] = -1; // Se asigna a -1 para que no se vuelva a comparar
            } else if (sortedMoves[i] == modMoves[1]) {
                if (moveRight()) break;
                modMoves[1] = -1;
            } else if (sortedMoves[i] == modMoves[2]) {
                if (moveDown()) break;
                modMoves[2] = -1;
            } else if (sortedMoves[i] == modMoves[3]) {
                if (moveLeft()) break;
                // No se asigna a -1 pues no hay posibilidad de que se compare antes que los demás
            } else {
                System.out.println("no hay camino");
                // TODO implementar backtracking
                return false;
            }
        }
        return true;
    }

    public static boolean moveUp() {
        if (teseoPos.getTop() != Cuadrado.border.PARED && !recorridos.contains(laberinto.getCuadrado(teseoPos.getPivotX(), teseoPos.getPivotY() + 1)) && !backtracked.contains(laberinto.getCuadrado(teseoPos.getPivotX(), teseoPos.getPivotY() + 1))) {
            teseoPos = laberinto.getCuadrado(teseoPos.getPivotX(), teseoPos.getPivotY() + 1);
            recorridos.add(teseoPos);
            if (teseoPos.getBottom() == Cuadrado.border.PUERTA) puertasCont++;
            return true;
        }
        else return false;
    }

    public static boolean moveRight() {
        if (teseoPos.getRight() != Cuadrado.border.PARED && !recorridos.contains(laberinto.getCuadrado(teseoPos.getPivotX() + 1, teseoPos.getPivotY())) && !backtracked.contains(laberinto.getCuadrado(teseoPos.getPivotX() + 1, teseoPos.getPivotY()))) {
            teseoPos = laberinto.getCuadrado(teseoPos.getPivotX() + 1, teseoPos.getPivotY());
            recorridos.add(teseoPos);
            if (teseoPos.getLeft() == Cuadrado.border.PUERTA) puertasCont++;
            return true;
        }
        else return false;
    }

    public static boolean moveDown() {
        if (teseoPos.getBottom() != Cuadrado.border.PARED && !recorridos.contains(laberinto.getCuadrado(teseoPos.getPivotX(), teseoPos.getPivotY() - 1)) && !backtracked.contains(laberinto.getCuadrado(teseoPos.getPivotX(), teseoPos.getPivotY() - 1))) {
            teseoPos = laberinto.getCuadrado(teseoPos.getPivotX(), teseoPos.getPivotY() - 1);
            recorridos.add(teseoPos);
            if (teseoPos.getTop() == Cuadrado.border.PUERTA) puertasCont++;
            return true;
        }
        else return false;
    }

    public static boolean moveLeft() {
        if (teseoPos.getLeft() != Cuadrado.border.PARED && !recorridos.contains(laberinto.getCuadrado(teseoPos.getPivotX() - 1, teseoPos.getPivotY())) && !backtracked.contains(laberinto.getCuadrado(teseoPos.getPivotX() - 1, teseoPos.getPivotY()))) {
            teseoPos = laberinto.getCuadrado(teseoPos.getPivotX() - 1, teseoPos.getPivotY());
            recorridos.add(teseoPos);
            if (teseoPos.getRight() == Cuadrado.border.PUERTA) puertasCont++;
            return true;
        }
        else return false;
    }

}