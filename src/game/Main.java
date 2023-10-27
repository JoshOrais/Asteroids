package game;

import engine.*;

public class Main {
	public static void main(String[] args) {
		//AsteroidsGame test = AsteroidsGame.getGame();
                A2Game a2 = A2Game.getInstance();
		GameEngine ge = new GameEngine("TEST", 1280, 720, true, a2);
		ge.run();
	}
}
