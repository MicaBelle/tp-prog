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
	private static HashSet<Vacuna> reservadas;
	
	public Almacen () { 
		this.stock = new HashMap <String,Integer>();
		this.vencidas = new HashMap <String,Integer>();
		this.listaVacunas = new HashSet <Vacuna>();
		this.reservadas = new HashSet<Vacuna>();
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
		if(stock.containsKey(nombre)) {
			Integer cantidadActualizada = stock.get(nombre);
			stock.put(nombre, cantidadActualizada);
			
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
			Almacen.reasignarVacunas();
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
			Almacen.reasignarVacunas();
			}
			throw new RuntimeException ("No hay vacunas disponibles");
		}

	public static void reasignarVacunas() {
		int contador = CentroVacunacion.getCapacidad();
		for (Vacuna v : listaVacunas) {
			if (CentroVacunacion.getCapacidad() == 1 && contador > 0 && v.getPrioridadMayores()) {
					contador--;
					reservadas.add(v);
					listaVacunas.remove(v);
			} else if(contador > 0 && !v.getPrioridadMayores()) {
				contador--;
				reservadas.add(v);
				listaVacunas.remove(v);
			}
			else 
				break;
		}
	}
		
}


