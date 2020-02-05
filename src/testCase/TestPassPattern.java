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

public class TestPassPattern extends BaseTestVariable{

    public TestPassPattern(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap) throws IOException {
        super(driver,targetFolderPath,currentFolderKey,imgFileListMap);
        file = new FileWriter("./log/正解パターン(2回目で正解、1回目で正解)確認テスト.txt", true);
        pw = new PrintWriter(new BufferedWriter(file));
        questionData  = new ChangeSelectQuestionAnswerData (pw);
        userData = new ChangeSelectUserData(pw);
    }

    //1回目不正解2回目正解と全問正解
    public void execute() throws InterruptedException, IOException {
          pw.println("外貨ユーザ情報クリア");
          userData.updateUserFMInfoClear();
          pw.println("問題正解できるように変更");
          questionData.updateMakeTestQuestion(10,new int[] {1,1,0,0});
          passChange(1);
          passChange(2);
          pw.println("外貨ユーザ情報クリア");
          userData.updateUserFMInfoClear();
          passChange(3);
          pw.println("問題デフォルトに変更");
          questionData.UpdateQuestionDefault();
          pw.close();
    }
    public void passChange(int checkCount) throws InterruptedException, IOException {
        driver.navigate().refresh();
        Thread.sleep(THREAD_TIME);  // Let the user actually see something!
        TestFuncs testFuncs = new TestFuncs(driver, targetFolderPath,imgFileListMap,currentFolderKey);        testFuncs.makeBrowserScreenShot("トップ画面初期表示");
        testFuncs.makeBrowserScreenShot("外貨資格更新_トップ画面初期表示");
        //学習リンクを押下
        testFuncs.idClick(idMAP,"learn-link", THREAD_TIME);
        //テスト開始ボタンを押下
        testFuncs.idClick(idMAP,"test-start-btn", THREAD_TIME);
        if(checkCount == 1) {
            ///選択肢クリック1〜5 2つ目の選択肢から4つ目の選択肢3つクリック
            testFuncs.checkChiocesNotPass(1, THREAD_TIME);
        }else {
            // 選択クリック2つクリック
            testFuncs.checkChioces(1, 2, THREAD_TIME);
        }
        //次ページへボタンを押下
        testFuncs.idClick(idMAP,"next-button", THREAD_TIME);
        //選択肢クリック6〜10 2つクリック
        testFuncs.checkChioces(6, 2,THREAD_TIME);
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
        userData.selectFMUserInfo();
    }
}