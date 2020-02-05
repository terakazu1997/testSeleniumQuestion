package database;
import java.io.PrintWriter;
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
    PrintWriter pw;
    public changeForeignMoneyData() {
    }
    public changeForeignMoneyData(PrintWriter pw) {
       this.pw = pw;
    }
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
    public void selectFMQuestionInfo() {
        initConnectionDB();
        try {
            //-> データを取得するSQLを準備
            ps = conn.prepareStatement(
                    "SELECT\n" +
                    "        CONCAT('パターン:',FMUQ.PATTERN_NO,' カテゴリ:',FMUQ.CATEGORY,' 問題番号:',FMUQ.QUESTION_ID,' 選択肢:',CHOICE_NO) AS uniqueNum\n" +
                    "        ,QUESTION_SENTENCE\n" +
                    "        ,QUESTION_EXPLANATION\n" +
                    "         ,CASE FMUQ.DELETE_FLG\n" +
                    "         WHEN '1' THEN '削除済み'\n" +
                    "         ELSE '削除されてない'\n" +
                    "         END AS '問題削除有無'\n" +
                    "        ,CHOICE_SENTENCE\n" +
                    "        ,CASE OK_FLG\n" +
                    "         WHEN '1' THEN '正答'\n" +
                    "         ELSE '不正答'\n" +
                    "         END as '正否'\n" +
                    "         ,CASE FMUA.DELETE_FLG\n" +
                    "         WHEN '1' THEN '削除済み'\n" +
                    "         ELSE '削除されてない'\n" +
                    "         END AS '回答削除有無'\n" +
                    "    FROM\n" +
                    "        test_mysql_database.FMUQ as FMUQ\n" +
                    "        INNER JOIN test_mysql_database.FMUA as FMUA\n" +
                    "        on FMUQ.PATTERN_NO = FMUA.PATTERN_NO\n" +
                    "        AND FMUQ.CATEGORY = FMUA.CATEGORY\n" +
                    "        AND FMUQ.QUESTION_ID = FMUA.QUESTION_ID" +
                    "    ORDER BY\n" +
                    "        FMUQ.PATTERN_NO, FMUQ.CATEGORY, FMUQ.QUESTION_ID"
            );
            //-> SQLを実行してデータを取得
            rs = ps.executeQuery();
            //-> データを表示
            pw.print("パターン-カテゴリ-問題番号-選択肢の連番,");
            pw.print("問題文,");
            pw.print("解説,");
            pw.print("問題削除有無,");
            pw.print("選択肢内容,");
            pw.print("正否,");
            pw.println("正否削除有無");
            while(rs.next()) {
                pw.print(rs.getString("uniqueNum"));
                pw.print(", ");
                pw.print(rs.getString("QUESTION_SENTENCE"));
                pw.print(", ");
                pw.print(rs.getString("QUESTION_EXPLANATION"));
                pw.print(", ");
                pw.print(rs.getString("問題削除有無"));
                pw.print(", ");
                pw.print(rs.getString("CHOICE_SENTENCE"));
                pw.print(", ");
                pw.print(rs.getString("正否"));
                pw.print(", ");
                pw.println(rs.getString("回答削除有無"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public void selectFMUserInfo() {
        initConnectionDB();
        try {
            //-> データを取得するSQLを準備
            ps = conn.prepareStatement(
              "select USER_ID, LAST_FM_PASS_DT, LAST_FM_AD_DT, FM_AD_DAY_COUNT,LAST_FM_SCORE,LAST_FM_PASS_NO from LUSER where USER_ID = ?"
            );
            //-> SQLを実行してデータを取得
            ps.setString(1, "A497480");
            rs = ps.executeQuery();
            //-> データを表示
            rs.next();
            pw.print("ユーザID,");
            pw.print("最終受講日,");
            pw.print("最終合格日,");
            pw.print("当日受講回数,");
            pw.println("最終取得点数");
            pw.println("最終合格番号");
            pw.print(rs.getString("USER_ID"));
            pw.print(", ");
            pw.print(rs.getString("LAST_FM_AD_DT"));
            pw.print(", ");
            pw.print(rs.getString("LAST_FM_PASS_DT"));
            pw.print(", ");
            pw.print(rs.getInt("FM_AD_DAY_COUNT"));
            pw.print(", ");
            pw.print(rs.getInt("LAST_FM_SCORE"));
            pw.print(", ");
            pw.println(rs.getInt("LAST_FM_PASS_NO"));
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
            selectFMQuestionInfo();
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
            selectFMQuestionInfo();
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
            selectFMQuestionInfo();
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
            selectFMQuestionInfo();
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
                "UPDATE FMUQ SET PATTERN_NO = 'X'"
            );
            ps.executeUpdate();
            ps = conn.prepareStatement(
                "UPDATE FMUA SET PATTERN_NO = 'X'"
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
            selectFMQuestionInfo();
            ps.close();
         } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public void updateQuestionRandom25() {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
                "UPDATE FMUQ SET PATTERN_NO = 'X'"
            );
            ps.executeUpdate();
            ps = conn.prepareStatement(
                "UPDATE FMUA SET PATTERN_NO = 'X'"
            );
            ps.executeUpdate();
            for(int i =1; i< 26; i++) {
                ps = conn.prepareStatement(
                     "insert into FMUQ VALUES('A','2',?,?,?,'')"
                 );
                ps.setInt(1, i);
                ps.setString(2, String.format("%d問目の問題", i));
                ps.setString(3, String.format("%d問目の問題の解説", i));
                ps.executeUpdate();
                for(int j=1; j<5; j++) {
                    if(j <= 2) {
                        ps = conn.prepareStatement(
                            "INSERT INTO FMUA VALUES ('A','2',?,?,?,'1','');"
                        );
                    } else {
                        ps = conn.prepareStatement(
                            "INSERT INTO FMUA VALUES ('A','2',?,?,?,'0','');"
                        );
                    }
                    ps.setInt(1, i);
                    ps.setInt(2, j);
                    ps.setString(3, String.format("%d問目の%dつ目の選択肢", i,j));
                    ps.executeUpdate();
                }
            }
            selectFMQuestionInfo();
            ps.close();
         } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }
    public void updateQuestionRandom9() {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
                "UPDATE FMUQ SET PATTERN_NO = 'X'"
            );
            ps.executeUpdate();
            ps = conn.prepareStatement(
                "UPDATE FMUA SET PATTERN_NO = 'X'"
            );
            ps.executeUpdate();
            for(int i =1; i< 10; i++) {
                ps = conn.prepareStatement(
                     "insert into FMUQ VALUES('A','2',?,?,?,'')"
                 );
                ps.setInt(1, i);
                ps.setString(2, String.format("%d問目の問題", i));
                ps.setString(3, String.format("%d問目の問題の解説", i));
                ps.executeUpdate();
                for(int j=1; j<5; j++) {
                    if(j <= 2) {
                        ps = conn.prepareStatement(
                            "INSERT INTO FMUA VALUES ('A','2',?,?,?,'1','');"
                        );
                    } else {
                        ps = conn.prepareStatement(
                            "INSERT INTO FMUA VALUES ('A','2',?,?,?,'0','');"
                        );
                    }
                    ps.setInt(1, i);
                    ps.setInt(2, j);
                    ps.setString(3, String.format("%d問目の%dつ目の選択肢", i,j));
                    ps.executeUpdate();
                }
            }
            selectFMQuestionInfo();
            ps.close();
         } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }
    public void updateQuestionZero() {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
                "UPDATE FMUQ SET DELETE_FLG = '1'"
            );
            ps.executeUpdate();
            ps = conn.prepareStatement(
                "UPDATE FMUA SET DELETE_FLG = '1'"
            );
            ps.executeUpdate();
            selectFMQuestionInfo();
            ps.close();
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
            st.executeUpdate("UPDATE LUSER SET LAST_FM_PASS_NO = 0,LAST_FM_AD_DT = NULL,FM_AD_DAY_COUNT = 0 WHERE USER_ID = 'A497480';");
            st.close();
            conn.close();
            selectFMUserInfo();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }
}