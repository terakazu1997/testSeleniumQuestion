package database;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeSelectUserData extends BaseDatabase{

    public ChangeSelectUserData(PrintWriter pw) {
       super(pw);
    }

    public ChangeSelectUserData() {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    public void selectFMUserInfo() {
        initConnectionDB();
        try {
            pw.println("テスト対象ユーザのユーザ情報テーブルの内容");
            //-> データを取得するSQLを準備
            ps = conn.prepareStatement(
              "select USER_ID, LAST_FM_PASS_DT, LAST_FM_AD_DT, FM_AD_DAY_COUNT,LAST_FM_SCORE,LAST_FM_PASS_NO from LUSER where USER_ID = ?"
            );
            //-> SQLを実行してデータを取得
            ps.setString(1, "A497480");
            rs = ps.executeQuery();
            //-> データを表示
            rs.next();
            pw.println("ユーザID,最終受講日,最終合格日,当日受講回数,最終取得点数,最終合格番号");
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
      public void updateUserFMInfoClear() {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE LUSER SET LAST_FM_PASS_NO = 0,LAST_FM_AD_DT = NULL,LAST_FM_PASS_DT = NULL, LAST_FM_SCORE = 0,FM_AD_DAY_COUNT = 0 WHERE USER_ID = 'A497480'");
            st.close();
            conn.close();
            selectFMUserInfo();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }
    public void updateUserFMInfoChange(int fMAdDayCount,String lastFMAdDate, int lastFMPassNo) {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
               "UPDATE LUSER SET FM_AD_DAY_COUNT = ?,LAST_FM_AD_DT = ?,LAST_FM_PASS_NO = ? WHERE USER_ID = 'A497480'"
            );
            ps.setInt(1, fMAdDayCount);
            ps.setString(2, lastFMAdDate);
            ps.setInt(3, lastFMPassNo);
            ps.executeUpdate();
            selectFMUserInfo();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public void updateUserFMInfoChange(int fMAdDayCount, String lastFMAdDate, String lastFMPassDate, int lastFMScore,int lastFMPassNo) {
        // (3) SQL送信用インスタンスの作成
        initConnectionDB();
        try {
            ps = conn.prepareStatement(
               "UPDATE LUSER SET FM_AD_DAY_COUNT = ?,LAST_FM_AD_DT = ?,LAST_FM_AD_DT = ?,LAST_FM_SCORE = ?,LAST_FM_PASS_NO = ? WHERE USER_ID = 'A497480'"
            );
            ps.setInt(1, fMAdDayCount);
            ps.setString(2, lastFMAdDate);
            ps.setString(3, lastFMPassDate);
            ps.setInt(4, lastFMScore);
            ps.setInt(5, lastFMPassNo);
            ps.executeUpdate();
            selectFMUserInfo();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }
}