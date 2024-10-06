## **Library Book Management**
- Create the first iteration of a system that a librarian can use to check out books for library members, and that an administrator can use to add new books to the collection, create new library members, and edit library member information.
- Support the following use cases:
1. Login
• The first screen a user of the system sees is the login screen, which requests ID and
password. When the Submit button is clicked, the ID is looked up in the data store. If this ID
can be found, and if the password for this ID matches the password submitted, the
authorization level is returned. Authorization levels are LIBRARIAN, ADMIN, and BOTH. If
login is successful, UI features are made available according to the authorization level of the
user.
2. Add a new library member to the system.
• When an Administrator selects the option to add a new member, then he is presented with
a form with fields: member id, first name, last name, street, city, state, zip, telephone
number. After the data is entered and submitted, it is persisted using the persistence
mechanism for this project.
3. Checkout a book (if available) for a library member.
• A librarian can enter in a form a member ID and an ISBN number for a book and ask the
system whether the requested item is available for checkout. If ID is not found, the system
will display a message indicating this, or if the requested book is not found or if none of the
copies of the book are available, the system will return a message indicating that the item is
not available. If both member ID and book ID are found and a copy is available, a new
checkout record entry is created, containing the copy of the requested book and the
checkout date and due date. This checkout entry is then added to the member’s checkout
record. The copy that is checked out is marked as unavailable. The updated checkout
record is displayed on the UI and is also persisted. The display of the checkout record uses a
JTable, with all cells of the table read-only (to see an example of a JTable, see the project
ProjectSwingSample code and the class TableExample in the tables package) .
4. Add a copy of an existing book to the library collection.
• An administrator can look up a book by ISBN and add a copy to the collection. The result is
then persisted.
