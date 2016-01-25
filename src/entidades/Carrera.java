package entidades;

public class Carrera {

	private int nroCarrera, tipoCarrera, edad2, metros, nroPrograma;
	private String estilo, categoria;
	private char sexo;
	
	//Cuando traigo la carrera desde la db, pongo un atributo nro programa? o tengo q instanciar un programa?
	@Override
	public String toString(){
		if(categoria!=null)
		{
		return "Número: "+this.nroCarrera+"  Edad: ("+this.tipoCarrera+" y "+this.edad2+")" + " Sexo: "+this.sexo+"  -  Estilo:  "+this.estilo+"  -  Categoria:  "+this.categoria+"  -  Metros: "+this.metros+"m.";
		}
		else
		{	if(edad2==14){
				return "Número: "+this.nroCarrera+"  Edad: ("+this.tipoCarrera+" y "+this.edad2+")" + " Sexo: "+this.sexo+"  -  Estilo:  "+this.estilo+"  -  Metros: "+this.metros+"m.";
			}
			else
			{
				return "Número: "+this.nroCarrera+"  Edad: ("+this.tipoCarrera+")" + " Sexo: "+this.sexo+"  -  Estilo: "+this.estilo+"  -  Metros: "+this.metros+"m.";
			}
		}
	}
	
	public Carrera(){
		
		
	}
	
	
	public int getNroCarrera() {
		return nroCarrera;
	}

	public void setNroCarrera(int nroCarrera) {
		this.nroCarrera = nroCarrera;
	}

	public int getTipoCarrera() {
		return tipoCarrera;
	}

	public void setTipoCarrera(int tipoCarrera) {
		this.tipoCarrera = tipoCarrera;
	}

	public int getMetros() {
		return metros;
	}

	public void setMetros(int metros) {
		this.metros = metros;
	}

	public int getNroPrograma() {
		return nroPrograma;
	}

	public void setNroPrograma(int nroPrograma) {
		this.nroPrograma = nroPrograma;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	public int getEdad2() {
		return edad2;
	}

	public void setEdad2(int edad2) {
		this.edad2 = edad2;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	

	
		
		

	

	
	
	
	
	
}
