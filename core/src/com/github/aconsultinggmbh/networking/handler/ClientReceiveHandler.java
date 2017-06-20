package com.github.aconsultinggmbh.networking.handler;

import com.badlogic.gdx.Gdx;
import com.github.aconsultinggmbh.networking.Client;
import com.github.aconsultinggmbh.networking.message.Message;
import com.github.aconsultinggmbh.networking.message.MessageTag;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientReceiveHandler implements Runnable{

	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private Client client;
	
	private Message message;
	
	public ClientReceiveHandler(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream,
			Client client) {
		super();
		this.objectInputStream = objectInputStream;
		this.objectOutputStream = objectOutputStream;
		this.client = client;
	}

	@Override
	public void run() {
		while (true) {
        	try {
        		Thread.sleep(1);
        		
				message = (Message)objectInputStream.readObject();
				
				if( message != null){
					
					if(message.getLabelMessage().equals(MessageTag.ID)){
						Gdx.app.log("DEBUG", message.getLabelMessage() + ":" + message.getIntMessage());
						client.setId(message.getIntMessage());
					}
					
					if(message.getLabelMessage().equals(MessageTag.CONNECTED)){
						Gdx.app.log("DEBUG", message.getLabelMessage() + ":" + message.getIntMessage());
						client.setNumberOfClientsConnected(message.getIntMessage());
					}
					
					if(message.getLabelMessage().equals(MessageTag.START)){
						Gdx.app.log("DEBUG", message.getLabelMessage() + ":" + message.isBooleanMessage());
						client.setStartGame(message.isBooleanMessage());
					}
					
					if(message.getLabelMessage().equals(MessageTag.PLAYERPOSITION)){
						
						// DEBUG -------
						/*
						for(int i = 0; i < message.getHashmapMessage().size(); i++) {
							MapPosition mp = message.getHashmapMessage().get(i+"");
							
							Gdx.app.log("Player"+i, mp.getX() + ":" +mp.getY());
						}
						*/
						// DEBUG -------
						
						client.setPlayerAndPosition(message.getMessageHashMap().getHashMapMapPositionMessage());
					}
					
					if(message.getLabelMessage().equals(MessageTag.PLAYERHEALTH)){
						client.setPlayerAndHealth(message.getMessageHashMap().getHashMapIntegerMessage());
					}
					
					if(message.getLabelMessage().equals(MessageTag.ITEMPOSITION)){
						client.setItemAndPosition(message.getMessageHashMap().getHashMapMapPositionMessage());
					}
					
					if(message.getLabelMessage().equals(MessageTag.ITEMTAKEN)){
						client.setItemAndTaken(message.getMessageHashMap().getHashMapBooleanMessage());
					}
					
					if(message.getLabelMessage().equals(MessageTag.PLAYERGODMODE)){
						client.setPlayerAndGodMode(message.getMessageHashMap().getHashMapBooleanMessage());
					}

					if(message.getLabelMessage().equals(MessageTag.GAMEOVER)){
						client.setPlayerAndGameover(message.getMessageHashMap().getHashMapBooleanMessage());
					}
					
					if(message.getLabelMessage().equals(MessageTag.PLAYERBULLETEXIST)){
						client.setBulletAndExist(message.getMessageHashMap().getHashMapBooleanMessage());
						
						/*
						for (Map.Entry<String, Boolean> entry : client.getBulletAndExist().entrySet()) {
				    	    String key = entry.getKey();
				    	    boolean value = entry.getValue();
				    	    
				    	    Gdx.app.log("DEBUG", key + "!" + value);
						}
						*/
					}
					
					if(message.getLabelMessage().equals(MessageTag.PLAYERBULLETPOSITION)){
						client.setBulletAndPosition(message.getMessageHashMap().getHashMapMapPositionMessage());
						
						/*
						for (Map.Entry<String, MapPosition> entry : client.getBulletAndPosition().entrySet()) {
				    	    String key = entry.getKey();
				    	    MapPosition value = entry.getValue();
				    	    
				    	    Gdx.app.log("DEBUG", key + "!" + value.getX() + ":" + value.getY());
						}
						*/
					}
				}
				
				message = null;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}

}
