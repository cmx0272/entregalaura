package dataFrame;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.tools.Enumerate;
import us.lsi.tools.List2;
import us.lsi.tools.Stream2;
import us.lsi.tools.File2;


class DataFrameImpl implements DataFrame {

    // Atributos
    private List<String> columNames; // Nombres de las columnas
    private Map<String,Integer> columIndex; // Dado el nombre de una columna indica el índice
    private List<List<String>> rows; // Lista de las filas
    
    // Constructores
    private DataFrameImpl(List<String> columNames, Map<String, Integer> columIndex, List<List<String>> rows) {
        if (columNames == null || columIndex == null || rows == null) {
            throw new IllegalArgumentException("Los datos pasados al constructor DataFrameImpl no pueden ser nulos.");
        }
        this.columNames = new ArrayList<>(columNames);
        this.columIndex = new HashMap<>(columIndex);
        this.rows = new ArrayList<>(rows);
    }
   

    // Métodos de factoría
    private static DataFrameImpl of(List<String> columNames, Map<String, Integer> columIndex, List<List<String>> rows) {
        return new DataFrameImpl(columNames, columIndex, rows);
    }

    public static DataFrameImpl of(Map<String, List<String>> data) {
        List<String> columNames = new ArrayList<>(data.keySet());
        Map<String, Integer> columIndex = IntStream.range(0, columNames.size())
                .boxed()
                .collect(Collectors.toMap(columNames::get, Function.identity()));
        List<List<String>> rows = DataFrameImpl.transpose(new ArrayList<>(data.values()));
        return of(columNames, columIndex, rows);
    }

    public static DataFrameImpl of(Map<String, List<String>> data, List<String> columNames) {
    	Map<String,Integer> columIndex = new HashMap<>();
    	for(int i = 0; i < columNames.size(); i++) {
    		columIndex.put(columNames.get(i), i);
    	}
    	if(!data.keySet().equals(new HashSet<>(columNames))) {
    		throw new IllegalArgumentException("Las claves de data deben coinciir con columNames");
    	}
    	
    	List<List<String>> rows = DataFrameImpl.transpose(new ArrayList<>(data.values()));
    	return DataFrameImpl.of(columNames,columIndex,rows);
    	
//        Map<String, Integer> columIndex = IntStream.range(0, columNames.size())
//                .boxed()
//                .collect(Collectors.toMap(columNames::get, Function.identity()));
//        List<List<String>> rows = columNames.stream().map(data::get).collect(Collectors.toList());
//        return of(columNames, columIndex, rows);
    }


    public static DataFrameImpl parse(String file) {
    	
        Map<String, List<String>> data = File2.mapDeCsv(file);
        List<List<String>> rows = DataFrameImpl.transpose(new ArrayList<>(data.values()));
        List<String> columnNames = new ArrayList<>(data.keySet());
        return DataFrameImpl.of(columnNames,rows);
    }

    public static DataFrameImpl parse(String file, List<String> columNames) {
        Map<String, List<String>> data = File2.mapDeCsv(file);
        return of(data, columNames);
    }

    public static DataFrameImpl of(List<String> columNames, List<List<String>> rows) {
        Map<String,Integer> columIndex = new HashMap<>();
        IntStream.range(0,columNames.size()).forEach(i->columIndex.put(columNames.get(i),i));
        return DataFrameImpl.of(columNames,columIndex,rows);
    }
    
    // Métodos estáticos auxiliares
	@SuppressWarnings("unused")
	private static List<List<String>> transpose(List<List<String>> matrix) {
		/*
		 * Transpone una lista de lista de strings
		 */
        List<List<String>> transposedMatrix = new ArrayList<>();

        // Obtener el número de filas y columnas
        int numRows = matrix.size();
        int numCols = matrix.get(0).size();

        // Inicializar la matriz transpuesta
        for (int i = 0; i < numCols; i++) {
            transposedMatrix.add(new ArrayList<>());
        }

        // Iterar sobre cada fila de la matriz original
        for (int row = 0; row < numRows; row++) {
            // Iterar sobre cada elemento de la fila y agregarlo a la columna correspondiente
            for (int col = 0; col < numCols; col++) {
                transposedMatrix.get(col).add(matrix.get(row).get(col));
            }
        }

        return transposedMatrix;
	}

    public static Boolean allDifferent(List<String> values) {
        Integer n = values.size();
        Integer m = values.stream().collect(Collectors.toSet()).size();
        return n.equals(m);
    }

    public static String string(Object r) {
        String s = null;
        if(r instanceof LocalDate) {
            LocalDate r1 = (LocalDate) r;
            s = r1.format(DataFrame.dateFormat());
        } if(r instanceof LocalTime) {
            LocalTime r1 = (LocalTime) r;
            s = r1.format(DataFrame.timeFormat());
        } if(r instanceof LocalDateTime) {
            LocalDateTime r1 = (LocalDateTime) r;
            s = r1.format(DataFrame.dateTimeFormat());
        } else if(r instanceof Double) {
            s = String.format("%.2f",r);
        } else if(r instanceof Integer) {
            s = String.format("%d",r);
        } else {
            s = r.toString();
        }
        return s;
    }
   

