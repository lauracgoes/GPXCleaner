package br.gpx.cleaner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import br.gpx.modelo.Ponto;
import br.gpx.modelo.Segmento;
import br.gpx.modelo.Trajeto;

/**
 * Classe responsavel por fazer as reducoes dos pontos
 *
 */
public class GPXCleaner {
	
	/**
	 * Unifica em um segmento unico o trajeto e reduz a partir da porcentagem
	 * especificada
	 * 
	 * @param porcentagem
	 * @param trajeto
	 * @return
	 */
	public static Trajeto reduzirPorPorcentagem(int porcentagem, Trajeto trajeto) {
		trajeto = reduzirParaSegmentoUnico(trajeto);
		List<Ponto> todosPontos = trajeto.getSegmentos().get(0).getPontos();
		/*
		 * Calcula quantos pontos devem ser reduzidos
		 */
		double quantidadeInicial = todosPontos.size();
		double quantidadeFinal = quantidadeInicial - ((quantidadeInicial * porcentagem) / 100);
		double menorDistancia = calcularMenorDesvio(todosPontos);
		/*
		 * Enquanto a quantidade de pontos for maior que a quantidade que deve
		 * retornar remove os pontos que possuam menor distancia entre eles
		 */
		while (todosPontos.size() > quantidadeFinal) {
			menorDistancia = calcularMenorDesvio(todosPontos);
			for (int i = 1; i < todosPontos.size() - 1; i++) {
				double distanciaAtual = calcularDesvio(todosPontos.get(i - 1), todosPontos.get(i),
						todosPontos.get(i + 1));
				if (distanciaAtual == menorDistancia) {
					todosPontos.remove(i);
					break;
				}
			}
		}
		// System.out.println("Quantidade de pontos inicial: " + quantidadeInicial + " Quantidade de pontos final: " + todosPontos.size());
		trajeto.getSegmentos().get(0).setPontos(todosPontos);
		return trajeto;
	}

	/**
	 * Verifica o menor desvio entre os pontos e retorna a menor distancia
	 * entre eles, o metodo elimina o primeiro e o ultimo pontos da trilha
	 * 
	 * @param pontos
	 * @return
	 */
	private static double calcularMenorDesvio(List<Ponto> pontos) {
		double menorDistancia = 1000;
		/*
		 * verifica a menor distancia exceto o primeiro (i = 1) e ultimo ponto (size -1)
		 */
		for (int i = 1; i < pontos.size() - 1; i++) {
			double menorDistanciaCalculada = calcularDesvio(pontos.get(i - 1), pontos.get(i), pontos.get(i + 1));
			if (menorDistanciaCalculada < menorDistancia) {
				/*
				 * Se a menor distancia calculada for menor que a menor
				 * distancia atual esta passa a ser a menor distancia
				 */
				menorDistancia = menorDistanciaCalculada;
			}
		}
		return menorDistancia;
	}
	
