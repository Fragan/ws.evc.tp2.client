package j3d.presentation.universe;

import java.awt.Component;

import j3d.controller.universe.CSharedUniverse;
import j3d.scene.Scene;

import javax.media.j3d.Canvas3D;
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
	private TransformGroup tgCamera;
	Scene scene ;
	
	public PSharedUnivrese(CSharedUniverse controller, Canvas3D canvas) {
		super(canvas);
		getViewingPlatform().setNominalViewingTransform();
		this.controller = controller;
		this.scene = Scene.createDefaultScene();
		getViewer().getView().setSceneAntialiasingEnable(true); // Yeah !!
		addBranchGraph(scene);
		
		//Create a user camera
		tgCamera = getViewingPlatform().getViewPlatformTransform(); 
		try {
			tgCamera.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		} catch (Exception e) {
		}
	
	}

	public CSharedUniverse getController() {
		return controller;
	}

	public TransformGroup getTransformgroupCamera() {
		return tgCamera;
	}
	
	public void add(PCamera camera) {
		scene.addChild(camera);
	}
	
	public void add(PObject object) {
		scene.addChild(object);
	}
	
	public void remove(PCamera camera) {
		scene.removeChild(camera);
	}
	
	public void remove(PObject object) {
		scene.removeChild(object);
	}
	
	public void compile() {		
		scene.compile();
	}

	public Scene getScene() {
		return scene;
	}
	
	
	
}
