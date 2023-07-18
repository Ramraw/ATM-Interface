# ATM-Interface

#The User class represents the ATM user, containing their name, user ID, user PIN, and 
#the associated Account class that holds the account number and balance.
#The Account class handles account-related operations such as withdrawal, deposit, and transfer. 
#It also maintains the account number and balance.
#The Transaction class represents a single transaction, storing information such as the transaction 
#type (withdrawal, deposit, or transfer), the amount, and in the case of transfers, the recipient's account number.
#When the ATM application starts, it prompts the user to enter their user ID and PIN. If the credentials are valid, 
#the main menu is displayed. The user can select options to view their account balance, withdraw money, deposit money, 
#transfer money, view transaction history, or quit the system.
#The ATM class contains methods to handle each operation, such as viewAccountBalance(), withdrawMoney(), depositMoney(), 
#transferMoney(), and viewTransactionHistory(). It also includes helper methods for authentication and account validation.
