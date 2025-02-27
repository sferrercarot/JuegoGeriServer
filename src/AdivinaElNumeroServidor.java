import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class AdivinaElNumeroServidor {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor iniciado. Esperando jugadores...");

            Socket jugador1 = serverSocket.accept();
            System.out.println("Jugador 1 conectado.");

            Socket jugador2 = serverSocket.accept();
            System.out.println("Jugador 2 conectado.");

            BufferedReader entradaJ1 = new BufferedReader(new InputStreamReader(jugador1.getInputStream()));
            PrintWriter salidaJ1 = new PrintWriter(jugador1.getOutputStream(), true);

            BufferedReader entradaJ2 = new BufferedReader(new InputStreamReader(jugador2.getInputStream()));
            PrintWriter salidaJ2 = new PrintWriter(jugador2.getOutputStream(), true);

            // Pedir nombres de los jugadores
            salidaJ1.println("Introduce tu nombre:");
            String nombreJ1 = entradaJ1.readLine();
            System.out.println("Jugador 1: " + nombreJ1);

            salidaJ2.println("Introduce tu nombre:");
            String nombreJ2 = entradaJ2.readLine();
            System.out.println("Jugador 2: " + nombreJ2);

            // Pedir número de rondas a ambos jugadores
            salidaJ1.println("Elige el número de rondas (3, 5 o 10):");
            salidaJ2.println("Elige el número de rondas (3, 5 o 10):");

            int rondasJ1 = Integer.parseInt(entradaJ1.readLine());
            System.out.println(nombreJ1 + " eligió " + rondasJ1 + " rondas.");

            int rondasJ2 = Integer.parseInt(entradaJ2.readLine());
            System.out.println(nombreJ2 + " eligió " + rondasJ2 + " rondas.");

            // Calcular número de rondas final y avisar a los jugadores
            int rondas = (rondasJ1 + rondasJ2) / 2;

            salidaJ1.println("Se jugará al mejor de " + rondas + " rondas.");
            salidaJ2.println("Se jugará al mejor de " + rondas + " rondas.");

            System.out.println("El juego comenzará al mejor de " + rondas + " rondas.");

            Random random = new Random();
            int puntajeJ1 = 0, puntajeJ2 = 0;
            int rondasNecesarias = (rondas / 2) + 1;
            boolean turnoJugador1 = true;
            boolean turnoAdivinarJugador1 = true;

            for (int i = 0; i < rondas; i++) {
                System.out.println("Ronda " + (i + 1) + " comenzando...");
                PrintWriter salidaActual = turnoJugador1 ? salidaJ1 : salidaJ2;
                BufferedReader entradaActual = turnoJugador1 ? entradaJ1 : entradaJ2;
                salidaActual.println("Introduce un número:");
                int num1 = Integer.parseInt(entradaActual.readLine());
                System.out.println("Número elegido por " + (turnoJugador1 ? nombreJ1 : nombreJ2) + ": " + num1);

                salidaActual = turnoJugador1 ? salidaJ2 : salidaJ1;
                entradaActual = turnoJugador1 ? entradaJ2 : entradaJ1;
                salidaActual.println("Introduce un número:");
                int num2 = Integer.parseInt(entradaActual.readLine());
                System.out.println("Número elegido por " + (!turnoJugador1 ? nombreJ1 : nombreJ2) + ": " + num2);

                int min = Math.min(num1, num2);
                int max = Math.max(num1, num2);
                int numeroSecreto = random.nextInt(max - min + 1) + min;

                salidaJ1.println("Se ha generado un número secreto entre " + min + " y " + max);
                salidaJ2.println("Se ha generado un número secreto entre " + min + " y " + max);

                boolean adivinado = false;
                boolean turnoAdivinar = turnoAdivinarJugador1;

                while (!adivinado) {
                    PrintWriter salidaTurno = turnoAdivinar ? salidaJ1 : salidaJ2;
                    BufferedReader entradaTurno = turnoAdivinar ? entradaJ1 : entradaJ2;
                    salidaTurno.println("Intenta adivinar el número:");
                    int intento = Integer.parseInt(entradaTurno.readLine());

                    if (intento == numeroSecreto) {
                        salidaTurno.println("¡Felicidades, " + (turnoAdivinar ? nombreJ1 : nombreJ2) + "! Has ganado esta ronda.");

                        if (turnoAdivinar) {
                            puntajeJ1++;
                            turnoJugador1 = false;
                            turnoAdivinarJugador1 = false;
                        } else {
                            puntajeJ2++;
                            turnoJugador1 = true;
                            turnoAdivinarJugador1 = true;
                        }
                        adivinado = true;
                    } else if (intento < numeroSecreto) {
                        salidaTurno.println("El número es mayor.");
                    } else {
                        salidaTurno.println("El número es menor.");
                    }
                    turnoAdivinar = !turnoAdivinar;
                }

                if (puntajeJ1 >= rondasNecesarias) {
                    salidaJ1.println("¡Has ganado la partida, " + nombreJ1 + "! =)");
                    salidaJ2.println("¡" + nombreJ1 + " ha ganado la partida! =(");
                    break;
                } else if (puntajeJ2 >= rondasNecesarias) {
                    salidaJ2.println("¡Has ganado la partida, " + nombreJ2 + "! =)");
                    salidaJ1.println("¡" + nombreJ2 + " ha ganado la partida! =(");
                    break;
                }
            }

            salidaJ1.println("Puntaje final: " + nombreJ1 + " - " + puntajeJ1 + " | " + nombreJ2 + " - " + puntajeJ2);
            salidaJ2.println("Puntaje final: " + nombreJ1 + " - " + puntajeJ1 + " | " + nombreJ2 + " - " + puntajeJ2);

            jugador1.close();
            jugador2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}