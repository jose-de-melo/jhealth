package jcms.too.avaliacaofisica.modelo;

import java.util.Calendar;


/**
 * Fornece métodos para manipulação e conversão de horas.
 * 
 * @author José do Carmo de Melo Silva
 *	@see Calendar
 */
public class Hora implements Comparable<Hora>{
	private int hora, minuto, segundo;
	private static Calendar calendar = Calendar.getInstance();

	/**
	 * Cria uma instância <code>Hora</code> com a hora atual do sistema.
	 * 
	 */
	public Hora() {
		this(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE) + 1, calendar.get(Calendar.SECOND));
	}

	/**
	 * Cria uma instância <code>Hora</code> a partir dos parâmetros recebidos.
	 * 
	 * @param hora - <code>int</code> : valor hora do objeto
	 * @param minuto - <code>int</code> : valor minuto do objeto
	 * @param segundo - <code>int</code> : valor segundo do objeto
	 */
	public Hora(int hora, int minuto, int segundo) {
		this.hora = hora;
		this.minuto = minuto;
		this.segundo = segundo;
	}

	/**
	 * Cria uma instância <code>Hora</code> a partir da {@link String} recebida, que deve ter o formato HH:MM:SS.
	 * 
	 * @param horaStr - {@link String} : texto com a hora que deve ter o formato HH:MM:SS
	 */
	public Hora(String horaStr) {
		String[] horaQuebrada = horaStr.split(":");
		
		hora = Integer.parseInt(horaQuebrada[0]);
		minuto = Integer.parseInt(horaQuebrada[1]);
		segundo = Integer.parseInt(horaQuebrada[2]);		
		
	}

	/**
	 * Retorna o valor hora do objeto <code>this</code>
	 * 
	 * @return <code>int</code> : valor hora do objeto <code>this</code>
	 */
	public int getHora() {
		return hora;
	}

	/**
	 * Define o valor hora do objeto <code>this</code>
	 * 
	 * @param hora - <code>int</code> : valor hora a ser armazenado
	 */
	public void setHora(int hora) {
		this.hora = hora;
	}

	/**
	 * Retorna o valor minuto do objeto <code>this</code>
	 * 
	 * @return <code>int</code> : valor minuto do objeto <code>this</code>
	 */
	public int getMinuto() {
		return minuto;
	}

	/**
	 * Define o valor minuto do objeto <code>this</code>
	 * 
	 * @param minuto - <code>int</code> : valor minuto a ser armazenado 
	 */
	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}

	/**
	 * Retorna o valor segundo do objeto <code>this</code>
	 * 
	 * @return <code>int</code> : valor segundo do objeto <code>this</code>
	 */
	public int getSegundo() {
		return segundo;
	}

	/**
	 * Define o valor segundo do objeto <code>this</code>
	 * 
	 * @param segundo - <code>int</code> : valor segundo a ser armazenado 
	 */
	public void setSegundo(int segundo) {
		this.segundo = segundo;
	}

	/**
	 * Retorna uma <code>String</code> com o valor da hora no formato HH:MM:SS
	 *
	 * @return {@link String} : valor da hora no formato HH:MM:SS
	 */
	@Override
	public String toString() {
		return String.format("%02d:%02d:%02d", hora, minuto, segundo);
	}
	
	/**
	 * Transforma o valor hora armazenado no objeto <code>this</code> em minutos.
	 * 
	 * @return <code><b>double</b></code> : valor em minutos da hora armazenada no objeto <code>this</code>.
	 */
	public double toMinutes(){
		return (hora * 60) + minuto + (segundo /60);
	}

	/**
	 * Compara o objeto <code>this</code> com o objeto {@link Hora} recebido como parâmetro.
	 * 
	 * @param hora : objeto a ser comparado com <code>this</code>
	 * 
	 * @return <code>int</code> : 1 quando a hora armazenada no objeto <code>this</code> for maior que a recebida, -1 quando for menor e 0 quando
	 * forem iguais.
	 * 
	 */
	@Override
	public int compareTo(Hora hora) {
		long horaConvertida = (getHora() * 3600) + (getMinuto()*60) + getSegundo(),
				  horaConvertida2 = (hora.getHora() * 3600 ) + (hora.getMinuto() * 60) + hora.getSegundo();
		
		if(horaConvertida > horaConvertida2)
			return 1;
		else if(horaConvertida < horaConvertida2)
			return -1;
		else
			return 0;
	}
}