package PPS_2;

import java.io.*;
import java.util.Scanner;

// -----------------------------------------------------
// Assignment: Assignment 2
// Question: Full Program - Parts 1, 2, and 3
// Written by: Dev Patel [40305577], Pranay Patel [40307223]
// -----------------------------------------------------


/**
 * The {@code Book} class represents a book object with details such as title, authors,
 * price, ISBN, genre, and publication year. This class implements the {@code Serializable}
 * interface, allowing book objects to be serialized and deserialized.
 */
// Serializable Book class

class Book implements Serializable {
    private String title;
    private String authors;
    private double price;
    private String isbn;
    private String genre;
    private int year;

    /**
     * Constructs a {@code Book} object with the specified attributes.
     *
     * @param title  the title of the book
     * @param authors the author(s) of the book
     * @param price  the price of the book
     * @param isbn   the ISBN number of the book
     * @param genre  the genre of the book
     * @param year   the year of publication
     */
    public Book(String title, String authors, double price, String isbn, String genre, int year) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
    }

    /**
     * Returns a string representation of the book object.
     *
     * @return a string containing title, authors, price, ISBN, genre, and year
     */
    public String toString() {
        return title + ", " + authors + ", " + price + ", " + isbn + ", " + genre + ", " + year;
    }
}
/**
 * Names and IDs: Dev Patel [40305577], Pranay Patel [40307223]
 * COMP6481
 * Assignment #: 2
 * Due Date: 11th March 2025
 *
 *
 *
 *
 *
 *
 * Description:
 * This Java program performs file-based processing of book records provided in CSV format.
 * It consists of three parts:
 *
 * Part 1:
 * - Reads a list of input file names from a text file.
 * - Parses and validates the syntax of each record.
 * - Sorts the records into genre-specific CSV files.
 * - Logs syntax errors to a "syntax_error_file.txt".
 *
 * Part 2:
 * - Validates the semantics of each record (price, ISBN, year).
 * - Creates Book objects for valid records.
 * - Serializes them into genre-specific .ser files.
 * - Logs semantic errors to "semantic_error_file.txt".
 *
 * Part 3:
 * - Deserializes the .ser files.
 * - Provides an interactive menu-driven interface to view records from the selected files.
 *
 * The program performs robust input validation, custom exception handling, and utilizes
 * object serialization and deserialization for persistent storage.
 *
 * Main Functionalities:
 * - Syntax checking (missing fields, too few/many fields, invalid formatting)
 * - Semantic checking (invalid price, ISBN, year, etc.)
 * - Book object creation and serialization
 * - User-friendly file viewing interface with interactive menus
 *
 * Each book record includes: Title, Authors, Price, ISBN, Genre, and Year.
 *
 * The output is categorized into the following genres:
 * CCB, HCB, MTV, MRB, NEB, OTR, SSM, TPA
 *
 * Welcome and exit messages are also included for a friendly user experience.
 *
 *
 * * The {@code BookProcessor} class handles the processing of book data,
 *  * including categorizing books by genre, serializing/deserializing book data,
 *  * and generating output files.
 *  *
 *  * <p>Each book is categorized into one of the predefined genres,
 *  * and the corresponding output and serialized file names are defined accordingly.</p>
 *  *
 *  * <p>The class contains static methods to perform specific tasks:
 *  * {@code do_part1()}, {@code do_part2()}, and {@code do_part3()}.</p>
 */


