package minigame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener 
{
	
	public Input(Game game)
	{
		game.addKeyListener(this);
	}

	
	public class Key
	{
		public boolean pressed = false;
		public boolean isPressed()
		{
			return pressed;
		}
		public void toggle(boolean isPressed)
		{
			pressed = isPressed;
		}
	}
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();

	public void keyPressed(KeyEvent e) 
	{
		toggle(e.getKeyCode(), true);
		
	}

	public void keyReleased(KeyEvent e) 
	{
		
		toggle(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent e) 
	{
		
		
	}
	
	public void toggle(int keyCode, boolean isPressed)
	{
		if(keyCode == KeyEvent.VK_W)
		{
			up.toggle(isPressed);
		}
		
		if(keyCode == KeyEvent.VK_S)
		{
			down.toggle(isPressed);
		}
		
		if(keyCode == KeyEvent.VK_A)
		{
			left.toggle(isPressed);
		}
		
		if(keyCode == KeyEvent.VK_D)
		{
			right.toggle(isPressed);
		}
	}

}
