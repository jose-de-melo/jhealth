package jcms.too.avaliacaofisica.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import jcms.too.avaliacaofisica.arquivos.IndiceUsuario;
import jcms.too.avaliacaofisica.dados.ManipuladorDeDados;
import jcms.too.avaliacaofisica.modelo.Exercicio;
import jcms.too.avaliacaofisica.modelo.ExercicioAerobico;
import jcms.too.avaliacaofisica.modelo.Usuario;
import jcms.too.avaliacaofisica.out.Mensagem;
import jcms.too.avaliacaofisica.pdf.GerarPDF;
import jcms.too.avaliacaofisica.utilitarios.Constantes;
import jcms.too.avaliacaofisica.utilitarios.Unidades;
import jcms.too.avaliacaofisica.utilitarios.Utilitarios;

/**
 * Classe usada para criar a interface usada para gerar relatórios sobre usuários/exercícios armazenados no banco de dados da aplicação.
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.2
 * @version 0.3
 *
 */
public class IGRelatorio extends JDialog {

	private static final long serialVersionUID = 3819920618537511506L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox<Object> comboBox_1;
	private JLabel duracao;
	private JLabel distancia;
	private JLabel calorias;
	private JLabel passos;
	private JLabel velocidade;
	private JPanel painelExportar;
	private JButton btnExportar;
	private JPanel panelDuracao;
	private JPanel panelDistancia;
	private JPanel panelCalorias;
	private JPanel panelPassos;
	private JPanel panelVelocidade;
	private Usuario usuarioSelecionado;

