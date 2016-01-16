//Name: Zain Zulfiqar
//Date: March 22nd, 2013 - April 8th 2013
//Purpose: Text-Based RPG Assignment

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.text.*;

public class rpgtexttest extends Applet implements MouseListener, KeyListener, MouseMotionListener
{
    int screen; //Screen switch variable

    /*
     0 = Title Screen
     1 = Waterfall Intro
     2 = Waterfall Cutscene
     3 = House Outside
     4 = House Inside
     5 = House Backyard
     6 = Room 1
     7 = Room 2
     8 = Room 3
     9 = Room 4
     10 = Room 5
     11 = Upstairs
     12 = Snake Battle Screem
     13 = Snake - Losing Screen
     14 = Key - Losing Screen
     15 = Winning Screen
    */

    int condition = 0; //event switch variable throughout the plot
    int backyardswitch = 1; //changes the backyard image depending on the plot, used with a switch
    int insideswitch = 1; //changes the house inside image depending on the plot, used with a switch
    int points = 0; //holdd the amount of points the player has from the challenges
    int basehealth = 60; //Variable which holds the amount of health points the player has
    int health = 60; //Variable which holds the current amount of health points the player has
    int snakebase = 60; //Variable which holds the amount of health points the snake has during battle
    int snakehealth = 60; //Variable which holds the current amount of health points the snake has during battle
    int snakeattack;

    //All image names defined here to be used later in the code
    Image Title, WaterFall, introtran, Housefront, sprite, spriteleft, spriteright, spriteback, housesign, insidehouse, spritefront, backyard1, locksign,
	backyard2, backyard, backyardsnake, backyardescape, inside, insidehouseswitch, insidehouseswitch2, insidehouseswitch3, room1, room2, room3, room4,
	room5, upstairs, RollUp, RollDown, RollLeft, RollRight, rollover, RollEmpty, mathstart, mathend, mathsign, tfsign, tfstart, tfend, mcsign, mcstart,
	mcend, cssign, csstart, csend, ussign, usstart, usend, battle, keygained, key, gameoverkey, gameoversnake, gamewin;

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

