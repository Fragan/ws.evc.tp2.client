package ihm.app;

import ihm.interaction.keyboard.KeyCameraStateForMouseInteractor;
import ihm.interaction.mouse.MouseInteractor;
import ihm.interaction.mouse.MouseStimulusCamera;
import ihm.interaction.mouse.MouseStimulusObject;
import j3d.controller.universe.CCamera;
import j3d.controller.universe.CObject;
import j3d.controller.universe.CSharedUniverse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.J3DGraphics2D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class CanvasExtended extends Canvas3D {

	private static final long serialVersionUID = 1L;
	private CSharedUniverse universe;
	
	private MouseInteractor mouseInteractor;
	private CCamera camera;

	public CanvasExtended(String ownerName) {
		super(SimpleUniverse.getPreferredConfiguration());
		setDoubleBufferEnable(true);
		
		//universe = new 

		//Load a vrml model
		
		CObject cube = new CObject(ownerName + "CubeObject", "http://espacezives.free.fr/colorcube2.wrl");
		CObject cube2 = new CObject(ownerName + "Cube2Object", "http://espacezives.free.fr/colorcube2.wrl");
		
		//Create a universe
		universe = new CSharedUniverse(ownerName, this);
		
		universe.add(cube);
		universe.add(cube2);
		
		TransformGroup tgCamera = universe.getPresentation().getTransformgroupCamera();
		camera = new CCamera(ownerName, tgCamera);
		camera.relativeTranslate(0, 0, 5.0); 
		universe.add(camera);

		//Add a mouse interactor to the scene
		mouseInteractor = new MouseInteractor(universe.getScene(), camera);
		mouseInteractor.setSchedulingBounds(new BoundingSphere(new Point3d(),
				1000.0));
		universe.addMouseInteractor(mouseInteractor);

		//Add a keylistener to the canvas
		addKeyListener(new KeyCameraStateForMouseInteractor(this, getMouseInteractor()));
	
		//Compile the scene
		universe.compileScene();

	}


	public MouseInteractor getMouseInteractor() {
		return mouseInteractor;
	}
		
	public CCamera getCamera() {
		return camera;
	}


	@Override
	public void update(Graphics g) {
		super.update(g);
	}


	@Override
	public void postRender() {	
		super.postRender();
		J3DGraphics2D g = getGraphics2D();
		Font myFont = new Font("Courier", Font.BOLD ,16);
  
		g.setFont(myFont);
		g.setColor(Color.WHITE);
		if (getMouseInteractor().getCurrentState() instanceof MouseStimulusObject)
			g.drawString("MODE OBJECT ON", 13, 17);
		else if (getMouseInteractor().getCurrentState() instanceof MouseStimulusCamera)
			g.drawString("MODE CAMERA ON", 13,17);
		else
			g.drawString("UNKNOWN MODE", 13, 17);
		g.flush(false);
		g.dispose();
	}

	

}
