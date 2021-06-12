package trabajoPractico;

abstract class Vacuna {
	private String nombre;
	private int temperatura;
	private Fecha fechaIngreso;
	private boolean prioridadMayores;

	public Vacuna(String nombre, int temperatura, Fecha fechaIngreso, boolean prioridadMayores) {
		this.nombre = nombre;
		this.temperatura = temperatura;
		this.fechaIngreso = fechaIngreso;
		this.prioridadMayores = prioridadMayores;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Fecha getFechaIngreso() {
		return fechaIngreso;
	}

	public boolean vencida() {
		return false;
	}

	protected boolean getPrioridadMayores() {
		return prioridadMayores;
	}

	public String toString() {
		return "Nombre: " + this.nombre;
	}
}