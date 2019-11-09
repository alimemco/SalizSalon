<?php

include '../../center/SalizDatabaseManager.php';

$db = new SalizDatabaseManager();

$request = $_GET['request'];

if (isset($request)){

	if ($request == "RESERVE"){

		$phone = $_GET['PHONE'];
		$dayName = $_GET['DAY_NAME'];
		$monthName = $_GET['MONTH_NAME'];
		$dayOfMonth = $_GET['DAY_OF_MONTH'];
		$price = $_GET['PRICE'];
		$hour = $_GET['HOUR'];
		$services = $_GET['SERVICES'];

		if (isset($phone,$dayName,$monthName,$dayOfMonth,$hour , $price , $services)){
			$result =  $db->addReserve($phone,$dayName , $dayOfMonth, $monthName , $hour,$price,$services);
			echo json_encode($result);
		}
	}
}else {
	echo " Parameters : </br> </br> </br> ";
	echo "[ request ] ? { RESERVE }";
	echo "</br> </br>";
	echo "[ phone ]";
	echo "</br> </br>";
	echo "[ DAY_NAME ]";
	echo "</br> </br>";
	echo "[ MONTH_NAME ]";
	echo "</br> </br>";
	echo "[ DAY_OF_MONTH ]";
	echo "</br> </br>";
	echo "[ PRICE ]";
	echo "</br> </br>";
	echo "[ PRICE ]";
	echo "</br> </br>";
	echo "[ HOUR ]";
	echo "</br> </br>";
	echo "[ SERVICES ]";

}




