package jcms.too.avaliacaofisica.graficos;

import java.util.ArrayList;
import java.util.List;

import jcms.too.avaliacaofisica.dados.ManipuladorDeDados;
import jcms.too.avaliacaofisica.modelo.Data;
import jcms.too.avaliacaofisica.modelo.Exercicio;
import jcms.too.avaliacaofisica.modelo.ExercicioAerobico;
import jcms.too.avaliacaofisica.utilitarios.Constantes;
import jcms.too.avaliacaofisica.utilitarios.Unidades;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Classe usada para gerar os gráficos usados pela aplicação.
 * 
 * @author José do Carmo de Melo Silva
 *	@version 0.5
 *	@since 0.2
 */
public class GerarGraficos {
	private static String tituloGraficosDuracao = "Duração /Dia"; 
	private static  String tituloGraficosCalorias ="Calorias Perdidas /Dia";
	private static  String tituloGraficosDistancia ="Distância Percorrida  /Dia";
	private static String tituloGraficosPassos ="Passos Dados  /Dia";
	private static String tituloGraficosVelocidade ="Velocidade Máxima /Dia";
	private static String tituloGraficosRitmo ="Ritmo Médio /Dia";
	private static String legendaInferior = "Data";

	/**
	 * Gera um gráfico de barras não cumulativo (os valores dos exercícios praticados no dia serão somados e separados por tipo e data) 
	 * a partir dos parâmetros recebidos. Esses dados serão armazenados em objetos {@link ValoresGrafico}.
	 * 
	 * @param tipoExercicio - tipo de exercício a ter os dados plotados no gráfico
	 * @param exercicios - {@link List} <{@link Exercicio}> : contém os exercícios onde os valores para gerar o {@link DefaultCategoryDataset}
	 * usado para gerar o gráfico estão armazenados.
	 * @param duracao - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param calorias - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param velocidade - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param ritmo - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param distancia - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param passos - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @return o {@link JFreeChart} gerado e personalizado pela classe {@link AparenciaGraficos}
	 */
	public static JFreeChart gerarGraficoBarrasPorExercicio(String tipoExercicio, List<Exercicio> exercicios, boolean duracao, boolean calorias, boolean velocidade , boolean ritmo, boolean distancia, boolean passos){
		DefaultCategoryDataset dados = new DefaultCategoryDataset();
		ArrayList<ValoresGrafico> valores = ValoresGrafico.transformarDados(exercicios, duracao, calorias, velocidade, ritmo, distancia, passos, false);
		
		tipoExercicio = "Tipo de Exercício - " + tipoExercicio + " : ";

		for (ValoresGrafico valor : valores) {
			if(distancia)
				dados.setValue(valor.getValor(),Unidades.DISTANCIA.getNome(), valor.getData().getDiaMes());
			if(duracao)
				dados.setValue(valor.getValor(), "Duração", valor.getData().getDiaMes());
			if(velocidade)
				dados.setValue(valor.getValor() / valor.numeroDeExercicios, Unidades.VELOCIDADE.getNome(), valor.getData().getDiaMes());
			if(calorias)
				dados.setValue(valor.getValor(), Unidades.CALORIA.getNome(), valor.getData().getDiaMes());
			if(passos)
				dados.setValue(valor.getValor(), Unidades.PASSOS.getNome(),  valor.getData().getDiaMes());
			if(ritmo)
				dados.setValue(valor.getValor() / valor.numeroDeExercicios,Unidades.RITMO.getNome(), valor.getData().getDiaMes());
		}

		String tituloGrafico = "", legendaEsquerda = "" ;
		if(duracao){
			tituloGrafico = tipoExercicio + tituloGraficosDuracao;
			legendaEsquerda = "Minutos";
		}
		if(calorias){
			tituloGrafico = tipoExercicio + tituloGraficosCalorias;
			legendaEsquerda = Unidades.CALORIA.getUnidade();
		}
		if(passos){
			tituloGrafico = tipoExercicio + tituloGraficosPassos;
			legendaEsquerda = Unidades.PASSOS.getUnidade();
		}
		if(distancia){
			tituloGrafico = tipoExercicio + tituloGraficosDistancia;
			legendaEsquerda = Unidades.DISTANCIA.getUnidade();
		}
		if(velocidade){
			tituloGrafico = tipoExercicio  + tituloGraficosVelocidade;
			legendaEsquerda = Unidades.VELOCIDADE.getUnidade();
		}
		if(ritmo){
			tituloGrafico = tipoExercicio  + tituloGraficosRitmo;
			legendaEsquerda = "Minutos/Km";
		}

		tituloGrafico = adicionarAnosDoPeriodoAoTitulo(tituloGrafico, exercicios.get(0).getData(), exercicios.get(exercicios.size() - 1).getData());
		JFreeChart grafico =  FabricaGraficos.criarGraficoBarras(tituloGrafico, legendaInferior, legendaEsquerda, dados, AparenciaGraficos.ORIENTACAO_DA_PLOTAGEM);
		AparenciaGraficos.definirAparenciaDosGraficosDeBarra(grafico);

		return grafico;
	}

