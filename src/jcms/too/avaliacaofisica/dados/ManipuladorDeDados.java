package jcms.too.avaliacaofisica.dados;

import static jcms.too.avaliacaofisica.out.Mensagem.msgErro;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import jcms.too.avaliacaofisica.arquivos.ArquivoExercicio;
import jcms.too.avaliacaofisica.arquivos.ArquivoExercicioAerobico;
import jcms.too.avaliacaofisica.arquivos.ArquivoIndiceExercicio;
import jcms.too.avaliacaofisica.arquivos.ArquivoIndiceUsuario;
import jcms.too.avaliacaofisica.arquivos.ArquivoRitmo;
import jcms.too.avaliacaofisica.arquivos.ArquivoTexto;
import jcms.too.avaliacaofisica.arquivos.ArquivoUsuario;
import jcms.too.avaliacaofisica.arquivos.IndiceExercicio;
import jcms.too.avaliacaofisica.arquivos.IndiceUsuario;
import jcms.too.avaliacaofisica.arquivos.RegistroRitmo;
import jcms.too.avaliacaofisica.modelo.Data;
import jcms.too.avaliacaofisica.modelo.Exercicio;
import jcms.too.avaliacaofisica.modelo.ExercicioAerobico;
import jcms.too.avaliacaofisica.modelo.Hora;
import jcms.too.avaliacaofisica.modelo.Ritmo;
import jcms.too.avaliacaofisica.modelo.Tempo;
import jcms.too.avaliacaofisica.modelo.Usuario;
import jcms.too.avaliacaofisica.out.Mensagem;
import jcms.too.avaliacaofisica.utilitarios.Constantes;
import jcms.too.avaliacaofisica.utilitarios.ExpressoesRegulares;
import br.mos.arquivo.aleatorio.BinaryFile;

/**
 * Classe responsável pela manipulação e importação dos dados vitais para o funcionamento da aplicação
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.1
 * @see ArquivoExercicio
 * @see ArquivoIndiceExercicio
 * @see ArquivoIndiceUsuario
 * @see ArquivoUsuario
 * @see ArquivoRitmo
 * @see ArquivoExercicioAerobico
 * @see ArquivoTexto
 */
public class ManipuladorDeDados {
	private ArquivoTexto arqTexto;

	private ArquivoExercicio arqExercicios;
	private final String caminhoArquivoExer = "arquivos" + File.separator + "Exercicios.dat";

	private ArquivoExercicioAerobico arqExerciciosAerobicos;
	private final String caminhoArquivoExerciciosAerobicos = "arquivos" + File.separator + "ExerciciosAerobicos.dat";

	private ArquivoRitmo arqRitmos;
	private final String caminhoArquivoRitmos = "arquivos" + File.separator + "Ritmos.dat";

	private ArquivoUsuario arqUsuario;
	private final String caminhoArquivoUsuarios = "arquivos" + File.separator + "Usuarios.dat";

	private ArquivoIndiceExercicio arqIndicesExercicios;
	private final String caminhoArquivoIndicesExercicios = "arquivos" + File.separator + "IndicesExercicios.dat";

	private ArquivoIndiceUsuario arqIndicesUsuarios;
	private final String nomeArquivoIndicesUsuarios = "arquivos" + File.separator + "IndicesUsuarios.dat";

	/**
	 * Cria uma nova instância <code>ManipuladorDeDados</code>.
	 */
	public ManipuladorDeDados() {
		arqTexto = new ArquivoTexto();
		arqExercicios = new ArquivoExercicio();
		arqUsuario = new ArquivoUsuario();
		arqExerciciosAerobicos = new ArquivoExercicioAerobico();
		arqRitmos = new ArquivoRitmo();
		arqIndicesExercicios = new ArquivoIndiceExercicio();
		arqIndicesUsuarios = new ArquivoIndiceUsuario();
		abrirArquivosDeDados();
	}
	
