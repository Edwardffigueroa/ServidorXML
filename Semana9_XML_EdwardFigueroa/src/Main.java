import processing.core.PApplet;

public class Main extends PApplet{
	
	
	Logica log;
	
	public static void main(String[] args) {
		PApplet.main("Main");
	}

	@Override
	public void settings() {
		super.settings();
		size(500, 500);
		
	}
	
	@Override
	public void setup() {
		log= new Logica(this);
	}
	
	@Override
	public void draw() {
	background(255);
	log.pintar();
	}
	
	
	
}
