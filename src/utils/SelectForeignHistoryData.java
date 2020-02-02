package utils;

import java.io.PrintWriter;
import java.sql.SQLException;

public class SelectForeignHistoryData extends BaseDatabase{
    public SelectForeignHistoryData() {
    }
    public SelectForeignHistoryData(PrintWriter pw) {
        super(pw);
    }
    public void selectForeignHistoryInfo() {
        initConnectionDB();
        try {
            pw.println("テスト対象ユーザの受講履歴テーブルの内容");
            //-> データを取得するSQLを準備
            ps = conn.prepareStatement(
              "SELECT SEQ,USER_ID,STATUS,UPDATE_DT FROM FMUH WHERE USER_ID = ? ORDER BY SEQ DESC LIMIT 1"
            );
            //-> SQLを実行してデータを取得
            ps.setString(1, "A497480");
            rs = ps.executeQuery();
            //-> データを表示
            rs.next();
            pw.println("シーケンス番号,ユーザID,ステータス,更新日付");
            pw.print(rs.getInt("SEQ"));
            pw.print(", ");
            pw.print(rs.getString("USER_ID"));
            pw.print(", ");
            pw.print(rs.getString("STATUS"));
            pw.print(", ");
            pw.println(rs.getString("UPDATE_DT"));
            rs.close();
            ps.close();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

}