	/**
	 * Lê todos os objetos {@link Exercicio}, que se encontram no arquivo {@link ArquivoExercicio} usado no objeto {@link ManipuladorDeDados}, do usuário especificado por parâmetro.
	 * 
	 * @param usuario - usuário a ter todos os exercícios lidos.
	 * @return {@link ArrayList} <{@link Object}> : todos os exercícios do usuário que foram lidos do arquivo {@link ArquivoExercicio} usado no objeto {@link ManipuladorDeDados}.
	 * @see ArrayList
	 * @see ArquivoExercicio
	 * @see Exercicio
	 */
	public ArrayList<Object> buscarExerciciosDeUsuario(Usuario usuario) {
		ArrayList<Object> indices = lerIndicesExercicios();

		ArrayList<IndiceExercicio> indicesExerciciosUsuario = new ArrayList<IndiceExercicio>();
		for (int indice = 0;indice < indices.size(); indice++) {
			IndiceExercicio indiceE = (IndiceExercicio) indices.get(indice);
			if(indiceE.getChaveEmail().equals(usuario.getEmail())){
				indicesExerciciosUsuario.add(indiceE);
			}
		}

		ArrayList<Object> exerciciosUsuario = new ArrayList<Object>();
		for (IndiceExercicio indice: indicesExerciciosUsuario) {
			if(indice.getNomeArquivo().equals("Exercicios.dat")){
				exerciciosUsuario.add(lerObjeto(arqExercicios, indice.getNumeroRegistroNoArquivo()));
			}else{
				ExercicioAerobico exercicioA = (ExercicioAerobico) (lerObjeto(arqExerciciosAerobicos, indice.getNumeroRegistroNoArquivo()));
				exercicioA.setRitmosNoExercicio(getRitmosDeUmExercicio(indice.getNumeroRegistroNoArquivo()));
				exerciciosUsuario.add(exercicioA);
			}
		}
		return exerciciosUsuario;
	}

	/**
	 * Importa o conteúdo de um ou mais arquivos texto válidos para a aplicação e o adiciona ao banco de dados do programa.
	 * 
	 * @param pathArquivos {@link List} <{@link String}>  contém o caminho completo de todos os arquivos a serem importados.
	 * @return <code><b>int</b></code> : 0 quando nenhum dado for importado, do contrário, retorna 1.
	 */
	public int importarEGerar(List<String> pathArquivos){
		int verificador = 0;
		ArrayList<Exercicio> exercicios = new ArrayList<Exercicio>();
		List<String> nomesArquivos = new ArrayList<String>();

		int situacaoArquivo = 0;
		Map<String, String> situacaoArquivos = new HashMap<String, String>();
		for (String pathArquivo : pathArquivos) {
			verificador = abrirArquivoTexto(pathArquivo);
			if(verificador == 0)
				break;

			situacaoArquivo = montarEstruturaDeDados(arqTexto.ler(), exercicios);
			verificador = 1;

			String nomeArquivo = getNomeArquivo(pathArquivo);
			nomesArquivos.add(nomeArquivo);
			if(situacaoArquivo == Constantes.ARQUIVO_DE_DADOS_INVALIDO_OU_SEM_DADOS_INTEGROS){
				situacaoArquivos.put(nomeArquivo, "Não importado (Arquivo inválido, sem dados íntegros ou com todos os dados já importados).");
			}else if(situacaoArquivo == Constantes.ARQUIVO_PARCIALMENTE_INTEGRO){
				situacaoArquivos.put(nomeArquivo, "Parcialmente importado. (Dados inválidos ou repetidos encontrados)");
			}else{
				situacaoArquivos.put(nomeArquivo, "Importado completamente.");
			}

			fecharArquivoTexto();
		}

		exibirRelatorioImportacao(situacaoArquivos, nomesArquivos);

		if(exercicios.isEmpty())
			return verificador = 0;

		gerarArquivosDeDados(exercicios);

		return verificador;

	}

	/**
	 * Escreve o objeto {@link Object} passado como parâmetro no arquivo {@link BinaryFile} também passado como parâmetro.
	 * 
	 * @param arq - arquivo no qual o objeto será gravado.
	 * @param obj - objeto a ser gravado no arquivo.
	 */
	public void escreverObjeto(BinaryFile arq, Object obj) {
		try {
			arq.writeObject(obj);
		} catch (IOException e) {
			msgErro("Erro ao escrever um objeto no arquivo " + arq.getFileName().substring(arq.getFileName().lastIndexOf(File.separator)+1) + "!", Constantes.NOME_PROGRAMA);
		}
	}
	
