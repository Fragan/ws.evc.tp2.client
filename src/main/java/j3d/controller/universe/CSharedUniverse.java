package j3d.controller.universe;

import ihm.app.CanvasLoader;

import java.util.Collection;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import j3d.abstraction.universe.ACamera;
import j3d.abstraction.universe.AObject;
import j3d.abstraction.universe.ASharedUniverse;
import j3d.presentation.universe.PSharedUnivrese;

public class CSharedUniverse extends ASharedUniverse {

	private String ownerName; // The same name as ACamera
	private PSharedUnivrese presentation;
	
	private boolean modeCameraRotationScene;
	
	public CSharedUniverse(CanvasLoader canvas) {
		super();
		modeCameraRotationScene = false;
		presentation = new PSharedUnivrese(this, canvas);
	}
	
	public CSharedUniverse(CCamera transCamera, TransformGroup scene) {
		super();
		modeCameraRotationScene = false;
		presentation = new PSharedUnivrese(this, transCamera.getPresentation(), scene);
	}
	
	public PSharedUnivrese getPresentetion() {
		return presentation;
	}
	
	public boolean isModeCameraRotationScene() {
		return modeCameraRotationScene;
	}

	public void setModeCameraRotationScene(boolean modeCameraRotationScene) {
		this.modeCameraRotationScene = modeCameraRotationScene;
	}
	
	/**
	 * Deplacement vers un point absolu de l'univers
	 */
	public void cameraTeleportTo(double x, double y, double z) {

		Vector3d translate = new Vector3d();
		translate.set(x, y, z);

		Transform3D t3D = new Transform3D();

		presentation.getCameraTransform(t3D);

		// setTranslation est absolu a l'univers
		t3D.setTranslation(translate);
		presentation.setCameraTransform(t3D);
		super.setCameraTransform(ownerName, t3D);
	}
	
	public void rotateTo(double h, double d, double r) {
		Vector3d rotate = new Vector3d();
		rotate.set(h, d, r);

		Transform3D t3D = new Transform3D();
		presentation.getCameraTransform(t3D);

		Vector3d translate = new Vector3d();

		t3D.get(translate);
		t3D.setEuler(rotate);
		t3D.setTranslation(translate);

		presentation.setCameraTransform(t3D);
		super.setCameraTransform(ownerName, t3D);
	}
	
	public void cameraRelativeTranslate(double dx, double dy, double dz) {
		Transform3D oldT3D = new Transform3D();
		presentation.getCameraTransform(oldT3D);
		Vector3d translate = new Vector3d();
		translate.set(dx, dy, dz);

		Transform3D localT3D = new Transform3D();
		localT3D.setTranslation(translate);

		Transform3D newT3D = new Transform3D();
		newT3D.mul(oldT3D, localT3D);
		presentation.setCameraTransform(newT3D);
		super.setCameraTransform(ownerName, newT3D);
	}
	
	public void cameraRelativeRotate(double dh, double dp, double dr) {
		Transform3D oldT3D = new Transform3D();
		presentation.getCameraTransform(oldT3D);
		
		Transform3D localT3D = new Transform3D();
		double x = 0, y = 0, z = 0;
		x = Math.PI * dh / 180;
		y = Math.PI * dp / 180;
		z = Math.PI * dr / 180;

		localT3D.setEuler(new Vector3d(y,-x,z));
		//localT3D.rotY(-Math.sin(x) * Math.cos(y));
		//oldT3D.mul(localT3D);
		
		//localT3D.rotX(Math.cos(x) * Math.sin(y));
		//oldT3D.mul(localT3D);		
		//localT3D.rotZ(z);
		
		if (modeCameraRotationScene)
			oldT3D.mul(localT3D, oldT3D);
		else
			oldT3D.mul(localT3D);
		
		presentation.setCameraTransform(oldT3D);
		super.setCameraTransform(ownerName, oldT3D);
	}
	
	public void objectRotate(TransformGroup objectInInteraction, double dh,
			double dp, double dr) {
		
		Transform3D oldT3D = new Transform3D();
		objectInInteraction.getTransform(oldT3D);	
		Transform3D tc = new Transform3D();
				
		double x = 0, y = 0, z = 0;
		x = (Math.PI * dh / 180);
		y = Math.PI * dp / 180;
		z = Math.PI * dr / 180;		

		tc.setEuler(new Vector3d(-y ,x ,z));
		oldT3D.mul(tc);
		
		objectInInteraction.setTransform(oldT3D);
	}
	
	public void objectTranslate(TransformGroup objectInInteraction, double dx,
			double dy, double dz) {
		Transform3D vpT3D = new Transform3D();
		presentation.getCameraTransform(vpT3D);
		Transform3D vpT3Dinv = new Transform3D();
		vpT3Dinv.invert(vpT3D);

		Transform3D oldT3D = new Transform3D();
		objectInInteraction.getTransform(oldT3D);
		Vector3d translate = new Vector3d();
		translate.set(dx, dy, dz);
		Transform3D localDeltaT3D = new Transform3D();
		localDeltaT3D.setTranslation(translate);
		Transform3D absoluteDeltaT3D = new Transform3D();
		absoluteDeltaT3D.mul(vpT3D, localDeltaT3D);
		absoluteDeltaT3D.mul(vpT3Dinv);
		Transform3D newT3D = new Transform3D();
		newT3D.mul(absoluteDeltaT3D, oldT3D);
		objectInInteraction.setTransform(newT3D);
	}
}
