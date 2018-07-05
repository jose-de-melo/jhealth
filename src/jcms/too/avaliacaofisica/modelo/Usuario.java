package jcms.too.avaliacaofisica.modelo;

import java.util.List;

/**
 * Representa um usuário que realizou um dos exercícios importados pela aplicação
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.1
 * @see Data
 *
 */
public class Usuario implements Comparable<Usuario>{

	private String nome, email, sexo, altura, peso;
	private Data dataNascimento;
	
	/**
	 * Cria uma nova instância <code>Usuario</code>.
	 */
	public Usuario() {
	}

	/**
	 * Cria uma nova instância <code>Usuario</code> com os valores recebidos como parâmetro.
	 * 
	 * @param nome - <code>String</code> : nome do usuário
	 * @param email - <code>String</code> : email do usuário
	 * @param sexo - <code>String</code> : sexo do usuário
	 * @param dataNascimento - {@link Data} : data de nascimento do usuário
	 * @param altura - <code>String</code> : altura do usuário
	 * @param peso - <code>String</code> : peso do usuário 
	 */
	public Usuario(String nome, String email, String sexo, Data dataNascimento, String altura, String peso) {
		this.nome = nome;
		this.email = email;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.altura = altura;
		this.peso = peso;
	}

	
	/**
	 * Retorna o nome do usuário.
	 * 
	 * @return  <code>String</code> : nome do usuário
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Define o nome do usuário
	 * 
	 * @param nome - <code>String</code> : nome do usuário a ser armazenado
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorna o email do usuário.
	 * 
	 * @return  <code>String</code> : email do usuário
	 */
	public String getEmail() {
		return email;
	}

	
	/**
	 * Define o email do usuário
	 * 
	 * @param email - <code>String</code> : email do usuário a ser armazenado
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	/**
	 * Retorna o sexo do usuário.
	 * 
	 * @return  <code>String</code> : sexo do usuário
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * Define o sexo do usuário
	 * 
	 * @param sexo - <code>String</code> : sexo do usuário a ser armazenado
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	
	/**
	 * Retorna a data de nascimento do usuário
	 * 
	 * @return {@link Data} : data de nascimento do usuário
	 */
	public Data getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * Define a data de nascimento do usuário
	 * 
	 * @param dataNascimento - {@link Data} : data de nascimento do usuário a ser armazenado
	 */
	public void setDataNascimento(Data dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * Retorna a altura do usuário
	 * 
	 * @return <code>String</code> : altura do usuário
	 */
	public String getAltura() {
		return altura;
	}

	/**
	 * Define a altura do usuário
	 * 
	 * @param altura - <code>String</code> : altura do usuário a ser armazenada
	 */
	public void setAltura(String altura) {
		this.altura = altura;
	}

	
	/**
	 * Retorna o peso do usuário
	 * 
	 * @return <code>String</code> : peso do usuário
	 */
	public String getPeso() {
		return peso;
	}

	/**
	 * Define o peso do usuário
	 * 
	 * @param peso - <code>String</code> : peso do usuário a ser armazenado
	 */
	public void setPeso(String peso) {
		this.peso = peso;
	}

	/**
	 * Compara o objeto <code>this</code> com o objeto {@link Usuario} recebido. Usa o campo <code>nome</code> como referência.
	 * 
	 * ATENÇÃO : esse método é usado apenas para ordenar {@link List} de {@link Usuario} pelo nome, e não foi usado para verificar se 
	 * o usuário armazenado já foi cadastrado no banco de dados da aplicação.
	 * 
	 * @param usuario : objeto a ser comparado ao <code>this</code>
	 * 
	 * @return <code>int</code> : 0 quando o <code>nome</code> dos usuários forem iguais, -1 quando o a representação lexicográfica
	 * do <code>nome</code> do usuário recebido preceder a representação do <code>nome</code> do usuário armazenado no objeto
	 * <code>this</code> e 1 quando for o contrário.
	 */
	@Override
	public int compareTo(Usuario usuario) {
		return nome.compareTo(usuario.nome);
	}
}