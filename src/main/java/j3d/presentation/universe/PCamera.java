package j3d.presentation.universe;

import j3d.controller.universe.CCamera;

import javax.media.j3d.TransformGroup;

public class PCamera extends TransformGroup {

	private CCamera controller;
	
	public PCamera(CCamera camera) {
		this.controller = camera;
	}
	
	public CCamera getController() {
		return this.controller;
	}
}