	/**
	 * Gera um gráfico de barras cumulativo (todos os valores dos exercícios praticados no dia são somados) a partir dos
	 * parâmetros recebidos. Esses dados serão armazenados em objetos {@link ValoresGrafico}.
	 * 
	 * @param exercicios - {@link List} <{@link Exercicio}> : contém os exercícios onde os valores para gerar o {@link DefaultCategoryDataset}
	 * usado para gerar o gráfico estão armazenados.
	 * @param duracao - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param calorias - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param velocidade - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param ritmo - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param distancia - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param passos - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @return o {@link JFreeChart} gerado e personalizado pela classe {@link AparenciaGraficos}
	 */
	public static JFreeChart gerarGraficoBarrasCumulativo(List<Exercicio> exercicios, boolean duracao, boolean calorias, boolean velocidade , boolean ritmo, boolean distancia, boolean passos){
		DefaultCategoryDataset dados = new DefaultCategoryDataset();
		ArrayList<ValoresGrafico> valores = ValoresGrafico.transformarDados(exercicios, duracao, calorias, velocidade, ritmo, distancia, passos, true);

		for (ValoresGrafico valor : valores) {
			if(distancia)
				dados.setValue(valor.getValor(), valor.tipoExercicio, valor.getData().getDiaMes());
			if(duracao)
				dados.setValue(valor.getValor(), valor.tipoExercicio, valor.getData().getDiaMes());
			if(velocidade)
				dados.setValue(valor.getValor() / valor.getNumeroDeExercicios(), valor.tipoExercicio, valor.getData().getDiaMes());
			if(calorias)
				dados.setValue(valor.getValor(), valor.tipoExercicio, valor.getData().getDiaMes());
			if(passos)
				dados.setValue(valor.getValor(), valor.tipoExercicio, valor.getData().getDiaMes());
			if(ritmo)
				dados.setValue(valor.getValor() / valor.numeroDeExercicios, valor.tipoExercicio, valor.getData().getDiaMes());
		}

		String tituloGrafico = "", legendaEsquerda = "" ;
		if(duracao){
			tituloGrafico =  tituloGraficosDuracao;
			legendaEsquerda = "Minutos";
		}
		if(calorias){
			tituloGrafico =  tituloGraficosCalorias;
			legendaEsquerda = Unidades.CALORIA.getUnidade();
		}
		if(passos){
			tituloGrafico = tituloGraficosPassos;
			legendaEsquerda = Unidades.PASSOS.getUnidade();
		}
		if(distancia){
			tituloGrafico =  tituloGraficosDistancia;
			legendaEsquerda = Unidades.DISTANCIA.getUnidade();
		}
		if(velocidade){
			tituloGrafico = tituloGraficosVelocidade;
			legendaEsquerda = Unidades.VELOCIDADE.getUnidade();
		}
		if(ritmo){
			tituloGrafico =tituloGraficosRitmo;
			legendaEsquerda = "Minutos/Km";
		}

		tituloGrafico = adicionarAnosDoPeriodoAoTitulo(tituloGrafico, exercicios.get(0).getData(), exercicios.get(exercicios.size() - 1).getData());
		JFreeChart grafico =  FabricaGraficos.criarGraficoBarras3D(tituloGrafico, legendaInferior, legendaEsquerda, dados, AparenciaGraficos.ORIENTACAO_DA_PLOTAGEM);
		substituirCamposNulos(dados);
		AparenciaGraficos.definirAparenciaDosGraficosDeBarra(grafico);

		return grafico;
	}

