<h1>Human_Resource_Management_System</h1>
<h2>This is a human resource management system where admin can manage everything between employees and departments</h3>

<h3>Work Flow diagram <h3/>

![Presentation_Img](https://user-images.githubusercontent.com/102857782/219963184-83259c11-ca94-45fc-91f6-56dfa98f9c0d.png)

<h3>Database Schema<h3/>

![Screenshot (35)](https://user-images.githubusercontent.com/102857782/219963220-daa634cf-4cce-4e04-a42b-6e9136f571cd.png)



<h3>Admin login details are hardcoded <h3/>
<p>Admin email : admin@123.com</p>
<p>Admin password : admin</p>

<h3> To Run this application first create a database in my sql</h3>
<p>Change db.password and db.username in DBdetail.properties file according to ur mysql username and password </p>

<h2>Next follow these steps in your mysql and copy paste these queries</h2>
<p>1) create database humanresource;</p>
<p>2) use humanresource;</p>
<p>3) create table Department(did int PRIMARY KEY AUTO_INCREMENT, dname varchar(20), location varchar(20));</p>
<p>4) create table Employee(eid int PRIMARY KEY AUTO_INCREMENT, ename varchar(20), email varchar(50) not null, password varchar(15) not null, deptid int , empLeave varchar(15) , FOREIGN KEY(deptid) REFERENCES Department(did));</p>

<h3>Now go to the usecase folder and run the application from main file </h3>


