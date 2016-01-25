package negocio;

import java.util.ArrayList;

import javax.swing.table.TableModel;

import entidades.Carrera;

import entidades.Club;
import entidades.Inscripcion;
import entidades.Nadador;
import entidades.NadadorCarreraIndividual;
import entidades.NadadorCarreraPosta;
import entidades.Programa;
import entidades.Serie;
import entidades.Torneo;
import datos.*;

public class ControladorNatacion 
{
//Definicion de staticos
		
		private CatalogoInscripciones ci = new CatalogoInscripciones();
		private CatalogoNadadores cn = new CatalogoNadadores();
		private CatalogoClubes cc = new CatalogoClubes();
		private CatalogoTorneos ct = new CatalogoTorneos();
		private CatalogoCarreras cca = new CatalogoCarreras();
		private CatalogoPreInscripcion cpi = new CatalogoPreInscripcion();
		private  Torneo torneoActual = null;
		private static ControladorNatacion instancia = null;
		
		public ControladorNatacion() 
		{
		   
		}
		public static ControladorNatacion getInstance() 
		{
			if(instancia == null)
			{
				instancia = new ControladorNatacion();
			}
			return instancia;
		}
//Club	
		public ArrayList<Club> buscarClubes()
		{
			return cc.buscarClubes();
		}
		
		public Club buscarClubPorNumeroClub(int nroClub)
		{
			return CatalogoClubes.getInstance().buscarClubPorNumeroClub(nroClub);
		}
		
			
		//Nadador
		public void cargarNadador(char sexo, String nombre, String apellido, String fechaNacimiento, int dni, int nroClub) {
			
			cn.cargarNadador(sexo, nombre, apellido, fechaNacimiento, dni, nroClub);
		}
		public boolean existeNadador(int dni) {
			
			return cn.existeNadador(dni);
			
		}
		
		//Filtro por dni
			public ArrayList<Nadador> buscarMuchosNadadoresPorDni(int dni) 
			{
			
				return cn.buscarMuchosNadadorPorDni(dni);
				
			}
		//Filtro por nombre o apellido	
			public ArrayList<Nadador> buscarMuchosNadadoresPorNombreYApellido(String nombreYApellido) 
			{
				return cn.buscarMuchosNadadoresPorNombre(nombreYApellido);
			}
		
			public ArrayList<Nadador> buscarTodosNadadores() 
			{
				return cn.buscarTodosNadadores();
			}
			
			
			public void eliminarNadador(int dni)
			{
				cn.eliminarNadador(dni);
			}
			
			public void modificarNadador(int dni, String apellido, String nombre, int nroClub, char sexo, String fechaNacimiento) 
			{
				cn.modificarNadador(dni, apellido, nombre, nroClub, sexo, fechaNacimiento);
			}	
			
			public Nadador buscarNadadorPorDni(int dni)
			{
				return cn.buscarNadadorPorDni(dni);
			}
//PreInscripcion			
			public ArrayList<NadadorCarreraIndividual> buscarNadadoresInscriptosACarreraIndividual(Carrera car,int nroTorneo){
				return cpi.buscarNadadoresInscriptosACarreraIndividual(car, nroTorneo, torneoActual.getFecha());
			}
			public ArrayList<Nadador> buscarNadadoresNoInscriptosACarreraIndividual(Carrera car,int nroTorneo){
				return cpi.buscarNadadoresNoInscriptosACarreraIndividual(car, nroTorneo, torneoActual.getFecha());
			}
			public void preInscribirACarreraIndividual(int dniNadadorSeleccionado, int nroCarrera, int nroPrograma, int nroTorneo)
			{
				cpi.preInscribirACarreraIndividual(dniNadadorSeleccionado, nroCarrera, nroPrograma, nroTorneo);
			}
			public void eliminarDePreInscripcionIndividual(int dniNadadorSeleccionado, int nroCarrera, int nroPrograma, int nroTorneo) 
			{
				cpi.dniNadadorSeleccionado(dniNadadorSeleccionado, nroCarrera, nroPrograma, nroTorneo);
			}
			