	/**
	 * Gera um gráfico de barras a partir dos parâmetros recebidos. Os dados dos exercícios do praticados no mesmo dia serão somados,
	 * e quando <code><b>mediaCalorias</b></code> ou <code><b>mediaDistancia</b></code> for igual a <code>true</code>, será calculada a média
	 * dos dados, dividindo o valor total pelo número de exercícios praticados no dia. Esses dados serão armazenados em um objeto 
	 * 
	 * @param tipoExercicio - tipo de exercício a ter os dados plotados no gráfico
	 * @param exercicios - {@link List}<{@link Exercicio}> : contém os exercícios que contém os valores para gerar o {@link DefaultCategoryDataset}
	 * usado para gerar o gráfico.
	 * @param duracao - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param calorias - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param velocidade - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param ritmo - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param distancia - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param passos - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @return o {@link JFreeChart} gerado e personalizado pela classe {@link AparenciaGraficos}
	 */
	
	/**
	 * Gera um gráfico de barras geral a partir dos parâmetros recebidos. Os dados dos exercícios do praticados no mesmo dia serão somados,
	 * e quando <code><b>mediaCalorias</b></code> ou <code><b>mediaDistancia</b></code> for igual a <code>true</code>, será calculada a média
	 * dos dados, dividindo o valor total pelo número de exercícios praticados no dia. Esses dados serão armazenados em objetos {@link ValoresGrafico}.
	 * 
	 * @param exercicios  - {@link List} <{@link Exercicio}> : contém os exercícios onde os valores para gerar o {@link DefaultCategoryDataset}
	 * usado para gerar o gráfico estão armazenados.
	 * @param totalPassos - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param mediaCalorias - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param totalCalorias - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param totalDistancia - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param mediaDistancia - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @return o {@link JFreeChart} gerado e personalizado pela classe {@link AparenciaGraficos}
	 */
	public static JFreeChart gerarGraficoDeBarrasGeral(List<Exercicio> exercicios, boolean totalPassos, boolean mediaCalorias, boolean totalCalorias,
			boolean totalDistancia, boolean mediaDistancia)
	{
		DefaultCategoryDataset dados = new DefaultCategoryDataset();

		ArrayList<ValoresGrafico> valores = ValoresGrafico.transformarDados(exercicios, false, totalCalorias || mediaCalorias, false, false, mediaDistancia || totalDistancia, totalPassos, false);

		for (ValoresGrafico valor : valores) {
			if(totalDistancia)
				dados.setValue(valor.getValor(), Unidades.DISTANCIA.getUnidade(), valor.getData().getDiaMes());
			if(mediaDistancia)
				dados.setValue(valor.getValor() / valor.getNumeroDeExercicios(), Unidades.DISTANCIA.getUnidade(), valor.getData().getDiaMes());
			if(totalCalorias)
				dados.setValue(valor.getValor(), Unidades.CALORIA.getUnidade(), valor.getData().getDiaMes());
			if(mediaCalorias)
				dados.setValue(valor.getValor() / valor.getNumeroDeExercicios(), Unidades.CALORIA.getUnidade(), valor.getData().getDiaMes());
			if(totalPassos)
				dados.setValue(valor.getValor(), Unidades.PASSOS.getUnidade(), valor.getData().getDiaMes());
		}
		
		String tituloGrafico = "", legendaEsquerda = "";
		if(totalDistancia){
			tituloGrafico = "Distância Total /Dia";
			legendaEsquerda = Unidades.DISTANCIA.getUnidade();
		}
		if(mediaDistancia){
			tituloGrafico = "Distância Média /Dia";
			legendaEsquerda = Unidades.DISTANCIA.getUnidade();
		}
		if(totalCalorias){
			tituloGrafico = "Total de Calorias Perdidas  /Dia";
			legendaEsquerda = Unidades.CALORIA.getUnidade();
		}
		if(mediaCalorias){
			tituloGrafico = "Média de Calorias Perdidas  /Dia";
			legendaEsquerda = Unidades.CALORIA.getUnidade();
		}
		if(totalPassos){
			tituloGrafico = "Total de Passos Dados  /Dia";
			legendaEsquerda = Unidades.PASSOS.getUnidade();
		}

		tituloGrafico = adicionarAnosDoPeriodoAoTitulo(tituloGrafico, exercicios.get(0).getData(), exercicios.get(exercicios.size() - 1).getData());
		JFreeChart grafico = FabricaGraficos.criarGraficoBarras(tituloGrafico, legendaInferior,legendaEsquerda, dados, AparenciaGraficos.ORIENTACAO_DA_PLOTAGEM);
		AparenciaGraficos.definirAparenciaDosGraficosDeBarra(grafico);

		return grafico;
	}
	

