package j3d.abstraction.universe;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;


public class ACamera {

	private String name;
	private Vector3d position;
	private Quat4d orientation;
	
	public ACamera(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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
}
