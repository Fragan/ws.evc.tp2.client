package j3d.abstraction.universe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
		for (AObject object: objects)
			this.objects.put(object.getName(), object);
	}
	
	public Collection<ACamera> getCameras() {
		return cameras.values();
	}
	
	public void addCameras(Collection<ACamera> cameras) {
		for (ACamera camera: cameras)
			this.cameras.put(camera.getName(), camera);
	}
	
	public void add(AObject object) {
		objects.put(object.getName(), object);
		
//		Dans le controller
//		if (!objects.containsKey(object.getName())) {
//			
//		}
	}
	
	public void remove(AObject object) {
		objects.remove(object.getName());
	}

	public void add(ACamera camera) {
		cameras.put(camera.getName(), camera);
	}
	
	public void remove(ACamera camera) {
		cameras.remove(camera.getName());
	}
	
	public AObject getObject(String name) {
		return objects.get(name);
	}

	public ACamera getCamera(String name) {
		return cameras.get(name);
	}
}
