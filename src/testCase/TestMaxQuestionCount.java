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

import database.ChangeSelectQuestionAnswerData;
import database.ChangeSelectUserData;
import utils.BaseTestVariable;
import utils.TestFuncs;

// 問題最大格納時表示確認
public class TestMaxQuestionCount extends BaseTestVariable{

    public TestMaxQuestionCount(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap) throws IOException {
        super(driver,targetFolderPath,currentFolderKey,imgFileListMap);
        file = new FileWriter("./log/問題最大格納時表示確認テスト.txt", true);
        pw = new PrintWriter(new BufferedWriter(file));
        questionData  = new ChangeSelectQuestionAnswerData(pw);
        userData = new ChangeSelectUserData(pw);
    }

    public void execute() throws InterruptedException, IOException {
      userData.updateUserFMInfoClear();
      questionData.updateQuestionMAX();
      driver.navigate().refresh();
      Thread.sleep(THREAD_TIME);  // Let the user actually see something!
      TestFuncs testFuncs = new TestFuncs(driver, targetFolderPath,imgFileListMap,currentFolderKey);
          testFuncs.makeBrowserScreenShot("外貨資格更新_トップ画面初期表示");
          //学習リンクを押下
          testFuncs.idClick(idMAP,"learn-link", THREAD_TIME);
          //テスト開始ボタンを押下
          testFuncs.idClick(idMAP,"test-start-btn", THREAD_TIME);
          ///選択肢クリック1〜5 4つクリック
          testFuncs.checkChioces(1, 4, THREAD_TIME);
          //次ページへボタンを押下
          testFuncs.idClick(idMAP,"next-button", THREAD_TIME);
          //選択肢クリック6〜10 4つクリック
          testFuncs.checkChioces( 6, 4,THREAD_TIME);
          //回答送信ボタン押下
          driver.findElement(By.id("send-answer")).click();
          Alert alert = driver.switchTo().alert();
          testFuncs.makeWindowScreenShot("回答送信ボタン押下");
          Thread.sleep(THREAD_TIME);
          //ダイアログのOKボタンを押下する
          alert.accept();
          testFuncs.makeBrowserScreenShot("回答ダイアログOKクリック");
          Thread.sleep(THREAD_TIME);
          testFuncs.resultUnderScroll("合否結果画面スクロール", THREAD_TIME);
          userData.selectFMUserInfo();
          questionData.UpdateQuestionDefault();
        pw.close();
    }
}