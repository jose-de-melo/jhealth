package jcms.too.avaliacaofisica.modelo;

/**
 * Classe usada para representar um exercício lido de um arquivo de dados que foi importado.
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.1
 *
 */
public class Exercicio implements Comparable<Exercicio>{
	private String tipoExercicio;
	private Usuario usuario;
	private Data dataExercicio;
	private Tempo tempo;
	private Hora duracao;
	private double distancia;
	private double caloriasPerdidas;
	private int passosDados;

	/**
	 * Cria uma instância <code>Exercicio</code> com valores default.
	 */
	public Exercicio() { }

	/**
	 * Cria uma instância <code>Exercicio</code> com os valores recebidos como parâmetros.
	 * 
	 * @param tipoExercicio - tipo do exerício
	 * @param data - data em que foi praticado. Formato (DD/MM/AAAA)
	 * @param tempo - hora de inicio e fim da prática do exercício. Formato (HH:MM - HH:MM)
	 * @param duracao - tempo gasto na prática do exercício. Formato (HH:MM:SS)
	 * @param distancia - distância percorrida na prática do exercício
 	 * @param caloriasPerdidas - calorias perdidas na prática do exercício
	 * @param passosDados - passos dados na prática do exercício
	 * @param usuario- usuário que praticou o exerício
	 */
	public Exercicio(String tipoExercicio, Data data, Tempo tempo, Hora duracao, double distancia, double caloriasPerdidas, int passosDados, Usuario usuario) {
		this.tipoExercicio = tipoExercicio;
		this.dataExercicio = data;
		this.tempo = tempo;
		this.duracao = duracao;
		this.distancia = distancia;
		this.caloriasPerdidas = caloriasPerdidas;
		this.passosDados = passosDados;
		this.usuario = usuario;
	}

	/**
	 * Retorna o tipo do exercício
	 * 
	 * @return <code>String</code> : tipo do exercício
	 */
	public String getTipoExercicio() {
		return tipoExercicio;
	}

	/**
	 * Define o tipo do exercício
	 * 
	 * @param tipoExercicio - <code>String</code> : tipo do exercício
	 */
	public void setTipoExercicio(String tipoExercicio) {
		this.tipoExercicio = tipoExercicio;
	}

	/**
	 * Retorna o <code>Usuario</code> que praticou o exercício
	 * 
	 * @return {@link Usuario} que praticou o exercício
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Define o <code>Usuario</code> que praticou o exercício
	 * 
	 * @param usuario : usuário que praticou o exercício a ser armazenado 
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Retorna a data em que o exercício foi praticado.
	 * 
	 * @return <code><b>Data</b></code> : data em que o exercício foi praticado
	 */
	public Data getData() {
		return dataExercicio;
	}

	/**
	 * Define a data em que o exercício foi praticado.
	 * 
	 * @param data : {@link Data} em que o exercício foi praticado a ser armazenada.
	 */
	public void setData(Data data) {
		this.dataExercicio = data;
	}

	/**
	 * Retorna o tempo em que o exercício foi realizado
	 * 
	 * @return {@link Tempo} : tempo em que o exercício foi realizado
	 */
	public Tempo getTempo() {
		return tempo;
	}

	/**
	 * Define o tempo em que o exercício foi realizado
	 * 
	 * 
	 * @param tempo {@link Tempo} : tempo em que o exercício foi realizado a ser armazenado
	 */
	public void setTempo(Tempo tempo) {
		this.tempo = tempo;
	}

	/**
	 * Retorna o tempo gasto (duração) na prática do exercício.
	 * 
	 * @return {@link Hora} : tempo gasto no exercício. 
	 */
	public Hora getDuracao() {
		return duracao;
	}

	/**
	 * Define o tempo gasto (duração) na prática do exercício.
	 * 
	 * @param duracao - {@link Hora} : tempo gasto no exercício a ser armazenado
	 */
	public void setDuracao(Hora duracao) {
		this.duracao = duracao;
	}

	/**
	 * Retorna a distância percorrida na prática do exercício
	 * 
	 * @return <code><b>double</b></code> : distância percorrida na prática do exercício
	 */
	public double getDistancia() {
		return distancia;
	}

	/**
	 * Define a distância percorrida na prática do exercício
	 * 
	 * @param distancia - <code><b>double</b></code> : distância percorrida na prática do exercício a ser armazenada
	 */
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	/**
	 * Retorna as calorias perdidas na prática do exercício
	 * 
	 * @return <code><b>double</b></code> : calorias perdidas na prática do exercício
	 */
	public double getCaloriasPerdidas() {
		return caloriasPerdidas;
	}

	/**
	 * Define as calorias perdidas na prática do exercício
	 * 
	 * @param caloriasPerdidas - <code><b>double</b></code> : calorias perdidas na prática do exercício a ser armazenada
	 */
	public void setCaloriasPerdidas(double caloriasPerdidas) {
		this.caloriasPerdidas = caloriasPerdidas;
	}

	/**
	 * Retorna o número de passos dados na prática do exercício
	 * 
	 * @return <code><b>int</b></code> : passos dados na prática do exercício
	 */
	public int getPassosDados() {
		return passosDados;
	}

	/**
	 * Define o número de passos dados na prática do exercício
	 * 
	 * @param passosDados - <code><b>int</b></code> : passos dados na prática do exercício a ser armazenada
	 */
	public void setPassosDados(int passosDados) {
		this.passosDados = passosDados;
	}

	/**
	 * Retorna a representação textual do objeto. 
	 * 
	 * @return {@link String} : representação textual do objeto. Formato "Tipo do Exercício -- Data em que foi praticado -- Tempo em que foi praticado"
	 */
	@Override
	public String toString() {
		return String .format(tipoExercicio + " -- " + dataExercicio.toString() + " -- " + tempo.toString());
	}

	/**
	 * Compara o objeto <code>this</code> com o objeto {@link Exercicio} recebido por parâmetro. A comparação é feita usando os valores
	 * dos campos <code>email</code> do objeto {@link Usuario} que representa o usuário que praticou o exercício, <code>data</code> e 
	 * <code>tempo</code>.
	 * 
	 * @param exercicio : objeto a ser comparado com <code>this</code>
	 * 
	 * @return 0 quando for exercícios iguais; 1 quando não for.
	 */
	@Override
	public int compareTo(Exercicio exercicio) {
		if(exercicio.usuario.getEmail().compareTo(usuario.getEmail()) == 0 && 
			 exercicio.getData().compareTo(dataExercicio) == 0 && 
			 exercicio.getTempo().toString().compareTo(tempo.toString()) == 0)
			return 0;
		else{
			return 1;
		}
	}
}