package database;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatabase {
    protected Connection conn = null;
    protected PreparedStatement ps  = null;
    protected ResultSet rs = null;
    protected Statement st = null;
    protected PrintWriter pw;
    public BaseDatabase() {
     }
    public BaseDatabase(PrintWriter pw) {
       this.pw = pw;
    }
    public void initConnectionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        // (1) 接続用のURIを用意する(必要に応じて認証指示user/passwordを付ける)
        String uri = "jdbc:mysql://localhost:3306/test_mysql_database";
        String user = "root";
        String password = "admin";
        // (2) DriverManagerクラスのメソッドで接続する
        conn = DriverManager.getConnection(uri,user,password);
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

}
