package org.cemaforo.aparcamiento;

import java.util.Random;

/**
 * Representa un vehículo que compite por una plaza de aparcamiento.
 * Cada coche se ejecuta en su propio hilo gracias a la interfaz {@link Runnable}.
 */
public class Coche implements Runnable {

    // Cada coche permanecerá como mínimo un segundo aparcado.
    private static final int TIEMPO_MINIMO_MS = 1_000;
    // Valor adicional aleatorio para repartir las salidas entre uno y cuatro segundos.
    private static final int TIEMPO_ALEATORIO_MS = 3_000;
    private static final Random RANDOM = new Random();

    private final Aparcamiento aparcamiento;
    private final String nombre;

    /**
     * Construye un coche con un identificador y acceso al aparcamiento compartido.
     *
     * @param aparcamiento instancia común para todos los coches.
     * @param nombre       identificador del coche, usado en los mensajes por consola.
     */
    public Coche(Aparcamiento aparcamiento, String nombre) {
        this.aparcamiento = aparcamiento;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            // El coche intenta entrar y, si no hay hueco inmediato, quedará bloqueado por el semáforo.
            aparcamiento.entrar(nombre);
            aparcarDuranteTiempoAleatorio();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.printf("%s fue interrumpido antes de completar su estacionamiento.%n", nombre);
            return;
        }

        aparcamiento.salir(nombre);
    }

    private void aparcarDuranteTiempoAleatorio() throws InterruptedException {
        // Sumamos el mínimo con un extra aleatorio para simular tiempos desiguales en cada ejecución.
        int tiempoAparcado = TIEMPO_MINIMO_MS + RANDOM.nextInt(TIEMPO_ALEATORIO_MS);
        Thread.sleep(tiempoAparcado);
    }
}


