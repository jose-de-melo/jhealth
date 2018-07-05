package jcms.too.avaliacaofisica.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jcms.too.avaliacaofisica.arquivos.IndiceUsuario;
import jcms.too.avaliacaofisica.dados.ManipuladorDeDados;
import jcms.too.avaliacaofisica.graficos.GerarGraficos;
import jcms.too.avaliacaofisica.modelo.Data;
import jcms.too.avaliacaofisica.modelo.Exercicio;
import jcms.too.avaliacaofisica.modelo.Usuario;
import jcms.too.avaliacaofisica.out.Mensagem;
import jcms.too.avaliacaofisica.pdf.GerarPDF;
import jcms.too.avaliacaofisica.utilitarios.Constantes;
import jcms.too.avaliacaofisica.utilitarios.Utilitarios;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import com.toedter.calendar.JDateChooser;

/**
 * Classe usada para criar a interface usada para exibir os gráficos gerados pela aplicação.
 * 
 * @author José do Carmo de Melo Silva
 * @see GerarGraficos
 * @since 0.3
 */
public class IGGraficos extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox<Object> boxUsuarios;
	private JPanel painelGrafico;
	private Boolean mostrarTelaAtencaoNovamente = true, realizarExportacao = false;


	private JPanel panel_1;
	private JDateChooser dataInicial;
	private JDateChooser dataFinal;
	private JPanel panel_2;
	private JButton btnSelecionarUsuario;
	private JButton btnDuracao;
	private JButton btnCaloriasPerdidas;
	private JButton btnDistancia;
	private JButton btnVelocidadeMedia;
	private JButton btnRitmoMedio;
	private JButton gerarPDF;
	private JPanel panel_4;
	private JButton botaoPeriodo;
	private ArrayList<Exercicio> exercicios;
	private ArrayList<Exercicio> exerciciosDoTipo;
	private Usuario usuario;
	private JButton btnPassos;

	private Data dtInicial, dtFinal;
	private JPanel painelTipoExercicio;
	private JComboBox<Object> comboBoxTipoExercicios;
	private JButton btnTipoExercicio;
	private String tipoExercicio = "";
	private JPanel panelTiposDeGraficos;
	private JComboBox<Object> comboBox_1;
	private JButton btnSelecionarTipoGrafico;
	private JButton btnDistanciaLinhas;
	private JButton btnCaloriasLinhas;
	private JButton btnPassosLinhas;
	private JButton btnPassosColunas2;
	private JButton btnDistanciaMediaColunas2;
	private JButton btnDistanciaColunas2;
	private JButton btnCaloriasColunas2;
	private JButton btnMediaCaloriasColunas2;
	private List<JFreeChart> graficosGerados;
	private String tipoDeGraficoSelecionado = "";

	/**
	 * Cria uma instância {@link IGGraficos}.
	 * 
	 * @param manipuladorDeDados - referência {@link ManipuladorDeDados} usada para executar as operações da interface.
	 */
	public IGGraficos(ManipuladorDeDados manipuladorDeDados) {
		setBounds(100, 100, 935, 720);
		setTitle(Constantes.NOME_PROGRAMA + " : Gráficos");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		setModal(true);
		setIconImage(AvaliacaoFisica.icone.getImage());

		painelGrafico = new JPanel();
		painelGrafico.setBounds(27, 266, 882, 372);
		painelGrafico.setBorder(BorderFactory.createTitledBorder("Gráfico"));
		contentPanel.add(painelGrafico);
		painelGrafico.setLayout(new BorderLayout(0, 0));

		JLabel lblGrficosDeDesempenho = new JLabel("Gr\u00E1ficos de Desempenho");
		lblGrficosDeDesempenho.setBounds(384, 11, 152, 14);
		contentPanel.add(lblGrficosDeDesempenho);

		panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createTitledBorder("2 - Selecionar o período"));
		panel_1.setBounds(478, 36, 431, 63);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);

		dataInicial = new JDateChooser();
		dataInicial.setBounds(10, 32, 121, 20);
		panel_1.add(dataInicial);

		dataFinal = new JDateChooser();
		dataFinal.setBounds(160, 32, 121, 20);
		panel_1.add(dataFinal);

		JLabel lblDataInicial = new JLabel("Data Inicial");
		lblDataInicial.setBounds(40, 17, 73, 14);
		panel_1.add(lblDataInicial);

		JLabel lblDataFinal = new JLabel("Data Final");
		lblDataFinal.setBounds(193, 17, 70, 14);
		panel_1.add(lblDataFinal);

		botaoPeriodo = new JButton("Selecionar");
		botaoPeriodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataInicial.getDate() != null && dataFinal.getDate() != null){
					dtInicial = Data.transformaDateEmData(dataInicial.getDate());
					dtFinal = Data.transformaDateEmData(dataFinal.getDate());

					if(dtInicial.compareTo(dtFinal) == 1 || dtInicial.compareTo(dtFinal) == 0){
						Mensagem.msgErro("A data inicial tem que ser anterior a final!", Constantes.NOME_PROGRAMA + " : Gráficos");
					}else{
						exercicios = manipuladorDeDados.lerExerciciosUsuarioDoPeriodo(usuario, dtInicial, dtFinal);

						if(exercicios.isEmpty()) {
							Utilitarios.alterarEnabledBotoes(false, btnSelecionarTipoGrafico);
							comboBoxTipoExercicios.removeAllItems();
							Mensagem.msgErro("O usuário não realizou nenhum exercício no período!", Constantes.NOME_PROGRAMA + " : Gráficos");
						}
						else {
							Utilitarios.alterarEnabledBotoes(true, btnSelecionarTipoGrafico);
							comboBoxTipoExercicios.removeAllItems();
							comboBoxTipoExercicios.addItem(Constantes.VALOR_TODOS_COMBO_BOX);
							Utilitarios.carregarComboBox(comboBoxTipoExercicios, ManipuladorDeDados.getTiposdeExercicios(exercicios).toArray());
						}
					}
				}else{
					Mensagem.msgErro("Período inválido!", Constantes.NOME_PROGRAMA + " : Gráficos");
				}
			}
		});
		botaoPeriodo.setEnabled(false);
		botaoPeriodo.setBounds(304, 32, 117, 23);
		panel_1.add(botaoPeriodo);

		panel_2 = new JPanel();
		panel_2.setBorder(BorderFactory.createTitledBorder("5 - Selecionar os dados a serem exibidos no gráfico"));
		panel_2.setBounds(25, 160, 443, 106);
		contentPanel.add(panel_2);
		panel_2.setLayout(null);

		btnDuracao = new JButton("Dura\u00E7\u00E3o\r\n");
		btnDuracao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_BARRAS_POR_TIPO_EXERCICIO_DURACAO)));
				painelGrafico.validate();
			}
		});
		btnDuracao.setEnabled(false);
		btnDuracao.setBounds(32, 18, 116, 33);
		panel_2.add(btnDuracao);

		btnDistancia = new JButton("Dist\u00E2ncia");
		btnDistancia.setEnabled(false);
		btnDistancia.setBounds(32, 62, 116, 33);
		panel_2.add(btnDistancia);

		btnCaloriasPerdidas = new JButton("<html>Calorias<br>Perdidas</html>");
		btnCaloriasPerdidas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_BARRAS_POR_TIPO_EXERCICIO_CALORIAS)));
				painelGrafico.validate();
			}
		});
		btnCaloriasPerdidas.setEnabled(false);
		btnCaloriasPerdidas.setBounds(169, 18, 116, 33);
		panel_2.add(btnCaloriasPerdidas);

		btnVelocidadeMedia = new JButton("<html>Velocidade<br>M\u00E9dia</html>\r\n");
		btnVelocidadeMedia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_BARRAS_POR_TIPO_EXERCICIO_VELOCIDADE)));
				painelGrafico.validate();
			}
		});
		btnVelocidadeMedia.setEnabled(false);
		btnVelocidadeMedia.setBounds(169, 62, 116, 33);
		panel_2.add(btnVelocidadeMedia);

		btnRitmoMedio = new JButton("Ritmo M\u00E9dio");
		btnRitmoMedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_BARRAS_POR_TIPO_EXERCICIO_RITMO)));
				painelGrafico.validate();
			}
		});
		btnRitmoMedio.setEnabled(false);
		btnRitmoMedio.setBounds(295, 62, 116, 33);
		panel_2.add(btnRitmoMedio);

		btnPassos = new JButton("Passos");
		btnPassos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_BARRAS_POR_TIPO_EXERCICIO_PASSOS)));
				painelGrafico.validate();
			}
		});
		btnPassos.setEnabled(false);
		btnPassos.setBounds(295, 18, 116, 33);
		panel_2.add(btnPassos);

		btnDistanciaLinhas = new JButton("Dist\u00E2ncia");
		btnDistanciaLinhas.setEnabled(false);
		btnDistanciaLinhas.setVisible(false);
		btnDistanciaLinhas.setBounds(32, 18, 116, 33);
		btnDistanciaLinhas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_LINHAS_DISTANCIA)));
				painelGrafico.validate();
			}
		});
		panel_2.add(btnDistanciaLinhas);

		btnCaloriasLinhas = new JButton("<html>Calorias<br>Perdidas</html>");
		btnCaloriasLinhas.setEnabled(false);
		btnCaloriasLinhas.setVisible(false);
		btnCaloriasLinhas.setBounds(158, 18, 116, 33);
		btnCaloriasLinhas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_LINHAS_CALORIAS)));
				painelGrafico.validate();
			}
		});
		panel_2.add(btnCaloriasLinhas);

		btnPassosLinhas = new JButton("Passos");
		btnPassosLinhas.setEnabled(false);
		btnPassosLinhas.setVisible(false);
		btnPassosLinhas.setBounds(284, 18, 116, 33);
		btnPassosLinhas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_LINHAS_PASSOS)));
				painelGrafico.validate();

			}
		});
		panel_2.add(btnPassosLinhas);

		btnPassosColunas2 = new JButton("<html><center>N\u00FAmero total<br>de passos</center></html>");
		btnPassosColunas2.setEnabled(false);
		btnPassosColunas2.setBounds(32, 18, 116, 33);
		btnPassosColunas2.setVisible(false);
		btnPassosColunas2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_BARRAS_GERAL_TOTAL_PASSOS)));
				painelGrafico.validate();
			}
		});
		panel_2.add(btnPassosColunas2);

		btnDistanciaMediaColunas2 = new JButton("<html><center>Dist\u00E2ncia<br>M\u00E9dia</center><html>");
		btnDistanciaMediaColunas2.setEnabled(false);
		btnDistanciaMediaColunas2.setVisible(false);
		btnDistanciaMediaColunas2.setBounds(166, 18, 116, 33);
		btnDistanciaMediaColunas2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_BARRAS_GERAL_MEDIA_DISTANCIA)));
				painelGrafico.validate();
			}
		});
		panel_2.add(btnDistanciaMediaColunas2);

		btnDistanciaColunas2 = new JButton("<html><center>Dist\u00E2ncia<br>Total</center><html>");
		btnDistanciaColunas2.setEnabled(false);
		btnDistanciaColunas2.setVisible(false);
		btnDistanciaColunas2.setBounds(302, 16, 116, 33);
		btnDistanciaColunas2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_BARRAS_GERAL_TOTAL_DISTANCIA)));
				painelGrafico.validate();
			}
		});
		panel_2.add(btnDistanciaColunas2);

		btnCaloriasColunas2 = new JButton("<html><center>Total de<br>Calorias Perdidas</center></html>");
		btnCaloriasColunas2.setEnabled(false);
		btnCaloriasColunas2.setVisible(false);
		btnCaloriasColunas2.setBounds(32, 62, 171, 33);
		btnCaloriasColunas2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_BARRAS_GERAL_TOTAL_CALORIAS)));
				painelGrafico.validate();
			}
		});
		panel_2.add(btnCaloriasColunas2);

		btnMediaCaloriasColunas2 = new JButton("<html><center>M\u00E9dia de<br>Calorias Perdidas</center></html>");
		btnMediaCaloriasColunas2.setEnabled(false);
		btnMediaCaloriasColunas2.setVisible(false);
		btnMediaCaloriasColunas2.setBounds(213, 62, 184, 33);
		btnMediaCaloriasColunas2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_BARRAS_GERAL_MEDIA_CALORIAS)));
				painelGrafico.validate();
			}
		});
		panel_2.add(btnMediaCaloriasColunas2);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(BorderFactory.createTitledBorder("1 - Selecionar o Usuário"));
		panel_3.setBounds(27, 36, 441, 63);
		contentPanel.add(panel_3);
		panel_3.setLayout(null);

		boxUsuarios = new JComboBox<Object>();
		boxUsuarios.setBounds(10, 30, 262, 20);
		panel_3.add(boxUsuarios);
		Utilitarios.carregarComboBox(boxUsuarios, manipuladorDeDados.lerIndicesUsuarios().toArray());

		btnSelecionarUsuario = new JButton("Selecionar");
		btnSelecionarUsuario.setBounds(294, 29, 128, 23);
		panel_3.add(btnSelecionarUsuario);

		panel_4 = new JPanel();
		panel_4.setBorder(BorderFactory.createTitledBorder("Exportar dados para PDF"));
		panel_4.setBounds(478, 160, 202, 63);
		contentPanel.add(panel_4);
		panel_4.setLayout(null);

		gerarPDF = new JButton("Exportar");
		gerarPDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if(mostrarTelaAtencaoNovamente){
					String nomeImagem = "";
					if(tipoDeGraficoSelecionado.compareTo(Constantes.TIPO_DE_GRAFICO_1_POR_TIPO) == 0){
						if(tipoExercicio.compareTo(Constantes.VALOR_TODOS_COMBO_BOX) == 0)
							nomeImagem = "graficoCumulativoTipo1.png";
						else
							nomeImagem = "graficoTipo1.png";
					}else if(tipoDeGraficoSelecionado.compareTo(Constantes.TIPO_DE_GRAFICO_2_POR_TIPO) == 0){
						if(tipoExercicio.compareTo(Constantes.VALOR_TODOS_COMBO_BOX) == 0)
							nomeImagem = "graficoCumulativoTipo2.jpg";
						else
							nomeImagem = "graficoTipo2.png";
					}else{
						nomeImagem = "graficoTipo3.png";
						tipoExercicio = null;
					}
					
					Icon icon = Utilitarios.getIconAjustado(nomeImagem, 665, 325);
					IGAvisoExportarPDF telaAviso = 	new IGAvisoExportarPDF(icon, usuario.getNome() +  " (" + usuario.getEmail() + ")", dtInicial.toString() + " - " + dtFinal.toString(), tipoDeGraficoSelecionado, tipoExercicio);
					realizarExportacao = telaAviso.getRealizarExportacao();
					mostrarTelaAtencaoNovamente = telaAviso.getMostrarNovamente();
				}else{
					
					if(GerarPDF.gerarPDFGraficos(usuario, dtInicial, dtFinal, contentPanel, graficosGerados.toArray(new JFreeChart[0])) == 1)
						Mensagem.msgInfo(contentPanel, Constantes.MENSAGEM_EXPORTACAO_CONCLUIDA, Constantes.NOME_PROGRAMA + " : Exportar para PDF");
					else
						Mensagem.msgErro(Constantes.MENSAGEM_EXPORTACAO_CANCELADA, Constantes.NOME_PROGRAMA + " : Exportar para PDF");
					return;
				}
				
				if(realizarExportacao){
					if(GerarPDF.gerarPDFGraficos(usuario, dtInicial, dtFinal, contentPanel, graficosGerados.toArray(new JFreeChart[0])) == 1)
						Mensagem.msgInfo(contentPanel, Constantes.MENSAGEM_EXPORTACAO_CONCLUIDA, Constantes.NOME_PROGRAMA + " : Exportar para PDF");
					else
						Mensagem.msgErro(Constantes.MENSAGEM_EXPORTACAO_CANCELADA, Constantes.NOME_PROGRAMA + " : Exportar para PDF");
				}
				else
					Mensagem.msgErro(Constantes.MENSAGEM_EXPORTACAO_CANCELADA, Constantes.NOME_PROGRAMA + " : Exportar para PDF");
			}
		});
		gerarPDF.setEnabled(false);
		gerarPDF.setBounds(28, 29, 150, 23);
		panel_4.add(gerarPDF);

		painelTipoExercicio = new JPanel();
		painelTipoExercicio.setBorder(BorderFactory.createTitledBorder("4 - Selecionar o tipo de exercício"));
		painelTipoExercicio.setBounds(478, 98, 431, 63);
		contentPanel.add(painelTipoExercicio);
		painelTipoExercicio.setLayout(null);

		btnTipoExercicio = new JButton("Selecionar");
		btnTipoExercicio.setEnabled(false);
		btnTipoExercicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoExercicio = (String) comboBoxTipoExercicios.getSelectedItem();
				exerciciosDoTipo = ManipuladorDeDados.getExerciciosDoTipo(exercicios, tipoExercicio);

				Utilitarios.alterarEnabledBotoes(true,btnCaloriasPerdidas,btnDistancia, btnDuracao,btnPassos, btnCaloriasLinhas, btnDistanciaLinhas, btnPassosLinhas);
				if(ManipuladorDeDados.isListOfExerciciosAerobicos(exerciciosDoTipo) || (ManipuladorDeDados.isListOfExerciciosAerobicos(exercicios) && tipoExercicio.equals(Constantes.VALOR_TODOS_COMBO_BOX))){
					Utilitarios.alterarEnabledBotoes(true, btnRitmoMedio,btnVelocidadeMedia);
				}
				else{
					Utilitarios.alterarEnabledBotoes(false, btnRitmoMedio,btnVelocidadeMedia);
				}

				if(tipoExercicio.equals(Constantes.VALOR_TODOS_COMBO_BOX))
					graficosGerados = GerarGraficos.gerarTodosOsGraficosDeUmTipo(null, exercicios, tipoDeGraficoSelecionado, true);
				else
					graficosGerados = GerarGraficos.gerarTodosOsGraficosDeUmTipo(tipoExercicio, exerciciosDoTipo, tipoDeGraficoSelecionado, false);
				
				gerarPDF.setEnabled(true);
			}
		});
		btnTipoExercicio.setBounds(304, 29, 117, 23);
		painelTipoExercicio.add(btnTipoExercicio);

		comboBoxTipoExercicios = new JComboBox<Object>();
		comboBoxTipoExercicios.setEnabled(false);
		comboBoxTipoExercicios.setBounds(10, 29, 272, 23);
		painelTipoExercicio.add(comboBoxTipoExercicios);

		panelTiposDeGraficos = new JPanel();
		panelTiposDeGraficos.setLayout(null);
		panelTiposDeGraficos.setBorder(BorderFactory.createTitledBorder("3 - Tipo do Gráfico"));
		panelTiposDeGraficos.setBounds(27, 98, 441, 63);
		contentPanel.add(panelTiposDeGraficos);

		comboBox_1 = new JComboBox<Object>();
		List<Object> tiposGraficos = new ArrayList<Object>();
		tiposGraficos.add(Constantes.TIPO_DE_GRAFICO_1_POR_TIPO);
		tiposGraficos.add(Constantes.TIPO_DE_GRAFICO_2_POR_TIPO);
		tiposGraficos.add(Constantes.TIPO_DE_GRAFICO_3);
		Utilitarios.carregarComboBox(comboBox_1, tiposGraficos.toArray());
		comboBox_1.setBounds(10, 30, 274, 20);
		panelTiposDeGraficos.add(comboBox_1);

		btnSelecionarTipoGrafico = new JButton("Selecionar");
		btnSelecionarTipoGrafico.setEnabled(false);
		btnSelecionarTipoGrafico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				tipoDeGraficoSelecionado = (String) comboBox_1.getSelectedItem();

				if(tipoDeGraficoSelecionado.compareTo(Constantes.TIPO_DE_GRAFICO_1_POR_TIPO) == 0){
					Utilitarios.alterarVisibleBotoes(true, btnCaloriasPerdidas, btnDistancia,btnDuracao,	btnPassos,btnRitmoMedio,btnVelocidadeMedia);
					Utilitarios.alterarEnabledBotoes(false,  btnCaloriasPerdidas, btnDistancia,btnDuracao,	btnPassos,btnRitmoMedio,btnVelocidadeMedia);
					Utilitarios.alterarVisibleBotoes(false, btnDistanciaLinhas,btnCaloriasLinhas,	btnPassosLinhas, btnCaloriasColunas2, btnDistanciaMediaColunas2,
							btnMediaCaloriasColunas2, btnPassosColunas2, btnDistanciaColunas2);
					comboBoxTipoExercicios.setEnabled(true);
					btnTipoExercicio.setEnabled(true);
					gerarPDF.setEnabled(false);
					panel_2.setBounds(25, 160, 443,106);
				}
				else if(tipoDeGraficoSelecionado.compareTo(Constantes.TIPO_DE_GRAFICO_2_POR_TIPO) == 0){
					Utilitarios.alterarVisibleBotoes(true, btnDistanciaLinhas,btnCaloriasLinhas,btnPassosLinhas);
					Utilitarios.alterarEnabledBotoes(false,btnDistanciaLinhas,btnCaloriasLinhas,btnPassosLinhas); 
					Utilitarios.alterarVisibleBotoes(false, btnCaloriasPerdidas, btnDistancia,btnDuracao,	btnPassos,btnRitmoMedio,btnVelocidadeMedia,
							btnCaloriasColunas2, btnDistanciaMediaColunas2,btnMediaCaloriasColunas2, btnPassosColunas2, btnDistanciaColunas2);

					comboBoxTipoExercicios.setEnabled(true);
					btnTipoExercicio.setEnabled(true);
					gerarPDF.setEnabled(false);

					panel_2.setBounds(25, 160, 443,60);
				}else{
					Utilitarios.alterarVisibleBotoes(false, btnCaloriasPerdidas, btnDistancia,btnDuracao,	btnPassos,btnRitmoMedio,btnVelocidadeMedia,
							btnDistanciaLinhas,btnCaloriasLinhas,btnPassosLinhas);
					Utilitarios.alterarEnabledBotoes(false, btnCaloriasPerdidas, btnDistancia,btnDuracao,	btnPassos,btnRitmoMedio,btnVelocidadeMedia,
							btnDistanciaLinhas,btnCaloriasLinhas,btnPassosLinhas);

					graficosGerados = GerarGraficos.gerarTodosOsGraficosDeUmTipo(null, exercicios, tipoDeGraficoSelecionado, false);
					Utilitarios.alterarVisibleBotoes(true,btnCaloriasColunas2, btnDistanciaMediaColunas2,btnMediaCaloriasColunas2, btnPassosColunas2, btnDistanciaColunas2);
					Utilitarios.alterarEnabledBotoes(true, btnCaloriasColunas2, btnDistanciaMediaColunas2,btnMediaCaloriasColunas2, btnPassosColunas2, btnDistanciaColunas2, gerarPDF);
					comboBoxTipoExercicios.setEnabled(false);
					btnTipoExercicio.setEnabled(false);

					panel_2.setBounds(25, 160, 443,106);
				}
			}
		});
		btnSelecionarTipoGrafico.setBounds(294, 29, 128, 23);
		panelTiposDeGraficos.add(btnSelecionarTipoGrafico);

		btnSelecionarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usuario = manipuladorDeDados.lerUsuario(((IndiceUsuario) boxUsuarios.getSelectedItem()).getNumeroRegistro());
				Utilitarios.alterarEnabledBotoes(true, botaoPeriodo);
			}
		});
		btnDistancia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelGrafico.removeAll();
				painelGrafico.add(new ChartPanel(graficosGerados.get(Constantes.POSICAO_GRAFICO_BARRAS_POR_TIPO_EXERCICIO_DISTANCIA)));
				painelGrafico.validate();
			}
		});
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton cancelButton = new JButton("Sair");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		setVisible(true);
		setLocationRelativeTo(null);
	}
}