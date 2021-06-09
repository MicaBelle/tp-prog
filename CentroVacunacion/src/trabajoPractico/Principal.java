package trabajoPractico;

public class Principal {

	public static void main(String[] args) {
		Fecha fTurnos = new Fecha(15, 7, 2021);
		CentroVacunacion centro = new CentroVacunacion("UNGS", 5);

		System.out.println("------------ Creacion -------------");
		System.out.println(centro);
		System.out.println("-----------------------------------");
		System.out.println();

		centro.ingresarVacunas("Moderna", 10, new Fecha(15,6,2021));
		centro.ingresarVacunas("Pfizer", 10, new Fecha(15,5,2021));
		
		//unico
		System.out.println("Vacunas: " + centro.vacunasDisponibles());
		System.out.println("Vacuna Pfizer: " + centro.vacunasDisponibles("Pfizer")); 
		System.out.println("Vacuna Moderna: " + centro.vacunasDisponibles("Moderna")); //tira error cuando cambio la cant ingresada
		System.out.println("Vencidas: " + centro.reporteVacunasVencidas());
		
		centro.inscribirPersona(34701000, new Fecha(1, 5, 1989), false, false);
		centro.inscribirPersona(29959000, new Fecha(20, 11, 1982), false, true);
		centro.inscribirPersona(24780201, new Fecha(1, 6, 1972), true, false);
		centro.inscribirPersona(29223000, new Fecha(2, 5, 1982), false, true);
		centro.inscribirPersona(13000000, new Fecha(1, 5, 1958), true, false);
		centro.inscribirPersona(13000050, new Fecha(20, 6, 1958), false, true);
		
		centro.generarTurnos(fTurnos);


		System.out.println("-------------- Turnos -------------");
		System.out.println("Lista espera: " + centro.listaDeEspera());
		System.out.println("Turnos con " + fTurnos + ": " + centro.turnosConFecha(fTurnos));
		System.out.println("-----------------------------------");
		System.out.println();
		
//		centro.vacunarInscripto(24780201, fTurnos);
//		centro.vacunarInscripto(13000000, fTurnos);

		System.out.println("------------- Centro --------------");
		System.out.println(centro);
		System.out.println("Vacunados: " + centro.reporteVacunacion());
		System.out.println("-----------------------------------");


	}
}
