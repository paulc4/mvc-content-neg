drop table T_ACCOUNT_BENEFICIARY if exists;
drop table T_ACCOUNT_CREDIT_CARD if exists;
drop table T_ACCOUNT_PROFILE if exists;
drop table T_ACCOUNT if exists;
drop table T_RESTAURANT if exists;
drop table T_REWARD if exists;
drop sequence S_REWARD_CONFIRMATION_NUMBER if exists;

create table T_CUSTOMER (ID integer identity primary key,
    NUMBER varchar(9) not null, 
    NAME varchar(50) not null,
    USERNAME varchar(16) not null,
    DATE_OF_BIRTH date not null,
    EMAIL varchar(80) not null,
    REWARDS_NEWSLETTER boolean not null,
    MONTHLY_EMAIL_UPDATE boolean not null,
    VERSION integer not null,
    unique(NUMBER));

create table T_ACCOUNT (ID integer identity primary key,
    CUST_ID integer not null,
    NUMBER varchar(9) not null, 
    ACC_TYPE varchar(10) not null,
    CREDIT_CARD varchar(16),
    BALANCE decimal(9,2) not null,
    VERSION integer not null,
    unique(NUMBER, CREDIT_CARD));
    
create table T_ACCOUNT_TRANSACTION (ID integer identity primary key,
    ACCOUNT_ID integer not null,
    NAME varchar(50) not null,
    NUMBER varchar(10) not null,
    CODE char(6) not null,   
    AMOUNT decimal(9,2) not null);

alter table T_ACCOUNT add constraint FK_ACCOUNT
      foreign key (CUST_ID) references T_CUSTOMER(ID) on delete cascade;

alter table T_ACCOUNT_TRANSACTION add constraint FK_ACCOUNT_TRANSACTION
      foreign key (ACCOUNT_ID) references T_ACCOUNT(ID) on delete cascade;
