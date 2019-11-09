<?php

include '../../center/SalizDatabaseManager.php';
$db = new SalizDatabaseManager();

define( "USER_RESERVE_LIST", "USER_RESERVE_LIST" );
define( "CATEGORY", "CATEGORY" );
define( "POSTS", "POSTS" );
define( "TIMES", "TIMES" );
define( "SERVICES", "SERVICES" );

$request = $_GET['request'];


if ( isset( $request ) ) {

	switch ( $request ) {
		case USER_RESERVE_LIST;
			$phone = $_GET['PHONE'];
			$res = $db->getUserReserveList( $phone );
			echo json_encode( [ 'result' => $res ] );
			break;

		case CATEGORY;
			$res = $db->getCategory();
			echo json_encode( [ 'result' => $res ] );
			break;


		case POSTS;
			$res = $db->getPosts();
			echo json_encode( [ 'result' => $res ] );
			break;

		case TIMES;
			$day   = $_GET['DAY'];
			$res = $db->getTimes( $day );
			echo json_encode( [ 'result' => $res ] );
			break;

		case SERVICES;
			$res = $db->getServices();
			echo json_encode( [ 'result' => $res ] );

			break;
	}



} else {
	echo " Parameters : </br> </br> </br> ";
	echo "[ request ] ? { USER_RESERVE_LIST  }";
	echo "</br> </br>";
}


