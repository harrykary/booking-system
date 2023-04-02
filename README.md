
# Booking System


Build a simple Java application for the use case of booking a Show. The program must take input from command line.

The program would set up available seats per show, allow buyers to select 1 or more available seats and buy/cancel tickets.

The application shall cater to the below 2 types of users & their requirements –
* Admin
* Buyer


Admin – The users should be able to Setup and view the list of shows and seat allocations.
Commands to be implemented for Admin :

* `Setup  <Show Number> <Number of Rows> <Number of seats per row>  <Cancellation window in minutes>`

(To setup the number of seats per show)

*  `View <Show Number>`

(To display Show Number, Ticket#, Buyer Phone#, Seat Numbers allocated to the buyer)

Buyer – The users should be able to retrieve list of available seats for a show, select 1 or more seats , buy and cancel tickets.
Commands to be implemented for Buyer :

*  `Availability  <Show Number>`

(To list all available seat numbers for a show. E,g A1, F4 etc)

*  `Book  <Show Number> <Phone#> <Comma separated list of seats>`

(To book a ticket. This must generate a unique ticket # and display)

*  `Cancel  <Ticket#>  <Phone#>`

(To cancel a ticket. See constraints in the section below)

# Constraints

1. Assume max seats per row is 10 and max rows are 26.
   Example seat number A1,  H5 etc. The “Add” command for admin must ensure rows cannot be added beyond the upper limit of 26.
2. After booking, User can cancel the seats within a time window of 2 minutes (configurable).   
   Cancellation after that is not allowed.

3. Only one booking per phone# is allowed per show.

# Requirements

1.  Implement the solution as Java standalone application (Java 8+). Can be Springboot as well. The data shall be in-memory.

2.  Write appropriate Unit Tests.

3.  Implement the above use case considering object-oriented principles and development best practices. The implementation should be a tested working executable.

# Additional Note
Just make an assumption if anything is not mentioned here. The same can be highlighted in the readme notes when submitting.

# Assumptions

1. After application started, it needs to prompt for login by taking username as parameter. This is done to maintain user context

To Login use below command
* `login <username>`
  where username is case-sensitive

Below are default users in system.

User Name  | Role
------------- | -------------
Hari  | Admin
Kari  | Buyer
Harry  | Buyer

2. Added logout command to clear user context. Logout should be used between different roles.
* `logout`

3. Additional command Quit is introduced to bring down/Exit from application.
* `quit`

# Build
To build application to generate a jar file
* `mvn clean install`

# Run application
To run application change the directory to target where executable jar is created after build

` cd target `

` java -jar booking-system-1.0-SNAPSHOT.jar`

When prompted with
`Enter command:` proceed with command

* To login as Admin

  `login Hari`

* To set up show

  `setup SHOW12345 3 5 1`
* To view booking details of show

  `view SHOW12345`

* To logout

  `logout`

* To login as Buyer

  `login Kari`

* To see availability

  `Availability SHOW12345`

* To book seats

  `Book SHOW12345 91234567 A1,A2,A3`

* To cancel seats

  `Cancel  <TicketNumber> 91234567`

* To logout

  `logout`
* To quit application

  `quit`
  
  __Note:__ Once quit is executed Memory is cleared

