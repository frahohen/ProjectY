package com.github.aconsultinggmbh.networking.handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.Gdx;
import com.github.aconsultinggmbh.networking.ServerThread;
import com.github.aconsultinggmbh.networking.message.Message;
import com.github.aconsultinggmbh.networking.message.MessageTag;
import com.github.aconsultinggmbh.point.MapPosition;

public class ServerReceiveHandler implements Runnable {

	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private ServerThread serverThread;
	private Message message;
	
	public ServerReceiveHandler(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream,
			ServerThread serverThread) {
		super();
		this.objectInputStream = objectInputStream;
		this.objectOutputStream = objectOutputStream;
		this.serverThread = serverThread;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(1);
				
				message = (Message) objectInputStream.readObject();
				if( message != null){
					if(message.getLabelMessage().equals(MessageTag.PLAYERPOSITION)){
						//Gdx.app.log("SERVERTHREAD", message.getLabelMessage() + ":" + message.getPointPosition().getX() + ":" + message.getPointPosition().getY());
						serverThread.getServer().getPlayerAndPosition().put(serverThread.getId()+"",message.getMapPosition());
						
						// DEBUG: --------
						/*
						MapPosition mp = (MapPosition) serverThread.getServer().getPlayerAndPosition().get(serverThread.getId()+"");
						Gdx.app.log("SERVERTHREAD"+serverThread.getId(), mp.getX() +":"+ mp.getY());
						*/
						// DEBUG: --------
						
			    		serverThread.getServer().updateClients(message.getLabelMessage());
			    	}
					
					if(message.getLabelMessage().equals(MessageTag.PLAYERHEALTH)){
						String stringMessage = message.getStringMessage();
						String[] stringArray = stringMessage.split(":");
						
						serverThread.getServer().getPlayerAndHealth().put(stringArray[0], Integer.parseInt(stringArray[1]));
						//Gdx.app.log("DEBUG", stringArray[0]+":"+stringArray[1]);
						
						serverThread.getServer().updateClients(message.getLabelMessage());
					}
					
					if(message.getLabelMessage().equals(MessageTag.ITEMTAKEN)){
						serverThread.getServer().getItemAndTaken().put(message.getStringMessage(), true);
						serverThread.getServer().updateClients(message.getLabelMessage());
					}
					
					if(message.getLabelMessage().equals(MessageTag.PLAYERGODMODE)){
						serverThread.getServer().getPlayerAndGodMode().put(serverThread.getId()+"", message.isBooleanMessage());
						serverThread.getServer().updateClients(message.getLabelMessage());
					}
					
					if(message.getLabelMessage().equals(MessageTag.PLAYERBULLETEXIST)){
						String stringMessage = message.getStringMessage();
						String[] stringArray = stringMessage.split("!");
						
						
						//Gdx.app.log("DEBUG", stringMessage);
						if(stringArray[1].equals("true")){
							serverThread.getServer().getBulletAndExist().put(stringArray[0],true);
						}
						if(stringArray[1].equals("false")){
							serverThread.getServer().getBulletAndExist().put(stringArray[0],false);
						}
						serverThread.getServer().updateClients(message.getLabelMessage());
					}
					
					if(message.getLabelMessage().equals(MessageTag.PLAYERBULLETPOSITION)){
						String stringMessage = message.getStringMessage();
						String[] stringArrayOne = stringMessage.split("!");
						String[] stringArrayTwo = stringArrayOne[1].split(":");
						
						//Gdx.app.log("DEBUG", stringMessage);
						serverThread.getServer().getBulletAndPosition().put(
								stringArrayOne[0], 
								new MapPosition(
										Float.parseFloat(stringArrayTwo[0]), 
										Float.parseFloat(stringArrayTwo[1])
										)
								);
						
						serverThread.getServer().updateClients(message.getLabelMessage());
					}
				}
				
				message = null;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

}
