CREATE DATABASE [CP2096G01_G01_East2West]
GO
USE [CP2096G01_G01_East2West]
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
	[Status] TINYINT,
	Thumbnail NVARCHAR(100)

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
GO

SET IDENTITY_INSERT [dbo].[Configuration] ON 
GO
INSERT [dbo].[Configuration] ([ConfigId], [ConfigName], [FileLocation]) VALUES (1, N'HOME_BANNER_1', N'banner-1146121234335-420.jpg')
GO
INSERT [dbo].[Configuration] ([ConfigId], [ConfigName], [FileLocation]) VALUES (2, N'HOME_BANNER_2', N'banner-2146121234341-420.jpg')
GO
INSERT [dbo].[Configuration] ([ConfigId], [ConfigName], [FileLocation]) VALUES (3, N'HOME_BANNER_3', N'banner-3146121234346-420.jpg')
GO
INSERT [dbo].[Configuration] ([ConfigId], [ConfigName], [FileLocation]) VALUES (4, N'HOME_BANNER_4', N'banner-4146121234353-420.jpg')
GO
INSERT [dbo].[Configuration] ([ConfigId], [ConfigName], [FileLocation]) VALUES (5, N'HOME_BANNER_5', N'banner-614612123441-420.jpg')
GO
INSERT [dbo].[Configuration] ([ConfigId], [ConfigName], [FileLocation]) VALUES (6, N'HOME_BANNER_6', N'banner-714612123447-420.jpg')
GO
INSERT [dbo].[Configuration] ([ConfigId], [ConfigName], [FileLocation]) VALUES (7, N'HOME_BANNER_7', N'banner-8146121234413-420.jpg')
GO
SET IDENTITY_INSERT [dbo].[Configuration] OFF
GO
SET IDENTITY_INSERT [dbo].[PaymentMethods] ON 
GO
INSERT [dbo].[PaymentMethods] ([MethodId], [MethodName], [Status]) VALUES (67, N'ViettinBank', 1)
GO
INSERT [dbo].[PaymentMethods] ([MethodId], [MethodName], [Status]) VALUES (68, N'DongABank', 0)
GO
INSERT [dbo].[PaymentMethods] ([MethodId], [MethodName], [Status]) VALUES (69, N'BIDV', 1)
GO
INSERT [dbo].[PaymentMethods] ([MethodId], [MethodName], [Status]) VALUES (70, N'Moca', 0)
GO
INSERT [dbo].[PaymentMethods] ([MethodId], [MethodName], [Status]) VALUES (71, N'MOMO', 1)
GO
INSERT [dbo].[PaymentMethods] ([MethodId], [MethodName], [Status]) VALUES (72, N'ShopeePay', 0)
GO
INSERT [dbo].[PaymentMethods] ([MethodId], [MethodName], [Status]) VALUES (73, N'VNPay', 1)
GO
SET IDENTITY_INSERT [dbo].[PaymentMethods] OFF
GO
SET IDENTITY_INSERT [dbo].[Towns] ON 
GO
INSERT INTO Towns(TownID, TownName) VALUES(1,'An Giang') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(2,'Ba Ria – Vung Tau')
GO
INSERT INTO Towns(TownID, TownName) VALUES(3,'Bac Giang') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(4,'Bac Kan') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(5,'Bac Lieu') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(6,'Bac Ninh') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(7,'Ben Tre') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(8,'Binh Dinh')
GO
INSERT INTO Towns(TownID, TownName) VALUES(9,'Binh Duong') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(10,'Binh Phuoc') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(11,'Binh Thuan') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(12,'Ca Mau') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(13,'Can Tho') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(14,'Cao Bang') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(15,'Da Nang') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(16,'Dak Lak') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(17,'Dak Nong') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(18,'Dien Bien') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(19,'Dong Nai') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(20,'Dong Thap') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(21,'Gia Lai') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(22,'Ha Giang') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(23,'Ha Nam') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(24,'Ha Noi') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(25,'Ha Tinh') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(26,'Hai Duong') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(27,'Hai Phong') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(28,'Hau Giang') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(29,'Hoa Binh') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(30,'Hung Yen') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(31,'Khanh Hoa') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(32,'Kien Giang') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(33,'Kon Tum') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(34,'Lai Chau') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(35,'Lam Dong') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(36,'Lang Son') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(37,'Lao Cai') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(38,'Long An') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(39,'Nam Dinh') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(40,'Nghe An') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(41,'Ninh Binh') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(42,'Ninh Thuan') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(43,'Phu Tho') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(44,'Phu Yen') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(45,'Quang Binh') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(46,'Quang Nam') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(47,'Quang Ngai') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(48,'Quang Ninh') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(49,'Quang Tri') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(50,'Soc Trang') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(51,'Son La') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(52,'Tay Ninh') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(53,'Thai Binh') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(54,'Thai Nguyen') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(55,'Thanh Hoa') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(56,'Thua Thien Hue') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(57,'Tien Giang') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(58,'TP Ho Chi Minh') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(59,'Tra Vinh') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(60,'Tuyen Quang') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(61,'Vinh Long') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(62,'Vinh Phuc') 
GO
INSERT INTO Towns(TownID, TownName) VALUES(63,'Yen Bai') 
GO
SET IDENTITY_INSERT [dbo].[Towns] OFF
GO
SET IDENTITY_INSERT [dbo].[FeedbackTypes] ON 
GO
INSERT [dbo].[FeedbackTypes] ([TypeId], [TypeName]) VALUES (1, N'Complaint')
GO
INSERT [dbo].[FeedbackTypes] ([TypeId], [TypeName]) VALUES (2, N'Registration Problem')
GO
INSERT [dbo].[FeedbackTypes] ([TypeId], [TypeName]) VALUES (3, N'Services')
GO
INSERT [dbo].[FeedbackTypes] ([TypeId], [TypeName]) VALUES (4, N'Guarantee')
GO
INSERT [dbo].[FeedbackTypes] ([TypeId], [TypeName]) VALUES (5, N'Quality')
GO
SET IDENTITY_INSERT [dbo].[FeedbackTypes] OFF
GO
SET IDENTITY_INSERT [dbo].[TourTypes] ON 
GO
INSERT [dbo].[TourTypes] ([TypeId], [TypeName]) VALUES (1, N'Ecotourism')
GO
INSERT [dbo].[TourTypes] ([TypeId], [TypeName]) VALUES (2, N'Sea Travel')
GO
INSERT [dbo].[TourTypes] ([TypeId], [TypeName]) VALUES (3, N'Historical Places')
GO
INSERT [dbo].[TourTypes] ([TypeId], [TypeName]) VALUES (4, N'Famous Landscape')
GO
INSERT [dbo].[TourTypes] ([TypeId], [TypeName]) VALUES (5, N'Exploration travel')
GO
SET IDENTITY_INSERT [dbo].[TourTypes] OFF
GO
SET IDENTITY_INSERT [dbo].[CarModels] ON 
GO
INSERT [dbo].[CarModels] ([ModelId], [ModelName]) VALUES (1, N'Toyota')
INSERT [dbo].[CarModels] ([ModelId], [ModelName]) VALUES (2, N'Huyndai')
INSERT [dbo].[CarModels] ([ModelId], [ModelName]) VALUES (3, N'Mercedes')
INSERT [dbo].[CarModels] ([ModelId], [ModelName]) VALUES (4, N'Mazda')
SET IDENTITY_INSERT [dbo].[CarModels] OFF
GO
SET IDENTITY_INSERT [dbo].[CarTypes] ON 
GO
INSERT [dbo].[CarTypes] ([TypeId], [Description], [Seat]) VALUES (1, N'Sedan', 5)
INSERT [dbo].[CarTypes] ([TypeId], [Description], [Seat]) VALUES (2, N'Hatchbacks', 7)
INSERT [dbo].[CarTypes] ([TypeId], [Description], [Seat]) VALUES (3, N'SUV', 8)
INSERT [dbo].[CarTypes] ([TypeId], [Description], [Seat]) VALUES (4, N'SUV', 9)
INSERT [dbo].[CarTypes] ([TypeId], [Description], [Seat]) VALUES (5, N'Multi-use', 15)
SET IDENTITY_INSERT [dbo].[CarTypes] OFF
GO
SET IDENTITY_INSERT [dbo].[Cars] ON 
GO
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (234, 1, 4, 1250000, N'Granvia owns an impressive, luxurious and unique design, highlighting the business style.', N'<p>• Number of seats: 9 seats<br>• Style: Multi-purpose<br>• Fuel: Oil<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission</p>', N'GRANVIA_PREMIUM_PACKAGE.png', 1, N'GRANVIA PREMIUM PACKAGE', N'30A-145.19')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (235, 2, 5, 1000000, N'Possessing a solid appearance, Hiace meets all transportation needs for business owners, business services.', N'<p>• Number of seats: 15 seats<br>• Style: Commercial<br>• Fuel: Oil<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed manual</p>', N'solati-h350.png', 1, N'Solati', N'65F-623.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (236, 3, 1, 1500000, N'Possessing a solid appearance, Hiace meets all transportation needs for business owners, business services.', N'<p>• Number of seats: 5 seats<br>• Style: Sedan<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission<br>+ 1,998 cc gasoline engine</p>', N'Mercedes-AMG-GT-R-VNE-1-1588663791.png', 1, N'Mercedes-AMG GT', N'65A-999.99')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (237, 4, 2, 900000, N'Unnecessary lines are thoroughly eliminated, creating a unique design with subtle rippled reflections that intertwine light and shadow as the vehicle moves.', N'<p>• Number of seats: 5 seats<br>• Style: Sedan<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission<br>+ 1,998 cc gasoline engine</p>', N'O1lMc55VPQ.png', 1, N'Mazda CX-3', N'65F-113.64')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (238, 1, 4, 900000, N'Granvia owns an impressive, luxurious and unique design, highlighting the business style.', N'<p>• Number of seats: 9 seats<br>• Style: Multi-purpose<br>• Fuel: Oil<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission</p>', N'mau-xe-toyota-granvia-den.png', 1, N'GRANVIA PREMIUM PACKAGE', N'29A-645.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (239, 2, 1, 1000000, N'Possessing a solid appearance, Hiace meets all transportation needs for business owners, business services.', N'<p>• Number of seats: 5 seats<br>• Style: Sedan<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission<br>+ 1,998 cc gasoline engine</p>', N'Hyundai-Tucson-e1600142942116.png', 1, N'Tucson', N'30A-145.51')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (241, 4, 2, 900000, N'Making a strong impression at first sight, Mazda CX-8 not only possesses a majestic, elegant and luxurious appearance but also offers the ultimate comfort experience.', N'<p>• Number of seats: 7 seats<br>• Style: SUV<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission</p>', N'mazda-cx-8.png', 1, N'MAZDA CX-8', N'65A-136.65')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (250, 1, 5, 900000, N'Possessing a solid appearance, Hiace meets all transportation needs for business owners, business services.', N'<p>• Number of seats: 15 seats<br>• Style: Commercial<br>• Fuel: Oil<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed manual</p>', N'unnamed.png', 1, N'HIACE ENGINE OIL', N'28A-186.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (254, 1, 1, 1300000, N'Possessing a sporty appearance with a youthful design language and a wide choice of colors, it deserves to be your first car.', N'<p>• Number of seats: 5 seats<br>• Style: Hatchback<br>• Fuel: Petrol<br>• Origin: Imported car<br>• Other information:<br>+ 5-speed manual</p>', N'R71_ORANGE-ME-2.png', 1, N'WIGO 5MT', N'28A-136.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (255, 1, 1, 900000, N'The new VIOS with its emotional design and 5-star safety technology will be an endless source of inspiration for you to explore every road.', N'<p>• Number of seats: 5 seats<br>• Style: Sedan<br>• Fuel: Gasoline<br>• Origin: Domestic car<br>• Other information:<br>+ 5-speed manual</p>', N'VÀNG-VIOS-1.5E-MT-3-TK.png', 1, N'VIOS 1.5E MT (3 AIRBAGS)', N'30A-145.19')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (256, 1, 1, 890000, N'The 2019 Camry version brings a new, extremely eye-catching appearance with a seamlessness in every design detail, which is the focal point to attract eyes when surfing.', N'<p>• Number of seats: 5 seats<br>• Style: Sedan<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission<br>+ 1,998 cc gasoline engine</p>', N'camry20E-2019.png', 1, N'CAMRY 2.0G', N'29A-645.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (259, 1, 1, 1200000, N'The new HILUX with a breakthrough appearance, majestic and confident attitude is a durable pickup truck that challenges you on all roads.', N'<p>• Number of seats: 5 seats<br>• Style : Pickup<br>• Fuel: Oil<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed manual transmission</p>', N'Car-4R8-2.png', 1, N'HILUX 2.4L 4X2 MT', N'28A-656.24')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (260, 1, 1, 1030000, N'Enjoy modern space and quality to every angle, giving the driver great comfort. Travels are limitless with Corolla Altis.', N'<p>• Number of seats : 5 seats<br>• Style : Sedan<br>• Fuel : Gasoline<br>• Origin: Domestic car<br>• Other information:<br>+ Stepless automatic transmission<br>+ Petrol engine capacity of 1,798 cm3</p>', N'Toyota-Corolla-Altis-1.8E-cvt-màu-Đen-218.png', 1, N'COROLLA ALTIS 1.8E CVT', N'29A-615.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (261, 1, 2, 1050000, N'Spacious space with high-class furniture and modern amenities, combining advanced technology to bring relaxation and great experience, honoring the owner''s style.', N'<p>• Number of seats: 7 seats<br>• Style: SUV<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission</p>', N'Bac-1F7-2.png', 1, N'LAND CRUISER PRADO VX', N'28A-116.24')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (262, 1, 2, 850000, N'Toyota Avanza with modern design and embossed lines on the body bring a sense of solidity and strength.', N'<p>• Number of seats: 7 seats<br>• Style: Multi-purpose<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 5-speed manual</p>', N'Bac-1E7-MT.png', 1, N'AVANZA MT', N'28A-136.24')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (263, 1, 2, 990000, N'With a strong appearance and delicate lines in every detail, TOYOTA RUSH represents the spirit of desire to conquer new heights.', N'<p>• Number of seats: 7 seats<br>• Style: SUV<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 4-speed automatic transmission</p>', N'Bac-2.png', 1, N'RUSH S 1.5AT', N'28A-136.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (264, 1, 2, 1050000, N'Spacious space with high-class furniture and modern amenities, combining advanced technology to bring relaxation and great experience, honoring the owner''s style.', N'<p>• Number of seats: 7 seats<br>• Style: SUV<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission</p>', N'anh-dai-dien-toyota-land-cruiser-prado-tinbanxe.png', 1, N'LAND CRUISER PRADO VX', N'28A-136.98')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (265, 1, 2, 1050000, N'Spacious space with high-class furniture and modern amenities, combining advanced technology to bring relaxation and great experience, honoring the owner''s style.', N'<p>• Number of seats: 7 seats<br>• Style: SUV<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission</p>', N'toyota_land_cruiser_prado_mau_den_2019-min.png', 1, N'LAND CRUISER PRADO VX', N'28A-836.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (267, 1, 2, 1050000, N'Spacious space with high-class furniture and modern amenities, combining advanced technology to bring relaxation and great experience, honoring the owner''s style.', N'<p>• Number of seats: 7 seats<br>• Style: SUV<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission</p>', N'Prado-vx-mau-do-3R3.png', 1, N'LAND CRUISER PRADO VX', N'28F-136.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (268, 1, 1, 900000, N'Spacious, comfortable interior is improved with modern technology, creating a classy and peaceful space.', N'<p>• Number of seats: 5 seats<br>• Style: Sedan<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission<br>+ 1,998 cc gasoline engine</p>', N'camrydo.png', 1, N'CAMRY 2.0G', N'28A-131.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (269, 1, 1, 900000, N'The 2019 Camry version brings a new, extremely eye-catching appearance with a seamlessness in every design detail, which is the focal point to attract eyes when surfing.', N'<p>• Number of seats: 5 seats<br>• Style: Sedan<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission<br>+ 1,998 cc gasoline engine</p>', N'toyota-camry-phien-ban-20-g-at.png', 1, N'CAMRY 2.0G', N'28A-762.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (270, 1, 1, 900000, N'The 2019 Camry version brings a new, extremely eye-catching appearance with a seamlessness in every design detail, which is the focal point to attract eyes when surfing.', N'<p>• Number of seats: 5 seats<br>• Style: Sedan<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission<br>+ 1,998 cc gasoline engine</p>', N'Bạc-(1D4)-G-2.png', 1, N'CAMRY 2.0G', N'28A-851.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (271, 1, 3, 950000, N'Possessing a luxurious appearance and a solid chassis, Innova Breakthrough Generation meets all the needs of modern life, worthy of an ideal companion with your family on every journey.', N'<p>• Number of seats: 8 seats<br>• Style: Multi-purpose<br>• Fuel: Gasoline<br>• Origin: Domestic car<br>• Other information:<br>+ 5-speed manual transmission<br>+ 1,998 cm3 capacity gasoline engine</p>', N'toyota_innova.png', 1, N'INNOVA E 2.0MT', N'64F-123.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (272, 1, 3, 950000, N'Possessing a luxurious appearance and a solid chassis, Innova Breakthrough Generation meets all the needs of modern life, worthy of an ideal companion with your family on every journey.', N'<p>• Number of seats: 8 seats<br>• Style: Multi-purpose<br>• Fuel: Gasoline<br>• Origin: Domestic car<br>• Other information:<br>+ 5-speed manual transmission<br>+ 1,998 cm3 capacity gasoline engine</p>', N'Taxi-Quận-9-TP-HCM.png', 1, N'INNOVA E 2.0MT', N'65A-31.23')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (273, 1, 3, 950000, N'Possessing a luxurious appearance and a solid chassis, Innova Breakthrough Generation meets all the needs of modern life, worthy of an ideal companion with your family on every journey.', N'<p>• Number of seats: 8 seats<br>• Style: Multi-purpose<br>• Fuel: Gasoline<br>• Origin: Domestic car<br>• Other information:<br>+ 5-speed manual transmission<br>+ 1,998 cm3 capacity gasoline engine</p>', N'mauxam-g-1482223226-2ec576e8-f92b-4571-bf50-1a880993fa19.png', 1, N'INNOVA E 2.0MT', N'65E-752.11')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (274, 1, 2, 1500000, N'Making a strong impression at first sight, Alphard not only possesses a majestic, elegant and luxurious appearance but also offers the ultimate comfort experience.', N'<p>• Number of seats: 7 seats<br>• Style: Multi-purpose<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 8-speed automatic transmission</p>', N'Toyota_Alphard_Ghi_4x7_600x249px.png', 1, N'ALPHARD LUXURY', N'65E-136.65')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (275, 1, 2, 1500000, N'Making a strong impression at first sight, Alphard not only possesses a majestic, elegant and luxurious appearance but also offers the ultimate comfort experience.', N'<p>• Number of seats: 7 seats<br>• Style: Multi-purpose<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 8-speed automatic transmission</p>', N'toyota-alphard-2022.png', 1, N'ALPHARD LUXURY', N'30A-145.11')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (276, 1, 2, 1500000, N'Making a strong impression at first sight, Alphard not only possesses a majestic, elegant and luxurious appearance but also offers the ultimate comfort experience.', N'<p>• Number of seats: 7 seats<br>• Style: Multi-purpose<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 8-speed automatic transmission</p>', N'3f59c84cfe326dc1b284dca0d728c340a86cccd9cc677531f69802bae3815e3c.png', 1, N'ALPHARD LUXURY', N'75A-645.21')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (282, 1, 5, 2000000, N'Possessing a solid appearance, Hiace meets all transportation needs for business owners, business services.', N'<p>• Number of seats: 15 seats<br>• Style: Commercial<br>• Fuel: Oil<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed manual</p>', N'unnamed2.png', 1, N'HIACE ENGINE OIL', N'28A-136.29')
INSERT [dbo].[Cars] ([CarId], [ModelId], [TypeId], [UnitPrice], [ShortDescripiton], [Description], [Thumbnail], [Status], [CarName], [LicencePlate]) VALUES (284, 1, 2, 1, N'High-class, luxurious interior perfectly combined with outstanding advanced features, the new Land Cruiser gives owners the ultimate experience with unlimited pride.', N'<p>• Number of seats: 7 seats<br>• Style: SUV<br>• Fuel: Gasoline<br>• Origin: Imported car<br>• Other information:<br>+ 6-speed automatic transmission</p>', N'sxwisgjt5pwk.png', 1, N'LAND CRUISER', N'28A-136.23')
SET IDENTITY_INSERT [dbo].[Cars] OFF
GO
SET IDENTITY_INSERT [dbo].[CarImages] ON 
GO
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (8, 234, N'01gw1h206121113243-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (9, 234, N'qneg1y206121113243-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (10, 234, N'sebefh206121113243-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (11, 238, N'01gw1h206121113620-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (12, 238, N'qneg1y206121113620-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (13, 238, N'sebefh206121113620-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (14, 254, N'asd206121113926-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (15, 254, N'hxie0p206121113926-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (16, 254, N'sad206121113926-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (17, 255, N'5smhzo206121114351-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (18, 255, N'25keny206121114351-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (19, 255, N'ijzvi2206121114351-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (20, 256, N'c1206121114718-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (21, 256, N'c2206121114719-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (22, 256, N'c3206121114719-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (23, 284, N'a120612111546-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (24, 284, N'a220612111546-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (25, 284, N'a320612111547-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (26, 263, N'1120612120594-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (27, 263, N'1220612120594-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (28, 263, N'1320612120594-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (29, 262, N'2120612121449-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (30, 262, N'2220612121449-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (31, 262, N'2320612121449-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (32, 260, N'3120612121921-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (33, 260, N'3220612121921-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (34, 260, N'3320612121921-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (35, 282, N'41206121211257-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (36, 282, N'42206121211257-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (37, 282, N'43206121211257-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (38, 261, N'51206121211640-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (39, 261, N'52206121211640-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (40, 261, N'53206121211640-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (41, 259, N'61206121212151-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (42, 259, N'62206121212151-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (43, 259, N'63206121212151-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (44, 250, N'71206121213052-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (45, 250, N'72206121213052-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (46, 250, N'73206121213052-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (47, 264, N'8120612121428-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (48, 264, N'8220612121428-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (49, 264, N'8320612121428-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (50, 265, N'81206121214410-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (51, 265, N'82206121214410-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (52, 265, N'83206121214410-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (53, 267, N'81206121214614-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (54, 267, N'82206121214614-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (55, 267, N'83206121214614-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (56, 268, N'91206121221129-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (57, 268, N'92206121221129-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (58, 268, N'93206121221129-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (59, 269, N'111206121222015-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (60, 269, N'112206121222015-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (61, 269, N'113206121222015-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (62, 270, N'211206121222955-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (63, 270, N'222206121222955-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (64, 270, N'233206121222955-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (65, 271, N'121206121223351-420.jpg')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (66, 271, N'122206121223351-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (67, 271, N'123206121223351-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (68, 273, N'121206121223927-420.jpg')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (69, 273, N'122206121223927-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (70, 273, N'123206121223927-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (71, 272, N'121206121224011-420.jpg')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (72, 272, N'122206121224011-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (73, 272, N'123206121224011-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (74, 274, N'al11206121225413-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (75, 274, N'al12206121225413-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (76, 274, N'al13206121225413-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (77, 275, N'al21206121225444-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (78, 275, N'al22206121225444-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (79, 275, N'al23206121225444-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (80, 276, N'al3120612122552-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (81, 276, N'al3220612122552-420.png')
INSERT [dbo].[CarImages] ([ImageId], [CarId], [FileName]) VALUES (82, 276, N'al3320612122552-420.png')
SET IDENTITY_INSERT [dbo].[CarImages] OFF
GO
SET IDENTITY_INSERT [dbo].[Accommodations] ON 
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (1, N'Sense Legend Hotel', N'<p>Centrally located in Ho Chi Minh City, Sense Legend Hotel &amp; Spa offers elegant and comfortable accommodation within 200 m from Tao Dan Park. It operates a 24-hour front desk and provides free WiFi access in the entire property.The hotel is within 800 m from Reunification Palace and War Remnants Museum. Tan Son Nhat International Airport is approximately 6.5 km away. The air-conditioned rooms feature a wardrobe, seating area, a minibar and a flat-screen TV with cable channels. The en suite bathroom comes with shower facility, slippers and free toiletries. Complimentary instant coffee and tea are provided. 24-hour room service is available. Fluently-versed in Vietnamese and English, the friendly staff at the hotel can assist guests with luggage storage, laundry and ironing services. Offering chargeable airport transfers, it has a tour desk and business centre. Located on the 9th floor, the in-house Sense Legend Hotel &amp; Spa resrtaurant serves daily breakfast from 07:00 to 09:00. This is our guests favourite part of Ho Chi Minh City, according to independent reviews. Couples particularly like the location — they rated it 8.8 for a two-person trip. We speak your language!</p>', 250000, 2500000, N'33 Bui Thi Xuan, Pham Ngu Lao, 1 District, H? Chí Minh city', N'hotel29612182150-420.png', 58)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (2, N'Lucky Star Hotel', N'<p>In a prime location in the District 1 of Ho Chi Minh City, Lucky Star Hotel 266 De Tham is located 600 m from Fine Arts Museum, 700 m from Ben Thanh Market and 700 m from Tao Dan Park. The property is close to Ben Thanh Street Food Market, Takashimaya Vietnam and Reunification Palace. The property is 1.1 km from Ho Chi Minh City Museum and 1.2 km from Ho Chi Minh City Hall. The hotel can conveniently provide information at the reception to help guests to get around the area. War Remnants Museum is 1.2 km from Lucky Star Hotel 266 De Tham, while Union Square is 1.3 km from the property. The nearest airport is Tan Son Nhat International Airport, 7 km from the property. This is our guests favourite part of Ho Chi Minh City, according to independent reviews. Couples particularly like the location — they rated it 8.9 for a two-person trip. We speak your language!</p>', 250000, 3000000, N'266 De Tham St, Pham Ngu Lao Ward, Dictrict 1, District 1, 700000 Ho Chi Minh City, Vietnam', N'hotel29612182217-420.jpg', 58)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (3, N'Hoa Cuong Hotel', N'<p>Hoa Cuong Hotel (***) is located in the center of the old town of Dong Van district - Ha Giang, where the center of the Stone Plateau Complex has been recognized by UNESCO. The hotel was inaugurated and put into operation in early 2015 (The most modern and large scale hotel in Ha Giang up to this point). With a scale of 9 floors, 82 bedrooms including: (58 2-bed rooms, 7 3-bed rooms, 15 1-bed rooms, 2 VIP rooms) are equipped with modern equipment such as Samsung 40" TVs supporting Internet connection and connect to MyTV TV service, connect to free Wifi. The hotel is equipped with two elevators, two stairs, fire protection system to ensure the safety of guests. In addition, the hotel is equipped with a modern bathroom with glass walls. Korean blankets, pillows ensure customers an airy space, full of convenience when coming to Stone Plateau. In addition, the hotel also has a spacious dining room, serving up to 300 guests, which is a place to organize events, conferences, seminars, weddings, banquets. Customers can stop at Dong Van Old Quarter to enjoy the local culture, rest and stay healthy for the next tour.</p>', 200000, 1500000, N'Dong Van, Dong Van District, Ha Giang', N'ks-429612182414-420.jpg', 22)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (4, N'SilkRiver Hotel', N'<p>Silk River Hotel Ha Giang is situated in Ha Giang and has a restaurant and a bar. The property features a garden, as well as a terrace. The property provides a 24-hour front desk. With a scale of 9 floors, 82 bedrooms including: (58 2-bed rooms, 7 3-bed rooms, 15 1-bed rooms, 2 VIP rooms) are equipped with modern equipment such as Samsung 40" TVs supporting Internet connection and connect to MyTV TV service, connect to free Wifi. The hotel is equipped with two elevators, two stairs, fire protection system to ensure the safety of guests. In addition, the hotel is equipped with a modern bathroom with glass walls. Korean blankets, pillows ensure customers an airy space, full of convenience when coming to Stone Plateau. The rooms in the hotel are fitted with a flat-screen TV with cable channels. Featuring a private bathroom, rooms at Silk River Hotel Ha Giang also have a lake view. Guest rooms have a desk. A buffet breakfast is served each morning at the property.</p>', 300000, 1200000, N'90 Nguyen Trai, Ha Giang, H Giang, Viet Nam, 100000', N'ks-429612182428-420.jpg', 22)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (5, N'Carita Hotel', N'<p>Set in Da Lat, 1.3 km from Lam Vien Square and 1.4 km from Xuan Huong Lake, Carita Hotel features free WiFi. The hotel is located in Xuan Huong Lake area, close to Yersin Park Da Lat, Hang Nga Villa and Summer Palace of King Bao Dai. Each room here will provide you with a work desk, a flat-screen TV and a private bathroom. Rooms come with a shared bathroom with a shower, while some rooms have a seating area. Rooms are also equipped with a wardrobe. An Asian breakfast is served daily at the property. Staff at the 24-hour front desk can help guests with directions to the area. Carita Hotel is 2.1 km from Dalat Flower Gardens and 4.4 km from Truc Lam Monastery. The nearest airport is Lien Khuong Airport, 22 km away. This is our guests favorite part of Da Lat, according to independent reviews.</p>', 300000, 1500000, N'83 Ba Thang Hai, Phuong 1, Da Lat City, Lam Dong', N'ks-329612182453-420.png', 35)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (6, N'Cozy Nook Hotel', N'<p>Set in Da Lat, 1.4 km from Lam Vien Square and 1.6 km from Xuan Huong Lake, Cozy Nook Hotel - By RAON offers accommodation with a garden and free WiFi throughout the property as well as free WiFi throughout the property. Free private parking for guests who drive. This 2-star hotel offers a concierge service and a tour desk. It also provides a 24-hour front desk, room service and currency exchange for guests. Guest rooms at the hotel have a private bathroom with a bidet, a hairdryer and free toiletries. Rooms are fitted with a seating area. Cozy Nook Hotel - By RAON features a terrace. Cycling is popular in the area, and a car can be rented at the property. Popular points of interest near Cozy Nook Hotel - By RAON include Yersin Park, Hang Nga Villa (Crazy House) and Bao Dai Palace. The nearest airport is Lien Khuong Airport, 22 km from the hotel, and the property offers an airport shuttle service at a surcharge. Couples especially like the location — they rate it 9.5 for a 2-person stay.</p>', 300000, 1600000, N'Behind Nhan Ngai Chicken Bread, January 23, Tran Phu Street, Ward 3, Da Lat City, Lam Dong 67000', N'hotel2961218256-420.png', 35)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (7, N'King Hotel', N'<p>Located in the center of Da Lat city. Kings Hotel is the perfect combination between the ancient features of the misty Da Lat city but still equally modern and luxurious according to 4-star standard. Services including rooms, restaurants, conference rooms, coffee, Spa, ... will really bring the highest satisfaction when coming to Kings Hotel. Location: Located right in the center of Da Lat city, next to Xuan Huong lake. It is 20km from Lien Khuong International Airport and takes about 2 minutes to walk to Da Lat Center Market, located on the main road of Bui Thi Xuan, easy and convenient to travel to visit famous tourist attractions of Da Lat. and enjoy the cuisine of the land of fog.</p>', 350000, 2500000, N'No. 10 Bui Thi Xuan Street, Ward 2, Da Lat City, Lam Dong', N'ks-42961218328-420.png', 35)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (8, N'Memory inn Dalat', N'<p>Memory inn Dalat is a new resort project, a new name in the list of top remarkable villas in Da Lat. Although it has just been opened and put into operation not long ago, it has quickly conquered discerning and fastidious guests. With a neoclassical design style, taking white as the main color and accented with delicate floral motifs along with a very beautiful location is a peaceful valley surrounded by hills in the distance. All have created a luxurious, charming and overwhelming Memory inn Dalat. Memory inn Dalat is a resort villa located on one of the most beautiful streets of Dalat, 3/4 m street. This is a very convenient location near shopping areas, places to eat, check-in,…. Especially very close to the intercity bus station. Visitors arriving in Da Lat will quickly settle into a place to rest before starting their journey to discover the City of Love. It can be said that moving to famous places such as: Lam Vien Square, Con Ga church, Bao Dai palace, ... are all very convenient. Inside Memory inn Dalat owns all 15 single rooms, 1 double room. The resort rooms and common spaces are equipped with high-class furniture with full equipment and are very comfortable.</p>', 350000, 2100000, N'8 D. Dao Duy Tu, Ward 3, Da Lat City, Lam Dong', N'hotel-129612183240-420.png', 35)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (9, N'Minh Khue Hotel', N'<p>Finding an ideal family-friendly small hotel in Vinh Long does not have to be difficult. Welcome to Minh Khue Hotel, a nice option for travelers like you. Rooms at Minh Khue Hotel offer air conditioning, a refrigerator, and a desk providing exceptional comfort and convenience, and guests can go online with free wifi. A 24 hour front desk is one of the conveniences offered at this small hotel. If you are driving to Minh Khue Hotel, free parking is available. Vinh Long has many Vietnamese-style restaurants. So while youre here, be sure to check out popular spots like Meo U Kitchen and Thoc Cafe, which are serving up some great dishes. Minh Khue Hotel looks forward to welcoming you on your visit to Vinh Long.</p>', 150000, 1200000, N'38 Trung Nu Vuong, Ward 1, Vinh Long', N'ks-22961218332-420.png', 61)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (10, N'Ngoc Hung Hotel', N'<p>Along with the development of the dynamic city of Vinh Long, Ngoc Hung Hotel was born with a system of 2 hotels with a scale of thousands of m2 located in the city center, near universities, administrative areas, supermarkets. town and amusement parks, is a new stop for your travel as well as business trip to Vinh Long in particular and the West in general. Located at Vo Van Kiet Street, Ward 3, Vinh Long City. Ngoc Hung 1 Hotel with a convenient traffic location on the main road is located near commercial centers and entertainment areas. This will be the ideal place for you and your loved ones to have the most wonderful relaxing and relaxing moments. Ngoc Hung Hotel is invested and modernly designed with 60 rooms equipped with high-class furniture from famous brands such as KymDan, Hanvico. Along with the cool space is a professional and dynamic staff that perfectly meets the needs of customers. The hotel has a variety of bedroom services including room types including VIP rooms, family vip rooms with luxurious interior systems, a different design space with gentle and warm tones that will make you happy. It feels good to stay here. This place is also specially designed for lovers, couples with a system of love rooms designed with eye-catching furniture and decoration for you to immerse yourself in the most romantic and sweetest space. Not only focusing on services, Ngoc Hung also invests in a spacious yard system so that customers can move freely.</p>', 120000, 1000000, N'71 Deputy Co Dieu, Ward 3, Vinh Long', N'ks-329612183319-420.png', 61)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (11, N'Aurora Hotel', N'<p>Whether you are a tourist or a business traveler, Aurora Hotel is a great choice to stay when visiting Bien Hoa city (Dong Nai). Only about 3 km from interesting activities in the city center. With a convenient location, the hotel offers easy access to the citys popular tourist attractions. Aurora Plaza Hotel is located in Bien Hoa, 32 km from Tan Son International Airport, 17.8 km from Suoi Tien Park and about 27 km from Royal Island Golf Course. Comprising 132 rooms designed with a combination of Asian art and modern architecture, fully equipped with air conditioning, flat-screen TV and private bathroom with bathtub. Hotel services include free WiFi, room service and 24-hour front desk service to meet all the needs of customers. Diverse recreational facilities with fitness center, pool (kids), pool outdoor, sauna. From the hotel, guests can visit tourist attractions quickly and easily. Aurora Hotel has 66 rooms. All furnitures are comfortable, quiet, and even many rooms provide amenities such as wireless internet access, wireless internet access (complimentary), non smoking rooms, air conditioning, service. Alarm. The hotel offers a variety of recreational facilities, including hot tub, fitness center, pool outdoor, pool (kids). With an ideal location and facilities to match, Aurora Hotel hits the spot in every way.</p>', 250000, 2000000, N'253 Pham Van Thuan, Tan Mai, Bien Hoa City, Dong Nai', N'sanh29612184210-420.png', 19)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (12, N'Duong Chau Hotel', N'<p>Designed for both business and leisure travel, Duong Chau Hotel is ideally situated in Bien Hoa; one of the famous areas of the city. Conveniently located just away from the city center, guests will have good accommodation to enjoy the famous attractions and activities here. With a convenient location, the hotel offers easy access to the citys popular tourist attractions. At Duong Chau Hotel, every effort is made to make guests satisfied. To do that, the hotel will provide the best service and facilities. The hotel offers access to a vast array of services, including 24-hour room service, free Wi-Fi in all rooms, 24-hour front desk, express check-in/check-out, luggage storage. Experience premium quality room facilities during your stay. Some rooms are equipped with flat screen television, internet access – wireless, internet access – wireless (complimentary), non smoking rooms, air conditioning, to help you recover after a long day. Besides, the hotel also suggests you entertainment activities to ensure you are always interested during your stay. With an ideal location and facilities to match, Duong Chau Hotel hits the spot in every way.</p>', 240000, 2200000, N'36 Le Quy Don, Tan Hiep, Bien Hoa City, Dong Nai', N'ks-duong-chau29612184153-420.png', 19)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (13, N'The Luxe hotel', N'<p>Conveniently located in central Ho Chi Minh is The Luxe Hotel, which offers contemporary air-conditioned rooms with free Wi-Fi access just a 5-minute walk from Ben Thanh Market. Free parking for motorbikes and a 24-hour front desk is available. Rooms come with comfortable bedding fitted with cotton linens, a duvet and down pillows. They are also equipped with a large flat-screen LED TV, minibar and en suite bathroom with shower facilities and free toiletries. Guest facilities include a 24-hour front desk and a small in-house restaurant serving breakfast. Laundry and room service is available. It is a 10-minute walk to attractions such as the Opera House, Reunification Palace and Notre Dam Cathedral. Tan Son Nhat International Airport is a 45-minute drive away. A variety of dining options are available within walking distance. This is our guests favourite part of Ho Chi Minh City, according to independent reviews. Couples particularly like the location — they rated it 9.2 for a two-person trip. We speak your language!</p>', 250000, 1200000, N'396 Tan Lo, Tan Lo Kieu Luong, Chau Phu A, Chau Doc, An Giang', N'luxe29612184127-420.jpg', 1)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (14, N'Victoria Hotel', N'<p>So much more than simply stylish resorts in some of Southeast Asias most spectacular destinations, Victoria Hotels &amp; Resorts is all about connecting our guests to authentic, local experiences. Within our walls, we find inspiration in the timeless elegance of the Indochine era. Beyond that, we pride ourselves on crafting unique experiences imbued with local flavour through our very own network of cruise ships, speedboats, vintage trains, sidecars, and more. A member of the Thien Minh Group, Victoria Hotels &amp; Resorts offers warm Asian hospitality at its finest, serving both our valued guests as well as the local community. Victoria Experiences invite you to truly get under the skin of your destination. Open to both Victoria guests and non-guests, our experiences are "low and slow", designed to give you a completely different perspective while fostering rich cultural connections. See all our unique experiences here. From exclusive web-only discounts to hassle-free all-inclusive packages, Victoria is all about providing excellent value for your travel dollar. Check out our offers or chat with us for more details and recommendations.</p>', 240000, 1200000, N'Vinh Dong 1 Hamlet, Nui Sam Ward, Chau Doc City, An Giang Province, Vietnam', N'victoria29612184057-420.jpg', 1)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (15, N'Mekong Home', N'<p>Mekong Home is a family-run homestay located on the Ham Luong River in Ben Tre Province. Guests enjoy free use of bicycles, free Wi-Fi access throughout and free parking at a location nearby. Featuring a thatched roof, all bungalows come with air conditioning, a private balcony that opens onto a lush garden, and a private bathroom with solar hot water. The on-site restaurant uses fresh vegetables, as well as locally sourced ingredients and flavors to serve a variety of local dishes. Guests can also cook in the homestays kitchen.</p>', 300000, 1500000, N'Hamlet 9, Phuoc Long Commune, Giong Trom District, Ben Tre, 930000', N'mekong29612183939-420.jpg', 7)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (16, N'Riverside Resort', N'<p>Ben Tre Riverside Resort is the first 4-star resort of Ben Tre province, built on the old ground of Ham Luong ferry terminal. With beautiful nature, unique landscape, fresh climate, visitors will feel the peaceful space of the river countryside, catch the sunrise under the green coconut leaves, and enjoy the cool breeze. from Ham Luong river when coming to Ben Tre Riverside Resort. Ben Tre Riverside Resorts natural landscape is meticulously and skillfully embellished, creating distinct features without losing harmony and nature. Guests can fully enjoy the romantic, meaningful, peaceful resort space but no less luxurious, comfortable and modern. Bridge of love is an impressive feature of Ben Tre Riverside Resort. Visitors can catch a familiar image like standing at Namsan Tower in Korea. The moment of love sublimated with the couple writing vows and locking the lock on the bridge Ben Tre Riverside Resort fully exploits the peaceful and green space of Ben Tre hometown. Guests will be sipping River View Cafe, watching the poetic features of the entire Ham Luong River at sunset from the roof top, a romantic and peaceful scene of the peaceful western country. Coming to Ben Tre Riverside Resort, visitors will enjoy attractive European-Asian-style dishes. Especially, the local specialties are bold with the style of Ben Tre hometown. Ben Tre Riverside Resort is an ideal resort for tourists who love nature, love rivers and the gentleness of the land and people of the Southwest region.</p>', 500000, 1300000, N'708 Nguyen Van Tu, Ward 7, Ben Tre', N'riverside2961218397-420.jpg', 7)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (17, N'Hafi Hotel', N'<p>Set in Vung Tau, 2.6 km from Back Beach, Hafi Hotel offers accommodation with a restaurant, free private parking, an outdoor swimming pool and a bar. This 3-star hotel offers a garden and air-conditioned rooms with free WiFi and a private bathroom. The property also provides a 24-hour front desk and room service for guests. Rooms at Hafi Hotel have a balcony. All rooms are equipped with a flat-screen TV and a hairdryer. Hotel guests can enjoy a buffet breakfast or a à la carte breakfast. Hafi Hotel also has a terrace. Ho May and Bach Dinh eco-tourism area are within 7 km from the hotel. The nearest airport is Tan Son Nhat International Airport, 72 km away, and Hafi Hotel offers an airport shuttle service at a surcharge. Couples especially like the location — they rate it 8.2 for a 2-person stay. We use your language!</p>', 1187000, 2550000, N'68 Ha Huy Tap Ward 10, Vung Tau, Vietnam', N'Ho-boi-sang29612183838-420.jpg', 2)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (18, N'Sun Beach Hotel', N'<p>Located in Vung Tau, less than 1 km from Back Beach and a 13-minute walk from Pineapple Beach, Sun Beach Hotel offers accommodation with a shared lounge and free WiFi as well as free private parking for guests who drive. This hotel offers family rooms and barbecue facilities. The property also provides a 24-hour front desk, room service and currency exchange for guests. Rooms at Sun Beach Hotel are equipped with air conditioning, a flat-screen TV with satellite channels, a fridge, a kettle, a bidet, a hairdryer, a wardrobe and a seating area. Rooms also have garden views and a private bathroom with a shower and free toiletries. The hotel has a terrace. Cycling is popular in the area, and bicycles/cars can be rented at Sun Beach Hotel. Popular points of interest near the hotel include Front Beach, Christ the King Statue and Hydrofoil Pier. The nearest airport is Tan Son Nhat International Airport, 108 km away, and Sun Beach Hotel offers an airport shuttle service at a surcharge.</p>', 1080000, 3000000, N'i1 Thai Van Lung, Ward 2, Vung Tau City, Ba Ria - Vung Tau 79000', N'Aerial-429612183821-420.jpeg', 2)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (19, N'Hoang Hung hotel', N'<p>Hoang Hung Hotel is located in the center of Di An city, Binh Duong province. It only takes you 30 minutes to drive from the hotel to Tan Son Nhat airport or from the hotel to the center of Ho Chi Minh City. With 27 standard rooms and deluxe rooms, beautifully and elegantly designed with attention to every detail, it will bring maximum comfort and comfort to you whether it is a relaxing time or during a business trip. . Rooms here come with air conditioning, wooden furniture, a flat-screen TV with cable channels, free WiFi and a minibar. The private bathroom has hot/cold water facilities and a hairdryer. The hotel also has free private parking available on site. In addition, the hotel also has an indoor entertainment complex for children, an outdoor swimming pool and a restaurant specializing in serving European - Asian dishes, along with a Bar - Café serving a variety of drinks. form. Hoang Hung Hotel with quality facilities and services is the ideal choice for you on business trips and vacations when visiting Di An, Binh Duong, Ho Chi Minh City or other nearby destinations. close. The hotel promises to bring you a wonderful time with relatives, family and friends.</p>', 400000, 1200000, N'Administrative center, 79 10th Street, Di An, city, Binh Duong 820000', N'hoahung29612183736-420.jpg', 9)
GO
INSERT [dbo].[Accommodations] ([AccommodationId], [AccommodationName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (20, N'Alizabeth Hotel', N'<p>Elizabeth Hotel is located in the Center of Culture, Economy, Finance and Education of Binh Duong Industrial City and was put into service from mid-2019. Located in an area of ??more than 2400m2, Elizabeth Hotel is built according to high-class royal architecture, 9 floors high with 85 bedrooms according to 4-star standards, including 14 luxury apartments equipped with full amenities, Modern equipment to serve your long-term stay needs It only takes 10 to 45 minutes to move to industrial parks in the province and neighboring provinces. Nearly 28km from Tan Son Nhat International Airport with 45 minutes of travel, convenient for international guests and foreign experts to come to Binh Duong for work and sightseeing. Elizabeth Hotel offers free breakfast, swimming pool, gym and modern equipment towards convenience and comfort to best serve your resort needs.</p>', 670000, 1200000, N'39A Tran Van On, Phu Hoa, Thu Dau Mot, Binh Duong 75000', N'aliza29612183713-420.jpg', 9)
GO
SET IDENTITY_INSERT [dbo].[Accommodations] OFF
GO
SET IDENTITY_INSERT [dbo].[Restaurants] ON 
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (4, N'Moo Beef Steak Prime', N'<p>New steak menu from Moo Beef Steak Prime with high quality ingredients from Wagyu, Black Angus, imported from prestigious brands such as Tyson and Omaha. USA), Mulwarra (Australia), Hitachi (Japan). With unique cooking method in line with American standards and tailored to Vietnamese taste, our talented chefs will please you all with tender, tasty and flavorful dishes. At Moo Beef Steak Prime, you can also feel the adventurous yet elegant touch which beautifully represents the typical traits of the American Far West. Moo Beef Steak Prime is the 5th restaurant of Moo Beef Steak. It would be our great honor to welcome you on the Opening Days.</p>', 100000, 700000, N'35 Ngo Ðuc Ke, Ben Nghe,  1 District, Ho Chi Minh city', N'do-an-42961219010-420.jpg', 58)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (5, N'Sorae Restaurant – Lounge', N'<p>“Sorae” – Above the sky, exactly what its name implies, the Japanese restaurant has one of the most breath-taking views and is the ideal rendezvous in the inner Saigon. Located in the 24th and 25th floor of AB Tower (76 Le Lai, District 1), our topmost gourmet food style is a must-known get-together place of food lovers. Sorae would be the ideal rendezvous for giving Japanese’s culinary delights a try and drowning in Japanese subcultures in the middle of Saigon. Sora restaurant is renowned not only for its sashimi from the fresh source of ingredients (either fished domestically or directly delivered from famous Tsukiji and Osaka seafood market in Japan) but also for Yakitori &amp; Beer Kitchen corner – The provenance of grilled Wagyu and Angus steak above the charcoal for your best aftertaste. With the great deal passion of 20 topmost chefs, every cuisine is an artwork that conceives the pure Japanese taste.</p>', 165000, 235000, N'24th – 25th Floor, AB Tower, 76A Le Lai, Ben Thanh Ward, District 1, HCMC', N'nha-hang2961219024-420.jpg', 58)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (6, N'Au tau Ngan Ha Porridge', N'<p>Au Tau Porridge in Ha Giang is made from the root of au Tau - a tuber with a very strong poison. Through the skillful processing of the Northwestern people, it becomes a healthy dish. Tubers are commonly found in the northern provinces of Vietnam. Outwardly similar to lowland tubers, but are two completely different types. Tubers grow on rocks, very hard and poisonous. In medicine, au tau has a very good healing effect, especially when soaked in alcohol to massage joints. According to the locals, eating the tubers directly can cause death. There was once a case of death due to mistakenly drinking alcohol and squeezing tubers. It is not known when the dishes from the tuber tuber were formed, becoming a unique feature here. Au Tau porridge is a unique feature when it comes to Ha Giang stone plateau. According to Ms. Huong, an owner of a famous au Tau porridge restaurant in Ha Giang, there must be a secret in processing this ingredient. Before cooking porridge, tubers must be soaked in rice water, then simmered until they are loose, then pureed, cooked with plain rice, glutinous rice, and pork feet. "The tuber is very hard, when cooked, wash the tuber and put it in the pot, keep simmering until it softens by itself. Due to its high toxicity, a large pot of porridge for sale uses only a few bulbs," the shop owner 30 years of experience revealed.</p>', 20000, 30000, N'161 Tran Hung Dao, P. Nguyan Trai, Ha Giang', N'soup2961219058-420.jpg', 22)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (7, N'River Lo fish restaurant', N'<p>River Lo Fish Restaurant is a restaurant specializing in river fish dishes. There are many rare fish species found only in Lo River and this is the highlight of our restaurant. Coming here, you will enjoy the fresh taste of fish dishes that are rarely found anywhere else, from fried, fried, steamed dishes, especially flavorful fish hotpot when eating once is difficult. forget. Come to our restaurant, we look forward to serving you.</p>', 100000, 300000, N'Ng. 382, P. Nguyen Trai, Ha Giang', N'ca-song-lo2961219114-420.jpg', 22)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (8, N'Sushi restaurant', N'<p>Although newly opened, Ms. Trangs restaurant quickly attracts and impresses a large number of diners not only with its cozy and spacious space but also with delicious and diverse dishes. Many diners appreciate that the dishes at the restaurant have a delicious, rich flavor with its own characteristics and are processed quite elaborately. Besides, this place gives guests the quality of attentive and professional service. Therefore, Su Sin Family Restaurant has become a delicious restaurant in Duc Trong, well known and chosen by many people as a stopover to enjoy meals during family and friends gatherings... With the desire to open a restaurant-restaurant, in May 2017, Ms. Trang attended a course on how to open a restaurant on demand at Netspace CN Da Lat Vocational Training. In addition to learning how to cook, Ms. Trang is also supported by the school with menu creation, price consulting, input material management, etc. to do business effectively. If you ever stop at Duc Trong - Lam Dong, you should stop by and enjoy delicious and unique food here!</p>', 100000, 200000, N'705 QL20, Lien Nghia, Ðuc Trong, Lam Ðong 670000', N'am-thuc2961219141-420.png', 35)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (9, N'Vokda restaurant cuisine', N'<p>Su Sin Family Restaurant owned by Ms. Ngo Thi Huyen Trang - former Student of Netspace CN Da Lat School, has been in operation since June 2017. Although appearing for a short time, Ms. Trangs restaurant quickly attracts and impresses a large number of diners not only with its cozy and spacious space but also with delicious and diverse dishes. Many diners appreciate that the dishes at the restaurant have a delicious, rich flavor with its own characteristics and are processed quite elaborately. Besides, this place gives guests the quality of attentive and professional service. Therefore, Su Sin Family Restaurant has become a delicious restaurant in Duc Trong, well known and chosen by many people as a stopover to enjoy meals in family and friends gatherings... Stop at Duc Trong - Lam Dong, please stop by and enjoy this delicious and unique dish.</p>', 159000, 350000, N'26 Nguyen Cong Tru, Lien Nghia, Ðuc Trong, Lam Ðong', N'do-an-32961219155-420.png', 35)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (10, N'Mountain town restaurant', N'<p>Pho Nui Restaurant has a very good location of Da Lat City. The space is spacious, beautiful and luxurious, the view to the outside is great. Here, you can enjoy delicious food, enjoy coffee and watch the romantic flower city in the mist with the cold air of the night sky. The restaurant is divided into many halls from the 1st to the 4th floor with a capacity of more than 1000 guests, decorated quite modernly and fully equipped, suitable for organizing weddings, parties, birthdays or weddings. for family gatherings. In particular, the top floor is the restaurants rooftop area. In the morning, the restaurant serves buffet with many delicious dishes, like the bowl of noodle soup here, very sweet and has up to 2 quail eggs, drinks are cooked soy milk, hot drink. In the evening, this restaurant serves a la carte dishes, many dishes to choose from. The next meal here, the group ordered steamed baby octopus with onions (advertised as fresh), it was great to see the octopus being steamed in a pot on a basket (about 10 fish), very crispy meat, marinated delicate, sweet meat. In addition, the vegetables are very fresh, fried or made in salads is very ok. Professional, attentive, enthusiastic service. In the evening, a lot of people come here to eat and drink. The music is all music without lyrics, it sounds melodious.</p>', 120000, 350000, N'46 Nguyen Chi Thanh Street, Ward 1, Da Lat City, Lam Dong', N'view296121924-420.png', 35)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (11, N'Song May restaurant', N'<p>Song May Restaurant Dalat offers guests typical Vietnamese dishes with an extremely diverse and rich menu. However, the restaurants chefs always make sure to filter the quintessence of cuisine to create new, attractive dishes with pure flavor. Besides, when coming to Song May, you can also enjoy a meal in a large space, surrounded by most of the precious woods used. The restaurant combines ancient and modern architecture to help visitors both feel close, familiar and feel really comfortable when eating here. It is not simply that Song May restaurant is favored by domestic and foreign tourists. This place not only brings you unique dishes, but also a careful investment in infrastructure, strictness in the selection of ingredients, thoughtful training of staff to bring to customers. a more than wonderful experience. Prominent among the space of the immense mountains is an ancient architecture but no less luxurious and noble. With a fairly large area, Song May owns many separate areas to serve different customers. From the outside of the gate, to all the interior furniture, are used precious woods to create a close and cozy culinary space but very sophisticated and luxurious. In addition, the restaurant also very delicately arranges flower pots and ornamental plants along the entrance to create a pleasant and comfortable feeling for diners. Song Mays campus has many kinds of flowers such as Do Quyen, Phuong Hoang, etc., especially the rockery with unique and eye-catching decoration.</p>', 100000, 300000, N'49 Tran Quang Khai Street, Ward 8, Da Lat City, Lam Dong', N'do-an2961219225-420.png', 35)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (12, N'Tran Nam frog porridge', N'<p>When you ask someone: “what is the best Singaporean dish”, I bet many of them will immediately answer Singapore frog porridge. Today, in Vietnam, it is not difficult for you to enjoy a delicious and nutritious bowl of frog porridge, but to eat a bowl of frog porridge properly, you must enjoy it in Singapore. From time immemorial, food has always been considered a special cultural feature of a certain nation or nation in the world. Most of the dishes are not only towards the delicious taste but also towards human health. According to mankind, frog meat is a kind of healthy, nutritious meat and has the ability to help strengthen resistance, repel diseases in humans, including diseases such as cardiovascular diseases, mental diseases. Eating frog meat regularly helps reduce symptoms of neurasthenia, helps with diuretics, supports blood circulation, clears body heat, especially helps patients recover quickly. Unlike other foods, frog meat does not contain cholesterol so it is completely good for human health. Frog meat is also especially suitable for those on a diet. Rice is considered one of the most popular starchy grains for Asians. From the clean rice germ formed from nature, it is used to make a delicious frog porridge that will surely make many people happy when enjoying this dish. The "right" combination of natural frogs and white rice will surely bring you wonderful and interesting feelings.</p>', 20000, 25000, N'Pham Thai Buong, Ward 4, Vinh Long', N'chao2961219253-420.png', 61)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (13, N'Chicken hot pot on stilts', N'<p>Hot pot is made from the meat of free-range chickens, so it is firm, soft and delicious. When cooking hot pot, the sweetness of the chicken blends into the hot pot, making the hot pot soup have a very distinctive sweet taste that other hotpot restaurants do not have. In addition to the delicious taste of meat, the restaurants chicken broth also has a greasy taste and a very fragrant smell. The owner said that the broth of the hot pot is also cooked with ground peanuts, so it has such a greasy and delicious taste. The chicken hot pot is also served with a very large vegetable plate full of Western raw vegetables such as broccoli, collard greens, bean sprouts, morning glory, lotus root... The list of dishes of Nha San chicken hotpot restaurant in addition to the chicken hotpot dish, there are also special dishes made from fresh seafood ingredients such as boiled snails, fried crab with tamarind, grilled shrimp, sweet and sour fried squid... These are all seasoned carefully so they are very tasty. Even the most fastidious diners must be soft-hearted before the dishes of Nha Sans chicken hot pot restaurant.</p>', 100000, 250000, N'National Highway 1A, Tan Ngai, Vinh Long', N'lau-22961219316-420.png', 61)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (14, N'Ngoc Canh Restaurant', N'<p>From 20 years ago, in the Ho Nai area, Bien Hoa, no one did not know Mr. Ngoc Canh, the owner of the restaurant, now at the age of seventy years old but still very enthusiastic about cooking. When he was young, he loved to cook and had the opportunity to be helped by Mrs. Quoc Viet (a culinary expert at that time) and became her student. Later, he opened a restaurant and named it after himself: Ngoc Canh Family Restaurant was born. With Formula Always give customers the most satisfaction when coming to Ngoc Canh shop Therefore, his shop is always crowded thanks to the trust and support of customers. During more than 20 years of business, there have been many classes of customers of different generations coming to support, customers from childhood were brought to the restaurant by their parents to eat and drink, and when they grow up they continue to lead the way. wife and children come to enjoy those dishes. Many customers, when they have settled abroad, when they have the opportunity to return to their homeland, they always want to eat the same dishes that they used to eat at the restaurant before. The feeling of guests when eating the typical Beef Luk Lak dish that nowhere else is processed like it, strange but delicious when biting into a piece of beef that is both soft and fragrant, very flavorful, served with tomato salad and vegetables. crispy fries. Rare Beef with Lemon with soft meat, sweet and sour taste is very harmonious when eating the meat, you will not feel alive, eat it all the time and not get bored and many other very special dishes such as Hot Pot Boiled Gun, Sturgeon, Salad Vinegar oil, seafood dishes… For the past 10 years, he has transferred his management part to his daughters to have some time off. His daughters have inherited the experience from him and upgraded to the present Ngoc Canh Restaurant, with spacious, clean premises, cozy luxurious space but very reasonable prices, so customers continue continue to support. The chef is personally his eldest daughter, Ms. Van, she takes care of, controls and trains the kitchen team to work according to what has been committed to customers, all food must be from Freshest source, cleanly processed, deliciously cooked, it is the motto of always caring about serving customers well that the restaurant always has the trust of customers and is enthusiastically supported. Ms. Van has a very good taste and she decorates the dishes beautifully, when customers come to order a party, she is always personally consulted about which dishes are suitable for the purpose of the party and suitable for the guests. banquet. The story that led her to this kitchen profession is also very coincidental. At that time, Ngoc Canh restaurant was increasingly crowded and her father wanted his daughter to help him manage and cook. Ms. Van, who was a Japanese interpreter at that time, was very troubled when she had to choose and finally, with her passion for serving delicious food to customers, she gave up her job to study. to stand out to help his father manage the restaurant. With a new management style and passion for her work, she managed very well and resulted in the current Ngoc Canh Restaurant. Despite her busy work, she still regularly updates new dishes from teachers who are now chefs at 5-star hotels in Saigon. In todays time, to have a party that is both cozy, delicious, beautiful, full of nutrients and hygienic, takes a lot of time and almost has to go to a restaurant to get it. Understanding that need, after a while of preparation, Ngoc Canh Restaurant wishes to bring new services</p>', 160000, 200000, N'390 Nguyen Ai Quoc Street, Ho Nai, Bien Hoa City, Dong Nai', N'dongnai2961219829-420.png', 19)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (15, N'Wellington Restaurant & Cafe', N'<p>Wellington Restaurant &amp; Café is one of the most luxurious restaurants in Bien Hoa City. MDK Investment Co., Ltd is very honored to be selected by the investor as a consultant - design and construction contractor here. The architectural style that MDK brings to Wellington Restaurant &amp; Café is minimalism, sophistication and elegance. The overall building is designed in the form of modern cubes, which are favored with brown color as the main color. The long glass walls alternating with the brown walls are the highlight for the restaurants exterior appearance, and also create a wide open view on the floors of the building, allowing diners to enjoy the view. outside street. The ground floor is an airy open space with lots of green trees around. The space inside is highlighted by thousands of circular ceiling lights hanging like a sparkling, beautiful starry sky. We choose the furniture for this place all made from wood materials, the natural brown color of the wood combined with the main brown color of the building creates a warm space with light and dark brown transitions. glittering golden light. MDK has also cooperated with its long-standing and reputable partners to provide kitchen equipment, dispensing equipment, smoke extraction systems, floor drains, etc., giving Wellington Restaurant &amp; Café a full service. .</p>', 89000, 250000, N'19 Le Quy Don, Tan Hiep, Bien Hoa City, Dong Nai', N'do-an-2296121986-420.png', 19)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (16, N'GoGi house', N'<p>With the situation of restaurants and eateries springing up like mushrooms after the rain, chain restaurants especially require investors to have experience, capital, as well as a solid understanding of restaurant marketing in order to survive. and monopolize the market. GoGi House is a typical name for the success of chain restaurants - barbecue chains. In December 2017, GoGi House opened its 68th restaurant. Although it has only been on the market for only 4 years, GoGi House has continuously proven its success with a series of achievements to take the throne. Champion in bringing Korean barbecue flavor to Vietnamese consumers. The highlight of GoGi House is the friendliness in branding to consumers. According to BuzzMetrics report on "Top 10 most talked about social media campaigns in November 2017", GoGi Houses "No.1 Tasty GoGi Barbecue" campaign is among the top 5 brands discussed by netizens. The most popular, even outstripping the two veterans, Closeup and OPPO. GoGi House – The #1 Delicious Korean Barbecue Restaurant will take you to the barbecue restaurants in Seoul that have created a reputation for kimchi cuisine. If you have once enjoyed barbecue at GoGi House, you will not be able to forget the "ecstatic" taste of American beef short ribs, lean American beef shoulder, fresh ribs…. When blended with typical Korean spices, how attractive it has become. In addition, the indispensable side dishes such as mixed rice, cold noodles, Kimchi soup and hot pot dishes will also impress you more about Korean cuisine.</p>', 140000, 399000, N'Vincom, L4-09 Shopping Center, Tran Hung Dao, My Binh Ward, Long Xuyen City, An Giang', N'gogi2961219738-420.jpg', 1)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (17, N'My village restaurant', N'<p>My village – a simple, rustic but very familiar name is also the main style of the restaurant. The dish of rice pot is pounded right after eating, creating a very unique feature of the restaurant.” Not too fussy in the layout but still leaving customers with the feeling of a beautiful restaurant – a rustic, simple beauty. menu. Almost all rooms here are decorated with a large picture frame right on the wall. Old Western-style paintings are always attached to the wall and blend in with the general landscape to give the restaurant a very unique feature. Lang Toi Restaurant mainly serves Vietnamese dishes, in addition the restaurant also has a number of dishes. Typical dishes: rice pot pounded - pounded when guests start eating, duck stewed with lotus seeds nuggets, burnt rice, braised cotton wool, fried shrimp from Yunnan... Lang Toi Restaurant mainly serves dishes. Vietnamese food, in addition the restaurant also has some Chinese-style dishes</p>', 45000, 220000, N'32 Tran Nhat Duat, My Long, Long Xuyen City, An Giang', N'myVillage2961219712-420.jpg', 1)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (18, N'Ben Tre floating restaurant', N'<p>Located right at the riverbank on Hung Vuong Street, which is designed in the model of a ship with a dragons head. The three-storey restaurant has a capacity of 500 guests, this is a place worth considering when you want to organize a party, birthday or reception, because of its spacious area, very cool river view. In the midst of charming river scenery and windy space, while enjoying specialties, watching the river and enjoying the sweet and deep melodies of the Southern Don Ca Tai Tu group, all the worries in the streets. The vision disappears, replaced by a feeling of relaxation, lightness and peace. Delicious, delicious dishes, garden specialties such as coconut tofu salad, egg meat braised in coconut water, crawling around a pink fire... especially if you dont eat coconut, its a bit wasteful ^^.</p>', 35000, 115000, N'Hung Vuong, Ward 5, Ben Tre', N'com-dua-ben-tre2961219627-420.jpg', 7)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (19, N'Hoang Pho Food Garden', N'<p>Hoang Pho Food Garden specializes in serving Vietnamese specialties, the variety of dishes and beverages with traditional flavors and experience will give you an authentic feeling about the Vietnamese culture. traditional culture. With a team of professional and hospitable kitchens and service staff, you will enjoy formal parties with relatives, family and friends. Huangpu Food Garden is really outstanding with a total usable area of ????up to 2000 m2, close space but extremely magnificent with a large area that can accommodate 100-200 guests. Convenient, safe parking and a professional and reliable staff. The menu is rich with over 100 specialty dishes meticulously prepared from the talented hands of experienced chefs. Professional and attentive service staff. Huangpu Conference: Huangpu is the venue for conferences, seminars, for companies and businesses...with modern light and sound equipment and professional service staff who will do the work. customer satisfaction. Buffet party, festival, birthday of Huangpu: receive the organization of wedding parties, buffets, parties, birthdays, birthday parties ... especially year-end gatherings with preferential prices. Hoang Pho Banquet: Arranged and decorated luxuriously, giving you a comfortable feeling. Hoang Pho staff: Professionally trained staff from service style to food quality, always satisfy customers. The Food Garden has a close, Asian-style cuisine, the space is a harmonious combination of natural beauty and the ingenuity of the arrangement of the banquet tables. No matter which corner of the restaurant you choose, you will feel the maximum of what nature gives you. Not only interested in space and professional service style, Food Garden always gives top priority to food quality, food hygiene and safety standards are always put on top. Although it is a place specializing in weddings or organizing parties, birthdays and conferences. But besides that, Huangpu Food Garden always has attractive and professional promotions and a very attentive and considerate service staff that will bring you comfort and much joy. Different from other restaurants, Huangpu Food Garden also serves a full range of seafood menus with extremely attractive dishes: clams, scallops, feather scallops, scallops,... cooked on the spot. In addition, there are large and small Vip rooms for agencies, companies or tours. With professionalism and careful investment in dishes, Huangpu Food Garden hopes to bring you peace of mind in terms of quality as well as price! The service motto of Huangpu Food Garden is: "Your joy and satisfaction is our happiness!"</p>', 45000, 85000, N'No. 135D National Highway 60, Binh Phu, Ben Tre', N'amthuc296121965-420.jpg', 7)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (20, N'Lam Đuong Restaurant', N'<p>Lam Duong Seafood Restaurant - Vung Tau has a very cool view overlooking the sea, the staff is young, polite, friendly, beautiful and handsome. The seafood dishes are completely fresh, raised in the lake, so visitors can choose their favorite seafood dishes and prepare them on the spot. There is also an indoor living area, which is used for wedding and birthday events. The restaurant has a fairly spacious parking lot and has many areas for visitors to choose from, the price is not too expensive printed on the list so you can consider your choice carefully. The bathroom is clean, spacious and airy. Those of you who are looking for a place to hold a wedding or birthday, I completely recommend this Lam Duong seafood restaurant because there is a promotion for wedding parties at this place with special offers. treat is very good.</p>', 100000, 300000, N'125B Tran Phu, Ward 5, Vung Tau City, Ba Ria - Vung Tau 790000', N'cua-bien2961219518-420.jpg', 2)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (21, N'Ganh Hao Restaurant', N'<p>The most typical dish of Vung Tau as well as other coastal cities, the first is seafood. It seems that anyone who goes to Vung Tau, must also have a seafood meal to satisfy. Vung Tau has many restaurants serving fresh seafood, but most of them go to Ganh Hao. Ganh Hao restaurant is located on Tran Phu street, Vung Tau city. Whenever you want to visit a restaurant, just ask the people on the roadside and everyone knows the way to point to this delicious restaurant. Ganh Hao restaurant is very crowded and open most days of the week, the busiest is still in the evening. The reason Ganh Hao is famous is because of its quality seafood, rich processing, many delicious dishes, and reasonable prices. Besides, the factor that makes a large number of satisfied diners love is because the restaurant has a beautiful space, overlooking the sea. The beautiful space here is very suitable for both couples having dinner while watching the sunset, suitable for the whole family to have breakfast while watching the sunrise, or anyone who wants to see Vung Tau sea at any place. what time. The menu at Ganh Hao focuses most on seafood dishes. Seafood at the restaurant is delicious, fresh, guaranteed to be caught from the sea and processed in food safety and hygiene. On average, each person eating at the restaurant may need 200-250 thousand. With that cost, visitors can enjoy a variety of Vung Tau delicacies on the menu such as mantis shrimp, baba, lobster, oysters, fried squid with butter, salted and chili shrimp, etc. customers, such as: crab rice, seafood hot pot, salad, soup... In addition, besides seafood, Ganh Hao restaurant also serves unique dishes, from: venison tendon, sea cucumber, abalone to salmon, frozen coriander. Botanical…with acceptable price. Another special feature of Ganh Hao is the richness in flavor. The dishes all complement each others flavors, which will not make diners boring even if they order many dishes on the same food. The restaurant also has a reputation for customer-friendly service, satisfying customers. This also contributes significantly to making the meal at the beach more delicious and the name Ganh Hao adds another point to be remembered in the hearts of tourists.</p>', 125000, 195000, N'03 Tran Phu, Ward 5, Vung Tau City, Ba Ria - Vung Tau', N'haisan296121952-420.jpg', 2)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (22, N'Marukame Udon', N'<p>Marukame Udon – Japan’s no.1 udon noodles has been at the 625 stores in Japan and over the world. Wheat raw materials are mainly imported directly from Japan with the purpose of maintaining the integrity and flavor of Sanuki Udon noodles – Kagawa province. Above all, the customers were seen directly the processes Udon noodles strict compliance with traditional rules. Udon noodles after cooked, if not used, the chefs will “kill” just after 15 minutes. Ensuring food safety is managed in Marukame also quite close to the process for cleaning hands and clothing in accordance with regulations. Customers enjoy a bowl of noodles which was assured by freshness, safety and quality in accordance with Japanese standards.</p>', 82500, 126000, N'Lot S15, 2nd Floor, Aeon Binh Duong Canary Shopping Center, Binh Duong Boulevard, Thuan Giao, Thuan An, Binh Duong', N'udon2961219431-420.jpg', 9)
GO
INSERT [dbo].[Restaurants] ([RestaurantId], [RestaurantName], [Description], [MinPrice], [MaxPrice], [Location], [Thumbnail], [TownId]) VALUES (23, N'Sushiya', N'<p>Referring to Japanese cuisine is referring to the beautiful, delicate and nutritious culinary style. Sushi and Sashimi always attract diners by their vibrant and attractive colors. But, if you only enjoy Sushi or Sashimi, you have not experienced the richness and quintessence of Japanese cuisine. Sushiya restaurant with more than 30 years of experience has received many compliments from Japanese diners for maintaining the original taste of Japanese dishes. Vietnamese diners constantly praise Udon noodles, Sukiyaki Japanese hot pot, grilled dishes, fried rice... delicious and suitable for Vietnamese tastes. Whats more, Sushiya also has a special menu featuring the best of Japanese cuisine for guests who dont know what to choose. With more than 200 Japanese dishes, Sushiya also serves famous dishes of Korean cuisine to help diners have more experiences and comparisons. Self-service counter with free salad and kimchi adds comfort to diners. Another plus point for Sushiya is the beautiful and poetic space surrounded by a lovely small garden located on the banks of the Saigon River. Enjoy delicious food, sip special sake with 18k gold leaves and watch the bustling river and boats. This time, Hotdeal.vn presents you a great opportunity to experience Japanese culinary culture at Sushiya restaurant, which is almost the largest Japanese &amp; Korean restaurant in Binh Duong</p>', 100000, 500000, N'18 / 2A1, QL 13, Ð?i l? Bình Duong, Bình Hoà, Lái Thiêu, Thu?n An, Bình Duong 72000', N'sushi2961219356-420.jpg', 9)
GO
SET IDENTITY_INSERT [dbo].[Restaurants] OFF
GO
SET IDENTITY_INSERT [dbo].[Customers] ON 
GO
INSERT [dbo].[Customers] ([CustomerId], [Username], [Password], [FirstName], [LastName], [Gender], [BirthDate], [Email], [Phone], [Address], [Avatar], [Point], [Status]) VALUES (36, N'ntchien', N'22975d8a5ed1b91445f6c55ac121505b', N'Chien', N'Nguyen Trong ', 1, CAST(N'2000-05-29' AS Date), N'nguyentrongchien@gmail.com', N'0334587834', N'Tra On - Vinh Long', N'100_329612165413-420.jpg', 0, 1)
GO
INSERT [dbo].[Customers] ([CustomerId], [Username], [Password], [FirstName], [LastName], [Gender], [BirthDate], [Email], [Phone], [Address], [Avatar], [Point], [Status]) VALUES (37, N'hducduy', N'22975d8a5ed1b91445f6c55ac121505b', N'Duy', N'Hoang Duc ', 1, CAST(N'2000-03-11' AS Date), N'hoangducduy@gmail.com', N'0334587835', N'Tam Binh - Vinh Long', N'100_1229612165427-420.jpg', 0, 1)
GO
INSERT [dbo].[Customers] ([CustomerId], [Username], [Password], [FirstName], [LastName], [Gender], [BirthDate], [Email], [Phone], [Address], [Avatar], [Point], [Status]) VALUES (38, N'ntklan', N'22975d8a5ed1b91445f6c55ac121505b', N'Kim Lan', N'Nguyen Thanh ', 0, CAST(N'2000-04-03' AS Date), N'kimlan@gmail.com', N'0334587836', N'Chau Thanh - Tra Vinh', N'100_1029612165540-420.jpg', 0, 1)
GO
INSERT [dbo].[Customers] ([CustomerId], [Username], [Password], [FirstName], [LastName], [Gender], [BirthDate], [Email], [Phone], [Address], [Avatar], [Point], [Status]) VALUES (39, N'dtdmy', N'22975d8a5ed1b91445f6c55ac121505b', N'Diem My', N'Doan Thi', 0, CAST(N'2000-11-03' AS Date), N'diemmy@gmail.com', N'0334587837', N'Ninh Kieu - Can Tho', N'300_2029612165548-420.jpg', 0, 1)
GO
INSERT [dbo].[Customers] ([CustomerId], [Username], [Password], [FirstName], [LastName], [Gender], [BirthDate], [Email], [Phone], [Address], [Avatar], [Point], [Status]) VALUES (40, N'mslthi', N'22975d8a5ed1b91445f6c55ac121505b', N'Loan Thi', N'Lo Thi ', 0, CAST(N'2001-05-30' AS Date), N'maisuot@gmail.com', N'0334587838', N'U Minh - Ca Mau', N'300_22961216560-420.jpg', 0, 1)
GO
INSERT [dbo].[Customers] ([CustomerId], [Username], [Password], [FirstName], [LastName], [Gender], [BirthDate], [Email], [Phone], [Address], [Avatar], [Point], [Status]) VALUES (41, N'ntathu', N'22975d8a5ed1b91445f6c55ac121505b', N'Anh Thu', N'Nguyen Thi ', 0, CAST(N'2005-03-12' AS Date), N'anhthu@gmail.com', N'0334587839', N'Tu Mo - KonTum', N'300_1129612165612-420.jpg', 0, 1)
GO
INSERT [dbo].[Customers] ([CustomerId], [Username], [Password], [FirstName], [LastName], [Gender], [BirthDate], [Email], [Phone], [Address], [Avatar], [Point], [Status]) VALUES (42, N'dvgiau', N'22975d8a5ed1b91445f6c55ac121505b', N'Giau', N'Dang Van ', 1, CAST(N'2003-08-11' AS Date), N'vangiau@gmail.com', N'0334587840', N'Chau Thanh - Ben Tre', N'user229612165521-420.jpg', 0, 1)
GO
INSERT [dbo].[Customers] ([CustomerId], [Username], [Password], [FirstName], [LastName], [Gender], [BirthDate], [Email], [Phone], [Address], [Avatar], [Point], [Status]) VALUES (43, N'lnhathue', N'22975d8a5ed1b91445f6c55ac121505b', N'Hue', N'Lo Nhat ', 1, CAST(N'2001-01-02' AS Date), N'nhathue@gmail.com', N'0334587841', N'Ba Ria Vung Tau', N'300_2129612165632-420.jpg', 0, 1)
GO
INSERT [dbo].[Customers] ([CustomerId], [Username], [Password], [FirstName], [LastName], [Gender], [BirthDate], [Email], [Phone], [Address], [Avatar], [Point], [Status]) VALUES (44, N'dquochuy', N'22975d8a5ed1b91445f6c55ac121505b', N'Huy', N'Duong Quoc ', 1, CAST(N'2005-03-29' AS Date), N'quochuy@gmail.com', N'0334587842', N'Tam Binh - Vinh Long', N'300_1429612165638-420.jpg', 0, 1)
GO
INSERT [dbo].[Customers] ([CustomerId], [Username], [Password], [FirstName], [LastName], [Gender], [BirthDate], [Email], [Phone], [Address], [Avatar], [Point], [Status]) VALUES (45, N'ptluan', N'22975d8a5ed1b91445f6c55ac121505b', N'Luan', N'Pham Tro ', 1, CAST(N'2005-05-28' AS Date), N'troluon@gmail.com', N'0334587843', N'Mo Cay - Ca Mau', N'300_62961216578-420.jpg', 0, 1)
GO
INSERT [dbo].[Customers] ([CustomerId], [Username], [Password], [FirstName], [LastName], [Gender], [BirthDate], [Email], [Phone], [Address], [Avatar], [Point], [Status]) VALUES (46, N'ntnmai', N'22975d8a5ed1b91445f6c55ac121505b', N'Ngoc Mai', N'Nguyen Thi ', 0, CAST(N'2003-12-05' AS Date), N'ngocmai@gmail.com', N'0334587844', N'Ninh Kieu - Can Tho', N'300_2029612165723-420.jpg', 0, 1)
GO
SET IDENTITY_INSERT [dbo].[Customers] OFF
GO
SET IDENTITY_INSERT [dbo].[Employees] ON 
GO
INSERT [dbo].[Employees] ([EmployeeId], [Username], [Password], [FirstName], [LastName], [Gender], [BirthDate], [Email], [Phone], [Address], [Avatar], [Point], [Status], [IsAdmin]) VALUES (1, N'phtam342', N'25d55ad283aa400af464c76d713c07ad', N'Tam', N'Pham Hoang', 1, CAST(N'2001-04-01' AS Date), N'phtam342000@gmail.com', N'0327291328', N'Tien Giang', N'MyAvatar29612165755-420.jpg', 0, 1, 1)
GO
SET IDENTITY_INSERT [dbo].[Employees] OFF
GO

