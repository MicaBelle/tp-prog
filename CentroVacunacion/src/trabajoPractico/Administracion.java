package trabajoPractico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Administracion {

	private static HashSet<Persona> personas;
	private static HashSet<Persona> colaPrioridad;
	private static HashMap<Integer, String> historialVacunados;
	private static HashMap<Integer, Fecha> turnosGenerados;
	
	public Administracion() {
		this.personas = new HashSet<Persona>();
		this.colaPrioridad = new HashSet<Persona>();
		this.historialVacunados = new HashMap<Integer, String>();
		this.turnosGenerados = new HashMap <Integer, Fecha>();
	}
	
	public static void ingresarPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esTrabajadorSalud) {
		if (personas.contains(dni))
			throw new RuntimeException ("Esta persona ya está inscripta");
		if (nacimiento.diferenciaAnios(Fecha.hoy(), nacimiento) < 18) {
			throw new RuntimeException ("No se puede ingresar un menor de edad");
		} 
		if (historialVacunados.containsKey(dni)) {
			throw new RuntimeException("Esta persona ya se encuentra vacunada");
		}
		else if (nacimiento.diferenciaAnios(Fecha.hoy(), nacimiento) > 60) {
			personas.add(new Persona(dni, nacimiento, 1));
		} else if (esTrabajadorSalud) {
			personas.add(new Persona(dni, nacimiento, 2));
		} else if (tienePadecimientos) {
			personas.add(new Persona(dni, nacimiento, 3));
		} else { 
			personas.add(new Persona(dni, nacimiento, 4));
		}
		asignarColaPrioridad();
	}
	
	private static void asignarColaPrioridad() {
		for(int i = 0; i<4; i++) {
			for(Persona p : personas) {
				if(p.getPrioridad() == i+1) {
					colaPrioridad.add(p);
				}
			}
		}
		personas.clear();
	}
	
	public void generarTurnos(Fecha fechaInicial) {	
		for (Integer clave : turnosGenerados.keySet()){
			 if (turnosGenerados.get(clave).anterior(fechaInicial)) {
				 turnosGenerados.remove(clave, fechaInicial);
				 for (Persona p : colaPrioridad) { 	
					 p = null;
				 }
			 }
		}
		
		Almacen.quitarVencidas();
		
		for (int i= 0;  i < CentroVacunacion.getCapacidad(); i++) { 
			for (Persona p : colaPrioridad) { 
					turnosGenerados.put(p.getDni(), fechaInicial);
			}	
		}
	 }
	
	public List<Integer> turnosConFecha(Fecha fecha){
		List <Integer> turnosConFecha = new LinkedList <Integer>();
		for (Integer clave: turnosGenerados.keySet()) { 
			if (turnosGenerados.get(clave).equals(fecha)) {
				turnosConFecha.add(clave);
			}
		}
		return turnosConFecha;
	}
  
	public boolean verificaTurno(Persona persona) {	
		return turnosGenerados.containsKey(persona.getDni()) && persona.getTurno().equals(Fecha.hoy());
	}
	
	public static void vacunarInscripto(int dni, Fecha fecha) {
		boolean encontrado = false; 
		 for (Integer clave : turnosGenerados.keySet()){
			 if (clave == dni && turnosGenerados.get(clave).equals(fecha)) {
				encontrado = true; 
				for (Persona p : colaPrioridad) {
					if (p.getDni() == clave)
						p.setVacunado();
						Almacen.asignarVacuna(p.getPrioridad());
						historialVacunados.put(p.getDni(), Almacen.asignarVacuna(p.getPrioridad()));
				}
		 	}
		 }
		 if (!encontrado) 
			 throw new RuntimeException ("La persona no se encuentra registrada o no tiene fecha para ese día");
		}
	
	
	public HashMap <Integer, String> reporteVacunacion() {
		return historialVacunados;
	}

	public List<Integer> listaDeEspera() {
		List <Integer> lista = new LinkedList <Integer>();
		for (Persona p : colaPrioridad) {
			if(p.getTurno() == null && !p.getVacunado()) {
				Integer aux = new Integer(p.getDni());
				lista.add(aux);
			}
		}
		return lista;
	}
	
	/**
	 * aqui se selecciona a las personas que recibiran la vacuna,
	 * devolviendo una lista con los datos de las personas cuya cantidad 
	 * esta determinada por la capacidad de vacunacion
	 */
	public ArrayList<Persona> asignarPersonas(int capacidad){
		ArrayList<Persona> asignados = new ArrayList<Persona>();
		asignarColaPrioridad();
		int cont = capacidad;
				
		for(int i = 0; i < 4; i++) {
			for(Persona p: colaPrioridad) {
				if(cont >= 0) {
				asignados.add(p);
				}
				else {
					break;
				}
			}
					
		}
		return asignados;
	}
}