	/**
	 * Gera um gráfico de linhas cumulativo (todos os valores dos exercícios praticados no dia são somados) a partir dos
	 * parâmetros recebidos. Esses dados serão armazenados em objetos {@link ValoresGrafico}.
	 * 
	 * @param exercicios  - {@link List} <{@link Exercicio}> : contém os exercícios onde os valores para gerar o {@link DefaultCategoryDataset}
	 * usado para gerar o gráfico estão armazenados.
	 * @param distancia - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param calorias - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param passos - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @return o {@link JFreeChart} (LineChart) gerado e personalizado pela classe {@link AparenciaGraficos}
	 */
	public static JFreeChart gerarGraficoDeLinhasCumulativo(List<Exercicio> exercicios, boolean distancia, boolean calorias, boolean passos){
		DefaultCategoryDataset dados = new DefaultCategoryDataset();
		ArrayList<ValoresGrafico> valores = ValoresGrafico.transformarDados(exercicios, false, calorias, false, false, distancia, passos, true);

		for (ValoresGrafico valor : valores) {
			if(distancia)
				dados.setValue(valor.getValor(), valor.tipoExercicio, valor.getData().getDiaMes());
			if(calorias)
				dados.setValue(valor.getValor(), valor.tipoExercicio, valor.getData().getDiaMes());
			if(passos)
				dados.setValue(valor.getValor(), valor.tipoExercicio, valor.getData().getDiaMes());
		}
		
		String tituloGrafico = "", legendaEsquerda = "";
		if(calorias){
			tituloGrafico = tituloGraficosCalorias;
			legendaEsquerda = Unidades.CALORIA.getUnidade();
		}
		if(passos){
			tituloGrafico = tituloGraficosPassos;
			legendaEsquerda = Unidades.PASSOS.getUnidade();
		}
		if(distancia){
			tituloGrafico = tituloGraficosDistancia;
			legendaEsquerda = Unidades.DISTANCIA.getUnidade();
		}
		
		tituloGrafico = adicionarAnosDoPeriodoAoTitulo(tituloGrafico, exercicios.get(0).getData(), exercicios.get(exercicios.size() - 1).getData());
		JFreeChart grafico  = FabricaGraficos.criarGraficoLinha(tituloGrafico, legendaInferior, legendaEsquerda, dados,AparenciaGraficos.ORIENTACAO_DA_PLOTAGEM);
		substituirCamposNulos(dados);
		AparenciaGraficos.definirAparenciaDosGraficosDeLinhas(grafico);
		
		return grafico;
	}
	
	/**
	 * Gera um gráfico de linha não cumulativo (os valores dos exercícios praticados no dia serão somados e separados por tipo e data) 
	 * a partir dos parâmetros recebidos. Esses dados serão armazenados em objetos {@link ValoresGrafico}.
	 * 
	 * @param tipoExercicio - tipo de exercício a ter os dados plotados no gráfico
	 * @param exercicios  - {@link List} <{@link Exercicio}> : contém os exercícios onde os valores para gerar o {@link DefaultCategoryDataset}
	 * usado para gerar o gráfico estão armazenados.
	 * @param distancia - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param calorias - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @param passos - define o valor do {@link Exercicio} a ser usado no gráfico.
	 * @return o {@link JFreeChart} (LineChart) gerado e personalizado pela classe {@link AparenciaGraficos}
	 */
	public static JFreeChart gerarGraficoDeLinhas(String tipoExercicio, List<Exercicio> exercicios, boolean distancia, boolean calorias, boolean passos){
		String promptTipoExercicio = "Tipo de Exercício - " + tipoExercicio + " : ";
		DefaultCategoryDataset dados = new DefaultCategoryDataset();
		ArrayList<ValoresGrafico> valores = ValoresGrafico.transformarDados(exercicios, false, calorias, false, false, distancia, passos, false);

		for (ValoresGrafico valor : valores) {
			if(distancia)
				dados.setValue(valor.getValor(), Unidades.DISTANCIA.getUnidade(), valor.getData().toString());
			if(calorias)
				dados.setValue(valor.getValor(), Unidades.CALORIA.getUnidade(), valor.getData().toString());
			if(passos)
				dados.setValue(valor.getValor(), Unidades.PASSOS.getUnidade(), valor.getData().toString());
		}
		
		String tituloGrafico = "", legendaEsquerda = "";
		if(calorias){
			tituloGrafico = promptTipoExercicio + tituloGraficosCalorias;
			legendaEsquerda = Unidades.CALORIA.getUnidade();
		}
		if(passos){
			tituloGrafico = promptTipoExercicio + tituloGraficosPassos;
			legendaEsquerda = Unidades.PASSOS.getUnidade();
		}
		if(distancia){
			tituloGrafico = promptTipoExercicio + tituloGraficosDistancia;
			legendaEsquerda = Unidades.DISTANCIA.getUnidade();
		}
		
		tituloGrafico = adicionarAnosDoPeriodoAoTitulo(tituloGrafico, exercicios.get(0).getData(), exercicios.get(exercicios.size() - 1).getData());
		JFreeChart grafico = FabricaGraficos.criarGraficoLinha(tituloGrafico,  legendaInferior, legendaEsquerda, dados,AparenciaGraficos.ORIENTACAO_DA_PLOTAGEM);
		substituirCamposNulos(dados);
		AparenciaGraficos.definirAparenciaDosGraficosDeLinhas(grafico);
		return grafico;
	}

