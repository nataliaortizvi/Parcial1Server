package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import model.Color;
import model.Generic;
import model.Movimiento;
import model.Nombre;

public class TcpSingleton extends Thread{
	
	////////constructor privado////////
	private TcpSingleton(){

	}

	//unica instancia de TcpSingleton
	private static TcpSingleton unicaInstancia;

	//metodo estático que devuelve la unica instancia
	public static TcpSingleton getInstance(){
		if(unicaInstancia == null){
			unicaInstancia = new TcpSingleton();
			unicaInstancia.start();
		}
		return unicaInstancia;
	}
	
	
	
	////patron observer
    private Main observer;
    
    public void setObserver(Main observer) {
    	this.observer = observer;
    }

    public Main getObserver() {
		return observer;
	}
    
    
    //variables globales tcp
	ServerSocket server;
	Socket socket;
	BufferedWriter writer;
	BufferedReader reader;
	
	
    @Override
    public void run() {
    	try {
    		
    		//conexion
			server = new ServerSocket(5000);
			System.out.println("esperando conexion");
			
			socket = server.accept();
			System.out.println("cliente conectado");
			
			
			
			//emisor
			OutputStream os = socket.getOutputStream();
			writer = new BufferedWriter (new OutputStreamWriter(os));
			
			//receptor
			InputStream is = socket.getInputStream();
			reader = new BufferedReader (new InputStreamReader(is));
			
			while(true) {
				//recibe constantemente
				String line = reader.readLine();
				System.out.println(line);
				
				Gson gson = new Gson();
				Generic generic = gson.fromJson(line, Generic.class);
				//System.out.println(generic.getType());
				
				switch(generic.getType()) {
				case "Nombre":
					Nombre nombreR = gson.fromJson(line, Nombre.class);
					String nombreRecibido = nombreR.getNombre();
					//mandar lo que llega del cliente al main del servidor
					observer.nombreLlegando(nombreRecibido);
					
					break;
					
				case "Movimiento":
					Movimiento mov = gson.fromJson(line, Movimiento.class);
					float xr = mov.getX();
					float yr = mov.getY();
					//mandar lo que llega del cliente al main del servidor
					observer.movimientoLlegando(xr,yr);
					
					break;
				
				case "Color":
					Color col = gson.fromJson(line, Color.class);
					int rr = col.getR();
					int gr = col.getG();
					int br = col.getB();
					//mandar lo que llega del cliente al main del servidor
					observer.colorLlegando(rr,gr,br);
					
					break;
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void enviarMensaje(String msg) {
    	new Thread(
				() -> {
					try {
						writer.write(msg + "\n");
						writer.flush();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			).start();
    	}

}
