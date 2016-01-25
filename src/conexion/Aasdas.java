package conexion;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import negocio.ControladorNatacion;
import util.UtilidadesEscritorio;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Aasdas extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Aasdas frame = new Aasdas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Aasdas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JCheckBox checkBox = new JCheckBox("New check box");
		checkBox.setBounds(53, 52, 104, 50);
		contentPane.add(checkBox);
		
		JCheckBox checkBox_1 = new JCheckBox("New check box");
		checkBox_1.setBounds(53, 108, 104, 50);
		contentPane.add(checkBox_1);
		
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(UtilidadesEscritorio.generarModeloComboBox(ControladorNatacion.getInstance().getDescalificaciones()));
		comboBox.setBounds(224, 52, 200, 50);
		contentPane.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(224, 124, 200, 50);
		comboBox_1.setModel(UtilidadesEscritorio.generarModeloComboBox(ControladorNatacion.getInstance().getDescalificaciones()));
		contentPane.add(comboBox_1);
		
		
		String[] asd = new String[2];
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(checkBox.isSelected())
				{
					asd[0]=(String)comboBox.getSelectedItem();
				}
				if(checkBox_1.isSelected())
				{
					JOptionPane.showMessageDialog(contentPane, (String)comboBox_1.getSelectedItem());
				}
			}
		});
		btnConfirmar.setBounds(236, 211, 89, 23);
		contentPane.add(btnConfirmar);
	}
}
