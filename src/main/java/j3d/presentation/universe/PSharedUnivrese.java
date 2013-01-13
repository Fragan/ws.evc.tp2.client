package j3d.presentation.universe;

import j3d.controller.universe.CSharedUniverse;
import ihm.app.CanvasLoader;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class PSharedUnivrese extends SimpleUniverse {
	
	/*
	 * Ici on met les objets Transgroup où plus généralement les objets java 3d
	 * les transformgroup (caméras et liste de virtuals objects doivent avoir la même position et angle que l'abstraction)
	 * les méthodes de tranformations se trouveront dans le controleur
	 * 
	 * on retrouvera aussi dans le controleur de object par exemple une méthode
	 * setangle qui s'occupera de modifier l'ange sur l'abstraction et aussi sur la présentation
	 */

	private CSharedUniverse controller;
	private PCamera camera;
	
	public PSharedUnivrese(CSharedUniverse controller, PCamera transCamera, TransformGroup scene) {
		super();
		this.controller = controller;
		getViewer().getView().setSceneAntialiasingEnable(true); // Yeah !!
		this.camera = transCamera;
	}

	public PSharedUnivrese(CSharedUniverse controller, CanvasLoader canvas) {
		super(canvas);
		this.controller = controller;
		getViewer().getView().setSceneAntialiasingEnable(true); // Yeah !!
		camera = (PCamera) canvas.getVpTrans();
	}
	
	public void getCameraTransform(Transform3D t3D) {
		camera.getTransform(t3D);
	}
	
	public void setCameraTransform(Transform3D t3D) {
		camera.setTransform(t3D);
	}
}
