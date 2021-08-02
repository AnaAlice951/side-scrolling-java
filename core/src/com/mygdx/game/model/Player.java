package com.mygdx.game.model;

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
    private Texture[] attackFrames = new Texture[5];
    private boolean flipped = false, attacking = false;
    private Rectangle walkHitbox, attackHitbox;
    private float GAME_UNIT = 32 * 0.8f;
    private float stateTime = 0f;
    private int walkFramesOriginalWidth, walkFramesOriginalHeight, attackFramesOriginalWidth, attackFramesOriginalHeight;
    
    //Player movimentação
    private boolean movingRight = false;
    private boolean movingLeft = false;

    public Player(){

        //Propriedades do player
        walkFramesOriginalWidth = 40;
        walkFramesOriginalHeight = 32;
        attackHitbox = new Rectangle(GAME_UNIT * 0, GAME_UNIT * 1, 150, 96);
        walkHitbox = new Rectangle(GAME_UNIT * 0, GAME_UNIT * 1, 77, 96);



        //Animação
        for(int i = 0; i < 5; i++)
            walkFrames[i] = new Texture(Gdx.files.internal("player/walk"+ (i + 1) + ".png"));
        walkAnimation = new Animation<Texture>(0.1f, walkFrames);
    
        for(int i = 0; i < 3; i++)
        	attackFrames[i] = new Texture(Gdx.files.internal("player/attack"+ (i + 1) + ".png"));
        attackAnimation = new Animation<Texture>(1f, attackFrames);
    }
    
	public void draw(SpriteBatch batch) {
		stateTime += Gdx.graphics.getDeltaTime();
		

		Texture currentFrameWalking = walkAnimation.getKeyFrame(stateTime, true);
		Texture currentFrameAttacking = attackAnimation.getKeyFrame(stateTime, false);
		
		if(movingLeft || movingRight) {
			batch.draw(
		        currentFrameWalking,
		        walkHitbox.x,walkHitbox.y,
		        77, 96,
		        0, 0,
		        walkFramesOriginalWidth, walkFramesOriginalHeight,
		        flipped, false
			);
		} else if (attacking) {
			if(!attackAnimation.isAnimationFinished(stateTime)) {
				switch(attackAnimation.getKeyFrameIndex(stateTime)) {
					case 0:
						attackFramesOriginalWidth = 34;
						attackFramesOriginalHeight = 32;
						break;
					case 1: 
						attackFramesOriginalWidth = 34;
						attackFramesOriginalHeight = 32;
						break;
					case 2: 
						attackFramesOriginalWidth = 62;
						attackFramesOriginalHeight = 32;
						break;	
				}
				if(currentFrameAttacking == null)
					System.out.println("cu");
					
				if(currentFrameAttacking != null) {
					batch.draw(
						currentFrameAttacking,
						attackHitbox.x, attackHitbox.y,
				        150, 96,
				        0, 0,
				        attackFramesOriginalWidth, attackFramesOriginalHeight,
				        flipped, false
					);
				}
			} else 
				attacking = false;
		} else {
			batch.draw(
				walkFrames[0],
				walkHitbox.x,walkHitbox.y,
		        77, 96,
		        0, 0,
		        walkFramesOriginalWidth, walkFramesOriginalHeight,
		        flipped, false
			);
		}
	}
	
	public void move(float delta, int stage) {
		if(isMovingRight()) {
				walkHitbox.x += 300 * delta;
				attackHitbox.x += 300 * delta;
				flipped = false;
			} else if(isMovingLeft()) {
				walkHitbox.x -= 300 * delta;
				attackHitbox.x -= 300 * delta;
				flipped = true;
			}
		
			//verifyOverflow(stage);
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
		this.attacking = attacking;
        attackAnimation = new Animation<Texture>(1f, attackFrames);

	}

	
}