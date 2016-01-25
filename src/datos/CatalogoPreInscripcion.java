package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import conexion.DataConnection;
import entidades.Carrera;
import entidades.Nadador;
import entidades.NadadorCarreraIndividual;
import entidades.NadadorCarreraPosta;

public class CatalogoPreInscripcion {
	
	private static CatalogoPreInscripcion instance = null;
	public CatalogoPreInscripcion() 
	{
   
	}
	public static CatalogoPreInscripcion getInstance()
	{
		if(instance == null) 
		{
			instance = new CatalogoPreInscripcion();
		}
		return instance;
	}
	public ArrayList<NadadorCarreraIndividual> buscarNadadoresInscriptosACarreraIndividual(Carrera car, int nroTorneo, String fechaTorneo) 	
	{	
		//CON ESTE CRITERIO:
		//TipoCarrera 6 -> Edad == 6
		//TipoCarrera 7 -> Edad == 7
		//...
		//...
		//TipoCarrera 14 -> Edad == 14
		//TipoCarrera 15 -> Edad >= 15
		
		ArrayList<NadadorCarreraIndividual> listaNadadores = new ArrayList<NadadorCarreraIndividual>();
		String sql;
		if(car.getTipoCarrera() == 6)
		{
			sql = "SELECT * FROM nadador inner join preinscripcionindividual on dni = nroNadador "
					+ "where (cast (strftime('%Y.%m%d', ?) - strftime('%Y.%m%d', fechaNacimiento) as int)) <= 6 and nroCarrera = ? and nroTorneo = ? and dni in "
					+ "(SELECT n.dni FROM preinscripcionindividual nc "
					+ "inner join nadador n on n.dni = nc.nroNadador "
					+ "where nroCarrera = ? and nroPrograma = ? and nroTorneo = ?) and sexo = (select sexo from carrera c where nroCarrera = ? and nroPrograma = ?)"
					+ "order by nroClub, apellido";
		}
		else if (car.getTipoCarrera() < 13 && car.getTipoCarrera() >= 7)
		{
			sql = "SELECT * FROM nadador inner join preinscripcionindividual on dni = nroNadador "
					+ "where (cast (strftime('%Y.%m%d', ?) - strftime('%Y.%m%d', fechaNacimiento) as int)) = ? and nroCarrera = ? and nroTorneo = ? and dni in "
					+ "(SELECT n.dni FROM preinscripcionindividual nc "
					+ "inner join nadador n on n.dni = nc.nroNadador "
					+ "where nroCarrera = ? and nroPrograma = ? and nroTorneo = ?) and sexo = (select sexo from carrera c where nroCarrera = ? and nroPrograma = ?)"
					+ "order by nroClub, apellido";
		}
		else if(car.getTipoCarrera() == 13 || car.getTipoCarrera() == 14)
		{
			sql = "SELECT * FROM nadador inner join preinscripcionindividual on dni = nroNadador "
					+ "where ((cast(strftime('%Y.%m%d', ?) - strftime('%Y.%m%d', fechaNacimiento) as int)) ==13 or (cast(strftime('%Y.%m%d', ?) - strftime('%Y.%m%d', fechaNacimiento) as int)) ==14) and nroCarrera = ? and nroTorneo = ? and dni in "
					+ "(SELECT n.dni FROM preinscripcionindividual nc "
					+ "inner join nadador n on n.dni = nc.nroNadador "
					+ "where nroCarrera = ? and nroPrograma = ? and nroTorneo = ?) and sexo = (select sexo from carrera c where nroCarrera = ? and nroPrograma = ?)"
					+ "order by nroClub, apellido";
		}
		else
		{
			sql = "SELECT * FROM nadador inner join preinscripcionindividual on dni = nroNadador "
					+ "where ((cast(strftime('%Y.%m%d', ?) - strftime('%Y.%m%d', fechaNacimiento) as int)) >= 15) and nroCarrera = ? and nroTorneo = ? and dni in "
					+ "(SELECT n.dni FROM preinscripcionindividual nc "
					+ "inner join nadador n on n.dni = nc.nroNadador "
					+ "where nroCarrera = ? and nroPrograma = ? and nroTorneo = ?) and sexo = (select sexo from carrera c where nroCarrera = ? and nroPrograma = ?)"
					+ "order by nroClub, apellido";
		}
		
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			String fecha=null;

			Date d = new SimpleDateFormat("dd/MM/yyyy").parse(fechaTorneo);
			fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);

