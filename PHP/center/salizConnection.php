<?php

function OpenSalizCon()
{
	$dbHost = "localhost";
	$dbUser = "khodemon_saliz";
	$dbPass = "Ali0631Abadan";
	$db = "khodemon_saliz";
	$conn = new mysqli( $dbHost, $dbUser, $dbPass,$db) or die("Connect failed: %s\n". $conn -> error);

	return $conn;
}

function CloseCon($conn)
{
	$conn -> this->close();
}
