package engine;

public class Utility {

	public static boolean calculateCollision(double distance, double x1,
			double y1, double x2, double y2) {
		double xDistance = Math.abs(x1 - x2);
		double yDistance = Math.abs(y1 - y2);
		double actualDistance = xDistance + yDistance;
		if (actualDistance < distance)
			return true;
		else
			return false;
	}
	
	
	public static double standardizeAngle(double angle) {
		while (angle < 0)
			angle += 360;
		return Math.abs(angle % 360);
	}
	
	public static boolean isOnCourse(double issuerXPos, double issuerYPos,
			double targetXPos, double targetYPos,
			double xVelocity, double yVelocity) {
		
		if (((targetXPos - issuerXPos) / xVelocity) * yVelocity >= (targetYPos - issuerYPos) * .9 &&
				((targetXPos - issuerXPos) / xVelocity) * yVelocity <= (targetXPos - issuerXPos) * 1.1)
			return true;
		return false;
		
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
