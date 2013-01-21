package j3d.controller.universe;

import j3d.interfaces.universe.ICamera;
import j3d.presentation.universe.PCamera;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public class CCamera implements ICamera  {

	private PCamera presentation;
	private ICamera abstraction;
	private boolean modeCameraRotationScene;
	
	public CCamera(ICamera abstraction, TransformGroup tgCamera) {
		this.abstraction = abstraction;
		modeCameraRotationScene = false;
		presentation = new PCamera(this, tgCamera);
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
	public void teleportTo(double x, double y, double z) {

		Vector3d translate = new Vector3d();
		translate.set(x, y, z);

		Transform3D t3D = new Transform3D();

		presentation.getTransform(t3D);

		// setTranslation est absolu a l'univers
		t3D.setTranslation(translate);
		presentation.setTransform(t3D);
		setTransform(t3D);
	}
	
	
	public void relativeTranslate(double dx, double dy, double dz) {
		Transform3D oldT3D = new Transform3D();
		presentation.getTransform(oldT3D);
		Vector3d translate = new Vector3d();
		translate.set(dx, dy, dz);

		Transform3D localT3D = new Transform3D();
		localT3D.setTranslation(translate);

		Transform3D newT3D = new Transform3D();
		newT3D.mul(oldT3D, localT3D);
		presentation.setTransform(newT3D);
		setTransform(newT3D);
	}
	
	public void relativeRotate(double dh, double dp, double dr) {
		Transform3D oldT3D = new Transform3D();
		presentation.getTransform(oldT3D);
		
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
		
		presentation.setTransform(oldT3D);
		setTransform(oldT3D);
	}
	
	public PCamera getPresentation() {
		return presentation;
	}

	@Override
	public String getOwnerName() {
		return abstraction.getOwnerName();
	}

	@Override
	public Vector3d getPosition() {
		return abstraction.getPosition();
	}

	@Override
	public void setPosition(Vector3d position) {
		abstraction.setPosition(position);
	}

	@Override
	public Quat4d getOrientation() {
		return abstraction.getOrientation();
	}

	@Override
	public void setOrientation(Quat4d orientation) {
		abstraction.setOrientation(orientation);
	}

	@Override
	public void setTransform(Transform3D t3d) {
		abstraction.setTransform(t3d);
	}
}
