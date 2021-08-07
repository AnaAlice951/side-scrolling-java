package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGame;
import com.mygdx.game.Batcher;

public class HowToPlayScreen implements Screen {
    private MyGame game;
    private Texture screen;
    private Batcher batch;

    public HowToPlayScreen(MyGame game) {
        batch = Batcher.getInstance();
        this.game = game;
    }

    @Override
    public void show() {
        screen = new Texture(Gdx.files.internal("how_to_play_screen.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(
            screen,
            0, 0,
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
        screen.dispose();
    }
}
