package trabajoPractico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class Almacen {
	private static HashMap<String, Integer> stock;
	private static HashMap<String, Integer> vencidas;
	private static HashSet<Vacuna> listaVacunas;
	
	public Almacen () { 
		this.stock = new HashMap <String,Integer>();
		this.vencidas = new HashMap <String,Integer>();
		this.listaVacunas = new HashSet <Vacuna>();
	}
	
	public static HashMap<String,Integer> reporteVacunasVencidas() {
		return vencidas;
	}
	
	public static boolean esValida(String nombreVacuna) {
		if (nombreVacuna.equals("Sputnik") || nombreVacuna.equals("Sinopharm") || nombreVacuna.equals("Pfizer") || nombreVacuna.equals("Moderna") || nombreVacuna.equals("AstraZeneca"))   {
			return true;
		}
		return false; 
	}
	
	public static void ingresarVacuna(String nombre, int Cantidad, Fecha fechaIngreso) {
		if(stock.containsKey(nombre) && stock.size() >0) {
			Integer canttidadActualizada = new Integer(stock.get(nombre)+(Integer)Cantidad);
			stock.put(nombre, canttidadActualizada);
			
			if (nombre.equals("Sputnik")) {
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new Vacuna3Grados(nombre,true,fechaIngreso));
				}
			}
			if (nombre.equals("Pfizer")) {
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new VacunaMenos18(nombre,true,fechaIngreso));
				}
			}
			if (nombre.equals("Sinopharm")) {
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new Vacuna3Grados(nombre,false,fechaIngreso));
				}
			}
			if (nombre.equals("Moderna")) { 
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new VacunaMenos18(nombre,false,fechaIngreso));
				}
			}
			if (nombre.equals("AstraZeneca")) {
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new Vacuna3Grados(nombre,false,fechaIngreso));
				}
			}
		}
		else {
			stock.put(nombre, Cantidad);
			
			if (nombre.equals("Sputnik")) {
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new Vacuna3Grados(nombre,true,fechaIngreso));
				}
			}
			if (nombre.equals("Pfizer")) {
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new VacunaMenos18(nombre,true,fechaIngreso));
				}
			}
			if (nombre.equals("Sinopharm")) {
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new Vacuna3Grados(nombre,false,fechaIngreso));
				}
			}
			if (nombre.equals("Moderna")) { 
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new VacunaMenos18(nombre,false,fechaIngreso));
				}
			}
			if (nombre.equals("AstraZeneca")) {
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new Vacuna3Grados(nombre,false,fechaIngreso));
				}
			}
		}
	}
	
	public static void quitarVencidas() { 
		for (Vacuna v : listaVacunas)
			if(v.vencida()) {
				stock.put(v.getNombre(), stock.get(v.getNombre())-1);
				vencidas.replace(v.getNombre(), vencidas.get(v.getNombre())+1);
			}
	}
	
	public static StringBuilder dameVencidas() { 
		StringBuilder s = new StringBuilder();
		for (Vacuna v : listaVacunas)
			if(v.vencida()) {
				s.append(v.getNombre());
			}
		return s; 
	}
	
	public HashSet<Vacuna> asignarVacunasEspeciales(int capacidad) {
		HashSet <Vacuna> vacunasListas = new HashSet <Vacuna>();
  
		Integer contador = capacidad;
		for (Vacuna v : listaVacunas) {
			if(contador > 0 && v.getPrioridadMayores()) {
				contador--;
				v.setAsignadaEnEspera();
				vacunasListas.add(v);
			}
			else { 
				break;
			}
		}
		return vacunasListas;
	}
	
	public HashSet<Vacuna> asignarVacunasGenerales(int capacidad) {
		HashSet <Vacuna> vacunasListas = new HashSet <Vacuna>();
		Integer contador = capacidad;
		for (Vacuna v : listaVacunas) {
			if(contador > 0 && !v.getPrioridadMayores()) {
				contador--;
				v.setAsignadaEnEspera();
				vacunasListas.add(v);
			}
			else { 
				break;
			}
		}
		return vacunasListas;
	}
		
	public static int vacunasDisponibles() { 
		
		int contador = 0;
		for (Vacuna v: listaVacunas) { 
			contador += 1;
		}
		return contador;
	}

	public static int vacunasDisponibles(String nombreVacuna) {
		if (stock.containsKey(nombreVacuna)) {
			return stock.get(nombreVacuna);
		}
		return 0;
	}

	public static String asignarVacuna(int prioridad) {
		if (prioridad == 1) {
			for (String v : stock.keySet()) {
				if (v == "Pfizer" && stock.get(v) > 0) {
					stock.replace("Pfizer", stock.get(v)-1);
					return "Pfizer";
				} else if (v == "Sputnik" && stock.get(v) > 0) {
					stock.replace("Sputnik", stock.get(v)-1);
					return "Sputnik";
				}
			}
		} else {
			for (String v : stock.keySet()) {
				if (v == "Moderna" && stock.get(v) > 0) {
					stock.replace("Moderna", stock.get(v)-1);
					return "Moderna";
				} else if (v == "Sinopharm" && stock.get(v) > 0) {
					stock.replace("Sinopharm", stock.get(v)-1);
					return "Sinopharm";
				} else if (v == "AstraZeneca" && stock.get(v) > 0) {
					stock.replace("AstraZeneca", stock.get(v)-1);
					return "AstraZeneca";
					} 
				}
			}
			throw new RuntimeException ("No hay vacunas disponibles");
		}

}


