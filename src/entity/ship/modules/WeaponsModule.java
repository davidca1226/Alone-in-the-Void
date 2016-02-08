package entity.ship.modules;

public class WeaponsModule extends ModuleAbstract {
	
	public WeaponsModule(ModuleInterface coreModule) {
		
	}
	
	public void doTypeSpecificTasks() {
		System.out.println("It worked, and overrode it suceffuly");
	}

	
}
