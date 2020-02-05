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

import database.ChangeSelectUserData;
import utils.BaseTestVariable;
import utils.TestFuncs;

public class TestChoiceCheck extends BaseTestVariable{

    public TestChoiceCheck(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap, int checkCount) throws IOException {
        super(driver,targetFolderPath,currentFolderKey,imgFileListMap,checkCount);
        file = new FileWriter(String.format("./log/全問題%dつずつ選択テスト.txt",checkCount), true);
        pw = new PrintWriter(new BufferedWriter(file));
        userData = new ChangeSelectUserData(pw);
    }

    public void execute() throws InterruptedException, IOException {
      userData.updateUserFMInfoClear();
      driver.navigate().refresh();
      Thread.sleep(THREAD_TIME);  // Let the user actually see something!
      TestFuncs testFuncs = new TestFuncs(driver, targetFolderPath,imgFileListMap,currentFolderKey);
      testFuncs.makeBrowserScreenShot("外貨資格更新_トップ画面初期表示");
      //学習リンクを押下
      testFuncs.idClick(idMAP,"learn-link", THREAD_TIME);
      //テスト開始ボタンを押下
      testFuncs.idClick(idMAP,"test-start-btn", THREAD_TIME);
      ///選択肢クリック1〜5 1〜4つクリック
      testFuncs.checkChioces(1, checkCount, THREAD_TIME);
      //次ページへボタンを押下
      testFuncs.idClick(idMAP,"next-button", THREAD_TIME);
      //選択肢クリック6〜10 1〜4つクリック
      testFuncs.checkChioces( 6, checkCount,THREAD_TIME);
      Thread.sleep(THREAD_TIME);
      driver.findElement(By.id("send-answer")).click();
      Alert alert = driver.switchTo().alert();
      Thread.sleep(THREAD_TIME);
      //ダイアログのOKボタンを押下する
      alert.accept();
      testFuncs.makeBrowserScreenShot("回答ダイアログOKクリック");
      Thread.sleep(THREAD_TIME);
      testFuncs.resultUnderScroll("合否結果画面スクロール", THREAD_TIME);
      pw.close();
    }
}