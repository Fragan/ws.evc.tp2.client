package j3d.controller.universe;

import j3d.abstraction.universe.ACamera;
import j3d.presentation.universe.PCamera;

public class CCamera extends ACamera {

	private PCamera presentation;
	
	public CCamera(String ownerName) {
		super(ownerName);
		presentation = new PCamera(this);
	}
	
	public PCamera getPresentation() {
		return presentation;
	}
}
