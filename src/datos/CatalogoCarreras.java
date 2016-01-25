package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexion.DataConnection;
import entidades.Carrera;

public class CatalogoCarreras {
	
	private static CatalogoCarreras instance = null;
	public CatalogoCarreras() 
	{
	   
	}
	public static CatalogoCarreras getInstance() 
	{
		if(instance == null)
		{
			instance = new CatalogoCarreras();
		}
		return instance;
	}
	
	@SuppressWarnings("null")
	public void registrarCarrera(int nroCarrera,int tipoCarrera,int metros,int nroPrograma,String sexo, String estilo)
	{
	
		String sql="Insert into carrera values (?,?,?,?,?,?,?,?)";
		PreparedStatement sentencia = null;
		try {
		 sentencia=DataConnection.getInstancia().getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		 sentencia.setInt(1, nroCarrera);
		 sentencia.setInt(2, nroPrograma);
		 sentencia.setInt(3, tipoCarrera);
		 sentencia.setInt(4, metros);
		 sentencia.setString(5, estilo);
		 sentencia.setString(6, String.valueOf(sexo));
		 sentencia.setInt(7, (Integer)null);
		 sentencia.setString(8, "");
		 sentencia.executeUpdate();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
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
	
	public ArrayList<Carrera> buscarCarrerasPrograma(int nroPrograma)
	{
		ArrayList<Carrera> lc = new ArrayList<Carrera>();
		String sql="select * from carrera where nroPrograma=? order by nroCarrera";
		PreparedStatement sentencia = null;
		ResultSet rs= null;
		Connection con = DataConnection.getInstancia().getConn();
		try
		{
			sentencia= con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroPrograma);
			rs=sentencia.executeQuery();
			while(rs.next())
			{
				Carrera car = new Carrera();
				car.setEstilo(rs.getString("estilo"));
				car.setMetros(rs.getInt("metros"));
				car.setNroCarrera(rs.getInt("nroCarrera"));
				car.setNroPrograma(rs.getInt("nroPrograma"));
				car.setSexo(rs.getString("sexo").charAt(0));
				car.setTipoCarrera(rs.getInt("edadCarrera"));
				car.setCategoria(rs.getString("categoria"));
				car.setEdad2(rs.getInt("edad2"));
				lc.add(car);
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
		return lc;
	}
	public ArrayList<Carrera> buscarCarrerasProgramaNoCargadas(int nroPrograma, int nroTorneo)
	{
		ArrayList<Carrera> lc = new ArrayList<Carrera>();
		String sql="select * from carrera where nroPrograma=? "
				+ " and nroCarrera not in "
				+ "(select nroCarrera from serie where nroPrograma=? and nroTorneo=?) and nroCarrera <= 40 order by nroCarrera";
		PreparedStatement sentencia = null;
		ResultSet rs= null;
		Connection con = DataConnection.getInstancia().getConn();
		try
		{
			sentencia= con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroPrograma);
			sentencia.setInt(2, nroPrograma);
			sentencia.setInt(3, nroTorneo);
			rs=sentencia.executeQuery();
			while(rs.next())
			{
				Carrera car = new Carrera();
				car.setEstilo(rs.getString("estilo"));
				car.setMetros(rs.getInt("metros"));
				car.setNroCarrera(rs.getInt("nroCarrera"));
				car.setNroPrograma(rs.getInt("nroPrograma"));
				car.setSexo(rs.getString("sexo").charAt(0));
				car.setTipoCarrera(rs.getInt("edadCarrera"));
				lc.add(car);
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
		return lc;
	}
	//Este busca las postas, el de arriba las carreras normales.
	public ArrayList<Carrera> buscarPostasProgramaNoCargadas(int nroPrograma, int nroTorneo)
	{
		ArrayList<Carrera> lc = new ArrayList<Carrera>();
		String sql="select * from carrera where nroPrograma=? "
				+ " and nroCarrera not in "
				+ "(select nroCarrera from serie where nroPrograma=? and nroTorneo=?) and nroCarrera > 40 order by nroCarrera" ;
		PreparedStatement sentencia = null;
		ResultSet rs= null;
		Connection con = DataConnection.getInstancia().getConn();
		try
		{
			sentencia= con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroPrograma);
			sentencia.setInt(2, nroPrograma);
			sentencia.setInt(3, nroTorneo);
			rs=sentencia.executeQuery();
			while(rs.next())
			{
				Carrera car = new Carrera();
				car.setEstilo(rs.getString("estilo"));
				car.setMetros(rs.getInt("metros"));
				car.setNroCarrera(rs.getInt("nroCarrera"));
				car.setNroPrograma(rs.getInt("nroPrograma"));
				car.setSexo(rs.getString("sexo").charAt(0));
				car.setTipoCarrera(rs.getInt("edadCarrera"));
				car.setCategoria(rs.getString("categoria"));
				car.setEdad2(rs.getInt("edad2"));
				
				lc.add(car);
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
		return lc;
	}
	
	//TODO Hacer para programa
	public int buscarUltimoNroCarrera(int nroPrograma) 
	{
		int nroCarreraMaximo = 0;
		String sql = "SELECT MAX(nroCarrera) FROM Carrera where nroPrograma=? "
				+ "group by nroPrograma;";
		PreparedStatement sentencia = null;
		ResultSet rs = null;
		try
		{
			sentencia = DataConnection.getInstancia().getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					sentencia.setInt(1, nroPrograma);
			rs = sentencia.executeQuery();
			if(rs.next())
			{
				nroCarreraMaximo = rs.getInt("MAX(nroCarrera)");
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
		
		return nroCarreraMaximo;
	}
}

