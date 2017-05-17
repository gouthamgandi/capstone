import java.sql.*;  
import java.io.*;  
import java.net.*;  
import java.sql.Timestamp;
import java.util.Date;



public class Final {
  
	public static void main(String[] args){  
               
		 tableName;
 
		try{
 
			Class.forName("com.mysql.jdbc.Driver"); 

			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Project","root","newpassword");  


			PreparedStatement getUserID=con.prepareStatement("select USERID from USER where RFID = ?");
			
			String rfid,area,type,user,mode,tableName;
 
                        int i,j,z=0;

			ServerSocket ss=new ServerSocket(9000);
			while(z==0)
				{
			Socket s=ss.accept();//establishes connection   

 
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream())); 
				rfid =in.readLine();
				area= in.readLine();
				type = in.readLine();
				mode = in.readLine();
				System.out.println("Data= "+rfid+" "+area +" "+type);

				getUserID.setString(1,rfid);
				ResultSet rs=getUserID.executeQuery();
                                System.out.println("Test 1");
				rs.next();
				user=rs.getString(1);
				System.out.println("USER ID :"+user);
				tableName=user;
                                  
				if(type.equals("Entry"))
					{
					
					PreparedStatement parkingEntry=con.prepareStatement("insert into "+ tableName+"(TYPE, AREAID ,TIMEIN)values(? ,?, ? ) ");
                                       java.util.Date date=new java.util.Date(); 
					Timestamp timestamp = new Timestamp(date.getTime());
					System.out.println(timestamp);
					parkingEntry.setTimestamp(3,timestamp);
					parkingEntry.setString(1,"Parking");
					parkingEntry.setString(2,area);	
					System.out.println("Test 2");				
					i = parkingEntry.executeUpdate();
					}

				if(type.equals("Exit"))
					{	
					
					PreparedStatement setTimeOut=con.prepareStatement("update " + tableName + " set TIMEOUT = ? where FLAG = 0");
					java.util.Date date=new java.util.Date(); 
					Timestamp timestamp = new Timestamp(date.getTime());
					System.out.println(timestamp);
					setTimeOut.setTimestamp(1,timestamp);
					i = setTimeOut.executeUpdate();
					System.out.println("test 3");
					PreparedStatement getTime=con.prepareStatement("select TIMESTAMPDIFF(MINUTE, TIMEIN, TIMEOUT) from "+tableName +" where FLAG = '0'");
					rs=getTime.executeQuery();
					rs.next();
					i=rs.getInt(1);
					PreparedStatement getRate=con.prepareStatement("select "+mode+" from PARKING where AREAID =?");
					getRate.setString(1,area);
					rs=getRate.executeQuery();
					System.out.println("test 4");
					rs.next();
					j=rs.getInt(1);
					System.out.println(j);
				
					PreparedStatement setCost=con.prepareStatement("update "+tableName +" set COST= ?,FLAG=1 where FLAG = '0'");
					setCost.setInt(1,j*i);
					i=setCost.executeUpdate();
					}
				if(type.equals("Toll"))
					{
					PreparedStatement getTollRate=con.prepareStatement("select COST from TOLL where TOLLID =?");
					System.out.println("test 5");
					getTollRate.setString(1,area);
					rs=getTollRate.executeQuery();
					System.out.println("test 6");
					rs.next();
					j=rs.getInt(1);
					PreparedStatement toll=con.prepareStatement("insert into "+ tableName+"(TYPE, AREAID  ,COST ,FLAG)values(? ,?, ?,? ) ");
					toll.setString(1,"TOLL");
					toll.setString(2,area);
					toll.setInt(3,j);
					toll.setInt(4,1);
					System.out.println("test 7");
					i=toll.executeUpdate();
					System.out.println("test 8");
					}
				s.close(); 
			}
			//never executes
			//step5 close the connection object  
		con.close();  
		ss.close();  
		}
		catch(Exception e){System.out.println(e);}
	}
}
  
 
