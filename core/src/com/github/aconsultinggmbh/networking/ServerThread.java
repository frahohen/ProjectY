package com.github.aconsultinggmbh.networking;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.Socket;
import com.github.aconsultinggmbh.networking.handler.ServerReceiveHandler;
import com.github.aconsultinggmbh.networking.handler.ServerSendHandler;
import com.github.aconsultinggmbh.networking.message.Message;
import com.github.aconsultinggmbh.networking.message.MessageTag;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerThread implements Runnable{
    private Server server;
    private Socket client;
    private String serverMessage;
    private int id;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    
    private Message message;
    private String triggerMessage;
    
    private ServerReceiveHandler serverReceiveHandler;
    private ServerSendHandler serverSendHandler;

    public ServerThread(Server server, Socket client, int id) {
        this.server = server;
        this.client = client;
        this.id = id;
        triggerMessage = "";
    }

    @Override
    public void run() {

        try {
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectInputStream = new ObjectInputStream(client.getInputStream());

            // Get answer from client "Connected" as boolean
            message = (Message) objectInputStream.readObject();
            
            if(message.getLabelMessage().equals(MessageTag.CONNECTED)){
            	// Update numberOfClientsConnected
            	int number = server.getNumberOfClientsConnected();
            	server.setNumberOfClientsConnected(++number);
            	
            	// Assign Client id
            	Message messageID = new Message(MessageTag.ID, id);
            	objectOutputStream.writeObject(messageID);
            	
            	// Update every Client
            	server.updateClients(MessageTag.CONNECTED);
            }
            //Gdx.app.log("SERVER", "Client " + id + " says: " + message.isBooleanMessage() + " -- Thread-Size: " + server.getThreadSize());
            
            // Communication Loop
            serverReceiveHandler = new ServerReceiveHandler(objectInputStream, objectOutputStream, this);
            serverSendHandler = new ServerSendHandler(objectInputStream, objectOutputStream, this);
            
            new Thread(serverReceiveHandler).start();
            new Thread(serverSendHandler).start();
            
        } catch (IOException e) {
        	Gdx.app.log("SERVER", "Waiting");
            //Gdx.app.log("SERVER", "An error occured", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
    }
    
	public synchronized Server getServer() {
		return server;
	}

	public synchronized void setServer(Server server) {
		this.server = server;
	}

	public String getTriggerMessage() {
		return triggerMessage;
	}

	public void setTriggerMessage(String triggerMessage) {
		this.triggerMessage = triggerMessage;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
