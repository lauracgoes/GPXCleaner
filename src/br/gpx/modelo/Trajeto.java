package br.gpx.modelo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe do modelo que define o elemento "trk" do arquivo GPX Possui como
 * atributo uma lista segmentos
 *
 */
@XmlRootElement(name = "trk")
public class Trajeto {
	
	private String nome;
	private List<Segmento> segmentos;

	public Trajeto() {
		this.segmentos = new ArrayList<Segmento>();
	}

	public Trajeto(String nome, List<Segmento> trackSegments) {
		this.nome = nome;
		this.segmentos = trackSegments;
	}

	@XmlElement(name = "name")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@XmlElements(value = { @XmlElement(name = "trkseg", type = Segmento.class) })
	public List<Segmento> getSegmentos() {
		return segmentos;
	}

	public void setSegmentos(List<Segmento> segmentos) {
		this.segmentos = segmentos;
	}
}