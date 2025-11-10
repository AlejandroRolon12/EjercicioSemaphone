# EjercicioSemaphone
# Control de Acceso Concurrente a un Aparcamiento

## 1. Descripción General

### 1.1 Objetivos del ejercicio
- Comprender el uso de `Semaphore` para gestionar recursos con disponibilidad limitada.
- Practicar la sincronización de hilos al controlar el acceso a un aparcamiento compartido.

### 1.2 Descripción del ejercicio
- El programa simula un aparcamiento de tres plazas al que acceden siete coches de forma concurrente.
- Se implementan tres clases principales: `Aparcamiento`, `Coche` y `PrincipalParking`.
- `Aparcamiento` encapsula el semáforo y evita que haya más de tres coches dentro al mismo tiempo.
- `Coche` modela cada hilo y permanece estacionado entre uno y cuatro segundos antes de liberar la plaza.
- `PrincipalParking` crea el aparcamiento, genera los siete vehículos e inicia sus hilos.

### 1.3 Documentación y presentación
- Cada clase está documentada con JavaDoc y comentarios contextuales que explican las decisiones clave.
- El código se organiza en el paquete `org.cemaforo.aparcamiento` para mantener una estructura limpia.

## 2. ¿Por qué usar Semaphore?
- `Semaphore` modela naturalmente el número de plazas libres, evita sobrecargas y simplifica el control de espera.
- A diferencia de `synchronized`, permite manejar varios permisos sin escribir contadores manuales.
- Frente a `wait()/notify()`, reduce errores de señalización y encapsula la lógica de bloqueo/desbloqueo.
- Ofrece una semántica más clara que `ReentrantLock` para recursos contables, lo que hace el código más directo.

## 3. Ejecución
Compila y ejecuta la clase `PrincipalParking` desde tu IDE o con `javac`/`java`. Cada ejecución mostrará una secuencia distinta de entradas, esperas y salidas según los tiempos aleatorios de estacionamiento.

