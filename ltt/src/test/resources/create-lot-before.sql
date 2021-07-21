delete from bid;
delete from buyout;
delete from lot;
delete from users;

insert into lot(id, archive, buyout, buyout_time, min_bid, title) values
(1, false, 10, 60, 1, 'aaa'),
(2, false, 50, 60, 5, 'bbb');

insert into users(id, cash) values
(1, 100),
(2, 50);