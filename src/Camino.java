/**
 * @auhor marcmar
 */

import java.util.ArrayList;

/**
 * La clase camino consta de un ArrayList con los cuadrados recorridos por ese camino
 * y otro ArrayList de booleanos del mismo tamaño que indica si en el indice correspodiente
 * a un cuadrado se encentra o no una puerta para pasar al siguiente cuadrado del camino
 */
public class Camino {
    private ArrayList<Cuadrado> cuadrados;
    private ArrayList<Boolean> puertas;
    private int numCuadrados;
    private int numPuertas;

    public Camino(Cuadrado cuadradoInicial) {
        cuadrados = new ArrayList<>();
        puertas = new ArrayList<>();
        addCuadrado(cuadradoInicial);
    }

    public void addCuadrado(Cuadrado cuadrado) {
        cuadrados.add(cuadrado);
        puertas.add(false);
        numCuadrados++;
    }

    public void addPuerta() {
        puertas.set(puertas.size() - 1, true);
        numPuertas++;
    }

    public void pop() {
        if (puertas.get(numCuadrados - 1)) numPuertas--;
        cuadrados.remove(numCuadrados - 1);
        puertas.remove(numCuadrados - 1);
        numCuadrados--;
    }

    public int getNumCuadrados() {
        return numCuadrados;
    }

    public int getNumPuertas() {
        return numPuertas;
    }

    public Cuadrado getTail() {
        return cuadrados.get(numCuadrados - 1);
    }

    public boolean contains(Cuadrado cuadrado) {
        return cuadrados.contains(cuadrado);
    }

    public ArrayList<Cuadrado> getCuadradosPrePuertas() {
        ArrayList<Cuadrado> cuadradosPuertas = new ArrayList<>();
        for (int i = 0; i < numCuadrados; i++) {
            if (puertas.get(i)) cuadradosPuertas.add(cuadrados.get(i - 1)); // Se añade el cuadrado anterior a la puerta
        }
        return cuadradosPuertas;
    }

    public int getPuertasFrom(Cuadrado cuadrado) { // cuadrado debe ser parte del camino
        int numPuertas = 0;
        try { // Se empieza a contar desde el siguiente cuadrado (pues el cuadrado dado podria contener la puerta que le da paso desde el cuadrado anterior)
            for (int i = cuadrados.indexOf(cuadrado) + 1; i < cuadrados.size(); i++)
                if (puertas.get(i)) numPuertas++;
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
        return numPuertas;
    }

    public void print() {// TODO BORRAR
        for (Cuadrado cuadrado : cuadrados) {
            System.out.println("RA "+cuadrado.getPivotX() + ", " + cuadrado.getPivotY());
        }
    }

}
