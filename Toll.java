
import java.io.DataInputStream;
import java.util.Random;
import java.io.*;
import java.net.*;


public class Toll
{
 

 public static void main(String argv[]) throws Exception
 {
  String sentence;
  String modifiedSentence;
  
  Socket clientSocket = new Socket("localhost", 9000);
  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
 
  sentence="AF5432EB987D";
  outToServer.writeBytes(sentence + '\n');
  sentence="XYZ123456789";
  outToServer.writeBytes(sentence + '\n');
  sentence="Toll";
  outToServer.writeBytes(sentence + '\n');
  sentence="FOUR";
  outToServer.writeBytes(sentence + '\n');
  
  clientSocket.close();
 }

}