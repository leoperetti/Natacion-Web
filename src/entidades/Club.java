package entidades;

public class Club {
	
	private int nroClub;
	public int getNroClub() {
		return nroClub;
	}


	public void setNroClub(int nroClub) {
		this.nroClub = nroClub;
	}
	private String abreviacion, nombre, localidad;
	
	@Override
	public String toString()
	{
		return "("+ abreviacion +") "+ nombre + " - " + localidad; 
	}
	

	public String getAbreviacion() {
		return abreviacion;
	}


	public void setAbreviacion(String abreviacion) {
		this.abreviacion = abreviacion;
	}


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

}
