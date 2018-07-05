package jcms.too.avaliacaofisica.arquivos;

import java.io.IOException;

import jcms.too.avaliacaofisica.modelo.Data;
import jcms.too.avaliacaofisica.modelo.Usuario;
import jcms.too.avaliacaofisica.utilitarios.Constantes;
import br.mos.arquivo.aleatorio.BinaryFile;

/**
 * Fornece operações para um arquivo binário usando os serviços de um arquivo com acesso aleatório.
 * O tipo de dado do registro do arquivo é {@link Usuario}.
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.1
 */
public class ArquivoUsuario extends BinaryFile {

	private static final int TAMANHO_REGISTRO_ARQUIVO = 340;
	
	@Override
	public Usuario readObject() throws IOException {
		Usuario usuario = new Usuario();
		
		usuario.setEmail(readString(Constantes.TAMANHO_STRING_EMAIL));
		usuario.setNome(readString(Constantes.TAMANHO_STRING_NOME_USUARIO));
		usuario.setSexo(readString(Constantes.TAMANHO_STRING_SEXO_USUARIO));
		usuario.setAltura(readString(Constantes.TAMANHO_STRING_ALTURA_USUARIO));
		usuario.setPeso(readString(Constantes.TAMANHO_STRING_PESO_USUARIO));
		usuario.setDataNascimento(new Data(readString(Constantes.TAMANHO_STRING_DATA)));
		
		return usuario;
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

	@Override
	public void writeObject(Object obj) throws IOException, ClassCastException {
		Usuario usuario;
		
		if(obj instanceof Usuario)
			usuario = (Usuario) obj;
		else
			throw new ClassCastException();
		
		
		randomAccessFile.writeChars(setStringLength(usuario.getEmail(), Constantes.TAMANHO_STRING_EMAIL));
		randomAccessFile.writeChars(setStringLength(usuario.getNome(), Constantes.TAMANHO_STRING_NOME_USUARIO));
		randomAccessFile.writeChars(setStringLength(usuario.getSexo(), Constantes.TAMANHO_STRING_SEXO_USUARIO));
		randomAccessFile.writeChars(setStringLength(usuario.getAltura(), Constantes.TAMANHO_STRING_ALTURA_USUARIO));
		randomAccessFile.writeChars(setStringLength(usuario.getPeso(), Constantes.TAMANHO_STRING_PESO_USUARIO));
		randomAccessFile.writeChars(setStringLength(usuario.getDataNascimento().toString(), Constantes.TAMANHO_STRING_DATA));
	}
}