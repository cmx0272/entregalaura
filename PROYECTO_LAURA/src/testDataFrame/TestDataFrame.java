package testDataFrame;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import dataFrame.DataFrame;

public class TestDataFrame {

    public static void main(String[] args) {
        // Leer el DataFrame desde el archivo CSV
        DataFrame d = DataFrame.parse("recursos/personas.csv",
                List.of("Id", "Nombre", "Apellidos", "Altura", "Fecha_Nacimiento"));

        // Mostrar el DataFrame
        System.out.println("DataFrame de personas:");
        System.out.println(d);
    
        // Mostrar las diez primeras filas
        System.out.println("Diez primeras filas:");
        System.out.println(d.head(10));
    
    // Mostrar las diez últimas filas
        System.out.println("Diez ultimas filas:");
        System.out.println(d.tail(10));
    
    // Mostrar las cinco primeras filas
        System.out.println("Cinco primeras filas:");
        System.out.println(d.head(5));
    
    // Mostrar las cinco últimas filas
        System.out.println("Cinco ultimas filas:");
        System.out.println(d.tail(5));
    
    // Mostrar una porción entre dos valores enteros que indican las filas (por ejemplo, entre la fila 5 y la fila 15)
        System.out.println("Porcion entre dos valores enteros que indican las filas:");
        System.out.println(d.slice(5, 15));
    
    // Eliminar una columna (por ejemplo, la columna “Nombre”)
        System.out.println("DataFrame sin la columna 'Nombre':");
        System.out.println(d.removeColum("Nombre"));
    
     // Devolver solo aquellas filas que verifiquen que la edad de la persona es mayor estricto que sesenta años
        System.out.println("Personas mayores de sesenta años:");
        DataFrame personasMayores = d.filter(row -> {
            String fechaNacimiento = row.get(4); // Obtener la fecha de nacimiento
            int yearNacimiento = Integer.parseInt(fechaNacimiento.substring(6)); // Obtener el año de nacimiento
            int edad = LocalDate.now().getYear() - yearNacimiento; // Calcular la edad
            return edad > 60; // Verificar si la edad es mayor que sesenta años
        });

        if (personasMayores.rowNumber() == 0) {
            System.out.println("No hay personas mayores de 60 años.");
        } else {
            System.out.println(personasMayores);
        }
        
        // Devolver solo aquellas filas que verifiquen que la edad de la persona es mayor estricto que treinta años
        System.out.println("Personas mayores de treinta años:");
        DataFrame personasAdultas = d.filter(row -> {
            String fechaNacimiento = row.get(4); // Obtener la fecha de nacimiento
            int yearNacimiento = Integer.parseInt(fechaNacimiento.substring(6)); // Obtener el año de nacimiento
            int edad = LocalDate.now().getYear() - yearNacimiento; // Calcular la edad
            return edad > 30; // Verificar si la edad es mayor que treita años
        });

        if (personasAdultas.rowNumber() == 0) {
            System.out.println("No hay personas mayores de 30 años.");
        } else {
            System.out.println(personasAdultas);
        }
    }
    
}