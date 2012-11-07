package com.copanitalia.wasplab.gwt.client;

import java.io.Serializable;

public class PickPoint implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6830067028933607531L;
	public PickPoint(float x, float y,int zoomlvl,float biasX,float biasY) {
		super();
		float scale = (float) ((Math.pow(2, 4))/(Math.pow(2, zoomlvl - 8)));
		this.x = (x-biasX)*scale;
		Y = (y-biasY)*scale;
	}
	private float x;
	private float Y;
	public float getY(int zoomlvl) {
		float scale = (float) ((Math.pow(2, 4))/(Math.pow(2, zoomlvl- 8)));
		return Y/scale;
	}
	public float getX(int zoomlvl) {
		float scale = (float) ((Math.pow(2, 4))/(Math.pow(2, zoomlvl - 8)));
		return x/scale;
	}
	public void setY(float y,int zoomlvl) {
		float scale = (float) ((Math.pow(2, 4))/(Math.pow(2, zoomlvl - 8)));
		Y = y*scale;
	}
	public void setX(float x,int zoomlvl) {
		float scale = (float) ((Math.pow(2, 4))/(Math.pow(2, zoomlvl - 8)));
		this.x = x*scale;
	}
}