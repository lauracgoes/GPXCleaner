package br.gpx.teste;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.gpx.IO.LeitorGPX;
import br.gpx.cleaner.GPXCleaner;
import br.gpx.exception.PontoInvalidoException;
import br.gpx.modelo.Ponto;
import br.gpx.modelo.Segmento;
import br.gpx.modelo.Trajeto;

/**
 * Classe de teste para testar reducoes
 */ 
public class TesteReducao {
	
	/* 
	 * Esse teste deve juntar todos os segmentos em um segmento unico
	 */
	@Test
	public void testeReducaoParaSegmentoUnico() {
		Ponto p1 = new Ponto(), p2 = new Ponto(), p3 = new Ponto(), p4 = new Ponto(), p5 = new Ponto();
		Segmento s1 = new Segmento(), s2 = new Segmento(), s3 = new Segmento();
		s1.getPontos().add(p1);
		s1.getPontos().add(p2);
		s2.getPontos().add(p3);
		s2.getPontos().add(p4);
		s3.getPontos().add(p5);
		Trajeto t = new Trajeto();
		t.getSegmentos().add(s1);
		t.getSegmentos().add(s2);
		t.getSegmentos().add(s3);
		assertEquals(3, t.getSegmentos().size());
		/* 
		 * reduz e verifica se apenas um segmento restou com todos os pontos
		 */
		t = GPXCleaner.reduzirParaSegmentoUnico(t);
		assertEquals(1, t.getSegmentos().size());
		assertEquals(5, t.getSegmentos().get(0).getPontos().size());
	}

	/*
	 * Esse teste deve verificar a funcionalidade da reducao por porcentagem
	 * Reducoes de 60 e 45 %
	 */
	@Test
	public void testeReducaoPorPorcentagemArquivo() {
		Trajeto trajeto = LeitorGPX.carregarArquivo("Century-2007-02-18.gpx");
		assertNotNull(trajeto);
		assertNotNull(trajeto.getSegmentos());
		assertNotNull(trajeto.getSegmentos().get(0).getPontos());
		trajeto = GPXCleaner.reduzirParaSegmentoUnico(trajeto);
		assertEquals(1, trajeto.getSegmentos().size());
		double quantidadeInicial = trajeto.getSegmentos().get(0).getPontos().size();
		double quantidadeFinal = quantidadeInicial - ((quantidadeInicial * 60) / 100);
		trajeto = GPXCleaner.reduzirPorPorcentagem(60, trajeto);
		/*
		 *  Variacao de 1 para o caso de numeros menores que 1 na reducao percentual
		 */
		assertEquals(quantidadeFinal, trajeto.getSegmentos().get(0).getPontos().size(), 1);
		quantidadeInicial = trajeto.getSegmentos().get(0).getPontos().size();
		quantidadeFinal = quantidadeInicial - ((quantidadeInicial * 45) / 100);
		trajeto = GPXCleaner.reduzirPorPorcentagem(45, trajeto);
		/* 
		 * Variacao de 1 para o caso de numeros menores que 1 na reducao percentual
		 */
		assertEquals(quantidadeFinal, trajeto.getSegmentos().get(0).getPontos().size(), 1);
	}
	
	/*
	 * Esse teste deve verificar a funcionalidade da reducao por porcentagem
	 * Reducoes de 33 e 66 %
	 */
	@Test
	public void testeReducaoPorPorcentagemArquivo2() {
		Trajeto trajeto = LeitorGPX.carregarArquivo("Century-2007-03-25.gpx");
		assertNotNull(trajeto);
		assertNotNull(trajeto.getSegmentos());
		assertNotNull(trajeto.getSegmentos().get(0).getPontos());
		trajeto = GPXCleaner.reduzirParaSegmentoUnico(trajeto);
		assertEquals(1, trajeto.getSegmentos().size());
		double quantidadeInicial = trajeto.getSegmentos().get(0).getPontos().size();
		double quantidadeFinal = quantidadeInicial - ((quantidadeInicial * 33) / 100);
		trajeto = GPXCleaner.reduzirPorPorcentagem(33, trajeto);
		assertEquals(quantidadeFinal, trajeto.getSegmentos().get(0).getPontos().size(), 1);
		quantidadeInicial = trajeto.getSegmentos().get(0).getPontos().size();
		quantidadeFinal = quantidadeInicial - ((quantidadeInicial * 66) / 100);
		trajeto = GPXCleaner.reduzirPorPorcentagem(66, trajeto);
		assertEquals(quantidadeFinal, trajeto.getSegmentos().get(0).getPontos().size(), 1);
	}
	
