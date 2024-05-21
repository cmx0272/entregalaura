package fp.banco;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import us.lsi.ejemplos_b1_tipos.Persona;
import us.lsi.tools.Preconditions;
import java.util.Optional;

public record Empleado(String dni, LocalDate fechaDeContrato, Double salarioMensual) {

    public static Empleado of(String dni, LocalDate fechaDeContrato, Double salarioMensual) {
        return new Empleado(dni, fechaDeContrato, salarioMensual);
    }

    public static Empleado parse(String text) {
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String[] partes = text.split(",");
        LocalDate fechaDeContrato = LocalDateTime.parse(partes[1], fm).toLocalDate();
        Double salarioMensual = Double.parseDouble(partes[2]);
        return Empleado.of(partes[0], fechaDeContrato, salarioMensual);
    }

    public Persona persona() {
        Optional<Persona> p = Personas.of().personaDni(this.dni());
        Preconditions.checkArgument(p.isPresent(),
                String.format("El empleado %s no está dado de alta como persona", this.dni()));
        return p.get();
    }

    public Integer mesesContratado() {
        LocalDate now = LocalDate.now();
        return now.until(this.fechaDeContrato()).getMonths();
    }

    @Override
    public String toString() {
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("%s,%s", this.dni(), this.fechaDeContrato.format(fm));
    }

    public static void main(String[] args) {
        // Add any testing code here if needed
    }
}
