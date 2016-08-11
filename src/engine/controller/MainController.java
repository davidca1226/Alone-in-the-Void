package engine.controller;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import engine.Game;
import engine.Utility;
import engine.grid.GridController;
import entity.Entity;
import entity.ship.Carrier;
import entity.ship.Fighter;
import entity.ship.WarpShip;
import entity.station.Station;

public class MainController {
	
	private int escalation = 0;
	private int momentum = 0;
	private int warpShips;
	private int desiredWarpShips = 1;
	private int resources;
	private int forceStrength;
	private int desiredForceStrength;
	private int aggrivation = 0;
	private int aggrivationThreshold;
	private boolean assaulting = false;
	
	private int assaultTimer;
	
	private static FighterController fighterController; //add new controllers to
	private static CarrierController carrierController; //both here and 
	private static WarpShipController warpShipController; //the constructor (1/3)
	private static Entity target;
	
	private static ArrayList<ControllerAbstract> controllerList = 
			new ArrayList<ControllerAbstract>();
	
	private static int difficulty = 1;

	public MainController(Station station) {
		
		resources = 10000;
		aggrivationThreshold = 100;
		
		fighterController = new FighterController(); //new controllers go here AND
		carrierController = new CarrierController(); //(2/3)
		warpShipController = new WarpShipController();
		
		controllerList.add(fighterController);  //new controllers also go here (3/3)
		controllerList.add(carrierController);
		controllerList.add(warpShipController);
		
		setTarget(station);	
		
	}
	
	public void update() {
		
		resources++;
		aggrivation++; //TODO hacked together, make this better
		
		if (aggrivation >= aggrivationThreshold && assaulting == false) {
			assaulting = true;
			momentum = 50;
			desiredForceStrength = 100; //TODO hacked together, make this better
		}
		
		//if (assaulting) {
		//	assaultUpdate();
		//}
		
		
		
		for (int i=0; i < controllerList.size(); i++) {
			controllerList.get(i).update();
		}
	}
	
	public void assaultUpdate() {
		assaultTimer++;
		if (!(assaultTimer >= Game.getTPS())) {
			return;
		} else {
			assaultTimer = 0;
		}
		int focus = 0;
		if (warpShips < desiredWarpShips) { //0 is do nothing, 1 is spawn warp ship
			focus = 1;
		} else if (Math.random() < .9) { //2 is spawn carrier
			focus = 0;
		} else if (Math.random() < .1) { //3 is spawn fighter
			focus = 2;
		} else {
			focus = 3;
		}
		
		switch (focus) {
		
		default: return;
		
		case 1: 
			int mapSize = GridController.getGridSize() *
				GridController.getMapSize(); //mapsize in PIXELS
			int xPos = 0;
			int yPos = 0;
			
			if (Math.random() >= .5) { //on x axis
				if (Math.random() >= .5) { // near 0
					xPos = 10;
					yPos = 10 + ((int)(Math.random() * (mapSize - 20)));
				} else { //near max
					xPos = mapSize - 10;
					yPos = 10 + ((int) (Math.random() * (mapSize - 20)));
				}
			} else { //on y axis
				if (Math.random() >= .5) { // near 0
					xPos = 10 + ((int)(Math.random() * (mapSize - 20)));
					yPos = 10;
				} else { // near max
					xPos = 10 + ((int)(Math.random() * (mapSize - 20)));
					yPos = mapSize - 10;
				}
			}
			createNewWarpShip(xPos, yPos);
			warpShips++;
			break;
		}
	}
	
	public void setTarget(Entity targetEntity) {
		
		target = targetEntity;
		for (int i=0; i < controllerList.size(); i++) {
			controllerList.get(i).setTarget(target);
			
		}
	}
	
	public void render(Graphics g) {

	}
	
	public void updateStation(Station station) {

	}
	
	public static List<Entity> getAllEntities() {
		ArrayList<Entity> finalList = new ArrayList<Entity>();
		
		for (int i = 0; i < controllerList.size(); i++)
			finalList.addAll(controllerList.get(i).getControlledList());
	
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
	
	public static void createNewFighter(int xPos, int yPos) {
		Fighter newFighter = new Fighter(xPos, yPos); 
			
		fighterController.add(newFighter);
		GridController.addEntity(newFighter);
	}	
	
	public static void createNewCarrier(int xPos, int yPos) {
		Carrier newCarrier = new Carrier(xPos, yPos); 
		
		carrierController.add(newCarrier);
		GridController.addEntity(newCarrier);
	}
	
	public static void createNewWarpShip(int xPos, int yPos) {
		WarpShip newWarpShip = new WarpShip(xPos, yPos); 
		
		warpShipController.add(newWarpShip);
		GridController.addEntity(newWarpShip);
	}
}
