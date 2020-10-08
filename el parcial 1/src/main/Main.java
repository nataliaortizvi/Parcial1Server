package main;


import java.net.InetAddress;
import java.net.UnknownHostException;

import processing.core.PApplet;

public class Main extends PApplet {
	
	TcpSingleton tcp;
	int r, g, b;
	float x, y;
	String nombre;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("main.Main");

	}
	
	public void settings() {
		size(800,500);
	}
	
	public void setup() {
		tcp = TcpSingleton.getInstance();
		tcp.setObserver(this);
		
		x = 100;
		y = 100;
		r = 355;
		g = 150;
		b = 169;
		
		//saber cual es mi ip para colocarla en el socket del cliente
		 try {
	            InetAddress n = InetAddress.getLocalHost();
	            String ip = n.getHostAddress();
	            System.out.println(ip);

	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        }
		
		
	}
	
	public void draw() {
		background(255);
		
		if(nombre == null) {
			//no hacer nada hasta que se escriba el nombre de avatar
		}else {
			fill (r,g,b);
			noStroke();
			ellipse(x,y,50,50);
			textAlign(CENTER);
			textSize(15);
			text (nombre, x, y-29);
		}
	}
	
	public void nombreLlegando(String msg) {
		nombre = msg;
		
	}
	public void movimientoLlegando(float xr, float yr) {
		x = xr;
		y = yr;
	}
	public void colorLlegando(int rr, int gr, int br) {
		r = rr;
		g = gr;
		b = br;
	}
	

	
}
