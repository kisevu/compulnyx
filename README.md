
# Student Management System

This is a Spring Boot + Angular based full-stack application designed for managing student records with features like Excel data generation, secure login via JWT, file processing, photo upload, and advanced filtering.

🚀 Features
✅ Authentication & Security

    JWT-based login system

    All endpoints (except login) are protected

    Test users are auto-generated at application startup

    BCrypt password encryption

✅ Data Generation (Excel)

    Generate student records via REST API

    Randomized data generation (first name, last name, DOB, class, score)

    Excel (.xlsx) file created on disk at:

        Windows: C:\var\log\applications\API\dataprocessing\

        Linux: /var/log/applications/API/dataprocessing/
✅ Data Processing (CSV)

    Reads the generated Excel file

    Updates score (+10) and exports data as CSV

    Stored in the same directory as Excel

✅ Data Upload to DB

    Reads Excel data

    Updates existing student scores (+5) in Microsoft SQL Server

    Follows ID mapping logic


✅ Student CRUD & File Upload

    View, edit, and delete (soft delete with status = 0)

    Upload student photo (JPG/PNG, max 5MB)

    Saves photo with filename format: {studentId}-{filename}

    Stored under:

        Windows: C:\var\log\applications\API\StudentPhotos\

✅ Reporting & Filtering

    Student report table with pagination

    Filters:
        Search by student ID
## steps to run  the app ## 
*********************
* clone the application.
* Given that it is a multi-module type of architecture, I have got different inside with business-logic being the functionaloty module.
*  ensure docker deamon is running.
* use docker compose up to spin up the sql server and the backend.
* I have provided swagger endpoints at http://localhost:8000/swagger-ui.html and you should be able to use then endpoints.
* I have my ui running on http://localhost:4200
* The ui is guarded and therefore without authenticating in the login page none, one cannot navigate or protected resources.