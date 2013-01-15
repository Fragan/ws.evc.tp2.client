package j3d.presentation.universe;

import java.io.FileNotFoundException;

import javax.media.j3d.TransformGroup;

import org.jdesktop.j3d.loaders.vrml97.VrmlLoader;

import com.sun.j3d.loaders.Scene;

import j3d.controller.universe.CObject;
import j3d.object.VirtualObject;


//  La 3d
public class PObject extends VirtualObject {


	private CObject controller;

	public PObject(CObject controller, String urlGeometry) {
		this.controller = controller;
		
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ENABLE_PICK_REPORTING);	

		VrmlLoader loader = new VrmlLoader();
		try {			
			Scene scene = loader.load(urlGeometry);
			this.addChild(scene.getSceneGroup());			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public CObject getController() {
		return controller;
	}
	
	
}
