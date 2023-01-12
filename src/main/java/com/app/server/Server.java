package com.app.server;

import com.app.server.ClientThread;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Server extends Thread {
    private final int PORT = 1234;
    private ServerSocket serverSocket;
    private ClientThread clientHandler;
    private static List<Socket> currentSockets = new ArrayList<>();
    private boolean isStarted;

    public static void removeSocket(Socket socket){
        currentSockets.remove(socket);
        System.out.println("Клиент " + String.valueOf(socket.getInetAddress()).substring(1) + " отключен");
        System.out.println(String.valueOf(socket.getInetAddress()).substring(1));
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            isStarted = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Сервер запущен");
                    try {
                        while (isStarted) {
                            Socket socket = serverSocket.accept();
                            currentSockets.add(socket);
                            System.out.println("Клиент " + String.valueOf(socket.getInetAddress()).substring(1) + " подключен");
                            System.out.println(String.valueOf(socket.getInetAddress()).substring(1));
                            clientHandler = new ClientThread(socket);
                            new Thread(clientHandler).start();
                        }
                    } catch (IOException e) {
                        System.out.println("Сервер остановлен");
                    }
                }
            }).start();
        } catch (IOException e) {
            System.out.println("Ошибка работы сервера");
        }
    }

    public void stopServer() {
        try {
            isStarted = false;
            for (Socket socket : currentSockets){
                if (socket.isClosed()){
                    socket.close();
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Не удалось закрыть потоки сервера");
        }
    }
}