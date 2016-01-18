package br.gpx.teste;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import br.gpx.exception.PontoInvalidoException;
import br.gpx.modelo.Ponto;

public class TestePontos {

	@SuppressWarnings("unused")
	@Test
	public void testPonto() {
		try {
			Ponto p1 = new Ponto(-95, 110, 200, new Date());
			fail();
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
