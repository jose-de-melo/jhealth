package jcms.too.avaliacaofisica.out;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Component;
/**
 * Define métodos para realizar a saída de dados em uma interface gráfica. 
 * 
 * @author José do Carmo de Melo Silva
 *
 * @version 0.2
 */
public class Mensagem {
	/**
	 * Exibe uma mensagem informativa em uma caixa de diálogo.
	 * 
	 * @param mensagem - texto a ser exibido na caixa de diálogo.
	 * @param titulo - texto a ser exibido na barra de título da caixa de diálogo.
	 */
	public static void msgInfo(String mensagem, String titulo){
		showMessageDialog(null, mensagem, titulo, INFORMATION_MESSAGE);
	}

	/**
	 * Exibe uma mensagem de erro em uma caixa de diálogo.
	 * 
	 * @param mensagem - texto a ser exibido na caixa de diálogo.
	 * @param titulo - texto a ser exibido na barra de título da caixa de diálogo.
	 */
	public static void msgErro(String mensagem, String titulo){
		showMessageDialog(null, mensagem, titulo, ERROR_MESSAGE);
	}

	/**
	 * Exibe uma mensagem informativa em uma caixa de diálogo.
	 * 
	 * @param componente - componente GUI a ser exibido na caixa de diálogo.
	 * @param mensagem - texto a ser exibido na barra de título da caixa de diálogo.
	 */
	public static void msgInfo(Object componente, String mensagem){
		showMessageDialog(null, componente, mensagem, INFORMATION_MESSAGE);
	}
	
	/**
	 * Exibe uma mensagem informativa em uma caixa de diálogo.
	 * 
	 * @param janelaPai - componente GUI sobre o qual a mensagem será exibida.
	 * @param mensagem - texto a ser exibido na barra de título da caixa de diálogo.
	 * @param titulo - texto a ser exibido na barra de título da caixa de diálogo.
	 */
	public static void msgInfo(Component janelaPai,String mensagem,  String titulo){
		showMessageDialog(janelaPai , mensagem, titulo, INFORMATION_MESSAGE);
	}
}