package fp.banco;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class ExamenEntrega3 {
	
	
//	A. NUEVOS METODOS

//ejercicio 1
    public static List<Empleado> empleadoEntreDiaMes(Banco banco, String ini, String fin) {
        if (ini == null || fin == null || ini.isEmpty() || fin.isEmpty()) {
            throw new IllegalArgumentException("Los parámetros ini y fin deben estar informados.");
        }

        LocalDate iniDate;
        LocalDate finDate;

        try {
            iniDate = LocalDate.parse("2020/" + ini, DateTimeFormatter.ofPattern("yyyy/dd/MM"));
            finDate = LocalDate.parse("2020/" + fin, DateTimeFormatter.ofPattern("yyyy/dd/MM"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Debe ser DD/MM.");
        }

        if (iniDate.isAfter(finDate)) {
            throw new IllegalArgumentException("El parámetro ini debe ser menor que fin.");
        }

        int iniDay = iniDate.getDayOfMonth();
        int iniMonth = iniDate.getMonthValue();
        int finDay = finDate.getDayOfMonth();
        int finMonth = finDate.getMonthValue();

        if (iniDay < 1 || iniDay > 31 || finDay < 1 || finDay > 31 || iniMonth < 1 || iniMonth > 12 || finMonth < 1 || finMonth > 12) {
            throw new IllegalArgumentException("El día debe estar entre 1 y 31 y el mes entre 1 y 12.");
        }

        return banco.empleados().todos().stream()
            .filter(e -> {
                LocalDateTime fechaNacimiento = banco.personas().personaDni(e.dni()).get().fechaDeNacimiento();
                int day = fechaNacimiento.getDayOfMonth();
                int month = fechaNacimiento.getMonthValue();

                LocalDate fechaNac = LocalDate.parse("2020/" + day + "/" + month, DateTimeFormatter.ofPattern("yyyy/d/M"));

                return !fechaNac.isBefore(iniDate) && !fechaNac.isAfter(finDate);
            })
            .collect(Collectors.toList());
    }
    
    
//ejercicio 2
    public static Map<Character, List<Empleado>> empleadosConLetraDNI(Banco banco, Set<Character> letras) {
        if (banco == null || letras == null) {
            throw new IllegalArgumentException("Los parámetros banco y letras deben estar informados.");
        }

        if (letras.isEmpty()) {
            throw new IllegalArgumentException("El conjunto letras no puede estar vacío.");
        }

        for (Character letra : letras) {
            if (!Character.isAlphabetic(letra)) {
                throw new IllegalArgumentException("El conjunto letras solo puede contener caracteres alfabéticos.");
            }
        }
        return banco.empleados().todos().stream()
                .filter(e -> letras.contains(e.dni().charAt(e.dni().length() - 1)))
                .collect(Collectors.groupingBy(e -> e.dni().charAt(e.dni().length() - 1)));
    }

    
        
//ejercicio 3
       
    public static double clientesEdadMedia(Banco banco, int m) {
        if (banco == null || m <= 0) {
            throw new IllegalArgumentException("Los parámetros banco y m deben estar informados, y m debe ser positivo.");
        }
        LocalDate fechaActual = LocalDate.now();
        List<Integer> edades = banco.personas().todos().stream()
                .filter(persona -> {
                    LocalDateTime fechaNacimiento = persona.fechaDeNacimiento();
                    int edad = fechaActual.getYear() - fechaNacimiento.getYear();
                    if (fechaNacimiento.getMonthValue() > fechaActual.getMonthValue()
                            || (fechaNacimiento.getMonthValue() == fechaActual.getMonthValue()
                            && fechaNacimiento.getDayOfMonth() > fechaActual.getDayOfMonth())) {
                        edad--;
                    }
                    return edad >= m;
                })
                .map(persona -> {
                    LocalDateTime fechaNacimiento = persona.fechaDeNacimiento();
                    int edad = fechaActual.getYear() - fechaNacimiento.getYear();
                    if (fechaNacimiento.getMonthValue() > fechaActual.getMonthValue()
                            || (fechaNacimiento.getMonthValue() == fechaActual.getMonthValue()
                            && fechaNacimiento.getDayOfMonth() > fechaActual.getDayOfMonth())) {
                        edad--;
                    }
                    return edad;
                })
                .collect(Collectors.toList());
        if (edades.isEmpty()) {
            return 0;
        } else {
            double sumaEdades = edades.stream().mapToInt(Integer::intValue).sum();
            return sumaEdades / edades.size();
        }
        
    }
        
        
//ejercicio 4
        
        public static Set<String> clientesConMenosPrestamos(Banco banco, int n) {
            if (banco == null) {
                throw new IllegalArgumentException("El banco no puede ser nulo.");
            }

            if (n <= 0) {
                throw new IllegalArgumentException("El número de clientes debe ser mayor que cero.");
            }

            Map<String, Integer> prestamosPorCliente = new HashMap<>();
            banco.prestamos().todos().forEach(prestamo -> {
                String dniCliente = prestamo.dniCliente();
                prestamosPorCliente.put(dniCliente, prestamosPorCliente.getOrDefault(dniCliente, 0) + 1);
            });

            Set<String> clientesConMenosPrestamos = new HashSet<>();
            prestamosPorCliente.entrySet().stream()
                    .sorted(Comparator.comparingInt(Map.Entry::getValue))
                    .limit(n)
                    .forEach(entry -> clientesConMenosPrestamos.add(entry.getKey()));

            return clientesConMenosPrestamos;
        }
    
    

    
    
        
    

       
        
        
        
//        B. MÉTODOS DE PRUEBA
        
    public static void main(String[] args) {
        Banco banco = Banco.of();
        
//test 1
        String ini = "23/08";
        String fin = "19/09";

        System.out.println("1: Empleados nacidos entre " + ini + " y " + fin);
        try {
            List<Empleado> empleados = empleadoEntreDiaMes(banco, ini, fin);
            empleados.forEach(e -> System.out.println(e.toString()));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

//test 2
        Set<Character> letras = new HashSet<>(Arrays.asList('L', 'B', 'G'));

        System.out.println("\n2: Empleados con letra DNI en " + letras);
        try {
            Map<Character, List<Empleado>> resultado = empleadosConLetraDNI(banco, letras);
            resultado.forEach((k, v) -> {
                System.out.println("Letra: " + k);
                v.forEach(e -> System.out.println(e));
            });
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        
//test 3
        
        int m = 18;

        System.out.println("\n3: Edad media de los clientes mayores o iguales a " + m + " años");
        try {
            double edadMedia = clientesEdadMedia(banco, m);
            System.out.println("Edad media: " + edadMedia);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    
    
    
//test 4

        int numClientes = 6;

        System.out.println("\n4: Conjunto de DNIs con los " + numClientes + " clientes con menos préstamos");
        try {
            Set<String> clientes = clientesConMenosPrestamos(banco, numClientes);
            clientes.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
          
    }
    
}
    


    
    
    