public class BookProcessor {
    /**
     * Array of valid genre codes used to categorize books.
     */
    private static final String[] VALID_GENRES = {"CCB", "HCB", "MTV", "MRB", "NEB", "OTR", "SSM", "TPA"};
    /**
     * Array of output file names corresponding to each genre.
     */
    private static final String[] OUTPUT_FILES = {
            "Cartoons_Comics_Books.csv", "Hobbies_Collectibles_Books.csv", "Movies_TV.csv", "Music_Radio_Books.csv",
            "Nostalgia_Eclectic_Books.csv", "Old_Time_Radio.csv", "Sports_Sports_Memorabilia.csv", "Trains_Planes_Automobiles.csv"
    };
    /**
     * Array of serialized file names corresponding to each genre.
     */
    private static final String[] FILE_NAMES = {
            "Cartoons_Comics_Books.csv.ser", "Hobbies_Collectibles_Books.csv.ser",
            "Movies_TV.csv.ser", "Music_Radio_Books.csv.ser",
            "Nostalgia_Eclectic_Books.csv.ser", "Old_Time_Radio.csv.ser",
            "Sports_Sports_Memorabilia.csv.ser", "Trains_Planes_Automobiles.csv.ser"
    };
    /**
     * A two-dimensional array that stores book objects categorized by genre.
     * Each row corresponds to a genre, and each column holds individual books (up to 100).
     */
    private static Book[][] bookLists = new Book[FILE_NAMES.length][100]; // Initializing arrays with size 100 for each file
    /**
     * Array that keeps track of the number of books currently stored in each genre category.
     */
    private static int[] bookCounts = new int[FILE_NAMES.length]; // Track the number of books in each file

