package minigame;

import java.util.Random;

public class Item 
{
	public int x_cord = 0;
	public int y_cord = 0;
	
	public Item () {
		Random rand = new Random();
		int rand_y = rand.nextInt((700) + 1);
		int rand_x = rand.nextInt((500) + 1);
		
		this.x_cord = 1300 + rand_x;
		this.y_cord = rand_y;
	}
	
	public int getX() {
		return x_cord;
	}
	
	public int getY() {
		return y_cord;
	}
}
