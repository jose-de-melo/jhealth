package jcms.too.avaliacaofisica.utilitarios;

import javax.swing.JComboBox;

import org.jfree.chart.JFreeChart;

import jcms.too.avaliacaofisica.gui.IGGraficos;

/**
 * Armazena todas as constantes usadas na aplicação.
 * 
 * @author José do Carmo de Melo Silva
 */
public class Constantes {
	/*
	 * -------------------------------------------- Constantes usadas nos arquivos  -------------------------------------------- 
	 */
	/**
	 * Representa o número de caracteres usados para representar um tipo do exercício.
	 */
	public static final int TAMANHO_STRING_TIPO_EXERCICIO  = 30;
	/**
	 * Representa o número de caracteres usados para representar um email.
	 */
	public static final int TAMANHO_STRING_EMAIL  = 60;
	/**
	 * Representa o número de caracteres usados para representar uma data.
	 */
	public static final int TAMANHO_STRING_DATA  = 15;
	/**
	 * Representa o número de caracteres usados para representar um tempo (00:00 - 00:00)..
	 */
	public static final int TAMANHO_STRING_TEMPO  = 20;
	/**
	 * Representa o número de caracteres usados para representar uma duração em hora.
	 */
	public static final int TAMANHO_STRING_DURACAO_EM_HORA = 15;
	/**
	 * Representa o número de caracteres usados para representar um ritmo.
	 */
	public static final int TAMANHO_STRING_RITMO = 15;
	/**
	 * Representa o número de caracteres usados para representar um ritmo completo (0 Km: 00'00").
	 */
	public static final int TAMANHO_STRING_RITMO_COMPLETO = 35;
	/**
	 * Representa o número de caracteres usados para representar o nome de um arquivo.
	 */
	public static final int TAMANHO_STRING_NOME_ARQUIVO = 30;
	/**
	 * Representa o número de caracteres usados para representar o nome de um usuário.
	 */
	public static final int TAMANHO_STRING_NOME_USUARIO = 60;
	/**
	 * Representa o número de caracteres usados para representar o sexo de um usuário.
	 */
	public static final int TAMANHO_STRING_SEXO_USUARIO = 15;
	/**
	 * Representa o número de caracteres usados para representar a altura de um usuário.
	 */
	public static final int TAMANHO_STRING_ALTURA_USUARIO = 10;
	/**
	 * Representa o número de caracteres usados para representar o peso de um usuário.
	 */
	public static final int TAMANHO_STRING_PESO_USUARIO = 10;


	/**
	 * Define o nome da aplicação.
	 */
	public static final String NOME_PROGRAMA = "Avaliação Física";
	/**
	 * Representa o nome de um tipo de gráfico
	 */
	public static final String TIPO_DE_GRAFICO_1  = "Gráfico de Colunas";
	/**
	 * Representa o nome de um tipo de gráfico
	 */
	public static final String TIPO_DE_GRAFICO_1_POR_TIPO  = "Gráfico de Colunas - Por Tipo de Exercício";
	/**
	 * Representa o nome de um tipo de gráfico
	 */
	public static final String TIPO_DE_GRAFICO_2 = "Gráfico de Linhas";
	/**
	 * Representa o nome de um tipo de gráfico
	 */
	public static final String TIPO_DE_GRAFICO_2_POR_TIPO  = "Gráfico de Linhas - Por Tipo de Exercício";
	/**
	 * Representa o nome de um tipo de gráfico
	 */
	public static final String TIPO_DE_GRAFICO_3  ="Gráfico de Colunas - Geral";
	/**
	 * Define um valor "todos" para os {@link JComboBox} usados na aplicação.
	 */
	public static final String VALOR_TODOS_COMBO_BOX = "--- Todos ---";
	/**
	 * Especifica o caminho onde estão armazenadas as imagens usadas na aplicação.
	 */
	public static final  String CAMINHO_IMAGENS = "/" + "jcms" + "/" + "too" +"/" + "avaliacaofisica" + "/"+"gui" + "/" + "imagens" + "/";
	/**
	 * Mensagem padrão de exportação bem sucedida.
	 */
	public static final String MENSAGEM_EXPORTACAO_CONCLUIDA = "Exportação concluída com êxito.";
	/**
	 * Mensagem padrão de exportação abortada ou falha.
	 */
	public static final String MENSAGEM_EXPORTACAO_CANCELADA = "Operação cancelada.";
	
	/**
	 * Valor usado para identificar a situação de um arquivo importado pela aplicação.
	 */
	public static final int ARQUIVO_DE_DADOS_INVALIDO_OU_SEM_DADOS_INTEGROS = -1;
	/**
	 * Valor usado para identificar a situação de um arquivo importado pela aplicação.
	 */
	public static final int ARQUIVO_PARCIALMENTE_INTEGRO = 0;
	/**
	 * Valor usado para identificar a situação de um arquivo importado pela aplicação.
	 */
	public static final int ARQUIVO_INTEGRO = 1;

	/**
	 * Posição do gráfico de barras por duração dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_BARRAS_POR_TIPO_EXERCICIO_DURACAO = 0;
	/**
	 * Posição do gráfico de barras por calorias dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_BARRAS_POR_TIPO_EXERCICIO_CALORIAS = 1;
	/**
	 * Posição do gráfico de barras por distância dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_BARRAS_POR_TIPO_EXERCICIO_DISTANCIA = 2;
	/**
	 * Posição do gráfico de barras por passos dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_BARRAS_POR_TIPO_EXERCICIO_PASSOS = 3;
	/**
	 * Posição do gráfico de barras por velocidade dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_BARRAS_POR_TIPO_EXERCICIO_VELOCIDADE = 4;
	/**
	 * Posição do gráfico de barras por ritmo dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_BARRAS_POR_TIPO_EXERCICIO_RITMO = 5;

	
	/**
	 * Posição do gráfico de barras geral por total de passos dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_BARRAS_GERAL_TOTAL_PASSOS = 0;
	/**
	 * Posição do gráfico de barras geral por média de calorias dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_BARRAS_GERAL_MEDIA_CALORIAS = 1;
	/**
	 * Posição do gráfico de barras geral por total de calorias dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_BARRAS_GERAL_TOTAL_CALORIAS = 2;
	/**
	 * Posição do gráfico de barras geral por distância total percorrida dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_BARRAS_GERAL_TOTAL_DISTANCIA = 3;
	/**
	 * Posição do gráfico de barras geral por média de distância percorrida dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_BARRAS_GERAL_MEDIA_DISTANCIA = 4;

	
	/**
	 * Posição do gráfico de linhas por distância percorrida dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_LINHAS_DISTANCIA = 0;
	/**
	 * Posição do gráfico de linhas por calorias dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_LINHAS_CALORIAS = 1;
	/**
	 * Posição do gráfico de linhas por passos dados dentro do <code>ArrayList</code> de {@link JFreeChart} usado na classe {@link IGGraficos}.
	 */
	public static final int POSICAO_GRAFICO_LINHAS_PASSOS = 2;

}
