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
import entidades.Nadador;
import entidades.Programa;

public class CatalogoPrograma 
{
	private static CatalogoPrograma instance = null;
	public CatalogoPrograma() 
	{
   
	}
	public static CatalogoPrograma getInstance()
	{
		if(instance == null) 
		{
			instance = new CatalogoPrograma();
		}
		return instance;
	}
	public void cargarPrograma(int nroPrograma, String descripcion) 
	{
		String 	sql = "INSERT INTO Programa (nroPrograma, descripcion)";
		PreparedStatement sentencia=null;
		Connection con = DataConnection.getInstancia().getConn();
			
		try
		{
			sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroPrograma);
			sentencia.setString(2, descripcion);
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
	
	
	public ArrayList<Programa> buscarTodosProgramas()
	{
		ArrayList<Programa> lp = new ArrayList<Programa>();

		String sql="Select * from Programa";
		Statement sentencia=null;
		ResultSet rs=null;
		try
		{
			sentencia=DataConnection.getInstancia().getConn().createStatement();
			rs = sentencia.executeQuery(sql);
			while (rs.next())
			{	
				Programa pro = new Programa();
				pro.setNroPrograma(rs.getInt("nroPrograma"));
				pro.setDescripcion(rs.getString("descripcion"));
				lp.add(pro);
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
		return lp;
	}
	

}
