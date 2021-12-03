-u dbuser28 -p
use studdb28;

IMPORTANT! Dont use æøå.

Seeding below:

drop table Hus;
create table Hus(id integer primary key auto_increment, beskrivelse varchar(150), gateadresse varchar(40), etasjer varchar(3), latitude varchar(10), longitude varchar(10));

insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('P 32 Andrea Arntzens hus','Pilestredet 32, 0167 Oslo, Norway',11,59.91987,10.73614);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('Pilestredet park 33 Henriette Bie Lorentzens hus','Pilestredet Park 33, 0167 Oslo, Norway',7,59.92012,10.73827);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('P 35 Ellen Gleditschs hus','Pilestredet 35, 0167 Oslo, Norway',11,59.91933,10.73501);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('Holbergsgate 1','Holbergsgate 1, 0167 Oslo, Norway',8,59.92110,10.73615);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('SG 26 Natalie Rogoff Ramsoys hus','Stenberggata 26, 0167 Oslo, Norway',9,59.92100,10.73502);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('Stenberggata 29','Stenberggata 29, 0167 Oslo, Norway',9,59.92123,10.73446);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('P 40 Signy Arctanders hus','Pilestredet 40, 0167 Oslo, Norway',9,59.92105,10.73413);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('P 42 Anna Sethnes hus','Pilestredet 42, 0167 Oslo, Norway',9,59.92161,10.73424);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('P 44 Elisabeth Lampes hus','Pilestredet 44, 0167 Oslo, Norway',6,59.92202,10.73401);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('P 46 Clara Holsts hus','Pilestredet 46, 0167 Oslo, Norway',8,59.92106,10.73329);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('P 48 Eva Balkes hus','Pilestredet 48, 0167 Oslo, Norway',8,59.92150,10.73297);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('P 50 Katti Anker Mollers hus','Pilestredet 50, 0167 Oslo, Norway',5,59.92193,10.73264);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('P 52 Fyrhuset','Pilestredet 52, 0167 Oslo, Norway',5,59.92235,10.73351);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('P 52 Studenthuset','Pilestredet 52, 0167 Oslo, Norway',7,59.92248,10.73230);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('Falbesgate 5','Falbesgate 5, 0167 Oslo, Norway',1,59.92178,10.73495);
insert into Hus (Beskrivelse,Gateadresse,Etasjer,Latitude,Longitude) values ('Falbesgate 18','Falbesgate 18, 0167 Oslo, Norway',1,59.92224,10.73434);



