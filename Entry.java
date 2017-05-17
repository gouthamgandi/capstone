
import java.io.DataInputStream;
import java.util.Random;
import java.io.*;
import java.net.*;


public class Entry
{
 

 public static void main(String argv[]) throws Exception
 {
  String sentence;
  String modifiedSentence;
  
  Socket clientSocket = new Socket("localhost", 9000);
  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
 
  sentence="AF5432EB987D";
  outToServer.writeBytes(sentence + '\n');
  sentence="ABC123456789";
  outToServer.writeBytes(sentence + '\n');
  sentence="Entry";
  outToServer.writeBytes(sentence + '\n');
  sentence="TWO";
  outToServer.writeBytes(sentence + '\n');
  
  clientSocket.close();
 }

}