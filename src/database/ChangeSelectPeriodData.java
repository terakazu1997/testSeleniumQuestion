package database;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeSelectPeriodData extends BaseDatabase{

    public ChangeSelectPeriodData(PrintWriter pw) {
        super(pw);
     }
    public ChangeSelectPeriodData() {
        // TODO 自動生成されたコンストラクター・スタブ
    }
    public void selectFMPeriodInfo() {
        initConnectionDB();
        try {
            Statement st = conn.createStatement();
            pw.println("・外貨資格更新問題期間テーブルのNoが1の内容");
            //-> データを取得するSQLを準備
            rs = st.executeQuery(
              "select NO, PERIOD_TEST_FROM, PERIOD_TEST_TO, PATTERN_NO from FMUP where NO = 1"
            );
            rs.next();
            pw.println("No, テスト開始日, テスト終了日, パターン番号");
            pw.print(rs.getInt("NO"));
            pw.print(", ");
            pw.print(rs.getString("PERIOD_TEST_FROM"));
            pw.print(", ");
            pw.print(rs.getString("PERIOD_TEST_TO"));
            pw.print(", ");
            pw.println(rs.getString("PATTERN_NO"));
            pw.println("・外貨資格更新問題期間テーブルのNoが2の内容");
            //-> データを取得するSQLを準備
            rs = st.executeQuery(
              "select NO, PERIOD_TEST_FROM, PERIOD_TEST_TO, PATTERN_NO from FMUP where NO = 2"
            );
            rs.next();
            pw.println("No, テスト開始日, テスト終了日, パターン番号");
            pw.print(rs.getInt("NO"));
            pw.print(", ");
            pw.print(rs.getString("PERIOD_TEST_FROM"));
            pw.print(", ");
            pw.print(rs.getString("PERIOD_TEST_TO"));
            pw.print(", ");
            pw.println(rs.getString("PATTERN_NO"));
            st.close();
            rs.close();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }
    public void updateFMPeriodChange(String[] periodList) {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
               "UPDATE FMUP SET PERIOD_TEST_FROM = ? ,PERIOD_TEST_TO = ? WHERE NO = 1"
            );
            ps.setString(1, periodList[0]);
            ps.setString(2, periodList[1]);
            ps.executeUpdate();
            ps = conn.prepareStatement(
                "UPDATE FMUP SET PERIOD_TEST_FROM = ? ,PERIOD_TEST_TO = ? WHERE NO = 2"
            );
            ps.setString(1, periodList[2]);
            ps.setString(2, periodList[3]);
            ps.executeUpdate();
            selectFMPeriodInfo();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateFMPeriodDefault() {
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(
                "UPDATE FMUP SET PERIOD_TEST_FROM = '20200101',PERIOD_TEST_TO = '20200331' WHERE No = 1"
            );
            st.executeUpdate(
                "UPDATE FMUP SET PERIOD_TEST_FROM = '20200401',PERIOD_TEST_TO = '20200930' WHERE No = 2"
            );
            selectFMPeriodInfo();
            st.close();
            conn.close();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }
}