    /**
     * The entry point of the program. It executes the three main processing stages:
     * <ul>
     *     <li>{@code do_part1()} - Load and categorize books.</li>
     *     <li>{@code do_part2()} - Serialize categorized book data.</li>
     *     <li>{@code do_part3()} - Deserialize and display book data.</li>
     * </ul>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        do_part1();
        do_part2();
        do_part3();
    }
    /**
     * Performs Part 1 of the book processing pipeline.
     * <p>
     * Reads a list of input filenames, processes each file to validate and categorize book records,
     * and writes the valid records to their corresponding genre output files. Invalid records are
     * logged into a file named {@code syntax_error_file.txt}.
     */
    public static void do_part1() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Dev Patel\\Desktop\\PPS\\PPS_codes\\PPS\\src\\PPS_2\\part1_input_file_names.txt"));
             PrintWriter errorWriter = new PrintWriter(new FileWriter("syntax_error_file.txt"))) {

            int fileCount = Integer.parseInt(br.readLine().trim()); // Read the number of files
            String[] inputFiles = new String[fileCount];

            for (int i = 0; i < fileCount; i++) {
                inputFiles[i] = br.readLine().trim();
            }

            PrintWriter[] genreWriters = new PrintWriter[VALID_GENRES.length];
            for (int i = 0; i < VALID_GENRES.length; i++) {
                genreWriters[i] = new PrintWriter(new FileWriter(OUTPUT_FILES[i]));
            }

            for (String fileName : inputFiles) {
                processFile(fileName, genreWriters, errorWriter);
            }

            for (PrintWriter writer : genreWriters) {
                writer.close();
            }

        } catch (IOException e) {
            System.out.println("Error reading Part1_input_file_names.txt: " + e.getMessage());
        }
    }

    /**
     * Processes a single file line by line and validates each record.
     *
     * @param fileName     Name of the input file to be processed.
     * @param genreWriters Array of writers for each genre to write valid records.
     * @param errorWriter  Writer for logging invalid records and syntax errors.
     */
    private static void processFile(String fileName, PrintWriter[] genreWriters, PrintWriter errorWriter) {
        int n = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Dev Patel\\Desktop\\PPS\\PPS_codes\\PPS\\src\\PPS_2\\" + fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    processRecord(line, fileName, genreWriters);

                } catch (Exception e) {

                    if(n==0){
                        errorWriter.println("Syntax error in file: " + fileName);
                        errorWriter.println("==================================================================");
                        n++;
                    }
                    errorWriter.println("Error: " + e.getMessage());
                    errorWriter.println("Record: " + line + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
        }
    }

    /**
     * Validates and processes a single CSV record line.
     * Checks field count, missing fields, and genre validity.
     * Writes the valid record to the appropriate genre writer.
     *
     * @param line         The CSV record line.
     * @param fileName     The name of the file this record came from.
     * @param genreWriters Array of writers for writing to genre-specific files.
     * @throws Exception If any validation fails (e.g., too many/too few/missing fields, invalid genre).
     */
    private static void processRecord(String line, String fileName, PrintWriter[] genreWriters) throws Exception {
        String[] fields = splitCSV(line);

        // Check for invalid field counts
        if (fields == null || fields.length > 6) {
            throw new TooManyFieldsException("Too many fields");
        }
        if (fields.length < 6) {
            throw new TooFewFieldsException("Too few fields");
        }

        // Check for missing fields
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
            if (fields[i].isEmpty()) {
                throw new MissingFieldException("missing " + getFieldName(i));
            }
        }

        // Ensure genre is valid
        int genreIndex = getGenreIndex(fields[4]);
        if (genreIndex == -1) throw new UnknownGenreException("invalid genre");

        // Write the original line as is, preserving the CSV format
        genreWriters[genreIndex].println(line);
    }


    /**
     * Returns the index of the genre in the VALID_GENRES array.
     *
     * @param genre Genre code to look up.
     * @return Index of the genre if valid; -1 otherwise.
     */
    private static int getGenreIndex(String genre) {
        for (int i = 0; i < VALID_GENRES.length; i++) {
            if (VALID_GENRES[i].equals(genre)) return i;
        }
        return -1;
    }

    /**
     * Returns the name of the field based on its index in the record.
     *
     * @param index Index of the field.
     * @return Field name as a string.
     */
    private static String getFieldName(int index) {
        String[] fieldNames = {"Title", "Authors", "Price", "ISBN", "Genre", "Year"};
        return fieldNames[index];
    }

    /**
     * Splits a CSV line into fields, handling quoted commas and malformed entries.
     *
     * @param line The CSV record line.
     * @return Array of fields if valid; null if formatting error is detected.
     */
    private static String[] splitCSV(String line) {
        String[] result = new String[10];
        int index = 0, start = 0;
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            }
            else if (c == ',' && !inQuotes) {
                if (index >= result.length) {
                    result = expandArray(result);
                }
                String field = line.substring(start, i).trim();

                // If this is NOT the title field (index != 0) and contains a comma → INVALID
                if (index != 0 && field.contains(",")) {
                    return null; // Signal invalid field to `processRecord`
                }

                result[index++] = field.replaceAll("^\"|\"$", "");
                start = i + 1;
            }
        }

        if (index >= result.length) {
            result = expandArray(result);
        }
        String lastField = line.substring(start).trim();

        // Validate last field
        if (index != 0 && lastField.contains(",")) {
            return null;
        }

        result[index++] = lastField.replaceAll("^\"|\"$", "");
        return trimArray(result, index);
    }

    /**
     * Expands the internal field array when it exceeds the default capacity.
     *
     * @param array Existing array of fields.
     * @return Expanded array with double the original size.
     */
    private static String[] expandArray(String[] array) {
        int newSize = array.length * 2;
        String[] newArray = new String[newSize];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    /**
     * Trims the internal field array to the actual number of fields found.
     *
     * @param array Array to be trimmed.
     * @param size  Number of valid elements.
     * @return Trimmed array.
     */
    private static String[] trimArray(String[] array, int size) {
        String[] trimmedArray = new String[size];
        for (int i = 0; i < size; i++) {
            trimmedArray[i] = array[i];
        }
        return trimmedArray;
    }

    /**
     * Processes each genre-based output file from part 1 for semantic errors,
     * such as invalid price, ISBN, or publication year.
     * Valid records are serialized into corresponding `.ser` files.
     */
    public static void do_part2(){
        for (String fileName : OUTPUT_FILES) {
            processFileForSemanticErrors(fileName);
        }
    }

    /**
     * Reads a genre-specific file, validates each book record semantically,
     * reports errors to the `semantic_error_file.txt`, and serializes valid records.
     *
     * @param fileName The name of the genre file to process.
     */
    private static void processFileForSemanticErrors(String fileName) {
        int a = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName));
             PrintWriter errorWriter = new PrintWriter(new FileWriter("semantic_error_file.txt", true))) {

            Book[] books = new Book[1000];
            int bookCount = 0;
            String line;

            while ((line = br.readLine()) != null) {
                try {
                    Book book = validateAndCreateBook(line);
                    books[bookCount++] = book;
                } catch (Exception e) {
                    if(a==0) {
                        errorWriter.println("Semantic error in file: " + fileName);
                        errorWriter.println("=======================================================================");
                        a++;
                    }
                    errorWriter.println("Error: " + e.getMessage());
                    errorWriter.println("Record: " + line + "\n");
                }
            }

            serializeBooks(fileName + ".ser", books, bookCount);
        } catch (IOException e) {
            System.out.println("Error processing file: " + fileName);
        }
    }

    /**
     * Validates the given CSV record semantically and creates a {@link Book} object.
     *
     * @param line The CSV-formatted line representing a book.
     * @return A validated Book object.
     * @throws Exception If any semantic validation fails (price, ISBN, year).
     */
    private static Book validateAndCreateBook(String line) throws Exception {
        String[] fields = splitCSV(line);
        String title = fields[0].trim();
        if (title.contains(",")) {
            title = "\"" + title + "\"";
        }
        String authors = fields[1].trim();
        double price = validatePrice(fields[2].trim());
        String rawIsbn = fields[3].trim();
        String normalizedIsbn = normalizeISBN(rawIsbn);
        String isbn = validateISBN(normalizedIsbn, rawIsbn);
        String genre = fields[4].trim();
        int year = validateYear(fields[5].trim());
        return new Book(title, authors, price, isbn, genre, year);
    }
    /**
     * Normalizes an ISBN string to remove scientific notation (e.g., 1.23E+12).
     *
     * @param isbn The raw ISBN string.
     * @return The normalized ISBN string in plain numeric format.
     */

    public static String normalizeISBN(String isbn) {
        if (isbn.contains("E") || isbn.contains("e")) {  // Detect scientific notation
            // Convert scientific notation to a plain string using String formatting
            return String.format("%.0f", Double.parseDouble(isbn));
        }
        return isbn;
    }

    /**
     * Validates the price value of a book.
     *
     * @param priceStr Price string from the CSV.
     * @return A valid positive price as double.
     * @throws BadPriceException If the price is negative or not a valid number.
     */
    private static double validatePrice(String priceStr) throws BadPriceException {
        try {
            double price = Double.parseDouble(priceStr);
            if (price < 0) throw new BadPriceException("invalid price");
            return price;
        } catch (NumberFormatException e) {
            throw new BadPriceException("Invalid price format: " + priceStr);
        }
    }

    /**
     * Validates the format and checksum of an ISBN.
     *
     * @param isbn Normalized ISBN string.
     * @param risbn Raw ISBN string for error messages.
     * @return A valid ISBN string.
     * @throws BadIsbn10Exception If the ISBN-10 is invalid.
     * @throws BadIsbn13Exception If the ISBN-13 is invalid.
     */
    private static String validateISBN(String isbn, String risbn) throws BadIsbn10Exception, BadIsbn13Exception {
        if (isbn.length() == 10) {
            if (!isbn.matches("\\d{9}[\\dX]")) {
                throw new BadIsbn10Exception("invalid ISBN-10");
            }
            if (!isValidIsbn10(isbn)) {
                throw new BadIsbn10Exception("invalid ISBN-10");
            }
        } else if (isbn.length() == 13) {
            if (!isbn.matches("\\d{13}")) {
                throw new BadIsbn13Exception("invalid ISBN-13");
            }
            if (!isValidIsbn13(isbn)) {
                throw new BadIsbn13Exception("invalid ISBN-13");
            }
        } else {
            throw new IllegalArgumentException("invalid ISBN length");
        }
        return isbn;
    }

    /**
     * Validates the checksum for ISBN-10.
     *
     * @param isbn ISBN-10 string.
     * @return true if valid, false otherwise.
     */
    // ISBN-10 Checksum Validation
    private static boolean isValidIsbn10(String isbn) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (isbn.charAt(i) - '0') * (10 - i);
        }
        char lastChar = isbn.charAt(9);
        int lastDigit = (lastChar == 'X') ? 10 : lastChar - '0';
        sum += lastDigit * 1; // x10 is multiplied by 1

        return sum % 11 == 0;
    }

    /**
     * Validates the checksum for ISBN-13.
     *
     * @param isbn ISBN-13 string.
     * @return true if valid, false otherwise.
     */
    // ISBN-13 Checksum Validation
    private static boolean isValidIsbn13(String isbn) {
        int sum = 0;
        for (int i = 0; i < 13; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        return sum % 10 == 0;
    }

    /**
     * Validates the publication year of the book.
     *
     * @param yearStr Year string from the CSV.
     * @return Valid year as integer.
     * @throws BadYearException If the year is not between 1995 and 2024 or not a number.
     */
    private static int validateYear(String yearStr) throws BadYearException {
        try {
            int year = Integer.parseInt(yearStr);
            if (year < 1995 || year > 2024) throw new BadYearException("invalid year");
            return year;
        } catch (NumberFormatException e) {
            throw new BadYearException("Invalid year format");
        }
    }

    /**
     * Serializes an array of valid Book objects into a binary `.ser` file.
     *
     * @param fileName  The target serialized file name.
     * @param books     Array of valid Book objects.
     * @param bookCount Number of valid books to serialize.
     */
    private static void serializeBooks(String fileName, Book[] books, int bookCount) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            // Serialize each book one by one
            for (int i = 0; i < bookCount; i++) {
                oos.writeObject(books[i]);
            }
        } catch (IOException e) {
            System.out.println("Error serializing file: " + fileName);
        }
    }

    /**
     * Handles deserialization and provides an interactive interface for viewing book records.
     * Allows user to select a file, view contents, or exit the program.
     */
    public static void do_part3() {
        // Deserialize files
        for (int i = 0; i < FILE_NAMES.length; i++) {
            deserializeFile(i);
        }

        // Main interactive loop
        Scanner scanner = new Scanner(System.in);
        int selectedFileIndex = 0; // Initially set to the first file

        while (true) {
            System.out.println("-----------------------------");
            System.out.println("Main Menu");
            System.out.println("-----------------------------");
            System.out.println("v View the selected file: " + FILE_NAMES[selectedFileIndex] + " (" + bookCounts[selectedFileIndex] + " records)");
            System.out.println("s Select a file to view");
            System.out.println("x Exit");
            System.out.print("Enter Your Choice: ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("x")) {
                break;  // Exit the program
            } else if (choice.equalsIgnoreCase("v")) {
                // View the selected file
                viewFile(selectedFileIndex);
            } else if (choice.equalsIgnoreCase("s")) {
                // Select a file to view

                int index = selectFile();
                if(index >= 0 && index <=7){
                    selectedFileIndex = index;// This will update the selected file index
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    /**
     * Deserializes the specified file and populates the book array.
     *
     * @param index The index of the file to deserialize.
     */
    private static void deserializeFile(int index) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAMES[index]))) {
            while (true) {
                try {
                    Book book = (Book) ois.readObject();

                    // Check if the array is full, resize it if necessary
                    if (bookCounts[index] == bookLists[index].length) {
                        bookLists[index] = resizeArray(bookLists[index]);
                    }

                    bookLists[index][bookCounts[index]++] = book; // Add book and increment counter
                } catch (EOFException e) {
                    break; // End of file
                }
            }
        } catch (Exception e) {
            System.out.println("Error deserializing file: " + FILE_NAMES[index]);
        }
    }

    /**
     * Doubles the size of the provided book array.
     *
     * @param oldArray The original array to resize.
     * @return A new array with double the size containing the elements of the old array.
     */
    private static Book[] resizeArray(Book[] oldArray) {
        // Resize the array by doubling its size
        Book[] newArray = new Book[oldArray.length * 2];
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }
    /**
     * Displays a submenu to allow the user to select a file for viewing.
     *
     * @return The index of the selected file or -1 if exit is chosen or invalid input.
     */
    private static int selectFile() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("------------------------------");
            System.out.println("File Sub-Menu");
            System.out.println("------------------------------");

            // Display available files and their record counts
            for (int i = 0; i < FILE_NAMES.length; i++) {
                System.out.println((i + 1) + " " + FILE_NAMES[i] + " (" + bookCounts[i] + " records)");
            }
            System.out.println("9 Exit");

            System.out.print("Enter Your Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (choice == 9) {
                return 0; // Exit to main menu
            } else if (choice >= 1 && choice <= 8) {
                // Set the selected file index
                int selectedFileIndex = choice - 1;
                //System.out.println("Now viewing: " + FILE_NAMES[selectedFileIndex]);
                return selectedFileIndex; // Return the selected file index
            } else {
                System.out.println("Invalid choice. Please try again.");
                            }
        }
    }

    /**
     * Views the contents of the selected file in a paginated manner.
     *
     * @param selectedFileIndex The index of the selected file.
     */

    private static void viewFile(int selectedFileIndex) {
        Scanner scanner = new Scanner(System.in);
        int currentIndex = 0; // Initially set to the first object in the array

        while (true) {
            System.out.println("Viewing: " + FILE_NAMES[selectedFileIndex] + " (" + bookCounts[selectedFileIndex] + " records)");
            System.out.print("Enter the number of records to view (+n or -n, 0 to return): ");
            int n = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (n == 0) {
                break; // Go back to the main menu
            }
            // Adjust the view range based on the input
            if (n > 0) {
                viewForward(selectedFileIndex, currentIndex, n);
                currentIndex = Math.min(currentIndex + n - 1, bookCounts[selectedFileIndex] - 1);
            } else {
                viewBackward(selectedFileIndex, currentIndex, n);
                currentIndex = Math.max(currentIndex + n + 1, 0);
            }
        }
    }

    /**
     * Views the next set of records from the current index.
     *
     * @param selectedFileIndex The index of the selected file.
     * @param currentIndex      The starting index of the view.
     * @param n                 The number of records to display forward.
     */
    private static void viewForward(int selectedFileIndex, int currentIndex, int n) {
        int endIndex = Math.min(currentIndex + n - 1, bookCounts[selectedFileIndex] - 1);
        for (int i = currentIndex; i <= endIndex; i++) {
            System.out.println(bookLists[selectedFileIndex][i]);
        }
        if (currentIndex + n - 1 > bookCounts[selectedFileIndex] - 1) {
            System.out.println("EOF has been reached.");
        }
    }

    /**
     * Views the previous set of records from the current index.
     *
     * @param selectedFileIndex The index of the selected file.
     * @param currentIndex      The current position in the record array.
     * @param n                 The number of records to display backward (negative value).
     */
    private static void viewBackward(int selectedFileIndex, int currentIndex, int n) {
        int startIndex = Math.max(currentIndex + n + 1, 0);
        for (int i = startIndex; i <= currentIndex; i++) {
            System.out.println(bookLists[selectedFileIndex][i]);
        }
        if (currentIndex + n + 1 < 0) {
            System.out.println("BOF has been reached.");
        }
    }
}

class TooFewFieldsException extends Exception {
    public TooFewFieldsException(String message) { super(message); }
}

class TooManyFieldsException extends Exception {
    public TooManyFieldsException(String message) { super(message); }
}

class MissingFieldException extends Exception {
    public MissingFieldException(String message) { super(message); }
}

class UnknownGenreException extends Exception {
    public UnknownGenreException(String message) { super(message); }
}

// Exception classes for semantic errors
class BadIsbn10Exception extends Exception {
    public BadIsbn10Exception(String message) {
        super(message);
    }
}

class BadIsbn13Exception extends Exception {
    public BadIsbn13Exception(String message) {
        super(message);
    }
}

class BadPriceException extends Exception {
    public BadPriceException(String message) {
        super(message);
    }
}

class BadYearException extends Exception {
    public BadYearException(String message) {
        super(message);
    }
}