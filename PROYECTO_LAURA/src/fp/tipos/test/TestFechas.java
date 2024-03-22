package fp.tipos.test;

import java.util.Arrays;

import fp.tipos.Fechas;

public class TestFechas {

    public static void main(String[] args) {
        // METODO OF(año, mes, dia)
        Fechas fecha1 = Fechas.of(2023, 10, 6);
        System.out.println("Fecha 1: " + fecha1);

        // METODO PARSE(cadenaFecha)
        Fechas fecha2 = Fechas.parse("2023-09-20");
        System.out.println("Fecha 2: " + fecha2);

        // NOMBRE MES
        String nombreMes = fecha1.nombreMes();
        System.out.println("Nombre del mes de la Fecha 1: " + nombreMes);

        // DIA SEMANA
        String diaSemana = fecha1.diaSemana();
        System.out.println("Dia de la semana de la Fecha 1: " + diaSemana);

        // SUMA DIAS
        Fechas fecha3 = fecha1.sumarDias(5);
        System.out.println("Fecha 3 (sumando 5 dias a la Fecha 1): " + fecha3);

        // RESTA DIAS
        Fechas fecha4 = fecha1.restarDias(3);
        System.out.println("Fecha 4 (restando 3 dias a la Fecha 1): " + fecha4);

        // DIFERENCIA DIAS
        int diferencia = fecha1.diferenciaEnDias(fecha2);
        System.out.println("Diferencia en dias entre Fecha 1 y Fecha 2: " + diferencia);

        // AÑO BISIESTO
        boolean esBisiesto = Fechas.esAñoBisiesto(2024);
        System.out.println("2024 es bisiesto: " + esBisiesto);
        
        //DEFENSA RESTAR DIAS FECHA DAD
        Fechas fecha = Fechas.of(2024, 3, 22);
        int numDias = 10;

        int[] resultado = Fechas.restarDiasFechaDada(fecha, numDias);
            System.out.println("Fecha resultante de restar " + numDias + " dias a " + fecha + " es: " + Arrays.toString(resultado));
        }

}
    

