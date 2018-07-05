package jcms.too.avaliacaofisica.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import jcms.too.avaliacaofisica.arquivos.IndiceUsuario;
import jcms.too.avaliacaofisica.controller.ExportarController;
import jcms.too.avaliacaofisica.dados.ManipuladorDeDados;
import jcms.too.avaliacaofisica.utilitarios.Constantes;
import jcms.too.avaliacaofisica.utilitarios.Utilitarios;

/**
 * Classe usada para criar a interface de exportação de gráficos para pdf.
 * 
 * @author José do Carmo de Melo Silva.
 *	@since 0.7
 */
public class IGExportarPDF extends JDialog {

	private static final long serialVersionUID = 3454352336066969601L;
	private final JPanel contentPanel = new JPanel();
	private JLabel label;
	private JComboBox<Object> tiposDeGrafico;
	private JComboBox<Object> boxUsuarios;
	private JDateChooser dateInicial;
	private JDateChooser dateFinal;

	/**
	 * Cria uma instância da classe {@link IGExportarPDF}.
	 * 
	 * @param manipulador : referência {@link ManipuladorDeDados} usada para executar operações da interface.
	 */
	public IGExportarPDF(ManipuladorDeDados manipulador) {
		setBounds(100, 100, 377, 447);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblExportarParaPdf = new JLabel("Exportar para PDF");
		lblExportarParaPdf.setFont(new Font("Verdana", Font.BOLD, 16));
		lblExportarParaPdf.setBounds(96, 11, 196, 21);
		contentPanel.add(lblExportarParaPdf);
		
		boxUsuarios = new JComboBox<Object>();
		boxUsuarios.setBounds(30, 159, 298, 20);
		contentPanel.add(boxUsuarios);
		
		JLabel lblUsurio = new JLabel("Usu\u00E1rio");
		lblUsurio.setBounds(149, 134, 78, 14);
		contentPanel.add(lblUsurio);
		
		JLabel lblRelatrio = new JLabel("Relat\u00F3rio");
		lblRelatrio.setBounds(149, 207, 78, 14);
		contentPanel.add(lblRelatrio);
		
		tiposDeGrafico = new JComboBox<Object>();
		tiposDeGrafico.setBounds(30, 232, 298, 20);
		contentPanel.add(tiposDeGrafico);
		
		label = new JLabel("");
		label.setIcon(Utilitarios.getIconAjustado("pdf.png", 70, 70));
		label.setBounds(155, 267, 83, 81);
		contentPanel.add(label);
		
		dateInicial = new JDateChooser();
		dateInicial.setBounds(30, 90, 132, 21);
		contentPanel.add(dateInicial);
		
		dateFinal = new JDateChooser();
		dateFinal.setBounds(196, 90, 132, 20);
		contentPanel.add(dateFinal);
		
		JLabel lblPerodo = new JLabel("Per\u00EDodo");
		lblPerodo.setBounds(160, 55, 78, 24);
		contentPanel.add(lblPerodo);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(BorderFactory.createTitledBorder(""));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton btnExportar = new JButton("Exportar");
				btnExportar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String tipoGrafico = "";
						String tipoGraficoSelecionado = (String) tiposDeGrafico.getSelectedItem();
						if(tipoGraficoSelecionado.compareTo(Constantes.VALOR_TODOS_COMBO_BOX) == 0){
							tipoGrafico = tipoGraficoSelecionado;
						}else if(tipoGraficoSelecionado.compareTo(Constantes.TIPO_DE_GRAFICO_1) == 0){
							tipoGrafico = Constantes.TIPO_DE_GRAFICO_1_POR_TIPO;
						}
						else if(tipoGraficoSelecionado.compareTo(Constantes.TIPO_DE_GRAFICO_2) == 0){
							tipoGrafico = Constantes.TIPO_DE_GRAFICO_2_POR_TIPO;
						}else{
							tipoGrafico = Constantes.TIPO_DE_GRAFICO_3;
						}
						
						ExportarController.acaoBotao(manipulador, dateInicial.getDate(), dateFinal.getDate(), 
								((IndiceUsuario)boxUsuarios.getSelectedItem()),tipoGrafico, contentPanel);
					}
				});
				buttonPane.add(btnExportar);
				getRootPane().setDefaultButton(btnExportar);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancelar");
				buttonPane.add(cancelButton);
			}
		}
		ExportarController.inicializarCampos(boxUsuarios, tiposDeGrafico, manipulador);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}
}
