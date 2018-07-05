package jcms.too.avaliacaofisica.graficos;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.util.ShapeUtilities;

/**
 * Fornece métodos para alterar a aparência de gráficos gerados pela classe {@link GerarGraficos}.
 * 
 *@see GerarGraficos
 *@see JFreeChart
 */
public class AparenciaGraficos {
	/**
	 * Cor usada para personalizar gráficos de barras. O valor defult é <code>Color(0, 172, 178)</code>. Pode ser modificada pelo método
	 *  {@link #setCOR_SERIES(Color)}
	 * 
	 * @see #setCOR_SERIES(Color)
	 */
	public static Color COR_SERIES = new Color(0, 172, 178);

	private static Color coresSeries[] = new Color[] { 
		COR_SERIES,
		new Color(255,0,0),
		new Color(0,255,0),
		new Color(210,105,30),
		new Color(75,0,130),
		new Color(255,165,0),  
		new Color(85, 177, 69) ,
		new Color(255,140,0),
		new Color(255,215,0),
		new Color(105,105,105),
		new Color(75,0,130),
		new Color(128,128,128),
		new Color(186,85,211),
		new Color(220,20,60),
		new Color(255,140,0),
		new Color(160,82,45),
		new Color(70,130,180),
		new Color(32,178,170),
		new Color(50,205,50),
		new Color(175,238,238)};

	/**
	 * Orientação padrão dos gráficos do programa. A orientação padrão original é <code>PlotOrientation.VERTICAL</code>, mas pode ser modificada através do método
	 * método {@link #setORIENTACAO_DA_PLOTAGEM(PlotOrientation)}
	 */
	public static PlotOrientation ORIENTACAO_DA_PLOTAGEM = PlotOrientation.VERTICAL;

	/**
	 * Posição da legenda inferior dos gráficos. A posição default é <code>CategoryLabelPositions.UP_45</code>.  Pode ser modificada pelo método
	 * {@link #setPOSICAO_LEGENDA_INFERIOR(CategoryLabelPositions)}.
	 */
	public static CategoryLabelPositions POSICAO_LEGENDA_INFERIOR = CategoryLabelPositions.STANDARD;


	/**
	 * Modifica a aparência padrão de um {@link JFreeChart} de barras (BarChart).
	 * 
	 * @param barChart - gráfico de barras a ser personalizado.
	 * @throws ClassCastException disparada quando o parâmetro passado não é um gráfico de barras (BarChart).
	 */
	public static void definirAparenciaDosGraficosDeBarra(JFreeChart barChart) throws ClassCastException{
		alterarBackgroundGrafico(barChart);
		removerBordaLegenda(barChart);
		posicionarLabelLegendaInferior(barChart);
		alterarFonteLegenda(barChart);
		
		for (int indice = 0; indice < coresSeries.length; indice++) {
			mudarCorSeries(barChart, indice);
		}

		CategoryPlot plot = barChart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setGradientPaintTransformer(null);
		renderer.setBarPainter(new StandardBarPainter());
	}
	
	/**
	 *  Modifica a aparência padrão de um {@link JFreeChart} de linhas (LineChart).
	 *  
	 * @param lineChart - gráfico de linhas a ser personalizado. 
	 * @throws ClassCastException disparada quando o parâmetro passado não é um gráfico de linhas (LineChart).
	 */
	public static void definirAparenciaDosGraficosDeLinhas(JFreeChart lineChart) throws ClassCastException{
		alterarBackgroundGrafico(lineChart);
		posicionarLabelLegendaInferior(lineChart);
		removerBordaLegenda(lineChart);
		alterarFonteLegenda(lineChart);
		
		LineAndShapeRenderer renderer = new LineAndShapeRenderer();
		renderer.setAutoPopulateSeriesShape(false);
		renderer.setBaseShape(ShapeUtilities.createDiamond(4.5F));
		renderer.setDrawOutlines(true);
		lineChart.getCategoryPlot().setRenderer(renderer);
		
		for (int indice = 0; indice < coresSeries.length; indice++) {
			mudarCorSeries(lineChart, indice);
		}
	}

	/**
	 * Modifica o valor de <code>COR_SERIES</code>.
	 * 
	 * @param novaCor - especifica a cor das séries (Ex.: linha ou barra) dos gráficos.
	 * @see Color
	 * @see #COR_SERIES
	 */
	public static void setCOR_SERIES(Color novaCor) {
		COR_SERIES = novaCor;
	}

	/**
	 * Modifica o valor de <code>ORIENTACAO_DA_PLOTAGEM</code>. Valores possíveis : <i>PlotOrientation.<b>VERTICAL</b></i> ou <i>PlotOrientation.<b>HORINZONTAL</b></i>.
	 * 
	 * @param novaOrientacao - a nova orientação dos gráficos  (horizontal or vertical) (null não permitido).
	 * @see PlotOrientation
	 * @see #ORIENTACAO_DA_PLOTAGEM
	 */
	public static void setORIENTACAO_DA_PLOTAGEM(PlotOrientation novaOrientacao) {
		ORIENTACAO_DA_PLOTAGEM = novaOrientacao;
	}

	/**
	 * Modifica o valor de <code>POSICAO_LEGENDA_INFERIOR</code>.
	 * 
	 * @param novaPosicaoLegenda - a nova posição das legendas inferiores dos gráficos.
	 * @see CategoryLabelPositions
	 * @see #POSICAO_LEGENDA_INFERIOR
	 */
	public static void setPOSICAO_LEGENDA_INFERIOR(CategoryLabelPositions novaPosicaoLegenda) {
		POSICAO_LEGENDA_INFERIOR = novaPosicaoLegenda;
	}

	private static void alterarFonteLegenda(JFreeChart chart){
		CategoryAxis domainAxis = ((CategoryPlot) chart.getPlot()).getDomainAxis();
		domainAxis.setCategoryLabelPositionOffset(4);
		domainAxis.setTickLabelPaint(new Color(105,105,105));
		domainAxis.setTickLabelFont(new Font("Verdana", Font.ITALIC, 12));
	}

	private static void removerBordaLegenda(JFreeChart chart){
		chart.getLegend().setFrame(BlockBorder.NONE);
	}

	private static void alterarBackgroundGrafico(JFreeChart chart){
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.gray);
		plot.setRangeGridlinePaint(Color.gray);
		plot.setOutlineVisible(false);
	}

	private static void mudarCorSeries(JFreeChart chart, int serie) {
		chart.getCategoryPlot().getRenderer().setSeriesPaint(serie, coresSeries[serie]);		
	}

	private static void posicionarLabelLegendaInferior(JFreeChart chart) {
		chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(POSICAO_LEGENDA_INFERIOR);
	}
}