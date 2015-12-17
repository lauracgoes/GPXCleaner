package br.gpx.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import br.gpx.modelo.Trajeto;

/**
 * Classe responsavel pela leitura de dados
 *
 */
public class LeitorGPX {
	
		
		/**
		 * Lê um arquivo GPX e retorna um elemento Trajeto 
		 * @param nomeArquivo
		 * @return
		 */
		public static Trajeto carregarArquivo(String nomeArquivo){
			String stringTrajeto = "";
			try { 
				FileReader arq = new FileReader(nomeArquivo); 
				BufferedReader lerArq = new BufferedReader(arq); 
				String linha = lerArq.readLine();  
				while (linha != null) { 
					stringTrajeto += linha;
					linha = lerArq.readLine(); 
				} 
				arq.close(); 
			} catch (IOException e) { 
				System.err.printf("Erro: %s.\n", e.getMessage()); 
			} 
			//Remove os elementos antes do Log Ativo
			stringTrajeto = stringTrajeto.substring(stringTrajeto.indexOf("<trk>"), stringTrajeto.indexOf("</gpx>"));
			return lerTrajeto(stringTrajeto);
		}

		/**
		 * Lê uma string e transforma em um elemento do tipo Track
		 * @param trackString
		 * @return 
		 */
		public static Trajeto lerTrajeto(String stringTrajeto){
			try {
				JAXBContext jaxb = JAXBContext.newInstance(Trajeto.class);
				Unmarshaller unmarshaller = jaxb.createUnmarshaller();
				StringReader reader = new StringReader(stringTrajeto);
				Trajeto t = (Trajeto) unmarshaller.unmarshal(reader);
				return t;
			} catch (JAXBException e) {
				e.printStackTrace();
			};
			return null;
		}
}
