package br.gpx.IO;

import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import br.gpx.modelo.GPX;
import br.gpx.modelo.Trajeto;

/**
 * Classe responsavel pela geracao dos arquivos gpx
 *
 */
public class GravacaoGPX {
	
	/**
	 * Grava um arquivo .gpx a partir do trajeto informado com o nomeArquivo especificado
	 * @param nomeArquivo
	 * @param trajeto
	 */
	public static void gerarArquivoXml(String nomeArquivo, Trajeto trajeto){
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(GPX.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			GPX gpx = new GPX(trajeto);
			OutputStream os = new FileOutputStream(nomeArquivo);
			jaxbMarshaller.marshal(gpx, os );
		} catch (Exception e) {
			System.err.printf("Erro: %s.\n", e.getMessage()); 
		}
	}

}
