package jcms.too.avaliacaofisica.arquivos;

import java.io.IOException;

import jcms.too.avaliacaofisica.utilitarios.Constantes;
import br.mos.arquivo.aleatorio.BinaryFile;

/**
 * Fornece operações para um arquivo binário usando os serviços de um arquivo com acesso aleatório.
 * O tipo de dado do registro do arquivo é {@link IndiceExercicio}.
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.1
 */
public class ArquivoIndiceExercicio extends BinaryFile {
	private static final int TAMANHO_REGISTRO_ARQUIVO = 314;
	
	/**
	 * Lê um objeto de um {@link ArquivoIndiceExercicio}.
	 * @return o objeto {@link IndiceExercicio} lido.
	 * @throws IOException se houver algum erro de E/S.
	 */
	@Override
	public IndiceExercicio readObject() throws IOException {
		return new IndiceExercicio(readString(Constantes.TAMANHO_STRING_TIPO_EXERCICIO),
														readString(Constantes.TAMANHO_STRING_EMAIL), 
														readString(Constantes.TAMANHO_STRING_DATA),
														readString(Constantes.TAMANHO_STRING_TEMPO),
														readString(Constantes.TAMANHO_STRING_NOME_ARQUIVO), randomAccessFile.readInt());
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
	 * Escreve um objeto {@link IndiceExercicio} como um registro do arquivo.
	 * 
	 * @param obj - um Object que será armazenado no arquivo.
	 * @throws IOException se ocorrer algum erro de E/S.
	 * @throws ClassCastException se o objeto passado como parâmetro não for um {@link IndiceExercicio}
	 */
	@Override
	public void writeObject(Object obj) throws IOException, ClassCastException {
		IndiceExercicio indice;
		
		if(obj instanceof IndiceExercicio){
			indice = (IndiceExercicio) obj;
		}
		else
			throw new ClassCastException();
		
		randomAccessFile.writeChars(setStringLength(indice.getTipoExercicio(), Constantes.TAMANHO_STRING_TIPO_EXERCICIO));
		randomAccessFile.writeChars(setStringLength(indice.getChaveEmail(), Constantes.TAMANHO_STRING_EMAIL));
		randomAccessFile.writeChars(setStringLength(indice.getChaveData(), Constantes.TAMANHO_STRING_DATA));
		randomAccessFile.writeChars(setStringLength(indice.getChaveTempo(), Constantes.TAMANHO_STRING_TEMPO));
		randomAccessFile.writeChars(setStringLength(indice.getNomeArquivo(), Constantes.TAMANHO_STRING_NOME_ARQUIVO));
		randomAccessFile.writeInt(indice.getNumeroRegistroNoArquivo());
	}
}