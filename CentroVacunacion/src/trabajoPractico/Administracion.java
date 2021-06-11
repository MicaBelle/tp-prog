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
	private static HashMap<Integer, Persona> turnosGenerados;
	
	public Administracion() {
		this.personas = new HashSet<Persona>();
		this.colaPrioridad = new HashSet<Persona>();
		this.historialVacunados = new HashMap<Integer, String>();
		this.turnosGenerados = new HashMap <Integer, Persona>();
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
	
	
	public static void generarTurnos(Fecha fechaInicial) {	
		Iterator<Integer> itClaves = turnosGenerados.keySet().iterator();
		while (itClaves.hasNext()) {
			int dni = itClaves.next();
			 if (turnosGenerados.get(dni).getTurno().anterior(fechaInicial)) {
				 itClaves.remove();
				 Almacen.devolver(turnosGenerados.get(dni).getPrioridad());
			 }
		}
		
		int capacidad = CentroVacunacion.getCapacidad();
			Iterator<Persona> it = colaPrioridad.iterator();
			while (it.hasNext() && capacidad > 0) {
				Persona otra = it.next();
				otra.setTurno(fechaInicial);
				turnosGenerados.put(otra.getDni(), otra);
				Almacen.reservar(otra.getPrioridad());
				it.remove();
				capacidad--;
			}
		}
	
	
	public List<Integer> turnosConFecha(Fecha fecha){
		List <Integer> turnosConFecha = new LinkedList <Integer>();
		for (Integer clave: turnosGenerados.keySet()) { 
			if (turnosGenerados.get(clave).getTurno().equals(fecha)) {
				turnosConFecha.add(clave);
			}
		}
		return turnosConFecha;
	}
	
	public static void vacunarInscripto(int dni, Fecha fecha) {
		boolean encontrado = false; 
		Iterator<Integer> itClaves = turnosGenerados.keySet().iterator();
		while(itClaves.hasNext()) {
			int otroDNI = itClaves.next();
			 if (otroDNI == dni && turnosGenerados.get(otroDNI).getTurno().equals(fecha)) {
				encontrado = true; 
				if (encontrado) { 
						historialVacunados.put(otroDNI, Almacen.vacunar(turnosGenerados.get(otroDNI).getPrioridad()));
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
		LinkedList<Integer> lista = new LinkedList<Integer>();
		for (Persona p : colaPrioridad) {
			lista.add(p.getDni());
		}
		return lista;
	}
	
	public static ArrayList<Persona> asignarPersonas(int capacidad){
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



