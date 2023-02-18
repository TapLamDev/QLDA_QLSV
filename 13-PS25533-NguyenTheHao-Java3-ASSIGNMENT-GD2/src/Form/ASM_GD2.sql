Create Database ASM_GD2
Go
Use ASM_GD2


Create Table Accounts
(
	UserName Varchar(30) Primary Key NOT NULL,
	Pass Varchar(30),
	VaiTro Bit,
	MaND Varchar(10),
);

Create Table NguoiDung
(
	MaND Varchar(10) Primary Key NOT NULL,
	TenND NVarchar(30),
	NgaySinh Date,
	CCCD Varchar(15),
	Email Varchar(30),
	SoDT Varchar(10),
	DiaChi NVarchar(100)
);

Create Table SinhVien
(
	MaSV Varchar(50) Primary Key NOT NULL,
	TenSV NVarchar(50) NULL,
	Email Varchar(30) NULL,
	SoDT Varchar(10) NULL,
	GioiTinh Bit,
	DiaChi NVarchar(50) NULL
);

Create Table Diem
(
	MaSV Varchar(50) Primary Key NOT NULL,
	DiemTA Float,
	DiemTH Float,
	DiemGDTC Float
);
-----------------
Alter Table Accounts Add Constraint FK_NguoiDung_Accounts Foreign Key (MaND) References NguoiDung(MaND)
Alter Table Diem Add Constraint FK_Diem_SinhVien Foreign Key (MaSV) References SinhVien(MaSV)


-------------------------------------------
-- Add data
Select * From NguoiDung
Insert Into NguoiDung Values
('ND01','Nguyen The Hao','2003-05-20','080203000262','hao2223@gmail.com','0702103752','Long An'),
('ND02','Nguyen Lam Truong','1999-01-25','095659655394','tamnguyen100@gmail.com','0907473433','Long An'),
('ND03','Dang Hoang Nhut','1999-03-19','65698845617','nhutnooii29@gmail.com','0905689933','TPHCM'),
('ND04','Than Nhan Duc','2003-01-04','886654542626','ducthan833@gmail.com','0705658448','TPHCM')

Select * From Accounts
Insert Into Accounts Values
('NguyenTheHao','Hao123',1,'ND01'),
('NguyenLamTruong','Truong123',0,'ND02')



Select * From SinhVien
Insert Into SinhVien Values
('SV01','Tran Hoang Danh','danhth112@gmail.com','0965640121',1,'Tan Phu'),
('SV02','Nguyen Tuan Anh','anhnt134@gmail.com','0938293963',1,'Thu Duc'),
('SV03','Nguyen Trung Kien','kientrung29@gmail.com','0123456789',1,'Tan Tao'),
('SV04','Cao Duc Thang','thangtoi245@gmail.com','0987999999',1,'Binh Tan'),
('SV05','Le Minh Tri','trile02@gmail.ocm','0767869386',1,'Binh Tan'),
('SV06','Huynh Van Thanh Hieu','hieuhuynh2@gmail.com','0987988999',1,'Tan Phu')


Select * From Diem
Insert Into Diem Values
('SV01',6.5,9.5,8.5),
('SV02',7,7.5,9),
('SV03',8,5.5,8),
('SV04',6.5,9,6.2),
('SV05',10,8.5,9)

select SinhVien.MaSV,TenSV,DiemTH,DiemTA,DiemGDTC,(DiemTA+DiemTH+DiemGDTC)/3.0 AS 'DiemTB'

From SinhVien,Diem
Where SinhVien.MaSV = Diem.MaSV

Select * From Diem
UPDATE Diem Set DiemTA =7.5,DiemTH = 10,DiemGDTC=9.5 Where MaSV = 'SV04'

Select TOP 3 SinhVien.MaSV,TenSV,DiemTH,DiemTA,DiemGDTC,(DiemTA+DiemTH+DiemGDTC)/3.0 AS 'DiemTB'
From SinhVien,Diem Where SinhVien.MaSV = DIeM.MaSV

select * from SinhVien
