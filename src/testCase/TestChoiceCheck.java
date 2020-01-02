package testCase;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.BaseTestVariable;
import utils.TestFuncs;

public class TestChoiceCheck extends BaseTestVariable{

    public TestChoiceCheck(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap, int checkCount) {
        super(driver,targetFolderPath,currentFolderKey,imgFileListMap,checkCount);
        this.firstIdListMap.put("learn-link","学習リンククリック");
        this.firstIdListMap.put("test-start-btn","テスト開始ボタンクリック");
        this.secondIdListMap.put("next-button","次のページへボタンクリック");
    }

    public void testChoiceCheck() throws InterruptedException, IOException {
      changeFMData.updateUserFMInfoClear();
      driver.get(FM_TOP_URL);
      Thread.sleep(THREAD_TIME);  // Let the user actually see something!
      TestFuncs testFuncs = new TestFuncs(driver, targetFolderPath,imgFileListMap,currentFolderKey);
      testFuncs.makeBrowserScreenShot("トップ画面初期表示");
      //1番目のボタンリストをクリック
      testFuncs.btnLinkClick( firstIdListMap, THREAD_TIME);
      ///選択肢クリック1〜5 1〜4つクリック
      testFuncs.checkChioces(1, checkCount, THREAD_TIME);
      //2つ目のボタンリストをクリック
      testFuncs.btnLinkClick( secondIdListMap, THREAD_TIME);
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
      testFuncs.underScroll( WINDOW_HEIGHT, 8,"合否結果画面スクロール", THREAD_TIME);
    }
}