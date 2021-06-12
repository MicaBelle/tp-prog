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

	public Almacen() {
		this.stock = new HashMap<String, Integer>();
		this.vencidas = new HashMap<String, Integer>();
		this.listaVacunas = new HashSet<Vacuna>();
		this.reservadas = new HashSet<Vacuna>();
	}

	@Override
	public String toString() {
		return "Cantidad de vacunas disponibles: " + listaVacunas.size();
	}

	public static HashMap<String, Integer> reporteVacunasVencidas() {
		return vencidas;
	}

	public static boolean esValida(String nombreVacuna) {
		if (nombreVacuna.equals("Sputnik") || nombreVacuna.equals("Sinopharm") || nombreVacuna.equals("Pfizer")
				|| nombreVacuna.equals("Moderna") || nombreVacuna.equals("AstraZeneca")) {
			return true;
		}
		return false;
	}

	public static void ingresarVacuna(String nombre, int Cantidad, Fecha fechaIngreso) {
		int auxStock = 0;
		if (stock.containsKey(nombre))
			auxStock = stock.get(nombre);
		if (nombre.equals("Sputnik")) {
			for (int i = 0; i < Cantidad; i++) {
				listaVacunas.add(new Vacuna3Grados(nombre, true, fechaIngreso));
			}
			if (stock.containsKey(nombre))
				stock.replace(nombre, Cantidad + auxStock);
			else
				stock.put(nombre, Cantidad);
		}
		if (nombre.equals("Sinopharm") || nombre.equals("AstraZeneca")) {
			for (int i = 0; i < Cantidad; i++) {
				listaVacunas.add(new Vacuna3Grados(nombre, false, fechaIngreso));
			}
			if (stock.containsKey(nombre))
				stock.replace(nombre, Cantidad + auxStock);
			else
				stock.put(nombre, Cantidad);
		}
		if (nombre.equals("Moderna") || nombre.equals("Pfizer")) {
			for (int i = 0; i < Cantidad; i++) {
				listaVacunas.add(new VacunaMenos18(nombre, false, fechaIngreso));
			}
			if (stock.containsKey(nombre))
				stock.replace(nombre, Cantidad + auxStock);
			else
				stock.put(nombre, Cantidad);
		}
	}

	public static void quitarVencidas() {
		int auxVencidas = 0;
		int auxStock = 0;
		Iterator<Vacuna> it = listaVacunas.iterator();
		while (it.hasNext()) {
			Vacuna otra = it.next();
			if (otra.vencida()) {
				if (vencidas.containsKey(otra.getNombre())) {
					auxVencidas = vencidas.get(otra.getNombre());
					vencidas.replace(otra.getNombre(), auxVencidas + 1);
				} else {
					vencidas.put(otra.getNombre(), 1);
				}
				if (stock.get(otra.getNombre()) > 0) {
					auxStock = stock.get(otra.getNombre());
					stock.replace(otra.getNombre(), auxStock - 1);
					it.remove();
				} else
					it.remove();

			}
		}
	}

	public static int vacunasDisponibles() {
		return listaVacunas.size();
	}

	public static int vacunasDisponibles(String nombreVacuna) {
		if (stock.containsKey(nombreVacuna)) {
			return stock.get(nombreVacuna);
		}
		return 0;
	}

	public static void reservar(int prioridad) {
		if (listaVacunas.size() < 1)
			throw new RuntimeException("No hay vacunas disponibles para vacunar");
		Iterator<Vacuna> it = listaVacunas.iterator();
		while (it.hasNext()) {
			Vacuna otra = it.next();
			if (prioridad == 1 && otra.getNombre().equals("Sputnik")
					|| otra.getNombre().equals("Moderna") && stock.get(otra.getNombre()) > 0) {
				reservadas.add(otra);
				int auxStock = stock.get(otra.getNombre());
				stock.replace(otra.getNombre(), auxStock - 1);
				it.remove();
				return;
			} else if (stock.get(otra.getNombre()) > 0) {
				reservadas.add(otra);
				int auxStock = stock.get(otra.getNombre());
				stock.replace(otra.getNombre(), auxStock - 1);
				it.remove();
				return;
			}
		}
	}

	public static void vacunar(int prioridad) {
		String elegida = new String();
		Iterator<Vacuna> it = reservadas.iterator();
		while (it.hasNext()) {
			Vacuna otra = it.next();
			if (prioridad == 1 && otra.getNombre().equals("Pfizer") || otra.getNombre().equals("Moderna")) {
				elegida = otra.getNombre();
				it.remove();
				return;
			} else {
				elegida = otra.getNombre();
				it.remove();
				return;
			}
		}
	}

	public static void devolver(int prioridad) {
		Iterator<Vacuna> it = reservadas.iterator();
		while (it.hasNext()) {
			Vacuna otra = it.next();
			if (prioridad == 1 && otra.getPrioridadMayores()) {
				listaVacunas.add(otra);
				int auxStock = stock.get(otra.getNombre());
				stock.replace(otra.getNombre(), auxStock + 1);
				it.remove();
				return;
			} else {
				listaVacunas.add(otra);
				int auxStock = stock.get(otra.getNombre());
				stock.replace(otra.getNombre(), auxStock + 1);
				it.remove();
				return;
			}
		}
	}

	public static String dameVacuna(int prioridad) {
		String elegida = new String();
		for (Vacuna v : reservadas) {
			if (prioridad == 1 && v.getPrioridadMayores()) {
				elegida = v.getNombre();
				break;
			} else {
				elegida = v.getNombre();
				break;
			}
		}
		return elegida;
	}
}