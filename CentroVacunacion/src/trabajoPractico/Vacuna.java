package trabajoPractico;

public abstract class Vacuna {
	protected String nombre;
	protected int temperatura;
	protected Fecha fechaIngreso;
	protected boolean prioridadMayores;
	protected boolean asignadaEnEspera;
	protected boolean utilizada;

	public Vacuna (String nombre, int temperatura, Fecha fechaIngreso, boolean prioridadMayores) {
		this.nombre = nombre;
		this.temperatura = temperatura;
		this.fechaIngreso = fechaIngreso;
		this.prioridadMayores = prioridadMayores;
		this.asignadaEnEspera= false;
		this.utilizada = false;
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
	
	public void setAsignadaEnEspera() { 
		asignadaEnEspera = true;
	}
	
	public void setUtilizada() { 
			utilizada = true; 
	}

	protected boolean getPrioridadMayores() {
		return prioridadMayores; 
	}
	
	public String toString() { 
		return "Nombre: " + this.nombre;
	}

	public void reasignar() {
		asignadaEnEspera = false;
  
	}
}