    @SuppressWarnings("unchecked")
    public static <R> R parse(String text, Class<R> type) {
        Object r = null;
        if(type.equals(LocalDate.class)) {
            r = LocalDate.parse(text,DataFrame.dateFormat());
        } else if(type.equals(LocalTime.class)) {
            r = LocalTime.parse(text,DataFrame.timeFormat());
        } else if(type.equals(LocalDateTime.class)) {
            r = LocalDateTime.parse(text,DataFrame.dateTimeFormat());
        } else if(type.equals(Double.class)) {
            r = Double.parseDouble(text);
        } else if(type.equals(Integer.class)) {
            r = Integer.parseInt(text);
        } else {
            r = text;
        }
        return (R) r;
    }

    // Métodos de las propiedades
    @Override
    public List<String> columNames() {
        return new ArrayList<>(this.columNames);
    }

    @Override
    public Integer columNumber() {
        return this.columNames.size();
    }

    @Override
    public List<String> colum(String name) {
        return this.rows.stream().map(row -> row.get(this.columIndex.get(name))).toList();
    }

    @Override
    public List<String> colum(Integer index) {
        return this.rows.stream().map(row -> row.get(index)).toList();
    }

    @Override
    public <R> List<R> colum(String name, Class<R> type) {
        return this.colum(name).stream().map(s -> DataFrame.parse(s, type)).toList();
    }

    @Override
    public <R> List<R> colum(Integer index, Class<R> type) {
        return this.colum(index).stream().map(s -> DataFrame.parse(s, type)).toList();
    }

    @Override
    public Boolean columAllDifferent(String name) {
        return allDifferent(this.colum(name));
    }

    @Override
    public String propertie(List<String> row, String colum) {
        return row.get(this.columIndex.get(colum));
    }

    @Override
    public <R> R propertie(List<String> row, String colum, Class<R> type) {
        String text = propertie(row, colum);
        return DataFrame.parse(text, type);
    }

    @Override
    public String cell(Integer row, String column) {
        if (row < 0 || row >= rowNumber()) {
            throw new IndexOutOfBoundsException("Índice de fila fuera de rango");
        }
        Integer columnIndex = this.columIndex.get(column);
        if (columnIndex == null) {
            throw new IllegalArgumentException("La columna especificada no existe en el DataFrame.");
        }
        List<String> rowData = this.rows.get(row);
        return rowData.get(columnIndex);
    }

    @Override
    public String cell(Integer row, Integer column) {
        if (row < 0 || row >= rowNumber()) {
            throw new IndexOutOfBoundsException("Índice de fila fuera de rango");
        }
        if (column < 0 || column >= columNumber()) {
            throw new IndexOutOfBoundsException("Índice de columna fuera de rango");
        }
        List<String> rowData = this.rows.get(row);
        return rowData.get(column);
    }

    @Override
    public String cell(String row, String column, String propertie) {
        List<String> columnValues = colum(column);
        if (!columnValues.contains(row)) {
            throw new IllegalArgumentException("La fila con el valor dado no se encuentra en la columna especificada.");
        }
        int rowIndex = columnValues.indexOf(row);
        return cell(rowIndex, column);
    }


    @Override
    public Integer rowNumber() {
        return this.rows.size();
    }

    @Override
    public List<String> row(Integer i) {
        if (i < 0 || i >= rowNumber()) {
            throw new IndexOutOfBoundsException("Índice de fila fuera de rango");
        }
        return this.rows.get(i);
    }

    @Override
    public List<String> row(String row, String column) {
        if (!columAllDifferent(column)) {
            throw new IllegalArgumentException("La columna debe tener valores todos diferentes para seleccionar por número de fila.");
        }
        List<String> columnValues = colum(column);
        if (!columnValues.contains(row)) {
            throw new IllegalArgumentException("La fila con el valor dado no se encuentra en la columna especificada.");
        }
        int rowIndex = columnValues.indexOf(row);
        return row(rowIndex);
    }

    @Override
    public List<List<String>> rows() {
        return this.rows;
    }

    @Override
    public DataFrame head() {
        return head(5);
    }

    @Override
    public DataFrame head(Integer n) {
    	List<String> columNames = new ArrayList<>(this.columNames);
		Map<String,Integer> columIndex = new HashMap<>(this.columIndex);
		List<List<String>> rows = new ArrayList<>(this.rows);
		return DataFrameImpl.of(columNames,columIndex,rows.subList(0, n));
	}
    

    @Override
    public DataFrame tail(Integer n) {
        int totalRows = rowNumber();
        return slice(totalRows - n, totalRows);
    }

