package engine.controller;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import engine.Utility;
import engine.grid.GridController;
import entity.Entity;
import entity.ship.Carrier;
import entity.ship.Fighter;
import entity.station.Station;

public class MainController {
	
	private int escalation = 0;
	private int momentum = 0;
	private int resources;
	private int aggrivation = 0;
	private boolean assaulting = false;
	
	private static FighterController fighterController;
	private static CarrierController carrierController;
	private static Entity target;
	
	private static ArrayList<ControllerAbstract> controllerList = 
			new ArrayList<ControllerAbstract>();
	
	private static int difficulty = 1;

	public MainController(Station station) {
		
		resources = 10000;
		
		fighterController = new FighterController();
		carrierController = new CarrierController();
		
		controllerList.add(fighterController);
		controllerList.add(carrierController);
		
		setTarget(station);
		
		Carrier carrier = new Carrier(25, 100); //TODO hacked together, remove later
		carrierController.add(carrier);
		GridController.addEntity(carrier);
		
		
	}
	
	public void update() {
		
		resources++;
		
		
		
		fighterController.update();
		carrierController.update();
	}
	
	public void setTarget(Entity targetEntity) {
		target = targetEntity;
		fighterController.setTarget(target);
		carrierController.setTarget(target);
	}
	
	public void render(Graphics g) {

	}
	
	public void updateStation(Station station) {

	}
	
	public static List<Entity> getFighterList() {
		ArrayList<Entity> finalList = new ArrayList<Entity>();
		finalList.addAll(fighterController.getControlledList());
		finalList.addAll(carrierController.getControlledList());
		return finalList;
	}
	
	public static Entity getFirstEntity() {
		if (!fighterController.getControlledList().isEmpty())
			return fighterController.getControlledList().get(0);
		else return null;
	}
	
	private static ArrayList<Entity> returnAllControlledEntities() {
		ArrayList<Entity> temp = new ArrayList<Entity>();
		for (int i = 0; i < controllerList.size(); i++)
			temp.addAll(controllerList.get(i).getControlledList());
		return temp;
	}
	
	public static Entity getNearestEntity(int xPos, int yPos) {
		Entity nearest= null;
		ArrayList<Entity> allControlledEntities = returnAllControlledEntities();
		if (allControlledEntities.size() != 0) {                                    //TODO make sure this won't screw me when I 
			nearest = allControlledEntities.get(0);                                 //only have 1 entity
			for (int i = 0; i < allControlledEntities.size(); i++) {
				if (Utility.getDistance(allControlledEntities.get(i).getXPos(),
						allControlledEntities.get(i).getYPos(), 
						xPos, yPos) 
						< Utility.getDistance(nearest.getXPos(), nearest.getYPos(), xPos, yPos))
					nearest = allControlledEntities.get(i);
			}
		}
			
		return nearest;
	}
	
	public static void createNewShip(int type, int xPos, int yPos) { //Type: 1 = fighter
		if (type == 1) {
			Fighter newFighter = new Fighter(xPos, yPos); 
			
			fighterController.add(newFighter);
			GridController.addEntity(newFighter);
		}	
	}
}
