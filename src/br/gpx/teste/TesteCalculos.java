package br.gpx.teste;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import br.gpx.cleaner.GPXCleaner;
import br.gpx.exception.PontoInvalidoException;
import br.gpx.modelo.Ponto;

/**
 * Classe com os testes para os calculos 
 */
public class TesteCalculos {

	/*
	 * Testa se a distancia entre dois pontos esta correta
	 * Pode ser conferido em: http://www.movable-type.co.uk/scripts/latlong.html
	 */
	@Test 
	 public void testeDistanciaEntrePontos() throws PontoInvalidoException {
	  Ponto p1 = new Ponto(); 
	  p1.setLatitude(-23);
	  p1.setLongitude(-43);
	  Ponto p2 = new Ponto( ); 
	  p2.setLatitude(-23);
	  p2.setLongitude(-46);
	  assertEquals(307.061, GPXCleaner.calcularDistanciaEntrePontos(p1, p2), 0.001);
	  p2.setLatitude(-5);
	  p2.setLongitude(-35);
	  assertEquals(2177.998, GPXCleaner.calcularDistanciaEntrePontos(p1, p2), 0.001);
	  p2.setLatitude(0);
	  p2.setLongitude(-51);
	  assertEquals(2699.817, GPXCleaner.calcularDistanciaEntrePontos(p1, p2), 0.001);
	 } 
}