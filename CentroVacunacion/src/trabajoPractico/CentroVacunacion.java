
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
	
	public void generarTurnos(Fecha fechaInicial) { 
		if (fecha.hoy().posterior(fechaInicial))
			throw new RuntimeException ("No se pueden generar turnos para una fecha pasada");
		Administracion.generarTurnos(fechaInicial);
		
	}

	public static HashSet<Vacuna> reservadas(){
		return Almacen.reservadas();
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
