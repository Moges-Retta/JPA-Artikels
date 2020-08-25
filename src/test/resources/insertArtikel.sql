insert into artikels (naam,aankoopprijs,verkoopprijs,soort,houdbaarheid) values ('FoodArtikel',1000,1000,'F',7)
insert into artikels (naam,aankoopprijs,verkoopprijs,soort,garantie) values ('NonFoodArtikel',1000,1000,'NF',36)
insert into kortingen(artikelid,vanafAantal,percentage) values ((select id from artikels where naam = 'NonFoodArtikel'),1,5.5)