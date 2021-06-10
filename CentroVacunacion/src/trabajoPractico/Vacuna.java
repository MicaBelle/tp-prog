package trabajoPractico;

public abstract class Vacuna {
	protected String nombre;
	protected int temperatura;
	protected Fecha fechaIngreso;
	protected boolean prioridadMayores;
	
	public Vacuna (String nombre, int temperatura, Fecha fechaIngreso, boolean prioridadMayores) {
		this.nombre = nombre;
		this.temperatura = temperatura;
		this.fechaIngreso = fechaIngreso;
		this.prioridadMayores = prioridadMayores;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public Fecha getFechaIngreso() {
		return this.fechaIngreso;
	}
	
	public Integer getTemperatura() { 
		return this.temperatura;
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

