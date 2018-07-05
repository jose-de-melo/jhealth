package jcms.too.avaliacaofisica.utilitarios;

import javax.swing.ImageIcon;

/**
 * Fornece um método para redimensionamento de imagens.
 * 
 * @author José do Carmo de Melo Silva
 *
 */
public class RedimensionadorDeImagem {
	/**
	 * Redimensiona a {@link ImageIcon} recebida de acordo com os valor recebidos.
	 * 
	 * @param img - {@link ImageIcon} a ser redimensionada
	 * @param xLargura - tamanho da largura para o ajuste da imagem
	 * @param yAltura - tamanho da altura para o ajuste da imagem
	 */
	public static void redimensionar(ImageIcon img, int xLargura, int yAltura){
		img.setImage(img.getImage().getScaledInstance(xLargura, yAltura, 100));
	}
}