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
import br.gpx.cleaner.GPXCleaner;
import br.gpx.exception.PontoInvalidoException;
import br.gpx.modelo.Ponto;
import br.gpx.modelo.Segmento;
import br.gpx.modelo.Trajeto;

/**
 * Classe de teste para as classes de gravacao de um arquivo GPX
 */
public class TesteGravacao {

   /* 
    * Esse teste verifica se um arquivo e criado a partir da classe GravacaoGPX
    * utilizando um ponto criado no codigo
    */
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
		GravacaoGPX.gerarArquivoXml("testeGravacaoELeitura.gpx", t);
		File f = new File("testeGravacaoELeitura.gpx");
		assertNotNull(f); // verifica se o arquivo foi criado realmente
		/*
		 * Depois que gera o arquivo, o programa valida e verifica se os valores foram
		 * os mesmos fornecidos
		 */
		Trajeto trajeto = LeitorGPX.carregarArquivo("testeGravacaoELeitura.gpx");
		assertNotNull(trajeto);
		assertEquals(trajeto.getNome(), "TEST_LOG");
		assertNotNull(trajeto.getSegmentos());
		assertNotNull(trajeto.getSegmentos().get(0).getPontos());
		assertEquals(1, trajeto.getSegmentos().size(), 1);
		assertEquals(1, trajeto.getSegmentos().get(0).getPontos().size());
		assertEquals(dt, trajeto.getSegmentos().get(0).getPontos().get(0).getData());
		/*
		 *  O metodo que verifica doubles pede uma variacao pela imprecisao do
		 *  double, mas nesse caso nao deve haver imprecisao
		 */
		assertEquals(44.03, trajeto.getSegmentos().get(0).getPontos().get(0).getLatitude(), 0);
		assertEquals(-100.2, trajeto.getSegmentos().get(0).getPontos().get(0).getLongitude(), 0);
		assertEquals(200, trajeto.getSegmentos().get(0).getPontos().get(0).getElevacao(), 0);
	}

	/* Esse teste verifica se o clone de um arquivo gpx possui os mesmos
	 * atributos do arquivo "Century" original
	 */ 
	@Test
	public void testeClonarArquivo() {
		Trajeto trajeto = LeitorGPX.carregarArquivo("Century-2007-03-25.gpx");
		assertNotNull(trajeto);
		assertNotNull(trajeto.getSegmentos());
		assertNotNull(trajeto.getSegmentos().get(0).getPontos());
		GravacaoGPX.gerarArquivoXml("testeCloneCentury.gpx", trajeto);
		Trajeto trajetoClone = LeitorGPX.carregarArquivo("testeCloneCentury.gpx");
		assertEquals(trajeto.getSegmentos().size(), trajetoClone.getSegmentos().size());
		/*
		 *  Verifica se todos os segmentos do clone possuem a mesma quantidade de
		 *  pontos do original
		 */
		for (int i = 0; i < trajeto.getSegmentos().size(); i++) {
			assertEquals(trajeto.getSegmentos().get(i).getPontos().size(),
					trajetoClone.getSegmentos().get(i).getPontos().size());
		}
	}

	/* Esse teste verifica se o clone de um arquivo gpx com segmento unico
	 * possui os mesmos atributos do arquivo "Century" original 
	 */
	@Test
	public void testeClonarArquivoSegmentoUnico() {
		Trajeto trajeto = LeitorGPX.carregarArquivo("Century-2007-03-25.gpx");
		trajeto = GPXCleaner.reduzirParaSegmentoUnico(trajeto);
		assertNotNull(trajeto);
		assertNotNull(trajeto.getSegmentos());
		assertNotNull(trajeto.getSegmentos().get(0).getPontos());
		GravacaoGPX.gerarArquivoXml("testeCloneSegmentoUnico.gpx", trajeto);
		Trajeto trajetoClone = LeitorGPX.carregarArquivo("testeCloneSegmentoUnico.gpx");
		assertEquals(trajeto.getSegmentos().size(), trajetoClone.getSegmentos().size());
		assertEquals(trajeto.getSegmentos().get(0).getPontos().size(),
				trajetoClone.getSegmentos().get(0).getPontos().size());
	}
}