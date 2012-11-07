package com.copanitalia.wasplab.gwt.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;

public class MyHandler implements MouseWheelHandler, MouseDownHandler,
		MouseMoveHandler, MouseUpHandler, ClickHandler {

	private static final int CANVASSIZE = 1000;
	private boolean dragging;
	private int mouseDownX;
	private int mouseDownY;
	private int mouseMoveX;
	private int mouseMoveY;
	private int mouseX;
	private int mouseY;
	private int zoomLevelMin = 0;
	private int zoomLevelMax = 100;
	private Canvas canvas;
	private MyCanvasZoom panel;

	public MyHandler(MyCanvasZoom myPanel) {
		this.canvas = myPanel.getCanvas();
		this.panel = myPanel;
		canvas.setCoordinateSpaceHeight(CANVASSIZE);
		canvas.setCoordinateSpaceWidth(CANVASSIZE);
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		// System.out.println("Drag Start:" + event);
		this.dragging = true;
		this.mouseDownX = event.getRelativeX(canvas.getCanvasElement());
		this.mouseDownY = event.getRelativeY(canvas.getCanvasElement());
		this.mouseMoveX = this.mouseDownX;
		this.mouseMoveY = this.mouseDownY;

	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		// System.out.println("Drag Stop:" + event);
		this.dragging = false;

		this.mouseX = event.getRelativeX(canvas.getCanvasElement());
		this.mouseY = event.getRelativeY(canvas.getCanvasElement());

	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		this.mouseX = event.getRelativeX(canvas.getCanvasElement());
		this.mouseY = event.getRelativeY(canvas.getCanvasElement());
		if (dragging) {
			// System.out.println("Drag Move:" + event);
			float newOffsetX = this.panel.getOffsetX()
					+ (this.mouseX - this.mouseMoveX);
			float newOffsetY = this.panel.getOffsetY()
					+ (this.mouseY - this.mouseMoveY);
			drag(event);
			this.mouseMoveX = this.mouseX;
			this.mouseMoveY = this.mouseY;
			this.panel.setOffsetX(newOffsetX);
			this.panel.setOffsetY(newOffsetY);
			this.panel.representZoomLevel(this.panel.getFormat(),
					this.panel.makeUrl(this.panel.getZoomLevel()), canvas,
					(int) Math.pow(2, this.panel.getZoomLevel() - 8));
			this.panel.showZoomLevel();
		}

	}

	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		// TODO Auto-generated method stub
		event.stopPropagation();
		event.preventDefault();
		// System.out.println("Event " + event);
		// System.out.println("event delta:" + event.getDeltaY());
		if (event.getDeltaY() > 0) {
			zoomOut(event);
		} else {
			zoomIn(event);
		}

	}

	private void zoomIn(MouseWheelEvent event) {
		if (this.panel.getZoomLevel() <= 11) {
			int newZoomLevel = this.panel.getZoomLevel() + 1;
			zoom(newZoomLevel, event.getRelativeX(canvas.getCanvasElement()),
					event.getRelativeY(canvas.getCanvasElement()));

		}

		refreshZoomZone(event);

	}

	private void zoomOut(MouseWheelEvent event) {
		if (this.panel.getZoomLevel() >= 9) {
			int newZoomLevel = this.panel.getZoomLevel() - 1;
			zoom(newZoomLevel, event.getRelativeX(canvas.getCanvasElement()),
					event.getRelativeY(canvas.getCanvasElement()));
		}

		refreshZoomZone(event);
	}

	private void zoom(int newZoomLevel, int newX, int newY) {
		if (newZoomLevel >= this.zoomLevelMin && newZoomLevel <= zoomLevelMax) {
			float currentImageX = newX - this.panel.getOffsetX();
			float currentImageY = newY - this.panel.getOffsetY();
			float scale = (float) ((Math.pow(2, newZoomLevel - 8)) / (Math.pow(
					2, this.panel.getZoomLevel() - 8)));
			float newImageX = currentImageX * scale;
			float newImageY = currentImageY * scale;
			float newOffsetX = this.panel.getOffsetX()
					- (newImageX - currentImageX);
			float newOffsetY = this.panel.getOffsetY()
					- (newImageY - currentImageY);
			this.panel.setOffsetX(newOffsetX);
			this.panel.setOffsetY(newOffsetY);
			this.panel.setZoomLevel(newZoomLevel);

		}
	}

	private void refreshZoomZone(@SuppressWarnings("rawtypes") MouseEvent event) {

		this.panel.representZoomLevel(this.panel.getFormat(),
				this.panel.makeUrl(this.panel.getZoomLevel()), canvas,
				(int) Math.pow(2, this.panel.getZoomLevel() - 8));
		this.panel.showZoomLevel();
	}

	private void drag(@SuppressWarnings("rawtypes") MouseEvent event) {
		int eventX = event.getRelativeX(canvas.getCanvasElement());
		int eventY = event.getRelativeY(canvas.getCanvasElement());
		float myXBias = this.panel.getOffsetX();
		float myYBias = this.panel.getOffsetY();
		float newXBias = eventX - myXBias;
		float newYBias = eventY - myYBias;
		this.panel.setOffsetX(newXBias);
		this.panel.setOffsetY(newYBias);
	}

	@Override
	public void onClick(ClickEvent event) {

		event.stopPropagation();
		event.preventDefault();
		// //System.out.println("Click Event " + event);
		int eventX = event.getRelativeX(canvas.getCanvasElement());
		int eventY = event.getRelativeY(canvas.getCanvasElement());
		if (panel.getEnablePickPointCreation()) {
			this.panel.getPickPoints().add(
					new PickPoint(eventX, eventY, this.panel.getZoomLevel(),
							this.panel.getOffsetX(), this.panel.getOffsetY()));
		}
	}
}