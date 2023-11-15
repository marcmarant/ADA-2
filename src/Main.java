import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static final String entryFile = "entrada.txt";
    private static ArrayList<Laberinto> laberintos;

    public static void main(String[] args) {
        laberintos = new ArrayList<>();
        readEntry();
        for (Laberinto laberinto: laberintos)
            draw(laberinto);
    }

    private static void readEntry() {
        try (BufferedReader br = new BufferedReader(new FileReader(entryFile))) {
            int laberintosCount = 0;
            while (true) {
                ArrayList<ArrayList<Cuadrado>> laberinto = new ArrayList<>();
                String line = br.readLine();
                String[] sizes = line.split(" ");
                int numParedes = Integer.parseInt(sizes[0]);
                int numPuertas = Integer.parseInt(sizes[1]);
                if (numParedes == -1 && numPuertas == -1) break;
                int lineCount = 0;
                int x, y, d, t, count;
                // Se leen las paredes
                while (lineCount < numParedes) {
                    line = br.readLine();
                    String[] pared = line.split(" ");
                    x = Integer.parseInt(pared[0]); // posición en X
                    y = Integer.parseInt(pared[1]); // posición en Y
                    d = Integer.parseInt(pared[2]); // direccion (0 || 1)
                    t = Integer.parseInt(pared[3]); // longitud
                    // Se generan los huecos en la matriz (Estos siempre estaran entre 1 y 199) [pero no se si comprobarlo]
                    count = 0;
                    while (laberinto.size() <= x+t) {
                        laberinto.add(new ArrayList<>());
                        while (laberinto.get(count).size() <= y+t) {
                            laberinto.get(count).add(new Cuadrado());
                        }
                        count++;
                    }
                    // Se insertan las paredes
                    if (d == 0) { // La pared esta alineada con el Eje X
                        for (int i = 0; i < t; i++) {
                            laberinto.get(x+i).get(y-1).setTop(Cuadrado.border.PARED); // Para el cuadrado inferior
                            laberinto.get(x+i).get(y).setBottom(Cuadrado.border.PARED); // Para el cuadrado superior
                        }
                    } else if (d == 1) { // La pared esta alineada con el Eje Y
                        for (int i = 0; i < t; i++) {
                            laberinto.get(x-1).get(y+i).setRight(Cuadrado.border.PARED); // Para el cuadrado izquierdo
                            laberinto.get(x).get(y+i).setLeft(Cuadrado.border.PARED); // Para el cuadrado derecho
                        }
                    }
                    lineCount++;
                }
                lineCount = 0;
                t = 1; // La longitud de las puertas siempre sera 1
                // Se leen las puertas
                while (lineCount < numPuertas) {
                    line = br.readLine();
                    String[] puerta = line.split(" ");
                    x = Integer.parseInt(puerta[0]);
                    y = Integer.parseInt(puerta[1]);
                    d = Integer.parseInt(puerta[2]);
                    // Se insertan las puertas
                    try {
                        if (d == 0) { // La puerta esta alineada con el Eje X
                            for (int i = 0; i < t; i++) {
                                laberinto.get(x+i).get(y-1).setTop(Cuadrado.border.PUERTA); // Para el cuadrado inferior
                                laberinto.get(x+i).get(y).setBottom(Cuadrado.border.PUERTA); // Para el cuadrado superior
                            }
                        } else if (d == 1) { // La puerta esta alineada con el Eje Y
                            for (int i = 0; i < t; i++) {
                                laberinto.get(x-1).get(y+i).setRight(Cuadrado.border.PUERTA); // Para el cuadrado izquierdo
                                laberinto.get(x).get(y+i).setLeft(Cuadrado.border.PUERTA); // Para el cuadrado derecho
                            }
                        }
                    } finally { // Si la puerta esta fuera de rango no nos importa pues no se pasara por ella
                        lineCount++;
                    }
                }
                laberintos.add(new Laberinto(laberinto));
                line = br.readLine();
                String[] minotauroPos = line.split(" ");
                laberintos.get(laberintosCount).setMinotauro(Double.parseDouble(minotauroPos[0]), Double.parseDouble(minotauroPos[1]));
                laberintosCount++;
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo "+entryFile);
        }
    }

    private static void draw(Laberinto laberinto) {
        Cuadrado square;
        for (int i = 0; i < laberinto.getNumX(); i++) {
            for (int j = 0; j < laberinto.getNumY()[i]; j++) {
                square = laberinto.getCuadrado(i, j);
                System.out.print("["+square.getTop()+","+square.getRight()+","+square.getBottom()+","+square.getLeft()+"] ");
            }
            System.out.println();
        }
    }

}