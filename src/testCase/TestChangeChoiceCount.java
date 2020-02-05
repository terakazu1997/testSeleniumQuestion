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

public class TestChangeChoiceCount extends BaseTestVariable{

    public TestChangeChoiceCount(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap, int checkCount) throws IOException {
        super(driver,targetFolderPath,currentFolderKey,imgFileListMap,checkCount);
        file = new FileWriter(String.format("./log/問題選択肢%d択に変更テスト.txt",checkCount), true);
        pw = new PrintWriter(new BufferedWriter(file));
        questionData  = new ChangeSelectQuestionAnswerData(pw);
        userData = new ChangeSelectUserData(pw);
    }
    //1拓、2択、3択、5択を切り替える。
    public void execute() throws InterruptedException, IOException {
          userData.updateUserFMInfoClear();
          switch(checkCount) {
              case 1:
                  questionData.UpdateQuestion1();
                  break;
              case 2:
                  questionData.UpdateQuestion2();
                  break;
              case 3:
                  questionData.UpdateQuestion3();
                  break;
              case 5:
                  questionData.UpdateQuestion5();
                  break;
          }
          driver.navigate().refresh();
          Thread.sleep(THREAD_TIME);  // Let the user actually see something!
          System.out.println(currentFolderKey);
          TestFuncs testFuncs = new TestFuncs(driver, targetFolderPath,imgFileListMap,currentFolderKey);
          testFuncs.makeBrowserScreenShot("トップ画面初期表示");
          //学習リンクを押下
          testFuncs.idClick(idMAP,"learn-link", THREAD_TIME);
          //テスト開始ボタンを押下
          testFuncs.idClick(idMAP,"test-start-btn", THREAD_TIME);
          ///選択肢クリック1〜5 選択肢の数クリック
          testFuncs.checkChioces(1, checkCount, THREAD_TIME);
          //次ページへボタンを押下
          testFuncs.idClick(idMAP,"next-button", THREAD_TIME);
          //選択肢クリック6〜10 選択肢の数クリック
          testFuncs.checkChioces(6, checkCount,THREAD_TIME);
          driver.findElement(By.id("send-answer")).click();
          Alert alert = driver.switchTo().alert();
          alert = driver.switchTo().alert();
          Thread.sleep(THREAD_TIME);
          //ダイアログのOKボタンを押下する
          alert.accept();
          testFuncs.makeBrowserScreenShot("回答ダイアログOKクリック");
          Thread.sleep(THREAD_TIME);
          testFuncs.resultUnderScroll("合否結果画面スクロール", THREAD_TIME);
          questionData.UpdateQuestionDefault();
          pw.close();
    }
}