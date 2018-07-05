package jcms.too.avaliacaofisica.arquivos;

import jcms.too.avaliacaofisica.modelo.Usuario;

/**
 * Classe usada para representar um registro de um arquivo da classe {@link ArquivoUsuario}
 * 
 * @author José do Carmo de Melo Silva
 *@since 0.2
 *@see ArquivoUsuario
 *
 */
public class IndiceUsuario {
	private String nomeUser, // 60 caracteres = 120bytes
			       emailUser; // 60 caracteres = 120bytes
	
	private int numeroRegistro; // 4 bytes
	
	/**
	 * Cria uma nova instânica IndiceUsuario a partir dos valores passados por parâmetro.
	 * 
	 * @param nomeUser - nome do usuário a ser referenciado 
	 * @param emailUser - email do usuário a ser referenciado 
	 * @param numeroRegistro - número do registro no arquivo onde o objeto referenciado foi gravado.
	 */
	public IndiceUsuario(String nomeUser, String emailUser, int numeroRegistro) {
		this.nomeUser = nomeUser;
		this.emailUser = emailUser;
		this.numeroRegistro = numeroRegistro;
	}

	/**
	 * Retorna o nome do usuário referenciado pelo objeto.
	 * 
	 * @return - {@link String} o nome do usuário.
	 */
	public String getNomeUser() {
		return nomeUser;
	}

	/**
	 * Define o nome do usuário referenciado pelo objeto.
	 * 
	 * @param nomeUser - {@link String} o nome do usuário.
	 */
	public void setNomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
	}

	/**
	 * Retorna o email do usuário referenciado pelo objeto.
	 * 
	 * @return - {@link String} o email do usuário.
	 */
	public String getEmailUser() {
		return emailUser;
	}

	/**
	 * Define o email do usuário referenciado pelo objeto.
	 * 
	 * @param emailUser - {@link String} o email do usuário.
	 */
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	/**
	 * Retorna o número do registro no arquivo onde o objeto {@link Usuario} referenciado foi gravado.
	 * 
	 * @return - <code><b>int</b></code> o número do registro.
	 */
	public int getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * Define o número do registro no arquivo onde o objeto {@link Usuario} referenciado foi gravado.
	 * 
	 * @param numeroRegistro - <code><b>int</b></code> o número do registro.
	 */
	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	/**
	 * Verifica se o objeto faz referência ao o objeto {@link Usuario} passado como parâmetro. 
	 * 
	 * @param usuario - {@link Usuario} a verificar se está sendo referenciado pelo objeto
	 * @return - <code><b>true</b></code> se o objeto fizer referência ao usuário, caso contrário, <code><b>false</b></code>.
	 */
	public boolean verificaUsuario(Usuario usuario){
		return usuario.getEmail().equals(emailUser);
	}
	
	/**
	 * Retorna a representação textual do objeto. Caso o valor de <code>emailUser</code> seja <code><b>null</b></code>, retorna
	 * no formato ("%s", <code>nomeUser</code>), caso não seja, retorna no formato ("%s (%s)", <code>nomeUser</code>, <code>emailUser</code>),
	 * 
	 * 
	 * @return - {@link String} com a representação textual do objeto. 
	 */
	@Override
	public String toString() {
		if(emailUser != null)
			return String.format("%s (%s)", nomeUser, emailUser);
		else
			return String.format("%s", nomeUser);
	}
}