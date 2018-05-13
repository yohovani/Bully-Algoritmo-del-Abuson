/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bully;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author yohov
 */
public class Bully {

	/**1
	 * 
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		ArrayList<Integer> Ids = new ArrayList();
		ArrayList<Boolean> Estados = new ArrayList();
		ArrayList<Integer> Mensajes = new ArrayList();
		ArrayList<String> m = new ArrayList();
		Ids.add(0);
		Ids.add(1);
		Ids.add(2);
		Ids.add(3);
		Ids.add(4);
		Ids.add(5);
		Ids.add(6);
		Ids.add(7);
		Mensajes.add(0);
		Mensajes.add(0);
		Mensajes.add(0);
		Mensajes.add(0);
		Mensajes.add(0);
		Mensajes.add(0);
		Mensajes.add(0);
		Mensajes.add(0);
		Estados.add(true);
		Estados.add(true);
		Estados.add(true);
		Estados.add(true);
		Estados.add(true);
		Estados.add(true);
		Estados.add(true);
		Estados.add(true);
		int coord[] = {-1,0};
		Proceso p1 = new Proceso(Ids,0,Estados,coord,Mensajes,m);
		Proceso p2 = new Proceso(Ids,1,Estados,coord,Mensajes,m);
		Proceso p3 = new Proceso(Ids,2,Estados,coord,Mensajes,m);
		Proceso p4 = new Proceso(Ids,3,Estados,coord,Mensajes,m);
		Proceso p5 = new Proceso(Ids,4,Estados,coord,Mensajes,m);
		Proceso p6 = new Proceso(Ids,5,Estados,coord,Mensajes,m);
		Proceso p7 = new Proceso(Ids,6,Estados,coord,Mensajes,m);
		Proceso p8 = new Proceso(Ids,7,Estados,coord,Mensajes,m);
		
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
		p6.start();
		p7.start();
		p8.start();
		
		Scanner reader = new Scanner(System.in);
		String aux = "";
		while(!aux.equals("fin")){
			System.out.println("Enviar Mensaje?");
			aux = reader.nextLine();
			if(aux.equals("enviar")){
				p1.coordinador();
			}
			//p1.enviar(aux);
			
			
		}
	}
	
}
