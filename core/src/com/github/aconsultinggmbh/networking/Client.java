package com.github.aconsultinggmbh.networking;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.github.aconsultinggmbh.networking.handler.ClientReceiveHandler;
import com.github.aconsultinggmbh.networking.handler.ClientSendHandler;
import com.github.aconsultinggmbh.networking.message.Message;
import com.github.aconsultinggmbh.networking.message.MessageTag;
import com.github.aconsultinggmbh.point.MapPosition;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Client implements Runnable {
    private String ip;
    private int port;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    
    private Message message;
    
    private int numberOfClientsConnected;
    private boolean startGame;
    
    private HashMap<String, MapPosition> playerAndPosition;
    private HashMap<String, Integer> playerAndHealth;
    private HashMap<String, MapPosition> itemAndPosition;
    private HashMap<String, Boolean> itemAndTaken;
    private HashMap<String, Boolean> playerAndGodMode;
	private HashMap<String, Boolean> playerAndGameover;
    private HashMap<String, MapPosition> bulletAndPosition;
    private HashMap<String, Boolean> bulletAndExist;
    private int id;
    
    private ClientSendHandler clientSendHandler;
    private ClientReceiveHandler clientReceiveHandler;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        startGame = false;
        playerAndPosition = new HashMap<String, MapPosition>();
        playerAndHealth = new HashMap<String, Integer>();
        itemAndPosition = new HashMap<String, MapPosition>();
        itemAndTaken = new HashMap<String, Boolean>();
        playerAndGodMode = new HashMap<String, Boolean>();
		playerAndGameover = new HashMap<String, Boolean>();
        bulletAndPosition = new HashMap<String, MapPosition>();
        bulletAndExist = new HashMap<String, Boolean>();
    }

    @Override
    public void run() {
        SocketHints hints = new SocketHints();
        Socket client = Gdx.net.newClientSocket(Net.Protocol.TCP, ip, port, hints);

        try {
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectInputStream = new ObjectInputStream(client.getInputStream());
            
            // Connected
            message = new Message(MessageTag.CONNECTED);
            objectOutputStream.writeObject(message);
            
            // Communication Loop
            clientReceiveHandler = new ClientReceiveHandler(objectInputStream, objectOutputStream, this);
            clientSendHandler = new ClientSendHandler(objectInputStream, objectOutputStream, this);
            
            new Thread(clientSendHandler).start();
            new Thread(clientReceiveHandler).start();
            
        } catch (IOException e) {
            Gdx.app.log("CLIENT", "An error occured", e);
        } 

    }
    
	public synchronized ClientSendHandler getClientSendHandler() {
		return clientSendHandler;
	}

	public synchronized void setClientSendHandler(ClientSendHandler clientSendReceiveHandler) {
		this.clientSendHandler = clientSendReceiveHandler;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public synchronized HashMap<String, Integer> getPlayerAndHealth() {
		return playerAndHealth;
	}

	public synchronized void setPlayerAndHealth(HashMap<String, Integer> playerAndHealth) {
		this.playerAndHealth = playerAndHealth;
	}

	public synchronized HashMap<String, MapPosition> getPlayerAndPosition() {
		return playerAndPosition;
	}

	public synchronized void setPlayerAndPosition(HashMap<String, MapPosition> playerAndPosition) {
		this.playerAndPosition = playerAndPosition;
	}
	
	public synchronized HashMap<String, MapPosition> getItemAndPosition() {
		return itemAndPosition;
	}

	public synchronized void setItemAndPosition(HashMap<String, MapPosition> itemAndPosition) {
		this.itemAndPosition = itemAndPosition;
	}

	public synchronized HashMap<String, Boolean> getItemAndTaken() {
		return itemAndTaken;
	}

	public synchronized void setItemAndTaken(HashMap<String, Boolean> itemAndTaken) {
		this.itemAndTaken = itemAndTaken;
	}

	public synchronized HashMap<String, Boolean> getPlayerAndGodMode() {
		return playerAndGodMode;
	}

	public synchronized void setPlayerAndGodMode(HashMap<String, Boolean> playerAndGodMode) {
		this.playerAndGodMode = playerAndGodMode;
	}

	public synchronized HashMap<String, Boolean> getPlayerAndGameover() {
		return playerAndGameover;
	}

	public synchronized void setPlayerAndGameover(HashMap<String, Boolean> playerAndGameover) {
		this.playerAndGameover = playerAndGameover;
	}

	public synchronized HashMap<String, MapPosition> getBulletAndPosition() {
		return bulletAndPosition;
	}

	public synchronized void setBulletAndPosition(HashMap<String, MapPosition> bulletAndPosition) {
		this.bulletAndPosition = bulletAndPosition;
	}

	public synchronized HashMap<String, Boolean> getBulletAndExist() {
		return bulletAndExist;
	}

	public synchronized void setBulletAndExist(HashMap<String, Boolean> bulletAndExist) {
		this.bulletAndExist = bulletAndExist;
	}

	public boolean isStartGame() {
		return startGame;
	}

	public void setStartGame(boolean startGame) {
		this.startGame = startGame;
	}

	public int getNumberOfClientsConnected() {
		return numberOfClientsConnected;
	}

	public void setNumberOfClientsConnected(int numberOfClientsConnected) {
		this.numberOfClientsConnected = numberOfClientsConnected;
	}
}
