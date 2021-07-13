CREATE DATABASE [East2WestToursAndTravels]
GO
USE [East2WestToursAndTravels]
GO

CREATE TABLE [Configuration](
	ConfigId INT IDENTITY(1,1),
	ConfigName NVARCHAR(30),
	FileLocation NVARCHAR(100)

	PRIMARY KEY (ConfigId)
)
GO

CREATE TABLE [Customers](
	CustomerId INT IDENTITY(1,1),
	Username NVARCHAR(50),
	[Password] NVARCHAR(60),
	FirstName NVARCHAR(20),
	LastName NVARCHAR(20),
	Gender BIT,
	BirthDate DATE,
	Email NVARCHAR(60),
	Phone NVARCHAR(20),
	[Address] NVARCHAR(120),
	[Avatar] NVARCHAR(100),
	Point INT,
	[Status] TINYINT

	PRIMARY KEY (CustomerId)
)
GO

CREATE TABLE [Employees](
	EmployeeId INT IDENTITY(1,1),
	Username NVARCHAR(50),
	[Password] NVARCHAR(60),
	FirstName NVARCHAR(20),
	LastName NVARCHAR(20),
	Gender BIT,
	BirthDate DATE,
	Email NVARCHAR(60),
	Phone NVARCHAR(20),
	[Address] NVARCHAR(120),
	[Avatar] NVARCHAR(100),
	Point INT,
	[Status] TINYINT,
	[IsAdmin] bit

	PRIMARY KEY (EmployeeId)
)
GO

CREATE TABLE [FeedbackTypes](
	TypeId INT IDENTITY(1,1),
	TypeName NVARCHAR(50)

	PRIMARY KEY (TypeId)
)
GO

CREATE TABLE [Feedbacks](
	FeedbackId INT IDENTITY(1,1),
	CustomerId INT REFERENCES [Customers](CustomerId),
	TypeId INT REFERENCES [FeedbackTypes](TypeId),
	[Message] TEXT

	PRIMARY KEY (FeedbackId)
)
GO

CREATE TABLE [PaymentMethods](
	MethodId INT IDENTITY(1,1),
	MethodName NVARCHAR(50),
	[Status] BIT

	PRIMARY KEY (MethodId)
)
GO

CREATE TABLE [CarTypes](
	TypeId INT IDENTITY(1,1),
	[Description] NVARCHAR(50),
	Seat TINYINT

	PRIMARY KEY (TypeId)
)
GO

CREATE TABLE [CarModels](
	ModelId INT IDENTITY(1,1),
	ModelName NVARCHAR(50)

	PRIMARY KEY (ModelId)
)
GO

CREATE TABLE [Cars](
	CarId INT IDENTITY(1,1),
	ModelId INT REFERENCES [CarModels](ModelId),
	TypeId INT REFERENCES [CarTypes](TypeId),
	UnitPrice BIGINT,
	ShortDescripiton NVARCHAR(300),
	[Description] TEXT,
	Thumbnail NVARCHAR(100),
	[Status] TINYINT,
	CarName NVARCHAR(80),
	LicencePlate NVARCHAR(15)
	
	PRIMARY KEY (CarId)
)
GO

CREATE TABLE [CarImages](
	ImageId INT IDENTITY(1,1),
	CarId INT REFERENCES Cars(CarId),
	[FileName] NVARCHAR(100)

	PRIMARY KEY (ImageId)
)
GO

CREATE TABLE [CarRegistration](
	RegistrationId INT IDENTITY(1,1),
	CustomerId INT REFERENCES Customers(CustomerId),
	RegistrationDate DATETIME,
	ReturnDate DATETIME,
	Total BIGINT,
	MethodId INT REFERENCES PaymentMethods(MethodId),
	Note NVARCHAR(200),
	[Status] TINYINT

	PRIMARY KEY (RegistrationId)
)
GO

CREATE TABLE [CarRegistrationDetails](
	RegistrationId INT REFERENCES [CarRegistration](RegistrationId),
	CarId INT REFERENCES [Cars](CarId),
	Quantity INT,
	UnitPrice BIGINT

	PRIMARY KEY (RegistrationId, CarId)
)
GO

CREATE TABLE [Towns](
	TownId INT IDENTITY(1,1),
	TownName NVARCHAR(20)

	PRIMARY KEY (TownId)
)
GO

