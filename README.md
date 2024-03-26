This assignment is built as maven Spring boot project. It has been created using **Java 17**, so you can compile it by 

**mvn clean package**

and in the prduced target folder, using Java 17, you can run the application as:

**java -jar .\New_Cards-0.0.1-SNAPSHOT.jar**

The application will start on localhost:8080 and will boot up a in memory DB, which if it runs for the first time, will initiate with some starting data.

Under the requests folder you can find the different requests created to develop and test the application. 
You can import them in postman and use them.
Please note that first you must use the "authenticate" request, in order to produce a valid JWT token. 

![generate_token](https://github.com/dglyk/cards_assignment/assets/11438108/ef5bcbd7-77c1-4598-8048-a3d28cb59c51)

In DB exist 3 different users you can use: 

userEmail: admin@admin.com 
userPassword: admin
which has admin privilleges

userEmail: member1@members.com
userPassword: pass1

userEmail: member2@members.com
userPassword: pass2

which have member privilleges

After producing the JWT token, you must replace the value following the word Bearer of the value of Authorization in each following request's header tab
![replace_value](https://github.com/dglyk/cards_assignment/assets/11438108/c59206ab-c83d-439a-8cb9-163316548ea5)

You can use the different users and their respective JWT tokens to test the difference in the Access between Admin and member accounts.
For any further information, notes and remarks, please contact me in dglikiotis@gmail.com
