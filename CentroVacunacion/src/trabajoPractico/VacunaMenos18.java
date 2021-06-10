package trabajoPractico;

public class VacunaMenos18 extends Vacuna{
	
	public VacunaMenos18 (String nombre, boolean prioridadMayores, Fecha fechaIngreso) {
		super (nombre, -18, fechaIngreso, prioridadMayores);
	}
	
	@Override
	public boolean vencida() {
		if (this.getNombre().equals("Pfizer")) {
			Fecha vencimientoPfizer = new Fecha(fechaIngreso);
			for (int i =0; i < 30; i++) {
				vencimientoPfizer.avanzarUnDia();
			}
			if (vencimientoPfizer.equals(Fecha.hoy()))
				return true; 
			else 
				return false;
		} else if (this.getNombre().equals("Moderna")) {
			Fecha vencimientoModerna = new Fecha(fechaIngreso);
			for (int i =0; i < 60; i++) {
				vencimientoModerna.avanzarUnDia();
			}
			if (vencimientoModerna.equals(Fecha.hoy()))
				return true; 
			else 
				return false;
		} else 
			return false;
	
	}
}




