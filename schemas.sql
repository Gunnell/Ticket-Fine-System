--tickets varlığı, oluşturulan mini dünya içerisinde verilebilecek cezaların verilerini saklamaktadır
create table tickets(
tid varchar(6) not null,
tname varchar(100) not null unique,
price int not null,
tpoint int not null,
constraint tickets_pk primary key(tid) 
--tid (abbr. for ticket id), primary key olup cezanın id’sini temsil etmektedir
);

--cars varlığı, mini dünyada bulunan arabaları temsil etmektedir
create table cars(
   platenum char(9) not null,
   ossn char(9) ,
   brand varchar(10),
   primary key (platenum) 
   --platenum, primary key olup arabaların plakalarını temsil etmektedir.
   --foreign key(ossn) references citizens(ssn)
   --henüz tablo oluşturulmadığı için foreign key alter table komutu ile eklenecektir
);
 
 --citizens varlığı, mini dünyadaki vatandaşları içermektedir
create table citizens(
 ssn char(9) not null,
 dlicence numeric default 000000,
 cname varchar(20) not null,
 csurname varchar(20) not null,
 sex char(2),
 dlpoint int not null,
 balance bigint,
 isdlvalid boolean not null,
 constraint citizens_pk primary key(ssn)
 --ssn, primary key olup vatandaşların kimlik numaralarıdır
);
 
 --cars tablosuna ossn foreign key'inin eklenmesi
alter table cars add
foreign key (ossn) references citizens(ssn) ;
 
--police varlığı, mini dünyada ceza verme yetkisine sahip olan polisleri saklamaktadır
create table police(
pid char(7) not null,
fname varchar(20) not null,
lname varchar(20) not null,
--polislerin yaşının 18 ve 55 arasında olmasının kontrolü
age smallint not null check ((age<=55) and (age>=18)), 
sex char(2),
station varchar(20) not null,
partnerid char(7) ,
constraint police_pk primary key(pid),
--pid (abbr. for police id), özniteliği olup polislerin sahip olduğu id’leri temsil etmektedir
constraint police_fk foreign key (partnerid) references police (pid)
 on delete set null on update cascade
--partnerid niteliği, eğer varsa polislerin partnerlerine ait id’lerini temsil etmekte olup varlık kendisine yani pid niteliğine işaret etmektedir
--pid'nin silinmesi durumunda null değeri atanacak, güncellenmesi durumunda otomotik olarak güncellenecektir
);

--giventickets varlığı, mini dünya içerisinde vatandaşlara verilmiş cezaları saklamaktadır
create table giventickets(
--id, 'sequence’ (bigserial) olarak oluşturulmuş olup her ceza eklendiğinde otomatik olarak artmaktadır
id bigserial not null,
tno varchar(6) not null,
gssn char(9) not null,
plateno char(9) not null,
policeid char(7) not null,
gdate date,
gaddress varchar(20) not null,
texpiredate date not null,
--default olarak 'f'e atanmıştır
ispaid boolean default 'f',
constraint giventickets_pk primary key(id),
--id, primary key olup verilen cezaların id’lerini temsil etmektedir
foreign key(tno) references tickets(tid) on delete cascade on update cascade,
--tno niteliği, verilmiş olan cezaların id’lerini tutmakta olup tickets varlığındaki tid niteliğine işaret etmektedir
foreign key(gssn) references citizens(ssn),
--gssn niteliği cezaların verildiği vatandaşların kimlik numaralarını temsil eder ve citizens varlığındaki ssn niteliğine işaret etmektedir
foreign key(policeid) references police(pid), 
--policeid niteliği, cezayı veren polisin id’sini temsil etmektedir ve police varlığındaki pid özelliğine işaret etmektedir.
foreign key(plateno) references cars(platenum) on update cascade
--plateno niteliği, cars tablosundaki platenum niteliğine işaret etmektedir
--plateno güncellenmesi durumunda otomatik olarak güncelenmektedir
);
 

