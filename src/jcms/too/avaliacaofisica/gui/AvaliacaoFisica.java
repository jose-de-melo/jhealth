package jcms.too.avaliacaofisica.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import jcms.too.avaliacaofisica.dados.ManipuladorDeDados;
import jcms.too.avaliacaofisica.out.Mensagem;
import jcms.too.avaliacaofisica.utilitarios.Chooser;
import jcms.too.avaliacaofisica.utilitarios.Constantes;
import jcms.too.avaliacaofisica.utilitarios.Recurso;
import jcms.too.avaliacaofisica.utilitarios.RedimensionadorDeImagem;
import jcms.too.avaliacaofisica.utilitarios.Utilitarios;

/**
 * Classe usada para gerar a tela principal da aplicação e criar o objeto {@link ManipuladorDeDados} que será usado
 * durante toda a execução da aplicação.
 * 
 * @author José do Carmo de Melo Silva
 * 
 */
public class AvaliacaoFisica extends JFrame {

	public static ImageIcon icone;
	private static final long serialVersionUID = -2607964007193973217L;
	
	private ManipuladorDeDados manipulador = new ManipuladorDeDados();

	private JPanel contentPane;
	JButton consultar, relatorio;
	private JButton graficos;
	private JButton btnExportar;

	/**
	 * Cria uma nova instância <code>AvaliacaoFisica</code>.
	 */
	public AvaliacaoFisica() {
		setTitle("Avaliação Física");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 340, 471);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		icone = new ImageIcon(Recurso.obterLocalizacao(Constantes.CAMINHO_IMAGENS + "icon_programa2.png"));
		RedimensionadorDeImagem.redimensionar(icone, 18, 18);
		setIconImage(icone.getImage());

		JButton importar = new JButton("<html>Importar<br>\r\nDados</html>");
		importar.setIcon(Utilitarios.getIconAjustado("import.png", 25,25));
		importar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> nomeArquivos = Chooser.selecionarArquivos("Selecione o arquivo que deseja importar", contentPane.getParent(), new FileNameExtensionFilter("Arquivos TXT (*.txt)", "txt"));

				
				if(nomeArquivos != null){
					int verificador = manipulador.importarEGerar(nomeArquivos);
					if(verificador != 1 ){
						if(!manipulador.haDados())
							Utilitarios.alterarEnabledBotoes(false,relatorio, consultar, graficos, btnExportar);
							Mensagem.msgErro("Chegou 3", "");
					}
					else{
						Utilitarios.alterarEnabledBotoes(true,relatorio, consultar, graficos, btnExportar);
					}
				}
			}
		});
		importar.setBounds(39, 229, 131, 47);
		contentPane.add(importar);

		consultar = new JButton("<html>Consultar<br>Usuário</html>\r\n");
		consultar.setIcon(Utilitarios.getIconAjustado("search.png", 25, 25));
		consultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new IGConsultarUsuarioExercicio(manipulador);
			}
		});
		consultar.setEnabled(false);
		consultar.setBounds(180, 229, 131, 47);
		contentPane.add(consultar);

		relatorio = new JButton("Relat\u00F3rio");
		relatorio.setIcon(Utilitarios.getIconAjustado("relatorio.png", 35, 35));
		relatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new IGRelatorio(manipulador);
			}
		});
		relatorio.setEnabled(false);
		relatorio.setBounds(39, 287, 131, 47);
		contentPane.add(relatorio);

		graficos = new JButton("Gr\u00E1ficos\r\n");
		graficos.setEnabled(false);
		graficos.setIcon(Utilitarios.getIconAjustado("graf.png", 25, 25));
		graficos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new IGGraficos(manipulador);
			}
		});
		graficos.setBounds(180, 287, 131, 47);
		contentPane.add(graficos);

		JLabel label = new JLabel("", Utilitarios.getIconAjustado("fisic.png", 250, 250), SwingConstants.CENTER);
		label.setBounds(39, 11, 272, 207);
		label.setHorizontalTextPosition( SwingConstants.CENTER);
		label.setVerticalTextPosition( SwingConstants.BOTTOM );

		contentPane.add(label);
		
		JButton sair = new JButton("Sair\r\n");
		sair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		sair.setBounds(180, 345, 131, 47);
		contentPane.add(sair);
		
		btnExportar = new JButton("<html><center>Exportar<br>para PDF</center></html>");
		btnExportar.setEnabled(false);
		btnExportar.setIcon(Utilitarios.getIconAjustado("pdf.png", 30, 30));
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new IGExportarPDF(manipulador);
			}
		});
		btnExportar.setBounds(39, 345, 131, 47);
		contentPane.add(btnExportar);
		
		if(manipulador.haDados()){
			Utilitarios.alterarEnabledBotoes(true, consultar, relatorio, graficos, btnExportar);
		}
		setResizable(false);
		setVisible(true);
		
	}
}
