package br.gpx.teste;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.gpx.cleaner.GPXCleaner;
import br.gpx.modelo.Ponto;
import br.gpx.modelo.Segmento;
import br.gpx.modelo.Trajeto;

public class TesteReducao {

	//Metodo que junta todos os segmentos em um segmento unico
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
		//reduz e verifica se apenas um segmento restou com todos os pontos
		t = GPXCleaner.reduzirParaSegmentoUnico(t);
		assertEquals(1, t.getSegmentos().size());
		assertEquals(5, t.getSegmentos().get(0).getPontos().size());
	}
	
	//Metodo que verifica a funcionalidade da reducao por porcentagem
	@Test
	public void testeReducaoPorPorcentagem() {
		Ponto p1 = new Ponto(), p2 = new Ponto(), p3 = new Ponto(), p4 = new Ponto(), p5 = new Ponto();
		Segmento s = new Segmento();
		s.getPontos().add(p1);
		s.getPontos().add(p2);
		s.getPontos().add(p3);
		s.getPontos().add(p4);
		s.getPontos().add(p5);
		Trajeto t = new Trajeto();
		t.getSegmentos().add(s);
		//Tamanho inicial 5
		assertEquals(5, t.getSegmentos().get(0).getPontos().size());
		t = GPXCleaner.reduzirPorPorcentagem(20, t);
		//-20% = 4 pontos
		assertEquals(4, t.getSegmentos().get(0).getPontos().size());
		t = GPXCleaner.reduzirPorPorcentagem(50, t);
		//-50% = 2 pontos
		assertEquals(2, t.getSegmentos().get(0).getPontos().size());
	}
	
	//Metodo que verifica a funcionalidade de reducao por distancia
	@Test
	public void testReducaoPorDistancia() {
		//O sistema nao valida verifica datas nem elevacao entao podemos usar como null e um valor generico pra elevacao 100
		Ponto p1 = new Ponto(40, 100, 100, null), p2 = new Ponto(40.001, 100.002, 100, null), p3 = new Ponto(40.002, 100, 100, null);
		//0.002 de diferenca no eixo y entre os dois pontos no eixo produz um desvio de cerca de 2 metros
		//                  o p2(40.001, 100.002) 
		//     o            |              o
		//  p1(40,100)     +-2m           p3(40.002,100)
		Segmento s = new Segmento();
		s.getPontos().add(p1);
		s.getPontos().add(p2);
		s.getPontos().add(p3);
		Trajeto t = new Trajeto();
		t.getSegmentos().add(s);
		assertEquals(3, t.getSegmentos().get(0).getPontos().size());
		//fazendo uma reducao por 1m os 3 pontos devem manter-se uma vez que o desvio foi de cerca de 2 metros, portanto maior que o especificado
		t = GPXCleaner.reduzirPorDistancia(0.001, t);
		assertEquals(3, t.getSegmentos().get(0).getPontos().size());
		t = GPXCleaner.reduzirPorDistancia(0.01, t);
		// uma reducao de 10 metros contudo remove o segundo ponto mantendo apenas o inicial e o final
		assertEquals(2, t.getSegmentos().get(0).getPontos().size());
	}
	

}
