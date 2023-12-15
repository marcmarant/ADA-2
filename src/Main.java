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
        for (Laberinto laberinto : laberintos) {
            // draw(laberinto);
            System.out.println(Algoritmo.run(laberinto));
        }
    }

    private static void readEntry() {
        try (BufferedReader br = new BufferedReader(new FileReader(entryFile))) {
            int laberintosCount = 0;
            while (true) {
                Cuadrado[][] laberinto = new Cuadrado[200][200];
                for (int i = 0; i < 200; i++) {
                    for (int j = 0; j < 200; j++) {
                        laberinto[i][j] = new Cuadrado(i, j);
                    }
                }
                String line = br.readLine();
                String[] sizes = line.split(" ");
                int numParedes = Integer.parseInt(sizes[0]);
                int numPuertas = Integer.parseInt(sizes[1]);
                if (numParedes == -1 && numPuertas == -1) break;
                int lineCount = 0;
                int x, y, d, t, xCount, yCount;
                // Se leen las paredes
                while (lineCount < numParedes) {
                    line = br.readLine();
                    String[] pared = line.split(" ");
                    x = Integer.parseInt(pared[0]); // posición en X
                    y = Integer.parseInt(pared[1]); // posición en Y
                    d = Integer.parseInt(pared[2]); // direccion (0 || 1)
                    t = Integer.parseInt(pared[3]); // longitud
                    // Se insertan las paredes
                    if (d == 0) { // La pared esta alineada con el Eje X
                        for (int i = 0; i < t; i++) {
                            laberinto[x+i][y-1].setTop(Cuadrado.border.PARED); // Para el cuadrado inferior
                            laberinto[x+i][y].setBottom(Cuadrado.border.PARED); // Para el cuadrado superior
                        }
                    } else if (d == 1) { // La pared esta alineada con el Eje Y
                        for (int i = 0; i < t; i++) {
                            laberinto[x-1][y+i].setRight(Cuadrado.border.PARED); // Para el cuadrado izquierdo
                            laberinto[x][y+i].setLeft(Cuadrado.border.PARED); // Para el cuadrado derecho
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
                                laberinto[x+i][y-1].setTop(Cuadrado.border.PUERTA); // Para el cuadrado inferior
                                laberinto[x+i][y].setBottom(Cuadrado.border.PUERTA); // Para el cuadrado superior
                            }
                        } else if (d == 1) { // La puerta esta alineada con el Eje Y
                            for (int i = 0; i < t; i++) {
                                laberinto[x-1][y+i].setRight(Cuadrado.border.PUERTA); // Para el cuadrado izquierdo
                                laberinto[x][y+i].setLeft(Cuadrado.border.PUERTA); // Para el cuadrado derecho
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

}