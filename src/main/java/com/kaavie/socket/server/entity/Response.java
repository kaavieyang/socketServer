package com.kaavie.socket.server.entity;

import com.kaavie.socket.server.MyHttpServer;

import java.io.*;

/**
 * Created by kaavie on 2017/10/26.
 */
public class Response {
    private static final int BUFFER_SIZE = 1024;
    private Request request;
    private OutputStream output;

    public Response(OutputStream outputStream) {

        this.output = outputStream;
    }

    public static void parse() {


    }

    public void setRequest(Request request) {

        this.request = request;
    }


    public void sendStaticResource() throws IOException {

        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            File file = new File(MyHttpServer.WEB_ROOT, request.getUri());
            if (file.exists()) {
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                String errorMessage = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: application/json\r\n" +
                        "Content-Length: "+  file.length()+"\r\n" +
                        "\r\n" ;
                output.write( errorMessage.getBytes());

                while (ch != -1) {
                    output.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
            } else {
// file not found
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());

            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (fis != null) {
                fis.close();

            }
        }
    }
}
