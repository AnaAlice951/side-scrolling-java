package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGame;
import com.mygdx.game.State;
import com.mygdx.game.components.TextComponent;
import com.mygdx.game.world.Camera;

public class AllClearScreen implements Screen {
    private MyGame game;

    private Texture screen;

    private State state;

    private TextComponent score;

    private Sound allClearSound;

    private SpriteBatch batch;

    private Camera camera;

    public AllClearScreen(SpriteBatch batch, MyGame game, State state, Camera camera) {
        this.batch = batch;
        this.game = game;
        this.state = state;
        this.camera = camera;
    }

    @Override
    public void show() {
        screen = new Texture(Gdx.files.internal("all_clear_screen.png"));
        allClearSound = Gdx.audio.newSound(Gdx.files.internal("audio/all_clear.mp3"));
        score = new TextComponent(40);
        allClearSound.play();
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

        score.write(
            (SpriteBatch) batch,
            (int) (camera.position.x - 50),
            (int) (camera.position.y + 20),
            String.valueOf(state.getScore() + 10 * state.getHeartsCollected()),
            Color.WHITE
        );

        batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
            game.setScreen(new PlayScreen(batch, game));
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
        screen.dispose();
        allClearSound.dispose();
    }
}
