package testCase;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.BaseTestVariable;
import utils.TestFuncs;

public class TestQuestionRandom extends BaseTestVariable{

    public TestQuestionRandom(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap) {
        super(driver,targetFolderPath,currentFolderKey,imgFileListMap);
        this.firstIdListMap.put("learn-link","学習リンククリック");
        this.firstIdListMap.put("test-start-btn","テスト開始ボタンクリック");
        this.secondIdListMap.put("next-button","次のページへボタンクリック");
    }

    public void testQuestionRandom() throws InterruptedException, IOException {
        for(int i=0; i<4; i++) {
            changeFMData.updateUserFMInfoClear();
            driver.navigate().refresh();
            Thread.sleep(THREAD_TIME);  // Let the user actually see something!
            TestFuncs testFuncs = new TestFuncs(driver, targetFolderPath,imgFileListMap,currentFolderKey);          testFuncs.makeBrowserScreenShot("トップ画面初期表示");
            //1番目のボタンリストをクリック
            testFuncs.btnLinkClick( firstIdListMap, THREAD_TIME);
            ///選択肢クリック1〜5 4つクリック
            testFuncs.checkChioces(1, 4, THREAD_TIME);
            //2つ目のボタンリストをクリック
            testFuncs.btnLinkClick( secondIdListMap, THREAD_TIME);
            //選択肢クリック6〜10 4つクリック
            testFuncs.checkChioces(6, 4,THREAD_TIME);
            driver.findElement(By.id("send-answer")).click();
            Alert alert = driver.switchTo().alert();
            alert = driver.switchTo().alert();
            Thread.sleep(THREAD_TIME);
            testFuncs.makeWindowScreenShot("alert-ok");
            //ダイアログのOKボタンを押下する
            alert.accept();
            testFuncs.makeBrowserScreenShot("回答ダイアログOKクリック");
            Thread.sleep(THREAD_TIME);
            testFuncs.resultUnderScroll("合否結果画面スクロール", THREAD_TIME);
        }
    }
}