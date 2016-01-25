package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexion.DataConnection;
import entidades.Serie;

public class CatalogoSeries {

	private static CatalogoSeries instance = null;
	  public CatalogoSeries() {
	   
	  }
	   public static CatalogoSeries getInstance() {
	      if(instance == null) {
	         instance = new CatalogoSeries();
	      }
	      return instance;
	   }
	
	   public ArrayList<Serie> buscarSeriesPorCarrera(int nroCarrera, int nroPrograma, int nroTorneo) 
		{
			ArrayList<Serie> listaSeries = new ArrayList<Serie>();
			String sql = "SELECT * FROM serie where nroCarrera = ? and nroPrograma=? and nroTorneo=?";
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
				
					while(rs.next())
					{
						Serie s = new Serie();
						s.setNroSerie(rs.getInt("nroSerie"));
						s.setNroCarrera(rs.getInt("nroCarrera"));
						s.setNroPrograma(rs.getInt("nroPrograma"));
						s.setNroTorneo(rs.getInt("nroTorneo"));
						listaSeries.add(s);
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
			
			return listaSeries;	
		}
		
		public void insertarSerie(int nroSerie, int nroCarrera,int nroPrograma, int nroTorneo) 
		{
			PreparedStatement sentencia = null;
			Connection con = DataConnection.getInstancia().getConn();
			String sql = "INSERT INTO serie  VALUES(?,?,?,?)";
			
			try{
				sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				sentencia.setInt(1, nroSerie);
				sentencia.setInt(2, nroCarrera);
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
		public int maximaSeriePorCarrera(int nroCarrera, int nroPrograma, int nroTorneo)
		{
			String sql="select max(nroSerie) from serie where nroCarrera=? and nroPrograma=? and nroTorneo=? group by nroPrograma,nroCarrera";
			PreparedStatement sentencia = null;
			Connection con = DataConnection.getInstancia().getConn();
			ResultSet rs = null;
			int maxNroSerie=0;
			try{
				sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				sentencia.setInt(1, nroCarrera);
				sentencia.setInt(2, nroPrograma);
				sentencia.setInt(3, nroTorneo);
				rs=sentencia.executeQuery();
				if(rs.next())
				{
					maxNroSerie=rs.getInt("max(nroSerie)");
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
			return maxNroSerie;
		}
}