	/**
	 * Gera todos os gráficos possíveis da classe.
	 * 
	 * @param tipoExercicio - tipo de exercício a ter os dados plotados no gráfico, pode ser <code>true</code>
	 * @param exercicios  - {@link List} <{@link Exercicio}> : contém os exercícios, onde os valores para gerar os {@link DefaultCategoryDataset}
	 * usados para gerar os gráficos, estão armazenados.
	 * @param tipoDeGrafico - tipo dos gráficos a serem gerados. Valores possíves:<code>Constantes.TIPO_DE_GRAFICO_1_POR_TIPO</code>,
	 * 																															   <code>Constantes.TIPO_DE_GRAFICO_2_POR_TIPO</code>,
	 * 																																<code>Constantes.TIPO_DE_GRAFICO_3</code>
	 * @param cumulativo - define se os gráficos serão ou não cumulativos (todos os valores dos exercícios praticados no dia são somados).
	 * @return {@link List} <{@link JFreeChart}> - os gráficos já personalizados gerados pela função
	 * 
	 * @see #gerarGraficoBarrasCumulativo(List, boolean, boolean, boolean, boolean, boolean, boolean)
	 * @see #gerarGraficoBarrasPorExercicio(String, List, boolean, boolean, boolean, boolean, boolean, boolean)
	 * @see #gerarGraficoDeBarrasGeral(List, boolean, boolean, boolean, boolean, boolean)
	 * @see #gerarGraficoDeLinhas(String, List, boolean, boolean, boolean)
	 * @see #gerarGraficoDeLinhasCumulativo(List, boolean, boolean, boolean)
	 * @see Constantes
	 * @see JFreeChart
	 * @see AparenciaGraficos
	 */
	public static List<JFreeChart> gerarTodosOsGraficosDeUmTipo(String tipoExercicio, List<Exercicio> exercicios, String tipoDeGrafico, boolean cumulativo){
		if(cumulativo) {
			return gerarGraficosCumulativos(exercicios, tipoDeGrafico);
		}
		else {
			return gerarGraficosNaoCumulativos(tipoExercicio, exercicios, tipoDeGrafico);
		}
	}
	
