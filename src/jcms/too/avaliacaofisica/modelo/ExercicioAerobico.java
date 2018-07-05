package jcms.too.avaliacaofisica.modelo;

import java.util.ArrayList;

/**
 * Classe usada para representar um exercício, com dados de atividades aeróbicas, lido de um arquivo de dados que foi importado.
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.2
 *
 */
public final class ExercicioAerobico extends Exercicio {
	private double velocidadeMedia, velocidadeMaxima;
	private Ritmo ritmoMedio, ritmoMaximo;
	private double menorElevacao, maiorElevacao;
	private ArrayList<Ritmo> ritmosNoExercicio;
	
	/**
	 * Cria uma nova instância <code>ExercicioAerobico</code> com valores default
	 * 
	 */
	public ExercicioAerobico() {
		ritmoMaximo = new Ritmo();
		ritmoMaximo = new Ritmo();
		ritmosNoExercicio = new ArrayList<Ritmo>();
	}

	/**
	 * Retorna a velocidade média na prática do exercício.
	 * 
	 * @return <code><b>double</b></code> : velocidade média na prática do exercício
	 */
	public double getVelocidadeMedia() {
		return velocidadeMedia;
	}

	/**
	 * Define a velocidade média na prática do exercício.
	 * 
	 * @param velocidadeMedia - <code><b>double</b></code> : velocidade média na prática do exercício a ser armazenada
	 */
	public void setVelocidadeMedia(double velocidadeMedia) {
		this.velocidadeMedia = velocidadeMedia;
	}

	/**
	 * Retorna a velocidade máxima na prática do exercício.
	 * 
	 * @return <code><b>double</b></code> : velocidade máxima na prática do exercício
	 */
	public double getVelocidadeMaxima() {
		return velocidadeMaxima;
	}

	/**
	 * Define a velocidade média na prática do exercício.
	 * 
	 * @param velocidadeMaxima - <code><b>double</b></code> : velocidade máxima na prática do exercício a ser armazenada
	 */
	public void setVelocidadeMaxima(double velocidadeMaxima) {
		this.velocidadeMaxima = velocidadeMaxima;
	}

	/**
	 * Retorna o ritmo médio do exercício.
	 * 
	 * @return <code><b>Ritmo</b></code> : ritmo médio do exercício
	 */
	public Ritmo getRitmoMedio() {
		return ritmoMedio;
	}

	/**
	 * Define o ritmo médio do exercício.
	 * 
	 * @param ritmoMedio - <code><b>Ritmo</b></code> : ritmo médio do exercício ser armazenado.
	 */
	public void setRitmoMedio(Ritmo ritmoMedio) {
		this.ritmoMedio = ritmoMedio;
	}

	/**
	 * Retorna o ritmo máximo do exercício.
	 * 
	 * @return <code><b>Ritmo</b></code> : ritmo máximo do exercício
	 */
	public Ritmo getRitmoMaximo() {
		return ritmoMaximo;
	}

	/**
	 * Define o ritmo máximo do exercício.
	 * 
	 * @param ritmoMaximo- <code><b>Ritmo</b></code> : ritmo máximo do exercício ser armazenado.
	 */
	public void setRitmoMaximo(Ritmo ritmoMaximo) {
		this.ritmoMaximo = ritmoMaximo;
	}

	/**
	 * Retorna a menor elevação alcançada no exercício.
	 * 
	 * @return <code><b>double</b></code> : menor elevação alcançada no exercício
	 */
	public double getMenorElevacao() {
		return menorElevacao;
	}

	/**
	 * Define a menor elevação alcançada no exercício.
	 * 
	 * @param menorElevacao - <code><b>double</b></code> : menor elevação alcançada no exercício a ser armazenada
	 */
	public void setMenorElevacao(double menorElevacao) {
		this.menorElevacao = menorElevacao;
	}

	/**
	 * Retorna a maior elevação alcançada no exercício.
	 * 
	 * @return <code><b>double</b></code> : maior elevação alcançada no exercício
	 */
	public double getMaiorElevacao() {
		return maiorElevacao;
	}

	/**
	 * Define a maior elevação alcançada no exercício.
	 * 
	 * @param maiorElevacao - <code><b>double</b></code> : maior elevação alcançada no exercício a ser armazenada
	 */
	public void setMaiorElevacao(double maiorElevacao) {
		this.maiorElevacao = maiorElevacao;
	}
	
	/**
	 * Define os ritmos do exercício.
	 * 
	 * @param ritmosNoExercicio - {@link ArrayList} <{@link Ritmo}> : ritmos do exercício a serem armazenados
	 */
	public void setRitmosNoExercicio(ArrayList<Ritmo> ritmosNoExercicio) {
		this.ritmosNoExercicio = ritmosNoExercicio;
	}

	/**
	 * Retorna os ritmos do exercício.
	 * 
	 * @return ritmosNoExercicio - {@link ArrayList} <{@link Ritmo}> : ritmos do exercício
	 */
	public ArrayList<Ritmo> getRitmosNoExercicio() {
		return ritmosNoExercicio;
	}
}