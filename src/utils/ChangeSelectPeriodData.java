package utils;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeSelectPeriodData extends BaseDatabase{
    public ChangeSelectPeriodData() {
     }
    public ChangeSelectPeriodData(PrintWriter pw) {
        super(pw);
     }
    public void selectFMPeriodInfo() {
        initConnectionDB();
        try {
            pw.println("・テスト対象の外貨資格更新問題期間テーブルの内容");
            Statement st = conn.createStatement();
            //-> データを取得するSQLを準備
            rs = st.executeQuery(
              "select NO, PERIOD_TEST_FROM, PERIOD_TEST_TO, PATTERN_NO from FMUP where PERIOD_TEST_FROM = '20200101' OR PERIOD_TEST_FROM = '20400101'"
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
    public void updateFMPeriodChange(int no) {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
               "UPDATE FMUP SET NO = ? WHERE PERIOD_TEST_FROM = '20200101'"
            );
            ps.setInt(1, no);
            ps.executeUpdate();
            selectFMPeriodInfo();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateFMPeriodChangeFuture(int no, String periodTestFrom,String periodTestTo) {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
               "UPDATE FMUP SET NO = ?,PERIOD_TEST_FROM = ?,PERIOD_TEST_TO = ? WHERE PERIOD_TEST_FROM = '20200101'"
            );
            ps.setInt(1, no);
            ps.setString(2, periodTestFrom);
            ps.setString(3, periodTestTo);
            ps.executeUpdate();
            selectFMPeriodInfo();
            ps.close();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }
    public void updateFMPeriodDefault() {
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(
                "UPDATE FMUP SET PERIOD_TEST_FROM = '20200101',PERIOD_TEST_TO = '20200331' WHERE PERIOD_TEST_FROM = '20400101'"
            );
            st.executeUpdate(
               "UPDATE FMUP SET NO = 1 WHERE PERIOD_TEST_FROM = '20200101'"
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
