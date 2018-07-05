package jcms.too.avaliacaofisica.graficos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Fornece métodos para criação de gráficos de barras e de linhas com aparência default da API {@link JFreeChart}
 * 
 * @author José do Carmo de Melo Silva
 *@see JFreeChart
 *@version 0.5
 */
public class FabricaGraficos {
	/**
	 * Cria um gráfico de barras 3D a partir dos parâmetros passados.
	 * 
	 * @param titulo - texto da legenda superior (título)
	 * @param legendaInferior - texto da legenda inferior
	 * @param legendaDaEsquerda - texto da legenda da esquerda.
	 * @param dados - {@link DefaultCategoryDataset} dados a serem plotados no gráfico
	 * @param orientacao - {@link PlotOrientation} orientação das barras no gráfico
	 * @return um {@link JFreeChart} (BarChart3D) com aparência defult da classe.
	 */
	public static JFreeChart criarGraficoBarras3D(String titulo, String legendaInferior, String legendaDaEsquerda, DefaultCategoryDataset dados, PlotOrientation orientacao){
		return ChartFactory.createBarChart3D(titulo, legendaInferior, legendaDaEsquerda, dados, orientacao, true, true, true);
	}
	
	/**
	 * Cria um gráfico de barras a partir dos parâmetros passados.
	 * 
	 * @param titulo - texto da legenda superior (título)
	 * @param legendaInferior - texto da legenda inferior
	 * @param legendaDaEsquerda - texto da legenda da esquerda.
	 * @param dados - {@link DefaultCategoryDataset} dados a serem plotados no gráfico
	 * @param orientacao - {@link PlotOrientation} orientação das barras no gráfico
	 * @return um {@link JFreeChart} (BarChart) com aparência defult da classe.
	 */
	public static JFreeChart criarGraficoBarras(String titulo, String legendaInferior, String legendaDaEsquerda, DefaultCategoryDataset dados, PlotOrientation orientacao){
		return ChartFactory.createBarChart(titulo, legendaInferior, legendaDaEsquerda, dados, orientacao, true, true, true);
	}

	/**
	 * Cria um gráfico de linhas a partir dos parâmetros passados.
	 * 
	 * @param titulo - texto da legenda superior (título)
	 * @param legendaInferior - texto da legenda inferior
	 * @param legendaDaEsquerda - texto da legenda da esquerda.
	 * @param dados - {@link DefaultCategoryDataset} dados a serem plotados no gráfico
	 * @param orientacao - {@link PlotOrientation} orientação das barras no gráfico
	 * @return um {@link JFreeChart} (LineChart) com aparência defult da classe.
	 */
	public static JFreeChart criarGraficoLinha(String titulo, String legendaInferior, String legendaDaEsquerda, DefaultCategoryDataset dados, PlotOrientation orientacao){
		return ChartFactory.createLineChart(titulo, legendaInferior, legendaDaEsquerda, dados, orientacao, true, true, true);
	}
}