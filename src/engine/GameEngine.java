package engine;

public class GameEngine implements Runnable {

	public static final int FPS_TARGET = 60;

	private final Window window;
	private final Timer timer;
	private final Game game;

	public GameEngine(String title, int width, int height, boolean vsync, Game game) {
		this.window = new Window(title, width, height, vsync);
		this.game = game;
		this.timer = new Timer();
	}

	@Override
	public void run() {
		try {
			init();
			mainLoop();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		finally {
			cleanup();
		}
	}

	public void init() throws Exception {
		window.init();
		game.init();
		timer.init();
	}

	public void mainLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / FPS_TARGET;

        boolean running = true;
        while (running && !window.windowShouldClose()) {

            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();

            if (!window.isVsync()) {
                sync();
            }
        }

				game.dispose();
	}

	private void sync() {
        float loopSlot = 1f / FPS_TARGET;
        double endTime = timer.getLastTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
    }

	public void update(float timestep) {
		game.update(timestep);
	}

	public void render() {
		game.render(window);
		window.update();
	}

	public void input() {
		game.input(window);
	}

	public void cleanup() {
		ResourceLoader.disposeShaders();
	}

}
