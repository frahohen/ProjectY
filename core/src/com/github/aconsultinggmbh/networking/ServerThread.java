package com.github.aconsultinggmbh.networking;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.net.Socket;
import com.github.aconsultinggmbh.networking.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ServerThread implements Runnable{
    private Server server;
    private Socket client;
    private String serverMessage;
    private int id;

    private int serverThreadSize;

    private ArrayList<String> playerList;

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

            objectOutputStream.writeObject("Player"+id);

            ArrayList<String> list = server.updateClients(id);
            objectOutputStream.writeObject(list);
            serverThreadSize = server.getSize();

            //while (true) {
                if(server.getSize() != serverThreadSize) {
                    list = server.updateClients(id);
                    objectOutputStream.writeObject(list);
                }
            //}
        } catch (IOException e) {
            Gdx.app.log("SERVER", "An error occured", e);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
