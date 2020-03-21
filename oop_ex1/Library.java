/**
 * This class represents a library, which hold a collection of books.
 * Patrons can register at the library to be able to check out books,
 * if a copy of the requested book is available.
 */

class Library {

    int storageCapcity;

    int borrowedBookCapcity;

    int clientCapcity;

    Book[] booksArray;

    Patron[] clientArray;

    int curBooksInLibrary;

    int curClientsInLibrary;

    int[] curBookBorrowedList;


    /**
     * Creates a new library with the given parameters.
     *
     * @param maxStorageCapacity The maximal number of books this library can hold.
     * @param maxBorrowedBooks   The maximal number of books this library allows a
     *                           single patron to borrow at the same time.
     * @param maxPatronCapacity  The maximal number of registered patrons this library can handle.
     */

    Library(int maxStorageCapacity,
            int maxBorrowedBooks,
            int maxPatronCapacity) {
        storageCapcity = maxStorageCapacity;
        borrowedBookCapcity = maxBorrowedBooks;
        clientCapcity = maxPatronCapacity;

        booksArray = new Book[storageCapcity];
        clientArray = new Patron[clientCapcity];
        curBookBorrowedList = new int[clientCapcity];
        curBooksInLibrary = 0;
        curClientsInLibrary = 0;
    }


    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     *
     * @param bookId The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        return bookId > -1 && curBooksInLibrary > bookId;
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     *
     * @param book The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        for (int currentIndex = 0; currentIndex < curBooksInLibrary; currentIndex++) {
            if (booksArray[currentIndex] == book) {
                return currentIndex;
            }
        }
        return -1;
    }


    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     *
     * @param book book to add to the library
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        if (isBookIdValid(getBookId(book)))
            return getBookId(book);
        else if (curBooksInLibrary < storageCapcity) {
            booksArray[curBooksInLibrary] = book;
            return curBooksInLibrary++;

        }
        return -1;
    }


    /**
     * Returns true if the book with the given id is available, false otherwise.
     *
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        return isBookIdValid(bookId) && booksArray[bookId].getCurrentBorrowerId() == -1;
    }


    /**
     * Returns the non-negative id number of the given patron if he is registered to this library,-1 otherwise.
     *
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library,-1 otherwise.
     */

    int getPatronId(Patron patron) {
        for (int currentIndex = 0; currentIndex < curClientsInLibrary; currentIndex++) {
            if (clientArray[currentIndex] == patron) {
                return currentIndex;
            }
        }
        return -1;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     *
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        if (patronId < 0 || patronId >= curClientsInLibrary) {
            return false;
        }

        return clientArray[patronId] != null;
    }


    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot
     * and the patron was successfully registered, a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {

        if (patron != null) {

            int clientId = getPatronId(patron);
            if (isPatronIdValid(clientId)) {
                return clientId;
            }

            if (curClientsInLibrary < clientCapcity) {
                clientArray[curClientsInLibrary] = patron;
                return curClientsInLibrary++;

            }
        }
        return -1;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id,
     * if this book is available, the given patron isn't already borrowing the maximal number
     * of books allowed, and if the patron will enjoy this book.
     *
     * @param bookId   The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        if (isBookAvailable(bookId) && isPatronIdValid(patronId) &&
                booksArray[bookId].getCurrentBorrowerId() == -1 &&
                curBookBorrowedList[patronId] < borrowedBookCapcity &&
                clientArray[patronId].willEnjoyBook(booksArray[bookId])) {
            curBookBorrowedList[patronId]++;
            booksArray[bookId].setBorrowerId(patronId);
            return true;
        }
        return false;
    }

    /**
     * Return the given book.
     *
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId) {
        if (isBookIdValid(bookId) && !isBookAvailable(bookId)) {
            curBookBorrowedList[booksArray[bookId].currentBorrowerId] -= 1;
            booksArray[bookId].returnBook();

        }
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most,
     * out of all available books he will enjoy, if any such exist.
     *
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given will enjoy the most.
     * Null if no book is available.
     */

    Book suggestBookToPatron(int patronId) {
        Book bookCandidate = null;
        if (isPatronIdValid(patronId) && curBooksInLibrary > 0) {
            Patron client = clientArray[patronId];
            int currentBookCandidateScore;
            int oldBookCandidateScore = client.getBookScore(booksArray[0]);
            for (int curIndex = 0; curIndex < curBooksInLibrary; curIndex++) {
                currentBookCandidateScore = client.getBookScore(booksArray[curIndex]);
                if (isBookAvailable(curIndex) &&
                        client.willEnjoyBook(booksArray[curIndex]) &&
                        oldBookCandidateScore < currentBookCandidateScore) {
                    oldBookCandidateScore = currentBookCandidateScore;
                    bookCandidate = booksArray[curIndex];
                }
            }
        }
        return bookCandidate;
    }
}
