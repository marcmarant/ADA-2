import java.util.ArrayList;

public class Algoritmo {

    private static Cuadrado teseoPos; // teseo siempre es (0,0) luego se puee optimizar el codigo !!!
    private static Cuadrado minotauroPos;
    private static Laberinto laberinto;
    private static ArrayList<Camino> caminos;
    private static Camino recorridoActual;
    private static ArrayList<Cuadrado> backtracked;
    private static ArrayList<Cuadrado> puertasToBacktrack;
    private static int numPuertas;

    public static int run(Laberinto newLaberinto) {
        laberinto = newLaberinto;
        teseoPos = laberinto.getCuadrado(0, 0);
        minotauroPos = laberinto.getCuadrado((int) Math.floor(laberinto.getMinotauroX()), (int) Math.floor(laberinto.getMinotauroY()));
        caminos = new ArrayList<>();
        backtracked = new ArrayList<>();
        puertasToBacktrack = new ArrayList<>();
        recorridoActual = new Camino(minotauroPos);
        numPuertas = -1;
        int temp = 0;
        while(temp < 40) {
            if (!selectMove(minotauroPos)) { // Se ha hecho backtracking y no se ha encontrado ninguna solucion
                if (numPuertas == -1) break; // Si era el primer intento el laberinto no tiene solucion
                else if (!puertasToBacktrack.isEmpty())  { // Mientras haya puertas sin recorrer se hace bactracking hacia la anterior puerta
                    System.out.println("Backtracking a puerta por no hay camino");
                    minotauroPos = puertasToBacktrack.get(puertasToBacktrack.size() - 1);
                    puertasToBacktrack.remove(puertasToBacktrack.size() - 1);
                    recorridoActual.pop(); // Se elimina el cuadrado restante despues de haber hecho backtracking
                    recorridoActual.addCuadrado(minotauroPos); // Y se añade la nueva posicion inicial
                }
                else break; // Si no quedan puertas por recorrer se acaba el algoritmo
            }
            else if (minotauroPos.isInRange(teseoPos.getPivotX(), teseoPos.getPivotY())) {
                System.out.println("Solucion encontrada");
                // Si se encuentra una solucion se comprueba si es mejor que la anterior
                if (recorridoActual.getNumPuertas() < numPuertas || numPuertas == -1) {
                    numPuertas = recorridoActual.getNumPuertas();
                    puertasToBacktrack = recorridoActual.getCuadradosPrePuertas();
                }
                // Mientras haya puertas sin recorrer se hace backtracking a la anterior puerta y se añade el anterior recorrido al array de caminos TODO solo se recorre una vez una puerta y puede tener mas de otra salida diferente
                if (!puertasToBacktrack.isEmpty()) {
                    System.out.println("Backtracking a puerta por solucion");
                    minotauroPos = puertasToBacktrack.get(puertasToBacktrack.size() - 1);
                    puertasToBacktrack.remove(puertasToBacktrack.size() - 1);
                    caminos.add(recorridoActual);
                    recorridoActual = new Camino(minotauroPos);
                }
                else break; // Si no quedan puertas por recorrer se acaba el algoritmo
            }
            temp++;
        }
        return numPuertas;
    }

    private static boolean selectMove(Cuadrado currentPos) {
        System.out.println(currentPos.getPivotX() + ", " + currentPos.getPivotY()+" P("+ recorridoActual.getNumPuertas()+")"); // TODO Borrar
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
        if (recorridoActual.getNumCuadrados() > 1) { // Si hay nodos recorridos se hace backtracking
            System.out.println("Backtracking obligado");
            recorridoActual.pop();
            backtracked.add(minotauroPos);
            minotauroPos = recorridoActual.getTail();
            return selectMove(minotauroPos); // Se hace backtracking
        }
        System.out.println("No hay camino");
        return false; // No hay camino
    }

    public static boolean canMoveTo(int pivotX, int pivotY, Cuadrado.border border) {
        if (pivotX < 0 || pivotY < 0) return false;
        Cuadrado posToCheck = laberinto.getCuadrado(pivotX, pivotY);
        for (Camino camino: caminos) {
            // Si se llega a un camino se comprueba de forma dinámica si este mejora la solucion actual y se descarta en caso contrario
            if (camino.contains(posToCheck)) {
                int posiblePuerta = 0;
                if (border == Cuadrado.border.PUERTA) posiblePuerta = 1;
                // Para comprobar si la nueva solucion es mejor se suma las puertas del camino a coger + las puertas del recorriddo actual + las puertas de puertasToBactrack (puertas ya recorridas del camino original)
                if (camino.getPuertasFrom(posToCheck)+posiblePuerta+recorridoActual.getNumPuertas()+puertasToBacktrack.size() < numPuertas){
                    System.out.println("SUIIIIIIIII");
                    return true;}
                else
                    return false;
            }
        }
        return !recorridoActual.contains(posToCheck) && !backtracked.contains(posToCheck);
    }
    public static boolean moveUp() {
        if (minotauroPos.getTop() != Cuadrado.border.PARED && canMoveTo(minotauroPos.getPivotX(), minotauroPos.getPivotY() + 1, minotauroPos.getTop())) {
            minotauroPos = laberinto.getCuadrado(minotauroPos.getPivotX(), minotauroPos.getPivotY() + 1);
            recorridoActual.addCuadrado(minotauroPos);
            if (minotauroPos.getBottom() == Cuadrado.border.PUERTA) {
                recorridoActual.addPuerta();
            }
            return true;
        }
        else return false;
    }

    public static boolean moveRight() {
        if (minotauroPos.getRight() != Cuadrado.border.PARED && canMoveTo(minotauroPos.getPivotX() + 1, minotauroPos.getPivotY(), minotauroPos.getRight())) {
            minotauroPos = laberinto.getCuadrado(minotauroPos.getPivotX() + 1, minotauroPos.getPivotY());
            recorridoActual.addCuadrado(minotauroPos);
            if (minotauroPos.getLeft() == Cuadrado.border.PUERTA) {
                recorridoActual.addPuerta();
            }
            return true;
        }
        else return false;
    }

    public static boolean moveDown() {
        if (minotauroPos.getBottom() != Cuadrado.border.PARED && canMoveTo(minotauroPos.getPivotX(), minotauroPos.getPivotY() - 1, minotauroPos.getBottom())) {
            minotauroPos = laberinto.getCuadrado(minotauroPos.getPivotX(), minotauroPos.getPivotY() - 1);
            recorridoActual.addCuadrado(minotauroPos);
            if (minotauroPos.getTop() == Cuadrado.border.PUERTA) {
                recorridoActual.addPuerta();
            }
            return true;
        }
        else return false;
    }

    public static boolean moveLeft() {
        if (minotauroPos.getLeft() != Cuadrado.border.PARED && canMoveTo(minotauroPos.getPivotX() - 1, minotauroPos.getPivotY(), minotauroPos.getLeft())) {
            minotauroPos = laberinto.getCuadrado(minotauroPos.getPivotX() - 1, minotauroPos.getPivotY());
            recorridoActual.addCuadrado(minotauroPos);
            if (minotauroPos.getRight() == Cuadrado.border.PUERTA) {
                recorridoActual.addPuerta();
            }
            return true;
        }
        else return false;
    }

}