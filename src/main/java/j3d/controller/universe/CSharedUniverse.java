package j3d.controller.universe;

import java.util.Collection;

import j3d.abstraction.universe.ACamera;
import j3d.abstraction.universe.AObject;
import j3d.abstraction.universe.ASharedUniverse;
import j3d.presentation.universe.PSharedUnivrese;

public class CSharedUniverse {

	private ASharedUniverse abstraction;
	private PSharedUnivrese presentation;
	
	private boolean modeCameraRotationScene;
	
	public CSharedUniverse() {
		abstraction = new ASharedUniverse();
		presentation = new PSharedUnivrese();
	}
	
	public void addObjects(Collection<AObject> objects) {
		for (AObject object: objects) {
			if (abstraction.getObject(object.getName()) != null) {
				// TODO : presntation interaction
				System.err.println("The object " + object.getName() + " already included in the universe");
				return;
			}
		}
		abstraction.addObjects(objects);
	}
	
	public void addCameras(Collection<ACamera> cameras) {
		for (ACamera camera: cameras) {
			if (abstraction.getObject(camera.getName()) != null) {
				// TODO : presntation interaction
				System.err.println("The camera " + camera.getName() + " already included in the universe");
				return;
			}
		}
		abstraction.addCameras(cameras);
	}
	
	public void add(AObject object) {
		if (abstraction.getObject(object.getName()) != null) {
			// TODO : presntation interaction
			System.err.println("The object " + object.getName() + " already included in the universe");
		}
		else {
			abstraction.remove(object);
		}
	}
	
	public void remove(AObject object) {
		abstraction.remove(object);
	}

	public void add(ACamera camera) {
		if (abstraction.getObject(camera.getName()) != null) {
			// TODO : presntation interaction
			System.err.println("The camera " + camera.getName() + " already included in the universe");
		}
		else {
			abstraction.add(camera);
		}
	}
	
	public void remove(ACamera camera) {
		abstraction.remove(camera);
	}
}
