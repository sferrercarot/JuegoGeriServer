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

        System.out.print(jugador1 + ", introduce un número: ");
        int num1 = scanner.nextInt();

        System.out.print(jugador2 + ", introduce un número: ");
        int num2 = scanner.nextInt();

        // Determinar el rango
        int min = Math.min(num1, num2);
        int max = Math.max(num1, num2);

        // Generar número aleatorio dentro del rango
        int numeroSecreto = random.nextInt(max - min + 1) + min;

        System.out.println("Se ha generado un número secreto entre " + min + " y " + max);

        boolean adivinado = false;
        int turno = 1;

        while (!adivinado) {
            String jugadorActual = (turno == 1) ? jugador1 : jugador2;
            System.out.print(jugadorActual + ", intenta adivinar el número: ");
            int intento = scanner.nextInt();

            if (intento == numeroSecreto) {
                System.out.println("¡Felicidades, " + jugadorActual + "! Has adivinado el número.");
                adivinado = true;
            } else if (intento < numeroSecreto) {
                System.out.println("El número es mayor.");
            } else {
                System.out.println("El número es menor.");
            }

            // Cambiar turno
            turno = (turno == 1) ? 2 : 1;
        }

        scanner.close();
    }
}
