package jcms.too.avaliacaofisica.arquivos;

import java.io.IOException;

import jcms.too.avaliacaofisica.utilitarios.Constantes;
import br.mos.arquivo.aleatorio.BinaryFile;

/**
 * Fornece operações para um arquivo binário usando os serviços de um arquivo com acesso aleatório.
 * O tipo de dado do registro do arquivo é {@link IndiceUsuario}.
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.1
 */
public class ArquivoIndiceUsuario extends BinaryFile {

	private static final int TAMANHO_REGISTRO = 244;
	
	/**
	 * Lê um objeto de um {@link ArquivoIndiceUsuario}.
	 * @return o objeto {@link IndiceUsuario} lido.
	 * @throws IOException se houver algum erro de E/S.
	 */
	@Override
	public IndiceUsuario readObject() throws IOException {
		return new IndiceUsuario(readString(Constantes.TAMANHO_STRING_NOME_USUARIO), readString(Constantes.TAMANHO_STRING_EMAIL), randomAccessFile.readInt());
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
	 * Escreve um objeto {@link IndiceUsuario} como um registro do arquivo.
	 * 
	 * @param obj - um Object que será armazenado no arquivo.
	 * @throws IOException se ocorrer algum erro de E/S.
	 * @throws ClassCastException se o objeto passado como parâmetro não for um {@link IndiceUsuario}
	 */
	@Override
	public void writeObject(Object obj) throws IOException {
		IndiceUsuario indice;
		
		if(obj instanceof IndiceUsuario)
			indice = (IndiceUsuario) obj;
		else
			throw new ClassCastException();
		
		randomAccessFile.writeChars(setStringLength(indice.getNomeUser(), Constantes.TAMANHO_STRING_NOME_USUARIO));
		randomAccessFile.writeChars(setStringLength(indice.getEmailUser(), Constantes.TAMANHO_STRING_EMAIL));
		randomAccessFile.writeInt(indice.getNumeroRegistro());
	}
}