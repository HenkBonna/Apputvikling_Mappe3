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

<!-- test URL -->
http://studdata.cs.oslomet.no/~dbuser28/hentallehus.php

<!-- Fetch single house -->

<?php
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);
$con=mysqli_connect('localhost','dbuser28','Oldofficepop6:','studdb28');
$id=(String)$_REQUEST['Id'];
$sql=("select * from Hus where id='$id'");
$tabell=mysqli_query($con,$sql);
mysqli_close($con);
while ($row=mysqli_fetch_assoc($tabell))
{
$output[]=$row;
}
print(json_encode($output));
?>


<!-- test URL -->
http://studdata.cs.oslomet.no/~dbuser28/henthus.php/?Id=2

<!-- Delete house -->

<?php
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);
$con=mysqli_connect('localhost','dbuser28','Oldofficepop6:','studdb28');
$id=(String)$_REQUEST['Id'];
$sql=("delete from Hus where id='$id'");
$tabell=mysqli_query($con,$sql);
mysqli_close($con);
while ($row=mysqli_fetch_assoc($tabell))
{
$output[]=$row;
}
print(json_encode($output));
?>

<!-- test URL -->
http://studdata.cs.oslomet.no/~dbuser28/sletthus.php/?Id=2

<!-- Edit house -->

<?php
$con=mysqli_connect('localhost','dbuser28','Oldofficepop6:','studdb28');
$id=(String)$_REQUEST['Id'];
$beskrivelse=(String)$_REQUEST['Beskrivelse'];
$gateadresse=(String)$_REQUEST['Gateadresse'];
$etasjer=(String)$_REQUEST['Etasjer'];
$latitude=(String)$_REQUEST['Latitude'];
$longitude=(String)$_REQUEST['Longitude'];
$sql=mysqli_query($con,"update Hus set beskrivelse='$beskrivelse',gateadresse='$gateadresse',etasjer='$etasjer',latitude='$latitude',longitude='$longitude' where id='$id';");
mysqli_close($con);
?>


<!-- test URL -->
http://studdata.cs.oslomet.no/~dbuser28/endrehus.php/?Id=2&Beskrivelse=Oppdatert&Gateadresse=Oppdatveien%2052&Etasjer=69&Latitude=69.6969&Longitude=69.69696
