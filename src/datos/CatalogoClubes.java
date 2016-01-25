package datos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexion.DataConnection;
import entidades.Club;

public class CatalogoClubes {
	
	private static CatalogoClubes instance = null;
	public CatalogoClubes() 
	{
	   
	}
	public static CatalogoClubes getInstance() 
	{
		if(instance == null)
		{
			instance = new CatalogoClubes();
		}
		return instance;
	}
	
	public ArrayList<Club> buscarClubes(){
		
		ArrayList<Club> clubes = new ArrayList<Club>();
		
		String sql= "SELECT * FROM club";
		Statement sentencia=null;
		ResultSet rs=null;
		try {
			sentencia=DataConnection.getInstancia().getConn().createStatement();
			rs = sentencia.executeQuery(sql);
			while(rs.next())
				{
					Club club = new Club();
					club.setAbreviacion(rs.getString("abreviacion"));
					club.setLocalidad(rs.getString("localidad"));
					club.setNombre(rs.getString("nombre"));
					club.setNroClub(rs.getInt("nroClub"));
					clubes.add(club);
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
		
		return clubes;
	}
	
public Club buscarClubPorNumeroClub(int nroClub)
{
		
		Club club = new Club();
		
		String sql= "SELECT * FROM club where nroClub = ?";
		PreparedStatement sentencia=null;
		ResultSet rs=null;
		try 
		{
			sentencia=DataConnection.getInstancia().getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.setInt(1, nroClub);
			rs = sentencia.executeQuery();
			while(rs.next())
				{
					club.setAbreviacion(rs.getString("abreviacion"));
					club.setLocalidad(rs.getString("localidad"));
					club.setNombre(rs.getString("nombre"));
					club.setNroClub(rs.getInt("nroClub"));
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
		
		return club;
	}
	
}
