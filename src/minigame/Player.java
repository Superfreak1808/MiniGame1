package minigame;

import java.awt.image.BufferedImage;

public class Player {
	public Sprite sprite;
	public int health = 100;
	
	public int score = 100;
	
	public Player() {
		this.sprite = new Sprite(50,50,"./res/ship.png");
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int amount) {
		score = amount;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void addHealth(int amount) {
		this.health += amount;
	}
	
	public void removeHealth(int amount) {
		this.health -= amount;
	}
	
	public BufferedImage getImage() {
		return sprite.getImage();
	}
}