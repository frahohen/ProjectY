package com.github.aconsultinggmbh.multiplayer.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;


public class ClientProgram extends Listener implements Runnable{

	//Our client object.
	private Client client;
	//IP to connect to.
	private String ip = "localhost";
	//Ports to connect on.
	private int tcpPort = 27960, udpPort = 27960;
	
	//A boolean value.
	private boolean messageReceived = false;
	
	//I'm only going to implement this method from Listener.class because I only need to use this one.
	public void received(Connection c, Object p){
		//Is the received packet the same class as PacketMessage.class?
		if(p instanceof PacketMessage){
			//Cast it, so we can access the message within.
			PacketMessage packet = (PacketMessage) p;
			Gdx.app.log("CLIENT","received a message from the host: "+packet.message);
			
			//We have now received the message!
			messageReceived = true;
		}
	}

	@Override
	public void run() {
		Gdx.app.log("CLIENT","Connecting to the server...");
		//Create the client.
		client = new Client();

		//Register the packet object.
		client.getKryo().register(PacketMessage.class);

		//Start the client
		client.start();
		//The client MUST be started before connecting can take place.

		//Connect to the server - wait 5000ms before failing.
		try {
			client.connect(5000, ip, tcpPort, udpPort);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Add a listener
		client.addListener(new ClientProgram());

		Gdx.app.log("CLIENT","Connected! The client program is now waiting for a packet...\n");

		//This is here to stop the program from closing before we receive a message.
		while(!messageReceived){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Gdx.app.log("CLIENT","Client will now exit.");
		//System.exit(0);
	}
}
