package jcms.too.avaliacaofisica.arquivos;

import jcms.too.avaliacaofisica.modelo.ExercicioAerobico;
import jcms.too.avaliacaofisica.modelo.Ritmo;

/**
 * Classe usada para representar o registro de um arquivo da classe {@link ArquivoRitmo}
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.3
 * @see ArquivoRitmo
 */
public class RegistroRitmo {
	private Ritmo ritmo;
	private int numeroRegistro;
	
	/**
	 * Cria uma nova instância <code>RegistroRitmo</code> a partir dos parâmetros passados.
	 * 
	 * @param ritmo - objeto {@link Ritmo}
	 * @param numeroRegistro - <code><b>int</b></code> número do registro no arquivo onde o objeto {@link ExercicioAerobico} que contém o detém o <code>Ritmo</code>
	 * referenciado nesse objeto foi gravado.
	 */
	public RegistroRitmo(Ritmo ritmo, int numeroRegistro) {
		this.ritmo = ritmo;
		this.numeroRegistro = numeroRegistro;
	}

	/**
	 * Retorna o ritmo referenciado pelo objeto.
	 * 
	 * @return - {@link Ritmo} referenciado pelo objeto.
	 */
	public Ritmo getRitmo() {
		return ritmo;
	}

	/**
	 * Retorna o número do registro no arquivo onde o objeto {@link ExercicioAerobico} que contém o detém o <code>Ritmo</code>
	 * referenciado nesse objeto foi gravado.
	 * 
	 * @return <code><b>int</b></code> :  número do registro no arquivo onde o objeto {@link ExercicioAerobico} que contém o detém o <code>Ritmo</code>
	 * referenciado nesse objeto foi gravado.
	 */
	public int getNumeroRegistro() {
		return numeroRegistro;
	}
}