			public void preInscribirAPosta(int nroCarrera, int nroPrograma, int nroTorneo, int dni, int dni2, int dni3,	int dni4) 
			{
				cpi.preInscribirAPosta(nroCarrera, nroPrograma, nroTorneo, dni, dni2, dni3, dni4);
			}
			
			public ArrayList<Nadador> buscarNadadoresNoInscriptosACarreraPosta(Carrera carreraSeleccionada, int nroTorneo)
			{
				return cpi.buscarNadadoresNoInscriptosACarreraPosta(carreraSeleccionada, nroTorneo);
			}
			
			public ArrayList<NadadorCarreraPosta> buscarTodosEquipoPosta(Carrera car, int nroTorneo) 
			{
				return CatalogoPreInscripcion.getInstance().buscarTodosEquipoPosta(car, nroTorneo);
			}

			
//Torneo
			public ArrayList<Torneo> buscarTorneos()
			{
				return ct.buscarTorneos();
			}
			
			public void cargarTorneo(String fecha, int nroPrograma, int nroClub)
			{
				int nroTorneo = CatalogoTorneos.getInstance().ultimoNroTorneo()+1;
				ct.registrarTorneo(fecha,nroTorneo, nroPrograma, nroClub);
			}
			public void eliminarTorneo(int nroTorneo)
			{
				 eliminarTorneo(nroTorneo);
			}
			
//Programa
			public void cargarPrograma(int nroPrograma, String descripcion) 
			{
				CatalogoPrograma.getInstance().cargarPrograma(nroPrograma, descripcion);
			}
			
			public ArrayList<Programa> buscarTodosProgramas()
			{
				return CatalogoPrograma.getInstance().buscarTodosProgramas();
			}
//Carrera
			public void registrarCarrera(int nroCarrera,int tipoCarrera,int metros,int nroPrograma,String sexo,String estilo)
			{
				cca.registrarCarrera(nroCarrera,tipoCarrera,metros,nroPrograma,sexo,estilo);
			}
			
			public ArrayList<Carrera> buscarCarrerasPrograma(int nroPrograma)
			{
				return cca.buscarCarrerasPrograma(nroPrograma);
			}
			
			public ArrayList<Carrera> buscarCarreraProgramaNoCargadas(int nroPrograma, int nroTorneo)
			{
				return cca.buscarCarrerasProgramaNoCargadas(nroPrograma, nroTorneo);
			}
			
			public ArrayList<Carrera> buscarPostasProgramaNoCargadas(int nroPrograma, int nroTorneo)
			{
				return cca.buscarPostasProgramaNoCargadas(nroPrograma, nroTorneo);
			}
			
//Serie
			public ArrayList<Serie> buscarSeriesPorCarrera(int nroCarrera, int nroPrograma, int nroTorneo)
			{
				return CatalogoSeries.getInstance().buscarSeriesPorCarrera(nroCarrera, nroPrograma, nroTorneo);
			}
			
