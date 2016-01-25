package entidades;

public class Serie {
	
	private int nroSerie, nroCarrera, nroPrograma, nroTorneo;
	
	public String toString(){
		return "Número de Serie: "+ Integer.toString(nroSerie);
	}
	
	
	public int getNroTorneo() {
		return nroTorneo;
	}

	public void setNroTorneo(int nroTorneo) {
		this.nroTorneo = nroTorneo;
	}

	public int getNroPrograma() {
		return nroPrograma;
	}

	public void setNroPrograma(int nroPrograma) {
		this.nroPrograma = nroPrograma;
	}

	public int getNroCarrera() {
		return nroCarrera;
	}

	public void setNroCarrera(int nroCarrera) {
		this.nroCarrera = nroCarrera;
	}
	
	public int getNroSerie() {
		return nroSerie;
	}

	public void setNroSerie(int nroSerie) {
		this.nroSerie = nroSerie;
	}
	
	
}

