package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexion.DataConnection;

public class CatalogoDescalificaciones {
	
	private static CatalogoDescalificaciones instance = null;
	public CatalogoDescalificaciones() 
	{
	   
	}
	public static CatalogoDescalificaciones getInstance() 
	{
		if(instance == null)
		{
			instance = new CatalogoDescalificaciones();
		}
		return instance;
	}

	public int getNroConDesc(String motivoDescalificacion)
	{
		String sql="select nroDescalificacion from descalificacion where descripcion like ? ";
		PreparedStatement sentencia = null;
		Connection con = DataConnection.getInstancia().getConn();
		ResultSet rs= null;
		int num=0;
		try
		{
			sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setString(1, motivoDescalificacion);
			rs=sentencia.executeQuery();
			if(rs.next())
			{
				num=rs.getInt("nroDescalificacion");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return num;
	}
	
	public void cargarDescalificacion(String motivoDescalificacion)
	{
		String sql="insert into descalificacion values (?,?)";
		PreparedStatement sentencia = null;
		Connection con = DataConnection.getInstancia().getConn();
		try
		{
			sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, getMaxNroDescalificacion());
			sentencia.setString(2, motivoDescalificacion);
			sentencia.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}


	public ArrayList<String> getDescalificaciones()
	{
		String sql="Select descripcion from descalificacion";
		Statement sentencia= null;
		ResultSet rs=null;
		ArrayList<String> descalif = new ArrayList<String>();
		try
		{
			sentencia=DataConnection.getInstancia().getConn().createStatement();
			rs = sentencia.executeQuery(sql);
			while(rs.next())
			{
				descalif.add(rs.getString("descripcion"));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return descalif;
	}
	
	public void modificarDescalificacion(int nroDesc, String motivoDesc)
	{
		String sql="update descalificacion set descripcion=? where nroDescalificacion=?";
		PreparedStatement sentencia=null;
		Connection con= DataConnection.getInstancia().getConn();
		try
		{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroDesc);
			sentencia.setString(2, motivoDesc);
			sentencia.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void eliminarDescalificacion(int nroDesc)
	{
		String sql="delete from descalificacion where nroDescalificacion=?";
		PreparedStatement sentencia=null;
		Connection con= DataConnection.getInstancia().getConn();
		try
		{
			sentencia=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroDesc);
			sentencia.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	private int getMaxNroDescalificacion() {
		String sql="select max(nroDescalificacion) from descalificacion";
		Statement sentencia = null;
		ResultSet rs = null;
		int max=0;
		try
		{
			sentencia=DataConnection.getInstancia().getConn().createStatement();
			rs = sentencia.executeQuery(sql);
			if(rs.next())
			{
				 max = rs.getInt("max(nroDescalificacion)");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return max;
		}
	
	
}
