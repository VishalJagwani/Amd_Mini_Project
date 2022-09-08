create table bank(accNo NUMBER primary key, fname varchar2(10) not null, lname varchar2(10) not null, balance NUMBER(6,2) not null);

insert into bank values(2, 'poonam','jagwani',2000.0);

select * from bank;

truncate table bank;

--create or replace function checkRec(accNo1 in number)
--return number
--is
--accNo1 bank.accNo%type;
--recex bank.accNo%type;
--begin
--    select count(*) into recex from bank where accNo = accNo1 and ROWNUM =1;
--    if recex =1 
--    then
--    dbms_output.put_line('Record exists');
--    else
--    dbms_output.put_line('Doesnt exists');
--    end if;
--end;
--set SERVEROUTPUT on;
--declare
--recor INTEGER;
--begin
--    select count(*) into recor from bank where accNo = 5 and ROWNUM =1;
--    if recor =1 
--    then
--        dbms_output.put_line('Record exists');
--    else    
--        dbms_output.put_line('Doesnt exists');
--  end if;
--end;

create or replace function chckRec(accNo1 in INTEGER)
return number
is
recor bank.accNo%type;
begin
    select count(*) into recor from bank where accNo = accNo1;
    if recor =1 
	then
        return 1;
    else    
        return 0;
    end if;
end;

declare
val bank.accNo%type;
begin
    val := chckRec(14);
end;





