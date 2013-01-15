package j3d.controller.universe;

import ihm.app.CanvasExtended;
import ihm.interaction.mouse.MouseInteractor;
import j3d.abstraction.universe.ACamera;
import j3d.abstraction.universe.AObject;
import j3d.abstraction.universe.ASharedUniverse;
import j3d.presentation.universe.PSharedUnivrese;
import j3d.scene.Scene;

public class CSharedUniverse extends ASharedUniverse {

	private String ownerName; // The same name as ACamera
	private Scene scene;
	private PSharedUnivrese presentation;
	
	
	
	public CSharedUniverse(String ownerName, CanvasExtended canvas) {
		super();
		this.ownerName = ownerName;
		presentation = new PSharedUnivrese(this, canvas);
		//Create the scene
		scene = Scene.createDefaultScene();
	}
	
	
	public PSharedUnivrese getPresentation() {
		return presentation;
	}


	@Override
	public boolean add(AObject object) {
		if (! (object instanceof CObject))
			return false;
		if (super.add(object)) {
			scene.addChild(((CObject) object).getPresentation());
			return true;
		}
		else return false;
	}


	@Override
	public void remove(AObject object) {
		if (object instanceof CObject) {
			if (super.getObjects().contains(object))
				scene.removeChild(((CObject) object).getPresentation());
		}
		super.remove(object);
	}


	@Override
	public boolean add(ACamera camera) {
		if (!(camera instanceof CCamera))
			return false;
		if (super.add(camera)) {
			scene.addChild(((CCamera) camera).getPresentation());
			return true;
		}
		else return false;
	}


	@Override
	public void remove(ACamera camera) {
		if (camera instanceof CCamera) {
			if (super.getObjects().contains(camera))
				scene.removeChild(((CCamera) camera).getPresentation());
		}
		super.remove(camera);
	}
	
	public void addMouseInteractor(MouseInteractor mi) {
		scene.addChild(mi);
	}
	
	public void compileScene() {
		scene.compile();
		getPresentation().addBranchGraph(scene);
	}
	
	public Scene getScene() {
		return scene;
	}


	public String getOwnerName() {
		return ownerName;
	}
	
	
	
	
	

	
	

}
