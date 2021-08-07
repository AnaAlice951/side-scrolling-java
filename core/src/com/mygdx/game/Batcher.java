package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Batcher extends SpriteBatch {
    private static Batcher instance;

    private Batcher() {
        super();
    }

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

    public Batcher resetInstance() {
        instance = new Batcher();
        return instance;
    }
}
