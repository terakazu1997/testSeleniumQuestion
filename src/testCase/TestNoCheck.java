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

// 未回答チェックテスト
public class TestNoCheck extends BaseTestVariable{

    public TestNoCheck(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap) throws IOException {
        super(driver,targetFolderPath,currentFolderKey,imgFileListMap);
        file = new FileWriter("./log/未回答チェック確認テスト.txt", true);
        pw = new PrintWriter(new BufferedWriter(file));
        questionData  = new ChangeSelectQuestionAnswerData(pw);
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
        //1番下にスクロール
        testFuncs.unasweredCheckUnderScroll("next-button", "一番下にスクロール", THREAD_TIME);
        //次ページへボタンを押下
        testFuncs.idClick(idMAP,"next-button", THREAD_TIME);
        //1問ずつ上までスクロール
        testFuncs.test1UpperScroll("テスト1ページ目上までスクロール", THREAD_TIME);
        // 選択肢クリック4、5 1つクリック
        testFuncs.checkChioce(4, 1, THREAD_TIME);
        testFuncs.checkChioce(5, 1, THREAD_TIME);
        //1番下にスクロール
        testFuncs.unasweredCheckUnderScroll("next-button", "一番下にスクロール", THREAD_TIME);
        //次ページへボタンを押下
        testFuncs.idClick(idMAP,"next-button", THREAD_TIME);
        //1問ずつ上までスクロール
        testFuncs.test1UpperScroll("テスト1ページ目上までスクロール", THREAD_TIME);
        // 選択肢クリック2、3 1つクリック
        testFuncs.checkChioce(2, 1, THREAD_TIME);
        testFuncs.checkChioce(3, 1, THREAD_TIME);
        //1番下にスクロール
        testFuncs.unasweredCheckUnderScroll("next-button", "一番下にスクロール", THREAD_TIME);
        //次ページへボタンを押下
        testFuncs.idClick(idMAP,"next-button", THREAD_TIME);
        //1問ずつ上までスクロール
        testFuncs.test1UpperScroll("テスト1ページ目上までスクロール", THREAD_TIME);
        // 選択肢クリック1 1つクリック
        testFuncs.checkChioce(1, 1, THREAD_TIME);
        //1番下にスクロール
        testFuncs.unasweredCheckUnderScroll("next-button", "一番下にスクロール", THREAD_TIME);
        //次ページへボタンを押下
        testFuncs.idClick(idMAP,"next-button", THREAD_TIME);
        //前ページへボタンを押下
        testFuncs.idClick(idMAP,"prev-button", THREAD_TIME);
        //1番下にスクロール
        testFuncs.unasweredCheckUnderScroll("next-button", "一番下にスクロール", THREAD_TIME);
        //次ページへボタンを押下
        testFuncs.idClick(idMAP,"next-button", THREAD_TIME);
        //1番下にスクロール
        testFuncs.unasweredCheckUnderScroll("prev-button", "一番下にスクロール", THREAD_TIME);
        //回答送信ボタン押下
        driver.findElement(By.id("send-answer")).click();
        //1問ずつ上までスクロール
        testFuncs.test2UpperScroll("テスト2ページ目上までスクロール", THREAD_TIME);
        //選択肢クリック6〜10 4つクリック
        testFuncs.checkChioces(6, 4,THREAD_TIME);
        //回答送信ボタン押下
        driver.findElement(By.id("send-answer")).click();
        Alert alert = driver.switchTo().alert();
        testFuncs.makeWindowScreenShot("回答送信ボタン押下");
        Thread.sleep(THREAD_TIME);
        Thread.sleep(THREAD_TIME);
        //ダイアログのNGボタンを押下する
        alert.dismiss();
        testFuncs.makeBrowserScreenShot("回答ダイアログキャンセル");
        pw.close();
    }
}