<?php

include 'salizConnection.php' ;

class SalizDatabaseManager {

	//TABLE CREATOR
	function createCategoryTable() {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$sqlQ = "CREATE TABLE category (
      ID INT AUTO_INCREMENT PRIMARY KEY,
      name TEXT,
      image TEXT
)COLLATE='utf8mb4_unicode_520_ci';";


		if ( $conn->query( $sqlQ ) ) {
			return "*** [ category ] created successfully ***";
		} else {
			return "[ category ]  ERROR | EXIST OR NOT CREATED";
		}
	}
	function createPostsTable() {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$sqlQ = "CREATE TABLE posts (
      ID INT AUTO_INCREMENT PRIMARY KEY,
      Category_ID INT,
      title TEXT,
      category TEXT,
      price TEXT,
      image TEXT
)COLLATE='utf8mb4_unicode_520_ci';";


		if ( $conn->query( $sqlQ ) ) {
			return "*** [ posts ] created successfully ***";
		} else {
			return "[ posts ]  ERROR | EXIST OR NOT CREATED";
		}
	}
	function createOpenTimesTable() {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$sqlQ = "CREATE TABLE times (
      ID INT AUTO_INCREMENT PRIMARY KEY,
      day TEXT,
      hour TEXT,
      reserved TEXT
)
COLLATE='utf8mb4_unicode_520_ci';";


		if ( $conn->query( $sqlQ ) ) {
			return "*** [ times ] created successfully ***";
		} else {
			return "[ times ]  ERROR | EXIST OR NOT CREATED";
		}
	}
	function createReserveTable() {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$sqlQ = "CREATE TABLE reserve (
      ID INT AUTO_INCREMENT PRIMARY KEY,
      userID INT DEFAULT 0,
      dayName TEXT,
      dayOfMonth TEXT,
      monthName TEXT,
      hour TEXT,
      price TEXT,
      services TEXT,
      status TEXT
)
COLLATE='utf8mb4_unicode_520_ci';";

		if ( $conn->query( $sqlQ ) ) {
			return "*** [ reserve ] created successfully ***";
		} else {
			return "[ reserve ]  ERROR | EXIST OR NOT CREATED";
		}
	}
	function createUserTable() {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$sqlQ = "CREATE TABLE users (
      ID INT AUTO_INCREMENT PRIMARY KEY,
      first_name TEXT,
      last_name TEXT,
      phone TEXT,
      password TEXT
)
COLLATE='utf8mb4_unicode_520_ci';";


		if ( $conn->query( $sqlQ ) ) {
			return "*** [ users ] created successfully ***";
		} else {
			return "[ users ] ERROR | EXIST OR NOT CREATED";
		}
	}
	function createServicesTable() {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$query = "CREATE TABLE services (
      ID INT AUTO_INCREMENT PRIMARY KEY,
      name TEXT,
      price TEXT,
      period TEXT
)
COLLATE='utf8mb4_unicode_520_ci';";


		if ( $conn->query( $query ) ) {
			return "*** [ services ] created successfully ***";
		} else {
			return "[ services ] ERROR | EXIST OR NOT CREATED";
		}
	}

	//ADD TO SERVER
	function addCategory( $name, $image ) {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$validate = "SELECT `serial_number` FROM `hijack` WHERE name='$name'";

		if ( $result = $conn->query( $validate ) ) {

			if ( mysqli_num_rows( $result ) > 0 ) {


				$res[] = array(
					"success" => "false",
					"message" => "exist"
				);

			} else {

				$query = "INSERT INTO `category`( `name`,`image`)
                     VALUES(
                     '$name', '$image')";


				if ( $result = $conn->query( $query ) ) {
					$res[] = array(
						"success" => "true",
						"message" => "successfully"
					);

				} else {

					$res[] = array(
						"success" => "false",
						"message" => "INSERT " . mysqli_error( $conn )
					);
				}
			};


		} else {

			$res[] = array(
				"success" => "false",
				"message" => mysqli_error( $conn )
			);
		}


		return $res;
	}
	function addReserve( $phone, $dayName, $dayOfMonth, $monthName, $hour, $price, $services ) {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$user_check_query = "SELECT ID FROM users WHERE phone='$phone' LIMIT 1";
		$result           = mysqli_query( $conn, $user_check_query );
		$user             = mysqli_fetch_assoc( $result );

		if ( $user ) {
			$userId = $user['ID'];

			$query = "INSERT INTO `reserve`( `userID`,`dayName`,`dayOfMonth`,`monthName`,`hour`,`price`,`services`,`status`)
                     VALUES(
                    '$userId', '$dayName', '$dayOfMonth' , '$monthName' ,'$hour' , '$price' ,'$services','pending')";


			if ( $result = $conn->query( $query ) ) {
				$res[] = array(
					"success" => "true",
					"message" => "successfully"
				);

			} else {

				$res[] = array(
					"success" => "false",
					"message" => "INSERT " . mysqli_error( $conn )
				);
			};


			return $res;

		}


	}

	//USER MANAGER
	function register( $first_name, $last_name, $phone, $password ) {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );
		$hash = md5( $password );
		$res  = array();

		$user_check_query = "SELECT * FROM users WHERE phone='$phone' LIMIT 1";
		$result           = mysqli_query( $conn, $user_check_query );
		$user             = mysqli_fetch_assoc( $result );

		if ( $user ) {

			$res[] = array(
				"success" => "false",
				"message" => "userExist"
			);
		} else {

			$query = "INSERT INTO `users` ( `first_name`,`last_name`,`phone`,`password`)
                     VALUES(
                     '$first_name', '$last_name' , '$phone' ,'$hash' )";


			if ( $result = $conn->query( $query ) ) {
				$res[] = array(
					"success" => "true",
					"message" => "successfully"
				);

			} else {

				$res[] = array(
					"success" => "false",
					"message" => "INSERT " . mysqli_error( $conn )
				);
			};

		}

		return $res;
	}
	function login( $phone, $password ) {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );
		$hash = md5( $password );

		$user_check_query = "SELECT * FROM users WHERE phone='$phone' LIMIT 1";
		$result           = mysqli_query( $conn, $user_check_query );
		$user             = mysqli_fetch_assoc( $result );

		if ( $user ) {

			$password_check_query = "SELECT * FROM users WHERE phone='$phone' AND password = '$hash' LIMIT 1";
			$result               = mysqli_query( $conn, $password_check_query );
			$pass                 = mysqli_fetch_assoc( $result );

			if ( $pass ) {
				$res[] = array(
					"success"    => "true",
					"message"    => "successfully",
					"first_name" => $pass['first_name'],
					"last_name"  => $pass['last_name']
				);
			} else {
				$res[] = array(
					"success" => "false",
					"message" => "passwordNotMatch"
				);
			}

		} else {
			$res[] = array(
				"success" => "false",
				"message" => "userNotFound"
			);
		}

		return $res;
	}
	function editUser( $first_name, $last_name, $phone, $new_phone, $password ) {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );
		$hash = md5( $password );

		if ( isset( $password ) ) {
			$query = "UPDATE users SET first_name='$first_name',last_name='$last_name',phone='$new_phone',password='$hash' WHERE phone='$phone'";
		} else {
			$query = "UPDATE users SET first_name='$first_name',last_name='$last_name',phone='$new_phone' WHERE phone='$phone'";
		}

		if ( $result = $conn->query( $query ) ) {
			$res[] = array(
				"success" => "true",
				"message" => "successfully"

			);

		} else {

			$res[] = array(
				"success" => "false",
				"message" => "EDIT " . mysqli_error( $conn )
			);
		};


		return $res;


	}

	//GET INFORMATION
	function getCategory() {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$mySql = "SELECT * FROM `category`";

		$rows = array();
		$res  = array();

		if ( $result = $conn->query( $mySql ) ) {

			if ( mysqli_num_rows( $result ) > 0 ) {

				while ( $res = $result->fetch_assoc() ) {
					$rows[] = array(

						"ID"    => $res['ID'],
						"name"  => $res['name'],
						"image" => $res['image']
					);

				}

				$res[] = array(
					"success" => "true",
					"message" => "successfully",
					"items"   => $rows
				);

			} else {
				$res[] = array(
					"success" => "false",
					"message" => "is empty"
				);
			};


		} else {
			$res[] = array(
				"success" => "false",
				"message" => mysqli_error( $conn )
			);
		}


		return $res;
	}
	function getTimes($day) {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$mySql = "SELECT * FROM `times` WHERE `day`='$day'";

		$rows = array();
		$res  = array();

		if ( $result = $conn->query( $mySql ) ) {

			if ( mysqli_num_rows( $result ) > 0 ) {

				while ( $res = $result->fetch_assoc() ) {
					$rows[] = array(

						"ID"    => $res['ID'],
						"day"  => $res['day'],
						"hour" => $res['hour'],
						"reserved" => $res['reserved']
					);

				}

				$res[] = array(
					"success" => "true",
					"message" => "successfully",
					"items"   => $rows
				);

			} else {
				$res[] = array(
					"success" => "false",
					"message" => "empty",
					"error" => mysqli_error($conn)
				);
			};


		} else {
			$res[] = array(
				"success" => "false",
				"message" => mysqli_error( $conn )
			);
		}


		return $res;
	}
	function getUserReserveList( $phone ) {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$rows   = array();
		$res    = array();
		$userID = 0;

		$query = "SELECT `ID` FROM `users` WHERE `phone`=$phone order by `ID` DESC ";

		if ( $result = $conn->query( $query ) ) {
			while ( $row = $result->fetch_assoc() ) {
				$userID = $row["ID"];
			}

			$queryReserve = "SELECT * FROM `reserve` WHERE `userID`='$userID' ORDER BY `ID` DESC ";
			if ( $resultReserve = $conn->query( $queryReserve ) ) {
				if ( mysqli_num_rows( $resultReserve ) > 0 ) {
					while ( $res = $resultReserve->fetch_assoc() ) {
						$rows[] = array(
							"ID"         => $res['ID'],
							"dayName"    => $res['dayName'],
							"dayOfMonth" => $res['dayOfMonth'],
							"monthName"  => $res['monthName'],
							"hour"       => $res['hour'],
							"price"      => $res['price'],
							"services"   => $res['services'],
							"status"     => $res['status']
						);
					}
					$res[] = array(
						"success" => "true",
						"message" => "successfully",
						"items"   => $rows
					);

				} else {
					$res[] = array(
						"success" => "false",
						"message" => "empty"
					);
				};


			} else {
				$res[] = array(
					"success" => "false",
					"message" => mysqli_error( $conn )
				);
			}

		} else {
			$res[] = array(
				"success" => "false",
				"message" => "empty"
			);
		}

		return $res;
	}
	function getPosts() {


		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$listItems = array();

		for ( $i = 0; $i < 3; $i ++ ) {

			$ID = $i + 1;

			$query = "SELECT * FROM `posts` WHERE `Category_ID`=$ID ";
			$res   = $conn->query( $query );
			$rows  = array();

			while ( $result = $res->fetch_assoc() ) {
				$rows[] = array(
					"Category_ID" => $result['Category_ID'],
					"ID"       => $result['ID'],
					"title"    => $result['title'],
					"category" => $result['category'],
					"price"    => $result['price'],
					"image"    => $result['image']
				);
			}

			$title = null;

			if ($i == 0){
				$title = "جدیدترین ها";
			}else if ($i == 1){
				$title = "محصولات";
			}else if ($i == 2){
				$title = "پر فروش ترین";
			}

			$listItems[ $i ] = array(
				"title" => $title,
				"items" => $rows
			);
		}

		return $listItems;
	}
	function getServices() {

		$conn = OpenSalizCon();
		mysqli_query( $conn, "SET NAMES utf8" );

		$rows   = array();
		$res    = array();

		$query = "SELECT * FROM `services` ";

		if ( $result = $conn->query( $query ) ) {
				if ( mysqli_num_rows( $result ) > 0 ) {

					while ( $res = $result->fetch_assoc() ) {
						$rows[] = array(
							"ID"         => $res['ID'],
							"name"    => $res['name'],
							"price" => $res['price'],
							"period"  => $res['period']
						);
					}
					$res[] = array(
						"success" => "true",
						"message" => "successfully",
						"items"   => $rows
					);

				} else {
					$res[] = array(
						"success" => "false",
						"message" => "empty"
					);
				};


			} else {
				$res[] = array(
					"success" => "false",
					"message" => mysqli_error( $conn )
				);
			}



		return $res;
	}
}