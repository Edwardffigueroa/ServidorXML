import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import contenido.Bolita;
import processing.core.PApplet;

public class Logica implements Observer{
	private PApplet app;
	private Comunicacion com;
	private ArrayList <Bolita> bolitas; 
	private AdminDB server;
	
	public Logica(PApplet app) {
		
		this.app=app;
		 
		com= new Comunicacion();
		com.addObserver(this);
		new Thread(com).start();
		server = new AdminDB("data"+File.separator+"bolitas.xml");
		bolitas= server.bolitasExistentes();
		JOptionPane.showMessageDialog(null, "Haciendo una pruebita de esto :v", "InfoBox: " + "¿que pedos?", JOptionPane.INFORMATION_MESSAGE);
		
		
		
		
	}
	
	public void pintar(){
		
		app.smooth();
		for (Bolita b : bolitas) {
			app.fill(b.getR(), b.getG(), b.getB());
			app.ellipse(b.getX(), b.getY(), b.getDiam(), b.getDiam());
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		
		Bolita bol= (Bolita) arg;
		server.agregarDato(bol);
		bolitas.add(bol);
		
	}

}
