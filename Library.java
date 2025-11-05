import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {

    final String URL = "jdbc:mysql://localhost:3306/librarydb";
    final String USER = "root";
    final String PASSWORD = "harsh";

    Scanner sc = new Scanner(System.in);
    Book bookObj = new Book();
    Member memberObj = new Member();

    public void addBook() {
        System.out.print("Enter book title: ");
        String title = sc.nextLine();
        System.out.print("Enter author name: ");
        String author = sc.nextLine();
        boolean available = true;

        bookObj.addBook(title, author, available);
    }

    public void viewBooks() {
        ArrayList<Book> books = bookObj.getAllBooks();
        if (books.isEmpty()) {
            System.out.println(" No books found!");
        } else {
            System.out.println("\n All Books in Library:");
            for (Book b : books) {
                System.out.println("ID: " + b.bookid + " | Title: " + b.title +
                        " | Author: " + b.author +
                        " | Available: " + (b.available ? "Yes" : "No"));
            }
        }
    }

    public void registerMember() {
        System.out.print("Enter member name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        memberObj.registerMember(name, email);
    }

    public void viewMembers() {
        ArrayList<Member> members = memberObj.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("No members found!");
        } else {
            System.out.println("\n Registered Members:");
            for (Member m : members) {
                System.out.println("ID: " + m.memberId + " | Name: " + m.name +
                        " | Email: " + m.email +
                        " | Issued Book ID: " + m.bookId);
            }
        }
    }

    public void issueBook() {
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();
        System.out.print("Enter Book ID to issue: ");
        int bookId = sc.nextInt();
        sc.nextLine();

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps1 = con.prepareStatement("SELECT available FROM books WHERE book_id=?");
            ps1.setInt(1, bookId);
            ResultSet rs = ps1.executeQuery();

            if (rs.next() && rs.getBoolean("available")) {
                PreparedStatement ps2 = con.prepareStatement("UPDATE books SET available=? WHERE book_id=?");
                ps2.setBoolean(1, false);
                ps2.setInt(2, bookId);
                ps2.executeUpdate();

                PreparedStatement ps3 = con.prepareStatement("UPDATE members SET issued_book_id=? WHERE member_id=?");
                ps3.setInt(1, bookId);
                ps3.setInt(2, memberId);
                ps3.executeUpdate();

                System.out.println("Book issued successfully!");
            } else {
                System.out.println("Book not available or not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnBook() {
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement ps1 = con.prepareStatement("SELECT issued_book_id FROM members WHERE member_id=?");
            ps1.setInt(1, memberId);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("issued_book_id");
                if (bookId == 0) {
                    System.out.println("This member has not issued any book.");
                    return;
                }

                PreparedStatement ps2 = con.prepareStatement("UPDATE books SET available=? WHERE book_id=?");
                ps2.setBoolean(1, true);
                ps2.setInt(2, bookId);
                ps2.executeUpdate();

                PreparedStatement ps3 = con.prepareStatement("UPDATE members SET issued_book_id=? WHERE member_id=?");
                ps3.setInt(1, 0);
                ps3.setInt(2, memberId);
                ps3.executeUpdate();

                System.out.println("Book returned successfully!");
            } else {
                System.out.println("Member not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}