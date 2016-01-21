package br.gpx.interfaces;

import br.gpx.IO.LeitorGPX;
import br.gpx.IO.GravacaoGPX;
import br.gpx.cleaner.GPXCleaner;
import br.gpx.modelo.Trajeto;

/**
 * Classe com a interface de linha de comando
 *
 */
public class LinhaDeComando {

	public static void main(String[] args) {
		String stringPercentual, nomeOriginal, nomeFinal;
		int percentual = 0;
		double distancia = 0;
		/*
		 * Verifica se todos os parametros foram fornecidos
		 */
		if (args.length == 3) {
			stringPercentual = args[0];
			nomeOriginal = args[1];
			nomeFinal = args[2];
		} else {
			System.out.println("Quantidade de parametros invalida");
			return;
		}
		try {
			/* 
			 * Decide se o usuario informou percentual ou desvio de distancia 
			 */
			if (stringPercentual.contains("%")) {
				stringPercentual = stringPercentual.replace("%", "");
				percentual = Integer.parseInt(stringPercentual);
				if (percentual >= 100 || percentual <= 0) {
					throw new Exception("Percentual Invalido");
				}
			} else {
				distancia = Double.parseDouble(stringPercentual);
				if (distancia <= 0) {
					throw new Exception("Distancia invalida");
				}
			}
		} catch (Exception e) {
			System.err.printf("Erro: %s.\n", e.getMessage());
			return;
		}
		Trajeto trajeto = LeitorGPX.carregarArquivo(nomeOriginal);
		/* 
		 * Reduz a partir do criterio especificado
		 */
		if (distancia != 0)
			trajeto = GPXCleaner.reduzirPorDistancia(distancia, trajeto);
		else
			trajeto = GPXCleaner.reduzirPorPorcentagem(percentual, trajeto);
		System.out.println(GPXCleaner.getDadosTrajeto(trajeto));
		GravacaoGPX.gerarArquivoXml(nomeFinal, trajeto);
		System.out.println("Reducao realizada com sucesso!!");
	}
}