package com.kaavie.socket.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by kaavie on 2017/10/26.
 */
public class SocketServer implements Runnable {
    @Override
    public void run() {

        try {
            ServerSocket serverSocket = new ServerSocket(22881);

            while (true) {

                Socket socket = serverSocket.accept();
                if(socket!=null&&socket.isConnected()&&!socket.isClosed()){
//3、获取输入流，并读取客户端信息
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String info = null;
                while ((info = br.readLine()) != null) {
                    System.out.println("我是服务器，客户端说：" + info);

                }
                socket.shutdownInput();//关闭输入流
                //4、获取输出流，响应客户端的请求
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                pw.write("欢迎您！");
                pw.flush();

                //5、关闭资源
                pw.close();
                os.close();
                br.close();
                isr.close();
                is.close();
                socket.close();
// serverSocket.close();
                    Thread.sleep(1000);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

        }


    }

    public static void main(String[] args) {
        int poolSize = 5;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 5,
                5L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
     //   for (int i = 0; i < poolSize; i++) {
            threadPoolExecutor.execute(new SocketServer());
      //  }


    }
}
