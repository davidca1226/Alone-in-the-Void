package engine;

import java.awt.Graphics;

public class Economy {

	private static double credits;
	private static double interest;
	private static int interestPeriod;

	private static int interestProgress = 1;

	public static void Init(double creditsInit, double interestInit,
			int periodInit) {
		credits = creditsInit;
		interest = interestInit;
		interestPeriod = periodInit;
	}

	public static void update() {
		interestProgress++;
		if (interestProgress >= interestPeriod) {
			interestProgress = 1;
			credits += (credits * interest);
		}
	}

	public static void render(Graphics g) {
		g.drawString(String.valueOf(credits), 100, 100);
	}

	public static void modifyCredits(int amount) {
		credits += amount;
	}

	public static void modifyCredits(double amount) {
		credits += amount;
	}

	public static double getCredits() {
		return credits;
	}

}
