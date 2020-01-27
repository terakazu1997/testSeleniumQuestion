package testCase;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.BaseTestVariable;
import utils.TestFuncs;
import utils.changeForeignMoneyData;

public class TestPassPatternAndAnswerRandom extends BaseTestVariable{


    public TestPassPatternAndAnswerRandom(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap) throws IOException {
        super(driver,targetFolderPath,currentFolderKey,imgFileListMap);
        //1つ目の押下するIDのリスト追加
        this.firstIdListMap.put("learn-link","学習リンククリック");
        this.firstIdListMap.put("test-start-btn","テスト開始ボタンクリック");
        //2つ目の押下するIDのリスト追加
        this.secondIdListMap.put("next-button","次のページへボタンクリック");
        file = new FileWriter("./log/正解パターン(2回目で正解、1回目で正解)と選択肢毎回ランダム確認テスト.txt", true);
        pw = new PrintWriter(new BufferedWriter(file));
        changeFMData = new changeForeignMoneyData(pw);
    }

    //1回目不正解2回目正解と全問正解
    public void testPassPatternAndAnswerRandom() throws InterruptedException, IOException {
          pw.println("外貨ユーザ情報クリア");
          changeFMData.updateUserFMInfoClear();
          pw.println("問題全て正解に変更");
          changeFMData.updateAllPass();
          passChange(3);
          passChange(4);
          pw.println("外貨ユーザ情報クリア");
          changeFMData.updateUserFMInfoClear();
          passChange(4);
          pw.println("問題デフォルトに変更");
          changeFMData.UpdateQuestionDefault();
          pw.close();
    }
    public void passChange(int checkCount) throws InterruptedException, IOException {
        driver.navigate().refresh();
        Thread.sleep(THREAD_TIME);  // Let the user actually see something!
        TestFuncs testFuncs = new TestFuncs(driver, targetFolderPath,imgFileListMap,currentFolderKey);        testFuncs.makeBrowserScreenShot("トップ画面初期表示");
        //1番目のボタンリストをクリック
        testFuncs.btnLinkClick( firstIdListMap, THREAD_TIME);
        ///選択肢クリック1〜5 3つか4つクリック
        testFuncs.checkChioces(1, checkCount, THREAD_TIME);
        //2つ目のボタンリストをクリック
        testFuncs.btnLinkClick( secondIdListMap, THREAD_TIME);
        //選択肢クリック6〜10 4つクリック
        testFuncs.checkChioces(6, 4,THREAD_TIME);
        driver.findElement(By.id("send-answer")).click();
        Alert alert = driver.switchTo().alert();
        Thread.sleep(THREAD_TIME);
        //ダイアログのOKボタンを押下する
        alert.accept();
        testFuncs.makeBrowserScreenShot("回答ダイアログOKクリック");
        Thread.sleep(THREAD_TIME);
        testFuncs.resultUnderScroll("合否結果画面スクロール", THREAD_TIME);
        driver.navigate().refresh();
        pw.println("現在の外貨ユーザ情報");
        changeFMData.selectFMUserInfo();
    }
}