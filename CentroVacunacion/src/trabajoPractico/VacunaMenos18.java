package trabajoPractico;

public class VacunaMenos18 extends Vacuna {

	public VacunaMenos18(String nombre, boolean prioridadMayores, Fecha fechaIngreso) {
		super(nombre, -18, fechaIngreso, prioridadMayores);
	}

	public boolean vencida() {
		Fecha ingreso = super.getFechaIngreso();
		Fecha vencimiento = new Fecha(ingreso.dia(), ingreso.mes(), ingreso.anio());
		if (this.getNombre().equals("Pfizer")) {
			for (int i = 0; i <= 30; i++) {
				vencimiento.avanzarUnDia();
			}
		}
		if (this.getNombre().equals("Moderna")) {
			for (int i = 0; i <= 60; i++) {
				vencimiento.avanzarUnDia();
			}
		}
		boolean vencida = vencimiento.anterior(Fecha.hoy());
		return vencida;
	}
}
