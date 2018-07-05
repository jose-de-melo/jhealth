package jcms.too.avaliacaofisica.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import jcms.too.avaliacaofisica.arquivos.IndiceUsuario;
import jcms.too.avaliacaofisica.dados.ManipuladorDeDados;
import jcms.too.avaliacaofisica.modelo.Exercicio;
import jcms.too.avaliacaofisica.modelo.ExercicioAerobico;
import jcms.too.avaliacaofisica.modelo.Ritmo;
import jcms.too.avaliacaofisica.modelo.Usuario;
import jcms.too.avaliacaofisica.utilitarios.Constantes;
import jcms.too.avaliacaofisica.utilitarios.Unidades;
import jcms.too.avaliacaofisica.utilitarios.Utilitarios;

/**
 * Classe usada para criar a interface usada na consulta de usuários/exercícios da aplicação.
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.2
 *
 */
public class IGConsultarUsuarioExercicio extends JDialog {
	private static final long serialVersionUID = -3389328870053397152L;
	private final JPanel contentPanel = new JPanel();
	protected JLabel labelNome, labelSexo, labelAltura, labelPeso, labelDataNascimento, labelEmail;
	protected JComboBox<Object> comboBox_1;

	protected JButton btnNewButton;
	private JLabel labelTipoExercicio;
	private JLabel labelData;
	private JLabel labelTempo;
	private JLabel labelDuracao;
	private JLabel labelCalorias;
	private JLabel labelVelocidadeMedia;
	private JLabel labelVelocidadeMaxima;
	private JLabel labelMenorElevacao;
	private JLabel labelRitmoMaximo;
	private JLabel labelRitmoMedio;
	private JLabel labelMaiorElevacao;
	private JLabel labelPassos;
	private JPanel panelRitmos;
	private JLabel labelDistancia;
	private JPanel painelSelecionarUsuario;
	private JPanel painelDadosUsuario;
	private JPanel painelExercicio;
	private JPanel painelDadosExercicios;
	private JPanel painelDadosComplementares;
	private JButton btnFinalizar1;

	/**
	 * Cria uma instância {@link IGConsultarUsuarioExercicio}.
	 * 
	 * @param manipuladorDeDados - referência {@link ManipuladorDeDados} usada para executar operações da interface.
	 */
	public IGConsultarUsuarioExercicio(ManipuladorDeDados manipuladorDeDados) {
		setResizable(false);
		setBounds(0, 0, 776, 447);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 995, 594);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle(Constantes.NOME_PROGRAMA + " : Consultar Usuário");
		contentPanel.setLayout(null);
		setIconImage(AvaliacaoFisica.icone.getImage());

		JLabel lblConsultarUsurios = new JLabel("Consulta de Usuários");
		lblConsultarUsurios.setBounds(10, 11, 182, 14);
		lblConsultarUsurios.setFont(new Font("Calibri", Font.BOLD, 18));
		contentPanel.add(lblConsultarUsurios);

		painelSelecionarUsuario = new JPanel();
		painelSelecionarUsuario.setBounds(10, 46, 338, 93);
		contentPanel.add(painelSelecionarUsuario);
		painelSelecionarUsuario.setLayout(null);

