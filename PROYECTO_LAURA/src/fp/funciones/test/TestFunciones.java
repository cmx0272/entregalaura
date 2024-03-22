package fp.funciones.test;

import fp.funciones.Funciones;
import java.util.List;
import java.util.ArrayList;

public class TestFunciones {

    public static void main(String[] args) {
        testEsPrimo();
        testNumeroCombinatorio();
        testCalculoNumeroS();
        testDiferencias();
        testCadenaMasLarga();
        testP2();
        testC2();
        testS2();
    }

    // TEST EJERCICIO 1
    public static void testEsPrimo() {
        int numero = 16; // NUMERO A PROBAR PRIMO
        boolean resultado = Funciones.esPrimo(numero);
        if (resultado) {
            System.out.println("El numero " + numero + " es primo");
        } else {
            System.out.println("El numero " + numero + " no es primo");
        }
    }

 // TEST EJERCICIO 2
    public static void testNumeroCombinatorio() {
        int n = 1; // VALOR N
        int k = 0; // VALOR K
        try {
            double resultado = Funciones.numeroCombinatorio(n, k);
            System.out.println("El numero combinatorio (" + n + ", " + k + ") es: " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // TEST EJERCICIO 3
    public static void testCalculoNumeroS() {
        int n = 10; // VALOR N
        int k = 7; // VALOR K
        try {
            double resultado = Funciones.calculoNumeroS(n, k);
            System.out.println("El resultado del calculo de S con n=" + n + " y k=" + k + " es: " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // TEST EJERCICIO 4
    public static void testDiferencias() {
        List<Integer> numeros = new ArrayList<>(); 
        numeros.add(5); // NUMERO AQUI
        numeros.add(11); // NUMERO AQUI
        numeros.add(17); // NUMERO AQUI
        List<Integer> diferencias = Funciones.diferencias(numeros);
        System.out.println("Diferencias entre numeros consecutivos: " + diferencias);
    }

    // TEST EJERCICIO 5
    public static void testCadenaMasLarga() {
        List<String> caracteres = new ArrayList<>(); 
        caracteres.add("toalla"); // CADENA AQUI
        caracteres.add("silla"); // CADENA AQUI
        caracteres.add("pelota de futbol"); // CADENA AQUI
        String masLarga = Funciones.cadenaMasLarga(caracteres);
        System.out.println("La cadena mas larga es: " + masLarga);
    }

 
//DEFENSA
 // TEST EJERCICIO A
    public static void testP2() {
        int n = 10;
        int k = 5;
        int i = 2;

        try {
            double resultado = Funciones.P2(n, k, i);
            System.out.println("El resultado de P2 con n=" + n + ", k=" + k + ", i=" + i + " es: " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error en testP2: " + e.getMessage());
        }
    }


    // TEST EJERCICIO B
    public static void testC2() {
        int n = 9;
        int k = 6;

        try {
            double resultado = Funciones.C2(n, k);
            System.out.println("El resultado de C2 con n=" + n + ", k=" + k + " es: " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error en testC2: " + e.getMessage());
        }
    } 


    // TEST EJERCICIO C
    public static void testS2() {
        int n = 5;
        int k = 2;

        try {
            double resultado = Funciones.S2(n, k);
            System.out.println("El resultado de S2 con n=" + n + ", k=" + k + " es: " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error en testS2: " + e.getMessage());
        }
    }

}
    
  

