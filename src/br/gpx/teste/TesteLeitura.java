package br.gpx.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.gpx.IO.LeitorGPX;
import br.gpx.modelo.Trajeto;

/**
 * Classe de teste para as classes de leitura de um arquivo GPX
 */
public class TesteLeitura {

  /*
   * Esse teste deve ser capaz de ler uma string e converte-la em um
   * Objeto do tipo Trajeto
   */
	@Test
	public void testLeTrajetoDeString() {
		String teste = "<trk>" 
				+ "<trkseg>" 
				+ "<trkpt lat=\"44.03328292\" lon=\"-123.0880082\">"
				+ "<ele>107.805054</ele>" 
				+ "<time>2007-02-18T03:47:37Z</time>" 
				+ "</trkpt>" 
				+ "</trkseg>" 
				+ "</trk>";
		Trajeto trajeto = LeitorGPX.lerTrajeto(teste);
		assertNotNull(trajeto);
		assertNotNull(trajeto.getSegmentos());
		assertNotNull(trajeto.getSegmentos().get(0).getPontos());
		assertEquals(1, trajeto.getSegmentos().size());
		assertEquals(1, trajeto.getSegmentos().get(0).getPontos().size()); 
		/*
		 * O metodo que verifica doubles pede uma variacao pela imprecisao do
		 * double, mas nesse caso nao deve haver imprecisao
		 */
		assertEquals(44.03328292, trajeto.getSegmentos().get(0).getPontos().get(0).getLatitude(), 0);
		assertEquals(-123.0880082, trajeto.getSegmentos().get(0).getPontos().get(0).getLongitude(), 0);
		assertEquals(107.805054, trajeto.getSegmentos().get(0).getPontos().get(0).getElevacao(), 0);
	}

	/* Esse teste deve ser capaz de ler um arquivo e retornar um objeto Trajeto
	 * Para esse teste sera utilizado o arquivo testeLeitura.gpx que contem 1
	 * Segmento com 7 Pontos 
	 */
	@Test
	public void testeCarregarArquivo() {
		Trajeto trajeto = LeitorGPX.carregarArquivo("testeLeitura.gpx");
		assertNotNull(trajeto);
		assertNotNull(trajeto.getSegmentos());
		assertNotNull(trajeto.getSegmentos().get(0).getPontos());
		assertEquals(1, trajeto.getSegmentos().size());
		assertEquals(7, trajeto.getSegmentos().get(0).getPontos().size());
	}

	/* 
	 * Esse teste deve ser capaz de ler um arquivo "Century" 
	 */
	@Test
	public void testeCarregarArquivoCentury() {
		Trajeto trajeto = LeitorGPX.carregarArquivo("Century-2007-02-18.gpx");
		/*
		 * O arquivo deve conter um trajeto valido
		 */
		assertNotNull(trajeto);
		assertNotNull(trajeto.getSegmentos());
		assertNotNull(trajeto.getSegmentos().get(0).getPontos());
	}

}
