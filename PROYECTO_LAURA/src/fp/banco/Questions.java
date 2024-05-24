package fp.banco;

import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.ejemplos_b1_tipos.Persona;

public class Questions {

	//	Vencimiento de los prestamos de un cliente
	public static Set<LocalDate> vencimientoDePrestamosDeCliente(Banco banco,String dni) {
		return banco.prestamosDeCliente(dni).stream()
				.map(Prestamo::fechaVencimiento)
				.collect(Collectors.toSet());
	}
	
	
	//	Persona con más prestamos
	public static Persona clienteConMasPrestamos(Banco banco) {
		return banco.personas().todos().stream()
				.filter(p -> banco.prestamosDeCliente(p.dni()).size() > 0)
				.max((p1,p2) -> Integer.compare(banco.prestamosDeCliente(p1.dni()).size(), banco.prestamosDeCliente(p2.dni()).size()))
				.orElse(null);
	}
	
	
	//	Cantidad total de los crétditos gestionados por un empleado
	public static Double cantidadPrestamosPmpledado(Banco banco,String dni) {
		return banco.prestamosDeEmpleado(dni).stream()
				.mapToDouble(Prestamo::cantidad)
				.sum();
	}
	
	
	//	Empleado más longevo
	public static Persona empleadoMasLongevo(Banco banco) {
		return banco.empleados().todos().stream().map(Empleado::persona)
				.min((p1,p2) -> p1.fechaDeNacimiento().compareTo(p2.fechaDeNacimiento()))
				.orElse(null);
	}
	
	
	//	Interés mínimo, máximo y medio de los préstamos
	public static record Info(Double min, Double max, Double mean) {
		public String toString() {
			return String.format("(%.2f,%.2f,%.2f)",this.min(),this.max(),this.mean());
		}
	}
	
	
	public static  Info rangoDeIntereseDePrestamos(Banco banco) {
		 DoubleSummaryStatistics stats = banco.prestamos().todos().stream()
	                .mapToDouble(Prestamo::interes)
	                .summaryStatistics();
	        return new Info(stats.getMin(), stats.getMax(), stats.getAverage());
	}

	//	Número de préstamos por mes y año
	public static record Info2(Integer mes, Integer año) {
		public String toString() {
			return String.format("(%d,%d)",this.mes(),this.año());
		}
	}
	public static Map<Info2,Integer> numPrestamosPorMesAño(Banco banco) {
		return banco.prestamos().todos().stream()
                .collect(Collectors.groupingBy(p -> new Info2(p.fechaComienzo().getMonthValue(), p.fechaComienzo().getYear()), Collectors.summingInt(p -> 1)));
	}
	
	public static Persona personaMasAntiguaEnEmpresa(Banco banco) {
	    return banco.empleados().todos().stream()
	            .map(Empleado::persona)
	            .min((p1, p2) -> {
	                LocalDate fechaContrato1 = banco.empleados().empleadoDni(p1.dni()).get().fechaDeContrato();
	                LocalDate fechaContrato2 = banco.empleados().empleadoDni(p2.dni()).get().fechaDeContrato();
	                return fechaContrato1.compareTo(fechaContrato2);
	            })
	            .orElse(null);
	}
	
	public static void main(String[] args) {
	    // Crear una instancia de Banco y agregar datos de prueba
	    Banco banco = Banco.of();
	    
	    // 1. 
	    String clienteDni = "64482505G"; 
	    Set<LocalDate> vencimientos = Questions.vencimientoDePrestamosDeCliente(banco, clienteDni);
	    System.out.println("Vencimiento de los préstamos del cliente " + clienteDni + ": " + vencimientos);

	    // 2.
	    Persona personaConMasPrestamos = Questions.clienteConMasPrestamos(banco);
	    System.out.println("Cliente con más préstamos: " + (personaConMasPrestamos != null ? personaConMasPrestamos : "No hay datos"));

	    // 3. 
	    String empleadoDni = "52184462S"; 
	    Double cantidadPrestamosEmpleado = Questions.cantidadPrestamosPmpledado(banco, empleadoDni);
	    System.out.println("Cantidad total de los préstamos gestionados por el empleado " + empleadoDni + ": " + cantidadPrestamosEmpleado);

	    // 4. 
	    Persona personaMasAntigua = Questions.personaMasAntiguaEnEmpresa(banco);
	    System.out.println("Persona más antigua en la empresa: " + (personaMasAntigua != null ? personaMasAntigua : "No hay datos"));

	    // 5.
	    Questions.Info interesInfo = Questions.rangoDeIntereseDePrestamos(banco);
	    System.out.println("Interés mínimo, máximo y medio de los préstamos: " + interesInfo);

	    // 6. 
	    Map<Questions.Info2, Integer> prestamosPorMesAño = Questions.numPrestamosPorMesAño(banco);
	    System.out.println("Número de préstamos por mes y año: " + prestamosPorMesAño);
	}

}