create database HotelManager
use HotelManager

create table Room(
roomId varchar(10) not null Primary key,
roomName varchar(50) not null unique,
roomNote varchar(100),
deletedRoom bit not null,
roomStatusId int not null,
roomTypeId int not null
);

create table RoomType(
roomTypeId int not null Primary key,
roomTypeName varchar(50) not null unique,
price bigint not null,
deletedRoomType bit not null
);

create table RoomStatus(
roomStatusId int not null Primary key,
roomStatusName varchar(50),
availableRoom bit not null
);

create table Customer(
customerId varchar(10) not null Primary key,
customerName varchar(100),
customerAddress varchar(200),
governmentId varchar(15) not null unique,
customerType int not null
);

create table CustomerType(
customerTypeId int not null Primary key,
customerTypeName varchar(100) not null unique,
customerTypeRate float not null
);

create table BookRoom(
bookRoomId varchar(10) not null Primary key,
customerId varchar(10) not null,
staffId varchar(10) not null,
dayStart int,
monthStart int,
yearStart int
);

create table BookRoomDetail(
bookRoomDetailId varchar(10) not null Primary key,
roomid varchar(10) not null,
bookRoomId varchar(10) not null,
numberOfGuests int not null
);

create table Staff(
staffId varchar(10) not null Primary key,
staffName varchar(100) not null,
staffAddress varchar(200) not null,
governmentId varchar(15) not null unique,
username varchar(10) not null unique,
staffPassword varchar(10) not null,
isManager bit not null
);

create table Template(
surcharge float not null,
maximumGuests int not null
);

