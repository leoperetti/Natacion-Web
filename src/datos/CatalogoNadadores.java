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
import entidades.Inscripcion;
import entidades.Nadador;

public class CatalogoNadadores {

	private static CatalogoNadadores instance = null;
	public CatalogoNadadores() 
	{
	   
	}
	public static CatalogoNadadores getInstance() 
	{
		if(instance == null)
		{
			instance = new CatalogoNadadores();
		}
		return instance;
	}
	
	
	public Nadador buscarNadadorPorAndarivel(Inscripcion i)
	{	
		Nadador nadador = new Nadador();
		String sql = "select n.* from inscripcion i inner join nadador n on n.dni=i.nroNadador "
				+ "where nroAndarivel=? and nroSerie=? and nroCarrera=? and nroPrograma=? and nroTorneo=? order by n.nroClub" ;
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try
		{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, i.getNroAndarivel());
			sentencia.setInt(2, i.getNroSerie());
			sentencia.setInt(3, i.getNroCarrera());
			sentencia.setInt(4, i.getNroPrograma());
			sentencia.setInt(5, i.getNroTorneo());
			rs = sentencia.executeQuery();
			
			while(rs.next())
			{			
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("fechaNacimiento"));
				String stringCorto = new SimpleDateFormat("dd/MM/yyyy").format(date);
				nadador = new Nadador();
				nadador.setDni(rs.getInt("dni"));
				nadador.setNombre(rs.getString("nombre"));
				nadador.setApellido(rs.getString("apellido"));
				nadador.setFechaNacimiento(stringCorto);
				nadador.setNroClub(rs.getInt("nroClub"));
				nadador.setSexo(rs.getString("sexo").charAt(0));
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
		return nadador;
	}
	
