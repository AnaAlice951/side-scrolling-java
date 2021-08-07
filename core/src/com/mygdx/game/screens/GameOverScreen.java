package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGame;
import com.mygdx.game.Batcher;
import com.mygdx.game.world.Camera;

public class GameOverScreen implements Screen {
    private MyGame game;
    private Texture screen;
    private Batcher batch;
    private Camera camera;
    private Sound gameOverSound;

    public GameOverScreen(MyGame game) {
        batch = Batcher.getInstance();
        this.game = game;
        camera = Camera.getInstance();
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("audio/game_over.mp3"));
    }

    @Override
    public void show() {
        screen = new Texture(Gdx.files.internal("game_over_screen.png"));
        gameOverSound.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(
                screen,
                camera.position.x - (Gdx.graphics.getWidth()/2), camera.position.y - (Gdx.graphics.getHeight()/2),
                900, 642,
                0, 0,
                900, 642,
                false, false
        );
        batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            game.setScreen(new PlayScreen(game));
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameOverSound.dispose();
    }
}
