package org.cemaforo.aparcamiento;

import java.util.concurrent.Semaphore;

/**
 * Gestiona las plazas de un aparcamiento mediante un {@link Semaphore}.
 * El semáforo garantiza que nunca haya más vehículos dentro que plazas disponibles.
 */
public class Aparcamiento {

    // Semáforo que regula cuántas plazas están ocupadas en cada momento.
    private final Semaphore controlPlazas;
    // Capacidad total del aparcamiento, se usa para los mensajes y cálculos.
    private final int capacidadMaxima;

    /**
     * Crea un aparcamiento con una capacidad fija.
     *
     * @param capacidad número total de plazas disponibles.
     * @throws IllegalArgumentException si la capacidad es menor o igual a cero.
     */
    public Aparcamiento(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad del aparcamiento debe ser mayor que cero.");
        }
        this.capacidadMaxima = capacidad;
        // El modo justo (fair) evita favoritismos: el primer coche que llega es el primero en entrar.
        this.controlPlazas = new Semaphore(capacidad, true);
    }

    /**
     * Solicita la entrada de un vehículo al aparcamiento.
     *
     * @param nombreCoche identificador legible del coche.
     * @throws InterruptedException si el hilo es interrumpido mientras espera por una plaza.
     */
    public void entrar(String nombreCoche) throws InterruptedException {
        // Probamos primero con tryAcquire para saber si el coche entra sin esperar.
        boolean adquiridoAlInstante = controlPlazas.tryAcquire();
        if (!adquiridoAlInstante) {
            mostrarEstado(nombreCoche, "espera", calcularOcupadas(), controlPlazas.getQueueLength());
            controlPlazas.acquire();
        }
        mostrarEstado(nombreCoche, "entra", calcularOcupadas(), controlPlazas.getQueueLength());
    }

    /**
     * Libera la plaza ocupada por un vehículo que abandona el aparcamiento.
     *
     * @param nombreCoche identificador legible del coche.
     */
    public void salir(String nombreCoche) {
        // Devolver el permiso al semáforo es lo que libera la plaza para el siguiente coche.
        controlPlazas.release();
        mostrarEstado(nombreCoche, "sale", calcularOcupadas(), controlPlazas.getQueueLength());
    }

    private void mostrarEstado(String nombreCoche, String accion, int plazasOcupadas, int enCola) {
        int plazasLibres = capacidadMaxima - plazasOcupadas;
        // El formato da una visión rápida del estado general del aparcamiento en cada evento.
        System.out.printf(
                "[Plazas %d/%d | Espera %d] %-8s %-6s | Ocupadas: %-2d | Libres: %-2d%n",
                plazasOcupadas,
                capacidadMaxima,
                enCola,
                nombreCoche,
                accion.toUpperCase(),
                plazasOcupadas,
                plazasLibres
        );
    }

    private int calcularOcupadas() {
        return capacidadMaxima - controlPlazas.availablePermits();
    }
}

