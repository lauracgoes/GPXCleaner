package br.gpx.modelo;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="trkpt")
public class Ponto {

	
	private double latitude;
	private double longitude;
	private double elevacao;
	private Date data;
	
	public Ponto() {
		// TODO Auto-generated constructor stub
	}
	
	public Ponto(double latitude, double longitude, double elevacao, Date data) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevacao = elevacao;
		this.data = data;
	}
	
	@XmlAttribute(name="lat")
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	@XmlAttribute(name="lon")
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	@XmlElement(name="ele")
	public double getElevacao() {
		return elevacao;
	}
	public void setElevacao(double elevacao) {
		this.elevacao = elevacao;
	}
	@XmlElement(name="time")
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "TrackPoint: \n  Latitude:"+this.latitude+"\n  Longitude:"+this.longitude+"\n  Elevacao:"+this.elevacao+"\n  Data:"+this.data;
	}
	
	
}
