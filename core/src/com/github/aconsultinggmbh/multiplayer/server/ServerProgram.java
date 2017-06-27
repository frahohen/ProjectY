package com.github.aconsultinggmbh.multiplayer.server;

import java.io.IOException;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;


public class ServerProgram extends Listener implements Runnable {

	//Server object
	private Server server;
	//Ports to listen on
	private int udpPort = 27960, tcpPort = 27960;
	
	//This is run when a connection is received!
	public void connected(Connection c){
		Gdx.app.log("SERVER","Received a connection from "+c.getRemoteAddressTCP().getHostName());
		//Create a message packet.
		PacketMessage packetMessage = new PacketMessage();
		//Assign the message text.
		packetMessage.message = "Hello friend! The time is: "+new Date().toString();
		
		//Send the message
		c.sendTCP(packetMessage);
		//Alternatively, we could do:
		//c.sendUDP(packetMessage);
		//To send over UDP.
	}
	
	//This is run when we receive a packet.
	public void received(Connection c, Object p){
		//We will do nothing here.
		//We do not expect to receive any packets.
		//(But if we did, nothing would happen)
	}
	
	//This is run when a client has disconnected.
	public void disconnected(Connection c){
		Gdx.app.log("SERVER", "A client disconnected!");
	}

	@Override
	public void run() {
		Gdx.app.log("SERVER", "Creating the server...");
		//Create the server
		server = new Server();

		//Register a packet class.
		server.getKryo().register(PacketMessage.class);
		//We can only send objects as packets if they are registered.

		//Bind to a port
		try {
			server.bind(tcpPort, udpPort);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Start the server
		server.start();

		//Add the listener
		server.addListener(new ServerProgram());

		Gdx.app.log("SERVER", "Server is operational!");
	}
}
