package EntregaExamen2;

import java.util.ArrayList;
import java.util.List;

public class TestEntregaExamen2 {
	
	



  
//EJERCICIO 1
    	    public static void main(String[] args) {
    	        // Crear una instancia de EntregaExamen2 y establecer los nombres de columna
    	        EntregaExamen2 df = new EntregaExamen2();
    	        df.setColumnNames(List.of("Id", "Nombre", "Apellidos", "Altura", "Fecha_Nacimiento"));

    	      
    	        
    	        // Crear un nuevo DataFrame vacío a partir del objeto EntregaExamen2
    	        EntregaExamen2 nuevoDataFrame = EntregaExamen2.emptyDataFrame(df);

    	        // Imprimir el nuevo DataFrame vacío en forma tabular
    	        System.out.println("Nuevo DataFrame vacío:");
    	        System.out.println(nuevoDataFrame);
    	        
    	   
    	    
    	    
    	    
    	    
    	    //EJERCICIO 2
    	        
    	        // Crear primer DataFrame
    	        EntregaExamen2 df1 = new EntregaExamen2();
    	        df1.setColumnNames(List.of("Id", "Nombre", "Apellidos"));
    	        List<List<String>> data1 = new ArrayList<>();
    	        data1.add(List.of("1", "Juan", "Pérez"));
    	        data1.add(List.of("2", "María", "López"));
    	        df1.setData(data1);

    	        // Crear segundo DataFrame
    	        EntregaExamen2 df2 = new EntregaExamen2();
    	        df2.setColumnNames(List.of("Altura", "Fecha_Nacimiento"));
    	        List<List<String>> data2 = new ArrayList<>();
    	        data2.add(List.of("170", "1990-05-15"));
    	        data2.add(List.of("165", "1985-10-20"));
    	        df2.setData(data2);

    	        // Agregar df2 al final de df1
    	        EntregaExamen2 result = EntregaExamen2.addDataFrame(df1, df2);

    	        // Imprimir el DataFrame resultante
    	        System.out.println("DataFrame resultante:");
    	        imprimirDataFrame(result);
    	    }

    	    // Método para imprimir un DataFrame
    	    public static void imprimirDataFrame(EntregaExamen2 df) {
    	        // Imprimir los nombres de las columnas
    	        List<String> columnNames = df.getColumnNames();
    	        System.out.println(String.join("\t", columnNames));

    	        // Imprimir los datos
    	        List<List<String>> data = df.getData();
    	        for (List<String> row : data) {
    	            System.out.println(String.join("\t", row));
    	        }
    	    }
//EJERCICIO 3
    	    {
            EntregaExamen2 df1 = EntregaExamen2.parse("recursos/personas.csv");
            EntregaExamen2 df2 = EntregaExamen2.parse("recursos/personas.csv");
            EntregaExamen2 result = EntregaExamen2.addDataFrame(df1, df2);
            System.out.println("DataFrame resultante:");
            imprimirDataFrame(result);
            System.out.println("DataFrame original:");
            imprimirDataFrame(df1);
            EntregaExamen2 result2 = EntregaExamen2.removeColumnIndex(df2, 1);
            System.out.println("\nDataFrame resultante:");
            imprimirDataFrame(result2);
        }

        public static void imprimirDataFrame1(EntregaExamen2 df) {
            // Imprimir los nombres de las columnas
            System.out.println(String.join("\t", df.getColumnNames()));

            // Imprimir los datos
            for (List<String> row : df.getData()) {
                System.out.println(String.join("\t", row));
            }
            
            
                 
        }
        
        
        
}
