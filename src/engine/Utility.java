package engine;

public class Utility {
	
	public static boolean calculateCollision(double x1,
			double y1, double x2, double y2, double issuerScale, double targetScale) {
		double xDistance = Math.abs(x1 - x2);
		double yDistance = Math.abs(y1 - y2);
		double actualDistance = Math.sqrt(xDistance*xDistance + yDistance*yDistance);
		if (actualDistance < (issuerScale+targetScale) / 2)
			return true;
		else
			return false;
	}
	
	public static double toDegrees(double angle) {
	    return (angle * 180) / Math.PI;
	}
	
	public static double standardizeAngle(double angle) {
		while (angle < 0)
			angle += 360;
		return Math.abs(angle % 360);
	}
	
	public static boolean isOnCourse(double issuerXPos, double issuerYPos,
			double targetXPos, double targetYPos,
			double issuerXVelocity, double issuerYVelocity,
			double targetXVelocity, double targetYVelocity,
			double issuerScale, double targetScale) {
		
		double mintime =  //time at which these will be closest
				  -(issuerXPos*issuerXVelocity - issuerXVelocity*targetXPos
						  - (issuerXPos - targetXPos)*0 + 
						  issuerYPos*issuerYVelocity - issuerYVelocity*targetYPos
						  - (issuerYPos - targetYPos)*0) 
				  /
				  (issuerXVelocity * issuerXVelocity - 2*issuerXVelocity*targetXVelocity + targetXVelocity * targetXVelocity +
						  issuerYVelocity * issuerYVelocity - 2*issuerYVelocity*targetYVelocity + targetYVelocity * targetYVelocity);
		if (mintime <= 0) return false;
		double mindist = Math.sqrt(
				  Math.pow((mintime*issuerXVelocity - mintime*targetXVelocity + issuerXPos - targetXPos), 2) 
				  + 
				  Math.pow((mintime*issuerYVelocity - mintime*targetYVelocity + issuerYPos - targetYPos), 2)
				);
		System.out.println(mintime + " . " + mindist);
		
		
		
		if (Math.abs(mindist) < issuerScale + targetScale) return true;
		else return false;
		
	}

	public static double getDistance(double xPos1, double yPos1, double xPos2,
			double yPos2) {
		return Math.sqrt(((xPos1 - xPos2) * (xPos1 - xPos2))
				+ ((yPos1 - yPos2) * (yPos1 - yPos2)));
	}
	
	public static boolean isWithinRect(int xPosTarget, int yPosTarget,
			int xOrigin, int yOrigin, int xSize, int ySize) {
		if (xPosTarget > xOrigin && xPosTarget < xOrigin + xSize &&
				yPosTarget > yOrigin && yPosTarget < yOrigin + ySize)
			return true;
		else
			return false;
	}
	
	public static int getLayoutOccurences(int[][] layout, int target) {
		int occurences = 0;
		for (int xi = 0; xi < layout.length; xi++) {
			for (int yi = 0; yi < layout[xi].length; yi++)
				if (layout[xi][yi] == target)
					occurences++;
		}
		return occurences;
	}
}
