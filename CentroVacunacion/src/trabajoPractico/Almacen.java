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
			int c = 0;
			if (stock.containsKey(nombre))
				c = stock.get(nombre);
			if (nombre.equals("Sputnik")) {
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new Vacuna3Grados(nombre,true,fechaIngreso));
				}
				if (stock.containsKey(nombre))
					stock.replace(nombre, Cantidad + c);
				else 
					stock.put(nombre, Cantidad);
			}
			if (nombre.equals("Sinopharm") || nombre.equals("AstraZeneca")) {
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new Vacuna3Grados(nombre,false,fechaIngreso));
				}
				if (stock.containsKey(nombre))
					stock.replace(nombre, Cantidad + c);
				else 
					stock.put(nombre, Cantidad);
			}
			if (nombre.equals("Moderna") || nombre.equals("Pfizer")) { 
				for(int i = 0; i<Cantidad; i++) {
					listaVacunas.add(new VacunaMenos18(nombre,false,fechaIngreso));
				}
				if (stock.containsKey(nombre))
					stock.replace(nombre, Cantidad + c);
				else 
					stock.put(nombre, Cantidad);
			}
	}
	
	public static void quitarVencidas() { 
		int auxVencidas = 0;
		int auxStock = 0;
		Iterator <Vacuna> it = listaVacunas.iterator();
		while (it.hasNext()) {
			Vacuna otra = it.next();
			if (otra.vencida()) {
				if (vencidas.containsKey(otra.getNombre())) {
					auxVencidas = vencidas.get(otra.getNombre());
					vencidas.replace(otra.getNombre(), auxVencidas + 1);
				} else {
					vencidas.put(otra.getNombre(), 1);
			} if (stock.get(otra.getNombre()) > 0) {
					auxStock = stock.get(otra.getNombre());
					stock.replace(otra.getNombre(), auxStock-1);
					it.remove();
			} else 
				it.remove();
				
			}
		}
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
		quitarVencidas();
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
		Iterator <Vacuna> it = listaVacunas.iterator();
		while (it.hasNext()) {
			Vacuna otra = it.next();
			if (CentroVacunacion.getCapacidad() == 1 && contador > 0 && otra.getPrioridadMayores()) {
					contador--;
					reservadas.add(otra);
					it.remove();
			} else if(contador > 0 && !otra.getPrioridadMayores()) {
				contador--;
				reservadas.add(otra);
				it.remove();
			}
			else 
				break;
		}
	}
		

//		public static void devolver(int prioridad) {
//			Iterator <Vacuna> it = reservadas.iterator();
//			while (it.hasNext()) {
//				Vacuna otra = it.next();
//				if (prioridad == 1 && otra.getPrioridadMayores()) {
//						listaVacunas.add(otra);
//						if (stock.containsKey(otra.getNombre())) {
//							int auxStock = stock.get(otra.getNombre());
//							stock.replace(otra.getNombre(), auxStock+1);
//						} else { 
//							stock.put(otra.getNombre(), 1);
//						}
//				} else if (!otra.getPrioridadMayores()) {
//						listaVacunas.add(otra);
//					if (stock.containsKey(otra.getNombre())) {
//						int auxStock = stock.get(otra.getNombre());
//						stock.replace(otra.getNombre(), auxStock+1);
//					} else { 
//						stock.put(otra.getNombre(), 1);
//					}
//				}
//			}
//		}
//			
//	}
//		
}


