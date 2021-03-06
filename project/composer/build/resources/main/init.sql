CREATE TABLE enrichment
(
    id               INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    type             VARCHAR(255),
    max_weight       INTEGER,
    type_of_delivery VARCHAR(255),
    volume           INTEGER,
    CONSTRAINT pk_enrichment PRIMARY KEY (id)
);

insert into enrichment(id, type, max_weight, type_of_delivery, volume) values
(1, 'Box', 200, 'Pickup', 200),
(2, 'Package', 500, 'Plane', 1000),
(3, 'Letter', 100, 'Courier', 70),
(4, 'Telegram', 70, 'Courier', 40);