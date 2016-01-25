package robot

import java.io.PrintWriter
import java.net.Socket
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io._
import java.net.InetSocketAddress


class SocketManager {
  
  def sendMessage[T](toAddr: InetSocketAddress, i: T) {
    var clientSocket = new Socket();
    try {
      clientSocket.connect(toAddr);
      var pw = new PrintWriter(clientSocket.getOutputStream());
      pw.print(i);
      pw.flush();
      pw.close();
      clientSocket.close();
    } catch {
      case e: Throwable => {
        println(s"$e: Error while send message to server with ip = ${toAddr.getAddress} and port = ${toAddr.getPort}.")
      }
    } finally {
      clientSocket.close();
    }
  }

  
  def receiveMessage(toAddr: InetSocketAddress): String = {
    var clientSocket = new Socket();
    var result = ""
    try {
      clientSocket.connect(toAddr);
      var br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
      var str = br.readLine()
      while (str != null) {
        result += str
        str = br.readLine()
      }
      br.close();
      clientSocket.close();
    } catch {
      case e: Throwable => {
        println(s"$e: Error while receive message to server with ip = ${toAddr.getAddress} and port = ${toAddr.getPort}.")
      }
    } finally {
      clientSocket.close();
    }
    return result
  }
  
}