			//Series e Inscripciones
			public boolean generarSeriesPorCarrera(int nroCarrera, int nroPrograma, int nroTorneo)
			{
				int numeroNadador = CatalogoPreInscripcion.getInstance().contarNadadoresEnCarrera(nroCarrera, nroPrograma, nroTorneo);
				int nroClub = ControladorNatacion.getInstance().getTorneoActual().getNroClub();

					if(nroClub == 8)
					{
						if (numeroNadador % 5 != 0)
						{
							for (int i=1; i <= (int)(numeroNadador/5)+1; i++)
							{
								CatalogoSeries.getInstance().insertarSerie(CatalogoSeries.getInstance().maximaSeriePorCarrera(nroCarrera,nroPrograma, nroTorneo) +1, nroCarrera, nroPrograma, nroTorneo);
							}
						}
						else
						{
							for (int i=1; i <= (numeroNadador/5); i++)
							{
								CatalogoSeries.getInstance().insertarSerie(CatalogoSeries.getInstance().maximaSeriePorCarrera(nroCarrera,nroPrograma, nroTorneo) +1, nroCarrera, nroPrograma, nroTorneo);
							}
						}
					}
					else
					{
							if (numeroNadador % 6 != 0)
						{
							for (int i=1; i <= (int)(numeroNadador/6)+1; i++)
							{
								CatalogoSeries.getInstance().insertarSerie(CatalogoSeries.getInstance().maximaSeriePorCarrera(nroCarrera,nroPrograma, nroTorneo) +1, nroCarrera, nroPrograma, nroTorneo);
							}
						}
						else
						{
							for (int i=1; i <= (numeroNadador/6); i++)
							{
								CatalogoSeries.getInstance().insertarSerie(CatalogoSeries.getInstance().maximaSeriePorCarrera(nroCarrera,nroPrograma, nroTorneo) +1, nroCarrera, nroPrograma, nroTorneo);
							}
						}
					}
					
					this.distribuirNadadoresEnSeries(CatalogoPreInscripcion.getInstance().buscarNadadoresPorCarreraOrdenadosPorTiempo(nroCarrera, nroPrograma), CatalogoSeries.getInstance().buscarSeriesPorCarrera(nroCarrera, nroPrograma, nroTorneo), numeroNadador, nroCarrera, nroPrograma, nroTorneo);
					if(nroCarrera<=40)
					{
						CatalogoPreInscripcion.getInstance().borrarPreInscripcionIndividual(nroCarrera, nroPrograma);
					}
					else
					{
						CatalogoPreInscripcion.getInstance().borrarPreInscripcionPosta(nroCarrera, nroPrograma);
					};
					return true;
				
			}
			
