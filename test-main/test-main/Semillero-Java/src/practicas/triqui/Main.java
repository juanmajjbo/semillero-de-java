package practicas.triqui;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

public class Main {

    static Function<Boolean, Boolean> turnoJugador = (actualTurno) -> !actualTurno;
    static Scanner sc = new Scanner(System.in);
    static boolean ganador = false;
    static char[][] tablero = new char[3][3];

    static void evaluarGanador() {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == tablero[i][1] && tablero[i][1] == tablero[i][2] && tablero[i][0] != '*' && tablero[i][1] != '*' && tablero[i][2] != '*') {
                ganador = true;
                return;
            }
            if (tablero[0][i] == tablero[1][i] && tablero[1][i] == tablero[2][i] && tablero[0][i] != '*' && tablero[1][i] != '*' && tablero[2][i] != '*') {
                ganador = true;
                return;
            }
            if (i == 0) {
                if (tablero[i][i] == tablero[i + 1][i + 1] && tablero[i + 1][i + 1] == tablero[i + 2][i + 2] && tablero[i][i] != '*' && tablero[i + 1][i + 1] != '*' && tablero[i + 2][i + 2] != '*') {
                    ganador = true;
                    return;
                }
            }
            if (i == 2) {
                if (tablero[i][i - 2] == tablero[i - 1][i - 1] && tablero[i - 1][i - 1] == tablero[i - 2][i] && tablero[i][i - 2] != '*' && tablero[i - 1][i - 1] != '*' && tablero[i - 2][i] != '*') {
                    ganador = true;
                    return;
                }
            }
        }
    }

    static boolean evaluarEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == '*') {
                    return false;
                }
            }
        }
        return true;
    }

    static void reiniciarJuego() {
        System.out.println("*************************************");
        System.out.println("¿Desean jugar de nuevo?\n1. Si\n2. No");
        try {
            int opcion = Integer.parseInt(sc.nextLine());
            switch (opcion) {
                case 1:
                    for (char[] vector : tablero) {
                        Arrays.fill(vector, '*');
                    }
                    ganador = false;
                    cambiarTurnos();
                    break;
                case 2:
                    System.out.println("Muchas gracias por jugar.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion no valida, ingrese 1 ó 2");
                    reiniciarJuego();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Opcion no valida, ingrese 1 ó 2");
            reiniciarJuego();
        }
    }

    static void seleccionarPosicion(boolean jugadorActual) {
        try {
            System.out.print("Fila: ");
            int fila = Integer.parseInt(sc.nextLine());
            System.out.print("Columna: ");
            int columna = Integer.parseInt(sc.nextLine());
            if (fila < 0 || fila > 2 || columna < 0 || columna > 2) {
                System.out.println("Los rangos son de [0,2]");
                seleccionarPosicion(jugadorActual);
            } else {
                if (tablero[fila][columna] == '*') {
                    tablero[fila][columna] = (jugadorActual) ? 'X' : 'O';
                    evaluarGanador();
                } else {
                    System.out.println("La posicion ya esta ocupada");
                    seleccionarPosicion(jugadorActual);
                }
            }
        } catch (Exception e) {
            System.out.println("Caracter no valido");
            seleccionarPosicion(jugadorActual);
        }
    }

    static void cambiarTurnos() {
        boolean jugadorActual = true;
        while (!ganador && !evaluarEmpate()) {
            if (jugadorActual) {
                System.out.println("*****************************");
                System.out.println("* Es el turno del jugador 1 *");
                System.out.println("*****************************");
                seleccionarPosicion(jugadorActual);
            } else {
                System.out.println("*****************************");
                System.out.println("* Es el turno del jugador 2 *");
                System.out.println("*****************************");
                seleccionarPosicion(jugadorActual);

            }
            for (char[] vector : tablero) {
                System.out.println(Arrays.toString(vector));
            }
            jugadorActual = turnoJugador.apply(jugadorActual);
        }
        System.out.println("*************************************");
        if (ganador) {
            String mensajeGanador = (jugadorActual) ? "El ganador es el jugador dos." : "El ganador es el jugador uno.";
            System.out.println(mensajeGanador);
        } else if (evaluarEmpate()) {
            System.out.println("Ha habido un empate.");
        }
        reiniciarJuego();
    }

    public static void main(String[] args) {
        for (char[] vector : tablero) {
            Arrays.fill(vector, '*');
        }
        cambiarTurnos();
    }
}
