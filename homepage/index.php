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
			   <h1>Firma 7</h1>
			</header>
  
		<nav>
		  <ul>
		    <li><a href="persoenlicheDaten.html">Persönliche Daten</a></li>
		    <li><a href="produkt.html">Produkt</a></li>
		    <li><a href="impressum.html">Impressum</a></li>
		  </ul>
		</nav>

		<article>
		  <h1>Kurzpotrait</h1>
			  
			 <p>Die Firma 7 hat ein neues Produkt entwickelt. Die Anzahle der Mitarbeiter beträgt 3 Leute.<br/>
				Das Produkt ist ein verschlüsseltes Messengersystem mit neuen Features.<br/> 
				Die Produktentwicklung hat im Rahmen der INT Veranstaltung an der HS Mannheim stattgefunden.</p>

		 <img src="mini2.gif" alt="HTML5 Icon" style="float:right;width:300px;height:250px;" >

			<br/>
			
			<?php
				if(!empty($_GET['email']) && !empty($_GET['name'])):
	
					// und nun die Daten in eine Datei schreiben
					// Datei wird zum Schreiben geoeffnet a für append hinzufügen
					$handle = fopen ( "anfragen.txt", "a" );
					$emailadress = $_GET['email'];
					$nameDesKunden =  $_GET['name'];
					 
					// schreiben des Inhaltes von email
					fwrite ( $handle, $emailadress );
					 
					// Trennzeichen einfügen, damit Auswertung möglich wird
					fwrite ( $handle, "|" );
					 
					// schreiben des Inhalts von name
					fwrite ( $handle, $nameDesKunden );
					// schreiben eines Leerzeichens
					fwrite ( $handle, " " );
					 
					// Datei schließen
					fclose ( $handle );
					
					echo "Danke - Ihre Daten wurden gespeichert";
					exit;
				else:
			?>
							
			<h3><u>E-Mail Registrerung</u></h3>

			<form action="index.php" method="get">

				<p>Ihre E-Mail-Adresse<br>
				<input type="Text" name="email"></p>

				<p>Name:<br>
				<input type="Text" name="name"></p>

				<input type="Submit" name="" value="speichern">

			</form>
	
			<?php endif; ?>			

			
</article>


<footer><p>Impressum</p>

			<br/>

			<p>Kontakt: Gruppe 7, Hochschule Mannheim, Speyererstrasse 10 </br>Gebäude A, Raum A107, Email: felixmorillas@gmail.com</p></footer>

</div>

	

	</body>
</html>