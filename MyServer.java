import java.sql.*;  
import java.io.*;  
import java.net.*;  
import java.sql.Timestamp;

//#############################################################################################################################
public class MyServer {
  
public static void main(String[] args){  
 
try{

//step1 load the driver class  
Class.forName("com.mysql.jdbc.Driver"); 

//step2 create  the connection object  
Connection con=DriverManager.getConnection(  
"jdbc:mysql://localhost:3306/capstone","root","newpassword");  

//step3 create the statement object  
PreparedStatement stmt1=con.prepareStatement("select USERID from USER where RFID =?");
PreparedStatement stmt2=con.prepareStatement("select COST from AREA where NAME =?");
PreparedStatement stmt3=con.prepareStatement("select * from ?");
PreparedStatement stmt4=con.prepareStatement("insert into ? (TYPE, AREA, TIMEIN)values(? ? ? ) ");
PreparedStatement stmt5=con.prepareStatement("update ? set TIMEOUT = ? where Flag = 0");
PreparedStatement stmt6=con.prepareStatement("select TIMESTAMPDIFF(MINUTE, TIMEIN, TIMEOUT) from ? where Flag=0");
PreparedStatement stmt7=con.prepareStatement("update ? set cost = ?,Flag=1 where Flag = 0"); 
//#################################################################################################################################

String rfid,name,type,user, cost;
int i,j,z=0;

ServerSocket ss=new ServerSocket(3333);
while(z==0)
{
Socket s=ss.accept();//establishes connection   

 
BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); 
rfid = in.readLine();
name = in.readLine();
type = in.readLine();
System.out.println("Data= "+rfid+" "+name+" "+type);

stmt1.setString(1,rfid);
ResultSet rs=stmt1.executeQuery();
user=rs.getString(1);
System.out.println("USER ID :"+user);

if(type.equals("Entry"))
{
stmt4.setString(1,user);
stmt4.setString(2,"Parking");
stmt4.setString(3,name);
stmt4.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
i = stmt4.executeUpdate();
}

if(type.equals("Exit"))
{
stmt5.setString(1,user);
stmt5.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
stmt5.setString(3,name);
i = stmt5.executeUpdate();
stmt6.setString(1,user);
rs=stmt6.executeQuery();
i=rs.getInt(1);
stmt2.setString(1,name);
rs=stmt2.executeQuery();
j=rs.getInt(1);
stmt7.setString(1,user);
stmt5.setInt(2,j*i);
i=stmt5.executeUpdate();
}
s.close(); 
}
//###############################################################################################################################
//never executes
//step5 close the connection object  
con.close();  
 

ss.close();  
}catch(Exception e){System.out.println(e);}
}
}
  
 

 

  

  
