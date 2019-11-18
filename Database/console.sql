--------------------------------------------------------------------------------------------------------
-- DDL code to create tables ---------------------------------------------------------------------------

-- create WritingGroup table
-- PK: GroupName
CREATE TABLE WritingGroups (
  GroupName VARCHAR(40) NOT NULL,
  HeadWriter VARCHAR(40) NOT NULL,
  YearFormed VARCHAR(4) NOT NULL,
  Subject VARCHAR(20) NOT NULL,
  CONSTRAINT pk_WritingGroups PRIMARY KEY (GroupName)
);

-- create Publisher table
-- PK: PublisherName
CREATE TABLE Publishers (
  PublisherName VARCHAR(40) NOT NULL,
  PublisherAddress VARCHAR(60) NOT NULL,
  PublisherPhone VARCHAR(20) NOT NULL,
  PublisherEmail VARCHAR(40) NOT NULL,
  CONSTRAINT pk_Publisheres PRIMARY KEY (PublisherName)
);

-- create Books table
-- PK: GroupName, BookTitle
-- FK: GroupName, PublisherName
CREATE TABLE Books (
  GroupName VARCHAR(40) NOT NULL,
  BookTitle VARCHAR(40) NOT NULL,
  PublisherName VARCHAR(40) NOT NULL,
  YearPublished VARCHAR(4) NOT NULL,
  NumberPages VARCHAR(5) NOT NULL,
  CONSTRAINT pk_Books PRIMARY KEY (GroupName, BookTitle)
);

-- add GroupName FK to Books table
ALTER TABLE Books
  ADD CONSTRAINT fk_GroupName FOREIGN KEY (GroupName) REFERENCES WritingGroups (GroupName);

-- add Publisher FK to Books table
ALTER TABLE Books
  ADD CONSTRAINT fk_PublisherName FOREIGN KEY (PublisherName) REFERENCES Publishers (PublisherName);

-------------------------------------------------------------------------------------------------------
-- DML code to populate tables ------------------------------------------------------------------------

-- inserting data into WritingGroups table
INSERT INTO WritingGroups (GroupName, HeadWriter, YearFormed, Subject)
VALUES  ('Arkansas Writers', 'Patricia Aakhus', '1978', 'Fiction'),
        ('Village Writers', 'Hans Aanrud', '1919', 'Romance'),
        ('Fiction Forge', 'David Aaron', '2000', 'Fiction'),
        ('The Phoenix Writers Club', 'Ben Aaronovitch', '1999', 'Non-Fiction'),
        ('Writers of Kern', 'Alexander Aaronsohn', '1950', 'History'),
        ('California Writers Club', 'Christopher Abani', '2009', 'Fantasy'),
        ('Write It Up', 'Edwin A. Abbott', '1995', 'Horror'),
        ('Coffee House Writers Group', 'George Abbott', '2015', 'Children'),
        ('Pikes Peak Writers', 'Peter Abelard', '2002', 'Fiction'),
        ('Women Writing the West', 'Leila Abouzeid', '2000', 'Romance');


-- inserting data into Publishers table
INSERT INTO Publishers (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail)
VALUES  ('TCK Publishing', '29 Hudson Avenue Astoria, NY 11102', '312-232-4224', 'tck@tckpublishin.com'),
        ('Harper Collins', '195 Broadway New York, NY 10007', '212-207-7000', 'contact@harpercollins.com'),
        ('Pan Macmillan', '627 Saint Clair Street Sunflower, MS 38778', '662-569-0729', 'books@panmac.com'),
        ('Arcadia Publishing', '3508 Orchard Street Burnsville, MN 55337', '888-313-2665', 'retailers@arcadiapublishing.com'),
        ('Manning Publications', '4303 Freedom Lane West Point, CA 95255', '209-293-9780', 'contact@manningpub.com');


-- inserting data into Books table
INSERT INTO Books (GroupName, BookTitle, PublisherName, YearPublished, NumberPages)
VALUES  ('Women Writing the West', 'Fairview', 'TCK Publishing', '2019', '120'),
        ('Pikes Peak Writers', 'The Institute', 'Pan Macmillan', '2019', '577'),
        ('Coffee House Writers Group', 'Where the Crawdads Sing', 'Pan Macmillan', '2016', '342'),
        ('Write It Up', 'Before We Were Yours', 'Arcadia Publishing', '2011', '234'),
        ('Writers of Kern', 'We Were the Lucky Ones', 'Arcadia Publishing', '1955', '430'),
        ('Arkansas Writers', 'The Hideaway', 'Harper Collins', '1999', '150'),
        ('Village Writers', 'Sold on a Monday', 'Harper Collins', '1959', '299'),
        ('California Writers Club', 'Little Fires Everywhere', 'TCK Publishing', '2018', '258'),
        ('The Phoenix Writers Club', 'The Silent Patient', 'TCK Publishing', '2000', '189'),
        ('Fiction Forge', 'The Art of Racing in the Rain', 'Arcadia Publishing', '2016', '139'),
        ('Women Writing the West', 'The Dutch House', 'Manning Publications', '2001', '220'),
        ('Women Writing the West', 'The Testaments', 'Pan Macmillan', '2009', '322'),
        ('Coffee House Writers Group', 'The Dressmaker', 'Pan Macmillan', '2019', '142'),
        ('Write It Up', 'Body of Proof', 'Arcadia Publishing', '2018', '211'),
        ('Write It Up', 'The Handmaids', 'Arcadia Publishing', '1975', '130'),
        ('Arkansas Writers', 'The Quantum', 'Arcadia Publishing', '2000', '190'),
        ('California Writers Club', 'My Big Fat Fake Wedding', 'Harper Collins', '2010', '200'),
        ('California Writers Club', 'Lethal Agent', 'TCK Publishing', '2012', '158'),
        ('California Writers Club', 'The Price of Time', 'TCK Publishing', '2015', '169'),
        ('Fiction Forge', 'Pushing Brilliance', 'Arcadia Publishing', '2017', '119');

