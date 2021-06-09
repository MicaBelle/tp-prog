
package trabajoPractico;

import java.util.ArrayList;
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
			throw new RuntimeException ("El nombre ingresado no es v�lido"); 
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
	
	/**
	* Primero se verifica si hay turnos vencidos. En caso de haber turnos
	* vencidos, la persona que no asistió al turno debe ser borrada del sistema
	* y la vacuna reservada debe volver a estar disponible.
	*
	* Segundo, se deben verificar si hay vacunas vencidas y quitarlas del sistema.
	*
	* Por último, se procede a asignar los turnos a partir de la fecha inicial
	* recibida según lo especificado en la 1ra parte.
	* Cada vez que se registra un nuevo turno, la vacuna destinada a esa persona
	* dejará de estar disponible. Dado que estará reservada para ser aplicada
	* el día del turno.
	*
	*
	*/
	public void generarTurnos(Fecha fechaInicial) { 
		if (fecha.hoy().posterior(fechaInicial))
			throw new RuntimeException ("No se pueden generar turnos para una fecha pasada");
		// el centro debe recibir la info de los seleccionados y las vacunas, luego se generan turnos y se asignan a las personas, recordar que se esta usando aliasing, tener cuidado
		// IMPORTANTE si no alcanzan las vacunas debe dejar de asiganrles, en caso de no haber suficientes personas no pasa nada ya que esta comtemplado en asignarPersonas
		ArrayList<Persona> seleccionados = administracion.asignarPersonas(this.capacidad);
		// una vez recibidas las personas se debe buscar las vacunas para los de prioridad 1, osea saber la cantidad de personas en esa prioridad y luego buscar esa cantidad de vacunas
		//luego de las vacunas de prioridad 1 hay que buscar las que quedan, es decir de la capacidad total restar las de prioridad 1
		// recordar que si las vacunas de prio 1 no alcanzan entonces llenar con otras vacunas
		HashSet<Vacuna> vacunasListas = almacen.asignarVacunasEspeciales(this.capacidad);
		
//		for(Persona p: seleccionados) {
//			if(p.getPrioridad() == 1) {
//				for(Vacuna v: vacunasListas)
//			}
//		}
	}
	
	/**
	* Devuelve una lista con los dni de las personas que tienen turno asignado
	* para la fecha pasada por parámetro.
	* Si no hay turnos asignados para ese día, se debe devolver una lista vacía.
	* La cantidad de turnos no puede exceder la capacidad por día de la ungs.
	*/
	public List<Integer> turnosConFecha(Fecha fecha){
		return administracion.turnosConFecha(fecha);
	}
	
	/**
	* Dado el DNI de la persona y la fecha de vacunación
	* se valida que esté inscripto y que tenga turno para ese dia.
	* - Si tiene turno y está inscripto se debe registrar la persona como
	* vacunada y la vacuna se quita del depósito.
	* - Si no está inscripto o no tiene turno ese día, se genera una Excepcion.
	*/
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
