package io.itpl.microservice.common;

import com.mongodb.client.model.geojson.Position;
import org.springframework.data.geo.Point;

import java.util.Arrays;
import java.util.List;

public class GeoPoint {

	private double lon;
	private double lat;
	
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}

	public Position toPosition(){
		List<Double> points = Arrays.asList(lon,lat);
		return new Position(points);
	}
	public Point toPoint(){

		return new Point(lon,lat);
	}


	public boolean equals(GeoPoint another){
		if(another != null){
			return another.getLon() == this.getLon() && another.getLat() == this.getLat();
		}else{
			return false;
		}
	}

	@Override
	public String toString() {
		return "GeoPoint{" +
				"lon=" + lon +
				", lat=" + lat +
				'}';
	}
}
