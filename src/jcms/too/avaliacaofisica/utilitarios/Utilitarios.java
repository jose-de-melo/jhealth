package jcms.too.avaliacaofisica.utilitarios;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * Fornece métodos que serão usados com frequência pela aplicação.
 * 
 * @author José do Carmo de Melo Silva
 * 	@since 0.5
 */
public class Utilitarios {
	
		/**
		 * Define se os {@link JButton} recebidos estão ou não ativados.
		 * 
		 * @param estado : <code><b>true</b></code> os botões serão ativados, <code><b>false</b></code> os botões serão desativados.
		 * @param botoes : botões a definir se estão ou não ativados.
		 */
		public static void alterarEnabledBotoes(boolean estado, JButton... botoes){
			for (JButton botao : botoes) {
				botao.setEnabled(estado);
			}
		}
		
		/**
		 * Limpa o texto dos {@link JLabel} recebidos.
		 * 
		 * @param labels - {@link JLabel} a serem limpados.
		 */
		public static void limparLabels(JLabel... labels){
			for (JLabel label : labels) {
				label.setText("");
			}
		}
		
		/**
		 * Define se os {@link JButton} recebidos estão ou não visíveis.
		 * 
		 * @param visible : <code><b>true</b></code> - os botões terão sua visibilidade ativada,
		 *  <code><b>false</b></code> os botões terão sua visibilidade desativada.
		 * @param botoes : botões a definir se estão ou não ativados.
		 */
		public static void alterarVisibleBotoes(boolean visible, JButton... botoes){
			for (JButton botao : botoes) {
				botao.setVisible(visible);
			}
		}
		
		/**
		 * Retorna um {@link Icon} com tamanho definido pelos parâmetros.
		 * 
		 * @param nomeImagem - <code>string</code> : caminho completo da imagem a ser usada para criar o {@link Icon}
		 * @param width - tamanho da largura do <code>Icon</code>
		 * @param height - tamanho da altura do <code>Icon</code>
		 * @return {@link Icon} : com a imagem especificada e com o tamanho especificado.
		 */
		public static Icon getIconAjustado(String nomeImagem, int width, int height) {
			ImageIcon icone = new ImageIcon(Recurso.obterLocalizacao(Constantes.CAMINHO_IMAGENS +  nomeImagem));
			RedimensionadorDeImagem.redimensionar(icone, width, height);
			return icone;
		}
		
		/**
		 * Define os itens do {@link JComboBox} recebido como parâmetro.
		 * 
		 * @param comboBox - {@link JComboBox} : componente a ter os itens definidos.
		 * @param objetos - objetos a serem setados no {@link JComboBox}
		 */
		public static void carregarComboBox(JComboBox<Object> comboBox, Object... objetos) {
			for (Object objeto : objetos) {
				comboBox.addItem(objeto);
			}
		}
}