		JButton btnSelecionarUsurio = new JButton("Selecionar Usu\u00E1rio");
		btnSelecionarUsurio.setBounds(100, 59, 136, 23);
		painelSelecionarUsuario.add(btnSelecionarUsurio);


		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setBounds(10, 22, 313, 26);
		painelSelecionarUsuario.add(comboBox);
		painelSelecionarUsuario.setBorder(BorderFactory.createTitledBorder("Selecione o usuário"));
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				comboBox_1.setEnabled(false);
				comboBox_1.removeAllItems();
				btnNewButton.setEnabled(false);
				Utilitarios.limparLabels(labelTipoExercicio, labelCalorias,labelData, labelDuracao, labelTempo, labelPassos, labelPassos,labelDistancia,
																labelDataNascimento, labelNome, labelSexo, labelAltura, labelPeso, labelEmail);
				painelDadosComplementares.setVisible(false);
			}
		});
		Utilitarios.carregarComboBox(comboBox, manipuladorDeDados.lerIndicesUsuarios().toArray());

		painelDadosUsuario = new JPanel();
		painelDadosUsuario.setBorder(BorderFactory.createTitledBorder("Dados do Usuário"));
		painelDadosUsuario.setBounds(10, 160, 338, 207);
		contentPanel.add(painelDadosUsuario);
		painelDadosUsuario.setLayout(null);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 24, 103, 14);
		painelDadosUsuario.add(lblNome);

		JLabel lblNewLabel = new JLabel("Sexo: \r\n");
		lblNewLabel.setBounds(10, 49, 103, 14);
		painelDadosUsuario.add(lblNewLabel);

		JLabel lblAltura = new JLabel("Altura:");
		lblAltura.setBounds(10, 74, 103, 14);
		painelDadosUsuario.add(lblAltura);

		JLabel lblPeso = new JLabel("Peso:");
		lblPeso.setBounds(10, 99, 103, 14);
		painelDadosUsuario.add(lblPeso);

		JLabel lblNewLabel_1 = new JLabel("Data de Nascimento: ");
		lblNewLabel_1.setBounds(10, 124, 128, 14);
		painelDadosUsuario.add(lblNewLabel_1);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(10, 149, 103, 14);
		painelDadosUsuario.add(lblEmail);

		labelNome = new JLabel("");
		labelNome.setBounds(148, 24, 185, 14);
		painelDadosUsuario.add(labelNome);

		labelSexo = new JLabel("");
		labelSexo.setBounds(148, 49, 185, 14);
		painelDadosUsuario.add(labelSexo);

		labelAltura = new JLabel("");
		labelAltura.setBounds(148, 74, 185, 14);
		painelDadosUsuario.add(labelAltura);

		labelPeso = new JLabel("");
		labelPeso.setBounds(148, 99, 185, 14);
		painelDadosUsuario.add(labelPeso);

		labelDataNascimento = new JLabel("");
		labelDataNascimento.setBounds(148, 124, 185, 14);
		painelDadosUsuario.add(labelDataNascimento);

		labelEmail = new JLabel("");
		labelEmail.setBounds(148, 149, 185, 20);
		painelDadosUsuario.add(labelEmail);

		painelExercicio = new JPanel();
		painelExercicio.setBounds(393, 46, 358, 93);
		painelExercicio.setBorder(BorderFactory.createTitledBorder("Selecione o exercício"));
		contentPanel.add(painelExercicio);
		painelExercicio.setLayout(null);

		comboBox_1 = new JComboBox<Object>();
		comboBox_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Utilitarios.limparLabels(labelTipoExercicio, labelCalorias,labelData, labelDuracao, labelTempo, labelPassos, labelPassos,labelDistancia);
				painelDadosComplementares.setVisible(false);
			}
		});
		comboBox_1.setBounds(23, 22, 313, 26);
		painelExercicio.add(comboBox_1);
		comboBox_1.setEnabled(false);

		btnNewButton = new JButton("Selecionar Exerc\u00EDcio\r\n");
		btnNewButton.setBounds(113, 59, 136, 23);
		painelExercicio.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = comboBox_1.getSelectedItem();

				if(obj instanceof Exercicio){
					Exercicio exercicio = (Exercicio) obj;
					painelDadosComplementares.setVisible(false);

					labelTipoExercicio.setText(exercicio.getTipoExercicio());
					labelCalorias.setText(exercicio.getCaloriasPerdidas() + " " +  Unidades.CALORIA.getUnidade());
					labelData.setText(exercicio.getData().toString());
					labelDuracao.setText(exercicio.getDuracao().toString());
					labelPassos.setText(Integer.valueOf(exercicio.getPassosDados()).toString());
					labelTempo.setText(exercicio.getTempo().toString());
					labelDistancia.setText(exercicio.getDistancia() + " "  + Unidades.DISTANCIA.getUnidade());
					btnFinalizar1.setVisible(true);
					setBounds(0, 0, 776, 447);
					setLocationRelativeTo(null);
				}
				if(obj instanceof ExercicioAerobico){
					setBounds(0, 11, 995, 660);
					setLocationRelativeTo(null);
					btnFinalizar1.setVisible(false);

					ExercicioAerobico exerA = (ExercicioAerobico) obj;

					labelMaiorElevacao.setText(exerA.getMaiorElevacao() + " " +Unidades.ELEVACAO.getUnidade());
					labelMenorElevacao.setText(exerA.getMenorElevacao() + " " + Unidades.ELEVACAO.getUnidade());
					labelVelocidadeMaxima.setText(exerA.getVelocidadeMaxima()  + " " + Unidades.VELOCIDADE.getUnidade());
					labelVelocidadeMedia.setText(exerA.getVelocidadeMedia()  + " " + Unidades.VELOCIDADE.getUnidade());
					labelRitmoMedio.setText(exerA.getRitmoMedio().getTempoRitmo());
					labelRitmoMaximo.setText(exerA.getRitmoMaximo().getTempoRitmo());
					painelDadosComplementares.setVisible(true);


					String ritmosStr = "";
					for (Ritmo ritmo : exerA.getRitmosNoExercicio()) {
						ritmosStr += ritmo.toString() + "\n";
					}

					JTextArea ritmos = new JTextArea(ritmosStr);
					ritmos.setEditable(false);
					JScrollPane scrollPane = new JScrollPane(ritmos);
					scrollPane.setPreferredSize(new Dimension(100, 100));
					panelRitmos.add(scrollPane);


				}
			}
		});
		btnNewButton.setEnabled(false);

		painelDadosExercicios = new JPanel();
		painelDadosExercicios.setBounds(393, 160, 358, 207);
		painelDadosExercicios.setBorder(BorderFactory.createTitledBorder("Dados do exercício"));
		contentPanel.add(painelDadosExercicios);
		painelDadosExercicios.setLayout(null);

		JLabel lblTipoDoExerccio = new JLabel("Tipo do Exerc\u00EDcio:");
		lblTipoDoExerccio.setBounds(10, 32, 111, 14);
		painelDadosExercicios.add(lblTipoDoExerccio);

		labelTipoExercicio = new JLabel("");
		labelTipoExercicio.setBounds(131, 32, 162, 14);
		painelDadosExercicios.add(labelTipoExercicio);

		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(10, 57, 111, 14);
		painelDadosExercicios.add(lblData);

		labelData = new JLabel("");
		labelData.setBounds(131, 57, 162, 14);
		painelDadosExercicios.add(labelData);

		JLabel lblTempo = new JLabel("Tempo:");
		lblTempo.setBounds(10, 82, 111, 14);
		painelDadosExercicios.add(lblTempo);

		labelTempo = new JLabel("");
		labelTempo.setBounds(131, 82, 162, 14);
		painelDadosExercicios.add(labelTempo);

		JLabel lblNewLabel_2 = new JLabel("Dura\u00E7\u00E3o:");
		lblNewLabel_2.setBounds(10, 107, 111, 14);
		painelDadosExercicios.add(lblNewLabel_2);

		labelDuracao = new JLabel("");
		labelDuracao.setBounds(131, 107, 162, 14);
		painelDadosExercicios.add(labelDuracao);

		JLabel lblDistncia = new JLabel("Dist\u00E2ncia:");
		lblDistncia.setBounds(10, 132, 111, 14);
		painelDadosExercicios.add(lblDistncia);

		labelDistancia = new JLabel("");
		labelDistancia.setBounds(131, 132, 162, 14);
		painelDadosExercicios.add(labelDistancia);

		JLabel lblCaloriasPerdidas = new JLabel("Calorias Perdidas:");
		lblCaloriasPerdidas.setBounds(10, 157, 111, 14);
		painelDadosExercicios.add(lblCaloriasPerdidas);

		labelCalorias = new JLabel("");
		labelCalorias.setBounds(131, 157, 162, 14);
		painelDadosExercicios.add(labelCalorias);

		JLabel lblPassos = new JLabel("Passos:");
		lblPassos.setBounds(10, 182, 111, 14);
		painelDadosExercicios.add(lblPassos);

		labelPassos = new JLabel("");
		labelPassos.setBounds(131, 182, 162, 14);
		painelDadosExercicios.add(labelPassos);

		painelDadosComplementares = new JPanel();
		painelDadosComplementares.setBounds(393, 378, 602, 198);
		painelDadosComplementares.setVisible(false);
		contentPanel.add(painelDadosComplementares);
		painelDadosComplementares.setLayout(null);

		JLabel lblVelocidadeMdia = new JLabel("Velocidade M\u00E9dia:");
		lblVelocidadeMdia.setBounds(10, 27, 111, 14);
		painelDadosComplementares.add(lblVelocidadeMdia);

		labelVelocidadeMedia = new JLabel("");
		labelVelocidadeMedia.setBounds(130, 27, 162, 14);
		painelDadosComplementares.add(labelVelocidadeMedia);

		JLabel lblVelocidadeMxima = new JLabel("Velocidade M\u00E1xima:");
		lblVelocidadeMxima.setBounds(10, 52, 111, 14);
		painelDadosComplementares.add(lblVelocidadeMxima);

		labelVelocidadeMaxima = new JLabel("");
		labelVelocidadeMaxima.setBounds(130, 52, 162, 14);
		painelDadosComplementares.add(labelVelocidadeMaxima);

		JLabel lblRitmoMdio = new JLabel("Ritmo M\u00E9dio:");
		lblRitmoMdio.setBounds(10, 77, 111, 14);
		painelDadosComplementares.add(lblRitmoMdio);

		labelRitmoMedio = new JLabel("");
		labelRitmoMedio.setBounds(131, 77, 162, 14);
		painelDadosComplementares.add(labelRitmoMedio);

		JLabel lblRitmoMximo = new JLabel("Ritmo M\u00E1ximo:");
		lblRitmoMximo.setBounds(10, 102, 111, 14);
		painelDadosComplementares.add(lblRitmoMximo);

		labelRitmoMaximo = new JLabel("");
		labelRitmoMaximo.setBounds(131, 102, 162, 14);
		painelDadosComplementares.add(labelRitmoMaximo);

		JLabel lblMenorElevao = new JLabel("Menor Eleva\u00E7\u00E3o:");
		lblMenorElevao.setBounds(10, 127, 111, 14);
		painelDadosComplementares.add(lblMenorElevao);

		labelMenorElevacao = new JLabel("");
		labelMenorElevacao.setBounds(131, 127, 162, 14);
		painelDadosComplementares.add(labelMenorElevacao);

		JLabel lblMaiorElevao = new JLabel("Maior Eleva\u00E7\u00E3o:");
		lblMaiorElevao.setBounds(10, 152, 111, 14);
		painelDadosComplementares.add(lblMaiorElevao);

		labelMaiorElevacao = new JLabel("");
		labelMaiorElevacao.setBounds(131, 152, 162, 14);
		painelDadosComplementares.add(labelMaiorElevacao);

		panelRitmos = new JPanel();
		panelRitmos.setBorder(BorderFactory.createTitledBorder("Ritmos No Exercício"));
		panelRitmos.setBounds(351, 11, 226, 105);
		painelDadosComplementares.add(panelRitmos);
		painelDadosComplementares.setBorder(BorderFactory.createTitledBorder("Dados Complementares"));
		panelRitmos.setLayout(new BorderLayout(0, 0));

		btnFinalizar1 = new JButton("Finalizar Consulta\r\n");
		btnFinalizar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnFinalizar1.setBounds(310, 387, 149, 23);
		contentPanel.add(btnFinalizar1);
		btnSelecionarUsurio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Usuario user = manipuladorDeDados.lerUsuario(((IndiceUsuario)comboBox.getSelectedItem()).getNumeroRegistro());
				setarValoresUsuario(user);
				comboBox_1.setEnabled(true);
				Utilitarios.carregarComboBox(comboBox_1, manipuladorDeDados.buscarExerciciosDeUsuario(user).toArray());
				btnNewButton.setEnabled(true);
			}
		});

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 595, 995, 111);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton btnFinalizar2 = new JButton("Finalizar Consulta\r\n");
				btnFinalizar2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(btnFinalizar2);
				getRootPane().setDefaultButton(btnFinalizar2);
			}
		}
		setVisible(true);
	}

	private void setarValoresUsuario(Usuario user) {
		labelNome.setText(user.getNome());
		labelAltura.setText(user.getAltura());
		labelDataNascimento.setText(user.getDataNascimento().toString());
		labelEmail.setText(user.getEmail());
		labelPeso.setText(user.getPeso());
		labelSexo.setText(user.getSexo());
	}
}