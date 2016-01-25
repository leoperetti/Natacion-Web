package entidades;

public class NadadorCarreraIndividual 
{
	private int nroCarrera, dniNadador, nroPrograma, nroTorneo;
	private String tiempoPreCompeticion;

	public String getTiempoPreCompeticion() {
		return tiempoPreCompeticion;
	}

	public void setTiempoPreCompeticion(String tiempoPreCompeticion) {
		this.tiempoPreCompeticion = tiempoPreCompeticion;
	}

	public int getNroPrograma() {
		return nroPrograma;
	}

	public void setNroPrograma(int nroPrograma) {
		this.nroPrograma = nroPrograma;
	}

	public int getNroTorneo() {
		return nroTorneo;
	}

	public void setNroTorneo(int nroTorneo) {
		this.nroTorneo = nroTorneo;
	}

	public int getNroCarrera() 
	{
		return nroCarrera;
	}

	public void setNroCarrera(int nroCarrera) 
	{
		this.nroCarrera = nroCarrera;
	}

	public int getDniNadador() 
	{
		return dniNadador;
	}

	public void setDniNadador(int dniNadador) 
	{
		this.dniNadador = dniNadador;
	}

}
