import java.util.Scanner;

public class testLibrary {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\n---------Library Management System ---------");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Register Member");
            System.out.println("4. View All Members");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> library.addBook();
                case 2 -> library.viewBooks();
                case 3 -> library.registerMember();
                case 4 -> library.viewMembers();
                case 5 -> library.issueBook();
                case 6 -> library.returnBook();
                case 7 -> {
                    System.out.println("Exiting System. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }
}