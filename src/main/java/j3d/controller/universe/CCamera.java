package j3d.controller.universe;

import j3d.abstraction.universe.ACamera;
import j3d.presentation.universe.PCamera;

public class CCamera {

	private ACamera abstraction;
	private PCamera presentation;
	
	public CCamera(String name) {
		abstraction = new ACamera(name);
		presentation = new PCamera(this);
	}
	
	public PCamera getCamera() {
		return presentation;
	}
}
