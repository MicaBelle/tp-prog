package trabajoPractico;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CentroVacunacion {
	private String nombre;
	private Integer capacidad;
	private Fecha fecha;
	private Administracion administracion;
	private Almacen almacen;
	
	
	/**
	* Constructor.
	* recibe el nombre del centro y la capacidad de vacunaci�n diaria.
	* Si la capacidad de vacunaci�n no es positiva se debe generar una excepci�n.
	* Si el nombre no est� definido, se debe generar una excepci�n.
	*/
	public CentroVacunacion (String nombreCentro, int capacidadVacunacionDiaria) {
		
		if (capacidadVacunacionDiaria <= 0) {
			throw new RuntimeException("La capacidad no puede ser negativa");
		}
		if (nombreCentro == "") {
			throw new RuntimeException("Se requiere un nombre");
		}
		
		this.nombre = nombreCentro;
		this.capacidad = capacidadVacunacionDiaria;
		this.fecha = Fecha.hoy();
		this.administracion = new Administracion();
		this.almacen = new Almacen();
		
	}	

	/**
	* Solo se pueden ingresar los tipos de vacunas planteados en la 1ra parte.
	* Si el nombre de la vacuna no coincidiera con los especificados se debe generar
	* una excepci�n.
	* Tambi�n se genera excepci�n si la cantidad es negativa.
	* La cantidad se debe
	* sumar al stock existente, tomando en cuenta las vacunas ya utilizadas.
	*/
	public void ingresarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso) {
		if (cantidad <= 0) {
			throw new RuntimeException ("La cantidad no puede ser negativa");
		}
		if (almacen.verificarVacuna(nombreVacuna)) {
			throw new RuntimeException("La vacuna ingresada no existe");
		}
		else {
			for(int i = 0; i<cantidad; i++) {
				almacen.ingresarVacuna(nombreVacuna, fechaIngreso);
			}
		}
	}
	
	/**
	* total de vacunas disponibles no vencidas sin distinci�n por tipo.
	*/
	public int vacunasDisponibles() {
		almacen.quitarVencidas();
		return almacen.vacunasDisponibles();
	}
	
	/**
	* total de vacunas disponibles no vencidas que coincida con el nombre de
	* vacuna especificado.
	*/
	public int vacunasDisponibles(String nombreVacuna) {
		if(almacen.verificarVacuna(nombreVacuna)) {
			throw new RuntimeException("La vacuna ingresada no existe");
		}
		return almacen.vacunasDisponibles(nombreVacuna);
	}
	
	/**
	* Se inscribe una persona en lista de espera.
	* Si la persona ya se encuentra inscripta o es menor de 18 a�os, se debe
	* generar una excepci�n.
	* Si la persona ya fue vacunada, tambi�n debe generar una excepci�n.
	*/
	public void inscribirPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		administracion.ingresarPersona(dni, nacimiento, tienePadecimientos, esEmpleadoSalud);
	}
	
	/**
	* Devuelve una lista con los DNI de todos los inscriptos que no se vacunaron
	* y que no tienen turno asignado.
	* Si no quedan inscriptos sin vacunas debe devolver una lista vac�a.
	*/
	public List<Integer> listaDeEspera() {
		return administracion.enEspera();
		}
	
	/**
	* Primero se verifica si hay turnos vencidos. En caso de haber turnos
	* vencidos, la persona que no asisti� al turno debe ser borrada del sistema
	* y la vacuna reservada debe volver a estar disponible.
	*
	* Segundo, se deben verificar si hay vacunas vencidas y quitarlas del sistema.
	*
	* Por �ltimo, se procede a asignar los turnos a partir de la fecha inicial
	* recibida seg�n lo especificado en la 1ra parte.
	* Cada vez que se registra un nuevo turno, la vacuna destinada a esa persona
	* dejar� de estar disponible. Dado que estar� reservada para ser aplicada
	* el d�a del turno.
	*
	*/
	public void generarTurnos(Fecha fechaInicial) { 
		actualizarStock();
		
		for(int i = 0; i<4; i++) {
			HashSet<Vacuna> vacunasListas = almacen.asignarVacunas(i+1, this.capacidad);
			administracion.generarTurnos(fechaInicial, this.capacidad);
		}
	}
	
	/**
	* Devuelve una lista con los dni de las personas que tienen turno asignado
	* para la fecha pasada por par�metro.
	* Si no hay turnos asignados para ese d�a, se debe devolver una lista vac�a.
	* La cantidad de turnos no puede exceder la capacidad por d�a de la ungs.
	*/
	public List<Integer> turnosConFecha(Fecha fecha){
		return administracion.turnosConFecha(fecha);
	}
	
	/**
	* Dado el DNI de la persona y la fecha de vacunaci�n
	* se valida que est� inscripto y que tenga turno para ese dia.
	* - Si tiene turno y est� inscripto se debe registrar la persona como
	* vacunada y la vacuna se quita del dep�sito.
	* - Si no est� inscripto o no tiene turno ese d�a, se genera una Excepcion.
	*/
	public void vacunarInscripto(int dni, Fecha fechaVacunacion) { 
		administracion.vacunarInscripto(dni, fechaVacunacion);
	}
	
	/**
	* Devuelve un Diccionario donde
	* - la clave es el dni de las personas vacunadas
	* - Y, el valor es el nombre de la vacuna aplicada.
	*/
	public Map<Integer, String> reporteVacunacion(){
		return administracion.reporteVacunacion();
	}
	
	/**
	* Devuelve en O(1) un Diccionario:
	* - clave: nombre de la vacuna
	* - valor: cantidad de vacunas vencidas conocidas hasta el momento.
	*/
	public Map<String, Integer> reporteVacunasVencidas(){
		return almacen.reporteVacunasVencidas();
	}
	
	/**
	 * revisa los turnos vencidos y reasigna las vacunas reservadas al stock.
	 */
	public void actualizarStock() {
		administracion.quitarTurnosVencidos();
		almacen.quitarVencidas();
		
	}
}
