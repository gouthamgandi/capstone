<style>
table, th, td {
    border: 1px solid black;
}
th {
    text-align: centre;
}
</style>



<?PHP
$value = $_GET['DL'];
$servername = "localhost";
$username = "root";
$password = "newpassword";
$dbname = "project";
if (isset($_GET['history'])) {
		
		
        $conn = new mysqli($servername, $username, $password, $dbname);
		if ($conn->connect_error) {
			die("Connection failed: " . $conn->connect_error);
			} 
		$tablename = $value;     
		$sql = "select * from  " . $value;
		$result = $conn->query($sql);
		echo "<Table style=\"width:100%;\">";
		echo "<tr>";
		echo "<td>";
        echo "TYPE : ";
		echo "<td>";
        echo "AREAID :";
		echo "<td>";
        echo " TIMEIN :";
		echo "<td>";
        echo  "TIMEOUT :";
		echo "<td>";
        echo  "COST :";
		echo "<td>";
        echo  "FLAG : ";
		if ($result->num_rows > 0) {
				
			while($row = $result->fetch_assoc()) {
				echo "<tr>";
				echo "<td>";
				echo $row["TYPE"];
				echo "<td>";
				echo  $row["AREAID"];
				echo "<td>";
				echo $row["TIMEIN"];
				echo "<td>";
				echo  $row["TIMEOUT"];
				echo "<td>";
				echo  $row["COST"];
				echo "<td>";
				echo  $row["FLAG"];
			}
		} else {
			echo "0 results";
			}
			$conn->close();
    }
    elseif (isset($_GET['pay'])) {
		
		$conn = new mysqli($servername, $username, $password, $dbname);
		if ($conn->connect_error) {
			die("Connection failed: " . $conn->connect_error);
		} 
		$sum=0;     
		$sql = "SELECT COST FROM ".$value;
		$result = $conn->query($sql) or die($conn->error);
		if ($result->num_rows > 0) {
				
			while($row = $result->fetch_assoc()) {
		
			$sum = $sum+$row['COST'];
			}
		}
		echo "The total payable amount is : ".$sum;
		echo "<h1> To Payment Gateway </h1>";
		$sql = "TRUNCATE ".$value;
		mysqli_query($conn,$sql);
		$conn->query($sql) or die($conn->error);
		$conn->close();
        echo "<h4> User table truncated </h4>";
    }

?>