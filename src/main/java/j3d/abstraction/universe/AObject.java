package j3d.abstraction.universe;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;


// Les donnees
public class AObject {

	private String name;
	private Vector3d position;
	private Quat4d orientation;
	private String urlGeometry;
	
	public AObject(String name, String urlGeometry) {
		this.name = name;
		this.urlGeometry = urlGeometry;
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

	public String getName() {
		return name;
	}

	public String getURLGeometry() {
		return urlGeometry;
	}
}
