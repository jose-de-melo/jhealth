package jcms.too.avaliacaofisica.utilitarios;

import java.net.URL;

/**
 * Fornece o método para se obter a localização de um recurso no sistema.
 */
public class Recurso {
		/**
		 * Obtém a localização de um recurso no sistema de arquivos do sistema operacional. 
		 * 
		 * @param nome - nome do recurso a ser localizado no sistema de arquivos.
		 * 
		 * @return {@link URL} : localização do recurso no sistema
		 */
		public static URL obterLocalizacao(String nome) {
			return Recurso.class.getResource(nome);
		}
}
