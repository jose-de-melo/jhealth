package jcms.too.avaliacaofisica.modelo;

import java.util.Calendar;
import java.util.Date;


/**
 * Fornece métodos para manipulação e conversão de datas.
 * 
 * @author José do Carmo de Melo Silva
 * @see Calendar
 */
public class Data implements Comparable<Data>{
	private int dia, mes, ano;
	private static Calendar calendar = Calendar.getInstance();

	/**
	 * Cria uma instância <code>Data</code> com a data atual do sistema.
	 * 
	 */
	public Data() {
		this(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
	}

	/**
	 * Cria uma instância <code>Data</code> a partir dos parâmetros recebidos.
	 * 
	 * @param dia - <code>int</code> : valor dia da data
	 * @param mes - <code>int</code> : valor mês da data
	 * @param ano - <code>int</code> : valor ano da data
	 */
	public Data(int dia, int mes, int ano) {
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
	}

	/**
	 * Cria uma instância <code>Data</code> a partir da {@link String} recebida, que deve ter o formato DD/MM/AAAA.
	 * 
	 * @param data - {@link String} : texto com a data que deve ter o formato DD/MM/AAAA
	 */
	public Data(String data) {
		String[] dataQuebrada = data.split("/");

		dia = Integer.parseInt(dataQuebrada[0]);
		mes = Integer.parseInt(dataQuebrada[1]);
		ano = Integer.parseInt(dataQuebrada[2]);
	}

	/**
	 * Retorna o valor dia do objeto
	 * 
	 * @return <code>int</code> : valor dia da data
	 */
	public int getDia() {
		return dia;
	}

	/**
	 * Define o valor dia do objeto
	 * 
	 * @param dia - <code>int</code> : valor dia a ser armazenado no objeto
	 */
	public void setDia(int dia) {
		this.dia = dia;
	}

	
	/**
	 * Retorna o valor mes do objeto
	 * 
	 * @return <code>int</code> : valor mes do objeto
	 */
	public int getMes() {
		return mes;
	}

	/**
	 * Define o valor mes do objeto
	 * 
	 * @param mes - <code>int</code> : valor mes a ser armazenado no objeto
	 */
	public void setMes(int mes) {
		this.mes = mes;
	}

	/**
	 * Retorna o valor ano do objeto
	 * 
	 * @return <code>int</code> : valor ano do objeto
	 */
	public int getAno() {
		return ano;
	}

	/**
	 * Define o valor ano do objeto
	 * 
	 * @param ano -  <code>int</code> : valor ano a ser armazenado no objeto
	 */
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	/**
	 * Retorna uma {@link String} com a data armazenada no objeto no formato DD/MM
	 * 
	 * @return {@link String} : data armazenada no objeto no formato DD/MM
	 */
	public String getDiaMes(){
		return String.format("%02d/%02d", dia, mes);
	}

	/**
	 * Retorna a data, no formato DD/MM/AAA, armazenada no objeto.
	 * 
	 * @return {@link String} : data no formato DD/MM/AAA
	 */
	@Override
	public String toString() {
		return String.format("%02d/%02d/%04d", dia, mes, ano);
	}

	/**
	 * Verifica se o objeto {@link Data} <code>this</code> está dentro do período especificado pelos parâmetros.
	 * 
	 * @param dataInicial - {@link Data} : data inicial do período
	 * @param dataFinal - {@link Data} : data final do período
	 * @return <code><b>true</b></code> : <code>this</code> {@link Data} dentro do período especificado; <code><b>false</b></code> fora do período
	 */
	public boolean dataDentroDoPeriodo(Data dataInicial, Data dataFinal){
		if((compareTo(dataInicial) == 1|| compareTo(dataInicial) == 0)  && (compareTo(dataFinal) == -1 || compareTo(dataFinal) == 0)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Converte o objeto {@link Date} recebido em um objeto {@link Data}.
	 * 
	 * @param date - {@link Date} : data a ser convertida
	 * @return {@link Data} : objeto resultante da conversão
	 */
	public static Data transformaDateEmData(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return new Data(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
	}

	/**
	 * Compara o objeto <code>this</code> com o objeto {@link Data} recebido como parâmetro.
	 * 
	 * 
	 * @param data : objeto a ser comparado com <code>this</code>
	 * 
	 * @return <code>int</code> : 1 quando a data armazenada no objeto for maior que a recebida como parâmetro, -1 quando for menor e 0 quando as datas
	 * forem iguais.
	 * 
	 */
	@Override
	public int compareTo(Data data) {
		long d1, d2;

		d1 = ((getDia()) + (getMes() * 30) + (getAno() * 365));
		d2 = ((data.getDia()) + (data.getMes() * 30) + (data.getAno() * 365));

		if (d1 > d2)
			return 1;
		else if (d1 < d2)
			return -1;
		else
			return 0;
	}
}