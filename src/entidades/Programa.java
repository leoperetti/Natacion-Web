package entidades;

public class Programa {

	private int nroPrograma;
	private String descripcion;
	//private String estilo1, estilo2;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString()
	{
		return this.nroPrograma + " - " + this.descripcion;
	}
	
	public Programa(){
		
		
	}
	
	
	public int getNroPrograma() {
		return nroPrograma;
	}
	public void setNroPrograma(int nroPrograma) {
		this.nroPrograma = nroPrograma;
	}
	/*public String getEstilo1() {
		return estilo1;
	}
	public void setEstilo1(String estilo1) {
		this.estilo1 = estilo1;
	}
	public String getEstilo2() {
		return estilo2;
	}
	public void setEstilo2(String estilo2) {
		this.estilo2 = estilo2;
	}*/
	
	
	
}
