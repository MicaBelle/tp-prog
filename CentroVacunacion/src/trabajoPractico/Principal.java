package trabajoPractico;

public class Principal {

	public static void main(String[] args) {
		
		Fecha fTurnos = new Fecha(15, 7, 2021);
		CentroVacunacion centro = new CentroVacunacion("UNGS", 5);
		
		Fecha.setFechaHoy();
		
		centro = new CentroVacunacion("UNGS", 5);
		centro.ingresarVacunas("Sputnik", 10,new Fecha(20,3,2021));
		centro.ingresarVacunas("AstraZeneca", 10,new Fecha(20,3,2021));

		centro.inscribirPersona(34701000, new Fecha(1, 5, 1989), false, false);  // 32 NS NP 4
		centro.inscribirPersona(29959000, new Fecha(20, 11, 1982), false, true); // 38 S  NP 1
		centro.inscribirPersona(24780201, new Fecha(1, 6, 1972), true, false);   // 49 NS P  3
		centro.inscribirPersona(29223000, new Fecha(2, 5, 1982), false, true);   // 39 S  NP 1
		centro.inscribirPersona(13000000, new Fecha(1, 5, 1958), true, false);   // 63 NS P  2
		centro.inscribirPersona(13000050, new Fecha(20, 6, 1958), false, true);  // 62 S  NP 1
		centro.inscribirPersona(14000000, new Fecha(1, 1, 1961), false, false);  // 60 NS NP 2
		centro.inscribirPersona(14005000, new Fecha(20, 12, 1961), true, false); // 59 NS P  3	

		Fecha fechaInicial = new Fecha(2, 7, 2021);
		Fecha fechaSiguiente = new Fecha(3, 7, 2021);
		Fecha fechaAnteriorSinTurnos = new Fecha(1, 7, 2021);
		Fecha fechaPosteriorSinTurnos = new Fecha(4, 7, 2021);

		
		System.out.println("------------ Creacion -------------");
		System.out.println(centro);
		System.out.println("-----------------------------------");
		System.out.println();
		
//		centro.ingresarVacunas("Pfizer", 10, new Fecha(2,3,2021));
//		centro.ingresarVacunas("AstraZeneca", 2, new Fecha(20,6,2021));
//		centro.ingresarVacunas("Sputnik", 5, new Fecha(15,6,2021));
//		
	
		System.out.println("Vacunas: " + centro.vacunasDisponibles());
		System.out.println("Vacuna Pfizer: " + centro.vacunasDisponibles("Pfizer")); 
		System.out.println("Vacuna Sputnik: " + centro.vacunasDisponibles("Sputnik")); 
		System.out.println("Vacuna AstraZeneca: " + centro.vacunasDisponibles("AstraZeneca")); 
		System.out.println("Vencidas: " + centro.reporteVacunasVencidas());
		System.out.println("Vacunas: " + centro.vacunasDisponibles());
		
		centro.generarTurnos(fechaInicial);
		
//		centro.inscribirPersona(34701000, new Fecha(1, 5, 1989), false, false);
//		centro.inscribirPersona(29959000, new Fecha(20, 11, 1982), false, true);
//		centro.inscribirPersona(24780201, new Fecha(1, 6, 1972), true, false);
//		centro.inscribirPersona(29223000, new Fecha(2, 5, 1982), false, true);
//		centro.inscribirPersona(13000000, new Fecha(1, 5, 1958), true, false);
//		centro.inscribirPersona(13000050, new Fecha(20, 6, 1958), false, true);
//		
		
		
		
		System.out.println("-------------- Turnos -------------");
		System.out.println("Lista espera previa a generar: " + centro.listaDeEspera());
		centro.generarTurnos(fTurnos);
		System.out.println("Reservadas al generar el turno: " + centro.reservadas());
		System.out.println("Vencidas: " + centro.reporteVacunasVencidas());
		System.out.println("Lista espera al generar: " + centro.listaDeEspera());
		System.out.println("Turnos con " + fTurnos + ": " + centro.turnosConFecha(fTurnos));
		System.out.println("-----------------------------------");
		System.out.println();
		
		centro.vacunarInscripto(24780201, fTurnos);
		centro.vacunarInscripto(13000000, fTurnos);
	//	centro.vacunarInscripto(34701000, fTurnos);
		centro.vacunarInscripto(29959000, fTurnos);
		centro.vacunarInscripto(24780201, fTurnos);

		System.out.println("------------- Centro --------------");
		System.out.println(centro);
		System.out.println("Reservadas tras vacunar: " + centro.reservadas());
		System.out.println("Vacunas restantes: " + centro.vacunasDisponibles());
		System.out.println("Vacunados: " + centro.reporteVacunacion());
		System.out.println("-----------------------------------");


	}
}
