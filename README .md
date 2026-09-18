# Student Placement Management System Using Java

## 1. Project Overview

The Student Placement Management System is a Java-based console application developed as a college project.

The main purpose of this project is to make basic placement activities easier to manage. Students can view available jobs, check their eligibility, apply for suitable jobs, and track their application status.

The admin can manage students, companies, jobs, applications, and placement statistics.

This project is implemented using Java and focuses on concepts such as Object-Oriented Programming, classes and objects, ArrayList, methods, loops, conditional statements, switch case, and exception handling.

---

## 2. Problem Statement

In a college placement process, information about students, companies, job openings, eligibility criteria, and applications needs to be managed properly.

If these activities are handled manually, it can become difficult to:

- Maintain student information
- Manage company and job details
- Check whether a student is eligible for a job
- Record job applications
- Track application status
- View basic placement information

This project provides a simple Java application to manage these basic placement activities in one system.

---

## 3. Objectives

The main objectives of the project are:

1. To maintain student placement information.
2. To manage company and job details.
3. To check student eligibility for different jobs.
4. To allow eligible students to apply for jobs.
5. To track job application status.
6. To allow an admin to update application status.
7. To display basic placement statistics.
8. To apply Java and Object-Oriented Programming concepts in a practical project.

---

## 4. Features

### Student Module

- Student login using Student ID
- View student profile
- View available jobs
- Check job eligibility
- Apply for an eligible job
- View submitted applications
- Track application status
- Update basic profile information

### Admin Module

- Admin login
- View students
- View companies
- View jobs
- Add new student
- Add new company
- Add new job
- View all applications
- Update application status
- View placement statistics

---

## 5. Eligibility Checking

Before applying for a job, the system checks the student's details against the job requirements.

The following conditions are checked:

- Minimum CGPA
- Branch
- Maximum allowed backlogs
- Required skills

For example:

```text
Minimum CGPA : 7.5
Branch       : CSE
Backlogs     : 0
Skills       : Java, SQL