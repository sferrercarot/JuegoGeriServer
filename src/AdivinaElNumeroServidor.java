import java.util.Random;
import java.util.Scanner;

public class AdivinaElNumeroServidor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Jugador 1, introduce tu nombre: ");
        String jugador1 = scanner.nextLine();

        System.out.print("Jugador 2, introduce tu nombre: ");
        String jugador2 = scanner.nextLine();

        System.out.print(jugador1 + ", elige el número de rondas (3, 5 o 10): ");
        int rondasJ1 = scanner.nextInt();

        System.out.print(jugador2 + ", elige el número de rondas (3, 5 o 10): ");
        int rondasJ2 = scanner.nextInt();

        int rondas = (rondasJ1 + rondasJ2) / 2;
        System.out.println("Se jugará al mejor de " + rondas + " rondas.");

        int puntajeJ1 = 0, puntajeJ2 = 0;
        int rondasNecesarias = (rondas / 2) + 1;
        boolean turnoJugador1 = true;
        boolean turnoAdivinarJugador1 = true;

        for (int i = 0; i < rondas; i++) {
            String jugadorQueElige = turnoJugador1 ? jugador1 : jugador2;
            System.out.print(jugadorQueElige + ", introduce un número: ");
            int num1 = scanner.nextInt();

            jugadorQueElige = turnoJugador1 ? jugador2 : jugador1;
            System.out.print(jugadorQueElige + ", introduce un número: ");
            int num2 = scanner.nextInt();

            int min = Math.min(num1, num2);
            int max = Math.max(num1, num2);
            int numeroSecreto = random.nextInt(max - min + 1) + min;

            System.out.println("Ronda " + (i + 1) + " - Se ha generado un número secreto entre " + min + " y " + max);

            boolean adivinado = false;
            int turno = turnoAdivinarJugador1 ? 1 : 2;

            while (!adivinado) {
                String jugadorActual = (turno == 1) ? jugador1 : jugador2;
                System.out.print(jugadorActual + ", intenta adivinar el número: ");
                int intento = scanner.nextInt();

                if (intento == numeroSecreto) {
                    System.out.println("¡Felicidades, " + jugadorActual + "! Has ganado esta ronda.");
                    if (turno == 1) {
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
                    System.out.println("El número es mayor.");
                } else {
                    System.out.println("El número es menor.");
                }

                turno = (turno == 1) ? 2 : 1;
            }

            if (puntajeJ1 >= rondasNecesarias) {
                System.out.println("¡" + jugador1 + " ha ganado la partida!");
                break;
            } else if (puntajeJ2 >= rondasNecesarias) {
                System.out.println("¡" + jugador2 + " ha ganado la partida!");
                break;
            }
        }

        System.out.println("Puntaje final: " + jugador1 + " - " + puntajeJ1 + " | " + jugador2 + " - " + puntajeJ2);
        scanner.close();
    }
}
