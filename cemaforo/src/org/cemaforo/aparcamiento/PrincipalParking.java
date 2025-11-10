package org.cemaforo.aparcamiento;

import java.util.ArrayList;
import java.util.List;

/**
 * Punto de entrada de la aplicación. Lanza múltiples hilos que simulan
 * la entrada y salida de coches en un aparcamiento con plazas limitadas.
 */
public final class PrincipalParking {

    private static final int PLAZAS = 3;
    private static final int TOTAL_COCHES = 7;

    private PrincipalParking() {
        // Esta clase no debe instanciarse.
    }

    /**
     * Método principal. Crea el aparcamiento y arranca los hilos que representan a los coches.
     *
     * @param args parámetros de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Creamos el recurso compartido con la capacidad que marca el enunciado.
        Aparcamiento aparcamiento = new Aparcamiento(PLAZAS);
        // Lanzamos los coches y guardamos los hilos para poder esperar a que terminen.
        List<Thread> hilos = crearYArrancarCoches(aparcamiento);
        esperarFinalizacion(hilos);
        System.out.println("Simulación completada.");
    }

    private static List<Thread> crearYArrancarCoches(Aparcamiento aparcamiento) {
        List<Thread> hilos = new ArrayList<>();
        for (int i = 1; i <= TOTAL_COCHES; i++) {
            // El nombre simbólico ayuda a seguir los mensajes por consola.
            Coche coche = new Coche(aparcamiento, "Coche-" + i);
            Thread hilo = new Thread(coche, "Hilo-" + i);
            hilo.start();
            hilos.add(hilo);
        }
        return hilos;
    }

    private static void esperarFinalizacion(List<Thread> hilos) {
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("La espera de finalización fue interrumpida.");
                break;
            }
        }
    }
}


