package jcms.too.avaliacaofisica.controller;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;

import jcms.too.avaliacaofisica.arquivos.IndiceUsuario;
import jcms.too.avaliacaofisica.dados.ManipuladorDeDados;
import jcms.too.avaliacaofisica.graficos.GerarGraficos;
import jcms.too.avaliacaofisica.gui.IGExportarPDF;
import jcms.too.avaliacaofisica.modelo.Data;
import jcms.too.avaliacaofisica.modelo.Exercicio;
import jcms.too.avaliacaofisica.modelo.Usuario;
import jcms.too.avaliacaofisica.out.Mensagem;
import jcms.too.avaliacaofisica.pdf.GerarPDF;
import jcms.too.avaliacaofisica.utilitarios.Constantes;
import jcms.too.avaliacaofisica.utilitarios.Utilitarios;

import org.jfree.chart.JFreeChart;

/**
 * Classe usada para controlar os eventos de um {@link IGExportarPDF}.
 * 
 * @author José do Carmo de Melo Silva
 *
 *@since 0.6
 */
public class ExportarController {
	
	/**
	 * Inicializa os campos, passados como parâmetro, com os valores default para os componentes de um {@link IGExportarPDF}.
	 * 
	 * @param boxUsuarios - {@link JComboBox} : campo a ser inicializado
	 * @param tiposDeGrafico - {@link JComboBox} : campo a ser inicializado
	 * @param manipulador - objeto usado para se obter os valores default dos componentes de um {@link IGExportarPDF}
	 */
	public static void inicializarCampos(JComboBox<Object> boxUsuarios, JComboBox<Object> tiposDeGrafico,ManipuladorDeDados manipulador) {
		Utilitarios.carregarComboBox(boxUsuarios, new IndiceUsuario(Constantes.VALOR_TODOS_COMBO_BOX, null, 0));
		Utilitarios.carregarComboBox(boxUsuarios, manipulador.lerIndicesUsuarios().toArray());
		Utilitarios.carregarComboBox(tiposDeGrafico, Constantes.VALOR_TODOS_COMBO_BOX, Constantes.TIPO_DE_GRAFICO_1, Constantes.TIPO_DE_GRAFICO_2, Constantes.TIPO_DE_GRAFICO_3);
	}
	
	/**
	 * Realiza as operações de tratamento do evento do botão <code>Exportar</code> de um {@link IGExportarPDF}.
	 * 
	 * @param manipulador - objeto usado para obter os dados necessários para tratar o evento.
	 * @param dtInicial - {@link Data} : data inicial do período selecionado
	 * @param dtFinal - {@link Data} : data final do período selecionado
	 * @param indice - {@link IndiceUsuario} : especifica o usuário selecionado
	 * @param tipoGrafico - {@link String} : especifica o tipo de gráfico selecionado
	 * @param janelaPai - {@link Component} : componente sobre o qual mensagens serão exibidas, caso sejam necessárias.
	 */
	public static void acaoBotao(ManipuladorDeDados manipulador, Date dtInicial, Date dtFinal, IndiceUsuario indice, String tipoGrafico, Component janelaPai){
		Data dataInicial = Data.transformaDateEmData(dtInicial), dataFinal = Data.transformaDateEmData(dtFinal);
		
		if(dataInicial.compareTo(dataFinal) ==  1){
			Mensagem.msgErro("A data inicial não pode ser maior que a data final do período!", Constantes.NOME_PROGRAMA + " : Exportar");
			return;
		}
		
		List<DadoExport> dadosExportacao = new ArrayList<DadoExport>();
		
		if(indice.getNomeUser().equals(Constantes.VALOR_TODOS_COMBO_BOX)){
			dadosExportacao.addAll(gerarDadosExportacaoAllUsers(manipulador, dataInicial, dataFinal, tipoGrafico));
		}else{
			DadoExport dado = gerarDadosExportacaoOneUser(manipulador.lerUsuario(indice.getNumeroRegistro()), manipulador, dataInicial, dataFinal, tipoGrafico);
			if(dado != null)
				dadosExportacao.add(dado);
		}
		
		if(GerarPDF.gerarPDFPorDadoExport(dadosExportacao, dataInicial, dataFinal, janelaPai) == 1){
			Mensagem.msgInfo(Constantes.MENSAGEM_EXPORTACAO_CONCLUIDA, Constantes.NOME_PROGRAMA + " : Exportar");
		}else{
			Mensagem.msgErro(Constantes.MENSAGEM_EXPORTACAO_CANCELADA, Constantes.NOME_PROGRAMA + " : Exportar");
		}
	}

