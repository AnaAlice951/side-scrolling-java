package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Classe responsável pela renderização de cada um dos componentes gráficos do jogo
 */
public class Batcher extends SpriteBatch {
    private static Batcher instance;

    private Batcher() {
        super();
    }

    /**
     * Retorna uma instância única da classe (Singleton)
     *
     * @return instância única
     */
    public static Batcher getInstance() {
        if(instance == null) {
            synchronized (State.class) {
                if(instance == null) {
                    instance = new Batcher();
                }
            }
        }

        return instance;
    }

    /**
     * Redefine a instância para o estado inicial
     *
     * @return instância redefinida
     */
    public Batcher resetInstance() {
        instance = new Batcher();
        return instance;
    }
}