	public void cargarNadador(char sexo, String nombre, String apellido, String fechaNacimiento, int dni, int nroClub)
	{
		String sql;
		PreparedStatement sentencia=null;
		Connection con = DataConnection.getInstancia().getConn();
			
		try
		{
			sql = "INSERT INTO nadador (dni, nombre, apellido, nroClub, fechaNacimiento, sexo) VALUES(?,?,?,?,?,?)";
			sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento);
			String stringLargo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	
			sentencia.setInt(1, dni);
			sentencia.setString(2, nombre);
			sentencia.setString(3, apellido);
			sentencia.setInt(4, nroClub);
			sentencia.setString(5, stringLargo);
			sentencia.setString(6, String.valueOf(sexo));
			sentencia.executeUpdate();
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
	}
	public ArrayList<Nadador> buscarMuchosNadadorPorDni(int dni)
	{
		ArrayList <Nadador> listaNadadores = new ArrayList<Nadador>();
		String sql = "select * from nadador where dni like ? order by nroClub" ;// like '?%'";
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try
		{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setString(1, Integer.toString(dni) + "%");
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
	
	public ArrayList<Nadador> buscarMuchosNadadoresPorNombre(String nombreYApellido) {
		ArrayList<Nadador> ln = new ArrayList<Nadador>();
		String sql = "Select * FROM nadador WHERE (nombre || ' ' || apellido) like ? or (apellido || ' ' || nombre) like ? order by nroClub";
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		Connection con = DataConnection.getInstancia().getConn();
		try
		{
			sentencia= con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setString(1, nombreYApellido+"%");
			sentencia.setString(2, nombreYApellido+"%");
			rs=sentencia.executeQuery();
			while(rs.next())
			{
				Nadador nad = new Nadador();
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("fechaNacimiento"));
				String stringCorto = new SimpleDateFormat("dd/MM/yyyy").format(date);
				nad.setApellido(rs.getString("apellido"));
				nad.setDni(rs.getInt("dni"));
				nad.setFechaNacimiento(stringCorto);
				nad.setNombre(rs.getString("nombre"));
				nad.setNroClub(rs.getInt("nroClub"));
				nad.setSexo(rs.getString("sexo").charAt(0));
				
				ln.add(nad);
			}
		}
		catch(SQLException | ParseException e){
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
		return ln;
	}
	
	public ArrayList<Nadador> buscarTodosNadadores()
	{
		ArrayList<Nadador> ln = new ArrayList<Nadador>();

		String sql="Select * from Nadador order by nroClub";
		Statement sentencia=null;
		ResultSet rs=null;
		try {
			sentencia=DataConnection.getInstancia().getConn().createStatement();
			rs = sentencia.executeQuery(sql);
				while (rs.next())
				{	
					Nadador nad = new Nadador();
					Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("fechaNacimiento"));
					String stringCorto = new SimpleDateFormat("dd/MM/yyyy").format(date);
					nad.setApellido(rs.getString("apellido"));
					nad.setDni(rs.getInt("dni"));
					nad.setFechaNacimiento(stringCorto);
					nad.setNombre(rs.getString("nombre"));
					nad.setNroClub(rs.getInt("nroClub"));
					nad.setSexo(rs.getString("sexo").charAt(0));
					ln.add(nad);
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
		return ln;
	}
	
	public void eliminarNadador(int dni) {
		String sql = "DELETE FROM nadador WHERE dni=?";
		PreparedStatement sentencia = null;
		Connection con = DataConnection.getInstancia().getConn();
		try
		{
			sentencia= con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, dni);
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
	
	public void modificarNadador(int dni, String apellido, String nombre, int nroClub, char sexo, String fechaNacimiento) {
		String sql = "UPDATE nadador set apellido=?, nombre=?, nroClub=?, sexo=?, fechaNacimiento=? where dni=? ";
		PreparedStatement sentencia = null;
		Connection con = DataConnection.getInstancia().getConn();
		try
		{
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento);
			String stringLargo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			sentencia= con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setString(1, apellido);
			sentencia.setString(2, nombre);
			sentencia.setInt(3, nroClub);
			sentencia.setString(4, String.valueOf(sexo));
			sentencia.setString(5, stringLargo);
			sentencia.setInt(6, dni);
			sentencia.executeUpdate();
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
	}

//Metodos Privados
	private Nadador cargarNadador(ResultSet rs)
	{
		Nadador nad = new Nadador();
		try
		{
				
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("fechaNacimiento"));
				String stringCorto = new SimpleDateFormat("dd/MM/yyyy").format(date);
				nad.setApellido(rs.getString("apellido"));
				nad.setDni(rs.getInt("dni"));
				nad.setFechaNacimiento(stringCorto);
				nad.setNombre(rs.getString("nombre"));
				nad.setNroClub(rs.getInt("nroClub"));
				nad.setSexo(rs.getString("sexo").charAt(0));
			
		}
		catch(SQLException | ParseException e)
		{
			e.printStackTrace();
		}
		
		return nad;
	}
	public Nadador buscarNadadorPorDni(int dni) {
		String sql = "select * from nadador where dni = ? order by nroClub" ;
		Nadador nadador = new Nadador();
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try
		{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1,dni);
			rs = sentencia.executeQuery();
			
			if(rs.next())
			{			
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("fechaNacimiento"));
				String stringCorto = new SimpleDateFormat("dd/MM/yyyy").format(date);
				
				nadador.setDni(rs.getInt("dni"));
				nadador.setNombre(rs.getString("nombre"));
				nadador.setApellido(rs.getString("apellido"));
				nadador.setFechaNacimiento(stringCorto);
				nadador.setNroClub(rs.getInt("nroClub"));
				nadador.setSexo(rs.getString("sexo").charAt(0));

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
		return nadador;
	}
	public boolean existeNadador(int dni) {
		String sql = "select * from nadador where dni = ? " ;
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		Connection con = DataConnection.getInstancia().getConn();
		
		try
		{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1,dni);
			rs = sentencia.executeQuery();
			
			if(rs.next())
			{		
				return true;
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
		return false;
	
		
	}
	
	
	
}
