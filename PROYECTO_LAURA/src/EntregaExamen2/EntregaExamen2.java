package EntregaExamen2;

import java.util.ArrayList;
import java.util.List;

import dataFrame.DataFrame;

public class EntregaExamen2 {
	
	 private List<String> columnNames;
	 private List<List<String>> data;
	 

//EJERCICIO 1
	    public List<String> getColumnNames() {
	        return columnNames;
	    }

	    public void setColumnNames(List<String> columnNames) {
	        this.columnNames = columnNames;
	    }

    // Otros métodos de la clase EntregaExamen2...

    public static EntregaExamen2 emptyDataFrame(EntregaExamen2 df) {
        // Creamos una nueva instancia de EntregaExamen2 vacía
        EntregaExamen2 newDataFrame = new EntregaExamen2();
        
        // Copiamos los nombres de columnas del DataFrame original
        newDataFrame.setColumnNames(df.getColumnNames());
        
        // Devolvemos el nuevo DataFrame vacío
        return newDataFrame;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        for (String columnName : columnNames) {
            sb.append(String.format(" %-15s |", columnName));
        }
        sb.append("\n");
        return sb.toString();
    }
    
    //EJERCIIO 2
    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
        
    

public static EntregaExamen2 addDataFrame(EntregaExamen2 df1, EntregaExamen2 df2) {
    EntregaExamen2 result = new EntregaExamen2();

    // Copiar los nombres de columnas de df1
    result.setColumnNames(new ArrayList<>(df1.getColumnNames()));

    // Copiar los datos de df1
    result.setData(new ArrayList<>(df1.getData()));

    // Añadir las columnas de df2 a df1
    for (int i = 0; i < df2.getColumnNames().size(); i++) {
        String columnName = df2.getColumnNames().get(i);
        result.getColumnNames().add(columnName);
        for (int j = 0; j < df2.getData().size(); j++) {
            if (i == 0) {
                // Añadir nueva fila para la primera columna de df2
                List<String> newRow = new ArrayList<>(df1.getData().get(j));
                newRow.add(df2.getData().get(j).get(i));
                result.getData().set(j, newRow);
            } else {
                // Añadir datos adicionales a las filas existentes
                result.getData().get(j).add(df2.getData().get(j).get(i));
            }
        }
    }

    return result;
    
}

//EJERCICIO 3
public static EntregaExamen2 removeColumnIndex(EntregaExamen2 df, int columnIndex) {
    EntregaExamen2 result = new EntregaExamen2();

    // Verificar si el índice es válido
    if (columnIndex < 0 || columnIndex >= df.getColumnNames().size()) {
        throw new IllegalArgumentException("El índice de columna proporcionado no es válido.");
    }

    // Copiar los nombres de columnas de df
    result.setColumnNames(new ArrayList<>(df.getColumnNames()));

    // Eliminar la columna correspondiente del resultado
    result.getColumnNames().remove(columnIndex);

    // Copiar los datos de df
    result.setData(new ArrayList<>());
    for (List<String> row : df.getData()) {
        List<String> newRow = new ArrayList<>(row);
        newRow.remove(columnIndex);
        result.getData().add(newRow);
    }

    return result;
}

public static EntregaExamen2 parse(String string) {
	// TODO Auto-generated method stub
	return null;
}


//EJERCICIO 4
 public static List

 <DataFrame> divideDataFrame(EntregaExamen2 df, int ci) {
	    List<DataFrame> dividedDataFrames = new ArrayList<>();

	    if (ci < 0 || ci >= df.getColumnNumber()) {
	        throw new IllegalArgumentException("El índice de la columna es inválido.");
	    }

	    List<String> firstColumnNames = df.getColumnNames().subList(0, ci + 1);
	    List<String> secondColumnNames = df.getColumnNames().subList(ci + 1, df.getColumnNumber());
	    
	    List<List<String>> firstData = new ArrayList<>();
	    List<List<String>> secondData = new ArrayList<>();

	    for (List<String> row : df.getData()) {
	        firstData.add(row.subList(0, ci + 1));
	        secondData.add(row.subList(ci + 1, df.getColumnNumber()));
	    }
	    dividedDataFrames.add(new DataFrame(firstColumnNames, df.subData(firstData)));
	    dividedDataFrames.add(new DataFrame(secondColumnNames, df.subData(secondData)));


	    return dividedDataFrames;
	}

private int getColumnNumber() {
	// TODO Auto-generated method stub
	return 0;
}
 
}
