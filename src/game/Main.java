package game;

import engine.*;

public class Main {
	public static void main(String[] args) {
		AsteroidsGame test = AsteroidsGame.getGame();
		GameEngine ge = new GameEngine("TEST", 1280, 720, true, test);
		ge.run();
	}
}
