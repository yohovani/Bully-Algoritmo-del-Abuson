/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bully;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yohov
 */
public class Proceso extends Thread{
	private final int puerto=55400;
	private final String group = "225.3.4.5";
	private MulticastSocket ms;
	private ArrayList<Integer> Ids,Mensajes;
	private ArrayList<Boolean> estados;
	private ArrayList<String> mens;
	private boolean envio;
	private int Id,aux;
	private String mensaje;
	private int coord[];

	public Proceso(ArrayList<Integer> Ids, int Id,ArrayList<Boolean> est, int[] c,ArrayList<Integer> m,ArrayList<String> me) {
		try {
			this.coord = c;
			this.envio = false;
			this.Ids = Ids;
			this.estados = est;
			this.Id = Id;
			ms = new MulticastSocket(puerto);
			ms.joinGroup(InetAddress.getByName(group));
			mensaje="";
			this.Mensajes = m;
			this.mens = me;
		} catch (UnknownHostException ex) {
			System.out.println(ex.getMessage());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public Proceso() {
		try {
			ms = new MulticastSocket(puerto);
			ms.joinGroup(InetAddress.getByName(group));
		} catch (IOException ex) {
			Logger.getLogger(Proceso.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void enviar(String mensaje){
		try {
			DatagramPacket paqueteEnviar;
			String m = Id+","+mensaje;
			if(mensaje.equals("1")){
				this.mens.add("Proceso: "+Id+" ha iniciado un proceso de eleccion");
				System.out.println(this.mens.get(mens.size()-1));
				
			}
			byte buffer[] = m.getBytes();
			paqueteEnviar = new DatagramPacket(buffer,buffer.length,InetAddress.getByName(group),puerto);
			ms.send(paqueteEnviar);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void recibirRespuesta(){
		Runnable r = new Runnable() {
			@Override
			public void run() {
				w:while(true){
					
					try {
						Thread.sleep(600);
						DatagramPacket paqueteRecibir;
						byte buffer[] = new byte[1024];
						paqueteRecibir = new DatagramPacket(buffer,buffer.length);
						ms.receive(paqueteRecibir);
						String recibe = new String(buffer);
						mensaje=recibe;
						String tipo[] = mensaje.split(",");
						try{
							int numP = Integer.parseInt(tipo[0]);
							int numM = (int) Double.parseDouble(tipo[1]);
						if((coord[1] <= Id) && coord[0]==-1){
							switch(numM){
								case 1:{
									if(Id > coord[1]){
										synchronized(Mensajes){
											int aux = Mensajes.get(coord[1]);
											aux+=1;
											Mensajes.set(coord[1], aux);
										}
										mens.add("Proceso "+Id+": Ok");
										System.out.println(mens.get(mens.size()-1));
										enviar("2");
									}
									break;
								}
								case 2:{
									if(Id < Ids.get(Ids.size()-1) && Id == coord[1] && envio){
										if(Mensajes.get(coord[1]) >= ((Ids.size()-1)-Id)){
											coord[1]++;
											mens.add("-> -> Proceso "+Id+": Me retiro de la elección");
											System.out.println(mens.get(mens.size()-1));
										}

									}else{
										if(Id < Ids.get(Ids.size()-1) && coord[1] == Id){
											envio = true;
											enviar("1");
										}else{
											enviar("3");
										}
									}
									break;
								}
								case 3:{
									if((Id == Ids.get(Ids.size()-1)) && (coord[1] == Id)){
										coord[0]=Id;
										enviar("3");
										mens.add("Proceso "+Id+": Soy el cordinador");
										System.out.println(mens.get(mens.size()-1));
									}
									break;
								}
							}
							
						}
						}catch(NumberFormatException ex){ // handle your exception
							System.out.println(ex.getMessage());
						}
					} catch (SocketException ex) {
						System.out.println(ex.getMessage());
					} catch (IOException ex) {
						System.out.println(ex.getMessage());
					} catch (InterruptedException ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}	

	@Override
	public void run() {
		recibirRespuesta();
	}
	
	public void coordinador(){
		this.envio = true;
		this.coord[0] = -1;
		this.coord[1] = Id;
		for(int i=0;i<this.Mensajes.size();i++){
			this.Mensajes.set(i, 0);
		}
		if(this.Id == this.Ids.get(Ids.size()-1)){
			abusivo();
		}else{
			enviar("1");
		}

	}
	
	public void abusivo(){
		this.envio = true;
		this.coord[0] = Id;
		this.coord[1] = Id;
		this.mens.add("Proceso "+Id+": Soy el nuevo Coordinador");
		System.out.println(mens.get(mens.size()-1));
	}
	
}
