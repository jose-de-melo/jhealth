package jcms.too.avaliacaofisica.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jcms.too.avaliacaofisica.out.Mensagem;
import jcms.too.avaliacaofisica.utilitarios.Constantes;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Jpeg;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Classe usada para criar e manipular arquivos PDF.
 * 
 * @author José do Carmo de Melo Silva
 * 
 * @see File
 * @see OutputStream
 * @see Document
 */
public class ManipuladorPDF {
	private OutputStream saida;
	private Document pdf;

	private File file;

	private ManipuladorPDF(File file) {
		this.file = file;
		this.pdf = new Document(PageSize.A4);
	}

	/**
	 * Abre um arquivo PDF cujo nome deve ser passado por parâmetro.
	 * Este arquivo PDF pode já ter sido criado anteriormente, se isto acontecer 
	 * ele será sobreescrito.
	 * 
	 * @param file {@link File} com a referência do arquivo a ser criado o domumento PDF.
	 * 
	 * @return Retorna um objeto <code>ManipuladorPDF</code> caso o arquivo seja aberto com sucesso.
	 * Se não, retorna null, que pode indicar que o arquivo está sendo utilizado 
	 * por outro proceso ou apenas ocorreu um erro na abertura do mesmo.
	 */
	public static ManipuladorPDF abrirPDF(File file){
		ManipuladorPDF manipulador = new ManipuladorPDF(file);

		try {
			manipulador.saida = new FileOutputStream(file);
			PdfWriter.getInstance(manipulador.pdf, manipulador.saida);
			manipulador.pdf.open();
			return manipulador;
		} catch (FileNotFoundException e){
			Mensagem.msgErro(e.getMessage(), Constantes.NOME_PROGRAMA + " : PDF");
			return null;
		}catch (DocumentException e) {
			Mensagem.msgErro("Erro ao abrir PDF!", Constantes.NOME_PROGRAMA + " : PDF");
			return null;
		}
	}
	
	/**
	 * Adiciona uma nova página ao arquivo PDF manipulado pelo objeto.
	 */
	public void adicionarNovaPagina(){
		try {
			pdf.add(Chunk.NEXTPAGE);
		} catch (DocumentException e) {
			Mensagem.msgErro("Erro ao adicionar uma nova página ao arquivo!", Constantes.NOME_PROGRAMA + " : PDF");
		}
	}

	/**
	 * Opçao se abrir um PDF com apenas com o nome do arquivo.
	 * 
	 * @param nomeArquivo Nome do arquivo a ser criado.
	 * @return Retorna um objeto <code>ManipuladorPDF</code> caso o arquivo seja 
	 * aberto com sucesso. Se não, retorna null, que pode indicar que o 
	 * arquivo está sendo utilizado por outro proceso ou apenas ocorreu um 
	 * erro na abertura do mesmo.
	 */
	public static ManipuladorPDF abrirPDF(String nomeArquivo){
		return abrirPDF(new File(nomeArquivo));
	}

