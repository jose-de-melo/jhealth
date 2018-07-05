package jcms.too.avaliacaofisica.pdf;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import jcms.too.avaliacaofisica.controller.ExportarController.DadoExport;
import jcms.too.avaliacaofisica.modelo.Data;
import jcms.too.avaliacaofisica.modelo.Usuario;
import jcms.too.avaliacaofisica.utilitarios.Chooser;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.itextpdf.text.pdf.PdfPTable;

/**
 * Classe usada para gerenciar a criação de arquivos PDF com gráficos ou relatórios.
 * 
 * 
 * @since 0.6
 * @author José do Carmo de Melo Silva
 *
 */
public class GerarPDF {
	private static int imagensGeradas = 0;

	/**
	 * Realiza a criação de um arquivo PDF, através da classe {@link ManipuladorPDF}, para exportar o relatório de estatísticas de um usuário.
	 * 
	 * @param usuario - contém os dados do usuário para o relatório
	 * @param dataInicialPeriodo - início do período em que as estatísticas foram coletadas
	 * @param dataFinalPeriodo - fim do período em que as estatísticas foram coletadas
	 * @param janelaPai - componente GUI sobre o qual o {@link JFileChooser} usado pela função será exibido
	 * @param graficos - gráficos com as estatísticas do usuário
	 * @return 1 : criação do PDF e exportação dos dados bem sucedida; 0 : erro na criação do PDF ou na exportação dos dados ou quando o usuário cancelar a
	 * exportação.
	 * 
	 * @see JFileChooser
	 * @see Chooser
	 * 
	 */
	public static int gerarPDFGraficos(Usuario usuario, Data dataInicialPeriodo, Data dataFinalPeriodo, Component janelaPai, JFreeChart... graficos){

		String pathArquivo = getPathArquivo(true, usuario, janelaPai);

		if(verificarCaminhoArquivo(pathArquivo) == 0){
			return 0;
		}

		if(!pathArquivo.contains(".pdf"))
			pathArquivo += ".pdf";

		ManipuladorPDF manipulador = ManipuladorPDF.abrirPDF(pathArquivo);

		manipulador.addTitulo(String.format("Relatório do período : %s - %s", dataInicialPeriodo.toString(), dataFinalPeriodo.toString()));
		manipulador.adicionarLinhas(1);
		adicionarInformacoesUsuarioAoPDF(manipulador, usuario);
		manipulador.addTitulo(" --- Estatísticas --- ");
		manipulador.adicionarLinhas(2);
		adicionarGraficosAoPDF(manipulador, graficos);
		manipulador.fecharPDF();
		apagarImagens();

		return 1;
	}

	/**
	 * Cria um arquivo PDF, através da classe {@link ManipuladorPDF}, para exportar as melhores estatísticas do usuário em uma tabela
	 * informativa.
	 * 
	 * @param usuario - contém os dados do usuário para o relatório
	 * @param celulasRelatorio - valores a serem mapeados para a tabela do arquivo PDF a ser gerado.
	 * @param janelaPai - componente GUI sobre o qual o {@link JFileChooser} usado pela função será exibido
	 * @return 1 : criação do PDF e exportação dos dados bem sucedida; 0 : erro na criação do PDF ou na exportação dos dados ou quando o usuário cancelar a
	 * exportação.
	 * 
	 * @see JFileChooser
	 * @see Chooser
	 * 
	 */
	public static int gerarPDFRelatorio(Usuario usuario, String[] celulasRelatorio, Component janelaPai){
		String pathArquivo = getPathArquivo(false, usuario, janelaPai);

		if(verificarCaminhoArquivo(pathArquivo) == 0){
			return 0;
		}

		if(!pathArquivo.contains(".pdf"))
			pathArquivo += ".pdf";

		ManipuladorPDF manipulador = ManipuladorPDF.abrirPDF(pathArquivo);

		manipulador.addTitulo(String.format("Relatório de Estatísticas",pathArquivo));
		manipulador.addEspaco(10);
		adicionarInformacoesUsuarioAoPDF(manipulador, usuario);
		manipulador.addEspaco(10);
		manipulador.addTitulo(" ---- Estatísticas do Usuário ----");
		manipulador.addEspaco(20);
		adcionarTabelaDeEstatisticas(manipulador, celulasRelatorio);
		manipulador.fecharPDF();

		return 1;

	}

