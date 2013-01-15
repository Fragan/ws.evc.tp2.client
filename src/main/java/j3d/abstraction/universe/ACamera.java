package j3d.abstraction.universe;

import javax.media.j3d.Transform3D;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;


public class ACamera {

	private String ownerName;
	private Vector3d position;
	private Quat4d orientation;
	
	public ACamera(String ownerName) {
		this.ownerName = ownerName;
		this.orientation = new Quat4d();
		this.position = new Vector3d();
	}

	public String getOwnerName() {
		return ownerName;
	}

	public Vector3d getPosition() {
		return position;
	}

	public void setPosition(Vector3d position) {
		this.position = position;
	}

	public Quat4d getOrientation() {
		return orientation;
	}

	public void setOrientation(Quat4d orientation) {
		this.orientation = orientation;
	}

	public void setTransform(Transform3D t3d) {
		t3d.get(orientation, position);
	}
}
