package br.gpx.exception;

/**
 * Classe responsavel por validar a latitude e longitude. Se algum valor for
 * invalido, uma mensagem de erro sera exibida.
 */
public class PontoInvalidoException extends Exception {
	private static final long serialVersionUID = 1L;

	public PontoInvalidoException(String messagem) {
		super(messagem);
	}
}