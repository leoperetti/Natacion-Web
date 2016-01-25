package util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import datos.CatalogoNadadores;
import entidades.Club;
import entidades.Nadador;
import entidades.NadadorCarreraIndividual;
import entidades.NadadorCarreraPosta;
import entidades.Torneo;
import negocio.ControladorNatacion;

public class UtilidadesEscritorio
{

	private static ControladorNatacion cc = new ControladorNatacion();
	
	public static void validarValoresNumericos(JTextField texto, JLabel lblError)
	{
		texto.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyReleased(KeyEvent arg0) 
			{
				if (!texto.getText().isEmpty())
				{
					if (!esNumerico(texto.getText()))
					{
						texto.setText("");
						lblError.setText("Este campo sólo acepta números");
					}
					else
					{
						lblError.setText("");
					}
				}
			}
		});
	}
	 
	public static boolean esNumerico(String str)
	{
	    try
	    {
	    	Integer.parseInt(str);
	    }
	    catch(NumberFormatException nfe)
	    {
		     return false;
	    }
	    return true;
	}
	
	public static MaskFormatter generarMascara(String patronDeMascara)
	{
		MaskFormatter mask = null;
        try 
        {
            mask = new MaskFormatter(patronDeMascara);//the # is for numeric values
            mask.setPlaceholderCharacter(' ');
        } 
        catch (ParseException e) 
        {
            e.printStackTrace();
        }
        return mask;
	}
	
	public static <T> DefaultComboBoxModel<T> generarModeloComboBox(ArrayList<T> lista)
	{
		DefaultComboBoxModel<T> modeloCombo = new DefaultComboBoxModel<T>();
		
		for(T clu : lista)
		{
			modeloCombo.addElement(clu);
		}
		return modeloCombo;
		
	}
	
	public static <T> DefaultTableModel crearModeloTabla(ArrayList<T> lista) 
	{
		DefaultTableModel modeloTabla = new DefaultTableModel()
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		//Primer devolución
		if(!lista.isEmpty())
		{
			if(lista.get(0) instanceof Nadador)
			{
	
				ArrayList<Nadador> listaNadadores = (ArrayList<Nadador>)lista;
				Object[] identifiers = {"Dni", "Nombre", "Apellido", "Edad", "Club"};
				modeloTabla.setColumnIdentifiers(identifiers);
				
				for(Nadador nad : listaNadadores)
				{
					Club club  = cc.buscarClubPorNumeroClub(nad.getNroClub());
					int edad = calcularEdad(nad.getFechaNacimiento());
					
					Object[] o = new Object[5];
					o[0] = nad.getDni();
					o[1] = nad.getNombre();
					o[2] = nad.getApellido();
					o[3] = edad;
					o[4] = club.getAbreviacion();
					modeloTabla.addRow(o);
				}
			}
			if(lista.get(0) instanceof NadadorCarreraIndividual)
			{
				ArrayList<NadadorCarreraIndividual> listaCarrerasIndividuales = (ArrayList<NadadorCarreraIndividual>)lista;
				Object[] identifiers = {"Dni", "Nombre", "Apellido","Edad", "Club"};
				modeloTabla.setColumnIdentifiers(identifiers);
				for(NadadorCarreraIndividual nad : listaCarrerasIndividuales)
				{
					int dniNadador = nad.getDniNadador();
					Nadador nadadorActual = cc.buscarNadadorPorDni(dniNadador);
					int edad = calcularEdad(nadadorActual.getFechaNacimiento());
					Club club  = cc.buscarClubPorNumeroClub(nadadorActual.getNroClub());
					Object[] o = new Object[5];
					o[0] = nadadorActual.getDni();
					o[1] = nadadorActual.getNombre();
					o[2] = nadadorActual.getApellido();
					o[3] = edad;
					o[4] = club.getAbreviacion();
					modeloTabla.addRow(o);
				}
			}
			if(lista.get(0) instanceof String)
			{
				ArrayList<String> listaDescalificaciones = (ArrayList<String>)lista;
				Object[] identifiers = {"Descalificación"};
				modeloTabla.setColumnIdentifiers(identifiers);
				
				for(String motivo : listaDescalificaciones)
				{
					Object[] o = new Object[1];
					o[0] = motivo;
					modeloTabla.addRow(o);
				}

			}
			if(lista.get(0) instanceof NadadorCarreraPosta)
			{
				ArrayList<NadadorCarreraPosta> listaCarrerasPosta = (ArrayList<NadadorCarreraPosta>)lista;
				Object[] identifiers = {"Nadador1", "Nadador2", "Nadador3", "Nadador4", "Club"};
				modeloTabla.setColumnIdentifiers(identifiers);
				for(NadadorCarreraPosta nad : listaCarrerasPosta)
				{
					int dniNadador1 = nad.getDniNadador1();
					Nadador nadadorActual = cc.buscarNadadorPorDni(dniNadador1);
					Nadador nad1= CatalogoNadadores.getInstance().buscarNadadorPorDni(dniNadador1);
					Nadador nad2= CatalogoNadadores.getInstance().buscarNadadorPorDni(nad.getDniNadador2());
					Nadador nad3= CatalogoNadadores.getInstance().buscarNadadorPorDni(nad.getDniNadador3());
					Nadador nad4= CatalogoNadadores.getInstance().buscarNadadorPorDni(nad.getDniNadador4());

					Club club  = cc.buscarClubPorNumeroClub(nadadorActual.getNroClub());
					Object[] o = new Object[5];
					o[0] = nad1;
					o[1] = nad2;
					o[2] = nad3;
					o[3] = nad4;
					o[4] = club.getAbreviacion();
					modeloTabla.addRow(o);
				}
			}
			if(lista.get(0) instanceof Torneo)
			{
				Object[] identifiers = {"Nro Torneo", "Fecha", "Club"};
				ArrayList<Torneo> torneos = (ArrayList<Torneo>)lista;
				modeloTabla.setColumnIdentifiers(identifiers);
				for(Torneo tor : torneos)
				{
					Club club  = cc.buscarClubPorNumeroClub(tor.getNroClub());
					Object[] o = new Object[3];
					o[0] = tor.getNroTorneo();
					o[1] = tor.getFecha();
					o[2] = club.getAbreviacion();
					modeloTabla.addRow(o);
				}
			}

		}
		return modeloTabla;
	}
	
	
	public static void filtrarTabla(JTextField txtFiltro, JTable tablaAFiltrar, DefaultTableModel modeloFiltradoNumero, DefaultTableModel modeloFiltradoLetra, DefaultTableModel modeloCompleto)
	{
		if (!txtFiltro.getText().isEmpty())
		{
			if (esNumerico(txtFiltro.getText()))
			{
				tablaAFiltrar.setModel(modeloFiltradoNumero);
			}
			else if (Pattern.matches("[a-zA-Z]+", txtFiltro.getText()))
			{
				tablaAFiltrar.setModel(modeloFiltradoLetra);
			}
			else if (txtFiltro.getText().charAt(0) == ' ')
			{
				txtFiltro.setText("");
			}
		}
		else
		{
			tablaAFiltrar.setModel(modeloCompleto);
		}
	}
	
	public static Integer calcularEdad(String fecha){
	    Date fechaNac=null;
	        try {
	            /*Se puede cambiar la mascara por el formato de la fecha
	            que se quiera recibir, por ejemplo año mes día "yyyy-MM-dd"
	            en este caso es día mes año*/
	            fechaNac = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
	        } catch (Exception ex) {
	            System.out.println("Error:"+ex);
	        }
	        Calendar fechaNacimiento = Calendar.getInstance();
	        //Se crea un objeto con la fecha actual
	        Calendar fechaActual = Calendar.getInstance();
	        //Se asigna la fecha recibida a la fecha de nacimiento.
	        fechaNacimiento.setTime(fechaNac);
	        //Se restan la fecha actual y la fecha de nacimiento
	        int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
	        int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
	        int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
	        //Se ajusta el año dependiendo el mes y el día
	        if(mes<0 || (mes==0 && dia<0)){
	            año--;
	        }
	        //Regresa la edad en base a la fecha de nacimiento
	        return año;
	    }
	
	
	}

