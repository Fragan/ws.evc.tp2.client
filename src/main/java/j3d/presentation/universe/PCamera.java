package j3d.presentation.universe;

import java.io.FileNotFoundException;

import j3d.controller.universe.CCamera;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import org.jdesktop.j3d.loaders.vrml97.VrmlLoader;

import com.sun.j3d.loaders.Scene;


//We have to simulate a TransformGroup
//because downcast is forbidden TransformGroup -> PCamera
public class PCamera extends TransformGroup {

	private CCamera controller;
	private TransformGroup realTgCamera;
	private Scene scene;
	
	public PCamera(CCamera camera) {
		this.controller = camera;
	}
	
	public PCamera(CCamera cCamera, TransformGroup tgCamera) {
		controller = cCamera;
		realTgCamera = tgCamera;
		if (realTgCamera == null)
			realTgCamera = new TransformGroup();
	}
	
	/**
	 * With a pyramid
	 * @param cCamera
	 * @param tgCamera
	 * @param cameraObjectUrl
	 */
	public PCamera(CCamera cCamera, TransformGroup tgCamera, String cameraObjectUrl) {
		
		controller = cCamera;
		realTgCamera = tgCamera;
		if (realTgCamera == null)
			realTgCamera = new TransformGroup();
		
		
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		this.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
		
		VrmlLoader loader = new VrmlLoader();
		try {			
			scene = loader.load(cameraObjectUrl);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		realTgCamera.addChild(scene.getSceneGroup());
	}
	
	public CCamera getController() {
		return this.controller;
	}

	@Override
	public void getTransform(Transform3D t3d) {
		realTgCamera.getTransform(t3d);
	}

	@Override
	public void setTransform(Transform3D t3d) {
		realTgCamera.setTransform(t3d); // TODO vérifier
	}
	
	
}
