-- tables
-- Table: Batch_Type
CREATE TABLE Batch_Type (
    Batch_Type_ID number(3,0)  NOT NULL,
    Batch_Type_Name varchar2(40)  NOT NULL,
    Batch_Type_Length number(3,0)  NOT NULL,
    CONSTRAINT Batch_Type_pk PRIMARY KEY (Batch_Type_ID)
) ;

-- Table: Batches
CREATE TABLE Batches (
    Batch_ID number(8,0)  NOT NULL,
    Batch_Name varchar2(60)  NOT NULL,
    Start_Date timestamp  NOT NULL,
    End_Date timestamp  NOT NULL,
    Trainer_ID number(8,0)  NOT NULL,
    Batch_Type_ID number(3,0)  NOT NULL,
    CONSTRAINT Batches_pk PRIMARY KEY (Batch_ID)
) ;

-- Table: Subtopic
CREATE TABLE Subtopic (
    Subtopic_ID number(9,0)  NOT NULL,
    Subtopic_Name_ID number(6,0)  NOT NULL,
    Subtopic_Batch_ID number(8,0)  NOT NULL,
    Subtopic_Status_ID number(1,0)  NOT NULL,
    Subtopic_Date timestamp  NOT NULL,
    CONSTRAINT Subtopic_pk PRIMARY KEY (Subtopic_ID)
) ;

-- Table: Subtopic_Name
CREATE TABLE Subtopic_Name (
    Subtopic_Name_ID number(6,0)  NOT NULL,
    Subtopic_Name varchar2(80)  NOT NULL,
    Subtopic_Topic number(6,0)  NULL,
    Subtopic_Type number(2,0)  NOT NULL,
    CONSTRAINT Subtopic_Name_pk PRIMARY KEY (Subtopic_Name_ID)
) ;

-- Table: Subtopic_Status
CREATE TABLE Subtopic_Status (
    Status_ID number(1,0)  NOT NULL,
    Status_Name varchar2(40)  NOT NULL,
    CONSTRAINT Subtopic_Status_pk PRIMARY KEY (Status_ID)
) ;

-- Table: Subtopic_Type
CREATE TABLE Subtopic_Type (
    Type_ID number(2,0)  NOT NULL,
    Type_Name varchar2(40)  NOT NULL,
    CONSTRAINT Subtopic_Type_pk PRIMARY KEY (Type_ID)
) ;

-- Table: Topic_Name
CREATE TABLE Topic_Name (
    Topic_ID number(6,0)  NOT NULL,
    Topic_Name varchar2(60)  NOT NULL,
    CONSTRAINT Topic_Name_pk PRIMARY KEY (Topic_ID)
) ;

-- Table: Topic_Week
CREATE TABLE Topic_Week (
    Week_ID number(2,0)  NOT NULL,
    Topic_Name_ID number(6,0)  NOT NULL,
    Topic_Batch_ID number(8,0)  NOT NULL,
    Topic_Week_Number number(2,0)  NOT NULL,
    CONSTRAINT Topic_Week_pk PRIMARY KEY (Week_ID)
) ;

-- Table: Users
CREATE TABLE Users (
    User_ID number(8,0)  NOT NULL,
    First_Name varchar2(40)  NOT NULL,
    Middle_Name varchar2(40)  NULL,
    Last_Name varchar2(40)  NOT NULL,
    eMail varchar2(80)  NOT NULL,
    Password varchar2(64)  NOT NULL,
    Role number(2,0)  NOT NULL,
    Batch_ID number(8,0)  NULL,
    Main_Phone varchar2(40)  NOT NULL,
    Second_Phone varchar2(40)  NULL,
    Skype_ID varchar2(40)  NULL,
    Password_Bak varchar2(64)  NULL,
    CONSTRAINT Users_pk PRIMARY KEY (User_ID)
) ;

-- Table: References
CREATE TABLE References (
    Reference_ID number(9, 0) NOT NULL,
    Reference_URL varchar2(100) NOT NULL,
    Subtopic_ID number(9, 0) NOT NULL,
    CONSTRAINT Reference_pk PRIMARY KEY (Reference_ID)
);

-- foreign keys
-- Reference: Batch_Trainer_ID (table: Batches)
ALTER TABLE Batches ADD CONSTRAINT Batch_Trainer_ID
    FOREIGN KEY (Trainer_ID)
    REFERENCES Users (User_ID);

-- Reference: Batches_Batch_Type (table: Batches)
ALTER TABLE Batches ADD CONSTRAINT Batches_Batch_Type
    FOREIGN KEY (Batch_Type_ID)
    REFERENCES Batch_Type (Batch_Type_ID);

