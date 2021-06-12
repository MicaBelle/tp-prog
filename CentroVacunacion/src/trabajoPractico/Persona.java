package trabajoPractico;

public class Persona implements Comparable<Persona> {

	private int dni;
	private int edad;
	private int prioridad;
	private Fecha turno;
	private boolean vacunado;

	public Persona(int dni, Fecha nacimiento, int prioridad) {
		this.dni = dni;
		this.edad = nacimiento.diferenciaAnios(Fecha.hoy(), nacimiento);
		this.prioridad = prioridad;
		this.vacunado = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dni;
		result = prime * result + prioridad;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (dni != other.dni)
			return false;
		if (prioridad != other.prioridad)
			return false;
		return true;
	}

	public int getDni() {
		return dni;
	}

	public void setVacunado() {
		vacunado = true;
	}

	public Fecha getTurno() {
		return turno;
	}

	public void setTurno(Fecha fecha) {
		turno = fecha;
	}

	public int getPrioridad() {
		return this.prioridad;
	}

	public boolean getVacunado() {
		return this.vacunado;
	}

	@Override
	public int compareTo(Persona otra) {
		if (this.prioridad == otra.prioridad) {
			return 0;
		} else if (this.prioridad < otra.prioridad) {
			return -1;
		} else {
			return 1;
		}
	}

}
