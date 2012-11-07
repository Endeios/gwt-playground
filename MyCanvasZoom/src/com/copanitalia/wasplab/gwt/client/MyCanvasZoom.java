package com.copanitalia.wasplab.gwt.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class MyCanvasZoom extends Panel {
	private static final String urlPrefix = "http://172.25.3.54/SYSID11726/T0/FS/";
	private static final int CANVASSIZE = 1000;
	private Canvas canvas;
	private String format;
	private int zoomLevel = 8;

	private Label showZoomLevel;
	private float yBias = 0;
	private float xBias = 0;

	private float fullImageXBias = 0;
	private float fullImageYBias = 0;
	
	
	private List<PickPoint>pickPoints = new ArrayList<PickPoint>();
	private boolean enablePickPointCreation; 

	public void showZoomLevel() {
		showZoomLevel.setText("Zoom level:" + getZoomLevel());
	}
	

	public void printInfos(List<PickPoint> picks){
		
	}
	

	public String makeUrl(int num) {
		return getUrlprefix() + num + "/";
	}

	public void representZoomLevel(String format, String baseUrl,
			Canvas canvas, int ARRAYSIZE) {

		Context2d ctx = canvas.getContext2d();
		ctx.clearRect(0, 0, CANVASSIZE, CANVASSIZE);
		String[][] map = new String[ARRAYSIZE][ARRAYSIZE];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = i + "_" + j;
			}
		}

		Image imap[][] = new Image[ARRAYSIZE][ARRAYSIZE];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				imap[i][j] = new Image(baseUrl + map[i][j] + format);
			}
		}
		ImageElement imageElement = null;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				imageElement = (ImageElement) imap[i][j].getElement().cast();
				ctx.drawImage(imageElement, (256 * i) + getOffsetX(), (256 * j)
						+ getOffsetY());
			}
		}
		
		for (PickPoint pick:getPickPoints()){
			ctx.setFillStyle("red");
//			ctx.setLineWidth(5);
//			ctx.arc(pick.getX()+getXBias(), pick.getY()+getYBias(), 2, 0, 2*Math.PI, false);
//			ctx.closePath();
//			ctx.fill();
			
			ctx.fillRect(pick.getX(getZoomLevel())+getOffsetX()-10, pick.getY(getZoomLevel())+getOffsetY()-10, 20, 20);
			//System.out.println("Printing "+pick.getX()+","+pick.getY());
		}
	}

	public float getOffsetY() {
		return yBias;
	}

	public float getOffsetX() {
		return xBias;
	}

	public void setOffsetY(float f) {
		yBias = f;
	}

	public void setOffsetX(float f) {
		xBias = f;
	}

	public float getFullImageXBias() {
		return fullImageXBias;
	}

	public void setFullImageXBias(float fullImageXBias) {
		this.fullImageXBias = fullImageXBias;
	}

	public float getFullImageYBias() {
		return fullImageYBias;
	}

	public void setFullImageYBias(float fullImageYBias) {
		this.fullImageYBias = fullImageYBias;
	}


	public Canvas getCanvas() {
		return canvas;
	}


	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}


	public int getZoomLevel() {
		return zoomLevel;
	}


	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}


	public String getFormat() {
		return format;
	}


	public void setFormat(String format) {
		this.format = format;
	}


	public List<PickPoint> getPickPoints() {
		return pickPoints;
	}


	public void setPickPoints(List<PickPoint> pickPoints) {
		this.pickPoints = pickPoints;
	}


	public boolean getEnablePickPointCreation() {
		return enablePickPointCreation;
	}


	public static String getUrlprefix() {
		return urlPrefix;
	}


	@Override
	public Iterator<Widget> iterator() {
		return null;
	}

	@Override
	public boolean remove(Widget child) {
		return false;
	}

}