			if(car.getTipoCarrera() == 6)
			{	
				sentencia.setString(1, fecha);
				sentencia.setInt(2, car.getNroCarrera());
				sentencia.setInt(3, nroTorneo);
				sentencia.setInt(4, car.getNroCarrera());	
				sentencia.setInt(5, car.getNroPrograma());
				sentencia.setInt(6, nroTorneo);
				sentencia.setInt(7, car.getNroCarrera());
				sentencia.setInt(8, car.getNroPrograma());
			}
			else if (car.getTipoCarrera() < 13 && car.getTipoCarrera() > 6)
			{
				sentencia.setString(1, fecha);
				sentencia.setInt(2, car.getTipoCarrera());
				sentencia.setInt(3, car.getNroCarrera());
				sentencia.setInt(4, nroTorneo);
				sentencia.setInt(5, car.getNroCarrera());
				sentencia.setInt(6, car.getNroPrograma());
				sentencia.setInt(7, nroTorneo);
				sentencia.setInt(8, car.getNroCarrera());
				sentencia.setInt(9, car.getNroPrograma());
			}
			else if(car.getTipoCarrera() == 13 || car.getTipoCarrera() == 14)
			{
				sentencia.setString(1, fecha);
				sentencia.setString(2, fecha);
				sentencia.setInt(3, car.getNroCarrera());
				sentencia.setInt(4, nroTorneo);
				sentencia.setInt(5, car.getNroCarrera());
				sentencia.setInt(6, car.getNroPrograma());
				sentencia.setInt(7, nroTorneo);
				sentencia.setInt(8, car.getNroCarrera());
				sentencia.setInt(9, car.getNroPrograma());
			}
			else
			{
				sentencia.setString(1, fecha);
				sentencia.setInt(2, car.getNroCarrera());
				sentencia.setInt(3, nroTorneo);
				sentencia.setInt(4, car.getNroCarrera());
				sentencia.setInt(5, car.getNroPrograma());
				sentencia.setInt(6, nroTorneo);
				sentencia.setInt(7, car.getNroCarrera());
				sentencia.setInt(8, car.getNroPrograma());
			}
			rs = sentencia.executeQuery();
			
