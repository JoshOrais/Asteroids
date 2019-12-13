package game.asteroids.graphics;

import java.util.ArrayList;

import org.joml.Matrix4f;

import engine.graphics.*;
import engine.ResourceLoader;

public class Hud {
    private float hudWidth, hudHeight;
    private Matrix4f projectionMatrix, viewMatrix;
    private GameFont font;
    private ArrayList<HudItem> items;
    private HudItem hpText, livesText, invulsplat, respawnTimer, gameOverText, gameOverSubText;
    private boolean doInvulsplat = false;
    private boolean oneFrameDelay = false, gameOver = false;
    private float flashTime = 0.f, respawnTime = 0.f;

    public Hud(GameFont font, float width, float height){
      this.projectionMatrix = new Matrix4f().identity().ortho(0.f, width, 0.f, height, 0.f, 1.f);
      this.viewMatrix = new Matrix4f().identity();
      this.hudWidth = width;
      this.hudHeight = height;
      this.font = font;
      items = new ArrayList<HudItem>();
      init();
    }

    public void init(){
      hpText = new HudItem(30.f, hudHeight - 90.f, 100, 30);
      hpText.setText("HP : 100%");
      items.add(hpText);

      livesText = new HudItem(30.f, hudHeight - 127.f, 100, 30);
      livesText.setText("Lives : 3");
      livesText.setTexture(ResourceLoader.getTexture("anime"));
      items.add(livesText);

      invulsplat = new HudItem(0, 0, hudWidth, hudHeight);
      invulsplat.setTexture(ResourceLoader.getTexture("invulsplat"));

      HudItem hud = new HudItem(0, 0, hudWidth, hudHeight);
      hud.setTexture(ResourceLoader.getTexture("hud"));
      items.add(hud);

      gameOverText = new HudItem(100.f, hudHeight - 200.f, 100, 80);
      gameOverText.setText("GAME OVER");

      gameOverSubText = new HudItem(100.f, hudHeight - 237.f, 100, 30);
      gameOverSubText.setText("Press R to restart");

      respawnTimer = new HudItem(300.f, hudHeight - 300.f, 100, 80);
    }

    public void flash(){
      flashTime = 1.f;
    }

    public void respawn(float time){
      if (respawnTime <= 0.f)
        respawnTime = time;
    }

    public void setHP(float amount){
      int hp = (int)amount;

      hpText.setText("HP : "+String.valueOf(hp)+"%");
    }

    public void update(float interval){
      flashTime -= interval;
      if (flashTime < 0.f) flashTime = 0.f;

      if (respawnTime > 0.f){
        respawnTime -= interval;
        respawnTimer.setText("Respawn in : " + String.valueOf(respawnTime));
        flashTime = 1.f;
      }
    }

    public void setLives(int amount){
      livesText.setText("Lives : "+String.valueOf(amount));
    }

    public void render(Shader s){
      s.bind();
      s.setUniformMatrix4f("projection", projectionMatrix);
      s.setUniformMatrix4f("view", viewMatrix);
      s.setUniform1f("flash", flashTime);

      if (gameOver){
        flashTime = 1.f;

        s.setUniformMatrix4f("model", invulsplat.getModelMatrix());
        invulsplat.getTexture().bind();
        ResourceLoader.getTranslatedQuadMesh().render(s);

        s.setUniformMatrix4f("model", gameOverText.getModelMatrix());
        font.getTexture().bind();
        gameOverText.getText().getMesh().render(s);

        s.setUniformMatrix4f("model", gameOverSubText.getModelMatrix());
        font.getTexture().bind();
        gameOverSubText.getText().getMesh().render(s);

        return;
      }

      for (HudItem i : items){
        s.setUniformMatrix4f("model", i.getModelMatrix());
        if (i.getTexture() != null){
          i.getTexture().bind();
          ResourceLoader.getTranslatedQuadMesh().render(s);
        }
        if (i.getText() != null){
          font.getTexture().bind();
          i.getText().getMesh().render(s);
        }
      }

      if (oneFrameDelay){
        s.setUniformMatrix4f("model", invulsplat.getModelMatrix());
        invulsplat.getTexture().bind();
        ResourceLoader.getTranslatedQuadMesh().render(s);
      }

      oneFrameDelay = doInvulsplat;

      if (respawnTime > 0.f){
        s.setUniformMatrix4f("model", respawnTimer.getModelMatrix());
        font.getTexture().bind();
        respawnTimer.getText().getMesh().render(s);
      }
    }

    public void setGameOver(boolean g){
      this.gameOver = g;
    }

    public void setInvulSplat(boolean a){
      this.doInvulsplat = a;
    }

    public class HudItem{
      private float posx, posy, width, height;
      private Texture texture = null;
      private Matrix4f modelMatrix;
      private TextObject text = null;
      public HudItem(float posx, float posy, float width, float height){
        this.posx = posx;
        this.posy = posy;
        this.width = width;
        this.height = height;
        modelMatrix = new Matrix4f();
      }

      public Matrix4f getModelMatrix(){
        modelMatrix.identity().translate(posx, posy, 0.f).scale(width, height, 1.f);
        return modelMatrix;
      }

      public void setTexture(Texture t){
        this.texture = t;
      }

      public void setText(String t){
        this.text = new TextObject(t, font);
      }

      public TextObject getText(){
        return text;
      }

      public Texture getTexture(){
        return texture;
      }
    }
}
