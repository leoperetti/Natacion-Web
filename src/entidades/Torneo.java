package entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Torneo 
{
	private int nroTorneo, nroPrograma, nroClub;
	private String fecha;
	

	@Override
	public String toString()
	{
		return "Nro: "+this.nroTorneo+"  Se realiza en: "+this.nroClub+" - Fecha: "+ fecha+ " Programa: "+this.nroPrograma;
	}
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public int getNroClub() {
		return nroClub;
	}

	public void setNroClub(int nroClub) {
		this.nroClub = nroClub;
	}


	
}
