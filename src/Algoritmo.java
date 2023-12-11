import java.util.ArrayList;

public class Algoritmo {

    private static Cuadrado teseoPos; // teseo siempre es (0,0) luego se puee optimizar el codigo !!!
    private static Cuadrado minotauroPos;
    private static Laberinto laberinto;
    private static ArrayList<Cuadrado> recorridos;
    private static ArrayList<Cuadrado> backtracked;
    private static int puertasCont;

    public static int run(Laberinto newLaberinto) {
        laberinto = newLaberinto;
        teseoPos = laberinto.getCuadrado(0, 0);
        minotauroPos = laberinto.getCuadrado((int) Math.floor(laberinto.getMinotauroX()), (int) Math.floor(laberinto.getMinotauroY()));
        recorridos = new ArrayList<>();
        recorridos.add(minotauroPos);
        backtracked = new ArrayList<>();
        puertasCont = 0;
        while(true) {
            if (!selectMove(minotauroPos)) break;
            else if (minotauroPos.isInRange(teseoPos.getPivotX(), teseoPos.getPivotY())) return puertasCont;
        }
        return -1;
    }

    private static boolean selectMove(Cuadrado currentPos) {
        double[] modMoves = new double[4];
        double difX = currentPos.getPivotX() - teseoPos.getPivotX();
        double difY = currentPos.getPivotY() - teseoPos.getPivotY();
        modMoves[0] = Math.sqrt(Math.pow(difX, 2) + Math.pow(difY + 1, 2));
        modMoves[1] = Math.sqrt(Math.pow(difX + 1, 2) + Math.pow(difY, 2));
        modMoves[2] = Math.sqrt(Math.pow(difX, 2) + Math.pow(difY - 1, 2));
        modMoves[3] = Math.sqrt(Math.pow(difX - 1, 2) + Math.pow(difY, 2));
        double[] sortedMoves = modMoves.clone();
        for (int i = 1; i < 4; ++i) {
            double k = sortedMoves[i];
            int j = i - 1;
            while (j >= 0 && sortedMoves[j] > k) {
                sortedMoves[j + 1] = sortedMoves[j];
                j = j - 1;
            }
            sortedMoves[j + 1] = k;
        }
        for (int i = 0; i < 4; ++i) {
            if (sortedMoves[i] == modMoves[0]) {
                if (moveUp()) return true;
                modMoves[0] = -1; // Se asigna a -1 para que no se vuelva a comparar
            } else if (sortedMoves[i] == modMoves[1]) {
                if (moveRight()) return true;
                modMoves[1] = -1;
            } else if (sortedMoves[i] == modMoves[2]) {
                if (moveDown()) return true;
                modMoves[2] = -1;
            } else if (sortedMoves[i] == modMoves[3]) {
                if (moveLeft()) return true;
                // No se asigna a -1 pues no hay posibilidad de que se compare antes que los demás
            }
        }
        if (recorridos.size() > 1) { // Si hay nodos recorridos se hace backtracking
            Cuadrado prevPos =  recorridos.get(recorridos.size() - 1);
            recorridos.remove(minotauroPos);
            backtracked.add(minotauroPos);
            minotauroPos = recorridos.get(recorridos.size() - 1);
            if ( // Se comprueba si al hacer backtracking se pasa por una puerta
                minotauroPos.getPivotX() - prevPos.getPivotX() == 1 && minotauroPos.getLeft() == Cuadrado.border.PUERTA ||
                minotauroPos.getPivotX() - prevPos.getPivotX() == -1 && minotauroPos.getRight() == Cuadrado.border.PUERTA ||
                minotauroPos.getPivotX() - prevPos.getPivotX() == 1 && minotauroPos.getBottom() == Cuadrado.border.PUERTA ||
                minotauroPos.getPivotX() - prevPos.getPivotX() == -1 && minotauroPos.getTop() == Cuadrado.border.PUERTA
            ) puertasCont--;
            return selectMove(minotauroPos); // Se hace backtracking
        }
        return false; // No hay camino
    }

    public static boolean moveUp() {
        if (minotauroPos.getTop() != Cuadrado.border.PARED && !recorridos.contains(laberinto.getCuadrado(minotauroPos.getPivotX(), minotauroPos.getPivotY() + 1)) && !backtracked.contains(laberinto.getCuadrado(minotauroPos.getPivotX(), minotauroPos.getPivotY() + 1))) {
            minotauroPos = laberinto.getCuadrado(minotauroPos.getPivotX(), minotauroPos.getPivotY() + 1);
            recorridos.add(minotauroPos);
            if (minotauroPos.getBottom() == Cuadrado.border.PUERTA) puertasCont++;
            return true;
        }
        else return false;
    }

    public static boolean moveRight() {
        if (minotauroPos.getRight() != Cuadrado.border.PARED && !recorridos.contains(laberinto.getCuadrado(minotauroPos.getPivotX() + 1, minotauroPos.getPivotY())) && !backtracked.contains(laberinto.getCuadrado(minotauroPos.getPivotX() + 1, minotauroPos.getPivotY()))) {
            minotauroPos = laberinto.getCuadrado(minotauroPos.getPivotX() + 1, minotauroPos.getPivotY());
            recorridos.add(minotauroPos);
            if (minotauroPos.getLeft() == Cuadrado.border.PUERTA) puertasCont++;
            return true;
        }
        else return false;
    }

    public static boolean moveDown() {
        if (minotauroPos.getBottom() != Cuadrado.border.PARED && !recorridos.contains(laberinto.getCuadrado(minotauroPos.getPivotX(), minotauroPos.getPivotY() - 1)) && !backtracked.contains(laberinto.getCuadrado(minotauroPos.getPivotX(), minotauroPos.getPivotY() - 1))) {
            minotauroPos = laberinto.getCuadrado(minotauroPos.getPivotX(), minotauroPos.getPivotY() - 1);
            recorridos.add(minotauroPos);
            if (minotauroPos.getTop() == Cuadrado.border.PUERTA) puertasCont++;
            return true;
        }
        else return false;
    }

    public static boolean moveLeft() {
        if (minotauroPos.getLeft() != Cuadrado.border.PARED && !recorridos.contains(laberinto.getCuadrado(minotauroPos.getPivotX() - 1, minotauroPos.getPivotY())) && !backtracked.contains(laberinto.getCuadrado(minotauroPos.getPivotX() - 1, minotauroPos.getPivotY()))) {
            minotauroPos = laberinto.getCuadrado(minotauroPos.getPivotX() - 1, minotauroPos.getPivotY());
            recorridos.add(minotauroPos);
            if (minotauroPos.getRight() == Cuadrado.border.PUERTA) puertasCont++;
            return true;
        }
        else return false;
    }

}