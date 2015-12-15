package br.gpx.cleaner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.gpx.modelo.Ponto;
import br.gpx.modelo.Segmento;
import br.gpx.modelo.Trajeto;

public class GPXCleaner {

	/**
	 * Unifica em um segmento unico o trajeto e reduz a partir da porcentagem especificada
	 * @param porcentagem
	 * @param trajeto
	 * @return
	 */
	public static Trajeto reduzirPorPorcentagem(int porcentagem, Trajeto trajeto){
		trajeto = reduzirParaSegmentoUnico(trajeto);
		List<Ponto> todosPontos = trajeto.getSegmentos().get(0).getPontos();
		List<Ponto> pontosReduzidos = new ArrayList<Ponto>();
		int quantidadePontosInicial = todosPontos.size();
		int quantidadePontosRemover = (int) ((quantidadePontosInicial*porcentagem)/100);
		int mod = quantidadePontosInicial / quantidadePontosRemover;
		for (int i = 0; i < todosPontos.toArray().length; i++) {
			if(i%mod != 0){
				pontosReduzidos.add(todosPontos.get(i));
			}
		}
		trajeto.getSegmentos().get(0).setPontos(pontosReduzidos);
		return trajeto;
	}
	
	/**
	 * Calcula a o segmento de reta com o tamanho relativo a distancia dos pontos a e b
	 * @param a
	 * @param b
	 * @return
	 */
	private static double calcularDistanciaEntrePontos(Ponto a, Ponto b){
		return Math.sqrt(Math.pow((a.getLatitude() - b.getLatitude()), 2) + Math.pow((a.getLongitude() - b.getLongitude()),2));
	}
	
	
	/**
	 * Calcula a area utilizando o determinante de uma matriz construida
	 * a partir dos pontos (x,y) de a, b e c em que x = latitude e y = longitude
	 *  e a ultima coluna da matriz eh preenchida com 1
	 * ABS(Ax * By + Ay * Cx + Bx * Cy - Ax * Cy - Ay * Bx - By * Cx)/2
	 * depois a altura a partir da formula 
	 * A = (bh)/2  >>> h = 2A/b
	 */
	private static double calcularDesvio(Ponto a, Ponto b, Ponto c){
		double areaTriangulo = Math.abs((a.getLatitude() * b.getLongitude() + a.getLongitude() * c.getLatitude() 
				+ b.getLatitude() * c.getLongitude() - a.getLatitude() * c.getLongitude() 
				- a.getLongitude() * b.getLatitude() - b.getLongitude() * c.getLatitude()))/2;
		double base = calcularDistanciaEntrePontos(a, c);
		return ((2 * areaTriangulo)/base);
	}
	
	/**
	 * Reduz o trajeto triangulando os segmentos e retornando os pontos apenas o se o desvio gerado 
	 * eh maior que o especificado em KM
	 * @param distancia
	 * @param trajeto
	 * @return
	 */
	public static Trajeto reduzirPorDistancia(double distancia, Trajeto trajeto){
		trajeto = reduzirParaSegmentoUnico(trajeto);
		List<Ponto> todosPontos = trajeto.getSegmentos().get(0).getPontos();
		List<Ponto> pontosReduzidos = new ArrayList<Ponto>();
		Iterator<Ponto> tp = todosPontos.iterator();
		Ponto a = tp.next(), b = tp.next(), c = null;
		pontosReduzidos.add(a);
		while (tp.hasNext()) {
			c = tp.next();
			//calcula o desvio entre os 3 pontos com a distancia minima especificada, se for maior, adiciona na lista se for menor, nao 
			if(calcularDesvio(a, b, c)> distancia){
				pontosReduzidos.add(b);
				//se adicionar o ponto B, este passa a ser o ponto A
				a = b;
			}
			//o ponto C entao vira B, e C recebe um novo ponto no proximo loop do if
			b = c;
		}
		//Por fim adiciona o ultimo C ao segmento
		pontosReduzidos.add(c);
		//Troca os trackpoints do segmento 1(unico) pelos pontos apos a reducao 
		trajeto.getSegmentos().get(0).setPontos(pontosReduzidos);
		return trajeto;
	}
	
	private static Trajeto reduzirParaSegmentoUnico(Trajeto trajeto){
		Segmento segmento = new Segmento();
		for (Segmento se : trajeto.getSegmentos()) {
			segmento.getPontos().addAll(se.getPontos());
		}
		List<Segmento> segmentos = new ArrayList<Segmento>();
		segmentos.add(segmento);
		Trajeto trajetoReduzido = new Trajeto(trajeto.getNome(), segmentos);
		return trajetoReduzido;
	}
	
	
}
