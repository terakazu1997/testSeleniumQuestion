package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class changeForeignMoneyData {
    Connection conn = null;
    PreparedStatement ps  = null;
    ResultSet rs = null;
    Statement st = null;
    public void initConnectionDB()  {
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

    public void selectFMUserInfo() {
        initConnectionDB();
        try {
            //-> データを取得するSQLを準備
            ps = conn.prepareStatement(
              "select USER_ID, LAST_FM_PASS_DT, LAST_FM_AD_DT, FM_AD_DAY_COUNT,LAST_FM_SCORE from LUSER where USER_ID = ?"
            );
            //-> SQLを実行してデータを取得
            ps.setString(1, "TEST01");
            rs = ps.executeQuery();
            //-> データを表示
            rs.next();
            System.out.print("ユーザID,");
            System.out.print("最終受講日,");
            System.out.print("最終合格日,");
            System.out.print("当日受講回数,");
            System.out.println("最終取得点数");
            System.out.print(rs.getString("USER_ID"));
            System.out.print(", ");
            System.out.print(rs.getString("LAST_FM_AD_DT"));
            System.out.print(", ");
            System.out.print(rs.getString("LAST_FM_PASS_DT"));
            System.out.print(", ");
            System.out.print(rs.getInt("FM_AD_DAY_COUNT"));
            System.out.print(", ");
            System.out.println(rs.getInt("LAST_FM_SCORE"));
            rs.close();
            ps.close();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

    }
    public void UpdateQuestionDefault() {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
                "DELETE FROM FMUQ WHERE CATEGORY = '2'"
            );
            ps.executeUpdate();
            ps = conn.prepareStatement(
                "DELETE FROM FMUA WHERE CHOICE_NO = '5' OR CATEGORY = '2'"
            );
            ps.executeUpdate();
            ps = conn.prepareStatement(
              "UPDATE FMUA SET DELETE_FLG = '',PATTERN_NO = 'A'"
            );
            ps.executeUpdate();
            ps = conn.prepareStatement(
              "UPDATE FMUQ SET DELETE_FLG = '',PATTERN_NO = 'A'"
            );
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public void UpdateQuestion2() {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
                "UPDATE FMUA SET DELETE_FLG = '1' WHERE CHOICE_NO ='3' OR CHOICE_NO = '4'"
            );
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public void UpdateQuestion3() {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
                "UPDATE FMUA SET DELETE_FLG = '1' WHERE CHOICE_NO = '4'"
            );
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public void UpdateQuestion5() {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
                "select MAX(QUESTION_ID) as MAX_QUESTION_ID FROM FMUA"
            );
            rs = ps.executeQuery();
            rs.next();
            int maxQuestionId = rs.getInt("MAX_QUESTION_ID");
            //-> データを表示
            rs.next();
            //-> データを取得するSQLを準備
            for(int i=1; i<maxQuestionId + 1; i++) {
                ps = conn.prepareStatement(
                    "INSERT INTO FMUA VALUES ('A','1',?,5,'TEST','1','');"
                );
                ps.setInt(1, i);
                ps.executeUpdate();
            }
            //-> SQLを実行してデータを取得
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public void updateAllPass() {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
                "UPDATE FMUQ SET PATTERN_NO = 'B'"
            );
            ps.executeUpdate();
            ps = conn.prepareStatement(
                "UPDATE FMUA SET PATTERN_NO = 'B'"
            );
            ps.executeUpdate();
            for(int i =1; i< 11; i++) {
                ps = conn.prepareStatement(
                     "insert into FMUQ VALUES('A','2',?,?,?,'')"
                 );
                ps.setInt(1, i);
                ps.setString(2, String.format("%d問目の問題", i));
                ps.setString(3, String.format("%d問目の問題の解説", i));
                ps.executeUpdate();
                for(int j=1; j<5; j++) {
                    ps = conn.prepareStatement(
                        "INSERT INTO FMUA VALUES ('A','2',?,?,?,'1','');"
                    );
                    ps.setInt(1, i);
                    ps.setInt(2, j);
                    ps.setString(3, String.format("%d問目の%dつ目の選択肢", i,j));
                    ps.executeUpdate();
                }
            }
         } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public void updateUserFMInfoClear() {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE LUSER SET LAST_FM_PASS_DT = NULL,LAST_FM_AD_DT = NULL,FM_AD_DAY_COUNT = 0 WHERE USER_ID = 'TEST01';");
            st.close();
            conn.close();
            selectFMUserInfo();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }
}