	/**
	 * Verifica se há dados no banco de dados do programa.
	 * @return <code>true</code> se houver, <code>false</code> se não houver.
	 */
	public boolean haDados(){
		if(numeroDeRegistrosNoArquivo(arqExercicios) > 0 || numeroDeRegistrosNoArquivo(arqExerciciosAerobicos) > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Retorna o valor inteiro de uma {@link String} que pode conter pontos.
	 * 
	 * @param quantidade -  {@link String} a se obter o valor inteiro
	 * @return <code><b>int</b></code> : valor inteiro da {@link String}
	 */
	public int getValorEmInteiro(String quantidade) {
		if(quantidade.contains("."))
			quantidade = quantidade.substring(0, quantidade.indexOf(".")) + quantidade.substring(quantidade.indexOf(".") + 1);
		return Integer.parseInt(quantidade);
	}
	
	/**
	 * Lê um objeto {@link Exercicio} do arquivo {@link ArquivoExercicio} usado por um objeto {@link ManipuladorDeDados} que será especificado pelo parâmetro <b>indice</b>.
	 * 
	 * @param indice : indica o objeto a ser lido e o arquivo onde ele se encontra.
	 * @return {@link Exercicio} ou <code><i>null</i></code> quando o objeto indicado pelo parâmentro <b>indice</b> não for encontrado ou quando ocorrer algum erro de E/S.
	 */
	public Exercicio lerExercicioPorIndice(IndiceExercicio indice){
		if(indice.getNomeArquivo().equals("Exercicios.dat")){
			return (Exercicio) lerObjeto(arqExercicios, indice.getNumeroRegistroNoArquivo());
		}else{
			return (Exercicio) lerObjeto(arqExerciciosAerobicos, indice.getNumeroRegistroNoArquivo());
		}
	}

	/**
	 * Lê todos os objetos gravados no arquivo {@link ArquivoIndiceExercicio} usado por um objeto {@link ManipuladorDeDados}.
	 * 
	 * @return {@link ArrayList} <{@link Object}> : índices lidos do arquivo {@link ArquivoIndiceExercicio}.
	 * @see ArquivoIndiceExercicio
	 * @see IndiceExercicio
	 */
	public ArrayList<Object> lerIndicesExercicios() {
		return lerObjetos(arqIndicesExercicios);
	}
	
	/**
	 * Lê do arquivo {@link ArquivoUsuario} usado no objeto {@link ManipuladorDeDados}, os exercícios do usuário praticados dentro do período especificado
	 * 
	 * @param usuario - usuário do qual serão lidos os exercícios
	 * @param dataInicioPeriodo - data inicial do período
	 * @param dataFimPeriodo - data final do período
	 * 
	 * @see Data
	 * @see IndiceUsuario
	 * @see Usuario
	 * 
	 * @return {@link ArrayList} <{@link Exercicio}> : exercícios lidos do arquivo {@link ArquivoUsuario} usado no objeto {@link ManipuladorDeDados}.
	 */
	public ArrayList<Exercicio> lerExerciciosUsuarioDoPeriodo(Usuario usuario, Data dataInicioPeriodo, Data dataFimPeriodo){
		ArrayList<Object> exercicios = buscarExerciciosDeUsuario(usuario);
		ArrayList<Exercicio> exerciciosNoPeriodo = new ArrayList<Exercicio>();

		for (Object object : exercicios) {
			Exercicio exer = (Exercicio) object;
			if(exer.getData().dataDentroDoPeriodo(dataInicioPeriodo, dataFimPeriodo)){
				exerciciosNoPeriodo.add(exer);
			}
		}

		exerciciosNoPeriodo.sort(new Comparator<Exercicio>() {
			@Override
			public int compare(Exercicio exercicio1, Exercicio exercicio2) {
				return exercicio1.getData().compareTo(exercicio2.getData());
			}
		});
		return exerciciosNoPeriodo;
	}
	
	/**
	 * Lê todos os objetos {@link IndiceUsuario} do arquivo {@link ArquivoIndiceUsuario} usado no objeto {@link ManipuladorDeDados}.
	 * 
	 * @return {@link ArrayList} <{@link IndiceUsuario}> : exercícios lidos do arquivo {@link ArquivoUsuario} usado no objeto {@link ManipuladorDeDados}.
	 * @see IndiceUsuario
	 * @see Usuario
	 */
	public ArrayList<Object> lerIndicesUsuarios(){
		return lerObjetos(arqIndicesUsuarios);
	}
	
	/**
	 * Retorna o número de registros no arquivo passado como parâmetro.
	 * 
	 * @param arq - {@link BinaryFile} a ter o número de registros calculado.
	 * @return  <code>long</code> : número de registros calculado.
	 */
	public long numeroDeRegistrosNoArquivo(BinaryFile arq){
		try {
			return arq.recordQuantity();
		} catch (IOException e) {
			msgErro("Erro ao calcular o tamanho do arquivo " + arq.getFileName() + " !", Constantes.NOME_PROGRAMA);
			return -1;
		}
	}
	
	/**
	 * Lê um objeto {@link Object} do arquivo <code>TipoArquivo</code>, que deve ser um {@link BinaryFile}, passado como parâmetro.
	 * 
	 * @param arq - arquivo do qual o objeto será lido
	 * @param posicaoNoArquivo - posição do objeto a ser lido no arquivo.
	 * @return {@link Object} lido, ou <code><b>null</b></code> quando o ocorrer algum erro de E/S.
	 * 
	 */
	public <TipoArquivo extends BinaryFile> Object lerObjeto(TipoArquivo arq, long posicaoNoArquivo){
		reposicionarPonteiroNoArquivo(arq, posicaoNoArquivo);
		try {
			return arq.readObject();
		} catch (IOException e) {
			msgErro("Erro ao ler um objeto do arquivo " + arq.getFileName() + " !", Constantes.NOME_PROGRAMA);
			return null;
		}
	}

	/**
	 * Reposiciona o ponteiro do arquivo (que deve ser um {@link BinaryFile}) passado como parâmetro.
	 * 
	 * @param arq - arquivo a ter o ponteiro reposicionado.
	 * @param posicaoNoArquivo - nova posição do ponteiro no arquivo
	 * @return <code><b>true</b></code> quando o reposiocionamento é bem sucedido, <code><b>false</b></code> quando não é.
	 */
	public <TipoArquivo extends BinaryFile> boolean reposicionarPonteiroNoArquivo(TipoArquivo arq, long posicaoNoArquivo){
		try {
			arq.setFilePointer(posicaoNoArquivo);
			return true;
		} catch (IOException e) {
			msgErro("Erro ao setar o ponteiro no arquivo " + arq.getFileName() + " !", Constantes.NOME_PROGRAMA);
			return false;
		}
	}

	/**
	 * Lê o objeto {@link Usuario} gravado no registro <code>posicaoNoArquivo</code> do <code>ArquivoUsuario</code>
	 * usado no {@link ManipuladorDeDados}.
	 * 
	 * @param posicaoNoArquivo - posição onde o objeto a ser lido se encontra no arquivo.
	 * @return o objeto {@link Usuario} lido ou <code><b>null</b></code> quando a leitura não for bem sucedida.
	 * @see Usuario
	 */
	public Usuario lerUsuario(long posicaoNoArquivo){
		Usuario usuario;
		Object obj = lerObjeto(arqUsuario, posicaoNoArquivo);
		if(obj != null){
			usuario = (Usuario) obj;
			return usuario;
		}else{
			return null;
		}
	}
	
	
	/**
	 * Retorna todos os tipos de exercícios dos exercícios armazenados no {@link List}<{@link Exercicio}>.
	 * 
	 * @param exercicios : {@link List} <{@link Exercicio}> - exercícios que terão seus tipos obtidos.
	 * @return {@link List} <{@link Object}> : tipos obtidos dos exercícios recebidos
	 */
	public static List<Object> getTiposdeExercicios(List<Exercicio> exercicios){
		List<Object> tipos = new ArrayList<Object>();

		for (Exercicio exercicio : exercicios) {
			if(!tipos.contains(exercicio.getTipoExercicio())){
				tipos.add(exercicio.getTipoExercicio());
			}
		}
		return tipos;
	}

	/**
	 * Verifica se o {@link List} <{@link Exercicio}> recebido contém apenas objetos da classe {@link ExercicioAerobico}
	 * 
	 * @param exercicios - {@link List} <{@link Exercicio}> - <code>list</code> a ser analizada
	 * @return <code><b>true</b></code> : quando o <code>list</code> conter apenas objetos da classe {@link ExercicioAerobico}; caso contrário
	 * retorna <code><b>false</b></code>
	 */
	public static boolean isListOfExerciciosAerobicos(List<Exercicio> exercicios){
		for (Exercicio exercicio : exercicios) {
			if(exercicio instanceof ExercicioAerobico){
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna um {@link ArrayList} <{@link Exercicio }> com os objetos {@link Exercicio} do tipo especificado pela <code>string</code>
	 * <code><i>tipoExercicio</i></code>. Esses exercícios serão obtidos do {@link ArrayList} < {@link Exercicio} >  recebido por parâmetro.
	 * 
	 * @param exercicios -  {@link ArrayList} < {@link Exercicio} > : exercícios a serem selecionados pelo tipo
	 * @param tipoExercicio - <code>string</code> : tipo dos exercícios a serem obtidos do <code>ArrayList</code>
	 * @return {@link ArrayList} < {@link Exercicio} > : exercícios do tipo especificado
	 */
	public static ArrayList<Exercicio> getExerciciosDoTipo(ArrayList<Exercicio> exercicios, String tipoExercicio) {
		ArrayList<Exercicio> exerciciosDoTipo = new ArrayList<Exercicio>();

		for (Exercicio exer : exercicios) {
			if(exer.getTipoExercicio().compareToIgnoreCase(tipoExercicio) == 0){
				exerciciosDoTipo.add(exer);
			}
		}
		return exerciciosDoTipo;
	}
	
	private String getNomeArquivo(String pathArquivo) {
		return pathArquivo.substring(pathArquivo.lastIndexOf("\\")+1);
	}

	private void exibirRelatorioImportacao(Map<String, String> situacaoArquivos, List<String> nomesArquivos) {
		String[] colunas = new String[] {"Arquivo", "Situação"}; 
		String[][] dados = new String[nomesArquivos.size()][colunas.length];

		for (int indice = 0; indice < nomesArquivos.size(); indice++) {
			dados[indice][0] = nomesArquivos.get(indice);
			dados[indice][1] = situacaoArquivos.get(nomesArquivos.get(indice));
		}

		JTable tabela = new JTable(dados, colunas);
		DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
		cellRender.setHorizontalAlignment(SwingConstants.CENTER);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(250);
		tabela.getColumnModel().getColumn(0).setCellRenderer(cellRender);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(550);
		tabela.getColumnModel().getColumn(1).setCellRenderer(cellRender);

		JScrollPane panel = new JScrollPane(tabela);
		panel.setPreferredSize(new Dimension(800, 200));

		Mensagem.msgInfo(panel, "Importação concluída!");
	}

	private void gerarArquivosDeDados(ArrayList<Exercicio> exercicios) {
		gravarDados(exercicios);
	}

	private void gravarDados(ArrayList<Exercicio> exercicios) {
		int indiceExercicio = (int) numeroDeRegistrosNoArquivo(arqExercicios), indiceExercicioAerobico = (int) numeroDeRegistrosNoArquivo(arqExerciciosAerobicos), verificadorUsuario = 1;
		List<IndiceExercicio> indices = new ArrayList<IndiceExercicio>();
		List<Usuario> usuarios = new ArrayList<Usuario>();

		reposicionarPonteiroNoArquivo(arqExercicios, numeroDeRegistrosNoArquivo(arqExercicios));
		reposicionarPonteiroNoArquivo(arqExerciciosAerobicos, numeroDeRegistrosNoArquivo(arqExerciciosAerobicos));
		reposicionarPonteiroNoArquivo(arqRitmos, numeroDeRegistrosNoArquivo(arqRitmos));

		for (Exercicio exercicio : exercicios) {
			if(exercicio instanceof ExercicioAerobico){
				ExercicioAerobico exercicioAerobico = (ExercicioAerobico) exercicio;

				escreverObjeto(arqExerciciosAerobicos, exercicioAerobico);
				for (Ritmo ritmo : exercicioAerobico.getRitmosNoExercicio()) {
					escreverObjeto(arqRitmos, new RegistroRitmo(ritmo, indiceExercicioAerobico));
				}
				indices.add(new IndiceExercicio(exercicioAerobico.getTipoExercicio(), exercicioAerobico.getUsuario().getEmail(), exercicioAerobico.getData().toString(), exercicioAerobico.getTempo().toString(), "ExerciciosAerobicos.dat", indiceExercicioAerobico));
				indiceExercicioAerobico++;
			}
			else{
				escreverObjeto(arqExercicios, exercicio);
				indices.add(new IndiceExercicio(exercicio.getTipoExercicio(), exercicio.getUsuario().getEmail(), exercicio.getData().toString(), exercicio.getTempo().toString(), "Exercicios.dat", indiceExercicio));
				indiceExercicio++;
			}

			for (int indice = 0; indice < usuarios.size(); indice++) {
				if(usuarios.get(indice).getEmail().equals(exercicio.getUsuario().getEmail())){
					verificadorUsuario = 0;
					break;
				}
			}

			if(verificadorUsuario == 1){
				usuarios.add(exercicio.getUsuario());
			}else{
				verificadorUsuario = 1;
			}
		}

		usuarios.sort(new Comparator<Usuario>() {
			@Override
			public int compare(Usuario usuario1, Usuario usuario2) {
				return usuario1.compareTo(usuario2);
			}
		});

		Usuario user;
		ArrayList<Object> indicesUsuario = lerIndicesUsuarios();
		int posicaoNoArquivo = (int) numeroDeRegistrosNoArquivo(arqUsuario);
		reposicionarPonteiroNoArquivo(arqUsuario, posicaoNoArquivo);
		reposicionarPonteiroNoArquivo(arqIndicesUsuarios, numeroDeRegistrosNoArquivo(arqIndicesUsuarios));
		for (int indice = 0; indice < usuarios.size(); indice++) {
			user = usuarios.get(indice);
			if(!verificarCadastroUsuario(user, indicesUsuario)){
				escreverObjeto(arqUsuario, user);
				escreverObjeto(arqIndicesUsuarios, new IndiceUsuario(user.getNome(), user.getEmail(),posicaoNoArquivo++));
			}
		}

		indices.sort(new Comparator<IndiceExercicio>() {
			@Override
			public int compare(IndiceExercicio indice1, IndiceExercicio indice2) {
				return indice1.getTipoExercicio().compareTo(indice2.getTipoExercicio());
			}
		});

		reposicionarPonteiroNoArquivo(arqIndicesExercicios, numeroDeRegistrosNoArquivo(arqIndicesExercicios));
		for (IndiceExercicio indice : indices) {
			escreverObjeto(arqIndicesExercicios, indice);
		}
	}

	private boolean verificarCadastroUsuario(Usuario user, ArrayList<Object> indicesUsuario) {
		for (Object object : indicesUsuario) {
			if(((IndiceUsuario)object).verificaUsuario(user)){
				return true;
			}
		}
		return false;
	}

	private void abrirArquivosDeDados() {
		abrirArquivo(arqExercicios,caminhoArquivoExer);
		abrirArquivo(arqExerciciosAerobicos, caminhoArquivoExerciciosAerobicos);
		abrirArquivo(arqRitmos, caminhoArquivoRitmos);
		abrirArquivo(arqUsuario, caminhoArquivoUsuarios);
		abrirArquivo(arqIndicesExercicios, caminhoArquivoIndicesExercicios);
		abrirArquivo(arqIndicesUsuarios, nomeArquivoIndicesUsuarios);
	}

	private void abrirArquivo(BinaryFile arq, String caminhoArquivo){
		try {
			arq.openFile(caminhoArquivo);
		} catch (FileNotFoundException e) {
			msgErro("Erro ao abrir o arquivo " + arq.getFileName().substring(arq.getFileName().lastIndexOf(File.separator)+1) + "!", Constantes.NOME_PROGRAMA);
		}
	}

	private int montarEstruturaDeDados(ArrayList<String> dados, List<Exercicio> exercicios) {
		ArrayList<Object> indices = lerIndicesExercicios();
		boolean exercicioComDadosInvalidos = false, exercicioAerobico = false, arquivoComDadosInvalidosOuRepetidos = false;

		String tipoExercicio = "", nomeUsuario= "" , dataDeNascimento = "00/00/00" , email= ""  , sexo= "" ,altura= ""   ,peso= ""  ,
				dataExercicio= "01/01/1999"  , tempo= "00:00 - 00:01"  , duracao = "00:01:00" , ritmoMedio= "1 Km: 01'00\""  , ritmoMaximo= "1 Km: 01'00\""  ;

		double velocidadeMedia = 1 , velocidadeMaxima= 1  , caloriasPerdidas = 1 , distancia = 1 ;
		int numeroDePassos= 1  , menorElevacao= 1  , maiorElevacao= 1 ;

		Ritmo ritmoMedioObj =new Ritmo(ritmoMedio) , ritmoMaximoObj = new Ritmo(ritmoMaximo);
		ArrayList<Exercicio> exerciciosDoArquivo = new ArrayList<Exercicio>();
		ArrayList<Ritmo> ritmos = null;

		

		for (int indice = 0; indice < dados.size(); indice++) {
			
			String linha = dados.get(indice);

			if(exercicioComDadosInvalidos && !arquivoComDadosInvalidosOuRepetidos)
				arquivoComDadosInvalidosOuRepetidos = exercicioComDadosInvalidos;

			if(indice == 0 && !linha.contains("Exercício:"))
				return Constantes.ARQUIVO_DE_DADOS_INVALIDO_OU_SEM_DADOS_INTEGROS;

			if(linha.contains("Usuário") || linha.contains("Detalhes do exercício"))
				continue;
			
			if(indice == dados.size() -1 ){
				if(linha.contains("Passos:")){
					if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.PASSOS)){
						String quantidade = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n")).replace(",", ".");
						numeroDePassos = getValorEmInteiro(quantidade);
					}else{
						exercicioComDadosInvalidos = true;
					}
				}else{
					if(linha.contains("Km:") || linha.contains("KM:") || linha.contains("kM:")){
						if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.RITMO_COMPLETO)){
							Ritmo ritmoObj = new Ritmo(linha);
							ritmos.add(ritmoObj);
						}else{
							exercicioComDadosInvalidos = true;
						}
					}
				}
			}
			
			if((linha.contains("Exercício:") || indice == dados.size() - 1) && !exercicioComDadosInvalidos){
				if(indice != 0){
					if(exercicioAerobico){
						if(dados.size() != 0){
							ExercicioAerobico exercicio = new ExercicioAerobico();

							exercicio.setTipoExercicio(tipoExercicio);
							exercicio.setUsuario(new Usuario(nomeUsuario, email, sexo, new Data(dataDeNascimento), altura, peso));
							exercicio.setData(new Data(dataExercicio));
							exercicio.setTempo(new Tempo(tempo));
							exercicio.setDuracao(new Hora(duracao));
							exercicio.setDistancia(distancia);
							exercicio.setCaloriasPerdidas(caloriasPerdidas);
							exercicio.setPassosDados(numeroDePassos);
							exercicio.setVelocidadeMedia(velocidadeMedia);
							exercicio.setVelocidadeMaxima(velocidadeMaxima);
							exercicio.setRitmoMedio(ritmoMedioObj);
							exercicio.setRitmoMaximo(ritmoMaximoObj);
							exercicio.setMenorElevacao(menorElevacao);
							exercicio.setMaiorElevacao(maiorElevacao);
							exercicio.setRitmosNoExercicio(ritmos);

							if(verificaExercicio(indices, exercicio) && !exercicioJaEstaNaListaDoArquivo(exerciciosDoArquivo, exercicio) && !exercicioJaEstaNaListaRecebida(exercicios, exercicio))
								exerciciosDoArquivo.add(exercicio);
							else{
								arquivoComDadosInvalidosOuRepetidos = true;
							}
						}
					}else{
						if(dados.size() != 0){
							Exercicio exercicio = new Exercicio();

							exercicio.setTipoExercicio(tipoExercicio);
							exercicio.setUsuario(new Usuario(nomeUsuario, email, sexo, new Data(dataDeNascimento), altura, peso));
							exercicio.setData(new Data(dataExercicio));
							exercicio.setTempo(new Tempo(tempo));
							exercicio.setDuracao(new Hora(duracao));
							exercicio.setDistancia(distancia);
							exercicio.setCaloriasPerdidas(caloriasPerdidas);
							exercicio.setPassosDados(numeroDePassos);

							if(verificaExercicio(indices, exercicio) &&  !exercicioJaEstaNaListaDoArquivo(exerciciosDoArquivo, exercicio) && !exercicioJaEstaNaListaRecebida(exercicios, exercicio))
								exerciciosDoArquivo.add(exercicio);
							else{
								arquivoComDadosInvalidosOuRepetidos = true;
							}
						}
					}
					exercicioAerobico = false;
				}
			}

			if(linha.contains("Exercício:")){
				exercicioComDadosInvalidos = false;
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.TIPO_EXERCICIO)){
					tipoExercicio = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Nome:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.NOME)){
					nomeUsuario = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Sexo:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.SEXO)){
					sexo = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Altura:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.ALTURA)){
					altura = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Peso:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.PESO)){
					peso = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}

			}

			if(linha.contains("Data de nascimento:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.DATA)){
					dataDeNascimento = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}

			}

			if(linha.contains("E-mail:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.EMAIL)){
					email = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}

			}

			if(linha.contains("Data:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.DATA)){
					dataExercicio = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Tempo:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.TEMPO)){
					tempo = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Duração:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.DURACAO)){
					duracao = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n"));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Distância")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.DISTANCIA)){
					distancia = Double.parseDouble(linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf(" ")-1).replace(",", "."));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Calorias perdidas:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.CALORIA)){
					caloriasPerdidas = Double.parseDouble(linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf(" ")).replace(",", "."));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Passos:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.PASSOS)){
					String quantidade = linha.substring(linha.indexOf(":") + 2, linha.indexOf("\n")).replace(",", ".");
					numeroDePassos = getValorEmInteiro(quantidade);
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Velocidade média:")){
				if(ExpressoesRegulares.verificarTexto(linha.replace("\n", ""), ExpressoesRegulares.VELOCIDADES)){
					exercicioAerobico = true;
					velocidadeMedia = Double.parseDouble(linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf(" ")).replace(",", "."));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Velocidade máxima:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.VELOCIDADES)){
					velocidadeMaxima = Double.parseDouble(linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf(" ")).replace(",", "."));
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Ritmo médio:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.RITMOS_MEDIO_MAXIMO)){
					ritmoMedio = "0 Km:" + linha.substring(linha.indexOf(":")+1, linha.lastIndexOf(" "));
					ritmoMedioObj = new Ritmo(ritmoMedio);
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("Ritmo máximo:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.RITMOS_MEDIO_MAXIMO)){
					ritmoMaximo = "0 Km:" + linha.substring(linha.indexOf(":")+1, linha.lastIndexOf(" "));
					ritmoMaximoObj = new Ritmo(ritmoMaximo);
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}

			}

			if(linha.contains("Menor elevação:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.ELEVACAO)){
					String valorElevacao = linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf(" ")).replace(",", ".");
					menorElevacao = getValorEmInteiro(valorElevacao);
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}

			}

			if(linha.contains("Maior elevação:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.ELEVACAO)){
					String quantidade = linha.substring(linha.indexOf(":") + 2, linha.lastIndexOf(" ")).replace(",", ".");
					maiorElevacao = getValorEmInteiro(quantidade);
					continue;
				}else{
					exercicioComDadosInvalidos = true;
					continue;
				}
			}

			if(linha.contains("-- Ritmo --")){
				ritmos = new ArrayList<Ritmo>();
				continue;
			}

			if(linha.contains("Km:") || linha.contains("KM:") || linha.contains("kM:")){
				if(ExpressoesRegulares.verificarTexto(linha, ExpressoesRegulares.RITMO_COMPLETO)){
					Ritmo ritmoObj = new Ritmo(linha);
					ritmos.add(ritmoObj);
					if(indice != dados.size() - 1)
						continue;
				}else{
					exercicioComDadosInvalidos = true;
				}
			}
		}
		
		if(exerciciosDoArquivo.isEmpty()){
			return Constantes.ARQUIVO_DE_DADOS_INVALIDO_OU_SEM_DADOS_INTEGROS;
		}

		exercicios.addAll(exerciciosDoArquivo);

		if(arquivoComDadosInvalidosOuRepetidos){
			return Constantes.ARQUIVO_PARCIALMENTE_INTEGRO;
		}

		return Constantes.ARQUIVO_INTEGRO;

	}

	private boolean exercicioJaEstaNaListaRecebida(List<Exercicio> exercicios, Exercicio exercicio) {
		for (Exercicio exer : exercicios) {
			if(exer.compareTo(exercicio) == 0){
				return true;
			}
		}
		return false;
	}

	private boolean exercicioJaEstaNaListaDoArquivo( ArrayList<Exercicio> exerciciosDoArquivo, Exercicio exercicio) {
		for (Exercicio exer : exerciciosDoArquivo) {
			if(exer.compareTo(exercicio) == 0){
				return true;
			}
		}
		return false;
	}

	private boolean verificaExercicio(ArrayList<Object> indices, Exercicio exercicio) {
		for (Object object : indices) {
			if(((IndiceExercicio)object).verificaDadosExercicio(exercicio)){
				return false;
			}
		}
		return true;
	}

	private void fecharArquivoTexto() {
		try {
			arqTexto.fechar();
		} catch (IOException e) {
			msgErro("Erro ao fechar o arquivo de dados !", Constantes.NOME_PROGRAMA);
		}
	}

	private int abrirArquivoTexto(String nomeArquivo){
		try {
			arqTexto.abrir(nomeArquivo);
			return 1;
		} catch (FileNotFoundException e) {
			msgErro("Erro ao abrir o arquivo de dados " + nomeArquivo + " !", Constantes.NOME_PROGRAMA);
			return 0;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		fecharArquivos();
	}

	private void fecharArquivos() {
		fecharArquivo(arqExercicios);
		fecharArquivo(arqExerciciosAerobicos);
		fecharArquivo(arqRitmos);
		fecharArquivo(arqUsuario);
		fecharArquivo(arqIndicesExercicios);
		fecharArquivo(arqIndicesUsuarios);
	}

	private void fecharArquivo(BinaryFile arq) {
		try {
			arq.closeFile();
		} catch (IOException e) {
			msgErro("Erro ao fechar o arquivo " + arq.getFileName().substring(arq.getFileName().lastIndexOf(File.separator)+1) + "!", Constantes.NOME_PROGRAMA);
		}
	}

	private ArrayList<Object> lerObjetos(BinaryFile arq){
		ArrayList<Object> objetos = new ArrayList<Object>();

		long tamanhoArquivo = numeroDeRegistrosNoArquivo(arq) ;
		tamanhoArquivo -= -1;
		if(tamanhoArquivo < 0)
			return null;

		for (int indice = 0; indice < tamanhoArquivo -1; indice++) {
			objetos.add(lerObjeto(arq, indice));
		}

		return objetos;
	}

	private ArrayList<Ritmo> getRitmosDeUmExercicio(int numeroRegistroNoArquivo) {
		ArrayList<Object> registros = lerObjetos(arqRitmos);
		ArrayList<Ritmo> ritmos =  new ArrayList<Ritmo>();

		for (Object registro : registros) {
			RegistroRitmo registroRitmo = (RegistroRitmo) registro;
			if(registroRitmo.getNumeroRegistro() == numeroRegistroNoArquivo){
				ritmos.add(registroRitmo.getRitmo());
			}
		}
		return ritmos;
	}
}