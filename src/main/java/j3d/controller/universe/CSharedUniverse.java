package j3d.controller.universe;

import ihm.app.CanvasExtended;
import ihm.interaction.mouse.MouseInteractor;
import j3d.interfaces.universe.ICamera;
import j3d.interfaces.universe.IObject;
import j3d.interfaces.universe.ISharedUniverse;
import j3d.presentation.universe.PSharedUnivrese;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CSharedUniverse implements ISharedUniverse {

	private PSharedUnivrese presentation;
	private ISharedUniverse abstraction;

	private Map<String, IObject> cObjects;
	private Map<String, ICamera> cCameras;

	public CSharedUniverse(ISharedUniverse abstraction,
			CanvasExtended canvas) {
		this.abstraction = abstraction;
		cObjects = new HashMap<String, IObject>();
		cCameras = new HashMap<String, ICamera>();

		for (ICamera camera : abstraction.getCameras()) {
			CCamera cCamera = new CCamera(camera, null);
			cCameras.put(cCamera.getOwnerName(), cCamera);
			presentation.add(cCamera.getPresentation());

		}

		for (IObject object : abstraction.getObjects()) {
			CObject cObject = new CObject(object);
			cObjects.put(cObject.getName(), cObject);
			presentation.add(cObject.getPresentation());
		}
		presentation.compile();
		presentation = new PSharedUnivrese(this, canvas);
		// Create the scene

	}

	public PSharedUnivrese getPresentation() {
		return presentation;
	}

	@Override
	public boolean add(IObject object) {
		if (!(object instanceof CObject))
			return false;
		if (abstraction.add(object)) {
			presentation.add(((CObject) object).getPresentation());
			cObjects.put(object.getName(), object);
			presentation.compile();
			return true;
		} else
			return false;
	}

	@Override
	public void remove(IObject object) {
		if (object instanceof CObject) {
			if (abstraction.getObjects().contains(object)) {
				presentation.remove(((CObject) object).getPresentation());
				cObjects.remove(object);
				presentation.compile();
			}
		}
		abstraction.remove(object);
	}

	@Override
	public boolean add(ICamera camera) {
		if (!(camera instanceof CCamera))
			return false;
		if (abstraction.add(camera)) {
			presentation.add(((CCamera) camera).getPresentation());
			cCameras.put(camera.getOwnerName(), camera);
			presentation.compile();
			return true;
		} else
			return false;
	}

	@Override
	public void remove(ICamera camera) {
		if (camera instanceof CCamera) {
			if (abstraction.getObjects().contains(camera)) {
				presentation.remove(((CCamera) camera).getPresentation());
				cCameras.put(camera.getOwnerName(), camera);
				presentation.compile();
			}
		}
		abstraction.remove(camera);
	}

	public void addMouseInteractor(MouseInteractor mi) {
		presentation.getScene().addChild(mi);
	}



	@Override
	public Collection<IObject> getObjects() {
		return abstraction.getObjects();
	}

	@Override
	public Collection<ICamera> getCameras() {
		return abstraction.getCameras();
	}

	@Override
	public IObject getObject(String name) {
		return abstraction.getObject(name);
	}

	@Override
	public ICamera getCamera(String name) {
		return abstraction.getCamera(name);
	}

	@Override
	public void update(ICamera camera) {
		abstraction.update(camera);
	}

	@Override
	public void update(IObject object) {
		abstraction.update(object);
	}

	@Override
	public String getName() {
		return abstraction.getName();
	}

}
