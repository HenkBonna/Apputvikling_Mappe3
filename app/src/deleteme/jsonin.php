<!-- Save a house -->

<?php
$con=mysqli_connect('localhost','dbuser28','Oldofficepop6:','studdb28');
$beskrivelse=(String)$_REQUEST['Beskrivelse'];
$gateadresse=(String)$_REQUEST['Gateadresse'];
$etasjer=(String)$_REQUEST['Etasjer'];
$latitude=(String)$_REQUEST['Latitude'];
$longitude=(String)$_REQUEST['Longitude'];
$sql=mysqli_query($con,"insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('$beskrivelse','$gateadresse','$etasjer','$latitude','$longitude');");
mysqli_close($con);
?>

<!-- test URL -->
http://studdata.cs.oslomet.no/~dbuser28/lagrehus.php/?Beskrivelse=Enda%20et%20Nytt%20Hus&Gateadresse=Gateveien%2052&Etasjer=42&Latitude=49.23242&Longitude=24.42424

<!-- Fetch all Houses -->
<?php
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);
$con=mysqli_connect('localhost','dbuser28','Oldofficepop6:','studdb28');
$sql=("select * from Food");
$tabell=mysqli_query($con,$sql);
mysqli_close($con);
while ($row=mysqli_fetch_assoc($tabell))
{
$output[]=$row;
}
print(json_encode($output));
?>

<!-- Fetch all Houses -->
<?php
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);
$con=mysqli_connect('localhost','dbuser28','Oldofficepop6:','studdb28');
$sql=("select * from Hus");
$tabell=mysqli_query($con,$sql);
mysqli_close($con);
while ($row=mysqli_fetch_assoc($tabell))
{
$output[]=$row;
}
print(json_encode($output));
?>

<!-- test URL -->
http://studdata.cs.oslomet.no/~dbuser28/hentallehus