	/**
	 * Adicina um título centralizado na posição atual do 
	 * arquivo PDF. 
	 * @param texto {@link String} contendo o título.
	 */
	public void addTitulo(String texto){
		Paragraph p = new Paragraph(texto);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		p.getFont().setStyle(Font.BOLD);
		p.getFont().setSize(19);
		try {
			pdf.add(p);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adiciona uma imagem na posição atual do arquivo PDF. A referência do arquivo de imagem deve ser passada como parâmetro.
	 *   
	 * @param file :  {@link File} contendo a referência do
	 * arquivo de imagem.
	 */
	public void addImagem(File file){
		try {
			Jpeg imagem = new Jpeg(file.toURI().toURL());
			imagem.setAlignment(Jpeg.ALIGN_CENTER);
			imagem.setBorder(1);
			pdf.add(imagem);
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}


	/**
	 * Adicina um paragrafo na posição atual do arquivo PDF. 
	 * 
	 * @param paragrafo {@link String} contendo o parágrafo.
	 * @param negrito <code>true</code> para um paragrafo negrito
	 * ou <code>false</code> para um normal.
	 */
	public void addParagrafo(String paragrafo, boolean negrito){
		Paragraph p = new Paragraph(paragrafo);
		p.getFont().setStyle((negrito)?Font.BOLD:Font.NORMAL);
		try {
			pdf.add(p);
		} catch (DocumentException e) {
			e.printStackTrace();
			System.err.println("Erro ao escrever paragrafo.");
		}
	}

	/**
	 * Adicina um espaço na vertical posição atual do 
	 * arquivo PDF. 
	 * @param espaco Tamanho do espaço a ser inserido.
	 */
	public void addEspaco(long espaco){
		Paragraph p = new Paragraph();
		p.setSpacingBefore(espaco);
		try {
			pdf.add(p);
		} catch (DocumentException e) {
			Mensagem.msgErro("Erro ao adicionar espaços no PDF.", Constantes.NOME_PROGRAMA + " : PDF");
		}
	}
	
	/**
	 * Adiciona novas linhas ao pdf manipulado pelo objeto.
	 * 
	 * @param numeroDeLinhas : número de linhas a ser adicionado ao arquivo.
	 */
	public void adicionarLinhas(int numeroDeLinhas){
		for (int i = 0; i < numeroDeLinhas; i++) {
			try {
				pdf.add(Chunk.NEWLINE);
			} catch (DocumentException e) {
				Mensagem.msgErro("Erro ao adicionar linhas ao arquivo PDF", Constantes.NOME_PROGRAMA + " : PDF");
			}
		}
	}

	/**
	 * Cria um objeto {@link PdfPTable} para ser salvo em um documento
	 * PDF posteriormente.
	 * 
	 * @param numColuna Número de colunas da tabela.
	 * @return Retorna um {@link PdfPTable} criado com o numéro de
	 * colunas especificado. 
	 */
	public static PdfPTable criarTabela(int numColuna){
		PdfPTable tabela = new PdfPTable(numColuna);
		tabela.setWidthPercentage(100);
		return tabela;
	}

	/**
	 * Cria um objeto {@link PdfPCell} para ser adicionado em uma tabela
	 * {@link PdfPTable} posteriormente.
	 * 
	 * @param tituloCelula {@link String} contendo o título da célula.
	 * @param colSpan Número de colunas que a célula irá oculpar.
	 * @param conteudo {@link String} contendo o contéudo da célula.
	 * @return Retorna um {@link PdfPCell} criado.
	 */
	public static PdfPCell criarCelulaTabela(String tituloCelula, int colSpan, String conteudo){

		PdfPCell celula = new PdfPCell();
		celula.setColspan(colSpan);

		if(tituloCelula != null){
			Paragraph titulo = new Paragraph(tituloCelula);
			titulo.getFont().setStyle(Font.BOLD);
			titulo.getFont().setSize(15);
			titulo.add("\n");
			celula.addElement(titulo);
		}
		
		if(conteudo != null){
			Paragraph p = new Paragraph(conteudo);
			p.getFont().setSize(11);
			celula.addElement(p);
		}

		return celula;
	}

	/**
	 * Adiciona uma tabela na posição corrente do documento PDF.
	 * @param tabela {@link PdfPTable} a ser salva.
	 */
	public void addTabela(PdfPTable tabela){
		try {
			pdf.add(tabela);
		} catch (DocumentException e) {
			Mensagem.msgErro("Erro ao adicionar tabela no PDF!", Constantes.NOME_PROGRAMA + " : PDF");
		}
	}

	/**
	 * Abre o documento em um programa externo, leitor de PDF padrão do sistema
	 * operacional que está sendo executado. 
	 */
	public void abrirExternamente(){
		try {
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file);
		} catch (IOException e) {
			Mensagem.msgErro("Erro ao abrir o documento pdf externamente!", Constantes.NOME_PROGRAMA + " : PDF");
		}
	}

	/**
	 * Fecha o documento PDF que está sendo manipulado por este objeto.
	 */
	public void fecharPDF(){
		if (pdf != null) {
			pdf.close();
		}
		if (saida != null)
			try {
				saida.close();
			} catch (IOException e) {
				Mensagem.msgErro("Erro ao fechar o arquivo PDF", Constantes.NOME_PROGRAMA + " : PDF");
			}
	}
}