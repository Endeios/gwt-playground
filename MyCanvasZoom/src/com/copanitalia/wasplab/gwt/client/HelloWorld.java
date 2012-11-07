package com.copanitalia.wasplab.gwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class HelloWorld implements EntryPoint {

	private static final String urlPrefix = "http://172.25.3.54/SYSID11726/T0/FS/";
	private static final int CANVASSIZE = 1000;
	//private Canvas canvas;
	//private String format;
	//private int zoomLevel = 8;

	private Label showZoomLevel;
	//private float yBias = 0;
	///private float xBias = 0;

	private float fullImageXBias = 0;
	private float fullImageYBias = 0;
	
	private TextArea output;

	private MyCanvasZoom myCanvasZoom;
	private MyHandler handler;
	
	private List<PickPoint>pickPoints = new ArrayList<PickPoint>();
	private boolean enablePickPointCreation; 

	public void showZoomLevel() {
//		showZoomLevel.setText("Zoom level:" + getZoomLevel());
	}
	

	public void printInfos(List<PickPoint> picks){
		
	}
	

	public void onModuleLoad() {
		Button theButton = new Button("SetZoomLevel");
		theButton.getElement().addClassName("btn"); 	
		Label infolabel = new Label("INFOS!");
		RootPanel.get().add(theButton);
		RootPanel.get().add(infolabel);
		SimplePanel testPanel = new SimplePanel();
		testPanel.add(new Label("Set zoom Level"));
		RootPanel.get().add(testPanel);
		final CheckBox enablePick = new CheckBox("Abilita pick");
		RootPanel.get().add(enablePick);
		final TextBox zoomlvl = new TextBox();
		RootPanel.get().add(zoomlvl);
		output = new TextArea();
		RootPanel.get().add(output);
//		setFormat(".jpg");
		// String baseUrl = makeUrl(num);
		//canvas = Canvas.createIfSupported();
		showZoomLevel = new Label("");
		RootPanel.get().add(showZoomLevel);
		showZoomLevel();
		myCanvasZoom = new MyCanvasZoom();
		handler = new MyHandler(myCanvasZoom);
		this.myCanvasZoom.getCanvas().addMouseDownHandler(handler);
		this.myCanvasZoom.getCanvas().addMouseMoveHandler(handler);
		this.myCanvasZoom.getCanvas().addMouseUpHandler(handler);
		this.myCanvasZoom.getCanvas().addMouseWheelHandler(handler);
		this.myCanvasZoom.getCanvas().addClickHandler(handler);
		enablePick.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				myCanvasZoom.setEnablePickPointCreation(enablePick.getValue());
//				enablePickPointCreation = enablePick.getValue();
				
			}
		});
		
		
		//RootPanel.get().add(canvas);
		// int ARRAYSIZE = 8;
		//canvas.setCoordinateSpaceHeight(CANVASSIZE);
		//canvas.setCoordinateSpaceWidth(CANVASSIZE);
		// RepresentZoomLevel(format, baseUrl, canvas, ARRAYSIZE);
		theButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String input = zoomlvl.getText();
				Integer num = Integer.parseInt(input);
				int myPow = num - 8;
				//representZoomLevel(getFormat(), makeUrl(num), canvas,
						//(int) Math.pow(2, myPow));
			}
		});

	

	}


	public String makeUrl(int num) {
		// return
		// "http://www.akademy.co.uk/software/canvaszoom/Galaxy/tiles/"+num+"/";
		return getUrlprefix() + num + "/";
	}

//	public void representZoomLevel(String format, String baseUrl,
//			Canvas canvas, int ARRAYSIZE) {
//
//		Context2d ctx = canvas.getContext2d();
//		ctx.clearRect(0, 0, CANVASSIZE, CANVASSIZE);
//		String[][] map = new String[ARRAYSIZE][ARRAYSIZE];
//		for (int i = 0; i < map.length; i++) {
//			for (int j = 0; j < map[i].length; j++) {
//				map[i][j] = i + "_" + j;
//			}
//		}
//
//		// for (int i = 0; i < map.length; i++) {
//		// for (int j = 0; j < map[i].length; j++) {
//		// RootPanel.get().add(new Label(map[i][j]));
//		// }
//		// }
//		Image imap[][] = new Image[ARRAYSIZE][ARRAYSIZE];
//		for (int i = 0; i < map.length; i++) {
//			for (int j = 0; j < map[i].length; j++) {
//				imap[i][j] = new Image(baseUrl + map[i][j] + format);
//			}
//		}
//		ImageElement imageElement = null;
//		for (int i = 0; i < map.length; i++) {
//			for (int j = 0; j < map[i].length; j++) {
//				imageElement = (ImageElement) imap[i][j].getElement().cast();
////				oldXBias = (256 * i) + getXBias();
////				oldYBias = (256 * j) + getYBias();
//				ctx.drawImage(imageElement, (256 * i) + getOffsetX(), (256 * j)
//						+ getOffsetY());
//			}
//		}
//		
//		for (PickPoint pick:getPickPoints()){
//			ctx.setFillStyle("red");
////			ctx.setLineWidth(5);
////			ctx.arc(pick.getX()+getXBias(), pick.getY()+getYBias(), 2, 0, 2*Math.PI, false);
////			ctx.closePath();
////			ctx.fill();
//			
//			ctx.fillRect(pick.getX(getZoomLevel())+getOffsetX()-10, pick.getY(getZoomLevel())+getOffsetY()-10, 20, 20);
//			//System.out.println("Printing "+pick.getX()+","+pick.getY());
//		}
//	}

//	public float getOffsetY() {
//		return yBias;// -((int) Math.pow(2, zoomLevel- 8)/2)*256;
//	}
//
//	public float getOffsetX() {
//		return xBias;// -((int) Math.pow(2, zoomLevel- 8)/2)*256;
//	}
//
//	public void setOffsetY(float f) {
//		yBias = f;
//	}
//
//	public void setOffsetX(float f) {
//		xBias = f;
//	}
//
//
//	public Canvas getCanvas() {
//		return canvas;
//	}
//
//
//	public void setCanvas(Canvas canvas) {
//		this.canvas = canvas;
//	}
//
//
//	public int getZoomLevel() {
//		return zoomLevel;
//	}
//
//
//	public void setZoomLevel(int zoomLevel) {
//		this.zoomLevel = zoomLevel;
//	}
//
//
//	public String getFormat() {
//		return format;
//	}
//
//
//	public void setFormat(String format) {
//		this.format = format;
//	}


	public List<PickPoint> getPickPoints() {
		return pickPoints;
	}


	public void setPickPoints(List<PickPoint> pickPoints) {
		this.pickPoints = pickPoints;
	}


	public boolean getEnablePickPointCreation() {
		// TODO Auto-generated method stub
		return enablePickPointCreation;
	}


	public static String getUrlprefix() {
		return urlPrefix;
	}

}
