package br.gpx.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "trkseg")
public class Segmento {

	List<Ponto> pontos;

	public Segmento() {
		this.pontos = new ArrayList<Ponto>();
	}

	@XmlElements(value = { @XmlElement(name = "trkpt", type = Ponto.class) })
	public List<Ponto> getPontos() {
		return pontos;
	}

	public void setPontos(List<Ponto> pontos) {
		this.pontos = pontos;
	}

}
