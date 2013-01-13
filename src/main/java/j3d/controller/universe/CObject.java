package j3d.controller.universe;

import j3d.abstraction.universe.AObject;
import j3d.presentation.universe.PObject;

// controle les interactions
public class CObject extends AObject {
	
	private PObject presentation;
	
	public CObject(String name, String urlGeometry) {
		super(name, urlGeometry);
		presentation = new PObject();
	}
	
	public PObject getPresentation() {
		return presentation;
	}

}