	/**
	 * Realiza a criação de um arquivo PDF, através da classe {@link ManipuladorPDF}, para exportar o relatório de estatísticas de um usuário
	 * ou mais usuários armazenadas em objetos {@link DadoExport}.
	 * 
	 * 
	 * @param dados - {@link List} <{@link DadoExport}> : contém todos os dados a serem exportados para o arquivo PDF.
	 * @param dataInicialPeriodo - início do período em que as estatísticas foram coletadas
	 * @param dataFinalPeriodo - fim do período em que as estatísticas foram coletadas
	 * @param janelaPai - componente GUI sobre o qual o {@link JFileChooser} usado pela função será exibido
	 * @return 1 : criação do PDF e exportação dos dados bem sucedida; 0 : erro na criação do PDF ou na exportação dos dados ou quando o usuário cancelar a
	 * exportação.
	 * 
	 * @see DadoExport
	 * @see JFileChooser
	 * @see Chooser
	 * 
	 */
	public static int gerarPDFPorDadoExport(List<DadoExport> dados,  Data dataInicialPeriodo, Data dataFinalPeriodo, Component janelaPai){
		String pathArquivo = "";

		if(dados.size() == 1){
			pathArquivo = getPathArquivo(true, dados.get(0).getUsuario(), janelaPai);
		}else{
			pathArquivo = getPathArquivo(true, new Usuario("Geral",null, null, null, null, null), janelaPai);
		}


		if(verificarCaminhoArquivo(pathArquivo) == 0){
			return 0;
		}

		if(!pathArquivo.contains(".pdf"))
			pathArquivo += ".pdf";

		ManipuladorPDF manipulador = ManipuladorPDF.abrirPDF(pathArquivo);

		manipulador.addTitulo(String.format("Relatório do período : %s - %s", dataInicialPeriodo.toString(), dataFinalPeriodo.toString()));
		for (DadoExport dadoExport : dados) {
			manipulador.adicionarLinhas(1);
			adicionarInformacoesUsuarioAoPDF(manipulador, dadoExport.getUsuario());
			manipulador.addTitulo(" --- Estatísticas --- ");
			manipulador.adicionarLinhas(1);
			adicionarGraficosAoPDF(manipulador, dadoExport.getGraficosDoUsuario().toArray(new JFreeChart[0]));
			manipulador.adicionarNovaPagina();
		}
		manipulador.fecharPDF();
		apagarImagens();

		return 1;
	}

	private static void adcionarTabelaDeEstatisticas(ManipuladorPDF manipulador, String[] celulasRelatorio) {
		PdfPTable tabela = ManipuladorPDF.criarTabela(3);
		tabela.addCell(ManipuladorPDF.criarCelulaTabela("Estatística", 1, null));
		tabela.addCell(ManipuladorPDF.criarCelulaTabela("Maior Valor", 1, null));
		tabela.addCell(ManipuladorPDF.criarCelulaTabela("Exercício", 1, null));

		for (int indice = 0; indice < celulasRelatorio.length; indice++) {
			tabela.addCell(ManipuladorPDF.criarCelulaTabela(null, 1, celulasRelatorio[indice]));
		}

		manipulador.addTabela(tabela);
	}

	private static int verificarCaminhoArquivo(String pathArquivo){
		if(pathArquivo == null)
			return 0;
		return 1;
	}

	private static String getPathArquivo(boolean relatorioGrafico, Usuario usuario, Component janelaPai){
		if(relatorioGrafico)
			return Chooser.salvarArquivo(String.format("Relatório de Gráficos - %s", usuario.getNome().replaceAll(" ", "")), new FileNameExtensionFilter("PDF(*.pdf)", "pdf"), janelaPai);
		else
			return Chooser.salvarArquivo(String.format("Relatório - %s", usuario.getNome().replaceAll(" ", "")), new FileNameExtensionFilter("PDF(*.pdf)", "pdf"), janelaPai);

	}

	private static void adicionarInformacoesUsuarioAoPDF(ManipuladorPDF manipulador, Usuario usuario){
		manipulador.addTitulo("--- Dados do Usuário ---");
		manipulador.addEspaco(5);
		manipulador.addParagrafo(String.format("Nome : %s", usuario.getNome()), false);
		manipulador.addParagrafo(String.format("Sexo : %s", usuario.getSexo()), false);
		manipulador.addParagrafo(String.format("Data de Nascimento : %s", usuario.getDataNascimento().toString()), false);
		manipulador.addParagrafo(String.format("Altura : %s", usuario.getAltura()), false);
		manipulador.addParagrafo(String.format("Peso : %s", usuario.getPeso()), false);
		manipulador.addParagrafo(String.format("Email : %s", usuario.getEmail()), false);
		manipulador.addEspaco(25);
	}

	private static void adicionarGraficosAoPDF(ManipuladorPDF manipulador, JFreeChart[] graficos){
		File file;
		for (int i = 0; i < graficos.length; i++) {
			file = new File(String.format("grafico%d.jpg", i));
			try {
				ChartUtilities.saveChartAsJPEG(file, graficos[i], 500, 350);
				manipulador.addImagem(file);
				manipulador.adicionarLinhas(5);
				imagensGeradas++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void apagarImagens(){
		File file;
		for (int i = 0; i <= imagensGeradas; i++) {
			file = new File(String.format("grafico%d.jpg", i));
			file.delete();
		}
	}
}