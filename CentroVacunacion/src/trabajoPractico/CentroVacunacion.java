
package trabajoPractico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CentroVacunacion {
	private static String nombre;
	private static int capacidad;
	private Fecha fecha;
	private Administracion administracion;
	private Almacen almacen;
	
	public CentroVacunacion (String nombreCentro, int capacidadVacunacionDiaria) {
		
		if (capacidadVacunacionDiaria <= 0) {
			throw new RuntimeException("La capacidad no puede ser negativa");
		}
		
		this.nombre = nombreCentro;
		this.capacidad = capacidadVacunacionDiaria;
		this.fecha = Fecha.hoy();
		this.administracion = new Administracion();
		this.almacen = new Almacen();
	}	
	
	public  String toString() {
		return "Nombre: " + nombre + " Capacidad:" + capacidad;  
	}
	
	public static int getCapacidad() { 
		return capacidad; 
	}
	
	public void ingresarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso) {
		if (cantidad <= 0) 
			throw new RuntimeException ("La cantidad no puede ser negativa");
		if (Almacen.esValida(nombreVacuna)) {
			Almacen.ingresarVacuna(nombreVacuna, cantidad,  fechaIngreso); 
		} else { 
			throw new RuntimeException ("El nombre ingresado no es válido"); 
		}
	}
	
	public int vacunasDisponibles() {
		Almacen.quitarVencidas();
		return Almacen.vacunasDisponibles();
	}
	
	public int vacunasDisponibles(String nombreVacuna) {

		if(!Almacen.esValida(nombreVacuna)) {
			throw new RuntimeException("La vacuna ingresada no existe");
		}
		Almacen.quitarVencidas();
		return Almacen.vacunasDisponibles(nombreVacuna);
	}
	
	
	public void inscribirPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		Administracion.ingresarPersona(dni, nacimiento, tienePadecimientos, esEmpleadoSalud);
	}
	
	public List<Integer> listaDeEspera() {
		return administracion.listaDeEspera();
		}

	// una vez recibidas las personas se debe buscar las vacunas para los de prioridad 1, osea saber la cantidad de personas en esa prioridad y luego buscar esa cantidad de vacunas		//luego de las vacunas de prioridad 1 hay que buscar las que quedan, es decir de la capacidad total restar las de prioridad 1
	// recordar que si las vacunas de prio 1 no alcanzan entonces llenar con otras vacunas
	// el centro debe recibir la info de los seleccionados y las vacunas, luego se generan turnos y se asignan a las personas, recordar que se esta usando aliasing, tener cuidado
	// IMPORTANTE si no alcanzan las vacunas debe dejar de asiganrles, en caso de no haber suficientes personas no pasa nada ya que esta comtemplado en asignarPersonas
	
	
	public void generarTurnos(Fecha fechaInicial) { 
		if (fecha.hoy().posterior(fechaInicial))
			throw new RuntimeException ("No se pueden generar turnos para una fecha pasada");
		Almacen.quitarVencidas();
	ArrayList<Persona> seleccionados = administracion.asignarPersonas(this.capacidad);
		
	}

	public List<Integer> turnosConFecha(Fecha fecha){
		return administracion.turnosConFecha(fecha);
	}
	

	public void vacunarInscripto(int dni, Fecha fechaVacunacion) { 
		Administracion.vacunarInscripto(dni, fechaVacunacion);
	}

	public Map<Integer, String> reporteVacunacion(){
		return administracion.reporteVacunacion();
	}
	

	public Map<String, Integer> reporteVacunasVencidas(){
		return Almacen.reporteVacunasVencidas();
	}

}
