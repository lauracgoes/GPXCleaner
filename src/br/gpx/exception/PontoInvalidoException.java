package br.gpx.exception;

/**
 * Quando uma latitude ou longitude for invalida, aparecer� uma mensagem.
 */
public class PontoInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	 public PontoInvalidoException(String messagem) {
	        super(messagem);
	 }
	

}
