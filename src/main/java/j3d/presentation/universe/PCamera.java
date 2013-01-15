package j3d.presentation.universe;

import j3d.controller.universe.CCamera;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;


//We have to simulate a TransformGroup
//because downcast is forbidden TransformGroup -> PCamera
public class PCamera extends TransformGroup {

	private CCamera controller;
	private TransformGroup realTgCamera;
	public PCamera(CCamera camera) {
		this.controller = camera;
	}
	
	public PCamera(CCamera cCamera, TransformGroup tgCamera) {
		controller = cCamera;
		realTgCamera = tgCamera;
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
		realTgCamera.setTransform(t3d);
	}
	
	
}