	private static List<JFreeChart> gerarGraficosNaoCumulativos(String tipoExercicio, List<Exercicio> exercicios, String tipoDeGrafico){
		List<JFreeChart> graficos = new ArrayList<JFreeChart>();
		if(tipoDeGrafico.compareTo(Constantes.TIPO_DE_GRAFICO_1_POR_TIPO) == 0){
			graficos.add(gerarGraficoBarrasPorExercicio(tipoExercicio, exercicios, true, false, false, false, false, false));
			graficos.add(gerarGraficoBarrasPorExercicio(tipoExercicio, exercicios, false, true, false, false, false, false));
			graficos.add(gerarGraficoBarrasPorExercicio(tipoExercicio, exercicios, false, false, false, false, true, false));
			graficos.add(gerarGraficoBarrasPorExercicio(tipoExercicio, exercicios, false, false, false, false, false, true));
			if(ManipuladorDeDados.isListOfExerciciosAerobicos(exercicios)){
				graficos.add(gerarGraficoBarrasPorExercicio(tipoExercicio, exercicios, false, false, true, false, false, false));
				graficos.add(gerarGraficoBarrasPorExercicio(tipoExercicio, exercicios, false, false, false, true, false, false));
			}
		}else if(tipoDeGrafico.compareTo(Constantes.TIPO_DE_GRAFICO_2_POR_TIPO) == 0){
			graficos.add(gerarGraficoDeLinhas(tipoExercicio, exercicios, true, false, false));
			graficos.add(gerarGraficoDeLinhas(tipoExercicio, exercicios, false, true, false));
			graficos.add(gerarGraficoDeLinhas(tipoExercicio, exercicios, false, false, true));

		}else{
			graficos.add(gerarGraficoDeBarrasGeral(exercicios, true, false, false, false, false));
			graficos.add(gerarGraficoDeBarrasGeral(exercicios, false, true, false, false, false));
			graficos.add(gerarGraficoDeBarrasGeral(exercicios, false, false, true, false, false));
			graficos.add(gerarGraficoDeBarrasGeral(exercicios, false, false, false, true, false));
			graficos.add(gerarGraficoDeBarrasGeral(exercicios, false, false, false, false, true));
		}
		return graficos;
	}

	private static List<JFreeChart> gerarGraficosCumulativos(List<Exercicio> exercicios, String tipoDeGrafico){
		List<JFreeChart> graficos = new ArrayList<JFreeChart>();

		if(tipoDeGrafico.compareTo(Constantes.TIPO_DE_GRAFICO_1_POR_TIPO) == 0){
			graficos.add(gerarGraficoBarrasCumulativo(exercicios, true, false, false, false, false, false));
			graficos.add(gerarGraficoBarrasCumulativo(exercicios, false, true, false, false, false, false));
			graficos.add(gerarGraficoBarrasCumulativo(exercicios, false, false, false, false, true, false));
			graficos.add(gerarGraficoBarrasCumulativo(exercicios, false, false, false, false, false, true));
			if(ManipuladorDeDados.isListOfExerciciosAerobicos(exercicios)){
				graficos.add(gerarGraficoBarrasCumulativo(exercicios, false, false, true, false, false, false));
				graficos.add(gerarGraficoBarrasCumulativo(exercicios, false, false, false, true, false, false));
			}
		}else if(tipoDeGrafico.compareTo(Constantes.TIPO_DE_GRAFICO_2_POR_TIPO) == 0){
			graficos.add(gerarGraficoDeLinhasCumulativo(exercicios, true, false, false));
			graficos.add(gerarGraficoDeLinhasCumulativo(exercicios, false, true, false));
			graficos.add(gerarGraficoDeLinhasCumulativo(exercicios, false, false, true));
		}

		return graficos;
	}
	
	private static String adicionarAnosDoPeriodoAoTitulo(String titulo, Data primeiraData, Data ultimaData){
		int anoPrimeiroExercico = primeiraData.getAno(), anoUltimoExercicio  = ultimaData.getAno();
		
		if(anoPrimeiroExercico == anoUltimoExercicio)
			return titulo += " - " + anoPrimeiroExercico;
		else
			return titulo += " : " + anoPrimeiroExercico + " - " + anoUltimoExercicio;
	}

	@SuppressWarnings("unchecked")
	private static void substituirCamposNulos(DefaultCategoryDataset dados) {
		List<Comparable<?>> dadosColunas = dados.getColumnKeys();
		List<Comparable<?>> dadosLinhas = dados.getRowKeys();

		for (Comparable<?> linha : dadosLinhas) {
			for (Comparable<?> coluna : dadosColunas) {
				Number valor = dados.getValue(linha, coluna);
				if(valor == null)
					dados.addValue(0, linha, coluna);
			}
		}
	}

	/**
	 * Fornece métodos para facilitar o mapeamento de dados para um {@link DefaultCategoryDataset}
	 * 
	 * @author José do Carmo de Melo Silva
	 * 
	 * @version 0.2
	 */
	public static class ValoresGrafico {
		private double valor;
		private Data data;
		private String tipoExercicio;
		private int numeroDeExercicios;


