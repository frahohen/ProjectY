package com.github.aconsultinggmbh.networking.handler;

import com.github.aconsultinggmbh.networking.ServerThread;
import com.github.aconsultinggmbh.networking.message.Message;
import com.github.aconsultinggmbh.networking.message.MessageHashMap;
import com.github.aconsultinggmbh.networking.message.MessageTag;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerSendHandler implements Runnable {

	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private ServerThread serverThread;
	private Message message;
	
	public ServerSendHandler(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream,
			ServerThread serverThread) {
		super();
		this.objectInputStream = objectInputStream;
		this.objectOutputStream = objectOutputStream;
		this.serverThread = serverThread;
	}

	@Override
	public void run() {
		while (true) {
        	// Tick rate of 1 millisecond
        	try {
				Thread.sleep(1);
				
				if(serverThread.getTriggerMessage().equals(MessageTag.CONNECTED)){
	        		// Update Clients with new value
	        		message = new Message(MessageTag.CONNECTED, serverThread.getServer().getNumberOfClientsConnected());
	        		objectOutputStream.writeObject(message);
	        		// Reset triggerMessage
	        		serverThread.setTriggerMessage("");
	        	}
	        	
	        	if(serverThread.getTriggerMessage().equals(MessageTag.START)){
	        		message = new Message(MessageTag.START, true);
	        		objectOutputStream.writeObject(message);
	        		serverThread.setTriggerMessage("");
	        	}
	        	
	        	if(serverThread.getTriggerMessage().equals(MessageTag.PLAYERPOSITION)){
	        		
	        		// DEBUG: --------
					/*
					MapPosition mp = (MapPosition) serverThread.getServer().getPlayerAndPosition().get(serverThread.getId()+"");
					Gdx.app.log("SERVERTHREAD"+serverThread.getId(), mp.getX() +":"+ mp.getY());
					*/
					// DEBUG: --------
	        		
	        		MessageHashMap hashMap = new MessageHashMap();
	        		hashMap.setHashMapMapPositionMessage(serverThread.getServer().getPlayerAndPosition());
	        		message = new Message(MessageTag.PLAYERPOSITION, hashMap);
	        		
	        		// DEBUG: --------
	        		/*
	        		MapPosition mp = (MapPosition) message.getHashmapMessage().get(serverThread.getId()+"");
					Gdx.app.log("SERVERTHREAD"+serverThread.getId(), mp.getX() +":"+ mp.getY());
					*/
					// DEBUG: --------
	        		
	        		objectOutputStream.writeObject(message);
	        		serverThread.setTriggerMessage("");
	        	}
	        	
	        	if(serverThread.getTriggerMessage().equals(MessageTag.PLAYERHEALTH)){

						MessageHashMap hashMap = new MessageHashMap();
						hashMap.setHashMapIntegerMessage(serverThread.getServer().getPlayerAndHealth());
						message = new Message(MessageTag.PLAYERHEALTH, hashMap);

						objectOutputStream.writeObject(message);
						serverThread.setTriggerMessage("");
	        	}
	        	
	        	if(serverThread.getTriggerMessage().equals(MessageTag.ITEMPOSITION)){
	        		
	        		MessageHashMap hashMap = new MessageHashMap();
	        		hashMap.setHashMapMapPositionMessage(serverThread.getServer().getItemAndPosition());
	        		message = new Message(MessageTag.ITEMPOSITION, hashMap);
	        		
	        		objectOutputStream.writeObject(message);
	        		serverThread.setTriggerMessage("");
	        	}
	        	
	        	if(serverThread.getTriggerMessage().equals(MessageTag.ITEMTAKEN)){
	        		MessageHashMap hashMap = new MessageHashMap();
	        		hashMap.setHashMapBooleanMessage(serverThread.getServer().getItemAndTaken());
	        		message = new Message(MessageTag.ITEMTAKEN, hashMap);
	        		
	        		objectOutputStream.writeObject(message);
	        		serverThread.setTriggerMessage("");
	        	}
	        	
	        	if(serverThread.getTriggerMessage().equals(MessageTag.PLAYERGODMODE)){
	        		MessageHashMap hashMap = new MessageHashMap();
	        		hashMap.setHashMapBooleanMessage(serverThread.getServer().getPlayerAndGodMode());
	        		message = new Message(MessageTag.PLAYERGODMODE, hashMap);
	        		
	        		objectOutputStream.writeObject(message);
	        		serverThread.setTriggerMessage("");
	        	}

				if(serverThread.getTriggerMessage().equals(MessageTag.GAMEOVER)){
					MessageHashMap hashMap = new MessageHashMap();
					hashMap.setHashMapBooleanMessage(serverThread.getServer().getPlayerAndGodMode()); ///////
					message = new Message(MessageTag.GAMEOVER, hashMap);

					objectOutputStream.writeObject(message);
					serverThread.setTriggerMessage("");
				}
	        	
	        	if(serverThread.getTriggerMessage().equals(MessageTag.PLAYERBULLETEXIST)){
	        		MessageHashMap hashMap = new MessageHashMap();
	        		hashMap.setHashMapBooleanMessage(serverThread.getServer().getBulletAndExist());
	        		message = new Message(MessageTag.PLAYERBULLETEXIST, hashMap);
	        		
	        		objectOutputStream.writeObject(message);
	        		serverThread.setTriggerMessage("");
	        	}
	        	
	        	if(serverThread.getTriggerMessage().equals(MessageTag.PLAYERBULLETPOSITION)){
	        		
	        		MessageHashMap hashMap = new MessageHashMap();
	        		hashMap.setHashMapMapPositionMessage(serverThread.getServer().getBulletAndPosition());
	        		message = new Message(MessageTag.PLAYERBULLETPOSITION, hashMap);
	        		
	        		objectOutputStream.writeObject(message);
	        		serverThread.setTriggerMessage("");
	        	}
	        	
	        	// Has to be done otherwise the stream gets overloaded
	        	objectOutputStream.reset();
	        	message = null;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        }
	}
	
}
