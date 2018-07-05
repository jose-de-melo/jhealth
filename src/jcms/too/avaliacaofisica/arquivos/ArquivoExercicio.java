package jcms.too.avaliacaofisica.arquivos;

import java.io.IOException;

import jcms.too.avaliacaofisica.modelo.Data;
import jcms.too.avaliacaofisica.modelo.Exercicio;
import jcms.too.avaliacaofisica.modelo.Hora;
import jcms.too.avaliacaofisica.modelo.Tempo;
import jcms.too.avaliacaofisica.modelo.Usuario;
import jcms.too.avaliacaofisica.utilitarios.Constantes;
import br.mos.arquivo.aleatorio.BinaryFile;

/**
 * Fornece operações para um arquivo binário usando os serviços de um arquivo com acesso aleatório.
 * O tipo de dado do registro do arquivo é {@link Exercicio}.
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.1
 */
public class ArquivoExercicio extends BinaryFile {

	private static final int TAMANHO_REGISTRO_ARQUIVO = 300;
	
	/**
	 * Lê um objeto de um {@link ArquivoExercicio}.
	 * @return o objeto {@link Exercicio} lido.
	 * @throws IOException se houver algum erro de E/S.
	 */
	@Override
	public Exercicio readObject() throws IOException {
		Exercicio exercicio = new Exercicio();
		
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
		
		return exercicio;
	}

	/**
	 * Retorna o tamanho, em bytes, do registro composto por variáveis membro do objeto.
	 * 
	 * @return um int com o tamanho, em bytes, do registro.
	 */
	@Override
	public int recordSize() {
		return TAMANHO_REGISTRO_ARQUIVO;
	}

	/**
	 * Escreve um objeto {@link Exercicio} como um registro do arquivo.
	 * 
	 * @param obj - um Object que será armazenado no arquivo.
	 * @throws IOException se ocorrer algum erro de E/S.
	 * @throws ClassCastException se o objeto passado como parâmetro não for um {@link Exercicio}
	 */
	@Override
	public void writeObject(Object obj) throws IOException, ClassCastException {
		Exercicio exercicio;
		
		if(obj instanceof Exercicio)
			exercicio = (Exercicio) obj;
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
	}
}
