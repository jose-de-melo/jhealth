package jcms.too.avaliacaofisica.modelo;

/**
 * Representa o tempo em que um exercício foi praticado.
 * 
 * @author José do Carmo de Melo Silva
 *
 */
public class Tempo {
	private Hora horaInicio, horaTermino;

	/**
	 * Cria uma nova instância <code>Tempo</code> com valores default
	 * 
	 */
	public Tempo() {
		horaTermino = new Hora();
		horaInicio = new Hora();
	}

	/**
	 * Cria uma nova instância <code>Tempo</code> com os valores recebidos.
	 * 
	 * @param tempoInicial - hora inicial do tempo 
	 * @param tempoFinal - hora final do tempo
	 */
	public Tempo(Hora tempoInicial, Hora tempoFinal) {
		this.horaInicio = tempoInicial;
		this.horaTermino = tempoFinal;
	}

	/**
	 * Cria uma nova instância <code>Tempo</code> através da <code>string</code> recebida por parâmetro.
	 * 
	 * @param tempo - <code>string</code> tempo em texto a ser transformado para um objeto <code>Tempo</code>
	 */
	public Tempo(String tempo) {
		this();
		String[] tempoQuebrado = tempo.split(" - ");
		horaInicio.setMinuto(Integer.parseInt(tempoQuebrado[0].substring(0, tempoQuebrado[0].indexOf(":"))));
		horaInicio.setSegundo(Integer.parseInt(tempoQuebrado[0].substring(tempoQuebrado[0].indexOf(":") +1)));
		
		horaTermino.setMinuto(Integer.parseInt(tempoQuebrado[1].substring(0, tempoQuebrado[1].indexOf(":"))));
		horaTermino.setSegundo(Integer.parseInt(tempoQuebrado[1].substring(tempoQuebrado[1].indexOf(":") +1)));
		
	}

	/**
	 * Retorna a hora inicial do <code>Tempo</code>.
	 * 
	 * @return {@link Hora} : hora inicial do tempo.
	 */
	public Hora getTempoInicial() {
		return horaInicio;
	}

	/**
	 * Define a hora inicial do <code>Tempo</code>.
	 * 
	 * @param tempoInicial - {@link Hora} : hora inicial do tempo a ser armazenado.
	 */
	public void setTempoInicial(Hora tempoInicial) {
		this.horaInicio = tempoInicial;
	}

	/**
	 * Retorna a hora final do <code>Tempo</code>.
	 * 
	 * @return {@link Hora} : hora final do tempo.
	 */
	public Hora getTempoFinal() {
		return horaTermino;
	}

	/**
	 * Define a hora final do <code>Tempo</code>.
	 * 
	 * @param tempoFinal - {@link Hora} : hora final do tempo a ser armazenado.
	 */
	public void setTempoFinal(Hora tempoFinal) {
		this.horaTermino = tempoFinal;
	}
	
	/**
	 * Retorna a representação textual do objeto no formato HoraInical - HoraFinal (HH:MM - HH:MM)
	 * 
	 * @return {@link String} : representação textual do objeto no formato HoraInical - HoraFinal (HH:MM - HH:MM)
	 */
	@Override
	public String toString() {
		return String.format("%02d:%02d - %02d:%02d",horaInicio.getMinuto(), horaInicio.getSegundo(), horaTermino.getMinuto(), horaTermino.getSegundo());
	}
}