CREATE TABLE [Restaurants](
	RestaurantId INT IDENTITY(1,1),
	RestaurantName NVARCHAR(50),
	[Description] TEXT,
	MinPrice BIGINT,
	MaxPrice BIGINT,
	[Location] TEXT,
	[Thumbnail] NVARCHAR(100),
	TownId INT REFERENCES [Towns](TownId)

	PRIMARY KEY (RestaurantId)
)
GO

CREATE TABLE [Accommodations](
	AccommodationId INT IDENTITY(1,1),
	AccommodationName NVARCHAR(50),
	[Description] TEXT,
	MinPrice BIGINT,
	MaxPrice BIGINT,
	[Location] TEXT,
	[Thumbnail] NVARCHAR(100),
	TownId INT REFERENCES [Towns](TownId)

	PRIMARY KEY (AccommodationId)
)
GO

CREATE TABLE [Airlines](
	AirlineId INT IDENTITY(1,1),
	AirlineName NVARCHAR(50)

	PRIMARY KEY (AirlineId)
)
GO

CREATE TABLE [Flights](
	FlightId INT IDENTITY(1,1),
	FlightName NVARCHAR(50),
	AirlineId INT REFERENCES [Airlines](AirlineId),
	DepartureTime DATETIME,
	TownFrom INT REFERENCES Towns(TownId), 
	TownTo INT REFERENCES Towns(TownId), 
	[Status] TINYINT

	PRIMARY KEY(FlightId)
)
GO

CREATE TABLE [TourTypes](
	TypeId INT IDENTITY(1,1),
	TypeName NVARCHAR(50),

	PRIMARY KEY (TypeId)
)
GO

CREATE TABLE [Destinations](
	DestinationId INT IDENTITY(1,1),
	DestinationName NVARCHAR(100),
	[Description] TEXT,
	Thumbnail NVARCHAR(100),
	TypeId INT REFERENCES [TourTypes](TypeId),
	TownId INT REFERENCES [Towns](TownId)

	PRIMARY KEY (DestinationId)
)
GO

CREATE TABLE [DestinationImages](
	ImageId INT IDENTITY(1,1),
	DestinationId INT REFERENCES [Destinations](DestinationId),
	[FileName] NVARCHAR(100) 

	PRIMARY KEY (ImageId)
)
GO

CREATE TABLE [TourPackages](
	PackageId INT IDENTITY(1,1),
	PackageName NVARCHAR(120),
	[Description] TEXT,
	[Status] TINYINT

	PRIMARY KEY (PackageId)
)
GO

CREATE TABLE [DestinationSchedules](
	PackageId INT REFERENCES [TourPackages](PackageId),
	DestinationId INT REFERENCES [Destinations](DestinationId),
	DayQuantity TINYINT

	PRIMARY KEY (PackageId, DestinationId)
)
GO

CREATE TABLE [Tours](
	TourId INT IDENTITY(1,1),
	TourName NVARCHAR(120),
	PackageId INT REFERENCES [TourPackages](PackageId),
	UnitPrice BIGINT,
	ShortDescription NVARCHAR(200),
	[Description] TEXT,
	DepartureDate DATETIME,
	ReturnDate DATETIME,
	MinQuantity INT,
	MaxQuantity INT,
	EmployeeId INT REFERENCES [Employees](EmployeeId),
	[Status] TINYINT

	PRIMARY KEY (TourId)
)
GO

CREATE TABLE [RestaurantSchedules](
	TourId INT REFERENCES [Tours](TourId),
	RestaurantId INT REFERENCES [Restaurants](RestaurantId),
	VisitDate DATETIME

	PRIMARY KEY (TourId, RestaurantId)
)
GO

CREATE TABLE [AccommodationSchedules](
	TourId INT REFERENCES [Tours](TourId),
	AccommodationId INT REFERENCES [Accommodations](AccommodationId),
	VisitDate DATETIME

	PRIMARY KEY (TourId, AccommodationId)
)
GO

CREATE TABLE [FlightSchedules](
	TourId INT REFERENCES [Tours](TourId),
	FlightId INT REFERENCES [Flights](FlightId)

	PRIMARY KEY (TourId, FlightId)
)
GO

CREATE TABLE [TourRegistration](
	RegistrationId INT IDENTITY(1,1),
	CustomerId INT REFERENCES [Customers](CustomerId),
	TourId INT REFERENCES [Tours](TourId),
	RegistrationDate DATETIME,
	Total BIGINT,
	Quantity INT,
	MethodId INT REFERENCES [PaymentMethods](MethodId),
	Note NVARCHAR(200),
	[Status] TINYINT

	PRIMARY KEY (RegistrationId)
)
