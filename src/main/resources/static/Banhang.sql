create database Banhang
go
use Banhang
go
create table users (
id nvarchar(50) primary key,
pass nvarchar(50),
fullname nvarchar(50),
Email nvarchar(50),
role bit
);

create table sanpham(
masp varchar(20) primary key,
tensp nvarchar(150),
anhsp nvarchar(500),
gia float,
trangthai bit,
madanhmuc nvarchar(200)
CONSTRAINT FK_sanpham_danhmuc FOREIGN KEY (madanhmuc) REFERENCES danhmuc(madm)
);
/*Mã danh mục và tên danh mục giống nhau*/

create table danhmuc
(
madm nvarchar(200) primary key,
tendm nvarchar(200),
);


create table hoadon(
id bigint,
Username nvarchar(50),
CreateDate datetime,
Address nvarchar(50)
);
create table Orderdetail(
id bigint,
name varchar(50),
image nvarchar(500),
Price float,
Quantity int
);
select * from users
insert into users
VALUES
('tuan','123',N'Nguyễn Đình Tuấn','tuanndps36835@fpt.edu.vn',0)
insert into Orders 
VALUES
('001','Võ Tiến Thanh','18-07-2024',N'382/4/1 TX14'),
('002','Đinh Tiền Hải','15-07-2024',N'1A Sô Viết Nghệ tĩnh '),
('003','Vũ Văn Thư','09-07-2024',N'115 Cánh Mạng Tháng Tám '),
('004','Nguyễn Việt Dũng','12-07-2024',N'305/2 TL12'),
('005','Nguyễn Hải Yến Nhi','10-07-2024',N'15 Lý Tự Trọng');\
insert into Orderdetail 
VALUES
('001','ghế bar ENNA',N'src/main/webapp/img/chair/ENNA.jpg',6490000,6),
('002','bàn ăn LUCERA',N'https://haydecor.com/wp-content/uploads/2024/07/ban-an-LUCERA-26.jpg',13590000,1),
('003','Tủ ANNIE',N'https://haydecor.com/wp-content/uploads/2024/07/tu-ANNIA-5.jpg',13290000,4),
('004','bàn ăn LEONA',N'https://haydecor.com/wp-content/uploads/2024/07/ban-an-LEONA-13.jpg',15590000,1),
('005','sofa MIA',N'https://haydecor.com/wp-content/uploads/2024/06/sofa-MIA-3.jpg',24790000,3);
insert into sanpham
VALUES
/*Ghế*/
('1',N'ghế bar ENNA',N'/img/chair/ENNA.jpg',200,1,N'GHẾ'),
('2',N'ghế bar ENNA',N'/img/chair/NOBLE.jpg',229,1,N'GHẾ'),
('3',N'ghế bar ENNA',N'/img/chair/TINNIE.jpg',451,1,N'GHẾ'),
('4',N'ghế bar ENNA',N'/img/chair/DONNO.jpg',122,1,N'GHẾ'),
('5',N'ghế bar ENNA',N'/img/chair/CINDY.jpg',232,1,N'GHẾ'),
('6',N'ghế bar ENNA',N'/img/chair/JENNY.jpg',322,1,N'GHẾ'),
('7',N'ghế bar ENNA',N'/img/chair/jenneret.jpg',532,1,N'GHẾ'),
('8',N'ghế bar ENNA',N'/img/chair/SHINNY.jpg',231,1,N'GHẾ'),
/*Bàn*/
select * from sanpham
insert into sanpham(masp,tensp,anhsp,mota,gia,trangthai,madanhmuc)
VALUES
('9',N'Bàn ăn LUCERA',N'/img/table/LUCERA.jpg',N'Bàn ăn tuyệt vời',200,1,N'BÀN'),
('10',N'Bàn ăn LEONA',N'/img/table/LEONA.jpg',N'Bàn ăn tuyệt vời',229,1,N'BÀN'),
('11',N'Bàn cafe Sakutara',N'/img/table/SATARU.jpg',N'Bàn ăn tuyệt vời',451,1,N'BÀN'),
('12',N'Bàn console IKACHI',N'/img/table/IKACHI.jpg',N'Bàn ăn tuyệt vời',122,1,N'BÀN'),
('13',N'Bàn ăn Mushroom',N'/img/table/MUSHROOM.jpg',N'Bàn ăn tuyệt vời',232,1,N'BÀN'),
('14',N'Bàn ăn LANDO',N'/img/table/LANDO.jpg',N'Bàn ăn tuyệt vời',322,1,N'BÀN'),
('15',N'Bàn ăn PINWHEEL',N'/img/table/PINWHEEL.jpg',N'Bàn ăn tuyệt vời',532,1,N'BÀN'),
('16',N'Bàn ăn Frammy',N'/img/table/FRAMMY.jpg',N'Bàn ăn tuyệt vời',231,1,N'BÀN'),
('17',N'Bàn ăn BRION',N'/img/table/BRION.jpg',N'Bàn ăn tuyệt vời',122,1,N'BÀN'),
('18',N'Bàn ăn HANA',N'/img/table/HANA.jpg',N'Bàn ăn tuyệt vời',231,1,N'BÀN'),
('19',N'Bàn ăn LUCAS',N'/img/table/LUCAS.jpg',N'Bàn ăn tuyệt vời',121,1,N'BÀN'),
('20',N'Bàn ăn MALIBU',N'/img/table/MALIBU.jpg',N'Bàn ăn tuyệt vời',531,1,N'BÀN')
/*Tủ kệ*/

