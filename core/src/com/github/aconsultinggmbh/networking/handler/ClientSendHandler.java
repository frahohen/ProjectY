package com.github.aconsultinggmbh.networking.handler;

import com.github.aconsultinggmbh.networking.Client;
import com.github.aconsultinggmbh.networking.message.Message;
import com.github.aconsultinggmbh.networking.message.MessageTag;
import com.github.aconsultinggmbh.point.MapPosition;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientSendHandler implements Runnable {

	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private Client client;
	private Message message;
	private String triggerMessage;
	private MapPosition mapPositionMessage;
	private String stringMessage;
	private Boolean booleanMessage;
	
	public ClientSendHandler(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream,
			Client client) {
		super();
		this.objectInputStream = objectInputStream;
		this.objectOutputStream = objectOutputStream;
		this.client = client;
		this.triggerMessage = "";
		this.stringMessage = "";
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Thread.sleep(10);
				
				if(triggerMessage.equals(MessageTag.PLAYERPOSITION)){
					//Gdx.app.log("Debug", "I am sending");
					message = new Message(MessageTag.PLAYERPOSITION, mapPositionMessage);
					objectOutputStream.writeObject(message);
					triggerMessage = "";
				}
				
				if(triggerMessage.equals(MessageTag.PLAYERHEALTH)){
					message = new Message(MessageTag.PLAYERHEALTH, stringMessage);
					objectOutputStream.writeObject(message);
					triggerMessage = "";
				}

				/*if(triggerMessage.equals(MessageTag.PLAYERSCORE)){
					message = new Message(MessageTag.PLAYERSCORE, stringMessage);
					objectOutputStream.writeObject(message);
					triggerMessage = "";
				}*/
				
				if(triggerMessage.equals(MessageTag.ITEMTAKEN)){
					message = new Message(MessageTag.ITEMTAKEN, stringMessage);
					objectOutputStream.writeObject(message);
					triggerMessage = "";
				}
				
				if(triggerMessage.equals(MessageTag.PLAYERGODMODE)){
					message = new Message(MessageTag.PLAYERGODMODE, booleanMessage);
					objectOutputStream.writeObject(message);
					triggerMessage = "";
				}
				
				if(triggerMessage.equals(MessageTag.PLAYERBULLETEXIST)){
					message = new Message(MessageTag.PLAYERBULLETEXIST, stringMessage);
					objectOutputStream.writeObject(message);
					triggerMessage = "";
				}
				
				if(triggerMessage.equals(MessageTag.PLAYERBULLETPOSITION)){
					//Gdx.app.log("Debug", "I am sending");
					message = new Message(MessageTag.PLAYERBULLETPOSITION, stringMessage);
					objectOutputStream.writeObject(message);
					triggerMessage = "";
				}
				
				// Has to be done otherwise the stream gets overloaded
				objectOutputStream.reset();
				message = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized void updateClients(String labelMessage, MapPosition mapPositionMessage){
		//Gdx.app.log("DEBUG", labelMessage);
    	this.mapPositionMessage = mapPositionMessage;
    	this.triggerMessage = labelMessage;
    }
	
	public synchronized void updateClients(String labelMessage, String stringMessage){
    	this.stringMessage = stringMessage;
    	this.triggerMessage = labelMessage;
    }
	
	public synchronized void updateClients(String labelMessage, Boolean booleanMessage){
		this.booleanMessage = booleanMessage;
		this.triggerMessage = labelMessage;
	}
}
