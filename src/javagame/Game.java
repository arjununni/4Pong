package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import java.util.Random;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

class Game extends BasicGame{
	
	private org.newdawn.slick.Font scoreFont;
	Color c;
	Random rand = new Random();
	static int height = 480;
	static int width = 640;
	static String title = "4Pong";
	static int fpsLimit = 60;
	Circle ball;
	Rectangle paddlePlayer;
	Rectangle cpu1;
	Rectangle cpu2;
	Rectangle cpu3;
	Vector2f ballVelocity;
	int scorePlayer;
	int scoreCPU1;
	int scoreCPU2;
	int scoreCPU3;
	float ypos;
	float xpos;
	float xpos2;
	int pc1;
	int pc2;
	int pc3;
	int rno;
	int r;
	int gr;
	int b;
	
	public Game(String title){
		super(title);
	}
	
	public void init(GameContainer gc){
		
		gc.getInput().enableKeyRepeat();
		paddlePlayer = new RoundedRectangle(5, height /2, 10, 80, 3);
		cpu1 = new RoundedRectangle(width - 15, height /2, 10, 80, 3);
		cpu2 = new RoundedRectangle(width /2, height - 15, 80, 10, 3);
		cpu3 = new RoundedRectangle(width /2, 10, 80, 10, 3);
		ball = new Circle(width /2, height/2, 6);
		ballVelocity = new Vector2f(-3, 1);
		this.scoreFont = createFont("Arial", 50);
	}
	
	private Font createFont(String string, int size){
		UnicodeFont font = new UnicodeFont(new java.awt.Font(string, 0, size), size, false, false);
		font.getEffects().add(new ColorEffect());
		font.addGlyphs("0123456789");
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return font;
	}

	public void update(GameContainer gc, int delta){
		
		if(gc.getInput().isKeyDown(Input.KEY_UP)){
			if(paddlePlayer.getMinY() > 0)
				paddlePlayer.setY(paddlePlayer.getY() - 10.0f);
		}else if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
			if(paddlePlayer.getMaxY()<height)
				paddlePlayer.setY(paddlePlayer.getY() + 10.0f);
		}
		
		ball.setLocation(ball.getX()+ballVelocity.getX(), ball.getY()+ballVelocity.getY());
		
		if(ball.intersects(paddlePlayer)){
			ballVelocity.x = -ballVelocity.getX();
			changecolor();
			rno = rand.nextInt(100);
		}
		if(ball.intersects(cpu1)){
			ballVelocity.x = -ballVelocity.getX();
			pc1 = 1;
			pc2 = 0;
			pc3 = 0;
			changecolor();
			
		}
		if(ball.intersects(cpu2)){
			ballVelocity.y = -ballVelocity.getY();
			pc1 = 0;
			pc2 = 1;
			pc3 = 0;
			changecolor();
		}
		if(ball.intersects(cpu3)){
			ballVelocity.y = -ballVelocity.getY();
			pc1 = 0;
			pc2 = 0;
			pc3 = 1;
			changecolor();
		}
		
		if(ball.getMinX() <= 0){
			ballVelocity.x = -ballVelocity.getX();
			if(pc1 == 1){
				scoreCPU1++;
			}
			if(pc2 == 1){
				scoreCPU2++;
			}
			if(pc3 == 1){
				scoreCPU3++;
			}
			changecolor();
		}
		if(ball.getMaxX()>= width){
			ballVelocity.x = -ballVelocity.getX();
			scorePlayer++;
			changecolor();
		}
		if(ball.getY()<=0){
			ballVelocity.y = -ballVelocity.getY();
			scorePlayer++;
			changecolor();
		}
		if(ball.getY()>= height){
			ballVelocity.y = -ballVelocity.getX();
			scorePlayer++;
			changecolor();
		}
		
		
		ypos = ball.getCenterY() - cpu1.getHeight() /2 ;
		xpos = ball.getCenterX() - cpu2.getWidth() /2 ;
		
		if(rno >= 75){
			ypos -= 43;
		}
		
		if(rno <= 75){
			xpos -= 43;
		}
		
		cpu1.setY(ypos);
		if(ball.getY() > 240){
			cpu2.setX(xpos);
		}
		if(ball.getY() < 240){
			cpu3.setX(xpos);
		}
		
	}
	
	public void changecolor(){
		r = rand.nextInt(256);
		gr = rand.nextInt(256);
		b = rand.nextInt(256);
		c = new Color(r,gr,b);
	}
	
	public void render(GameContainer gc, Graphics g){
		
		g.setColor(Color.gray);
		g.fillRect(0.0F, 0.0F, width, height);
		g.setColor(Color.white);
		g.drawString("4Pong", 10, 25);
		g.drawString("LooneyTunes, 31/3", 10, 40);
		g.fill(paddlePlayer);
		g.fill(cpu1);
		g.fill(cpu2);
		g.setColor(c);
		g.fill(ball);
		g.setColor(Color.white);
		g.fill(cpu3);
		g.setFont(scoreFont);
		g.drawString(Integer.toString(scoreCPU1), width/2+100, height /2);
		g.drawString(Integer.toString(scorePlayer), width/2-100, height /2);
		g.drawString(Integer.toString(scoreCPU2), width /2, height/2 + 100);
		g.drawString(Integer.toString(scoreCPU3),width/2, height/2 - 100);
	}
	
	public static void main(String[] args) throws SlickException{
		
		AppGameContainer app = new AppGameContainer(new Game(title));
		app.setDisplayMode(640, 480, false);
		app.setTargetFrameRate(fpsLimit);
		app.start();
		
	}
}
