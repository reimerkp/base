--Select Statements

select * from "Employee"; -- selecting all values from employee

select * from "Employee" where "LastName" = 'King'; -- selecting all values from employee where the last name is king

select * from "Album" order by "Title" desc; -- selecting all albums from Album, sorting by title descending order

select "FirstName" from "Customer" order by "City"; -- selecting first name from customer sorting by city in ascending order

select * from "Invoice" where "BillingAddress" like 'T%'; -- selects all invoices where the billing address starts with a T

select "Name" from "Track" where "Milliseconds" = (select max("Milliseconds") from "Track"); -- finding the name of the longest track

select avg("Total") from "Invoice"; -- finding the average of the total column in invoice

select "Title", count(*) from "Employee" group by "Title"; -- find the number of employees in each role 

-----------------------------------------------------------------

--Insert Statements

insert into "Genre"values(26,'Psychedelic Rock'),(27,'Smooth Jazz'); -- inserting two values into Genre 

-- Inserting two values into the Employee Table
INSERT INTO "Employee" values
(9, N'Reimer', N'Kyle', N'IT Manager', 1, '1997/7/3', '2019/4/20', N'123 Lovely Way', N'Newport', N'WY', N'Germany', N'T23 14D', N'+1 (324) 223-4483', N'+1 (324) 452-2111', N'kyle@chinookcorp.com'),
(10, N'Biggs', N'Biggy', N'IT Dancer', 1, '1999/2/1', '2011/4/10', N'1442 Dance Away', N'Giggy', N'VA', N'Italy', N'B00 T1E', N'+1 (345) 111-3456', N'+1 (123) 193-3491', N'giggy@chinookcorp.com');

-- Insert two values into Customer table
insert into "Customer" values 
(60, N'Kyle', N'Reimers', N'Apple Inc.', N'059 Maywood Pass', N'Culpepper', N'VA', N'USA', N'95321', N'+1 (302) 449-1029', N'+1 (232) 411-9400', N'kyle@apple.com', 3),
(61, N'Jim', N'Jeffers', N'Microsoft Inc.', N'80 Pennsylvania Way', N'Cokoo', N'CA', N'USA', N'94391', N'+1 (123) 345-5392', N'+1 (234) 543-2345', N'jim@microsoft.com', 3);

----------------------------------------------------------------------

--Update Statements

--Update Aaron Mitchell and change his name to Robert Walter
update "Customer"
set "FirstName" = 'Robert',
"LastName" = 'Walter'
where "LastName" = 'Mitchell';

--Update Creedence Clearwater Revivals name to CCR in Artist Table
update "Artist"
set "Name" = 'CCR'
where "Name" = 'Creedence Clearwater Revival';


------------------------------------------------------------------------

--Join commands

select * from "Customer";
select * from "Invoice";

--Inner join the firstName column from customer and invoiceID column from invoice based on the customerId in both, ordering alphabetically by first name
select "FirstName", "InvoiceId" from "Customer" c inner join "Invoice" e on e."CustomerId" = c."CustomerId" order by "FirstName";

--Outer Join of Customer and Invoice tables 
select c."CustomerId", c."FirstName", c."LastName",i."InvoiceId",i."Total" from "Customer" c full join "Invoice" i on c."CustomerId" = i."CustomerId";

--Right join of Artist and Album tables based on artistId ordered by Artist name
select 	a."Name", b."Title" from  "Artist" a right join "Album" b on a."ArtistId" = b."ArtistId" order by a."Name";

--Cross joining artist and album, ordering by name
select * from "Artist" cross join "Album" order by "Name";

--Inner joining the employee table stating who works for whom according to the reportsTo column
select 
e."FirstName"|| ' ' || e."LastName" Employee, 
m."FirstName"|| ' ' || m."LastName" manager
from "Employee" e
inner join "Employee" m on m."EmployeeId" = e."ReportsTo" 
order by manager;
