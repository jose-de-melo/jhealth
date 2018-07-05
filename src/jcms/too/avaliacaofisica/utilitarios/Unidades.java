package jcms.too.avaliacaofisica.utilitarios;
/**
 * Unidades usadas pela aplicação.
 * 
 * @author José do Carmo de Melo Silva
 * @since 0.1
 *
 */
public enum Unidades {
		/**
		 * Unidade que representa distâncias.
		 */
		DISTANCIA("Distância", "Km"),
		/**
		 * Unidade que representa calorias.
		 */
		CALORIA("Calorias", "kcal"),
		/**
		 * Unidade que representa elevações.
		 */
		ELEVACAO("Elevacão", "m"),
		/**
		 * Unidade que representa minutos.
		 */
		MINUTO("Minuto", "\'"),
		/**
		 * Unidade que representa segundos.
		 */
		SEGUNDO("Segundo", "\""), 
		/**
		 * Unidade que representa velocidades.
		 */
		VELOCIDADE("Velocidade", "Km/h"),
		/**
		 * Unidade que representa ritmos.
		 */
		RITMO("Ritmo", "/Km"),
		/**
		 * Unidade que representa passos.
		 */
		PASSOS("Passos", "Número de Passos");
		
		private String nome;
		private String unidade;
		
		private Unidades(String nome, String unidade) {
			this.nome = nome;
			this.unidade = unidade;
		}

		/**
		 * Retorna o nome da enumeração.
		 * 
		 * @return <code>string</code> : nome da enumeração.
		 */
		public String getNome() {
			return nome;
		}

		/**
		 * Define o nome da enumeração.
		 * 
		 * @param nome - <code>string</code> : nome da enumeração a ser armazenado
		 */
		public void setNome(String nome) {
			this.nome = nome;
		}

		/**
		 * Retorna a unidade usada pela enumeração.
		 * 
		 * @return <code>string</code> : unidade usada pela enumeração.
		 */
		public String getUnidade() {
			return unidade;
		}

		/**
		 * Define a unidade usada pela enumeração.
		 * 
		 * @param unidade - <code>string</code> : unidade usada pela enumeração a ser armazenada.
		 */
		public void setUnidade(String unidade) {
			this.unidade = unidade;
		}
		
}