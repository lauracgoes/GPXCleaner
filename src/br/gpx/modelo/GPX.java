package br.gpx.modelo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe para criacao do XML de saida
 */
@XmlRootElement(name = "gpx")
public class GPX {

	
	private Trajeto trajeto;
	
	public GPX() {
		// TODO Auto-generated constructor stub
	}
	
	public GPX(Trajeto trajeto) {
		this.trajeto = trajeto;
	}

	@XmlElement(name = "trk")
	public Trajeto getTrajeto() {
		return trajeto;
	}

	@XmlAttribute(name="creator")
	public String getCreator() {
		return "LoadMyTracks/uniriotec https://github.com/lauracgoes/GPXCleaner.git";
	}

	@XmlAttribute(name="version")
	public String getVersion() {
		return "1.1";
	}

	@XmlAttribute(namespace="xmlns")
	public String getXmlns() {
		return "http://www.topografix.com/GPX/1/1";
	}

	@XmlAttribute(namespace="xmlns:geocache")
	public String getGeocache() {
		return "http://www.groundspeak.com/cache/1/0";
	}

	@XmlAttribute(namespace="xmlns:gpxdata")
	public String getGpxdata() {
		return "http://www.cluetrust.com/XML/GPXDATA/1/0";
	}

	@XmlAttribute(namespace="xmlns:xsi")
	public String getXsi() {
		return "http://www.w3.org/2001/XMLSchema-instance";
	}

	@XmlAttribute(namespace="xmlns:xsi:schemaLocation")
	public String getXsi_schemaLocation() {
		return "http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd http://www.cluetrust.com/XML/GPXDATA/1/0 http://www.cluetrust.com/Schemas/gpxdata10.xsd";
	}
	
}
