create table OrderTest
(
    order_id            varchar(100) charset utf8           not null
        primary key,
    order_score         mediumtext                          null,
    app_created_timestamp timestamp                           null,
    db_created_timestamp  timestamp default CURRENT_TIMESTAMP null,
    lat                   mediumtext                          null,
    lon                   mediumtext                          null
);