package br.gpx.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import br.gpx.IO.GravacaoGPX;
import br.gpx.IO.LeitorGPX;
import br.gpx.exception.PontoInvalidoException;
import br.gpx.modelo.Ponto;
import br.gpx.modelo.Segmento;
import br.gpx.modelo.Trajeto;

/**
 * Classe de teste para as classes de leitura e gravacao de arquivo GPX
 *
 */
public class TesteLeitorGravacao {
	

	// O Metodo deve ser capaz de ler uma string e converte-la em um
	// Objeto do tipo Trajeto
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
		//O metodo que verifica doubles pede uma variacao pela imprecisao do double, mas nesse caso nao deve haver imprecisao
		assertEquals(44.03328292, trajeto.getSegmentos().get(0).getPontos().get(0).getLatitude(), 0);
		assertEquals(-123.0880082, trajeto.getSegmentos().get(0).getPontos().get(0).getLongitude(), 0);
		assertEquals(107.805054, trajeto.getSegmentos().get(0).getPontos().get(0).getElevacao(), 0);
	}
	
	// O Metodo deve ser capaz de ler um arquivo e retornar um objeto Trajeto
	// Para esse teste sera utilizado o arquivo testeLeitura.gpx que contem 1
	// Segmento com 7 Pontos
	@Test
	public void testeCarregarArquivo() {
		Trajeto trajeto = LeitorGPX.carregarArquivo("testeLeitura.gpx");
		assertNotNull(trajeto);
		assertNotNull(trajeto.getSegmentos());
		assertNotNull(trajeto.getSegmentos().get(0).getPontos());
		assertEquals(1, trajeto.getSegmentos().size());
		assertEquals(7, trajeto.getSegmentos().get(0).getPontos().size());
	}
	
	//Esse teste verifica se um arquivo e criado a partir da classe GravacaoGPX utilizando um ponto criado 
	//no codigo
	@Test
	public void testeGravar() {
		Date dt = new Date();
		Ponto p = null;
		try {
			p = new Ponto(44.03, -100.2, 200, dt);
		} catch (PontoInvalidoException e) {
			fail(e.getMessage());
		}
		Segmento s = new Segmento();
		s.getPontos().add(p);
		List<Segmento> ls = new ArrayList<Segmento>();
		ls.add(s);
 		Trajeto t = new Trajeto("TEST_LOG", ls);
		GravacaoGPX.gerarArquivoXml("testeGravacaoELeitura.gpx",t);
		//verifica se o arquivo foi criado realmente
		File f = new File("testeGravacaoELeitura.gpx");
		assertNotNull(f);
		//Depois que gera o arquivo, ele valida e verifica se os valores foram os mesmos fornecidos
		Trajeto trajeto = LeitorGPX.carregarArquivo("testeGravacaoELeitura.gpx");
		assertNotNull(trajeto);
		assertEquals(trajeto.getNome(), "TEST_LOG");
		assertNotNull(trajeto.getSegmentos());
		assertNotNull(trajeto.getSegmentos().get(0).getPontos());
		assertEquals(1, trajeto.getSegmentos().size(), 1);
		assertEquals(1, trajeto.getSegmentos().get(0).getPontos().size());
		assertEquals(dt, trajeto.getSegmentos().get(0).getPontos().get(0).getData());
		//O metodo que verifica doubles pede uma variacao pela imprecisao do double, mas nesse caso nao deve haver imprecisao
		assertEquals(44.03, trajeto.getSegmentos().get(0).getPontos().get(0).getLatitude(), 0);
		assertEquals(-100.2, trajeto.getSegmentos().get(0).getPontos().get(0).getLongitude(), 0);
		assertEquals(200, trajeto.getSegmentos().get(0).getPontos().get(0).getElevacao(), 0);
	}

}