-- Reference: Subtopic_Batches (table: Subtopic)
ALTER TABLE Subtopic ADD CONSTRAINT Subtopic_Batches
    FOREIGN KEY (Subtopic_Batch_ID)
    REFERENCES Batches (Batch_ID);

-- Reference: Subtopic_Name_Subtopic_Type (table: Subtopic_Name)
ALTER TABLE Subtopic_Name ADD CONSTRAINT Subtopic_Name_Subtopic_Type
    FOREIGN KEY (Subtopic_Type)
    REFERENCES Subtopic_Type (Type_ID);

-- Reference: Subtopic_Name_Topic_Name (table: Subtopic_Name)
ALTER TABLE Subtopic_Name ADD CONSTRAINT Subtopic_Name_Topic_Name
    FOREIGN KEY (Subtopic_Topic)
    REFERENCES Topic_Name (Topic_ID);

-- Reference: Subtopic_Subtopic_Name (table: Subtopic)
ALTER TABLE Subtopic ADD CONSTRAINT Subtopic_Subtopic_Name
    FOREIGN KEY (Subtopic_Name_ID)
    REFERENCES Subtopic_Name (Subtopic_Name_ID);

-- Reference: Subtopic_Subtopic_Status (table: Subtopic)
ALTER TABLE Subtopic ADD CONSTRAINT Subtopic_Subtopic_Status
    FOREIGN KEY (Subtopic_Status_ID)
    REFERENCES Subtopic_Status (Status_ID);

-- Reference: Topic_Week_Batches (table: Topic_Week)
ALTER TABLE Topic_Week ADD CONSTRAINT Topic_Week_Batches
    FOREIGN KEY (Topic_Batch_ID)
    REFERENCES Batches (Batch_ID);

-- Reference: Topic_Week_Topic_Name (table: Topic_Week)
ALTER TABLE Topic_Week ADD CONSTRAINT Topic_Week_Topic_Name
    FOREIGN KEY (Topic_Name_ID)
    REFERENCES Topic_Name (Topic_ID);

-- Reference: Users_Batch_FK (table: Users)
ALTER TABLE Users ADD CONSTRAINT Users_Batch_FK
    FOREIGN KEY (Batch_ID)
    REFERENCES Batches (Batch_ID);
ALTER TABLE Users ADD CONSTRAINT Users_Email_Unq
    UNIQUE (eMail);
    
-- Reference: Reference_FK (table: References)
ALTER TABLE References ADD CONSTRAINT References_FK
    FOREIGN KEY (Subtopic_ID)
    REFERENCES Subtopic (Subtopic_ID);

-- End of file.

-- Populate some of the tables with the basic information
INSERT INTO Subtopic_Status (Status_ID, Status_Name) VALUES (1, 'Pending/Missed');
INSERT INTO Subtopic_Status (Status_ID, Status_Name) VALUES (2, 'Completed');
INSERT INTO Subtopic_Status (Status_ID, Status_Name) VALUES (3, 'Canceled');

INSERT INTO Batch_Type (Batch_Type_ID, Batch_Type_Name, Batch_Type_Length) VALUES (1, 'Java', 10);
INSERT INTO Batch_Type (Batch_Type_ID, Batch_Type_Name, Batch_Type_Length) VALUES (2, '.NET', 10);
INSERT INTO Batch_Type (Batch_Type_ID, Batch_Type_Name, Batch_Type_Length) VALUES (3, 'SDET', 10);

INSERT INTO Subtopic_Type (Type_ID, Type_Name) VALUES (1, 'Lesson');
INSERT INTO Subtopic_Type (Type_ID, Type_Name) VALUES (2, 'Evaluation');
INSERT INTO Subtopic_Type (Type_ID, Type_Name) VALUES (3, 'Assignments');
INSERT INTO Subtopic_Type (Type_ID, Type_Name) VALUES (4, 'Meeting');
INSERT INTO Subtopic_Type (Type_ID, Type_Name) VALUES (5, 'Misc');

ALTER TABLE Users
    ADD CONSTRAINT Batch_Assign CHECK
    ((Batch_ID IS NULL) OR (Role = 1));
    
    
    
CREATE SEQUENCE subtopic_seq
 START WITH 184
 INCREMENT BY 1;
/
create or replace trigger subtopic_seq_trigg
 BEFORE INSERT on subtopic
 FOR EACH ROW
 BEGIN
   IF :new.Subtopic_ID is NULL THEN
     SELECT subtopic_seq.nextval INTO :new.Subtopic_ID FROM dual;
   END IF;
 END;
/
