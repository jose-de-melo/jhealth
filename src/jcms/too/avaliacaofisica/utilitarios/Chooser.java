package jcms.too.avaliacaofisica.utilitarios;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Fornece métodos para encapsular os serviços da classe {@link JFileChooser}.
 * 
 * 	@author José do Carmo de Melo Silva
 *	@version 0.2
 *	@since 0.3
 */
public class Chooser {

	/**
	 * Cria uma janela para realizar a criação de um arquivo no sistema de arquivos, usando a classe {@link JFileChooser}, de acordo com
	 * os parâmetros recebidos.
	 * 
	 * @param nomeSugeridoArquivo - sugestão para o nome a ser salvo
	 * @param filtro - {@link FileNameExtensionFilter} - define o tipo de arquivo a ser salvo
	 * @param janelaPai - {@link Component} - componente GUI sobre o qual a janela será executada.
	 * @return - {@link String} : caminho completo do arquivo criado
	 */
	public static String salvarArquivo(String nomeSugeridoArquivo, FileNameExtensionFilter filtro, Component janelaPai){
		File novoArquivo = new File(nomeSugeridoArquivo);

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Escolha o arquivo desejado ou crie um novo");
		fileChooser.setFileFilter(filtro);
		fileChooser.setSelectedFile(novoArquivo);

		int opcao = fileChooser.showSaveDialog(null);
		if(opcao == JFileChooser.CANCEL_OPTION)
			return null;

		novoArquivo = fileChooser.getSelectedFile();

		return novoArquivo.getAbsolutePath();
	}

	/**
	 * Cria uma janela para escolher arquivos do sistema de arquivos, usando {@link JFileChooser}
	 * 
	 * @param titulo - {@link String} : título da janela
	 * @param janelaPai - {@link Component} - componente GUI sobre o qual a janela será executada.
	 * @param filtro - {@link FileNameExtensionFilter} - define o tipo de arquivo que pode ser escolhido
	 * @return - {@link String} : caminho completo do(s) arquivo(s) escolhidos.
	 */
	public static ArrayList<String> selecionarArquivos(String titulo, Component janelaPai, FileNameExtensionFilter filtro) {
		JFileChooser selecionaArquivo = new JFileChooser();

		selecionaArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
		selecionaArquivo.setDialogTitle(titulo);

		selecionaArquivo.setFileFilter(filtro);

		selecionaArquivo.setMultiSelectionEnabled(true);

		int opcao = selecionaArquivo.showOpenDialog(janelaPai);

		if(opcao == JFileChooser.CANCEL_OPTION)
			return null;

		File[] files = selecionaArquivo.getSelectedFiles();
		ArrayList<String> nomesArquivos = new ArrayList<String>();

		for (File file : files) {
			nomesArquivos.add(file.getPath());
		}
		return nomesArquivos;
	}
}