package j3d.presentation.universe;

import j3d.controller.universe.CCamera;
import j3d.object.VirtualObject;

import java.awt.Font;
import java.io.FileNotFoundException;

import javax.media.j3d.Appearance;
import javax.media.j3d.Billboard;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

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
	 * 
	 * @param cCamera
	 * @param tgCamera
	 * @param cameraObjectUrl
	 */
	public PCamera(CCamera cCamera, String cameraObjectUrl) {

		
		controller = cCamera;
		realTgCamera = new VirtualObject();

		realTgCamera.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		realTgCamera.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		realTgCamera.setCapability(TransformGroup.ENABLE_PICK_REPORTING);

		VrmlLoader loader = new VrmlLoader();
		try {
			scene = loader.load(cameraObjectUrl);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Pyramid
		realTgCamera.addChild(scene.getSceneGroup());

		// Billboard name
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				400.0);
		TransformGroup billboard = createBillboard(controller.getOwnerName(),
				new Point3f(0f, 0f, 0f), Billboard.ROTATE_ABOUT_POINT,
				new Point3f(0f, 0f, 0f), bounds);
		realTgCamera.addChild(billboard);

		this.addChild(realTgCamera);
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

	private TransformGroup createBillboard(String szText,
			Point3f locationPoint, int nMode, Point3f billboardPoint,
			BoundingSphere bounds) {

		TransformGroup subTg = new TransformGroup();
		subTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Font3D f3d = new Font3D(new Font("SansSerif", Font.PLAIN, 1),
				new FontExtrusion());
		Text3D label3D = new Text3D(f3d, szText, locationPoint);

		Appearance app = new Appearance();

		Color3f black = new Color3f(0.1f, 0.1f, 0.1f);
		Color3f objColor = new Color3f(0.2f, 0.2f, 0.2f);

		app.setMaterial(new Material(objColor, black, objColor, black, 90.0f));
		Shape3D sh = new Shape3D(label3D, app);

		subTg.addChild(sh);

		Billboard billboard = new Billboard(subTg, nMode, billboardPoint);
		billboard.setSchedulingBounds(bounds);
		subTg.addChild(billboard);

		return subTg;
	}

}
