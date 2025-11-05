import java.sql.*;
import java.util.ArrayList;

public class Book {

    int bookid;
    String title;
    String author;
    boolean available;

    String URL = "jdbc:mysql://localhost:3306/librarydb";
    String user = "root";
    String password = "harsh";

    public Book(int bookid, String title, String author, boolean available) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public Book() {
    }

    public void addBook(String title, String author, boolean available) {
        try (Connection con = DriverManager.getConnection(URL, user, password)) {

            String sql = "insert into books (title , author , available) values (? , ? ,  ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setBoolean(3, true);

            ps.executeUpdate();

            System.out.println("Books added successfully!");

        } catch (Exception e) {
            System.out.println("an error occured " + e);
        }

    }

    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> list = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, user, password)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                list.add(new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("available")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}