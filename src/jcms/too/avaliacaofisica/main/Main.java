package jcms.too.avaliacaofisica.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jcms.too.avaliacaofisica.gui.AvaliacaoFisica;
import jcms.too.avaliacaofisica.out.Mensagem;
import jcms.too.avaliacaofisica.utilitarios.Constantes;

/**
 * Classe responsável por iniciar e definir o <i>LookAndFeel</i> do programa . 
 * A aplicação trabalha sobre arquivos textos com dados específicos, afim de realizar análises sobre exercícios praticados por 
 * usuários de um determinado personal trainer.
 * 
 * @author José do Carmo de Melo Silva
 * @see UIManager
 * @see AvaliacaoFisica
 */
public class Main {
	/**
	 * Inicia e define o <i>LookAndFeel</i> da aplicação.
	 * @param args - não utiliza parâmetros de linha de comando.
	 * @throws ClassNotFoundException disparada quando houver algum erro na definição do <code>LookAndFeel</code>
	 * @throws InstantiationException disparada quando houver algum erro na definição do <code>LookAndFeel</code>
	 * @throws IllegalAccessException disparada quando houver algum erro na definição do <code>LookAndFeel</code>
	 * @throws UnsupportedLookAndFeelException disparada quando houver algum erro na definição do <code>LookAndFeel</code>
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			Mensagem.msgErro("Não foi possível alterar o LookAndFeel do programa!", Constantes.NOME_PROGRAMA);
		}
		new AvaliacaoFisica();
	}
}