	/**
	 * Cria uma instância {@link IGRelatorio}.
	 * 
	 * @param manipuladorDeDados - referência {@link ManipuladorDeDados} usada para executar operações da interface.
	 */
	public IGRelatorio(ManipuladorDeDados manipuladorDeDados) {
		setBounds(100, 100, 607, 521);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setModal(true);
		setIconImage(AvaliacaoFisica.icone.getImage());
		setTitle(Constantes.NOME_PROGRAMA + " : Relatório");

		{
			JLabel lblRelatrio = new JLabel("Relat\u00F3rio\r\n");
			lblRelatrio.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblRelatrio.setBounds(34, 11, 101, 20);
			contentPanel.add(lblRelatrio);
		}
		{
			comboBox_1 = new JComboBox<Object>();
			comboBox_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					Utilitarios.limparLabels(distancia, duracao, velocidade, passos, calorias);
					Utilitarios.alterarEnabledBotoes(false, btnExportar);
				}
			});
			Utilitarios.carregarComboBox(comboBox_1, manipuladorDeDados.lerIndicesUsuarios().toArray());
			comboBox_1.setBounds(34, 62, 313, 20);
			contentPanel.add(comboBox_1);
		}
		{
			JButton button = new JButton("Selecionar Cliente");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					usuarioSelecionado = manipuladorDeDados.lerUsuario(((IndiceUsuario)comboBox_1.getSelectedItem()).getNumeroRegistro());
					ArrayList<Object> objetos = manipuladorDeDados.buscarExerciciosDeUsuario(usuarioSelecionado);
					ArrayList<Exercicio> exercicios = new ArrayList<Exercicio>();
					Utilitarios.limparLabels(distancia, duracao, velocidade, passos, calorias);
					Utilitarios.alterarEnabledBotoes(false, btnExportar);
					
					Exercicio exercicio;
					for (Object object : objetos) {
						exercicios.add((Exercicio) object);
					}

					exercicios.sort(new Comparator<Exercicio>() {
						@Override
						public int compare(Exercicio exer1, Exercicio exer2) {
							return (-1) * exer1.getDuracao().compareTo(exer2.getDuracao());
						}
					});
					exercicio = exercicios.get(0);
					duracao.setText(exercicio.getDuracao() + " : " + exercicio.getTipoExercicio() + " - " + exercicio.getData().toString());

					exercicios.sort(new Comparator<Exercicio>() {
						@Override
						public int compare(Exercicio exer1, Exercicio exer2) {
							return (-1) * Double.compare(exer1.getDistancia(), exer2.getDistancia());
						}
					});
					exercicio = exercicios.get(0);
					distancia.setText(exercicio.getDistancia() + " " + Unidades.DISTANCIA.getUnidade() + " : " + exercicio.getTipoExercicio() + " - " + exercicio.getData().toString());

					exercicios.sort(new Comparator<Exercicio>() {
						@Override
						public int compare(Exercicio o1, Exercicio o2) {
							return (-1) * Double.compare(o1.getCaloriasPerdidas(), o2.getCaloriasPerdidas());
						}
					});
					exercicio = exercicios.get(0);
					calorias.setText(exercicio.getCaloriasPerdidas() + " " + Unidades.CALORIA.getUnidade() + " : " + exercicio.getTipoExercicio() + " - " + exercicio.getData().toString());
					
					exercicios.sort(new Comparator<Exercicio>() {
						@Override
						public int compare(Exercicio o1, Exercicio o2) {
							return (-1) * Integer.compare(o1.getPassosDados(), o2.getPassosDados());
						}
					});
					exercicio = exercicios.get(0);
					passos.setText(exercicio.getPassosDados() + " passos dados" + " : " + exercicio.getTipoExercicio() + " - " + exercicio.getData().toString());

					ArrayList<ExercicioAerobico> exerciciosA = new ArrayList<ExercicioAerobico>();
					for (Exercicio exer : exercicios) {
						if(exer instanceof ExercicioAerobico){
							exerciciosA.add((ExercicioAerobico) exer);
						}
					}
					if(!exerciciosA.isEmpty()){
						exerciciosA.sort(new Comparator<ExercicioAerobico>() {
							@Override
							public int compare(ExercicioAerobico o1, ExercicioAerobico o2) {
								return (-1) * Double.compare(o1.getVelocidadeMaxima(), o2.getVelocidadeMaxima());
							}
						});
						velocidade.setText(exerciciosA.get(0).getVelocidadeMaxima() + " " + Unidades.VELOCIDADE.getUnidade() + " : " + exerciciosA.get(0).getTipoExercicio() + " - " + exerciciosA.get(0).getData().toString());
					}
					else{
						velocidade.setText( " -----"  + " : Nenhum exercício aeróbico praticado.");
					}
					Utilitarios.alterarEnabledBotoes(true, btnExportar);
				}
			});
			button.setBounds(393, 61, 164, 23);
			contentPanel.add(button);
		}
		{
			painelExportar = new JPanel();
			painelExportar.setBorder(BorderFactory.createTitledBorder("Exportar dados p/PDF"));
			painelExportar.setBounds(393, 124, 153, 51);
			contentPanel.add(painelExportar);
			painelExportar.setLayout(null);
			
			btnExportar = new JButton("Exportar");
			btnExportar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(GerarPDF.gerarPDFRelatorio(usuarioSelecionado, gerarLinhas(), contentPanel) == 1){
						Mensagem.msgInfo(contentPanel, Constantes.MENSAGEM_EXPORTACAO_CONCLUIDA, Constantes.NOME_PROGRAMA + " : Exportar ");
					}else{
						Mensagem.msgErro(Constantes.MENSAGEM_EXPORTACAO_CANCELADA, Constantes.NOME_PROGRAMA + " : Exportar ");
					}
				}
			});
			btnExportar.setEnabled(false);
			btnExportar.setBounds(29, 17, 89, 23);
			painelExportar.add(btnExportar);
		}
		
		panelDuracao = new JPanel();
		panelDuracao.setBounds(34, 124, 313, 51);
		panelDuracao.setBorder(BorderFactory.createTitledBorder("Exercício com a maior Duração"));
		contentPanel.add(panelDuracao);
		panelDuracao.setLayout(null);
		{
			duracao = new JLabel("");
			duracao.setBounds(10, 20, 293, 20);
			panelDuracao.add(duracao);
		}
		{
			panelDistancia = new JPanel();
			panelDistancia.setBorder(BorderFactory.createTitledBorder("Exercício com a maior Distância Percorrida"));
			panelDistancia.setBounds(34, 186, 313, 51);
			contentPanel.add(panelDistancia);
			panelDistancia.setLayout(null);
			{
				distancia = new JLabel("");
				distancia.setBounds(10, 20, 293, 20);
				panelDistancia.add(distancia);
			}
		}
		{
			panelCalorias = new JPanel();
			panelCalorias.setBorder(BorderFactory.createTitledBorder("Exercício com o maior número de Calorias Perdidas"));
			panelCalorias.setBounds(34, 248, 313, 51);
			contentPanel.add(panelCalorias);
			panelCalorias.setLayout(null);
			{
				calorias = new JLabel("");
				calorias.setBounds(10, 20, 293, 20);
				panelCalorias.add(calorias);
			}
		}
		{
			panelPassos = new JPanel();
			panelPassos.setBorder(BorderFactory.createTitledBorder("Exercício com o maior Número de Passos Dados"));
			panelPassos.setBounds(34, 303, 313, 51);
			contentPanel.add(panelPassos);
			panelPassos.setLayout(null);
			{
				passos = new JLabel("");
				passos.setBounds(10, 20, 293, 20);
				panelPassos.add(passos);
			}
		}
		{
			panelVelocidade = new JPanel();
			panelVelocidade.setBorder(BorderFactory.createTitledBorder("Exercício com a velocidade máxima mais rápida"));
			panelVelocidade.setBounds(34, 364, 313, 51);
			contentPanel.add(panelVelocidade);
			panelVelocidade.setLayout(null);
			{
				velocidade = new JLabel("");
				velocidade.setBounds(10, 20, 293, 20);
				panelVelocidade.add(velocidade);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, (Color) Color.LIGHT_GRAY));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton cancelButton = new JButton("Sair");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private String[] gerarLinhas() {
		List<String> linhas = new ArrayList<String>();
		
		linhas.add("Duração");
		linhas.add(duracao.getText().split(" : ")[0]);
		linhas.add(duracao.getText().split(" : ")[1]);
		linhas.add("Distância Percorrida");
		linhas.add(distancia.getText().split(" : ")[0]);
		linhas.add(distancia.getText().split(" : ")[1]);
		linhas.add("Número de Calorias Perdidas");
		linhas.add(calorias.getText().split(" : ")[0]);
		linhas.add(calorias.getText().split(" : ")[1]);
		linhas.add("Número de Passos Dados");
		linhas.add(passos.getText().split(" : ")[0].replace(" passos dados", ""));
		linhas.add(passos.getText().split(" : ")[1]);
		linhas.add("Velocidade Máxima");
		linhas.add(velocidade.getText().split(" : ")[0]);
		linhas.add(velocidade.getText().split(" : ")[1]);
		
		return linhas.toArray(new String[0]);
	}
}