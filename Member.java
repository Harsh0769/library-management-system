import java.sql.*;
import java.util.ArrayList;

public class Member {
    int memberId;
    String name;
    String email;
    int bookId;

    String URL = "jdbc:mysql://localhost:3306/librarydb";
    String USER = "root";
    String PASSWORD = "harsh";

    public Member(int memberId, String name, String email, int bookId) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.bookId = bookId;
    }

    public Member() {
    }

    public void registerMember(String name, String email) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO members (name, email, issued_book_id) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, 0);
            ps.executeUpdate();
            System.out.println("Member registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Member> getAllMembers() {
        ArrayList<Member> list = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM members");
            while (rs.next()) {
                list.add(new Member(
                        rs.getInt("member_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("issued_book_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
