package jcms.too.avaliacaofisica.arquivos;

import java.io.IOException;

import jcms.too.avaliacaofisica.modelo.Data;
import jcms.too.avaliacaofisica.modelo.ExercicioAerobico;
import jcms.too.avaliacaofisica.modelo.Hora;
import jcms.too.avaliacaofisica.modelo.Ritmo;
import jcms.too.avaliacaofisica.modelo.Tempo;
import jcms.too.avaliacaofisica.modelo.Usuario;
import jcms.too.avaliacaofisica.utilitarios.Constantes;
import br.mos.arquivo.aleatorio.BinaryFile;

/**
 * Fornece operações para um arquivo binário usando os serviços de um arquivo com acesso aleatório.
 * O tipo de dado do registro do arquivo é {@link ExercicioAerobico}.
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.1
 */
public class ArquivoExercicioAerobico extends BinaryFile {

	private static final int TAMANHO_REGISTRO = 392;
	
	/**
	 * Lê o objeto como um registro do arquivo.
	 * 
	 * @return um {@link ExercicioAerobico} com os atributos do objeto lido do arquivo.
	 * @throws IOException se houver algum erro de E/S.
	 */
	@Override
	public ExercicioAerobico readObject() throws IOException {
		ExercicioAerobico exercicio = new ExercicioAerobico();

		exercicio.setTipoExercicio(readString(Constantes.TAMANHO_STRING_TIPO_EXERCICIO));
		Usuario usuario = new Usuario();
		usuario.setEmail(readString(Constantes.TAMANHO_STRING_EMAIL));
		exercicio.setUsuario(usuario);
		exercicio.setData(new Data(readString(Constantes.TAMANHO_STRING_DATA)));
		exercicio.setTempo(new Tempo(readString(Constantes.TAMANHO_STRING_TEMPO)));
		exercicio.setDuracao(new Hora(readString(Constantes.TAMANHO_STRING_DURACAO_EM_HORA)));
		exercicio.setDistancia(randomAccessFile.readDouble());
		exercicio.setCaloriasPerdidas(randomAccessFile.readDouble());
		exercicio.setPassosDados(randomAccessFile.readInt());
		exercicio.setVelocidadeMedia(randomAccessFile.readDouble());
		exercicio.setVelocidadeMaxima(randomAccessFile.readDouble());
		Ritmo ritmo = new Ritmo();
		ritmo.setTempoRitmo(readString(Constantes.TAMANHO_STRING_RITMO));
		exercicio.setRitmoMedio(ritmo);
		Ritmo ritmoMaximo = new Ritmo();
		ritmoMaximo.setTempoRitmo(readString(Constantes.TAMANHO_STRING_RITMO));
		exercicio.setRitmoMaximo(ritmoMaximo);
		exercicio.setMenorElevacao(randomAccessFile.readDouble());
		exercicio.setMaiorElevacao(randomAccessFile.readDouble());
		
		return exercicio;
	}

	/**
	 * Retorna o tamanho, em bytes, do registro composto por variáveis membro do objeto.
	 * 
	 * @return um int com o tamanho, em bytes, do registro.
	 */
	@Override
	public int recordSize() {
		return TAMANHO_REGISTRO;
	}

	/**
	 * Escreve um objeto {@link ExercicioAerobico} como um registro do arquivo.
	 * 
	 * @param obj - um Object que será armazenado no arquivo.
	 * @throws IOException se ocorrer algum erro de E/S.
	 * @throws ClassCastException se o objeto passado como parâmetro não for um {@link ExercicioAerobico}
	 */
	@Override
	public void writeObject(Object obj) throws IOException, ClassCastException {
		ExercicioAerobico exercicio;

		if(obj instanceof ExercicioAerobico)
			exercicio = (ExercicioAerobico) obj;
		else
			throw new ClassCastException();

		randomAccessFile.writeChars(setStringLength(exercicio.getTipoExercicio(), Constantes.TAMANHO_STRING_TIPO_EXERCICIO));
		randomAccessFile.writeChars(setStringLength(exercicio.getUsuario().getEmail(), Constantes.TAMANHO_STRING_EMAIL));
		randomAccessFile.writeChars(setStringLength(exercicio.getData().toString(), Constantes.TAMANHO_STRING_DATA));
		randomAccessFile.writeChars(setStringLength(exercicio.getTempo().toString(), Constantes.TAMANHO_STRING_TEMPO));
		randomAccessFile.writeChars(setStringLength(exercicio.getDuracao().toString(), Constantes.TAMANHO_STRING_DURACAO_EM_HORA));
		randomAccessFile.writeDouble(exercicio.getDistancia());
		randomAccessFile.writeDouble(exercicio.getCaloriasPerdidas());
		randomAccessFile.writeInt(exercicio.getPassosDados());
		randomAccessFile.writeDouble(exercicio.getVelocidadeMedia());
		randomAccessFile.writeDouble(exercicio.getVelocidadeMaxima());
		randomAccessFile.writeChars(setStringLength(exercicio.getRitmoMedio().getTempoRitmo(), Constantes.TAMANHO_STRING_RITMO));
		randomAccessFile.writeChars(setStringLength(exercicio.getRitmoMaximo().getTempoRitmo(), Constantes.TAMANHO_STRING_RITMO));
		randomAccessFile.writeDouble(exercicio.getMenorElevacao());
		randomAccessFile.writeDouble(exercicio.getMaiorElevacao());
	}
}