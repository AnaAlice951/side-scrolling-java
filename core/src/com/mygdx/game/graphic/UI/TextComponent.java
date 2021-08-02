package com.mygdx.game.graphic.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Classe responsável pela criação dos componentes de texto na tela
 * 
 * Referências:
 * 
 * Resposta de Luis Frontanilla no fórum do StackOverflow
 * @link https://stackoverflow.com/questions/57045981/draw-multi-line-bitmapfont-vertically-centered
 * 
 * Resposta de Pinkie Swirl no fórum do StackOverflow
 * @link https://stackoverflow.com/questions/14271570/libgdx-is-there-an-easy-way-to-center-text-on-each-axis-on-a-button
 */
public class TextComponent {
	private BitmapFont font;
	private FreeTypeFontGenerator fontGenerator;
	private FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter;
	private GlyphLayout glyphLayout;
	
	public TextComponent(int size) {
		font = new BitmapFont();
		
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoMono-Regular.ttf"));
		freeTypeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		freeTypeFontParameter.size = size;
		fontGenerator.generateData(freeTypeFontParameter);
		font = fontGenerator.generateFont(freeTypeFontParameter);
		
		glyphLayout  = new GlyphLayout();

	}
	
	public GlyphLayout getGlyphLayout() {
		return glyphLayout;
	}

	public void setGlyphLayout(GlyphLayout glyphLayout) {
		this.glyphLayout = glyphLayout;
	}

	public void write(SpriteBatch batch, int x, int y, String s, Color color) {
		glyphLayout.setText(font, s);
		font.setColor(color);
		font.draw(batch, glyphLayout, x, y);
	}
	
	public void dispose() {
		fontGenerator.dispose();
		font.dispose();
	}
}
