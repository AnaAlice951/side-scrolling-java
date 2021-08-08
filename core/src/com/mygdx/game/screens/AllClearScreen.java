package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGame;
import com.mygdx.game.State;
import com.mygdx.game.Batcher;
import com.mygdx.game.components.TextComponent;
import com.mygdx.game.world.Camera;

/**
 * Classe responsável por renderizar a tela de "fase concluída"
 */
public class AllClearScreen implements Screen {
    private MyGame game;

    private Texture screen;

    private State state;

    private TextComponent score;

    private Sound allClearSound;

    private Batcher batch;

    private Camera camera;

    public AllClearScreen(MyGame game) {
        batch = Batcher.getInstance();
        this.game = game;
        state = State.getInstance();
        camera = Camera.getInstance();
    }

    @Override
    public void show() {
        // define a tela de jogo completo
        screen = new Texture(Gdx.files.internal("all_clear_screen.png"));
        score = new TextComponent(40);

        // toca o som de jogo completo
        allClearSound = Gdx.audio.newSound(Gdx.files.internal("audio/all_clear.mp3"));
        allClearSound.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // renderiza a tela de jogo completo
        batch.begin();
        batch.draw(
            screen,
            camera.position.x - (Gdx.graphics.getWidth()/2), camera.position.y - (Gdx.graphics.getHeight()/2),
            900, 642,
            0, 0,
            900, 642,
            false, false
        );

        // renderiza a pontuação final do jogador
        score.write(
            (SpriteBatch) batch,
            (int) (camera.position.x - 50),
            (int) (camera.position.y + 20),
            String.valueOf(state.getScore() + 10 * state.getHeartsCollected()),
            Color.WHITE
        );

        batch.end();

        // verifica se o jogador deseja jogar novamente
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
        allClearSound.dispose();
    }
}