		/**
		 * Cria uma nova instância <code>ValoresGrafico</code> de acordo com os parâmetros recebidos.
		 * 
		 * @param valor - <code>double</code> a ser armazenado
		 * @param data - {@link Data} data em que o exercício, do qual o <code>valor</code> armazenado foi obtido, foi praticado.
		 * @param tipoExercicio -  {@link String} tipo do exercício, do qual o <code>valor</code> armazenado foi obtido.
		 */
		public ValoresGrafico(double valor, Data data, String tipoExercicio) {
			this.valor = valor;
			this.data = data;
			this.tipoExercicio = tipoExercicio;
			numeroDeExercicios = 1;
		}

		/**
		 * Retorna o valor armazenado no objeto.
		 * 
		 * @return <code>double</code> - com o valor armazenado no objeto.
		 */
		public double getValor() {
			return valor;
		}

		/**
		 * Define o valor a ser armazenado no objeto.
		 * 
		 * @param valor : <code>double</code> - valor  a ser armazenado no objeto.
		 */
		public void setValor(double valor) {
			this.valor = valor;
		}

		/**
		 * Retorna a data em que o ou os exercícios, que tiveram seus valores armazenados no objeto, foram praticados.
		 * 
		 * @return {@link Data} - data em que o ou os exercícios, que tiveram seus valores armazenados no objeto, foram praticados.
		 */
		public Data getData() {
			return data;
		}

		/**
		 * Define a data em que o ou os exercícios, que tiveram seus valores armazenados no objeto, foram praticados.
		 * 
		 * @param data : {@link Data} que o ou os exercícios, que tiveram seus valores armazenados no objeto, foram praticados a ser armazenada no objeto.
		 */
		public void setData(Data data) {
			this.data = data;
		}

		/**
		 * Retorna o número de exercícios que tiveram um valor armazenado no objeto. 
		 * 
		 * @return <code>int</code> : número de exercícios que tiveram um valor armazenado no objeto. 
		 */
		public int getNumeroDeExercicios() {
			return numeroDeExercicios;
		}

		/**
		 * Define o número de exercícios que tiveram um valor armazenado no objeto. 
		 * 
		 * @param numeroDeExercicios : novo número de exercícios que a um valor a ser armazenado no objeto. 
		 */
		public void setNumeroDeExercicios(int numeroDeExercicios) {
			this.numeroDeExercicios = numeroDeExercicios;
		}

		/**
		 * Retorna o tipo do(s) exercício(s) que tiveram um valor armazenado no objeto.
		 * 
		 * @return {@link String} : tipo dos exercícios que tiveram um valor armazenado no objeto. Pode ser <code>null</code> quando o objeto for
		 * usado para armazenar valores de mais de um tipo de exercício.
		 */
		public String getTipoExercicio() {
			return tipoExercicio;
		}

		/**
		 * Define o tipo do(s) exercício(s) que tiveram um valor armazenado no objeto.
		 * 
		 * @param tipoExercicio -  {@link String} : tipo dos exercícios que tiveram um valor armazenado no objeto a ser armazenado no objeto. 
		 * Pode ser <code>null</code> quando o objeto for usado para armazenar valores de mais de um tipo de exercício.
		 */
		public void setTipoExercicio(String tipoExercicio) {
			this.tipoExercicio = tipoExercicio;
		}

