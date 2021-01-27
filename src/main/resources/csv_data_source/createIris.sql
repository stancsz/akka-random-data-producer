create database IrisTest;

create table IrisTest.iris
(
	id int not null
		primary key,
	SepalLengthCm float null,
	SepalWidthCm float null,
	PetalLengthCm float null,
	PetalWidthCm float null,
	Species varchar(50) null
);

