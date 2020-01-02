package testCase;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.BaseTestVariable;
import utils.TestFuncs;

public class TestPassPatternAndAnswerRandom extends BaseTestVariable{


    public TestPassPatternAndAnswerRandom(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap) {
        super(driver,targetFolderPath,currentFolderKey,imgFileListMap);
        //1つ目の押下するIDのリスト追加
        this.firstIdListMap.put("learn-link","学習リンククリック");
        this.firstIdListMap.put("test-start-btn","テスト開始ボタンクリック");
        //2つ目の押下するIDのリスト追加
        this.secondIdListMap.put("next-button","次のページへボタンクリック");
    }

    //1回目不正解2回目正解と全問正解
    public void testPassPatternAndAnswerRandom() throws InterruptedException, IOException {
          changeFMData.updateUserFMInfoClear();
          changeFMData.updateAllPass();
          passChange(3);
          passChange(4);
          changeFMData.updateUserFMInfoClear();
          passChange(4);
          changeFMData.UpdateQuestionDefault();
    }
    public void passChange(int checkCount) throws InterruptedException, IOException {
        driver.get(FM_TOP_URL);
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
        testFuncs.underScroll( WINDOW_HEIGHT, 8,"合否結果画面スクロール", THREAD_TIME);
        driver.get(FM_TOP_URL);
        changeFMData.selectFMUserInfo();
    }
}