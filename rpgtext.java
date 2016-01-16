//Name: Zain
//Date: March 22nd, 2013
//Purpose: Text-Based RPG Assignment

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class rpgtext extends Applet implements MouseListener, KeyListener
{
    int screen; //Global variable to hold what screen is to be displayed
    int challenges; //Global variable for switching challenges
    /*
     0 = Title Screen
     1 = Waterfall Intro
     2 = Waterfall Cutscene
     3 = House Outside
     4 = House Inside
     5 = House Backyard
     */

    //All image names defined here to be used later in the code
    Image Title, WaterFall, introtran, Housefront, sprite, spriteleft, spriteright, spriteback, housesign, insidehouse, spritefront;
    ;

    //Player position: x plot, y plot (mainpositionx is used to set the sprite to default when the game is exited because the x1 and y1 change
    int x1 = 0;
    int y1 = 0;
    int mainposx = x1;
    int mainposy = y1;

    //Waterfall restrictions
    int[] [] validwaterfall = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
	    {1, 1, 0, 1, 1, 1, 0, 1, 1, 0},
	    {1, 1, 0, 1, 1, 1, 0, 1, 1, 0},
	    {1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
	    {1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
	    {1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
	    {1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //House outside restrictions
    int[] [] validhousefront = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
	    {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
	    {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0},
	    {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
	    {1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1},
	    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
	    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //House inside restrictions
    int[] [] validhouseinside = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0},
	    {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
	    {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
	    {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
	    {0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
	    {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};


    public void init ()
    {
	resize (800, 500); //applet size
	screen = 0; //start on screen 0, the Title Screen
	addMouseListener (this); //tracks mouse activity
	addKeyListener (this); //tracks keyboard activity

	Graphics g = getGraphics ();

	WaterFall = getImage (getDocumentBase (), "WaterfallHUD.jpg");
	g.drawImage (WaterFall, -100, -100, this);

	Title = getImage (getDocumentBase (), "Title.jpg");
	g.drawImage (WaterFall, -100, -100, this);

	spritefront = getImage (getDocumentBase (), "sprite.png");
	g.drawImage (spritefront, -100, -100, this);

	sprite = spritefront; //default direction for character (looking down)

	spriteleft = getImage (getDocumentBase (), "spriteleft.png");
	g.drawImage (spriteleft, -100, -100, this);

	spriteright = getImage (getDocumentBase (), "spriteright.png");
	g.drawImage (spriteright, -100, -100, this);

	spriteback = getImage (getDocumentBase (), "spriteback.png");
	g.drawImage (spriteback, -100, -100, this);

	Housefront = getImage (getDocumentBase (), "FronthousHUD.jpg");
	g.drawImage (Housefront, -100, -100, this);

	introtran = getImage (getDocumentBase (), "WaterfallHUDTransition.jpg");
	g.drawImage (introtran, -100, -100, this);

	housesign = getImage (getDocumentBase (), "Housesign.png");
	g.drawImage (housesign, -100, -100, this);

	insidehouse = getImage (getDocumentBase (), "InsidehouseHUD.jpg");
	g.drawImage (insidehouse, -100, -100, this);
    }


    public void mouseClicked (MouseEvent e)
    {
    }


    public void mouseEntered (MouseEvent e)
    {
    }


    public void mouseExited (MouseEvent e)
    {
    }


    public void mouseReleased (MouseEvent e)
    {
    }


    //Code which listens for mouse click activity
    public void mousePressed (MouseEvent e)
    {

	Graphics g = getGraphics ();

	//gets the x and y co-ordinates of the mouse pointer
	int x = e.getX ();
	int y = e.getY ();

	//Title Screen
	if (screen == 0)
	{
	    // Play button
	    if (x > 306 && x < 500 && y > 340 && y < 415)
		screen = 1;
	}

	//Waterfall DPAD + Exit Button
	if (screen == 1)
	{
	    //Up Button
	    if (x > 662 && x < 697 && y > 21 && y < 66)
	    {
		if (y1 - 1 < 0)
		{
		}

		else if (validwaterfall [y1 - 1] [x1] == 1)
		    y1--;

		sprite = spriteback;
	    }

	    //Down Button
	    else if (x > 662 && x < 697 && y > 101 && y < 146)
	    {
		if (validwaterfall [y1 + 1] [x1] == 1)
		    y1++;

		sprite = spritefront;
	    }

	    //Left Button
	    else if (x > 617 && x < 661 && y > 65 && y < 101)
	    {
		if (x1 - 1 < 0)
		{
		}

		else if (validwaterfall [y1] [x1 - 1] == 1)
		    x1--;

		sprite = spriteleft;
	    }

	    //Right Button
	    else if (x > 697 && x < 741 && y > 65 && y < 101)
	    {
		if (validwaterfall [y1] [x1 + 1] == 1)
		    x1++;

		sprite = spriteright;
	    }

	    //Exit button
	    if (x > 630 && x < 735 && y > 384 && y < 481)

		//Pops up a window for Yes or No exit option
		{
		    int exit = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit?\n\nAll of your progress will be deleted!\n", "Warning!", JOptionPane.YES_NO_OPTION);

		    if (exit == 0)
		    {
			x1 = 0;
			y1 = 0;
			screen = 0;
			sprite = spritefront;
		    }
		}
	}

	//House screen DPAD + Exit Button
	else if (screen == 3)
	{
	    if (x > 662 && x < 697 && y > 21 && y < 66) //Up Button
	    {
		if (y1 - 1 < 0)
		{
		}

		else if (validhousefront [y1 - 1] [x1] == 1)
		    y1--;

		sprite = spriteback;
	    }

	    else if (x > 662 && x < 697 && y > 101 && y < 146) //Down Button
	    {
		if (y1 + 1 > 10)
		{
		}

		else if (validhousefront [y1 + 1] [x1] == 1)
		    y1++;

		sprite = spritefront;
	    }

	    else if (x > 617 && x < 661 && y > 65 && y < 101) //Left Button
	    {
		if (x1 - 1 < 0)
		{
		}

		else if (validhousefront [y1] [x1 - 1] == 1)
		    x1--;

		sprite = spriteleft;
	    }

	    else if (x > 697 && x < 741 && y > 65 && y < 101) //Right Button
	    {
		if (x1 + 1 > 16)
		{
		}

		else if (validhousefront [y1] [x1 + 1] == 1)
		    x1++;

		sprite = spriteright;
	    }

	    if (x > 630 && x < 735 && y > 384 && y < 481)  //Exit button

		//Pops up a window for Yes or No exit option

		{
		    int exit = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit?\n\nAll of your progress will be deleted!\n", "Warning!", JOptionPane.YES_NO_OPTION);

		    if (exit == 0)
		    {
			x1 = 0;
			y1 = 0;
			screen = 0;
			sprite = spritefront;
		    }
		}
	}

	else if (screen == 4)
	{
	    if (x > 662 && x < 697 && y > 21 && y < 66) //Up Button
	    {
		if (y1 - 1 < 0)
		{
		}

		else if (validhouseinside [y1 - 1] [x1] == 1)
		    y1--;

		sprite = spriteback;
	    }

	    else if (x > 662 && x < 697 && y > 101 && y < 146) //Down Button
	    {
		if (y1 + 1 > 11)
		{
		}

		else if (validhouseinside [y1 + 1] [x1] == 1)
		    y1++;

		sprite = spritefront;
	    }

	    else if (x > 617 && x < 661 && y > 65 && y < 101) //Left Button
	    {
		if (x1 - 1 < 0)
		{
		}

		else if (validhouseinside [y1] [x1 - 1] == 1)
		    x1--;

		sprite = spriteleft;
	    }

	    else if (x > 697 && x < 741 && y > 65 && y < 101) //Right Button
	    {
		if (x1 + 1 > 16)
		{
		}

		else if (validhouseinside [y1] [x1 + 1] == 1)
		    x1++;

		sprite = spriteright;
	    }

	    if (x > 630 && x < 735 && y > 384 && y < 481)  //Exit button

		//Pops up a window for Yes or No exit option

		{
		    int exit = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit?\n\nAll of your progress will be deleted!\n", "Warning!", JOptionPane.YES_NO_OPTION);

		    if (exit == 0)
		    {
			x1 = 0;
			y1 = 0;
			screen = 0;
			sprite = spritefront;
		    }
		}
	}

	repaint ();
	//end screen 1
    }


    //Keyboard input

    public void keyPressed (KeyEvent e)
    {
	if (screen == 1)
	{
	    if (e.getKeyCode () == e.VK_UP) //up
	    {
		if (y1 - 1 < 0)
		{
		}

		else if (validwaterfall [y1 - 1] [x1] == 1)
		    y1--;

		sprite = spriteback;
	    }

	    if (e.getKeyCode () == e.VK_DOWN) //down
	    {
		if (validwaterfall [y1 + 1] [x1] == 1)
		    y1++;

		sprite = spritefront;
	    }

	    if (e.getKeyCode () == e.VK_LEFT) //left
	    {
		if (x1 - 1 < 0)
		{
		}

		else if (validwaterfall [y1] [x1 - 1] == 1)
		    x1--;

		sprite = spriteleft;
	    }

	    if (e.getKeyCode () == e.VK_RIGHT) //right
	    {
		if (validwaterfall [y1] [x1 + 1] == 1)
		    x1++;

		sprite = spriteright;
	    }
	}

	else if (screen == 3)
	{
	    if (e.getKeyCode () == e.VK_UP) //up
	    {
		if (y1 - 1 < 0)
		{
		}

		else if (validhousefront [y1 - 1] [x1] == 1)
		    y1--;

		sprite = spriteback;
	    }

	    if (e.getKeyCode () == e.VK_DOWN) //down
	    {
		if (y1 + 1 > 10)
		{
		}

		else if (validhousefront [y1 + 1] [x1] == 1)
		    y1++;

		sprite = spritefront;
	    }

	    if (e.getKeyCode () == e.VK_LEFT) //left
	    {
		if (x1 - 1 < 0)
		{
		}

		else if (validhousefront [y1] [x1 - 1] == 1)
		    x1--;

		sprite = spriteleft;
	    }

	    if (e.getKeyCode () == e.VK_RIGHT) //right
	    {
		if (x1 + 1 > 16)
		{
		}

		else if (validhousefront [y1] [x1 + 1] == 1)
		    x1++;

		sprite = spriteright;
	    }
	}

	else if (screen == 4)
	{
	    if (e.getKeyCode () == e.VK_UP) //up
	    {
		if (y1 - 1 < 0)
		{
		}

		else if (validhouseinside [y1 - 1] [x1] == 1)
		    y1--;

		sprite = spriteback;
	    }

	    if (e.getKeyCode () == e.VK_DOWN) //down
	    {
		if (y1 + 1 > 11)
		{
		}

		else if (validhouseinside [y1 + 1] [x1] == 1)
		    y1++;

		sprite = spritefront;
	    }

	    if (e.getKeyCode () == e.VK_LEFT) //left
	    {
		if (x1 - 1 < 0)
		{
		}

		else if (validhouseinside [y1] [x1 - 1] == 1)
		    x1--;

		sprite = spriteleft;
	    }

	    if (e.getKeyCode () == e.VK_RIGHT) //right
	    {
		if (x1 + 1 > 16)
		{
		}

		else if (validhouseinside [y1] [x1 + 1] == 1)
		    x1++;

		sprite = spriteright;
	    }
	}
	repaint ();
	//end screen 1

    }


    public void keyReleased (KeyEvent e)
    {

    }


    public void keyTyped (KeyEvent e)
    {

    }


    public void update (Graphics g)
    { //Overide the regular update method so it doesn't clear the screen before it draws (Gets rid of annoying white flicker)
	paint (g);
    }


    public void paint (Graphics g)
    {
	if (screen == 0) //Start
	{
	    g.drawImage (Title, 0, 0, this);
	}

	if (screen == 1) //Start
	{
	    g.drawImage (WaterFall, 0, 0, this);
	    g.drawImage (sprite, (x1 * 32 + 20), (y1 * 32 + 44), this);
	    //g.drawString ("" + validhousefront [y1] [x1], 680, 227);
	    //g.drawString ("" + x1, 680, 327);
	    //g.drawString ("" + y1, 680, 427);

	    if (y1 == 6 && x1 == 8 && sprite == spriteright) // animates the character and transitions into the second map
	    {
		JOptionPane.showMessageDialog (null, "You find a mysterious old bottle with a letter inside.\nIt is stuck in the rocks near the edge of the waterfall.\n\n"
			+ "You would be asked if you would like to pick it up,\nbut you don't have a choice.\n"
			+ "So you take the risk at the edge of the waterfall\nand try to grab the bottle.", "Discovery", JOptionPane.INFORMATION_MESSAGE);
		x1++;
		sprite = spritefront;
		screen = 2;   //switches to the screen with animation
	    }
	}

	if (screen == 2)
	{
	    g.drawImage (introtran, 0, 0, this);
	    try
	    {
		Thread.sleep (2000);
	    }
	    catch (InterruptedException e)
	    {
	    }

	    x1 += 7;
	    y1 += 4;
	    sprite = spriteleft;
	    screen = 3;
	}

	if (screen == 3)
	{
	    g.drawImage (Housefront, 0, 0, this);
	    g.drawImage (sprite, (x1 * 32 + 20), (y1 * 32 + 44), this);

	    g.setFont (new Font ("Arial", Font.PLAIN, 64));
	    g.setColor (Color.white);
	    //g.drawString ("" + validhousefront [y1] [x1], 680, 227);
	    //g.drawString ("" + x1, 680, 327);
	    //g.drawString ("" + y1, 680, 427);

	    if (x1 == 6 && y1 == 9 && sprite == spriteback)
	    {
		g.drawImage (housesign, 0, 0, this);
	    }

	    if (x1 == 7 && y1 == 6 && sprite == spriteback)
	    {
		int houseenter = JOptionPane.showConfirmDialog (null, "Are you sure you want to enter the house?", "Confirm", JOptionPane.YES_NO_OPTION);
		if (houseenter == 0)
		{
		    screen = 4;
		    y1 += 3;
		}

		else
		{
		    sprite = spritefront;
		}
	    }
	}

	if (screen == 4)
	{
	    g.drawImage (insidehouse, 0, 0, this);
	    g.drawImage (sprite, (x1 * 32 + 20), (y1 * 32 + 44), this);
	    g.setFont (new Font ("Arial", Font.PLAIN, 64));
	    g.setColor (Color.white);
	    //g.drawString ("" + validhousefront [y1] [x1], 680, 227);
	    //g.drawString ("" + x1, 680, 327);
	    //g.drawString ("" + y1, 680, 427);

	    if (x1 == 5 && y1 == 2 && sprite == spriteback)
	    {
		screen = 2;
	    }

	    if (x1 == 7 && y1 == 10 && sprite == spritefront)
	    {
		int houseexit = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit the house?", "Confirm", JOptionPane.YES_NO_OPTION);
		if (houseexit == 0)
		{
		    screen = 3;
		    y1 -= 4;
		     repaint ();
		    sprite = spritefront;
		}

		else
		{
		    sprite = spriteback;
		}
	    }
	}
	
	public void arrows (int x, int y, int valid [] [])
	{
	    if (x > 662 && x < 697 && y > 21 && y < 66)
	    {
		if (y1 - 1 < 0)
		{
		}

		else if (valid [y1 - 1] [x1] == 1)
		    y1--;

		sprite = spriteback;
	    }

	    //Down Button
	    else if (x > 662 && x < 697 && y > 101 && y < 146)
	    {
		if (valid [y1 + 1] [x1] == 1)
		    y1++;

		sprite = spritefront;
	    }

	    //Left Button
	    else if (x > 617 && x < 661 && y > 65 && y < 101)
	    {
		if (x1 - 1 < 0)
		{
		}

		else if (valid [y1] [x1 - 1] == 1)
		    x1--;

		sprite = spriteleft;
	    }

	    //Right Button
	    else if (x > 697 && x < 741 && y > 65 && y < 101)
	    {
		if (valid [y1] [x1 + 1] == 1)
		    x1++;

		sprite = spriteright;
	    }
	}

       
	
	showStatus ("Running: Sahir, the Adventurist - Zain Zulfiqar");
    }
}





