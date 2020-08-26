insert into artikels (naam,aankoopprijs,verkoopprijs,soort,houdbaarheid,artikelgroepid)
values ('FoodArtikel',1000,1000,'F',7,
(select id from artikelgroepen where naam = 'test'));
insert into artikels (naam,aankoopprijs,verkoopprijs,soort,garantie,artikelgroepid)
values ('NonFoodArtikel',1000,1000,'NF',36,
(select id from artikelgroepen where naam = 'test'));
insert into kortingen(artikelid,vanafAantal,percentage)
values ((select id from artikels where naam = 'NonFoodArtikel'),1,5.5);