    @Override
    public DataFrame tail() {
        return tail(5);
    }

    @Override
    public DataFrame slice(Integer n, Integer m) {
        List<List<String>> slicedRows = this.rows.subList(n, Math.min(m, rowNumber()));
        return of(this.columNames, slicedRows);
    }

    @Override
    public DataFrame filter(Predicate<List<String>> p) {
        List<List<String>> filteredRows = this.rows.stream().filter(p).toList();
        return of(this.columNames, filteredRows);
    }

	@Override
	public <E extends Comparable<? super E>> DataFrame sortBy(Function<List<String>, E> f, Boolean reverse) {
		//
		List<String> columNames = new ArrayList<>(this.columNames);
		Map<String,Integer> columIndex = new HashMap<>(this.columIndex);
		List<List<String>> rows = new ArrayList<>(this.rows);
		Comparator<List<String>> cmp = reverse?Comparator.comparing(f).reversed():Comparator.comparing(f);
		Collections.sort(rows,cmp);
		return DataFrameImpl.of(columNames,columIndex,rows);
	}
	private Set<Integer> indexes(List<String> columNames){
		//
		return columNames.stream().map(cn->this.columIndex.get(cn)).collect(Collectors.toSet());
	}
	private List<String> select(List<String> ls, Set<Integer> sl){
		//
		return IntStream.range(0, ls.size()).boxed()
				.filter(i->sl.contains(i))
				.map(i->ls.get(i))
				.toList();
	}
	@Override
	public <R> DataFrame groupBy(List<String> columNames, String newColumn, BinaryOperator<R> op,
			Function<List<String>, R> value) {
		//
		Function<List<String>,List<String>> key = ls->this.select(ls,this.indexes(columNames));
		Map<List<String>,R> g = Stream2.groupingReduce(this.rows.stream(),key,op,value);
		DataFrame r = DataFrame.of(columNames,g.keySet().stream().toList());		
		r = r.addColum(newColumn,g.values().stream().map(x->DataFrameImpl.string(x)).toList());
		return r;
	}
	@Override
	public DataFrame addColum(String newColum, List<String> datos) {
		//
		List<String> columNames = new ArrayList<>(this.columNames);
		columNames.add(newColum);
		Map<String,Integer> columIndex = new HashMap<>(this.columIndex);
		columIndex.put(newColum,this.columNumber()+1);
		List<List<String>> rows = new ArrayList<>(this.rows);
		Integer nr = this.rowNumber();
		List<List<String>> rn = IntStream.range(0, nr).boxed()
				.map(r->List2.concat(rows.get(r),List.of(datos.get(r))))
				.toList();
		return DataFrameImpl.of(columNames,columIndex,rn);
	}
    @Override
    public DataFrame addCalculatedColum(String newColum, Function<List<String>, String> f) {
        List<String> newColumnValues = this.rows.stream().map(f).toList();
        return addColum(newColum, newColumnValues);
    }

    @Override
    public DataFrame removeColum(String colum) {
        Integer c = this.columIndex.get(colum);
        List<String> columNames = new ArrayList<>(this.columNames);
        columNames.remove(colum);
        Map<String,Integer> columIndex = new HashMap<>();
        IntStream.range(0,columNames.size()).forEach(i->columIndex.put(columNames.get(i),i));       
        List<List<String>> rows = new ArrayList<>(this.rows);
        List<List<String>> rn = rows.stream()
                .map(r->IntStream.range(0, this.columNumber()).boxed().filter(i->i != c).map(i->r.get(i)).toList())
                .toList();
        return DataFrameImpl.of(columNames,columIndex,rn);
    }

 // Métodos adicionales: redefinidos de Object
 	@Override
 	public String toString() {
 		//
 		Integer t = 16;
 		String r = this.format(" ",this.columNames(),t);
 		String line = this.line(this.columNames().size()+1, t);
 		String r3 = Stream2.enumerate(this.rows.stream()).map(x->this.format(x,t))
 				.collect(Collectors.joining("\n", r+"\n"+line+"\n", "\n"));
 		return r3;
 	}
 	private String format(String propertie, List<String> ls, Integer n) {
 		//
 		List<String> nls = new ArrayList<String>();
 		nls.add(propertie);
 		nls.addAll(ls);
 		String fmt = "%"+n+"s";
 		return nls.stream().map(c->String.format(fmt,c)).collect(Collectors.joining("|","|","|"));
 	}
 	private String format(Enumerate<List<String>> e, Integer n) {
 		//
 		return this.format(e.counter().toString(),e.value(),n);
 	}
 	private String line(Integer m, Integer n) {
 		//
 		String s = IntStream.range(0, n).boxed().map(i->"_").collect(Collectors.joining(""));
 		return IntStream.range(0, m).boxed().map(i->s).collect(Collectors.joining("|","|","|"));
 	
 	}
 }
