package com.kaavie.socket.server;

import com.kaavie.socket.server.entity.Request;
import com.kaavie.socket.server.entity.Response;

import java.io.*;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kaavie on 2017/10/26.
 */
public class MyHttpServer {
    public static final String WEB_ROOT = System.getProperty("user.dir")+ File.separator+"src" + File.separator +"main"+ File.separator +"webapp"  + File.separator + "webroot";


    public static void main(String[] args) {
        MyHttpServer httpServer = new MyHttpServer();
        httpServer.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080, 1, Inet4Address.getByName("127.0.0.1"));
            while (true) {
                Socket socket = serverSocket.accept();
                if (socket != null && socket.isConnected() && !socket.isClosed()) {
                    Request request   = new Request(socket.getInputStream());
                    request.parse();
                    String url=request.getUri();
                    if(url!=null&&url.trim().length()>0&&url.startsWith("/servlet")){


                    }

                    Response response = new Response(socket.getOutputStream());
                    response.setRequest(request);
                    response.sendStaticResource();
                    socket.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
