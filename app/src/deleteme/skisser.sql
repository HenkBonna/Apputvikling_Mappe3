-u dbuser28 -p
use studdb28;
create table Hus(id integer primary key auto_increment, beskrivelse varchar(150), gateadresse varchar(40), etasjer varchar(3), latitude varchar(10), longitude varchar(10));
insert into Hus values (1, 'Dette er en skyskraper', 'Gateveien 42', '102', '10.2525', '71.2525');
insert into Hus values (2, 'Dette er et hus', 'Veigata 56', '3', '30.3333', '41.3333');
