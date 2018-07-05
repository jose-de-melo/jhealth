package jcms.too.avaliacaofisica.modelo;

import jcms.too.avaliacaofisica.utilitarios.Unidades;

/**
 * Classe usada para manipular ritmos de exercícios aeróbicos
 * 
 * @author José do Carmo de Melo Silva
 */
public class Ritmo implements Comparable<Ritmo>{
	private double quilometragem;
	private Hora tempo;
	
	/**
	 * Cria uma nova instância <code>Ritmo</code> com valores default
	 * 
	 */
	public Ritmo() {
		this(0,new Hora());
	}
	
	/**
	 * Cria uma nova instância <code>Ritmo</code> a partir dos valores recebidos.
	 * 
	 * @param quilometragem - quilômetro em que o ritmo foi registrado
	 * @param minutosGastos - {@link Ritmo} : minutos gastos no q
	 */
	public Ritmo(double quilometragem, Hora minutosGastos) {
		this.quilometragem = quilometragem;
		this.tempo = minutosGastos;
	}
	
	/**
	 * Cria uma nova instância <code>Ritmo</code> de acordo com a {@link String} recebida.
	 * 
	 * @param ritmo : <code>string</code> com os valores necessários para criar um objeto {@link Ritmo}. 
	 * Formato aceito : Q Km: MM'SS" (Q = Quilometragem, MM = Minutos e SS = Segundos)
	 */
	public Ritmo(String ritmo){
		this();
		String[] ritmoQuebrado = ritmo.split(" ");
		quilometragem = Double.parseDouble(ritmoQuebrado[0].replace(",", "."));
		
		String tempoGasto = ritmoQuebrado[2];
		tempo.setMinuto(Integer.parseInt(tempoGasto.substring(0, 2)));
		tempo.setSegundo(Integer.parseInt(tempoGasto.substring(3, 5)));
	}
	
	/**
	 * Define o tempo do ritmo.
	 * 
	 * @param ritmo - <code>string</code> : tempo a ser armazenado no objeto. Formato aceito: MM'SS" (MM = Minutos e SS = Segundos)
	 */
	public void setTempoRitmo(String ritmo){
		tempo.setMinuto(Integer.parseInt(ritmo.substring(0, 2)));
		tempo.setSegundo(Integer.parseInt(ritmo.substring(3, 5)));
	}
	
	/**
	 * Retorna a quilometragem do ritmo.
	 * 
	 * @return <code>double</code> : valor da quilometragem do ritmo
	 */
	public double getQuilometragem() {
		return quilometragem;
	}

	/**
	 * Define a quilometragem do ritmo.
	 * 
	 * @param quilometragem - <code>double</code> : valor da quilometragem a ser armazenado
	 */
	public void setQuilometragem(double quilometragem) {
		this.quilometragem = quilometragem;
	}

	/**
	 * Retorna os minutos gastos do ritmo.
	 * 
	 * @return {@link Hora} : minutos gastos do ritmo que podem ser obtidos com o método {@link Hora#toMinutes()}
	 */
	public Hora getMinutosGastos() {
		return tempo;
	}

	/**
	 * Define os minutos gastos do ritmo.
	 * 
	 * @param minutosGastos - {@link Hora} : minutos gastos a serem armazenados.
	 */
	public void setMinutosGastos(Hora minutosGastos) {
		this.tempo = minutosGastos;
	}

	/**
	 * Retorna a representação textual do objeto. Formato Q Km: MM'SS" (Q = Quilometragem, MM = Minutos e SS = Segundos)
	 * 
	 * @return {@link String} - representação textual do objeto
	 */
	@Override
	public String toString() {
		return String.format("%1.2f %s: %02d%s%02d%s", quilometragem, Unidades.DISTANCIA.getUnidade(),tempo.getMinuto(), Unidades.MINUTO.getUnidade(),
							 tempo.getSegundo(), Unidades.SEGUNDO.getUnidade());
	}
	
	/**
	 * Retorna os minutos gastos na quilometragem registrada.
	 * 
	 * @return {@link String} - minutos gastos. Formato MM'SS" (MM = Minutos e SS = Segundos)
	 */
	public String getTempoRitmo(){
		return String.format("%02d%s%02d%s",tempo.getMinuto(), Unidades.MINUTO.getUnidade(),
				 tempo.getSegundo(), Unidades.SEGUNDO.getUnidade());
	}

	/**
	 * Compara o objeto <code>this</code> com o objeto {@link Ritmo} recebido. Usa o valor <code>quilometragem</code> como
	 * critério de comparação.
	 * 
	 * @param ritmo - {@link Ritmo} a ser comparado com <code>this</code>
	 * 
	 * @return <code>int</code> : 0 quando forem iguais, 1 quando o objeto <code>this</code> for maior que o objeto {@link Ritmo} recebido e -1
	 * quando for o contrário.
	 * 
	 */
	@Override
	public int compareTo(Ritmo ritmo) {
		Double quilometragemRitmoThis = getQuilometragem(), quilometragemRitmoRecebido = ritmo.getQuilometragem();
		
		return quilometragemRitmoThis.compareTo(quilometragemRitmoRecebido);
	}
}