			private void distribuirNadadoresEnSeries(ArrayList<Nadador> nadadoresEnCarreraOrdenadosPorTiempo, ArrayList<Serie> seriesEnCarrera, int numeroNadadores, int nroCarrera, int nroPrograma, int nroTorneo)
			{
				int cantidadSeries;
				int nroClub = ControladorNatacion.getInstance().getTorneoActual().getNroClub();
				//San Martin de Pellegrini tiene nro club = 8.
				if(nroClub == 8)
				{		
					if (numeroNadadores % 5 != 0)
					{
						cantidadSeries = (int)(numeroNadadores/5) + 1;
					}
					else
					{
						cantidadSeries = numeroNadadores/5;
					}
				}
				else 
				{
					if (numeroNadadores % 6 != 0)
					{
						cantidadSeries = (int)(numeroNadadores/6) + 1;
					}
					else
					{
						cantidadSeries = numeroNadadores/6;
					}
				}
					int nroNadadoresPorSerie = (int) numeroNadadores/cantidadSeries;
					int restoNroNadadores = numeroNadadores % cantidadSeries;
					int cont = 0;
					int contSeries = 0;
					
					if(nroNadadoresPorSerie>=1)
					{
						for (int i = 1; i <= cantidadSeries; i++)
						{
							CatalogoInscripciones.getInstance().cargarInscripciones(nadadoresEnCarreraOrdenadosPorTiempo.get(cont).getDni(), seriesEnCarrera.get(i-1).getNroSerie(), 3, nroCarrera, nroPrograma, nroTorneo);
							cont += 1;
						}
					}
					else if(nroNadadoresPorSerie == 0)
					{
						for (int i = 1; i<=restoNroNadadores; i ++)
						{
								CatalogoInscripciones.getInstance().cargarInscripciones(nadadoresEnCarreraOrdenadosPorTiempo.get(cont).getDni(), seriesEnCarrera.get(i-1).getNroSerie(), 3, nroCarrera, nroPrograma, nroTorneo);
							cont += 1;
						}
					}
					if(nroNadadoresPorSerie>=2)
					{
						for (int i = 1; i <= cantidadSeries; i++)
						{
							CatalogoInscripciones.getInstance().cargarInscripciones(nadadoresEnCarreraOrdenadosPorTiempo.get(cont).getDni(), seriesEnCarrera.get(i-1).getNroSerie(), 4, nroCarrera, nroPrograma, nroTorneo);
							cont += 1;
						}
					}
					else if(nroNadadoresPorSerie == 1)
					{
						for (int i = 1; i<=restoNroNadadores; i ++)
						{
								CatalogoInscripciones.getInstance().cargarInscripciones(nadadoresEnCarreraOrdenadosPorTiempo.get(cont).getDni(), seriesEnCarrera.get(i-1).getNroSerie(), 4, nroCarrera, nroPrograma, nroTorneo);
							cont += 1;
						}
					}
					if(nroNadadoresPorSerie>=3)
					{
						for (int i = 1; i <= cantidadSeries; i++)
						{
							CatalogoInscripciones.getInstance().cargarInscripciones(nadadoresEnCarreraOrdenadosPorTiempo.get(cont).getDni(), seriesEnCarrera.get(i-1).getNroSerie(), 2, nroCarrera, nroPrograma, nroTorneo);
							cont += 1;
						}
					}
					else if(nroNadadoresPorSerie == 2)
					{
						for (int i = 1; i<=restoNroNadadores; i ++)
						{
								CatalogoInscripciones.getInstance().cargarInscripciones(nadadoresEnCarreraOrdenadosPorTiempo.get(cont).getDni(), seriesEnCarrera.get(i-1).getNroSerie(), 2, nroCarrera, nroPrograma, nroTorneo);
							cont += 1;
						}
					}
					if(nroNadadoresPorSerie>=4)
					{
						for (int i = 1; i <= cantidadSeries; i++)
						{
							CatalogoInscripciones.getInstance().cargarInscripciones(nadadoresEnCarreraOrdenadosPorTiempo.get(cont).getDni(), seriesEnCarrera.get(i-1).getNroSerie(), 5, nroCarrera, nroPrograma, nroTorneo);
							cont += 1;
						}
					}
					else if(nroNadadoresPorSerie == 3)
					{
						for (int i = 1; i<=restoNroNadadores; i ++)
						{
								CatalogoInscripciones.getInstance().cargarInscripciones(nadadoresEnCarreraOrdenadosPorTiempo.get(cont).getDni(), seriesEnCarrera.get(i-1).getNroSerie(), 5, nroCarrera, nroPrograma, nroTorneo);
							cont += 1;
						}
					}
					if(nroNadadoresPorSerie>=5)
					{
						for (int i = 1; i <= cantidadSeries; i++)
						{
							CatalogoInscripciones.getInstance().cargarInscripciones(nadadoresEnCarreraOrdenadosPorTiempo.get(cont).getDni(), seriesEnCarrera.get(i-1).getNroSerie(), 1, nroCarrera, nroPrograma, nroTorneo);
							cont += 1;
						}
					}
					else if(nroNadadoresPorSerie == 4)
					{
						for (int i = 1; i<=restoNroNadadores; i ++)
						{
								CatalogoInscripciones.getInstance().cargarInscripciones(nadadoresEnCarreraOrdenadosPorTiempo.get(cont).getDni(), seriesEnCarrera.get(i-1).getNroSerie(), 1, nroCarrera, nroPrograma, nroTorneo);
							cont += 1;
						}
					}
					if(nroNadadoresPorSerie>=6)
					{
						for (int i = 1; i <= cantidadSeries; i++)
						{
							CatalogoInscripciones.getInstance().cargarInscripciones(nadadoresEnCarreraOrdenadosPorTiempo.get(cont).getDni(), seriesEnCarrera.get(i-1).getNroSerie(), 6, nroCarrera, nroPrograma, nroTorneo);
							cont += 1;
						}
					}
					else if(nroNadadoresPorSerie == 5)
					{
						for (int i = 1; i<=restoNroNadadores; i ++)
						{
								CatalogoInscripciones.getInstance().cargarInscripciones(nadadoresEnCarreraOrdenadosPorTiempo.get(cont).getDni(), seriesEnCarrera.get(i-1).getNroSerie(), 6, nroCarrera, nroPrograma, nroTorneo);
							cont += 1;
						}
					}
				
			}
			//tiempo

