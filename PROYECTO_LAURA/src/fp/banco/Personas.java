package fp.banco;


import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.ejemplos_b1_tipos.Persona;
import us.lsi.tools.File2;

public class Personas {

	private static Personas gestorDePersonas = null;
	private Set<Persona> personas;
	private Map<String, Persona> personaDni;
	private Set<String> dnis;

	private Personas(Set<Persona> personas) {
		this.personas = personas;
		this.personaDni = this.personas.stream().collect(Collectors.toMap(p -> p.dni(), p -> p));
		this.dnis = this.personaDni.keySet();
	}

	public static Personas of() {
		if (Personas.gestorDePersonas == null)
			Personas.gestorDePersonas = Personas.parse("bancos/personas.txt");
		return Personas.gestorDePersonas;
	}

	public static Personas parse(String fichero) {
		DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Set<Persona> personas = File2.streamDeFichero(fichero, "UTF-8").map(ln -> Persona.parse(ln, fm))
				.collect(Collectors.toSet());
		Personas.gestorDePersonas = new Personas(personas);
		return Personas.gestorDePersonas;
	}

	public Set<Persona> todos() {
		return this.personas;
	}

	public Set<String> dnis() {
		return this.dnis;
	}

	public Optional<Persona> personaDni(String dni) {
		return Optional.ofNullable(this.personaDni.getOrDefault(dni, null));
	}

	public Integer size() {
		return this.personas.size();
	}

	public Persona personaIndex(Integer index) {
		return this.personas.stream().toList().get(index);
	}

	public String toString() {
		return this.personas.stream()
				.map(p -> p.toString())
				.collect(Collectors.joining("\n\t", "Personas\n\t", ""));
	}
	
	public static void main(String[] args) {
		Personas p = Personas.of();
		System.out.println(p);
	}
}