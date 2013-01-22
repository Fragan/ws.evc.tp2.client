package j3d.controller.universe;

import java.rmi.RemoteException;

import j3d.abstraction.universe.ACamera;
import j3d.interfaces.universe.ICamera;
import j3d.interfaces.universe.ISharedUniverseServer;
import j3d.presentation.universe.PCamera;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public class CCamera implements ICamera {

	private PCamera presentation;
	private ACamera abstraction;

	private boolean modeCameraRotationScene;
	private ISharedUniverseServer serverProxy;

	public CCamera(ACamera abstraction, TransformGroup tgCamera,
			ISharedUniverseServer proxy) {
		this.abstraction = abstraction;
		serverProxy = proxy;
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
		setTransform(t3D, true);
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
		setTransform(newT3D, true);
	}

	public void relativeRotate(double dh, double dp, double dr) {
		Transform3D oldT3D = new Transform3D();
		presentation.getTransform(oldT3D);

		Transform3D localT3D = new Transform3D();
		double x = 0, y = 0, z = 0;
		x = Math.PI * dh / 180;
		y = Math.PI * dp / 180;
		z = Math.PI * dr / 180;

		localT3D.setEuler(new Vector3d(y, -x, z));
		// localT3D.rotY(-Math.sin(x) * Math.cos(y));
		// oldT3D.mul(localT3D);

		// localT3D.rotX(Math.cos(x) * Math.sin(y));
		// oldT3D.mul(localT3D);
		// localT3D.rotZ(z);

		if (modeCameraRotationScene)
			oldT3D.mul(localT3D, oldT3D);
		else
			oldT3D.mul(localT3D);

		presentation.setTransform(oldT3D);
		setTransform(oldT3D, true);
	}

	public void refresh() {
		Transform3D t3d = new Transform3D();
		presentation.getTransform(t3d);
		t3d.setRotation(abstraction.getOrientation());
		t3d.setTranslation(abstraction.getPosition());
		presentation.setTransform(t3d);
	}

	public PCamera getPresentation() {
		return presentation;
	}

	public String getOwnerName() {
		return abstraction.getOwnerName();
	}

	public Vector3d getPosition() {
		return abstraction.getPosition();
	}

	public void setPosition(Vector3d position, boolean diffuse) {
		abstraction.setPosition(position, false);
		if (diffuse) {
			try {
				serverProxy.update(abstraction);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public Quat4d getOrientation() {
		return abstraction.getOrientation();
	}

	public void setOrientation(Quat4d orientation, boolean diffuse) {
		abstraction.setOrientation(orientation, false);
		if (diffuse) {
			try {
				serverProxy.update(abstraction);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void setTransform(Transform3D t3d, boolean diffuse) {
		abstraction.setTransform(t3d, false);
		if (diffuse) {
			try {
				serverProxy.update(abstraction);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public ACamera getAbstraction() {
		return abstraction;
	}
}