				while(rs.next())
				{					
					NadadorCarreraIndividual nadador = new NadadorCarreraIndividual();
					nadador.setNroCarrera(rs.getInt("nroCarrera"));									
					nadador.setDniNadador(rs.getInt("dni"));
					nadador.setNroPrograma(rs.getInt("nroPrograma"));
					nadador.setNroTorneo(rs.getInt(nroTorneo));
					nadador.setTiempoPreCompeticion(rs.getString("tiempoPreCompeticion"));
					listaNadadores.add(nadador);
				}
				
		}
		catch(SQLException | ParseException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}	
		
		return listaNadadores;
	}

	public ArrayList<Nadador> buscarNadadoresNoInscriptosACarreraIndividual(Carrera car, int nroTorneo, String fechaTorneo) 	
	{	
		//CON ESTE CRITERIO:
		//TipoCarrera 6 -> Edad == 6
		//TipoCarrera 7 -> Edad == 7
		//...
		//...
		//TipoCarrera 14 -> Edad == 14
		//TipoCarrera 15 -> Edad >= 15
		
		ArrayList<Nadador> listaNadadores = new ArrayList<Nadador>();
		String sql;
		//TODO ACA VA EL IF CON LA CONDICION DE LA CARRERA
		if(car.getTipoCarrera() == 6)
		{
			sql = "SELECT * FROM nadador "
					+ "where cast (strftime('%Y.%m%d', ?) - strftime('%Y.%m%d', fechaNacimiento) as int) <= 6 and dni not in "
					+ "(SELECT n.dni FROM preinscripcionindividual nc "
					+ "inner join nadador n on n.dni = nc.nroNadador "
					+ "where nroCarrera = ? and nroPrograma = ? and nroTorneo = ?) and sexo = (select sexo from carrera c where nroCarrera = ? and nroPrograma = ?)"
					+ "order by nroClub, apellido";
		}
		else if (car.getTipoCarrera() < 13 && car.getTipoCarrera() >= 7)
		{
			sql = "SELECT * FROM nadador where "
					+ "cast(strftime('%Y.%m%d', ?) - strftime('%Y.%m%d', fechaNacimiento) as int) = ? and dni not in "
					+ "(SELECT n.dni FROM preinscripcionindividual nc "
					+ "inner join nadador n on n.dni = nc.nroNadador "
					+ "where nroCarrera = ? and nroPrograma = ? and nroTorneo = ?) and sexo = (select sexo from carrera c where nroCarrera = ? and nroPrograma = ?)"
					+ "order by nroClub, apellido";
		}
		else if(car.getTipoCarrera() == 13 || car.getTipoCarrera() == 14)
		{
			sql = "SELECT * FROM nadador "
					+ "where (cast(strftime('%Y.%m%d', ?) - strftime('%Y.%m%d', fechaNacimiento) as int) ==13 or cast(strftime('%Y.%m%d', ?) - strftime('%Y.%m%d', fechaNacimiento) as int) ==14) and dni not in "
					+ "(SELECT n.dni FROM preinscripcionindividual nc "
					+ "inner join nadador n on n.dni = nc.nroNadador "
					+ "where nroCarrera = ? and nroPrograma = ? and nroTorneo = ?) and sexo = (select sexo from carrera c where nroCarrera = ? and nroPrograma = ?)"
					+ "order by nroClub, apellido";
		}
		else
		{
			sql = "SELECT * FROM nadador "
					+ "where cast (strftime('%Y.%m%d', ?) - strftime('%Y.%m%d', fechaNacimiento) as int) >= 15 and dni not in "
					+ "(SELECT n.dni FROM preinscripcionindividual nc "
					+ "inner join nadador n on n.dni = nc.nroNadador "
					+ "where nroCarrera = ? and nroPrograma = ? and nroTorneo = ?) and sexo = (select sexo from carrera c where nroCarrera = ? and nroPrograma = ?)"
					+ "order by nroClub, apellido";
		}
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			
			String fecha=null;
			

			Date d = new SimpleDateFormat("dd/MM/yyyy").parse(fechaTorneo);
			fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);

			if(car.getTipoCarrera() == 6)
			{
				sentencia.setString(1, fecha);
				sentencia.setInt(2, car.getNroCarrera());
				sentencia.setInt(3, car.getNroPrograma());
				sentencia.setInt(4, nroTorneo);
				sentencia.setInt(5, car.getNroCarrera());
				sentencia.setInt(6, car.getNroPrograma());
			}
			else if (car.getTipoCarrera() < 13 && car.getTipoCarrera() > 6)
			{
				sentencia.setString(1, fecha);
				sentencia.setInt(2, car.getTipoCarrera());
				sentencia.setInt(3, car.getNroCarrera());
				sentencia.setInt(4, car.getNroPrograma());
				sentencia.setInt(5, nroTorneo);
				sentencia.setInt(6, car.getNroCarrera());
				sentencia.setInt(7, car.getNroPrograma());
			}
			else if(car.getTipoCarrera() == 13 || car.getTipoCarrera() == 14)
			{
				sentencia.setString(1, fecha);
				sentencia.setString(2, fecha);
				sentencia.setInt(3, car.getNroCarrera());
				sentencia.setInt(4, car.getNroPrograma());
				sentencia.setInt(5, nroTorneo);
				sentencia.setInt(6, car.getNroCarrera());
				sentencia.setInt(7, car.getNroPrograma());
			}
			else
			{
				sentencia.setString(1, fecha);
				sentencia.setInt(2, car.getNroCarrera());
				sentencia.setInt(3, car.getNroPrograma());
				sentencia.setInt(4, nroTorneo);
				sentencia.setInt(5, car.getNroCarrera());
				sentencia.setInt(6, car.getNroPrograma());
			}
			rs = sentencia.executeQuery();
			
				while(rs.next())
				{					
					Nadador nadador = new Nadador();
					String fechaCorta=null;
					
					try {
						Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("fechaNacimiento"));
						 fechaCorta = new SimpleDateFormat("dd/MM/yyyy").format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					nadador.setDni(rs.getInt("dni"));
					nadador.setNombre(rs.getString("nombre"));
					nadador.setApellido(rs.getString("apellido"));
					nadador.setFechaNacimiento(fechaCorta);
					nadador.setNroClub(rs.getInt("nroClub"));
					nadador.setSexo(rs.getString("sexo").charAt(0));
					listaNadadores.add(nadador);
				}
				
		}
		catch(SQLException | ParseException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}	
		
		return listaNadadores;
	}
	
	public void preInscribirACarreraIndividual(int dniNadadorSeleccionado, int nroCarrera, int nroPrograma, int nroTorneo) 
	{
		String 	sql = "INSERT INTO preInscripcionIndividual (nroNadador, nroCarrera, nroPrograma, nroTorneo, tiempoPreCompeticion) VALUES(?,?,?,?,?)";
		PreparedStatement sentencia=null;
		Connection con = DataConnection.getInstancia().getConn();
			
		try
		{
			sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			

			sentencia.setInt(1, dniNadadorSeleccionado);
			sentencia.setInt(2, nroCarrera);
			sentencia.setInt(3, nroPrograma);
			sentencia.setInt(4, nroTorneo);
			sentencia.setString(5, "null");
			sentencia.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}		
	}
	
	public void dniNadadorSeleccionado(int dniNadadorSeleccionado, int nroCarrera, int nroPrograma, int nroTorneo) 
	{
		String sql = "DELETE FROM PreInscripcionIndividual WHERE nroNadador = ? and nroCarrera = ? and nroPrograma = ? and nroTorneo = ?";
		PreparedStatement sentencia = null;
		Connection con = DataConnection.getInstancia().getConn();
		try
		{
			sentencia= con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, dniNadadorSeleccionado);
			sentencia.setInt(2,nroCarrera);
			sentencia.setInt(3, nroPrograma);
			sentencia.setInt(4, nroTorneo);
			sentencia.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}		
	}
	
	public int contarNadadoresEnCarrera(int nroCarrera, int nroPrograma, int nroTorneo) 
	{
		int numeroNadadores = 0;
		String sql = "SELECT count(*) FROM preinscripcionindividual where nroCarrera = ? and nroPrograma = ? and nroTorneo = ?";
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try
		{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroCarrera);
			sentencia.setInt(2, nroPrograma);
			sentencia.setInt(3, nroTorneo);
			rs = sentencia.executeQuery();
			
				if(rs.next())
				{
					numeroNadadores = rs.getInt(1);
				}
				
		}
		catch(SQLException e)
			{
			e.printStackTrace();
			}
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}	
		
		return numeroNadadores;	
	}
	
	public int contarNadadoresEnCarreraPosta(int nroCarrera, int nroPrograma, int nroTorneo) 
	{
		int numeroNadadores = 0;
		String sql = "SELECT count(*) FROM preinscripcionposta where nroCarrera = ? and nroPrograma = ? and nroTorneo = ?";
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try
		{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroCarrera);
			sentencia.setInt(2, nroPrograma);
			sentencia.setInt(3, nroTorneo);
			rs = sentencia.executeQuery();
			
				if(rs.next())
				{
					numeroNadadores = rs.getInt(1);
				}
				
		}
		catch(SQLException e)
			{
			e.printStackTrace();
			}
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}	
		
		return numeroNadadores;	
	}
	
	
	public ArrayList<Nadador> buscarNadadoresPorCarreraOrdenadosPorTiempo(int nroCarrera, int nroPrograma) 
	{

		ArrayList<Nadador> listaNadadores = new ArrayList<Nadador>();
		String sql;

		sql = "SELECT n.* FROM nadador n inner join preinscripcionIndividual nc on n.dni = nc.nroNadador where nc.nroCarrera = ? and nroPrograma = ? order by n.nroClub";
		
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroCarrera);
			sentencia.setInt(2, nroPrograma);
			rs = sentencia.executeQuery();
			
				while(rs.next())
				{
					Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("fechaNacimiento"));
					String stringCorto = new SimpleDateFormat("dd/MM/yyyy").format(date);
					Nadador nadador = new Nadador();
					nadador.setDni(rs.getInt("dni"));
					nadador.setNombre(rs.getString("nombre"));
					nadador.setApellido(rs.getString("apellido"));
					nadador.setFechaNacimiento(stringCorto);
					nadador.setNroClub(rs.getInt("nroClub"));
					nadador.setSexo(rs.getString("sexo").charAt(0));
					listaNadadores.add(nadador);
				}
				
		}
		catch(SQLException | ParseException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}	
		
		return listaNadadores;
	}
	
	
	public void borrarPreInscripcionIndividual(int nroCarrera, int nroPrograma){
		String sql="DELETE FROM PreInscripcionIndividual WHERE nroCarrera=? AND nroPrograma=?";
		PreparedStatement sentencia = null;
		Connection con = DataConnection.getInstancia().getConn();
		try
		{
			sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroCarrera);
			sentencia.setInt(2, nroPrograma);
			sentencia.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}	
	}
	public void borrarPreInscripcionPosta(int nroCarrera, int nroPrograma) {
		String sql="DELETE FROM PreInscripcionPosta WHERE nroCarrera=? AND nroPrograma=?";
		PreparedStatement sentencia = null;
		Connection con = DataConnection.getInstancia().getConn();
		try
		{
			sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroCarrera);
			sentencia.setInt(2, nroPrograma);
			sentencia.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}	
	}
	public void preInscribirAPosta(int nroCarrera, int nroPrograma, int nroTorneo, int dni, int dni2, int dni3, int dni4)
	{
		String 	sql = "INSERT INTO PreInscripcionPosta (nroNadador1, nroNadador2, nroNadador3, nroNadador4, nroCarrera, nroPrograma, nroTorneo) VALUES(?,?,?,?,?,?,?)";
		PreparedStatement sentencia=null;
		Connection con = DataConnection.getInstancia().getConn();
			
		try
		{
			sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			

			sentencia.setInt(1, dni);
			sentencia.setInt(2, dni2);
			sentencia.setInt(3, dni3);
			sentencia.setInt(4, dni4);
			sentencia.setInt(5, nroCarrera);
			sentencia.setInt(6, nroPrograma);
			sentencia.setInt(7, nroTorneo);
			sentencia.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}		
	}
	public ArrayList<Nadador> buscarNadadoresNoInscriptosACarreraPosta(Carrera car, int nroTorneo) 
	{
		ArrayList<Nadador> listaNadadores = new ArrayList<Nadador>();
		String sql;
		if(car.getSexo()=='x')
		{
			sql = "select * from nadador where (dni not in (select nroNadador1 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador2 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador3 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador4 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?))))) "
					+ "order by nroClub, fechaNacimiento";
			}
		else
		{
			sql = "select * from nadador where (dni not in (select nroNadador1 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador2 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador3 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador4 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?))))) "
					+ "and sexo = (select sexo from carrera c where nroCarrera = ? and nroPrograma = ?)"
					+ "order by nroClub, fechaNacimiento";
		}
		
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if(car.getSexo()=='x')
			{
				sentencia.setInt(3, car.getNroCarrera());
				sentencia.setInt(2, car.getNroPrograma());
				sentencia.setInt(1, nroTorneo);

				
				sentencia.setInt(6, car.getNroCarrera());
				sentencia.setInt(5, car.getNroPrograma());
				sentencia.setInt(4, nroTorneo);

				
				sentencia.setInt(9, car.getNroCarrera());
				sentencia.setInt(8, car.getNroPrograma());
				sentencia.setInt(7, nroTorneo);

				
				sentencia.setInt(12, car.getNroCarrera());
				sentencia.setInt(11, car.getNroPrograma());
				sentencia.setInt(10, nroTorneo);
			}
			else
			{
				sentencia.setInt(3, car.getNroCarrera());
				sentencia.setInt(2, car.getNroPrograma());
				sentencia.setInt(1, nroTorneo);

				
				sentencia.setInt(6, car.getNroCarrera());
				sentencia.setInt(5, car.getNroPrograma());
				sentencia.setInt(4, nroTorneo);

				
				sentencia.setInt(9, car.getNroCarrera());
				sentencia.setInt(8, car.getNroPrograma());
				sentencia.setInt(7, nroTorneo);

				
				sentencia.setInt(12, car.getNroCarrera());
				sentencia.setInt(11, car.getNroPrograma());
				sentencia.setInt(10, nroTorneo);
			
				sentencia.setInt(13, car.getNroCarrera());
				sentencia.setInt(14, car.getNroPrograma());
				
			}
			rs = sentencia.executeQuery();
			
				while(rs.next())
				{					
					Nadador nadador = new Nadador();
					String fechaCorta=null;
					
					try {
						Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("fechaNacimiento"));
						 fechaCorta = new SimpleDateFormat("dd/MM/yyyy").format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					nadador.setDni(rs.getInt("dni"));
					nadador.setNombre(rs.getString("nombre"));
					nadador.setApellido(rs.getString("apellido"));
					nadador.setFechaNacimiento(fechaCorta);
					nadador.setNroClub(rs.getInt("nroClub"));
					nadador.setSexo(rs.getString("sexo").charAt(0));
					listaNadadores.add(nadador);
				}
				
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}	
		
		return listaNadadores;
	}
	
	
	public ArrayList<NadadorCarreraPosta> buscarTodosEquipoPosta(Carrera car, int nroTorneo) 
	{
		ArrayList<NadadorCarreraPosta> listaNadadores = new ArrayList<NadadorCarreraPosta>();
		String sql;
		if(car.getSexo()=='x')
		{
			sql = "SELECT * FROM nadador inner join preinscripcionPosta on dni = nroNadador1 "
					+ "where nroCarrera = ? and nroTorneo = ? and dni in "
					+ "(SELECT n.dni FROM preinscripcionPosta nc "
					+ "inner join nadador n on n.dni = nc.nroNadador1 "
					+ "where nroCarrera = ? and nroPrograma = ? and nroTorneo = ?)"
					+ "order by nroClub";
		}
		else
		{
			sql = "SELECT * FROM nadador inner join preinscripcionPosta on dni = nroNadador1 "
					+ "where nroCarrera = ? and nroTorneo = ? and dni in "
					+ "(SELECT n.dni FROM preinscripcionPosta nc "
					+ "inner join nadador n on n.dni = nc.nroNadador1 "
					+ "where nroCarrera = ? and nroPrograma = ? and nroTorneo = ?) and sexo = (select sexo from carrera c where nroCarrera = ? and nroPrograma = ?)"
					+ "order by nroClub";
		}
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if(car.getSexo()=='x')
			{
				sentencia.setInt(1, car.getNroCarrera());
				sentencia.setInt(2, nroTorneo);
				sentencia.setInt(3, car.getNroCarrera());
				sentencia.setInt(4, car.getNroPrograma());
				sentencia.setInt(5, nroTorneo);
			}
			else
			{
				sentencia.setInt(1, car.getNroCarrera());
				sentencia.setInt(2, nroTorneo);
				sentencia.setInt(3, car.getNroCarrera());
				sentencia.setInt(4, car.getNroPrograma());
				sentencia.setInt(5, nroTorneo);
				sentencia.setInt(6, car.getNroCarrera());
				sentencia.setInt(7, car.getNroPrograma());
			}
		
			rs = sentencia.executeQuery();
			
				while(rs.next())
				{					
					NadadorCarreraPosta nadador = new NadadorCarreraPosta();									
					nadador.setDniNadador1(rs.getInt("nroNadador1"));
					nadador.setDniNadador2(rs.getInt("nroNadador2"));
					nadador.setDniNadador3(rs.getInt("nroNadador3"));
					nadador.setDniNadador4(rs.getInt("nroNadador4"));
					nadador.setNroCarrera(rs.getInt("nroCarrera"));
					nadador.setNroPrograma(rs.getInt("nroPrograma"));
					nadador.setNroTorneo(rs.getInt(nroTorneo));
					listaNadadores.add(nadador);
				}
				
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}	
		
		return listaNadadores;
	}
	public void eliminarDePreInscripcionPosta(int nroCarrera, int nroTorneo, int nroPrograma, int dni1) {
		
			String sql="DELETE FROM PreInscripcionPosta WHERE nroCarrera=? AND nroTorneo=? AND nroPrograma=? AND nroNadador1=?";
			PreparedStatement sentencia = null;
			Connection con = DataConnection.getInstancia().getConn();
			try
			{
				sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				sentencia.setInt(1, nroCarrera);
				sentencia.setInt(2, nroTorneo);
				sentencia.setInt(3, nroPrograma);
				sentencia.setInt(4, dni1);
				sentencia.executeUpdate();

			}
			catch(SQLException e){
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(sentencia!=null && !sentencia.isClosed())
					{
						sentencia.close();
					}
					DataConnection.getInstancia().CloseConn();
				}
				catch (SQLException sqle)
				{
					sqle.printStackTrace();
				}
			}	
		}
	public ArrayList<Nadador> ordenarNadadoresPorClub(ArrayList<Nadador> listaNadadores) {
		
		return null;
	}
	
	
	public ArrayList<Nadador> buscarMuchosNadadoresPorDniPosta(Carrera car, int dni, int nroTorneo) {
		ArrayList<Nadador> listaNadadores = new ArrayList<Nadador>();
		String sql;
		if(car.getSexo()=='x')
		{
			sql = "select * from nadador where (dni like ?) and (dni not in (select nroNadador1 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador2 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador3 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador4 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?))))) "
					+ "order by nroClub, fechaNacimiento";
			}
		else
		{
			sql = "select * from nadador where (dni like ?) and (dni not in (select nroNadador1 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador2 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador3 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador4 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?))))) "
					+ "and sexo = (select sexo from carrera c where nroCarrera = ? and nroPrograma = ?)"
					+ "order by nroClub, fechaNacimiento";
		}
		
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if(car.getSexo()=='x')
			{
				sentencia.setInt(4, car.getNroCarrera());
				sentencia.setInt(3, car.getNroPrograma());
				sentencia.setInt(2, nroTorneo);
				sentencia.setString(1, Integer.toString(dni) + "%");

				
				sentencia.setInt(7, car.getNroCarrera());
				sentencia.setInt(6, car.getNroPrograma());
				sentencia.setInt(5, nroTorneo);

				
				sentencia.setInt(10, car.getNroCarrera());
				sentencia.setInt(9, car.getNroPrograma());
				sentencia.setInt(8, nroTorneo);

				
				sentencia.setInt(13, car.getNroCarrera());
				sentencia.setInt(12, car.getNroPrograma());
				sentencia.setInt(11, nroTorneo);
			}
			else
			{
				sentencia.setInt(4, car.getNroCarrera());
				sentencia.setInt(3, car.getNroPrograma());
				sentencia.setInt(2, nroTorneo);
				sentencia.setString(1, Integer.toString(dni) + "%");
				
				sentencia.setInt(7, car.getNroCarrera());
				sentencia.setInt(6, car.getNroPrograma());
				sentencia.setInt(5, nroTorneo);

				
				sentencia.setInt(10, car.getNroCarrera());
				sentencia.setInt(9, car.getNroPrograma());
				sentencia.setInt(8, nroTorneo);

				
				sentencia.setInt(13, car.getNroCarrera());
				sentencia.setInt(12, car.getNroPrograma());
				sentencia.setInt(11, nroTorneo);
			
				sentencia.setInt(14, car.getNroCarrera());
				sentencia.setInt(15, car.getNroPrograma());
				
			}
			rs = sentencia.executeQuery();
			
				while(rs.next())
				{					
					Nadador nadador = new Nadador();
					String fechaCorta=null;
					
					try {
						Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("fechaNacimiento"));
						 fechaCorta = new SimpleDateFormat("dd/MM/yyyy").format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					nadador.setDni(rs.getInt("dni"));
					nadador.setNombre(rs.getString("nombre"));
					nadador.setApellido(rs.getString("apellido"));
					nadador.setFechaNacimiento(fechaCorta);
					nadador.setNroClub(rs.getInt("nroClub"));
					nadador.setSexo(rs.getString("sexo").charAt(0));
					listaNadadores.add(nadador);
				}
				
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}	
		
		return listaNadadores;
	}
	
	public ArrayList<Nadador> buscarMuchosNadadoresPorNombreYApellidoPosta(Carrera car, String nombreYApellido, int nroTorneo) {
		
		ArrayList<Nadador> listaNadadores = new ArrayList<Nadador>();
		String sql;
		if(car.getSexo()=='x')
		{
			sql = "select * from nadador where ((nombre || ' ' || apellido) like ? or (apellido || ' ' || nombre) like ?) and (dni not in (select nroNadador1 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador2 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador3 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador4 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?))))) "
					+ "order by nroClub, fechaNacimiento";
			}
		else
		{
			sql = "select * from nadador where (nombre || ' ' || apellido) like ? or (apellido || ' ' || nombre) like ? and (dni not in (select nroNadador1 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador2 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador3 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?) "
					+ "and (dni not in (select nroNadador4 from preinscripcionposta where nroTorneo = ? and nroPrograma = ? and nroCarrera = ?))))) "
					+ "and sexo = (select sexo from carrera c where nroCarrera = ? and nroPrograma = ?)"
					+ "order by nroClub, fechaNacimiento";
		}
		
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if(car.getSexo()=='x')
			{
				sentencia.setInt(5, car.getNroCarrera());
				sentencia.setInt(4, car.getNroPrograma());
				sentencia.setInt(3, nroTorneo);
				sentencia.setString(1, nombreYApellido+"%");
				sentencia.setString(2, nombreYApellido+"%");

				
				sentencia.setInt(8, car.getNroCarrera());
				sentencia.setInt(7, car.getNroPrograma());
				sentencia.setInt(6, nroTorneo);

				
				sentencia.setInt(11, car.getNroCarrera());
				sentencia.setInt(10, car.getNroPrograma());
				sentencia.setInt(9, nroTorneo);

				
				sentencia.setInt(14, car.getNroCarrera());
				sentencia.setInt(13, car.getNroPrograma());
				sentencia.setInt(12, nroTorneo);
			}
			else
			{
				sentencia.setInt(4, car.getNroCarrera());
				sentencia.setInt(3, car.getNroPrograma());
				sentencia.setInt(2, nroTorneo);
				sentencia.setString(1, nombreYApellido+"%");
				sentencia.setString(2, nombreYApellido+"%");
				
				sentencia.setInt(7, car.getNroCarrera());
				sentencia.setInt(6, car.getNroPrograma());
				sentencia.setInt(5, nroTorneo);

				
				sentencia.setInt(10, car.getNroCarrera());
				sentencia.setInt(9, car.getNroPrograma());
				sentencia.setInt(8, nroTorneo);

				
				sentencia.setInt(13, car.getNroCarrera());
				sentencia.setInt(12, car.getNroPrograma());
				sentencia.setInt(11, nroTorneo);
			
				sentencia.setInt(14, car.getNroCarrera());
				sentencia.setInt(15, car.getNroPrograma());
				
			}
			rs = sentencia.executeQuery();
			
				while(rs.next())
				{					
					Nadador nadador = new Nadador();
					String fechaCorta=null;
					
					try {
						Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("fechaNacimiento"));
						 fechaCorta = new SimpleDateFormat("dd/MM/yyyy").format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					nadador.setDni(rs.getInt("dni"));
					nadador.setNombre(rs.getString("nombre"));
					nadador.setApellido(rs.getString("apellido"));
					nadador.setFechaNacimiento(fechaCorta);
					nadador.setNroClub(rs.getInt("nroClub"));
					nadador.setSexo(rs.getString("sexo").charAt(0));
					listaNadadores.add(nadador);
				}
				
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			try
			{
				if(sentencia!=null && !sentencia.isClosed())
				{
					sentencia.close();
				}
				DataConnection.getInstancia().CloseConn();
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
		}	
		
		return listaNadadores;
	}
		
		
	}