INSERT INTO danhmuc (madm, tendm) VALUES
(N'Ấm chén', N'Ấm chén'),
(N'BÀN', N'BÀN'),
(N'Bàn ăn', N'Bàn ăn'),
(N'Bàn ban công', N'Bàn ban công'),
(N'Bàn cafe', N'Bàn cafe'),
(N'Bàn cho bé', N'Bàn cho bé'),
(N'Bàn console', N'Bàn console'),
(N'Bàn góc', N'Bàn góc'),
(N'Bàn làm việc', N'Bàn làm việc'),
(N'Bàn trang điểm', N'Bàn trang điểm'),
(N'BẾP', N'BẾP'),
(N'Bình đựng', N'Bình đựng'),
(N'BÌNH HOA', N'BÌNH HOA'),
(N'BỘ ĐỒ GỐM SỨ', N'BỘ ĐỒ GỐM SỨ'),
(N'Bộ men Biển Xanh', N'Bộ men Biển Xanh'),
(N'Bộ men Cánh Gián', N'Bộ men Cánh Gián'),
(N'Bộ men Dubai', N'Bộ men Dubai'),
(N'Bộ men Gấm Vàng', N'Bộ men Gấm Vàng'),
(N'Bộ men Hồng Tuyết', N'Bộ men Hồng Tuyết'),
(N'ĐÈN', N'ĐÈN'),
(N'Đèn bàn', N'Đèn bàn'),
(N'Đèn sàn', N'Đèn sàn'),
(N'Đèn trần', N'Đèn trần'),
(N'Đèn tường', N'Đèn tường'),
(N'Đĩa', N'Đĩa'),
(N'ĐỒ DÙNG BÀN ĂN', N'ĐỒ DÙNG BÀN ĂN'),
(N'ĐỒ TRANG TRÍ', N'ĐỒ TRANG TRÍ'),
(N'Đôn', N'Đôn'),
(N'Đồng hồ', N'Đồng hồ'),
(N'GHẾ', N'GHẾ'),
(N'Ghế ăn', N'Ghế ăn'),
(N'Ghế ban công', N'Ghế ban công'),
(N'Ghế băng', N'Ghế băng'),
(N'Ghế bar', N'Ghế bar'),
(N'Ghế cafe', N'Ghế cafe'),
(N'Ghế cho bé', N'Ghế cho bé'),
(N'Ghế divan', N'Ghế divan'),
(N'Ghế đôn', N'Ghế đôn'),
(N'Ghế thư giãn', N'Ghế thư giãn'),
(N'Ghế trang điểm', N'Ghế trang điểm'),
(N'Ghế văn phòng', N'Ghế văn phòng'),
(N'Giá treo', N'Giá treo'),
(N'Giường', N'Giường'),
(N'Giường cho bé', N'Giường cho bé'),
(N'Gối sofa', N'Gối sofa'),
(N'Gương', N'Gương'),
(N'Kệ sách', N'Kệ sách'),
(N'Kệ tivi', N'Kệ tivi'),
(N'Kệ trang trí', N'Kệ trang trí'),
(N'Khay', N'Khay'),
(N'Lồng đèn nến', N'Lồng đèn nến'),
(N'Ly tách', N'Ly tách'),
(N'LY TÁCH & ẤM CHÉN', N'LY TÁCH & ẤM CHÉN'),
(N'Muỗng đũa các loại', N'Muỗng đũa các loại'),
(N'PHÒNG NGỦ', N'PHÒNG NGỦ'),
(N'PHỤC VỤ BÀN ĂN', N'PHỤC VỤ BÀN ĂN'),
(N'Sofa', N'Sofa'),
(N'Tô chén', N'Tô chén'),
(N'TRANG TRÍ TƯỜNG', N'TRANG TRÍ TƯỜNG'),
(N'Tranh và khung ảnh', N'Tranh và khung ảnh'),
(N'Tủ Bếp', N'Tủ Bếp'),
(N'Tủ đầu giường', N'Tủ đầu giường'),
(N'Tủ giầy dép', N'Tủ giầy dép'),
(N'TỦ KỆ', N'TỦ KỆ'),
(N'Tủ ly chén', N'Tủ ly chén'),
(N'Tủ quần áo', N'Tủ quần áo'),
(N'Tủ trang điểm', N'Tủ trang điểm'),
(N'Uncategorized', N'Uncategorized'),
(N'VẬT PHẨM TRANG TRÍ', N'VẬT PHẨM TRANG TRÍ');
