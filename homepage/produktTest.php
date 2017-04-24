<?php
	// Wenn name, empfaenger und text (Nachricht) gesetzt und nicht leer sind, schreibe alles in messages.txt
	if(!empty($name=$_GET['name']) && !empty($text=$_GET['text']) && !empty($empfaenger=$_GET['empfaenger'])){

		// und nun die Daten in eine Datei schreiben
		// Datei wird zum Schreiben geoeffnet a für append hinzufügen
		if(!file_exists($chatTxt="chats/$name.$empfaenger.chat.txt")){
			$chatTxt = "chats/$empfaenger.$name.chat.txt";
		}
		$handle = fopen ($chatTxt, a );
		// schreiben des Inhalts
		fwrite ( $handle, $name );
		fwrite ( $handle, "  ->  ");
		fwrite ( $handle, $empfaenger);
		fwrite ( $handle, "  |  ");
		fwrite ( $handle, $text);
		fwrite ( $handle, "\r\n");
		fwrite ( $handle, " " );

		fclose ( $handle );

  } ?>


<!DOCTYPE html>
	<html lang="de">
		<head>
			<meta charset="utf-8">

				<title>Gruppe 7</title>

				<link rel="stylesheet" type="text/css" href="stylesheet.css">

		</head>
	<body>

		<div class="container">

			<header>
			   <h1>Produkt</h1>
			</header>

		<nav>
		  <ul>
		    <li><a href="index.php">Startseite</a></li> 
			<li><a href="produkt.html">Produkt</a></li>
		    <li><a href="persoenlicheDaten.html">Persönliche Daten</a></li>
		    <li><a href="impressum.html">Impressum</a></li>
		  </ul>
		</nav>

		<article>
		  <h1>Messenger</h1>

				<p>Ist ein verschlüseltes Messengersystem für die Kommuniktion mit verschiedenen Clients. <br/>
				Die Realisierung wird mit Hilfe des JMS Systems bewerkstelligt.</p>

		<form action="produktTest.php" method="post ">

			<p><b>Username:</b></p>

			<?php
			// Wenn username bereits vorhanden ist, übergebe ihn beim nächsten submit, ansonsten wird usernamen eingegeben und beim nächsten submit gespeichert
			if (!empty($username = $_GET['name'])) {
				echo $username . '<br>';
				echo '<input type="hidden" name="name" value="' . $username . '">';

				// Wenn saveUsername als Parameter mitgegeben wurde, speicher username in user.txt
				if (isset($_GET['saveUsername'])) {
					// Schau, ob username in user.txt schon vorhanden ist
					$userArray = file("user.txt");
					$i = 0;
					$duplicate = FALSE;
					while (($duplicate == FALSE) && ($i < count($userArray))){
						if($userArray[$i] == ($username . "\r\n")){
							$duplicate = TRUE;
						}
						$i++;
					}
    			// Wenn username noch nicht in user.txt, dann speicher username
					if($duplicate == FALSE){
						$userTxt = fopen ("user.txt", a);
						fwrite ($userTxt, $username);
						fwrite ($userTxt, "\r\n");
						fclose ($userTxt);
					}
				}
			// Eingabe von username und setzten des Befehls zum speichern von username
			} else {
				echo '<input type="Text" name="name"><br>';
				echo '<input type="hidden" name="saveUsername" value="1">';
			}
			?>

			<p><b>Empfaenger:</b></p>
			<!-- Liste mit Empfaengern -->
			<select name="empfaenger" size="5">
				<?php
				$userArray = file("user.txt");
				for ($i=0; $i < count($userArray); $i++) {
					if($userArray[$i] != ($username . "\r\n")){
						echo '<option>' . $userArray[$i] . '</option><br>';
					}
				}
				?>
			</select>

			<p><b>Text:</b></p>
			<!-- Eingabe des message -->
			<p><input type="Text" name="text"></p>
			<input type="Submit" name="" value="senden">
		</form>
	</br>

		<!-- Ausgabe des Chatverlaufs -->
		<textarea name="displayfile" cols="150" rows="20">
			<?php
			if ($file = fopen($chatTxt, r)) {
    		while(!feof($file)) {
        	$line = fgets($file);
					echo "$line";
    		}
    	fclose($file);
			}
			?>
		</textarea>

	</article>

	<footer></footer>

	</div>

	</body>
</html>