		/**
		 * Transforma um {@link List} <{@link Exercicio}> em um  {@link ArrayList} <{@link ValoresGrafico}> afim de facilitar o mapeamento dos dados dos exercícios
		 * para um {@link DefaultCategoryDataset} de um {@link JFreeChart}
		 * 
		 * @param exercicios {@link List} <{@link Exercicio}> a ser transformada em {@link ArrayList} <{@link ValoresGrafico}>
		 * @param duracao especifica o valor a ser armazenado nos objetos {@link ValoresGrafico}
		 * @param calorias especifica o valor a ser armazenado nos objetos {@link ValoresGrafico}
		 * @param velocidade especifica o valor a ser armazenado nos objetos {@link ValoresGrafico}
		 * @param ritmo especifica o valor a ser armazenado nos objetos {@link ValoresGrafico}
		 * @param distancia especifica o valor a ser armazenado nos objetos {@link ValoresGrafico}
		 * @param passos especifica o valor a ser armazenado nos objetos {@link ValoresGrafico}
		 * @param cumulativo define se os objetos {@link ValoresGrafico} serão cumulativos ou não
		 * @return {@link ArrayList} <{@link ValoresGrafico}> com os dados já transformados
		 * 
		 * @since 0.2
		 */
		public static ArrayList<ValoresGrafico> transformarDados(List<Exercicio> exercicios, boolean duracao, boolean calorias, boolean velocidade, 
				boolean ritmo, boolean distancia, boolean passos, boolean cumulativo) 
				{
			ArrayList<ValoresGrafico> valores = new ArrayList<ValoresGrafico>();

			int valorEncontrado;
			for (Exercicio exercicio : exercicios) {
				valorEncontrado= 0;
				for (ValoresGrafico valor : valores) {
					if(cumulativo) {
						if(exercicio.getData().compareTo(valor.getData()) == 0 && exercicio.getTipoExercicio().compareTo(valor.tipoExercicio) == 0) {
							valorEncontrado = 1;
							adicaoCumulativaValorGrafico(valor,exercicio, duracao, calorias, velocidade, ritmo, distancia, passos);
							break;
						}
					}else {
						if(exercicio.getData().compareTo(valor.getData()) == 0) {
							adicaoCumulativaValorGrafico(valor,exercicio, duracao, calorias, velocidade, ritmo, distancia, passos);
							valorEncontrado= 1;
						}
					}
				}
				if(valorEncontrado == 0)
					criarNovoValorGrafico(valores, exercicio, duracao, calorias, velocidade, ritmo, distancia, passos);
			}
			return valores;
				}

		private static void criarNovoValorGrafico(ArrayList<ValoresGrafico> valores, Exercicio exercicio, boolean duracao, boolean calorias, boolean velocidade, boolean ritmo, boolean distancia,
				boolean passos) {
			if(duracao)
				valores.add(new ValoresGrafico(exercicio.getDuracao().toMinutes(), exercicio.getData(), exercicio.getTipoExercicio()));

			if(passos)
				valores.add(new ValoresGrafico(exercicio.getPassosDados(), exercicio.getData(), exercicio.getTipoExercicio()));

			if(distancia)
				valores.add(new ValoresGrafico(exercicio.getDistancia(), exercicio.getData(), exercicio.getTipoExercicio()));

			if(calorias)
				valores.add(new ValoresGrafico(exercicio.getCaloriasPerdidas(), exercicio.getData(), exercicio.getTipoExercicio()));

			if(ritmo)
				if(exercicio instanceof ExercicioAerobico)
					valores.add(new ValoresGrafico(((ExercicioAerobico)exercicio).getRitmoMedio().getMinutosGastos().toMinutes(), exercicio.getData(), exercicio.getTipoExercicio()));

			if(velocidade)
				if(exercicio instanceof ExercicioAerobico)
					valores.add(new ValoresGrafico(((ExercicioAerobico)exercicio).getVelocidadeMedia(), exercicio.getData(), exercicio.getTipoExercicio()));

		}

		private static void adicaoCumulativaValorGrafico(ValoresGrafico valor, Exercicio exercicio, boolean duracao, boolean calorias, boolean velocidade, boolean ritmo, boolean distancia, boolean passos) {
			if(duracao){
				valor.setValor(valor.getValor() + exercicio.getDuracao().toMinutes());
				valor.setNumeroDeExercicios(valor.getNumeroDeExercicios() + 1);
			}

			if(calorias){
				valor.setValor(valor.getValor() + exercicio.getCaloriasPerdidas());
				valor.setNumeroDeExercicios(valor.getNumeroDeExercicios() + 1);
			}
			if(velocidade){
				if(exercicio instanceof ExercicioAerobico){
					valor.setValor(valor.getValor() + ((ExercicioAerobico)exercicio).getVelocidadeMedia());
					valor.setNumeroDeExercicios(valor.getNumeroDeExercicios() + 1);
				}
			}

			if(distancia){
				valor.setValor(valor.getValor() + exercicio.getDistancia());
				valor.setNumeroDeExercicios(valor.getNumeroDeExercicios() + 1);
			}
			if(passos){
				valor.setValor(valor.getValor() + exercicio.getPassosDados());
				valor.setNumeroDeExercicios(valor.getNumeroDeExercicios() + 1);
			}

			if(ritmo){
				if(exercicio instanceof ExercicioAerobico){
					valor.setValor(valor.getValor() + ((ExercicioAerobico)exercicio).getRitmoMedio().getMinutosGastos().toMinutes());
					valor.setNumeroDeExercicios(valor.getNumeroDeExercicios() + 1);
				}
			}
		}
	}
}