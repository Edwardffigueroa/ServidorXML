import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import contenido.Bolita;
import processing.data.XML;

public class AdminDB {
	
	private XML bd;
	private String rutaBD;
	public AdminDB(String rutaBD) {
		this.rutaBD=rutaBD;
		
		File file= new File(rutaBD);
		
		if (file.exists()) {
			
			try {
				bd = new XML(file);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
			
		}else{
			
			bd= new XML("bolitas");	
			
		}
		
		
	}
	
	public void agregarDato(Bolita b){
		
		XML bolitaBD = new XML("bolita");
		bolitaBD.setInt("x", b.getX());
		bolitaBD.setInt("y", b.getY());
		bolitaBD.setInt("diam", b.getDiam());
		bolitaBD.setInt("r", b.getR());
		bolitaBD.setInt("g", b.getG());
		bolitaBD.setInt("b", b.getB());
		
		System.out.println(bolitaBD);
		bd.addChild(bolitaBD);
		
		
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				bd.save(new File(rutaBD));
				
			}
		});
		
	}
	
	
	public ArrayList<Bolita> bolitasExistentes(){
		ArrayList<Bolita> list= new ArrayList<Bolita>();
		XML [] bolitasBD= bd.getChildren("bolita");
		for (int i = 0; i < bolitasBD.length; i++) {
			int x= bolitasBD[i].getInt("x");
			int y= bolitasBD[i].getInt("y");
			int diam= bolitasBD[i].getInt("diam");
			int r= bolitasBD[i].getInt("r");
			int g= bolitasBD[i].getInt("g");
			int b= bolitasBD[i].getInt("b");
			
			Bolita bolaExistente = new Bolita(x, y, diam, r, g, b);
			list.add(bolaExistente);
			
		}
		return list;
	}

}