	/**
	 * Calcula a o segmento de reta com o tamanho relativo a distancia dos
	 * pontos a e b utilizando a formula do Haversine
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double calcularDistanciaEntrePontos(Ponto a, Ponto b) {
		double raio = 6371; // Distancia obtida a partir do raio medio da terra = 6.371 km
		double dlong = Math.toRadians(b.getLongitude() - a.getLongitude());
		double dlat = Math.toRadians(b.getLatitude() - a.getLatitude());
		double hav = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(Math.toRadians(a.getLatitude()))
				* Math.cos(Math.toRadians(b.getLatitude())) * Math.pow(Math.sin(dlong / 2), 2);
		double distanciaAngular = 2 * Math.atan2(Math.sqrt(hav), Math.sqrt(1 - hav));
		double distanciaKm = raio * distanciaAngular;
		return distanciaKm;
	}

	/**
	 * Calcula a distancia do ponto atual B a reta formada pelo ponto anterior e
	 * o proximo ponto
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	private static double calcularDesvio(Ponto a, Ponto b, Ponto c) {
		Double ladoAC = calcularDistanciaEntrePontos(a, c);
		Double ladoAB = calcularDistanciaEntrePontos(a, b);
		Double ladoCB = calcularDistanciaEntrePontos(c, b);
		/*
		 * Calculo da area do triangulo ABC pelo Teorema de Heron
		 */
		Double semiPerimetro = (ladoAC + ladoAB + ladoCB) / 2;
		Double area = Math
				.sqrt(semiPerimetro * (semiPerimetro - ladoAC) * (semiPerimetro - ladoAB) * (semiPerimetro - ladoCB));
		/*
		 * A distancia e dada atravez da altura do triangulo com relacao a
		 * baseAC (h = 2*area/base)
		 */
		return (2 * area) / ladoAC;
	}

	/**
	 * Reduz o trajeto triangulando os segmentos e retornando os pontos apenas o
	 * se o desvio gerado eh maior que o especificado em KM
	 * 
	 * @param distancia
	 * @param trajeto
	 * @return
	 */
	public static Trajeto reduzirPorDistancia(double distancia, Trajeto trajeto) {
		trajeto = reduzirParaSegmentoUnico(trajeto);
		List<Ponto> todosPontos = trajeto.getSegmentos().get(0).getPontos();
		/*
		 * Enquanto a menor distancia entre todos os pontos for menor que a distancia especificada
		 * ainda devem ser removidos pontos
		 */
		double menorDistancia = calcularMenorDesvio(todosPontos);
		while ( menorDistancia < distancia) {
			for (int i = 1; i < todosPontos.size() - 1; i++) {
				double distanciaAtual = calcularDesvio(todosPontos.get(i - 1), todosPontos.get(i),
						todosPontos.get(i + 1));
				if (distanciaAtual == menorDistancia) {
					/*
					 *  Se a distancia atua for a menor, remove o ponto
					 *  e recalcula a distancia, sempre eliminando os extremos
					 */
					todosPontos.remove(i); 
					menorDistancia = calcularMenorDesvio(todosPontos);
					break;
				}
			}
		}
		trajeto.getSegmentos().get(0).setPontos(todosPontos);
		return trajeto;
	}

	/**
	 * Metodo que junta todos os segmentos de um trajeto em um segmento unico
	 * 
	 * @param trajeto
	 * @return
	 */
	public static Trajeto reduzirParaSegmentoUnico(Trajeto trajeto) {
		Segmento segmento = new Segmento();
		for (Segmento se : trajeto.getSegmentos()) {
			segmento.getPontos().addAll(se.getPontos());
		}
		List<Segmento> segmentos = new ArrayList<Segmento>();
		segmentos.add(segmento);
		Trajeto trajetoReduzido = new Trajeto(trajeto.getNome(), segmentos);
		return trajetoReduzido;
	}

	/**
	 * Retorna uma string com a distancia percorrida, tempo gasto e velocidade
	 * media no trajeto
	 * 
	 * @param trajeto
	 * @return
	 */
	public static String getDadosTrajeto(Trajeto trajeto) {
		trajeto = reduzirParaSegmentoUnico(trajeto);
		double distanciaTotal = 0;
		List<Ponto> pontos = trajeto.getSegmentos().get(0).getPontos();
		Date horaInicial = pontos.get(0).getData();
		Date horaFinal = pontos.get(pontos.size() - 1).getData();
		/*
		 * Soma a distancia entre todos os pontos
		 */
		for (int i = 1; i < pontos.size(); i++) {
			distanciaTotal += calcularDistanciaEntrePontos(pontos.get(i - 1), pontos.get(i));
		}
		double dataFinal = (horaFinal.getTime() - horaInicial.getTime()) / 1000 / 60 / 60; 
		/*
		 *  Pega o tempo total em horas
		 */
		String dadosTrajeto = String.format(
				"Distancia Total Percorrida: %.2f km em %.2f horas \nA uma velocidade media de: %.2f km/h \n", 
				distanciaTotal, dataFinal, (distanciaTotal / dataFinal));
		/*
		 *  Cria a string com os decimais truncados na segunda casa decimal
		 */
		return dadosTrajeto;
	}
}