	private static DadoExport gerarDadosExportacaoOneUser(Usuario usuario, ManipuladorDeDados manipulador, Data dataInicial, Data dataFinal, String tipoGrafico) {
		List<Exercicio> exercicios = manipulador.lerExerciciosUsuarioDoPeriodo(usuario, dataInicial, dataFinal);
		if(exercicios.isEmpty())
			return null;
		List<JFreeChart> graficosDoUsuario = gerarGraficosPorTipo(exercicios, tipoGrafico);
		return new DadoExport(usuario, graficosDoUsuario);
	}
	
	private static List<JFreeChart>  gerarGraficosPorTipo(List<Exercicio> exercicios, String tipoGrafico){
		List<JFreeChart> graficos = new ArrayList<JFreeChart>();
		
		if(tipoGrafico.equals(Constantes.VALOR_TODOS_COMBO_BOX)){
			graficos.addAll(GerarGraficos.gerarTodosOsGraficosDeUmTipo(null, exercicios, Constantes.TIPO_DE_GRAFICO_1_POR_TIPO, true));
			graficos.addAll(GerarGraficos.gerarTodosOsGraficosDeUmTipo(null, exercicios, Constantes.TIPO_DE_GRAFICO_2_POR_TIPO, true));
			graficos.addAll(GerarGraficos.gerarTodosOsGraficosDeUmTipo(null, exercicios, Constantes.TIPO_DE_GRAFICO_3, false));
		}else{
			graficos.addAll(GerarGraficos.gerarTodosOsGraficosDeUmTipo(null, (ArrayList<Exercicio>) exercicios, tipoGrafico, true));
		}
		return graficos;
	}

	private static List<DadoExport> gerarDadosExportacaoAllUsers(ManipuladorDeDados manipulador, Data dataInicial, Data dataFinal, String tipoGrafico) {
		List<Object> indices = manipulador.lerIndicesUsuarios();
		List<DadoExport> dados = new ArrayList<DadoExport>();
		
		for (Object indice : indices) {
			Usuario usuario = manipulador.lerUsuario(((IndiceUsuario)indice).getNumeroRegistro());
			DadoExport dado = gerarDadosExportacaoOneUser(usuario, manipulador, dataInicial, dataFinal, tipoGrafico);
			if(dado != null){
				dados.add(dado);
			}
		}
		
		return dados;
	}
	
	/**
	 * Classe usada para facilitar a exportação de gráficos gerados pelo programa para arquivos PDF.
	 * 
	 * @author José do Carmo de Melo Silva
	 *
	 */
	public static class DadoExport {
			private Usuario usuario;
			private List<JFreeChart> graficosDoUsuario;
			
			/**
			 * Cria uma nova instância <code>DadoExport</code> a partir dos parâmetros recebidos.
			 * 
			 * @param usuario - {@link Usuario} : usuário a ser armazenado no objeto.
			 * @param graficosDoUsuario - {@link List} <{@link JFreeChart}> : gráficos, correspondentes ao usuário a ser armazendo no objeto, que serão armazenados
			 * no objeto.
			 */
			public DadoExport(Usuario usuario, List<JFreeChart> graficosDoUsuario) {
				this.usuario = usuario;
				this.graficosDoUsuario = graficosDoUsuario;
			}

			/**
			 * Retorna o usuário armazenado no objeto.
			 * 
			 * @return {@link Usuario} : usuário armazenado no objeto.
			 */
			public Usuario getUsuario() {
				return usuario;
			}
			
			/**
			 * Retorna os gráficos, correspondentes ao usuário armazenado no objeto, que estão armazenados no objeto.
			 * 
			 * @return {@link List} <{@link JFreeChart}> : gráficos correspondentes ao usuário armazenado no objeto.
			 */
			public List<JFreeChart> getGraficosDoUsuario() {
				return graficosDoUsuario;
			}
	}
}