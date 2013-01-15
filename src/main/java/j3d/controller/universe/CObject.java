package j3d.controller.universe;

import j3d.abstraction.universe.AObject;
import j3d.presentation.universe.PObject;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3d;

import tools.Downloader;

// controle les interactions
public class CObject extends AObject {
	
	private PObject presentation;
	
	public CObject(String name, String urlGeometry) {
		super(name, urlGeometry);		
		
		if (urlGeometry.startsWith("http")) {
			urlGeometry = Downloader.donwloadFile(urlGeometry, false);
		}
	
		presentation = new PObject(this, urlGeometry);
	}
	
	/*public void rotateTo(double h, double d, double r) {
		Vector3d rotate = new Vector3d();
		rotate.set(h, d, r);

		Transform3D t3D = new Transform3D();
		presentation.getTransform(t3D);

		Vector3d translate = new Vector3d();

		t3D.get(translate);
		t3D.setEuler(rotate);
		t3D.setTranslation(translate);

		presentation.setTransform(t3D);
		super.setTransform(t3D);
	}*/
	
	public void rotate(double dh,
			double dp, double dr) {
		
		Transform3D oldT3D = new Transform3D();
		presentation.getTransform(oldT3D);	
		Transform3D tc = new Transform3D();
				
		double x = 0, y = 0, z = 0;
		x = (Math.PI * dh / 180);
		y = Math.PI * dp / 180;
		z = Math.PI * dr / 180;		

		tc.setEuler(new Vector3d(-y ,x ,z));
		oldT3D.mul(tc);
		
		presentation.setTransform(oldT3D);
		setTransform(oldT3D);
	}
	
	public void translate(double dx,
			double dy, double dz) {
		Transform3D vpT3D = new Transform3D();
		presentation.getTransform(vpT3D);
		Transform3D vpT3Dinv = new Transform3D();
		vpT3Dinv.invert(vpT3D);

		Transform3D oldT3D = new Transform3D();
		presentation.getTransform(oldT3D);
		Vector3d translate = new Vector3d();
		translate.set(dx, dy, dz);
		Transform3D localDeltaT3D = new Transform3D();
		localDeltaT3D.setTranslation(translate);
		Transform3D absoluteDeltaT3D = new Transform3D();
		absoluteDeltaT3D.mul(vpT3D, localDeltaT3D);
		absoluteDeltaT3D.mul(vpT3Dinv);
		Transform3D newT3D = new Transform3D();
		newT3D.mul(absoluteDeltaT3D, oldT3D);
		presentation.setTransform(newT3D);
		setTransform(newT3D);
	}
	
	public PObject getPresentation() {
		return presentation;
	}

}