			public void cargarDescalificacion(int nroTorneo, int nroPrograma, int nroCarrera, int nroSerie, ArrayList<String> descalificacion, boolean[] checkboxActive)
			{
				CatalogoInscripciones.getInstance().cargarDescalificacion(nroTorneo,nroPrograma,nroCarrera,nroSerie,descalificacion,checkboxActive);
			}
			
			public void cargarTiempo(int nroTorneo, int nroPrograma, int nroCarrera, int nroSerie, String tiempo[])
			{
				CatalogoInscripciones.getInstance().cargarTiempo(nroTorneo,nroPrograma,nroCarrera,nroSerie,tiempo);

			}
			
			public ArrayList<Inscripcion> buscarInscripcion(int nroSerie, int nroCarrera, int nroTorneo, int nroPrograma)
			{
				return CatalogoInscripciones.getInstance().buscarInscripcion(nroSerie, nroCarrera, nroTorneo, nroPrograma);
			}
			public boolean existeInscripcion(int nroCarrera, int nroTorneo, int nroPrograma) {
				
				return ci.existeInscripcion(nroCarrera, nroTorneo, nroPrograma);
			}
			
			//Fin de series e inscripciones
			
			
			//Inscripcion Grupal (posta)
			
			public void generarSeriesPosta(ArrayList<NadadorCarreraPosta> nadadores, int nroCarrera, int nroPrograma, int nroTorneo )
			{
				
				int numeroNadador = CatalogoPreInscripcion.getInstance().contarNadadoresEnCarreraPosta(nroCarrera, nroPrograma, nroTorneo);
				int nroClub = ControladorNatacion.getInstance().getTorneoActual().getNroClub();
				if(nroClub == 8)
				{
					if (numeroNadador % 5 != 0)
					{
						for (int i=1; i <= (int)(numeroNadador/5)+1; i++)
						{
							CatalogoSeries.getInstance().insertarSerie(CatalogoSeries.getInstance().maximaSeriePorCarrera(nroCarrera,nroPrograma, nroTorneo) +1, nroCarrera, nroPrograma, nroTorneo);
						}
					}
					else
					{
						for (int i=1; i <= (numeroNadador/5); i++)
						{
							CatalogoSeries.getInstance().insertarSerie(CatalogoSeries.getInstance().maximaSeriePorCarrera(nroCarrera,nroPrograma, nroTorneo) +1, nroCarrera, nroPrograma, nroTorneo);
						}
					}
				}
				else
				{
					if (numeroNadador % 6 != 0)
					{
						for (int i=1; i <= (int)(numeroNadador/6)+1; i++)
						{
							CatalogoSeries.getInstance().insertarSerie(CatalogoSeries.getInstance().maximaSeriePorCarrera(nroCarrera,nroPrograma, nroTorneo) +1, nroCarrera, nroPrograma, nroTorneo);
						}
					}
					else
					{
						for (int i=1; i <= (numeroNadador/6); i++)
						{
							CatalogoSeries.getInstance().insertarSerie(CatalogoSeries.getInstance().maximaSeriePorCarrera(nroCarrera,nroPrograma, nroTorneo) +1, nroCarrera, nroPrograma, nroTorneo);
						}
					}
				}
				int cantidadSeries;
				if(nroClub == 8)
				{
					if (numeroNadador % 5 != 0)
					{
						cantidadSeries = (int)(numeroNadador/5) + 1;
					}
					else
					{
						cantidadSeries = numeroNadador/5;
					}
				}
				else
				{
					if (numeroNadador % 6 != 0)
					{
						cantidadSeries = (int)(numeroNadador/6) + 1;
					}
					else
					{
						cantidadSeries = numeroNadador/6;
					}
				}
				int nadadoresPorSerie=(int)numeroNadador/cantidadSeries;
				int restoNadadores= numeroNadador%cantidadSeries;
				int cont=0;
				for(int j=1;j<=cantidadSeries;j++)
				{	
					int i=1;
					for( i=1;i<=nadadoresPorSerie;i++)
					{
						//nroSerie=j ,  nroAndarivel=i;
						CatalogoInscripciones.getInstance().cargarInscripcionesPosta(nadadores.get(cont).getDniNadador1(), nadadores.get(cont).getDniNadador2(), nadadores.get(cont).getDniNadador3(), nadadores.get(cont).getDniNadador4(), j, i, nroCarrera, nroPrograma, nroTorneo);
					cont=cont+1;
					}
					if(restoNadadores!=0)
					{
						CatalogoInscripciones.getInstance().cargarInscripcionesPosta(nadadores.get(cont).getDniNadador1(), nadadores.get(cont).getDniNadador2(), nadadores.get(cont).getDniNadador3(), nadadores.get(cont).getDniNadador4(), j, i +1, nroCarrera, nroPrograma, nroTorneo);
						cont=cont+1;
						restoNadadores=restoNadadores-1;
					}
				}
				CatalogoPreInscripcion.getInstance().borrarPreInscripcionPosta(nroCarrera, nroPrograma);
			}
			