	/*
	 * Esse teste deve verificar a funcionalidade da reducao por porcentagem
	 * Reducoes de 75 e 95 %
	 */
	@Test
	public void testeReducaoPorPorcentagemArquivo3() {
		Trajeto trajeto = LeitorGPX.carregarArquivo("Century-2007-04-08.gpx");
		assertNotNull(trajeto);
		assertNotNull(trajeto.getSegmentos());
		assertNotNull(trajeto.getSegmentos().get(0).getPontos());
		trajeto = GPXCleaner.reduzirParaSegmentoUnico(trajeto);
		assertEquals(1, trajeto.getSegmentos().size());
		double quantidadeInicial = trajeto.getSegmentos().get(0).getPontos().size();
		double quantidadeFinal = quantidadeInicial - ((quantidadeInicial * 75) / 100);
		trajeto = GPXCleaner.reduzirPorPorcentagem(75, trajeto);
		assertEquals(quantidadeFinal, trajeto.getSegmentos().get(0).getPontos().size(), 1);
		quantidadeInicial = trajeto.getSegmentos().get(0).getPontos().size();
		quantidadeFinal = quantidadeInicial - ((quantidadeInicial * 95) / 100);
		trajeto = GPXCleaner.reduzirPorPorcentagem(95, trajeto);
		assertEquals(quantidadeFinal, trajeto.getSegmentos().get(0).getPontos().size(), 1);
	}

	/* 
	 * Esse teste deve verificar a funcionalidade de reducao por distancia
	 */
	@Test
	public void testReducaoPorDistancia() {
		// O sistema nao valida datas nem elevacao, entao podemos usar
		// como null e um valor generico pra elevacao 100
		Ponto p1 = null, p2 = null, p3 = null;
		try {
			p1 = new Ponto(40, 100, 100, null);
			p2 = new Ponto(40.001, 100.001, 100, null);
			p3 = new Ponto(40.002, 100, 100, null);
		} catch (PontoInvalidoException e) {
			fail(e.getMessage());
		}
		/* 0.001 graus de diferenca no eixo y entre os dois pontos no eixo produz um
		 * desvio de cerca de 85 metros
		 * Pode ser conferido em: 
		 * http://www.movable-type.co.uk/scripts/latlong.html
		 * 
		 *              p2(40.001, 100.001)
		 * p1(40,100)  -  p4(40.001, 100)  -  p3(40.002,100)
		 * O desvio eh a distancia entre p2 e p4 (ponto contido na reta p1-p3)
		 */
		Segmento s = new Segmento();
		s.getPontos().add(p1);
		s.getPontos().add(p2);
		s.getPontos().add(p3);
		Trajeto t = new Trajeto();
		t.getSegmentos().add(s);
		assertEquals(3, t.getSegmentos().get(0).getPontos().size());
		/*
		 * Fazendo uma reducao por 1m os 3 pontos devem manter-se uma vez que o
		 * desvio foi de cerca de 85 metros, portanto maior que o especificado
		 */
		t = GPXCleaner.reduzirPorDistancia(0.001, t);
		assertEquals(3, t.getSegmentos().get(0).getPontos().size());
		t = GPXCleaner.reduzirPorDistancia(0.1, t);
		/* 
		 * uma reducao de 10 metros contudo remove o segundo ponto mantendo
		 * apenas o inicial e o final 
		 */
		assertEquals(2, t.getSegmentos().get(0).getPontos().size());
	}
}