    //Backyard restrictions
    int[] [] validbackyard = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
	    {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
	    {0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
	    {0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
	    {0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0},
	    {0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
	    {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
	    {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
	    {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}};

    //Challenge Room 1 restrictions
    int[] [] validroom1 = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //Challenge Room 2 restrictions
    int[] [] validroom2 = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //Challenge Room 3 restrictions
    int[] [] validroom3 = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //Challenge Room 4 restrictions
    int[] [] validroom4 = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //Challenge Room 5 restrictions
    int[] [] validroom5 = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //Key Room restrictions
    int[] [] validupstairs = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    //Java method which collects other files to be used in the program
    public void init ()
    {
	resize (800, 500); //applet size
	screen = 0; //start on screen 0, the Title Screen
	addMouseListener (this); //tracks mouse activity
	addKeyListener (this); //tracks keyboard activity
	addMouseMotionListener (this); //tracks cursor motion/position

	Graphics g = getGraphics ();

	//Bellow are all of the graphics imported from the root path of the program
	WaterFall = getImage (getDocumentBase (), "WaterfallHUD.jpg");

	Title = getImage (getDocumentBase (), "Title.jpg");

	spritefront = getImage (getDocumentBase (), "sprite.png");
	sprite = spritefront; //default direction for character (looking down)
	spriteleft = getImage (getDocumentBase (), "spriteleft.png");
	spriteright = getImage (getDocumentBase (), "spriteright.png");
	spriteback = getImage (getDocumentBase (), "spriteback.png");

	Housefront = getImage (getDocumentBase (), "FronthousHUD.jpg");
	introtran = getImage (getDocumentBase (), "WaterfallHUDTransition.jpg");
	g.drawImage (introtran, 0, 0, this);
	housesign = getImage (getDocumentBase (), "Housesign.png");

	insidehouseswitch = getImage (getDocumentBase (), "InsidehouseHUD.jpg");
	inside = insidehouseswitch;
	insidehouseswitch2 = getImage (getDocumentBase (), "Insidehouse2aHUD.jpg");
	insidehouseswitch3 = getImage (getDocumentBase (), "Insidehouse2bHUD.jpg");

	backyard1 = getImage (getDocumentBase (), "BackyardHUD1.jpg");
	backyard = backyard1;
	backyard2 = getImage (getDocumentBase (), "BackyardHUD2.jpg");
	backyardsnake = getImage (getDocumentBase (), "BackyardHUD3.jpg");
	backyardescape = getImage (getDocumentBase (), "BackyardHUD4.jpg");
	locksign = getImage (getDocumentBase (), "Locksign.png");
	g.drawImage (locksign, 0, 0, this);

	room1 = getImage (getDocumentBase (), "Room1.jpg");
	mathstart = getImage (getDocumentBase (), "MathStart.png");
	mathend = getImage (getDocumentBase (), "MathEnd.png");
	mathsign = mathstart;
	g.drawImage (mathsign, 0, 0, this);

	room2 = getImage (getDocumentBase (), "Room2.jpg");
	tfstart = getImage (getDocumentBase (), "TFStart.png");
	tfend = getImage (getDocumentBase (), "TFEnd.png");
	tfsign = tfstart;
	g.drawImage (tfsign, 0, 0, this);

	room3 = getImage (getDocumentBase (), "Room3.jpg");
	mcstart = getImage (getDocumentBase (), "MCStart.png");
	mcend = getImage (getDocumentBase (), "MCEnd.png");
	mcsign = mcstart;
	g.drawImage (mcsign, 0, 0, this);

	room4 = getImage (getDocumentBase (), "Room4.jpg");
	csstart = getImage (getDocumentBase (), "CSStart.png");
	csend = getImage (getDocumentBase (), "CSEnd.png");
	cssign = csstart;
	g.drawImage (cssign, 0, 0, this);

	room5 = getImage (getDocumentBase (), "Room5.jpg");
	usstart = getImage (getDocumentBase (), "USStart.png");
	usend = getImage (getDocumentBase (), "USEnd.png");
	ussign = usstart;
	g.drawImage (ussign, 0, 0, this);

	upstairs = getImage (getDocumentBase (), "Upstairs.jpg");
	keygained = getImage (getDocumentBase (), "keygained.png");

	RollUp = getImage (getDocumentBase (), "RolloverUp.png");
	RollEmpty = getImage (getDocumentBase (), "RolloverEmpty.png");
	rollover = RollEmpty;
	RollDown = getImage (getDocumentBase (), "RolloverDown.png");
	RollLeft = getImage (getDocumentBase (), "RolloverLeft.png");
	RollRight = getImage (getDocumentBase (), "RolloverRight.png");

	battle = getImage (getDocumentBase (), "Battle.jpg");

	gameoversnake = getImage (getDocumentBase (), "GameoverSnake.png");
	gameoverkey = getImage (getDocumentBase (), "GameoverKey.png");

	gamewin = getImage (getDocumentBase (), "Gamewin.png");
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


    //Method which listens for mouse click activity
    public void mousePressed (MouseEvent e)
    {

	Graphics g = getGraphics ();

	//gets the x and y co-ordinates of the cursor
	int x = e.getX ();
	int y = e.getY ();

	//Title Screen
	if (screen == 0)
	{
	    // Play button
	    if (x > 306 && x < 500 && y > 340 && y < 415)
		screen = 1;
	}

	//DPAD + Exit Button cursor co-ordinates screen 1 - 11
	if (screen == 1)
	    arrows (x, y, validwaterfall);

	else if (screen == 3)
	    arrows (x, y, validhousefront);

	else if (screen == 4)
	    arrows (x, y, validhouseinside);

	else if (screen == 5)
	    arrows (x, y, validbackyard);

	else if (screen == 6)
	    arrows (x, y, validroom1);

	else if (screen == 7)
	    arrows (x, y, validroom2);

	else if (screen == 8)
	    arrows (x, y, validroom3);

	else if (screen == 9)
	    arrows (x, y, validroom4);

	else if (screen == 10)
	    arrows (x, y, validroom5);

	else if (screen == 11)
	    arrows (x, y, validupstairs);

	//Battle screen button options based on mouse input
	else if (screen == 12)
	{

	    try
	    {
		if (x > 630 && x < 770 && y > 324 && y < 368)
		{
		    String[] possibleValues = {"Punch", "Kick", "Slash", "Bite", "Hammer"};
		    String selectedValue = (String) JOptionPane.showInputDialog (null, "The snake is vicious. Quickly select an attack:",
			    "Choose One", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues [0]);

		    if (selectedValue.equals ("Punch"))
			attack (selectedValue);

		    else if (selectedValue.equals ("Kick"))
			attack (selectedValue);

		    else if (selectedValue.equals ("Slash"))
			attack (selectedValue);

		    else if (selectedValue.equals ("Bite"))
			attack (selectedValue);

		    else if (selectedValue.equals ("Hammer"))
			attack (selectedValue);

		    if (snakehealth > attack (selectedValue))
		    {
			snakehealth -= attack (selectedValue);
			snakeattack = (int) (Math.random () * 8) + 1;
			health -= snakeattack;
			JOptionPane.showMessageDialog (null, "You attack and the snake loses: " + (attack (selectedValue) + 1)
				+ " HP!\n\nThe snake attacks and you lose: " + snakeattack + " HP!", "Hurt", JOptionPane.INFORMATION_MESSAGE);
		    }

		    else if (snakehealth <= attack (selectedValue) || snakehealth <= 0)
		    {
			snakehealth = 0;
			JOptionPane.showMessageDialog (null, "Congratulations! You defeated the snake!", "Win", JOptionPane.INFORMATION_MESSAGE);
			condition = 4;
			validbackyard [4] [10] = 1;
			validbackyard [4] [9] = 1;
			backyardswitch = 2;
			x1 = 10;
			y1 = 4;
			screen = 5;
		    }

		    if (health <= 0)
		    {
			health = 0;
			screen = 13;
		    }
		}
	    }

	    catch (java.lang.NullPointerException m)
	    {
	    }
	}

	//Play again option if player loses with snake battle/key
	//all stats are reset for new game
	if (screen == 13 || screen == 14)
	{
	    if (x > 311 && x < 470 && y > 331 && y < 400)
	    {
		screen = 1;
		condition = 0;
		backyardswitch = 1;
		insideswitch = 1;
		points = 0;
		basehealth = 60;
		health = 60;
		snakebase = 60;
		snakehealth = 60;
		x1 = mainposx;
		y1 = mainposy;
	    }

	    if (x > 536 && x < 695 && y > 331 && y < 400)
	    {
		screen = 0;
		condition = 0;
		backyardswitch = 1;
		insideswitch = 1;
		points = 0;
		basehealth = 60;
		health = 60;
		snakebase = 60;
		snakehealth = 60;
		y1 = mainposy;
		x1 = mainposx;
	    }
	}

	//Play again option if player wins the game
	//all stats are reset for new game
	if (screen == 15)
	{
	    if (x > 311 && x < 470 && y > 375 && y < 444)
	    {
		screen = 1;
		condition = 0;
		backyardswitch = 1;
		insideswitch = 1;
		points = 0;
		basehealth = 50;
		health = 50;
		snakebase = 60;
		snakehealth = 60;
		x1 = mainposx;
		y1 = mainposy;
	    }

	    if (x > 536 && x < 695 && y > 375 && y < 444)
	    {
		screen = 0;
		condition = 0;
		backyardswitch = 1;
		insideswitch = 1;
		points = 0;
		basehealth = 50;
		health = 50;
		snakebase = 60;
		snakehealth = 60;
		y1 = mainposy;
		x1 = mainposx;
	    }
	}

	repaint ();
    }


    public void mouseDragged (MouseEvent e)
    {

    }


    //Method which listens for mouse hover/move activity
    public void mouseMoved (MouseEvent e)
    {
	//get the x and y co-ordinates of the mouse
	int x = e.getX ();
	int y = e.getY ();

	Graphics g = getGraphics ();

	if (screen != 0)
	{
	    //Down DPAD rollover
	    if (x > 662 && x < 697 && y > 101 && y < 146)
		rollover = RollDown;

	    //Up DPAD rollover
	    else if (x > 662 && x < 697 && y > 21 && y < 66)
		rollover = RollUp;

	    //Right DPAD rollover
	    else if (x > 697 && x < 741 && y > 65 && y < 101)
		rollover = RollRight;

	    //Left DPAD rollover
	    else if (x > 617 && x < 661 && y > 65 && y < 101)
		rollover = RollLeft;

	    //Empty rollover if outside DPAD
	    else
		rollover = RollEmpty;
	}

	//Only allow repaint(); bellow if you would like the "button rollover" effect instead of the "button pressed" effect
	//repaint();
    }


    //Method for keyboard input
    public void keyPressed (KeyEvent e)
    {

	//The keyboard listener is ONLY called for the screens with the DPAD, where as the mouse listener is called other times for extra options.
	if (screen == 1)
	    keyboard (e, validwaterfall);

	else if (screen == 3)
	    keyboard (e, validhousefront);

	else if (screen == 4)
	    keyboard (e, validhouseinside);

	else if (screen == 5)
	    keyboard (e, validbackyard);

	else if (screen == 6)
	    keyboard (e, validroom1);

	else if (screen == 7)
	    keyboard (e, validroom2);

	else if (screen == 8)
	    keyboard (e, validroom3);

	else if (screen == 9)
	    keyboard (e, validroom4);

	else if (screen == 10)
	    keyboard (e, validroom5);

	else if (screen == 11)
	    keyboard (e, validupstairs);

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
    {
	//Overide the regular update method so it doesn't clear the screen before it draws (Gets rid of annoying white flicker)
	paint (g);
    }


    //Java method which draws the graphics on the screen
    public void paint (Graphics g)
    {

	//Start
	if (screen == 0)
	{
	    g.drawImage (Title, 0, 0, this);
	}

	//Waterfall
	if (screen == 1)
	{
	    gamescreen (WaterFall);
	    if (y1 == 6 && x1 == 8 && sprite == spriteright) // animates the character and transitions into the second map
	    {
		sprite = spritefront;
		JOptionPane.showMessageDialog (null, "You find a mysterious old bottle with a letter inside.\nIt is stuck in the rocks near the edge of the waterfall.\n\n"
			+ "You would be asked if you would like to pick it up,\nbut you don't have a choice.\n"
			+ "So you take the risk at the edge of the waterfall\nand try to grab the bottle.", "Discovery", JOptionPane.INFORMATION_MESSAGE);
		x1++;
		sprite = spritefront;
		screen = 2;
	    }
	}

	//Waterfall animation
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

	//House Outside
	if (screen == 3)
	{
	    gamescreen (Housefront);

	    //House signboard pop-up
	    if (x1 == 6 && y1 == 9 && sprite == spriteback)
		g.drawImage (housesign, 0, 0, this);

	    //House enter
	    if (x1 == 7 && y1 == 6 && sprite == spriteback)
	    {
		sprite = spriteright;
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

	//House inside
	if (screen == 4)
	{
	    gamescreen (inside);

	    //If player knows the door outside is locked, the inside will change
	    if (condition == 1)
	    {
		insideswitch = 2;
		changein (insideswitch);
		condition = 2;
	    }

	    //If player does not know the door outside is locked, he or she has to go straight out and not interact with anything
	    if ((condition == 0 && x1 == 2 && y1 == 2 && sprite == spriteback) ||
		    (condition == 0 && x1 == 6 && y1 == 2 && sprite == spriteback) || (condition == 0 && x1 == 8 && y1 == 2 && sprite == spriteback) ||
		    (condition == 0 && x1 == 11 && y1 == 2 && sprite == spriteback) || (condition == 0 && x1 == 14 && y1 == 2 && sprite == spriteback))
	    {
		sprite = spritefront;
		JOptionPane.showMessageDialog (null, "\"I should head straight oustide to\nthe staircase to avoid any problems.\"", "No", JOptionPane.INFORMATION_MESSAGE);
	    }

	    if (condition == 0 && x1 == 15 && y1 == 4 && sprite == spriteright)
	    {
		sprite = spriteleft;
		JOptionPane.showMessageDialog (null, "\"I should head straight oustide to\nthe staircase to avoid any problems.\"", "No", JOptionPane.INFORMATION_MESSAGE);
		x1--;
	    }

	    //Inside exit to outside
	    if (x1 == 7 && y1 == 10 && sprite == spritefront)
	    {
		sprite = spriteleft;
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

	    //Player can only go to the backyard if they completed challenges or if its the do not know the door is locked
	    while (x1 == 5 && y1 == 2 && sprite == spriteback && (condition == 0 || condition == 3))
	    {
		screen = 5;
		y1 += 7;
		x1 += 1;
		repaint ();
		sprite = spriteback;
	    }

	    //Player must complete all challenges before going to the backyard.
	    while (x1 == 5 && y1 == 2 && sprite == spriteback && condition == 2)
	    {
		JOptionPane.showMessageDialog (null, "\"I should obtain the key and then go outside.\"", "Get the key first", JOptionPane.INFORMATION_MESSAGE);
		sprite = spritefront;
	    }

	    //After the player knows the backyard door outside is locked, they can interact and have the option ot eat food.
	    if (insideswitch == 2 && x1 == 2 && y1 == 8 && sprite == spritefront || insideswitch == 2 && x1 == 3 && y1 == 9 && sprite == spriteleft ||
		    insideswitch == 2 && x1 == 2 && y1 == 10 && sprite == spriteback)
	    {
		sprite = spriteright;
		int eatfood = JOptionPane.showConfirmDialog (null, "This looks something like food? It may be nutritious.\n\n"
			+ "Would you like to eat it?", "Free Food", JOptionPane.YES_NO_OPTION);
		if (eatfood == 0)
		{
		    health -= 10;
		    insideswitch = 3;
		    changein (insideswitch);
		    JOptionPane.showMessageDialog (null, "The food is poisonous!\n\nYou lose 10 health points.\n\nYou should know better! Do not eat\n"
			    + "anything you find randomly, especially\nin a haunted house!", "Dangerous", JOptionPane.INFORMATION_MESSAGE);
		}

		else
		    sprite = spriteright;
	    }

	    //Room 1 door
	    if (x1 == 2 && y1 == 2 && sprite == spriteback)
	    {
		screen = 6;
		y1 += 7;
		x1 += 6;
		repaint ();
		sprite = spriteback;
	    }

	    //Room 2 door
	    if (x1 == 6 && y1 == 2 && sprite == spriteback)
	    {
		screen = 7;
		y1 += 7;
		x1 += 2;
		repaint ();
		sprite = spriteback;
	    }

	    //Room 3 door
	    if (x1 == 8 && y1 == 2 && sprite == spriteback)
	    {
		screen = 8;
		y1 += 7;
		repaint ();
		sprite = spriteback;
	    }

	    //Room 4 door
	    if (x1 == 11 && y1 == 2 && sprite == spriteback)
	    {
		screen = 9;
		y1 += 7;
		x1 -= 3;
		repaint ();
		sprite = spriteback;
	    }

	    //Room 5 door
	    if (x1 == 14 && y1 == 2 && sprite == spriteback)
	    {
		screen = 10;
		y1 += 7;
		x1 -= 6;
		repaint ();
		sprite = spriteback;
	    }

	    //Upstairs
	    if (x1 == 15 && y1 == 4 && sprite == spriteright)
	    {
		screen = 11;
		y1 += 3;
		x1 -= 11;
		repaint ();
		sprite = spriteright;
	    }

	    //Room 1 signboard pop-up
	    if (x1 == 3 && y1 == 3 && sprite == spriteback && insideswitch == 2)
		g.drawImage (mathsign, 0, 0, this);

	    //Room 2 signboard pop-up
	    if (x1 == 7 && y1 == 3 && sprite == spriteback && insideswitch == 2)
		g.drawImage (tfsign, 0, 0, this);

	    //Room 3 signboard pop-up
	    if (x1 == 9 && y1 == 3 && sprite == spriteback && insideswitch == 2)
		g.drawImage (mcsign, 0, 0, this);

	    //Room 4 signboard pop-up
	    if (x1 == 12 && y1 == 3 && sprite == spriteback && insideswitch == 2)
		g.drawImage (cssign, 0, 0, this);

	    //Room 5 signboard pop-up
	    if (x1 == 15 && y1 == 3 && sprite == spriteback && insideswitch == 2)
		g.drawImage (ussign, 0, 0, this);
	}

	//Backyard
	if (screen == 5)
	{
	    gamescreen (backyard);

	    //Player finds out door is locked
	    if (x1 == 10 && y1 == 4 && sprite == spriteback && condition == 0)
	    {
		backyardswitch = 2;
		changeback (backyardswitch);
		g.drawImage (locksign, 0, 0, this);
		condition = 1;
	    }

	    //Door to go back inside
	    if (x1 == 6 && y1 == 9 && sprite == spritefront)
	    {
		screen = 4;
		y1 -= 7;
		x1 -= 1;
		sprite = spritefront;

		if (condition == 1)
		{
		    insideswitch = 2;
		    changein (insideswitch);
		    condition = 2;
		}
		repaint ();
	    }

	    //After all challenges are completed, player comes outside to a snake
	    if ((((x1 == 10 || x1 == 9) && y1 == 5 && sprite == spriteback) || (x1 == 11 && y1 == 4 && sprite == spriteleft)
			|| (x1 == 8 && y1 == 4 && sprite == spriteright)) && condition == 3)
	    {
		sprite = spritefront;

		int start = JOptionPane.showConfirmDialog (null, "You will enter in a battle with the snake.\n"
			+ "Use the options provided and select the attack you desire.\n\n"
			+ "Would you like to continue?", "Battle", JOptionPane.YES_NO_OPTION);

		if (start == 0)
		    screen = 12;

		else
		{
		}
	    }

	    //After snake is defeated, player tries to escape
	    if (x1 == 10 && y1 == 4 && sprite == spriteback && condition == 4)
	    {
		sprite = spritefront;

		int lock = JOptionPane.showConfirmDialog (null, "You instert the key and twist it. However, the lock does\nnot open!"
			+ " The key is bent.\n\nWould you like to try to open the lock once more?\n", "Exit?", JOptionPane.YES_NO_OPTION);

		if (lock == 0)
		    screen = 14;
	    }

	    //Secret exit
	    if (((x1 == 2 && y1 == 1 && sprite == spritefront) || (x1 == 2 && y1 == 3 && sprite == spriteback) || (x1 == 1 && y1 == 2 && sprite == spriteright) || (x1 == 3 && y1 == 2 && sprite == spriteleft)) && condition == 4)
	    {
		condition = 5;

		if (sprite == spritefront)
		    sprite = spriteback;

		else if (sprite == spriteback)
		    sprite = spritefront;

		else if (sprite == spriteleft)
		    sprite = spriteright;

		else if (sprite == spriteright)
		    sprite = spriteleft;

		int digup = JOptionPane.showConfirmDialog (null, "It looks like you can dig up this area.\nWho knows, maybe there is some treasure!\n\n"
			+ "Would you like to try to dig this space up?\n", "Dig?", JOptionPane.YES_NO_OPTION);

		if (digup == 0)
		{
		    backyardswitch = 4;
		    changeback (backyardswitch);
		}
	    }

	    //Switch to game win
	    if (((x1 == 2 && y1 == 1 && sprite == spritefront) || (x1 == 2 && y1 == 3 && sprite == spriteback) || (x1 == 1 && y1 == 2 && sprite == spriteright) || (x1 == 3 && y1 == 2 && sprite == spriteleft)) && condition == 5)
	    {
		screen = 15;
	    }
	}

	//Room 1
	if (screen == 6)
	{
	    gamescreen (room1);

	    //Exit
	    if (x1 == 8 && y1 == 9 && sprite == spritefront)
	    {
		screen = 4;
		y1 -= 6;
		x1 -= 6;
		repaint ();
	    }

	    //Challenge
	    if (x1 == 7 && y1 == 6 && sprite == spriteback)
	    {
		sprite = spritefront;

		if (mathsign == mathstart)
		{
		    int start = JOptionPane.showConfirmDialog (null, "You will have to answer 5 math questions. You should\n"
			    + "be familiar with the questions as they are based off of\nMYP 9 and 10 Mathematics.\n\n"
			    + "You must get all 5 questions right to gain a clue for the key in the chest.\n\n"
			    + "Would you like to continue?", "Math Challenges", JOptionPane.YES_NO_OPTION);

		    if (start == 0)
			room1math ();

		    else
		    {
		    }
		}

		else if (mathsign == mathend)
		    g.drawImage (mathsign, 0, 0, this);
	    }
	}

	//Room 2
	if (screen == 7)
	{
	    gamescreen (room2);

	    //exit
	    if (x1 == 8 && y1 == 9 && sprite == spritefront)
	    {
		screen = 4;
		y1 -= 6;
		x1 -= 2;
		repaint ();
	    }

	    //challenge
	    if (x1 == 8 && y1 == 6 && sprite == spriteback)
	    {
		sprite = spritefront;

		if (tfsign == tfstart)
		{
		    int start = JOptionPane.showConfirmDialog (null, "You will be givin 5 true or false questions. You should\n"
			    + "be familiar with the questions as they are based on\n Turner Fenton Secondary School.\n\n"
			    + "You must get at least 4/5 questions right to gain a clue for\nthe key in the chest.\n\n"
			    + "Would you like to continue?", "True or False", JOptionPane.YES_NO_OPTION);

		    if (start == 0)
			room2tf ();

		    else
		    {
		    }
		}

		else if (tfsign == tfend)
		    g.drawImage (tfsign, 0, 0, this);
	    }
	}

	//Room 3
	if (screen == 8)
	{
	    gamescreen (room3);

	    //exit
	    if (x1 == 8 && y1 == 9 && sprite == spritefront)
	    {
		screen = 4;
		y1 -= 6;
		repaint ();
	    }

	    //challenge
	    if (x1 == 8 && y1 == 5 && sprite == spriteback)
	    {
		sprite = spritefront;

		if (mcsign == mcstart)
		{
		    int start = JOptionPane.showConfirmDialog (null, "You will be givin 5 multiple choice questions. These questions\n"
			    + "are based on common sense for safety.\nSelect the best answer for each scenario.\n\n"
			    + "You must get at least 4/5 questions right to gain a clue for\nthe key in the chest.\n\n"
			    + "Would you like to continue?", "Multiple Choice", JOptionPane.YES_NO_OPTION);

		    if (start == 0)
			room3mc ();

		    else
		    {
		    }
		}

		else if (mcsign == mcend)
		    g.drawImage (mcsign, 0, 0, this);
	    }
	}

	//Room 4
	if (screen == 9)
	{
	    gamescreen (room4);

	    //exit
	    if (x1 == 8 && y1 == 9 && sprite == spritefront)
	    {
		screen = 4;
		y1 -= 6;
		x1 += 3;
		repaint ();
	    }

	    //challenge
	    if (x1 == 8 && y1 == 5 && sprite == spriteback)
	    {
		sprite = spritefront;

		if (cssign == csstart)
		{
		    int start = JOptionPane.showConfirmDialog (null, "You will be givin 5 computer science questions. These questions\n"
			    + "are based on Unit 1 and Unit 2 of what you learnt in Mrs. Gorski's class.\n\n"
			    + "You must get at least 3/5 questions right to gain a clue for\nthe key in the chest.\n\n"
			    + "Would you like to continue?", "Computer Science", JOptionPane.YES_NO_OPTION);

		    if (start == 0)
			room4cs ();

		    else
		    {
		    }
		}

		else if (cssign == csend)
		    g.drawImage (cssign, 0, 0, this);
	    }
	}

	//Room 5
	if (screen == 10)
	{
	    gamescreen (room5);

	    //exit
	    if (x1 == 8 && y1 == 9 && sprite == spritefront)
	    {
		screen = 4;
		y1 -= 6;
		x1 += 6;
		repaint ();
	    }

	    //challenge
	    if (x1 == 8 && y1 == 6 && sprite == spriteback)
	    {
		sprite = spritefront;

		if (ussign == usstart)
		{
		    int start = JOptionPane.showConfirmDialog (null, "You will be givin 5 riddles. The answer to the riddles is one word.\n"
			    + "You will be givin a series of letter and you are too unscramble\n"
			    + "the letters and try to form a word, the answer.\n"
			    + "There will be more letters then you need.\n\n"
			    + "You must get at least 4/5 riddles right to gain a clue for\nthe key in the chest.\n\n"
			    + "Would you like to continue?", "Computer Science", JOptionPane.YES_NO_OPTION);

		    if (start == 0)
			room5us ();

		    else
		    {
		    }
		}

		else if (ussign == usend)
		    g.drawImage (ussign, 0, 0, this);
	    }
	}

	//Upstairs
	if (screen == 11)
	{
	    gamescreen (upstairs);

	    //exit
	    if (x1 == 4 && y1 == 7 && sprite == spriteleft)
	    {
		screen = 4;
		y1 -= 3;
		x1 += 11;
		repaint ();
	    }

	    if (x1 == 8 && y1 == 6 && sprite == spriteback && key == keygained)
		g.drawImage (key, 0, 0, this);

	    //if challenges completed, key gained
	    else if (x1 == 8 && y1 == 6 && sprite == spriteback && points >= 20)
	    {
		sprite = spritefront;
		keyroom ();
	    }

	    else if (x1 == 8 && y1 == 6 && sprite == spriteback && points < 20)
	    {
		JOptionPane.showMessageDialog (null, "You have not completed all of the challenges successfully.\n"
			+ "Please gain at least 20/25 points and come back to get the key.", "Wrong", JOptionPane.INFORMATION_MESSAGE);
		sprite = spritefront;
	    }
	}

	//Battle
	if (screen == 12)
	{
	    g.drawImage (battle, 0, 0, this);
	    g.setFont (new Font ("Arial", Font.PLAIN, 38));
	    g.setColor (Color.white);
	    g.drawString ("" + health + "/" + basehealth, 165, 377);
	    g.drawString ("" + snakehealth + "/" + snakebase, 165, 432);
	    g.drawRect (275, 349, (5 * basehealth) + 1, 26);
	    g.drawRect (274, 348, (5 * basehealth) + 2, 27);
	    g.drawRect (275, 404, (5 * snakebase) + 1, 26);
	    g.drawRect (274, 403, (5 * snakebase) + 2, 27);

	    if (health >= ((basehealth / 2) + 1))
		g.setColor (Color.green);

	    else if (health <= (basehealth / 2) && health > 13)
		g.setColor (Color.yellow);

	    else if (health <= 13)
		g.setColor (Color.red);

	    g.fillRect (276, 350, 5 * health, 25);

	    if (snakehealth >= 31)
		g.setColor (Color.green);

	    else if (snakehealth <= 30 && snakehealth >= 16)
		g.setColor (Color.yellow);

	    else if (snakehealth <= 15)
		g.setColor (Color.red);

	    g.fillRect (276, 405, 5 * snakehealth, 25);
	}

	//Lose in battle
	if (screen == 13)
	{
	    g.drawImage (gameoversnake, 0, 0, this);
	}

	//Lose with key
	if (screen == 14)
	{
	    g.drawImage (gameoverkey, 0, 0, this);
	}

	//Win game
	if (screen == 15)
	{
	    g.drawImage (gamewin, 0, 0, this);
	}

	Date now = new Date ();
	DateFormat df = DateFormat.getDateInstance ();
	String s = df.format (now);

	showStatus ("Running: Sahir, the Adventurist - Zain Zulfiqar ------ Today is: " + s);
    }


    /*
     METHODS

     *********************************************************************************************************************************
     *********************************************************************************************************************************
     *********************************************************************************************************************************
     */

    //Method for printing the game screen and sprite depending on image specified, saves work and coding lines
    public void gamescreen (Image name)
    {
	Graphics g = getGraphics ();
	g.drawImage (name, 0, 0, this);
	g.drawImage (rollover, 0, 0, this);
	g.drawImage (sprite, (x1 * 32 + 20), (y1 * 32 + 44), this);
    }


    //Method for mouse when clicking on the DPAD to move the sprite
    public void arrows (int x, int y, int valid[] [])
    {
	//Up Button
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
	    if (y1 + 1 > 12)
	    {
	    }

	    else if (valid [y1 + 1] [x1] == 1)
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
	    if (x1 + 1 > 16)
	    {
	    }

	    else if (valid [y1] [x1 + 1] == 1)
		x1++;

	    sprite = spriteright;
	}


	//Exit button
	if (x > 630 && x < 735 && y > 384 && y < 481)

	    //Pops up a window for Yes or No exit option
	    {
		int exit = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit?\n\nAll of your progress will be deleted!\n", "Warning!", JOptionPane.YES_NO_OPTION);

		//if player exit, all stats are reset for new game
		if (exit == 0)
		{
		    screen = 0;
		    int condition = 0;
		    int backyardswitch = 1;
		    int insideswitch = 1;
		    int points = 0;
		    int basehealth = 60;
		    int health = 60;
		    int snakebase = 60;
		    int snakehealth = 60;
		    int snakeattack;
		    y1 = mainposy;
		    x1 = mainposx;
		}
	    }
    }


    //Method for keyboard input for arrow keys to move sprite
    public void keyboard (KeyEvent x, int valid[] [])
    {
	//up key
	if (x.getKeyCode () == x.VK_UP)
	{
	    if (y1 - 1 < 0)
	    {
	    }

	    else if (valid [y1 - 1] [x1] == 1)
		y1--;

	    sprite = spriteback;
	    rollover = RollUp;
	}


	//down key
	else if (x.getKeyCode () == x.VK_DOWN)
	{
	    if (y1 + 1 > 12)
	    {
	    }

	    else if (valid [y1 + 1] [x1] == 1)
		y1++;

	    sprite = spritefront;
	    rollover = RollDown;
	}


	//left key
	else if (x.getKeyCode () == x.VK_LEFT)
	{
	    if (x1 - 1 < 0)
	    {
	    }

	    else if (valid [y1] [x1 - 1] == 1)
		x1--;

	    sprite = spriteleft;
	    rollover = RollLeft;
	}


	//right key
	else if (x.getKeyCode () == x.VK_RIGHT)
	{
	    if (x1 + 1 > 16)
	    {
	    }

	    else if (valid [y1] [x1 + 1] == 1)
		x1++;

	    sprite = spriteright;
	    rollover = RollRight;
	}


	else
	    rollover = RollEmpty;
    }


    //Method with a switch to change backyard image for next instructions or event, returns the switched backyard house image
    public Image changeback (int switchname)
    {
	switch (switchname)
	{
	    case 1:
		backyard = backyard1;
		break;
	    case 2:
		backyard = backyard2;
		break;
	    case 3:
		backyard = backyardsnake;
		break;
	    case 4:
		backyard = backyardescape;
		break;
	}


	return backyard;
    }


    //Method with a switch to change inside house image for next instructions or event, returns the switched inside house image
    public Image changein (int switchname)
    {
	switch (switchname)
	{
	    case 1:
		inside = insidehouseswitch;
		break;
	    case 2:
		inside = insidehouseswitch2;
		break;
	    case 3:
		inside = insidehouseswitch3;
		break;
	}


	return inside;
    }


    //Method for importing the images needed, saves work and coding lines as there are lots of images
    /*
    public Image importimage (Image name, String filename)
    {
	Graphics g = getGraphics ();
	name = getImage (getDocumentBase (), ("" + filename));
	g.drawImage (name, -100, -100, this);
	return name;
    }
    */

    //Method for snake battle when called in plot
    public void battle ()
    {
	Graphics g = getGraphics ();
	g.drawImage (battle, 0, 0, this);
    }


    //Room 1 math challenges
    public void room1math ()
    {
	int mathpoints = 0;

	int mathroom = 0;
	switch (mathroom)
	{
	    case 0:
		String question = JOptionPane.showInputDialog ("Jimmy measures a tower. He thinks the tower is 45 feet high\n"
			+ "and 20 feet wide.\n\n"
			+ "Calculate the mass of the Sun in our solar system.\n\nHINT: (Answer something)", "Answer Here");

		JOptionPane.showMessageDialog (null, "Don't worry, you do not need to know the right answer.\n"
			+ "This was simply a test question to get your brain up and running.\n\n"
			+ "The real questions will come now.", "Start", JOptionPane.INFORMATION_MESSAGE);

	    case 1:
		question = JOptionPane.showInputDialog ("Factor the following expression.\nWrite your answer as: \n\n"
			+ "\"(a+b)(a-b)\"\n\nFactor:\nx^2 + 9x + 20", "Answer Here");
		try
		{
		    if (question.equalsIgnoreCase ("(x+4)(x+5)") || question.equalsIgnoreCase ("(x+5)(x+4)"))
		    {
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct answer!\n\nOn to the next question.", "1/5", JOptionPane.INFORMATION_MESSAGE);
			mathpoints++;
		    }

		    else
		    {
			JOptionPane.showMessageDialog (null, "Sorry, that is incorrect.\n\nOn to the next question.", "Wrong", JOptionPane.INFORMATION_MESSAGE);
		    }
		}

		catch (java.lang.NullPointerException m)
		{
		}

	    case 2:
		question = JOptionPane.showInputDialog ("A porabola has its x-intercept on (4, 5).\n"
			+ "What is the vertex of the parabola?\n\nWrite your answer as (x, y).", "Answer Here");

		try
		{
		    if (question.equals ("(4,5)") || question.equals ("(4, 5)") || question.equals ("4, 5") || question.equals ("4,5"))
		    {
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct answer!\n\nOn to the next question.", "2/5", JOptionPane.INFORMATION_MESSAGE);
			mathpoints++;
		    }

		    else
		    {
			JOptionPane.showMessageDialog (null, "Sorry, that is incorrect.\n\nOn to the next question.", "Wrong", JOptionPane.INFORMATION_MESSAGE);
		    }
		}

		catch (java.lang.NullPointerException m)
		{
		}

	    case 3:

		String[] possibleValues = {"Choose One:", "9.22m", "9.68cm", "11.32m", "8.56m"};
		String selectedValue = (String) JOptionPane.showInputDialog (null,
			"A triangle has a height of 6m and hypotenuse of 11m.\n"
			+ "What is the correct length of the triangle?", "Choose One", JOptionPane.INFORMATION_MESSAGE, null,
			possibleValues, possibleValues [0]);
		try
		{
		    if (selectedValue.equals ("9.22m"))
		    {
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct answer!\n\nOn to the next question.", "3/5", JOptionPane.INFORMATION_MESSAGE);
			mathpoints++;
		    }

		    else
		    {
			JOptionPane.showMessageDialog (null, "Sorry, that is incorrect.\n\nOn to the next question.", "Wrong", JOptionPane.INFORMATION_MESSAGE);
		    }
		}

		catch (java.lang.NullPointerException m)
		{
		}

	    case 4:
		JCheckBox checkbox1 = new JCheckBox ("(121/11) + (-(6)-(6))");
		JCheckBox checkbox2 = new JCheckBox ("(Sqrt of 196) - 14");
		JCheckBox checkbox3 = new JCheckBox ("2(3)^3 + 3^2 - 15");
		JCheckBox checkbox4 = new JCheckBox ("(-(6 x 36) + 81) x 2");
		JCheckBox checkbox5 = new JCheckBox ("|(((x-5)^2)^3 + (67-42)^2 - 6x)|");

		question = "Check the questions which work out to a positive value.\n\n";
		Object[] params = {question, checkbox1, checkbox2, checkbox3, checkbox4, checkbox5};
		JOptionPane.showMessageDialog (null, params, "Choose the Correct Options", JOptionPane.INFORMATION_MESSAGE);

		try
		{
		    if (checkbox1.isSelected () && checkbox3.isSelected () && checkbox5.isSelected () && !checkbox2.isSelected () && !checkbox4.isSelected ())
		    {
			JOptionPane.showMessageDialog (null, "Congratulations! You selected the correct questions!\n\nOn to the next question.", "4/5", JOptionPane.INFORMATION_MESSAGE);
			mathpoints++;
		    }

		    else
		    {
			JOptionPane.showMessageDialog (null, "Sorry, you did not check the correct questions.\n\nOn to the final question.", "Wrong", JOptionPane.INFORMATION_MESSAGE);
		    }
		}

		catch (java.lang.NullPointerException m)
		{
		}

	    case 5:
		question = JOptionPane.showInputDialog ("Who invented Pythagorean theorem?\n\n"
			+ "HINT: It's in the theorem itself.", "Answer Here");

		try
		{
		    if (question.equalsIgnoreCase ("Pythagoras") && mathpoints == 2)
		    {
			mathpoints = 0;
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct name!\n\n"
				+ "This was the last question and you only have 3 points.\n\n"
				+ "Try again!", "5/5", JOptionPane.INFORMATION_MESSAGE);
		    }

		    else if (question.equalsIgnoreCase ("Pythagoras") && mathpoints == 3)
		    {
			mathpoints = 0;
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct name!\n\n"
				+ "This was the last question and you only have 4 points.\n\n"
				+ "Try again!", "5/5", JOptionPane.INFORMATION_MESSAGE);
		    }

		    else if (question.equalsIgnoreCase ("Pythagoras") && mathpoints == 1)
		    {
			mathpoints = 0;
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct name!\n\n"
				+ "This was the last question and you only have 2 points.\n\n"
				+ "Try again!", "5/5", JOptionPane.INFORMATION_MESSAGE);
		    }

		    else if (question.equalsIgnoreCase ("Pythagoras") && mathpoints == 0)
		    {
			mathpoints = 0;
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct name!\n\n"
				+ "This was the last question and you only have 1 point.\n\n"
				+ "Try again!", "5/5", JOptionPane.INFORMATION_MESSAGE);
		    }

		    else if (question.equalsIgnoreCase ("Pythagoras") && mathpoints == 4)
		    {
			mathpoints++;
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct name!\n\n"
				+ "This was the last question and you have answered all of the questions correctly.\n\n"
				+ "A secret code is revealed.", "5/5", JOptionPane.INFORMATION_MESSAGE);
		    }

		    else
		    {
			JOptionPane.showMessageDialog (null, "Sorry, that is incorrect.\n\nThis was the last question and you do not have a total of 5 points.\n\n"
				+ "Try again!", "Wrong", JOptionPane.INFORMATION_MESSAGE);
		    }
		}

		catch (java.lang.NullPointerException m)
		{
		}


		if (mathpoints == 5)
		{
		    mathsign = mathend;
		    points += mathpoints;
		}
	}
    }


    //Room 2 true or false challenges
    public void room2tf ()
    {
	int tfpoints = 0;

	int tfroom = 0;
	switch (tfroom)
	{
	    case 0:
		String question = "Grade 10 IBs will graduate in the year of 2015.\n\n";
		JRadioButton button1 = new JRadioButton ("This statement is TRUE");
		JRadioButton button2 = new JRadioButton ("This statement is FALSE");
		ButtonGroup group = new ButtonGroup ();
		group.add (button1);
		group.add (button2);
		Object[] params = {question, button1, button2};
		JOptionPane.showMessageDialog (null, params, "True or False", JOptionPane.INFORMATION_MESSAGE);

		if (button1.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nOn to the next question.", "1/5", JOptionPane.INFORMATION_MESSAGE);
		    tfpoints++;
		}

		else if (button2.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is incorrect!\n\nOn to the next question.", "1/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 1:
		question = "Turner Fenton is a combination of two schools.\n\n";
		button1 = new JRadioButton ("This statement is TRUE");
		button2 = new JRadioButton ("This statement is FALSE");
		group = new ButtonGroup ();
		group.add (button1);
		group.add (button2);
		Object[] params2 = {question, button1, button2};
		JOptionPane.showMessageDialog (null, params2, "True or False", JOptionPane.INFORMATION_MESSAGE);

		if (button1.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nOn to the next question.", "2/5", JOptionPane.INFORMATION_MESSAGE);
		    tfpoints++;
		}

		else if (button2.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is incorrect!\n\nOn to the next question.", "2/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 2:
		question = "There are 10 ICS courses available for students at Turner Fenton.\n\n";
		button1 = new JRadioButton ("This statement is TRUE");
		button2 = new JRadioButton ("This statement is FALSE");
		group = new ButtonGroup ();
		group.add (button1);
		group.add (button2);
		Object[] params3 = {question, button1, button2};
		JOptionPane.showMessageDialog (null, params3, "True or False", JOptionPane.INFORMATION_MESSAGE);

		if (button2.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nOn to the next question.", "3/5", JOptionPane.INFORMATION_MESSAGE);
		    tfpoints++;
		}

		else if (button1.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is incorrect!\n\nOn to the next question.", "3/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 3:
		question = "The IB learners profile is still drawn as a hexagon.\n\n";
		button1 = new JRadioButton ("This statement is TRUE");
		button2 = new JRadioButton ("This statement is FALSE");
		group = new ButtonGroup ();
		group.add (button1);
		group.add (button2);
		Object[] params4 = {question, button1, button2};
		JOptionPane.showMessageDialog (null, params4, "True or False", JOptionPane.INFORMATION_MESSAGE);

		if (button2.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nOn to the next question.", "4/5", JOptionPane.INFORMATION_MESSAGE);
		    tfpoints++;
		}

		else if (button1.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is incorrect!\nJust rescently, it was changed to a circle.\n\n"
			    + "On to the next question.", "4/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 4:
		question = "The Turner Fenton library has gotten computers with i5 proccessors.\n\n";
		button1 = new JRadioButton ("This statement is TRUE");
		button2 = new JRadioButton ("This statement is FALSE");
		group = new ButtonGroup ();
		group.add (button1);
		group.add (button2);
		Object[] params5 = {question, button1, button2};
		JOptionPane.showMessageDialog (null, params5, "True or False", JOptionPane.INFORMATION_MESSAGE);

		if (button1.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nOn to your results...", "5/5", JOptionPane.INFORMATION_MESSAGE);
		    tfpoints++;
		}

		else if (button2.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is incorrect!\n\nYour results:", "5/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 5:
		if (tfpoints == 5)
		{
		    JOptionPane.showMessageDialog (null, "Congratulations! You got all of the questions right!\n\nA secret code is revealed.", "Win", JOptionPane.INFORMATION_MESSAGE);
		    tfpoints = 5;
		}

		else if (tfpoints == 4)
		{
		    JOptionPane.showMessageDialog (null, "Congratulations! You got 4/5 questions right!\n\nA secret code is revealed.", "Win", JOptionPane.INFORMATION_MESSAGE);
		    tfpoints = 4;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, you do not have enough points.\n\nTry Again!", "Lose", JOptionPane.INFORMATION_MESSAGE);
		    tfpoints = 0;
		}

		if (tfpoints == 5 || tfpoints == 4)
		{
		    tfsign = tfend;
		    points += tfpoints;
		}
	}
    }


    //Room 3 multiple choice challenges
    public void room3mc ()
    {
	int mcpoints = 0;

	int mcroom = 0;
	switch (mcroom)
	{
	    case 0:
		JCheckBox checkbox1 = new JCheckBox ("First-Aid Kit");
		JCheckBox checkbox2 = new JCheckBox ("Smoke Detector");
		JCheckBox checkbox3 = new JCheckBox ("Carbon Monoxide Detector");
		JCheckBox checkbox4 = new JCheckBox ("Fire Extinguisher");
		JCheckBox checkbox5 = new JCheckBox ("All of the above");
		ButtonGroup group = new ButtonGroup ();
		group.add (checkbox1);
		group.add (checkbox2);
		group.add (checkbox3);
		group.add (checkbox4);
		group.add (checkbox5);

		String question = "There are many things a house should have.\nSelect the most important thing for safety.\n\nEach house must have a:\n\n";
		Object[] params = {question, checkbox1, checkbox2, checkbox3, checkbox4, checkbox5};
		JOptionPane.showMessageDialog (null, params, "Choose the Most-Correct Option", JOptionPane.INFORMATION_MESSAGE);

		if (checkbox5.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nNext question!", "1/5", JOptionPane.INFORMATION_MESSAGE);
		    mcpoints++;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is not the best answer!", "1/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 1:
		checkbox1 = new JCheckBox ("Knee/Elbow Pads");
		checkbox2 = new JCheckBox ("A Helmet");
		checkbox3 = new JCheckBox ("A horn");
		checkbox4 = new JCheckBox ("Reflectors");
		checkbox5 = new JCheckBox ("Mirrors");
		group = new ButtonGroup ();
		group.add (checkbox1);
		group.add (checkbox2);
		group.add (checkbox3);
		group.add (checkbox4);
		group.add (checkbox5);

		question = "What is the most important piece of equipment\nneeded when riding a bike?\n\nSelect the most important piece of equipment for safety.\n\n";
		Object[] params1 = {question, checkbox1, checkbox2, checkbox3, checkbox4, checkbox5};
		JOptionPane.showMessageDialog (null, params1, "Choose the Most-Correct Option", JOptionPane.INFORMATION_MESSAGE);

		if (checkbox2.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nNext question!", "2/5", JOptionPane.INFORMATION_MESSAGE);
		    mcpoints++;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is not the best answer!", "2/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 2:
		checkbox1 = new JCheckBox ("Look both ways before crossing");
		checkbox2 = new JCheckBox ("Run across quickly and try to dodge the cars");
		checkbox3 = new JCheckBox ("Check the soil's pH level to see if you can cross");
		checkbox4 = new JCheckBox ("Walk right after a car passes by");
		group = new ButtonGroup ();
		group.add (checkbox1);
		group.add (checkbox2);
		group.add (checkbox3);
		group.add (checkbox4);

		question = "What is the number 1 rule for crossing a street?\n\nSelect the best rule.\n\n";
		Object[] params2 = {question, checkbox1, checkbox2, checkbox3, checkbox4};
		JOptionPane.showMessageDialog (null, params2, "Choose the Most-Correct Option", JOptionPane.INFORMATION_MESSAGE);

		if (checkbox1.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nNext question!", "3/5", JOptionPane.INFORMATION_MESSAGE);
		    mcpoints++;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is not the best answer!", "3/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 3:
		checkbox1 = new JCheckBox ("Leave a note in case something bad happens");
		checkbox2 = new JCheckBox ("Tie yourself with a rope to a stable place");
		checkbox3 = new JCheckBox ("Have a friend watch in case you need help");
		checkbox4 = new JCheckBox ("No way to prevent an accident, just do the stunt and hope to live");
		group = new ButtonGroup ();
		group.add (checkbox1);
		group.add (checkbox2);
		group.add (checkbox3);
		group.add (checkbox4);

		question = "What is the best way to minimize the risk of an accident when attempting a stunt?\n\n";
		Object[] params3 = {question, checkbox1, checkbox2, checkbox3, checkbox4};
		JOptionPane.showMessageDialog (null, params3, "Choose the Most-Correct Option", JOptionPane.INFORMATION_MESSAGE);

		if (checkbox3.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nLast question!", "4/5", JOptionPane.INFORMATION_MESSAGE);
		    mcpoints++;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is not the best answer!\n\nLast question!", "4/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 4:
		checkbox1 = new JCheckBox ("Wear a metal suit");
		checkbox2 = new JCheckBox ("Hold the copper wire, not the rubber outside");
		checkbox3 = new JCheckBox ("Soak your hands with water so that the wire is easy to grip");
		checkbox4 = new JCheckBox ("All of the above");
		checkbox5 = new JCheckBox ("None of the above");
		group = new ButtonGroup ();
		group.add (checkbox1);
		group.add (checkbox2);
		group.add (checkbox3);
		group.add (checkbox4);
		group.add (checkbox5);

		question = "What is the safest way to deal with an electrical wire?\n\n";
		Object[] params4 = {question, checkbox1, checkbox2, checkbox3, checkbox4, checkbox5};
		JOptionPane.showMessageDialog (null, params4, "Choose the Most-Correct Option", JOptionPane.INFORMATION_MESSAGE);

		if (checkbox5.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nOn to your results...", "5/5", JOptionPane.INFORMATION_MESSAGE);
		    mcpoints++;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is not the best answer!\n\nLast question!", "5/5", JOptionPane.INFORMATION_MESSAGE);
		}
	    case 5:
		if (mcpoints == 5)
		{
		    JOptionPane.showMessageDialog (null, "Congratulations! You got all of the questions right!\n\nA secret code is revealed.", "Win", JOptionPane.INFORMATION_MESSAGE);
		    mcpoints = 5;
		}

		else if (mcpoints == 4)
		{
		    JOptionPane.showMessageDialog (null, "Congratulations! You got 4/5 questions right!\n\nA secret code is revealed.", "Win", JOptionPane.INFORMATION_MESSAGE);
		    mcpoints = 4;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, you do not have enough points.\n\nTry Again!", "Lose", JOptionPane.INFORMATION_MESSAGE);
		    mcpoints = 0;
		}

		if (mcpoints == 5 || mcpoints == 4)
		{
		    mcsign = mcend;
		    points += mcpoints;
		}
	}
    }


    //Room 4 computer science challenges
    public void room4cs ()
    {
	int cspoints = 0;

	int csroom = 0;
	switch (csroom)
	{
	    case 0:
		String question = JOptionPane.showInputDialog ("What is 92 in binary?\n", "Answer Here");

		if (question.equals ("1011100"))
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nOn to the next question.", "1/5", JOptionPane.INFORMATION_MESSAGE);
		    cspoints++;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is incorrect.\n\nNext question.", "1/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 1:
		String[] possibleValues = {"Choose One:", "heywhatsup", "heywhatsup.class", "HEYWHATSUP.java", "heywhatsup.java"};
		String selectedValue = (String) JOptionPane.showInputDialog (null,
			"If a class is named \"heywhatsup\" what should the program be saved as?\n\n", "Choose One", JOptionPane.INFORMATION_MESSAGE, null,
			possibleValues, possibleValues [0]);
		try
		{
		    if (selectedValue.equals ("heywhatsup.java"))
		    {
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct answer!\n\nOn to the next question.", "2/5", JOptionPane.INFORMATION_MESSAGE);
			cspoints++;
		    }

		    else
		    {
			JOptionPane.showMessageDialog (null, "Sorry, that is incorrect.\n\nOn to the next question.", "Wrong", JOptionPane.INFORMATION_MESSAGE);
		    }
		}

		catch (java.lang.NullPointerException m)
		{
		}

	    case 2:
		question = "What are the steps of a loop in order?\n\na) Test loop stopping condition\nb) Steps to repeat\n"
		    + "c) Initialize loop stopping variable\nd) Progress towards stopping condition\n\n";
		JRadioButton button1 = new JRadioButton ("1) a, b, c, d");
		JRadioButton button2 = new JRadioButton ("2) c, a, b, d");
		JRadioButton button3 = new JRadioButton ("3) d, a, b, c");
		JRadioButton button4 = new JRadioButton ("4) b, c, a, d");
		ButtonGroup group = new ButtonGroup ();
		group.add (button1);
		group.add (button2);
		group.add (button3);
		group.add (button4);
		Object[] params = {question, button1, button2, button3, button4};
		JOptionPane.showMessageDialog (null, params, "Choose One", JOptionPane.INFORMATION_MESSAGE);

		if (button2.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nOn to the next question.", "3/5", JOptionPane.INFORMATION_MESSAGE);
		    cspoints++;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is incorrect!\n\nOn to the next question.", "3/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 3:
		question = "Which of the following has a return type?\n\n";
		button1 = new JRadioButton ("1) public String test ();");
		button2 = new JRadioButton ("2) public void test ();");
		button3 = new JRadioButton ("3) public int test ();");
		button4 = new JRadioButton ("4) public Image test ();");
		JRadioButton button6 = new JRadioButton ("5) All of the above");
		JRadioButton button5 = new JRadioButton ("6) 1, 3, 4");
		JRadioButton button7 = new JRadioButton ("7) None of the above");

		group = new ButtonGroup ();
		group.add (button1);
		group.add (button2);
		group.add (button3);
		group.add (button4);
		group.add (button5);
		group.add (button6);
		Object[] params1 = {question, button1, button2, button3, button4, button5, button6, button7};
		JOptionPane.showMessageDialog (null, params1, "Choose One", JOptionPane.INFORMATION_MESSAGE);

		if (button5.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nOn to the final question.", "4/5", JOptionPane.INFORMATION_MESSAGE);
		    cspoints++;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is incorrect!\n\nOn to the final question.", "4/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 4:
		question = "What does PDLC stand for?\n\n";
		button1 = new JRadioButton ("Plan Data Locating Company");
		button2 = new JRadioButton ("Product Detail Logging Cycle");
		button3 = new JRadioButton ("Product Development Life Cycle");
		button4 = new JRadioButton ("People Doing Lame Creations");

		group = new ButtonGroup ();
		group.add (button1);
		group.add (button2);
		group.add (button3);
		group.add (button4);

		Object[] params2 = {question, button1, button2, button3, button4};
		JOptionPane.showMessageDialog (null, params2, "Choose One", JOptionPane.INFORMATION_MESSAGE);

		if (button3.isSelected ())
		{
		    JOptionPane.showMessageDialog (null, "Correct!\n\nOn to your results...", "4/5", JOptionPane.INFORMATION_MESSAGE);
		    cspoints++;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, that is incorrect!\n\nOn to your results...", "4/5", JOptionPane.INFORMATION_MESSAGE);
		}

	    case 5:
		if (cspoints == 5)
		{
		    JOptionPane.showMessageDialog (null, "Congratulations! You got all of the questions right!\n\nA secret code is revealed.", "Win", JOptionPane.INFORMATION_MESSAGE);
		    cspoints = 5;
		}

		else if (cspoints == 4 || cspoints == 3)
		{
		    JOptionPane.showMessageDialog (null, "Congratulations! You got " + cspoints + "/5 questions right!\n\nA secret code is revealed.", "Win", JOptionPane.INFORMATION_MESSAGE);
		    cspoints = 4;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, you do not have enough points.\n\nTry Again!", "Lose", JOptionPane.INFORMATION_MESSAGE);
		    cspoints = 0;
		}

		if (cspoints == 5 || cspoints == 4 || cspoints == 3)
		{
		    cssign = csend;
		    points += cspoints;
		}

	}
    }


    //Room 5 unscrammbling challenges
    public void room5us ()
    {
	int uspoints = 0;

	int usroom = 0;
	switch (usroom)
	{
	    case 0:
		String question = JOptionPane.showInputDialog ("Solve the following riddle:\n\nWhat gets wetter and wetter as it dries?\n\nHINT: u, m, e, l, t, w, b, r, a, o", "Answer Here");

		try
		{
		    if (question.equalsIgnoreCase ("towel") || question.equalsIgnoreCase ("a towel"))
		    {
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct answer!\n\nOn to the next question.", "1/5", JOptionPane.INFORMATION_MESSAGE);
			uspoints++;
		    }

		    else
		    {
			JOptionPane.showMessageDialog (null, "Sorry, that is incorrect.\n\nOn to the next question.", "Wrong", JOptionPane.INFORMATION_MESSAGE);
		    }
		}

		catch (java.lang.NullPointerException m)
		{
		}

	    case 1:
		question = JOptionPane.showInputDialog ("If the day before the day after tomorrow will be Saturday,\n"
			+ "and the day after the day before yesterday was Thursday,\n\nWhat is today?\n\nHINT: t, r, a, d, f, y, h, u, s, i", "Answer Here");

		try
		{
		    if (question.equalsIgnoreCase ("friday"))
		    {
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct answer!\n\nOn to the next question.", "2/5", JOptionPane.INFORMATION_MESSAGE);
			uspoints++;
		    }

		    else
		    {
			JOptionPane.showMessageDialog (null, "Sorry, that is incorrect.\n\nOn to the next question.", "Wrong", JOptionPane.INFORMATION_MESSAGE);
		    }
		}

		catch (java.lang.NullPointerException m)
		{
		}

	    case 2:
		question = JOptionPane.showInputDialog ("Throw me off the highest building, and I will not break.\nBut put me in the ocean, and I will.\n\nWhat am I?\n\n"
			+ "HINT: r, c, t, s, k, s, i, u, o, e", "Answer Here");

		try
		{
		    if (question.equalsIgnoreCase ("tissue") || question.equalsIgnoreCase ("a tissue"))
		    {
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct answer!\n\nOn to the next question.", "3/5", JOptionPane.INFORMATION_MESSAGE);
			uspoints++;
		    }

		    else
		    {
			JOptionPane.showMessageDialog (null, "Sorry, that is incorrect.\n\nOn to the next question.", "Wrong", JOptionPane.INFORMATION_MESSAGE);
		    }
		}

		catch (java.lang.NullPointerException m)
		{
		}

	    case 3:
		question = JOptionPane.showInputDialog ("Give me food, and I will live.\nGive me water, and I will die.\n\nWhat am I?\n\nHINT: w, r, a, r, f, i, t, e", "Answer Here");

		try
		{
		    if (question.equalsIgnoreCase ("fire"))
		    {
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct answer!\n\nOn to the final question.", "4/5", JOptionPane.INFORMATION_MESSAGE);
			uspoints++;
		    }

		    else
		    {
			JOptionPane.showMessageDialog (null, "Sorry, that is incorrect.\n\nOn to the final question.", "Wrong", JOptionPane.INFORMATION_MESSAGE);
		    }
		}

		catch (java.lang.NullPointerException m)
		{
		}

	    case 4:
		question = JOptionPane.showInputDialog ("Until you acknowledge me,\nI am not known.\nYet how much you miss me,\nWhen I have flown.\n\nWhat am I?\n\n"
			+ "HINT: t, g, a, m, s, e, i, r, s", "Answer Here");

		try
		{
		    if (question.equalsIgnoreCase ("time"))
		    {
			JOptionPane.showMessageDialog (null, "Congratulations! You got the correct answer!\n\nOn to your results...", "5/5", JOptionPane.INFORMATION_MESSAGE);
			uspoints++;
		    }

		    else
		    {
			JOptionPane.showMessageDialog (null, "Sorry, that is incorrect.\n\nOn to your results...", "Wrong", JOptionPane.INFORMATION_MESSAGE);
		    }
		}

		catch (java.lang.NullPointerException m)
		{
		}

	    case 5:
		if (uspoints == 5)
		{
		    JOptionPane.showMessageDialog (null, "Congratulations! You got all of the riddles right!\n\nA secret code is revealed.", "Win", JOptionPane.INFORMATION_MESSAGE);
		    uspoints = 5;
		}

		else if (uspoints == 4)
		{
		    JOptionPane.showMessageDialog (null, "Congratulations! You got " + uspoints + "/5 riddles right!\n\nA secret code is revealed.", "Win", JOptionPane.INFORMATION_MESSAGE);
		    uspoints = 4;
		}

		else
		{
		    JOptionPane.showMessageDialog (null, "Sorry, you do not have enough points.\n\nTry Again!", "Lose", JOptionPane.INFORMATION_MESSAGE);
		    uspoints = 0;
		}

		if (uspoints == 5 || uspoints == 4)
		{
		    ussign = usend;
		    points += uspoints;
		}
	}
    }


    //upstairs chest challenge when all challeneges are completed
    public void keyroom ()
    {
	JOptionPane.showMessageDialog (null, "Answer the following riddle to gain the key.", "Final Test", JOptionPane.INFORMATION_MESSAGE);

	try
	{
	    String question = JOptionPane.showInputDialog ("How do you get a gun unemployed?\n\nIt gets ____________\n ", "Answer Here");

	    if (question.equalsIgnoreCase ("fired"))
	    {
		JOptionPane.showMessageDialog (null, "Congratulations! You got the correct answer!", "Key Gained", JOptionPane.INFORMATION_MESSAGE);
		key = keygained;
		condition = 3;
		backyardswitch = 3;
		changeback (backyardswitch);
		validbackyard [4] [10] = 0;
		validbackyard [4] [9] = 0;
	    }

	    else
	    {
		JOptionPane.showMessageDialog (null, "Sorry, that is incorrect.\nTry again!", "Try Again", JOptionPane.INFORMATION_MESSAGE);
	    }
	}


	catch (java.lang.NullPointerException m)
	{
	}
    }


    //attack options for player during snake battle, returns the attack damage which subtracts the health of snake.
    public int attack (String move)
    {
	int damage = 0;

	if (move.equals ("Punch"))
	    damage = 4 + ((int) (Math.random () * 3) + 1);

	else if (move.equals ("Kick"))
	    damage = 5 + ((int) (Math.random () * 5) + 1);

	else if (move.equals ("Slash"))
	    damage = 7 + ((int) (Math.random () * 6) + 1);

	else if (move.equals ("Bite"))
	    damage = 3;

	else if (move.equals ("Hammer"))
	    damage = ((int) (Math.random () * 5) + 1) + ((int) (Math.random () * 5) + 1);

	return damage;
    }
}



