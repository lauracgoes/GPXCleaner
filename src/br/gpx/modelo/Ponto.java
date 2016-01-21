package br.gpx.modelo;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.gpx.exception.PontoInvalidoException;

/**
 * Classe do modelo que define o elemento "trkpt" do arquivo GPX 
 * Possui os atributos de Latitude, Longitude, Elevacao e Data
 *
 */
@XmlRootElement(name = "trkpt")
public class Ponto {

	private double latitude;
	private double longitude;
	private double elevacao;
	private Date data;

	public Ponto() {
	}

	public Ponto(double latitude, double longitude, double elevacao, Date data) throws PontoInvalidoException {
		setLatitude(latitude);
		setLongitude(longitude);
		this.elevacao = elevacao;
		this.data = data;
	}

	@XmlAttribute(name = "lat")
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Quando a latitude for invalida deve ser disparada uma excecao
	 * 
	 * @param latitude
	 * @throws PontoInvalidoException
	 */
	public void setLatitude(double latitude) throws PontoInvalidoException {
		if (latitude < -90 || latitude > 90) {
			throw new PontoInvalidoException("Latitude deve ser estar entre -90 e 90");
		}
		this.latitude = latitude;
	}

	@XmlAttribute(name = "lon")
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Quando a longitude for invalida deve ser disparada uma excecao
	 * 
	 * @param longitude
	 * @throws PontoInvalidoException
	 */
	public void setLongitude(double longitude) throws PontoInvalidoException {
		if (longitude < -180 || longitude > 180) {
			throw new PontoInvalidoException("Latitude deve ser estar entre -180 e 180");
		}
		this.longitude = longitude;
	}

	@XmlElement(name = "ele")
	public double getElevacao() {
		return elevacao;
	}

	public void setElevacao(double elevacao) {
		this.elevacao = elevacao;
	}

	@XmlElement(name = "time")
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "TrackPoint: \n  Latitude:" + this.latitude + "\n  Longitude:" + this.longitude + "\n  Elevacao:"
				+ this.elevacao + "\n  Data:" + this.data;
	}

}
