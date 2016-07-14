<?php
	function validateDate($date)
	{
    	$d = DateTime::createFromFormat('Y-m-d', $date);
    	return $d && $d->format('Y-m-d') === $date;
	}
	
	//POST Arguments
	$name = $_POST['name'];
	$date_of_birth = $_POST['date_of_birth'];
	$phone_number = $_POST['phone_number'];
	$email = $_POST['email'];
	$blood_type = $_POST['blood_type'];
	$city = $_POST['city'];
	$hospital = $_POST['hospital'];
	$department = $_POST['department'];
	$description = $_POST['description'];
	
	//Return statements
	$message = "";
	$response = "Successfull";
	$data = array("name" => $name, "date_of_birth" => $date_of_birth, "phone_number" => $phone_number,
				  "email" => $email, "blood_type" => $blood_type, "city" => $city,
				  "hospital" => $hospital, "department" => $department, "description" => $description);
	$result = array("data"=>$data, "message"=>$message, "response"=>$response);
	
	//Connection info
	$servername = "localhost";
	$username = "root";
	$password = "";
	$database = "giveblood";
	
	if($name === "")
	{
		$nameErr = "Name Error : Please input name \n";
		$message .= $nameErr;
	}
	
	if($phone_number === "")
	{
		$phoneErr = "Phone Error : Please input phone number \n";
		$message .= $phoneErr;
	}
	
	if($email === "")
	{
		$emailErr = "Email Error : Please input email \n";
		$message .= $emailErr;
	}
	
	if($date_of_birth === "")
	{
		$dateErr = "Date Error : Please choose date of birth \n";
		$message .= $dateErr;
	}
	
	if($city === "")
	{
		$cityErr = "City Error : Please choose a city \n";
		$message .= $cityErr;
	}
	
	if($hospital === "")
	{
		$hospitalErr = "Hospital Error : Please choose a hospital \n";
		$message .= $hospitalErr;
	}
	
	//Data check
	if (!preg_match("/^[a-zA-Z ]*$/", $name) && !preg_match ("/^[а-яА-Я ]*$/su", trim($name)))  
	{
  		$nameErr =  "Name Error : Only letters and white space allowed \n"; 
		$message .= $nameErr;
	}
	
	if(validateDate($date_of_birth) == FALSE)
	{
		$dateErr = "Date Error : Invalid date \n";
		$message .= $dateErr;
	}
	
	if(is_numeric($phone_number) == FALSE)
	{
		$phoneErr = "Phone Error : Only numbers allowed \n";
		$message .= $phoneErr;
	}
	
	if (!filter_var($email, FILTER_VALIDATE_EMAIL)) 
	{
  		$emailErr = "Email Error : Invalid email format \n";
		$message .= $emailErr;
	}
	
	if(!preg_match("/^(A|B|AB|O)[+-]$/", $blood_type))
	{
		$typeErr = "Blood type Error : Invalid blood type \n";
		$message .= $typeErr;
	}
	
	if (!preg_match("/^[a-zA-Z ]*$/", $city) && !preg_match ("/^[а-яА-Я ]*$/su", trim($city))) 
	{
  		$cityErr =  "City Error : Only letters and white space allowed \n"; 
		$message .= $cityErr;
	}
	
	//If there is no error message insert into database
	if($message == "")
	{
	
		// Create query
		$sql = "INSERT INTO `consumers` (`ID`, `name`, `date_of_birth`, `phone_number`, `email`, `blood_type`, `city`, `hospital`, `department`, `description`) 
							  	 VALUES (NULL, '$name', '$date_of_birth', '$phone_number', '$email', '$blood_type', '$city', '$hospital', '$department', '$description');";

		// Create PDO connection
		$db = new PDO("mysql:host=$servername;dbname=$database;charset=utf8mb4", $username, $password);
	
		try 
		{
    		//connect as appropriate as above
    		$db->query($sql); 
		} 
		catch(PDOException $ex) 
		{
    		echo "An Error occured!"; 
    		some_logging_function($ex->getMessage());
		}
	}
	else
	{
		$response = "Error";
	}
	
	$result = array("data"=>$data, "message"=>$message, "response"=>$response);
	
	echo json_encode($result);
?>