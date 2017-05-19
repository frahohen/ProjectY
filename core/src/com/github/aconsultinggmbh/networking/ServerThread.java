package com.github.aconsultinggmbh.networking;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.Socket;
import com.github.aconsultinggmbh.networking.message.Message;

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

    public ServerThread(Server server, Socket client, int id) {
        this.server = server;
        this.client = client;
        this.id = id;
    }

    @Override
    public void run() {

        try {
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectInputStream = new ObjectInputStream(client.getInputStream());

            Message message;
            while (true) {
                message = (Message) objectInputStream.readObject();
                Gdx.app.log("SERVER", "Client " + id + " says: " + message.getMessage() + " -- Thread-Size: " + server.getThreadSize());
            }
        } catch (IOException e) {
            Gdx.app.log("SERVER", "An error occured", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
