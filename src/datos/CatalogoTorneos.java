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
import entidades.Torneo;

public class CatalogoTorneos {
	
	private static CatalogoTorneos instance = null;
	public CatalogoTorneos() 
	{
	   
	}
	public static CatalogoTorneos getInstance() 
	{
		if(instance == null)
		{
			instance = new CatalogoTorneos();
		}
		return instance;
	}

	public void eliminarTorneo(int nroTorneo)
	{
		String sql="delete from torneo where nroTorneo=?";
		PreparedStatement sentencia= null;
		Connection con= DataConnection.getInstancia().getConn();
		try
		{
			sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroTorneo);
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
	
	public void registrarTorneo(String fecha, int nroTorneo, int nroPrograma, int nroClub)
	{
		String sql="insert into torneo values (?,?,?,?);";
		PreparedStatement sentencia=null;
		Connection con= DataConnection.getInstancia().getConn();
		try{
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
			String fechaLarga = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroTorneo);
			sentencia.setInt(3, nroPrograma);
			sentencia.setString(2, fechaLarga);
			sentencia.setInt(4, nroClub);
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
	
	public ArrayList<Torneo> buscarTorneos()
	{
		ArrayList<Torneo> lt = new ArrayList<Torneo>();
		String sql="Select * from torneo";
		Statement sentencia = null;
		ResultSet rs = null;
		Connection con = DataConnection.getInstancia().getConn();
		try{
			sentencia = con.createStatement();
			rs=sentencia.executeQuery(sql);
			while(rs.next())
			{
				Torneo t = new Torneo();
				Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("fechaTorneo"));
				String stringCorto = new SimpleDateFormat("dd/MM/yyyy").format(date);
				t.setNroTorneo(rs.getInt("nroTorneo"));
				t.setNroClub(rs.getInt("nroClub"));
				t.setNroPrograma(rs.getInt("nroPrograma"));
				t.setFecha(stringCorto);
				lt.add(t);
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
		return lt;
	}
	
	public int ultimoNroTorneo()
	{
		String sql="select max(nroTorneo) from torneo";
		Statement sentencia = null;
		ResultSet rs = null;
		int max=0;
		try
		{
			sentencia= DataConnection.getInstancia().getConn().createStatement();
			rs=sentencia.executeQuery(sql);
			if(rs.next())
			{
				max=rs.getInt("max(nroTorneo)");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return max;
	}
}
