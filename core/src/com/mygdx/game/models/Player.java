package com.mygdx.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {

	//PlAYER FRAMES
    private Animation<Texture> walkAnimation;
    private Animation <Texture> attackAnimation;
    private Texture[] walkFrames = new Texture[5];
    private Texture[] attackFrames = new Texture[3];
	private boolean flipped = false;
	private float stateTime = 0f;
	

	private float GAME_UNIT = 32 * 0.8f;
    public int PLAYER_WIDTH = 32;
    public int PLAYER_HEIGHT = 96;
	private Rectangle playerHitbox, attackHitbox;

	private float x, y;
    private boolean attacking = false;
    private boolean movingRight = false;
    private boolean movingLeft = false;
    private boolean jumping = false;
    private float jumpingInitialPosition;
    private State gameState;

    public Player(State state){
    	gameState = state;
        //Propriedades do player
        playerHitbox = new Rectangle(GAME_UNIT, 332, PLAYER_WIDTH, PLAYER_HEIGHT);
        x = GAME_UNIT;
        y = 332;

        attackHitbox = new Rectangle(0, 0, 0, 0);
        
        //Animação
        for(int i = 0; i < 5; i++)
            walkFrames[i] = new Texture(Gdx.files.internal("player/walk"+ (i + 1) + ".png"));
        walkAnimation = new Animation<Texture>(0.1f, walkFrames);
    
        for(int i = 0; i < 3; i++)
        	attackFrames[i] = new Texture(Gdx.files.internal("player/attack"+ (i + 1) + ".png"));
        attackAnimation = new Animation<Texture>(0.1f, attackFrames);
    }
    
	public void draw(SpriteBatch batch) {
		attackHitbox = new Rectangle(0, 0, 0, 0);
		stateTime += Gdx.graphics.getDeltaTime();

		if(!attacking && (movingLeft || movingRight)) {
			Texture currentFrameWalking = walkAnimation.getKeyFrame(stateTime, true);

			batch.draw(
		        currentFrameWalking,
		        x,y,
		        64, PLAYER_HEIGHT,
		        0, 0,
		        currentFrameWalking.getWidth(), currentFrameWalking.getHeight(),
		        flipped, false
			);
		} else if (attacking) {
			Texture currentFrameAttacking = attackAnimation.getKeyFrame(stateTime, false);

			if(!attackAnimation.isAnimationFinished(stateTime)) {
				if(attackAnimation.getKeyFrameIndex(stateTime) == 0 || attackAnimation.getKeyFrameIndex(stateTime) == 1) {
					batch.draw(
						currentFrameAttacking,
						x, y,
						64, PLAYER_HEIGHT,
						0, 0,
						currentFrameAttacking.getWidth(), currentFrameAttacking.getHeight(),
						!flipped, false
					);
				} else {
					if(flipped) {
						attackHitbox = new Rectangle(x - 64, y + 32, 64, 32);
						batch.draw(
							currentFrameAttacking,
							x - 64, y,
							64 * 2, PLAYER_HEIGHT,
							0, 0,
							currentFrameAttacking.getWidth(), currentFrameAttacking.getHeight(),
							!flipped, false
						);
					} else {
				        attackHitbox = new Rectangle(x + 64, y + 32, 64, 12);
						batch.draw(
							currentFrameAttacking,
							x, y,
							64 * 2, PLAYER_HEIGHT,
							0, 0,
							currentFrameAttacking.getWidth(), currentFrameAttacking.getHeight(),
							!flipped, false
							
						);
					}
				}
			} else {
				setAttacking(false);
			}
		} else {
			batch.draw(
				walkFrames[0],
				x, y,
				64, PLAYER_HEIGHT,
				0, 0,
				walkFrames[0].getWidth(), walkFrames[0].getHeight(),
				flipped, false
			);
		}
	}
	
	public void move(float delta) {
		if(isMovingRight()) {
			x += 300 * delta;
			flipped = false;
		} else if(isMovingLeft()) {
			x -= 300 * delta;
			flipped = true;
		}

		verifyOverflow(gameState.getStage());

		if(isJumping())
			y += 1000 * delta;

		if(y >= jumpingInitialPosition + 120)
			setJumping(false);

		playerHitbox.x = x;
		playerHitbox.y = y;
    }

    public void verifyOverflow(int stage) {
    	float leftLimit = (GAME_UNIT * 50 * (stage - 1) - 20);
		float rightLimit = leftLimit + (GAME_UNIT * 50 - (PLAYER_WIDTH - 10));

		if(stage == 2 || stage == 3 ) {
			leftLimit = (GAME_UNIT * 50) -20;
			rightLimit = leftLimit + (GAME_UNIT * 100 - (PLAYER_WIDTH - 10));
		}

		if(x <= leftLimit)
			x = leftLimit;

		if(x >= rightLimit)
			x = rightLimit;
	}

    public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public void dispose() {
        for(Texture frame: walkFrames)
            frame.dispose();
        for(Texture frame: attackFrames)
            frame.dispose();
    }

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
    	stateTime = 0;
		this.attacking = attacking;
	}

	public Rectangle getPlayerHitbox() {
		return playerHitbox;
	}

	public Rectangle getAttackHitbox() {
		return attackHitbox;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;

		if(jumping)
			jumpingInitialPosition = y;
	}
}