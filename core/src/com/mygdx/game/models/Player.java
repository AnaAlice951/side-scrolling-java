package com.mygdx.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    private int PLAYER_WIDTH = 32;
    private int PLAYER_HEIGHT = 96;
    private int WHIP_WIDTH = 150 - PLAYER_WIDTH;
    private int WHIP_HEIGHT = 32;
	private Rectangle playerHitbox, attackHitbox;

    private boolean attacking = false;
    private boolean movingRight = false;
    private boolean movingLeft = false;
    private State gameState;

    private ShapeRenderer sr = new ShapeRenderer();

    public Player(State state){
    	gameState = state;
        //Propriedades do player
        playerHitbox = new Rectangle(GAME_UNIT, GAME_UNIT, PLAYER_WIDTH, PLAYER_HEIGHT);
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
		        playerHitbox.x,playerHitbox.y,
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
						playerHitbox.x, playerHitbox.y,
						64, PLAYER_HEIGHT,
						0, 0,
						currentFrameAttacking.getWidth(), currentFrameAttacking.getHeight(),
						!flipped, false
					);
				} else {
					if(flipped) {
						//playerHitbox = new Rectangle(playerHitbox.x+32, playerHitbox.y, PLAYER_WIDTH, PLAYER_HEIGHT);
				        attackHitbox = new Rectangle(playerHitbox.x - 64, playerHitbox.y + 32, 64, 32);
						batch.draw(
							currentFrameAttacking,
							playerHitbox.x - 64, playerHitbox.y,
							64 * 2, PLAYER_HEIGHT,
							0, 0,
							currentFrameAttacking.getWidth(), currentFrameAttacking.getHeight(),
							!flipped, false
						);
					} else {
				        attackHitbox = new Rectangle(playerHitbox.x + 64, playerHitbox.y + 32, 64, 32);
						batch.draw(
							currentFrameAttacking,
							playerHitbox.x, playerHitbox.y,
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
				playerHitbox.x, playerHitbox.y,
				64, PLAYER_HEIGHT,
				0, 0,
				walkFrames[0].getWidth(), walkFrames[0].getHeight(),
				flipped, false
			);
		}
		//sr.begin(ShapeRenderer.ShapeType.Filled);
		//sr.rect(attackHitbox.x, attackHitbox.y, attackHitbox.width, attackHitbox.height);
		//sr.end();
	}
	
	public void move(float delta, int stage) {
		if(isMovingRight()) {
			playerHitbox.x += 300 * delta;
			flipped = false;
		} else if(isMovingLeft()) {
			playerHitbox.x -= 300 * delta;
			flipped = true;
		}

		verifyOverflow(gameState.getStage());
    }

    public void verifyOverflow(int stage) {
    	float leftLimit = (GAME_UNIT * 50 * (stage - 1) - 20);
		float rightLimit = leftLimit + (GAME_UNIT * 50 - (PLAYER_WIDTH - 10));

		if(stage == 2 || stage == 3 ) {
			leftLimit = (GAME_UNIT * 50) -20;
			rightLimit = leftLimit + (GAME_UNIT * 100 - (PLAYER_WIDTH - 10));
		}

		if(playerHitbox.x <= leftLimit)
			playerHitbox.x = leftLimit;
		if(playerHitbox.x >= rightLimit)
			playerHitbox.x = rightLimit;
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

        sr.dispose();
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

	public void setPlayerHitbox(Rectangle playerHitbox) {
		this.playerHitbox = playerHitbox;
	}

	public Rectangle getAttackHitbox() {
		return attackHitbox;
	}

	public void setAttackHitbox(Rectangle attackHitbox) {
		this.attackHitbox = attackHitbox;
	}
}