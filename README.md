# ThreadExample
Ejemplo del uso de Thread en Java

# Enunciado
El programa simula la actividad que realiza un grupo de jugadores de golf en las instalaciones del club. El clube tiene un nº máximo de palos y pelotas disponible para
todos los jugadores. 
El club en este caso tiene un total de 20 palos y 20 pelotas, el jugador que quiera utilizarla debe reservar un nº de cada una para poder jugar, los jugadores tenemos 
dos clases disponibles:
  - Novatos: Alquila un máximo de pelotas entre 2 y 5 y 2 palos.
  - Expertos: Alquila 1 pelota y entre 2 y 5 palos.
 
El procedimiento del jugador es el siguiente:
  1º Reserva recursos (si los hay disponibles)
  2º Juega (cierto tiempo aleatorio)
  3º Devuelve los recursos
  4º Descansa (cierto tiempo aleatorio)
  
Los jugadores tienen la siguiente salida en pantalla:
  2e[1,4] reserva -> Esto indica lo siguiente, nº jugador (2), experiencia (e -> experto, n -> novato) [nº de pelotas, nº de palos] acción que realiza.
  
  

# Clases
- Simulador
- Jugador
- Club

# Simulador.java
Esta clase contiene el método main, donde se crean todos los hilos de la clase Jugador y el recurso compartido que es un objeto de la clase Club, donde
configuramos todo el sistema, se especifica un máximo de 10 jugadores y los recursos del Club (20 y 20), contiene los métodos log, tiempoJuego y tiempoDescanso.
  - log(): En este método vamos a imprimir el estado de los recursos del club en el momento actual.
  - tiempoJuego(): Indicamos un tiempo aleatorio en milisegundos que es el tiempo que el hilo jugador estará jugando (sleep).
  - tiempoDescanso(): Indicamos un tiempo aleatorio en milisegundos que es el tiempo que el hilo descansa (sleep).
  
# Jugador.java
La clase jugador extiende de Thread, tiene varios atributos los cuales especifican el tipo de jugador que es, los recursos que quiere,las veces que hará el ciclo
de juego (reserva, juego, devuelvo, descanso).

Atributos:
  - numJugador: Nº de identificador del jugador.
  - experience: Indica si es un jugador novato (n) ó experimentado (e).
  - action: La acción por la que empieza (0).
  - numRepeticion: El nº de veces que hará el ciclo de juego.
  - nPalos: El nº de palos que reserva.
  - nPelotas: El nº de pelotas que reserva.
  - c: Referencia al objeto de la clase Club.
  
run(): Aquí es donde tendremos la ejecución de cada hilo y haciendo la secuencia que tenemos programada, mientras el numRepeticiones sea mayor a 0. Tenemos en un switch
en función del valor de la variable action, hará unas acciones u otras.

  - Case 1: Se hace la reserva pasando los recursos que queremos (Club), muestra el mensaje por pantalla con el formato del enunciado y cambiamos el action al siguiente (2).
  - Case 2: Mostramos el mensaje con el formato del enunciado y dormimos el hilo el tiempo indicado por el método Simulador.tiempoJuego(), cambiamos el action al siguiente (3).
  - Case 3: Se hace la devolución pasando los recursos que tenemos (Club), muestra el mensaje por pantalla con el formato del enunciado y cambiamos el action al siguiente(4).
  - Case 4: Mostramos el mensaje con el formato del enunciado y dormimos el hilo el tiempo indicado por el método Simulador.tiempoDescanso(), cambiamos el action a 1 y se
  minimiza el valor de numRepeticiones en 1.
  
Esto se va a repetir en bucle hasta que se cumpla el bucle.

# Club.java
Esta clase es la que contiene los recursos compartidos para todos los hilos, por lo que las variables que serán modificadas por estos, debemos marcarlas como volatile para
asegurarnos de que solamente un hilo está modificando dichos valores, de lo contrario, podría modificar el valor 2 hilos a la vez y tener malos valores.
Los métodos que queremos que solamente pueda acceder un hilo, se marcan con synchronized.

synchronized reserva(): Recibe por parámetro el nº de palos y pelotas que se quiere reservar, el flujo de este método es el siguiente:
  1º Se comprueba que tenemos los recursos que se nos piden, en caso contrario paramos el hilo wait()
  2º En caso de que se tengan los recursos, restamos el nº de palos y pelotas disponibles y llamamos al método Simulador.log() para mostrar el mensaje por pantalla.
  Luego el hilo de la clase Jugador continúa al siguiente case.
  
synchronized devolucion(): Recibe por parámetro el nº de palos y pelotas que se quieren devolver, esto hace que se sumen a los recursos actuales, se llama el método
Simulador.log() para mostrar por pantalla el mensaje y se hace un notifyAll() para despertar a todos los hilos que estaban a la espera de poder reservar los recursos.

# Ejemplo de Hilos:

![image](https://user-images.githubusercontent.com/17860464/150222811-2cd6c1a1-883e-40f1-8aca-7a43cfb593ce.png)

Como podemos ver, cuando iniciamos el start() desde nuestro hilo, entra en el bucle de ejcución donde pueden pasar varias cosas, mientras se tengan recursos, el planificador
va a estar pasando al procesador por un pequeño tiempo el hilo que le toca y ouede acceder a los recursos teniendo el cerrojo de este para que solamente pueda acceder un único hilo a modificar los datos (lock) y haciendo las acciones programadas, hasta que el bucle termine y sea el fin del run() donde terminan los hilos. Pero puede pasar varias cosas.

Si se hace una llamada a wait() como tenemos en nuestro ejemplo, el hilo se mantiene a la espera de ser avisado, podemos imaginarlo como una lista de hilos, donde se van añadiendo y esperan a que sean notificados (notify ó notifyAll), cuando entran en este estado liberan el lock para que otro hilo lo pueda coger y acceder a los métodos synchronized.
Cuando otro hilo usa el notify ó notifiAll, desbloquea a un sólo hilo (notify) o a todos los hilos (notifyAll) que estaban a la espera, luego ellos compiten por obtener de nuevo el lock y poder acceder a los recursos. Cuando un hilo se libera (que estaba en wait()) y tiene el lock, continúa por donde se había quedado, por lo que continuaría después del wait() en nuestro ejemplo.
Si se llama a sleep(t) lo que haremos es dormir ese hilo el nº de tiempo en milisegundos, luego continúa con su secuencia de ejecución.
También puede quedarse bloqueado por la E/S, por ejemplo si utilizamos recursos del disco que es bastante lento en comparación con la velocidad que el procesador ejecuta los hilos.
