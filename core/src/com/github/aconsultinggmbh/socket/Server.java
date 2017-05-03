package com.github.aconsultinggmbh.socket;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

public class Server implements Runnable{

    private String message;

    @Override
    public void run() {
        ServerSocketHints serverSocketHints = new ServerSocketHints();
        serverSocketHints.acceptTimeout = 0;
        ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 10000, serverSocketHints); //socket aufbauen, timeout bei 0

        Gdx.app.log("DEBUG","SERVER: Server created on " + serverSocket.getProtocol().name());

        while (true){
            Socket socket = serverSocket.accept(null); //Waiting for connection
            Gdx.app.log("DEBUG","SERVER: Client connected!");
            ClientThread clientThread = new ClientThread(socket);
            clientThread.run();
        }
    }
}
