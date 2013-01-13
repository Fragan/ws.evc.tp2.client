package j3d.abstraction.universe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.media.j3d.Transform3D;

public class ASharedUniverse {

	Map<String, AObject> objects;
	Map<String, ACamera> cameras;
	
	public ASharedUniverse() {
		objects = new HashMap<String, AObject>();
		cameras = new HashMap<String, ACamera>();
	}
	
	public Collection<AObject> getObjects() {
		return objects.values();
	}
	
	public void addObjects(Collection<AObject> objects) {
		for (AObject object: objects) {
			if (getObject(object.getName()) != null) {
				// TODO : presntation interaction
				System.err.println("The object " + object.getName() + " already included in the universe");
			}
			else {
				this.objects.put(object.getName(), object);
			}
		}
	}
	
	public Collection<ACamera> getCameras() {
		return cameras.values();
	}
	
	public void addCameras(Collection<ACamera> cameras) {
		for (ACamera camera: cameras) {
			if (getObject(camera.getOwnerName()) != null) {
				// TODO : presntation interaction
				System.err.println("The camera " + camera.getOwnerName() + " already included in the universe");
			}
			else {
				this.cameras.put(camera.getOwnerName(), camera);
			}
		}
		addCameras(cameras);
	}
	
	public void add(AObject object) {
		if (getObject(object.getName()) != null) {
			// TODO : presntation interaction
			System.err.println("The object " + object.getName() + " already included in the universe");
		}
		else {
			objects.put(object.getName(), object);
		}
	}
	
	public void remove(AObject object) {
		objects.remove(object.getName());
	}
	
	public void add(ACamera camera) {
		if (getObject(camera.getOwnerName()) != null) {
			// TODO : presntation interaction
			System.err.println("The camera " + camera.getOwnerName() + " already included in the universe");
		}
		else {
			cameras.put(camera.getOwnerName(), camera);
		}
	}
	
	public void remove(ACamera camera) {
		cameras.remove(camera.getOwnerName());
	}
	
	public AObject getObject(String name) {
		return objects.get(name);
	}

	public ACamera getCamera(String name) {
		return cameras.get(name);
	}

	public void setCameraTransform(String ownerName, Transform3D t3D) {
		cameras.get(ownerName).setTransform(t3D);
	}
}
