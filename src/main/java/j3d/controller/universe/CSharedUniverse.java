package j3d.controller.universe;

import ihm.app.CanvasExtended;
import ihm.interaction.mouse.MouseInteractor;
import j3d.abstraction.universe.ACamera;
import j3d.abstraction.universe.AObject;
import j3d.interfaces.universe.ICamera;
import j3d.interfaces.universe.IObject;
import j3d.interfaces.universe.ISharedUniverse;
import j3d.interfaces.universe.ISharedUniverseServer;
import j3d.presentation.universe.PSharedUnivrese;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CSharedUniverse implements ISharedUniverse {

	private PSharedUnivrese presentation;
	private ISharedUniverseServer abstractionProxy;

	private Map<String, IObject> cObjects;
	private Map<String, ICamera> cCameras;

	public CSharedUniverse(ISharedUniverseServer abstraction,
			CanvasExtended canvas) throws RemoteException {
		this.abstractionProxy = abstraction;
		cObjects = new HashMap<String, IObject>();
		cCameras = new HashMap<String, ICamera>();
		presentation = new PSharedUnivrese(this, canvas);

		for (ICamera camera : abstraction.getCameras()) {
			CCamera cCamera = new CCamera((ACamera) camera, null,
					abstractionProxy);
			cCameras.put(cCamera.getOwnerName(), cCamera);
			presentation.add(cCamera.getPresentation());

		}

		for (IObject object : abstraction.getObjects()) {
			CObject cObject = new CObject((AObject) object, abstractionProxy);
			cObjects.put(cObject.getName(), cObject);
			presentation.add(cObject.getPresentation());
		}
		// presentation.compile();
		// Create the scene

	}

	public PSharedUnivrese getPresentation() {
		return presentation;
	}

	public boolean add(IObject object) {
		if (object instanceof CObject) {
			try {
				if (abstractionProxy.add(object.getAbstraction())) {
					presentation.add(((CObject) object).getPresentation());
					cObjects.put(object.getName(), object);
					return true;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void remove(IObject object) {
		if (object instanceof CObject) {
			try {
				if (abstractionProxy.getObjects().contains(object)) {
					presentation.remove(((CObject) object).getPresentation());
					cObjects.remove(object);
				}
				abstractionProxy.remove(object);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean add(ICamera camera) {
		if (camera instanceof CCamera) {
			try {
				if (abstractionProxy.add(camera.getAbstraction())) {
					presentation.add(((CCamera) camera).getPresentation());
					cCameras.put(camera.getOwnerName(), camera);
					return true;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void remove(ICamera camera) {
		if (camera instanceof CCamera) {
			try {
				if (abstractionProxy.getObjects().contains(camera)) {
					presentation.remove(((CCamera) camera).getPresentation());
					cCameras.put(camera.getOwnerName(), camera);
				}
				abstractionProxy.remove(camera);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void addMouseInteractor(MouseInteractor mi) {
		presentation.add(mi);
	}

	public Collection<IObject> getObjects() {
		try {
			return abstractionProxy.getObjects();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Collection<ICamera> getCameras() {
		try {
			return abstractionProxy.getCameras();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	public IObject getObject(String name) {
		try {
			return abstractionProxy.getObject(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ICamera getCamera(String name) {
		try {
			return abstractionProxy.getCamera(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void update(ICamera camera) {
		ICamera cCamera = cCameras.get(camera.getOwnerName());
		if (cCamera != null) {
			cCamera.setOrientation(camera.getOrientation());
			cCamera.setPosition(camera.getPosition());
		}

	}

	public void update(IObject object) {
		IObject cObject = cObjects.get(object.getName());
		if (cObject != null) {
			cObject.setOrientation(object.getOrientation());
			cObject.setPosition(object.getPosition());
		}

	}

	public String getName() {
		try {
			return abstractionProxy.getName();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

}