			public void eliminarDePreInscripcionPosta(int nroCarrera, int nroTorneo, int nroPrograma, int dni1) {
				
				CatalogoPreInscripcion.getInstance().eliminarDePreInscripcionPosta(nroCarrera, nroTorneo, nroPrograma, dni1);
				
			}
			public ArrayList<Nadador> buscarMuchosNadadoresPorDniPosta(Carrera car, int dni, int nroTor) {
				
				return CatalogoPreInscripcion.getInstance().buscarMuchosNadadoresPorDniPosta(car, dni, nroTor);
			}
			
			

			//Fin posta
			
			
//Descalificaciones
			public void cargarDescalificacion(String motivoDesc)
			{
				CatalogoDescalificaciones.getInstance().cargarDescalificacion(motivoDesc);
			}
			public ArrayList<String> getDescalificaciones()
			{
				return CatalogoDescalificaciones.getInstance().getDescalificaciones();
			}
			public void eliminarDescalificacion(int nroDesc)
			{
				CatalogoDescalificaciones.getInstance().eliminarDescalificacion(nroDesc);
			}
			public void modificarDescalificacion(int nroDesc, String motivoDesc)
			{
				CatalogoDescalificaciones.getInstance().modificarDescalificacion(nroDesc, motivoDesc);
			}
			public int getNroConDesc(String motivoDescalificacion)
			{
				return CatalogoDescalificaciones.getInstance().getNroConDesc( motivoDescalificacion);
			}
//Torneo --> Ya tengo el programa
			public Torneo getTorneoActual() {
				return torneoActual;
			}
			public void setTorneoActual(Torneo torneoActual) {
				this.torneoActual = torneoActual;
			}
			public int nroTorneo(){
				return (CatalogoTorneos.getInstance().ultimoNroTorneo()+1);
			}
			public ArrayList<Nadador> buscarMuchosNadadoresPorNombreYApellidoPosta(Carrera carreraSeleccionada, String text, int nroTorneo) {
				
				return CatalogoPreInscripcion.getInstance().buscarMuchosNadadoresPorNombreYApellidoPosta(carreraSeleccionada, text, nroTorneo);
			}
		
			





			
}