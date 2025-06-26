-- 1. Create the database if not exists
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'compulnyx')
BEGIN
    CREATE DATABASE compulnyx;
END
GO

-- 2. Change context to the new database
USE compulnyx;
GO

-- 3. Create the tbl_users table (since Student is @Embedded inside User)
IF NOT EXISTS (
    SELECT * FROM INFORMATION_SCHEMA.TABLES
    WHERE TABLE_NAME = 'tbl_users'
)
BEGIN
    CREATE TABLE tbl_users (
        student_id BIGINT PRIMARY KEY IDENTITY(1,1),
        username NVARCHAR(100) NOT NULL UNIQUE,
        email NVARCHAR(100) NOT NULL UNIQUE,
        password NVARCHAR(255) NOT NULL,
        enabled BIT NOT NULL,

        -- Embedded Student fields
        first_name NVARCHAR(50) NOT NULL,
        last_name NVARCHAR(50) NOT NULL,
        dob DATE NOT NULL,
        class_name NVARCHAR(20) NOT NULL,
        score INT NOT NULL CHECK (score BETWEEN 55 AND 85),
        status INT NOT NULL DEFAULT 1,
        photo_path NVARCHAR(255) NOT NULL DEFAULT ''
    );
END
GO

-- 4. Insert dummy users with embedded student data
IF NOT EXISTS (SELECT 1 FROM tbl_users)
BEGIN
    INSERT INTO tbl_users (
        username, email, password, enabled,
        first_name, last_name, dob, class_name, score, status, photo_path
    ) VALUES
    ('alice.johnson', 'alice@example.com', 'encoded-password-1', 1,
     'Alice', 'Johnson', '2005-04-12', 'Class1', 75, 1, ''),

    ('bob.smith', 'bob@example.com', 'encoded-password-2', 1,
     'Bob', 'Smith', '2007-09-23', 'Class2', 68, 1, ''),

    ('charlie.brown', 'charlie@example.com', 'encoded-password-3', 1,
     'Charlie', 'Brown', '2006-11-05', 'Class3', 82, 1, '');
END
GO


// excel   generating file in this extension with SDK from Apache POI  .xlsx
// data processing processes data... Generates an csv file