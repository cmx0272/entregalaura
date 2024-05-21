package fp.banco;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cuenta {
    private String iban;
    private String dni;
    private LocalDate fechaCreacion;
    private Double saldo;

    private Cuenta(String iban, String dni, LocalDate fechaCreacion, Double saldo) {
        this.iban = iban;
        this.dni = dni;
        this.fechaCreacion = fechaCreacion;
        this.saldo = saldo;
    }

    public static Cuenta of(String iban, String dni, LocalDate fechaCreacion, Double saldo) {
        return new Cuenta(iban, dni, fechaCreacion, saldo);
    }

    public static Cuenta parse(String line) {
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String[] parts = line.split(",");
        String iban = parts[0];
        String dni = parts[1];
        LocalDate fechaCreacion = LocalDateTime.parse(parts[2], fm).toLocalDate();
        Double saldo = Double.parseDouble(parts[3]);
        return of(iban, dni, fechaCreacion, saldo);
    }

    public void ingresar(Double cantidad) {
        this.saldo += cantidad;
    }

    public void retirar(Double cantidad) {
        this.saldo -= cantidad;
    }

    public String getIban() {
        return iban;
    }

    public String getDni() {
        return dni;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Double getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return iban + "," + saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return iban.equals(cuenta.iban) && dni.equals(cuenta.dni) &&
                fechaCreacion.equals(cuenta.fechaCreacion) && saldo.equals(cuenta.saldo);
    }

    @Override
    public int hashCode() {
        int result = iban.hashCode();
        result = 31 * result + dni.hashCode();
        result = 31 * result + fechaCreacion.hashCode();
        result = 31 * result + saldo.hashCode();
        return result;
    }

    public static void main(String[] args) {
        Cuenta c = Cuenta.parse("ES5267093500351659831393,52184462S,2017-06-25 03:09:54,129522.44");
        System.out.println(c);
    }
}
