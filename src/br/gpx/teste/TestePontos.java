package br.gpx.teste;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import br.gpx.exception.PontoInvalidoException;
import br.gpx.modelo.Ponto;

/**
 * Classe para testar se os pontos sao validos
 */
public class TestePontos {

	/*
	 * O sistema deve ser capaz de reconhecer que uma latitude ou longitude eh
	 * invalida e levantar excecoes quando estas forem inseridas
	 */
	@SuppressWarnings("unused")
	@Test
	public void testPonto() {
		try {
			Ponto p1 = new Ponto(-95, 110, 200, new Date());
			fail(); //Se nenhuma excecao acontecer o teste deve falhar
		} catch (PontoInvalidoException e) {
			assertTrue(true);
		}
		try {
			Ponto p2 = new Ponto(95, 110, 200, new Date());
			fail();
		} catch (PontoInvalidoException e) {
			assertTrue(true);
		}
		try {
			Ponto p3 = new Ponto(-45, 190, 200, new Date());
			fail();
		} catch (PontoInvalidoException e) {
			assertTrue(true);
		}
		try {
			Ponto p4 = new Ponto(45, -190, 200, new Date());
			fail();
		} catch (PontoInvalidoException e) {
			assertTrue(true);
		}

	}

}
