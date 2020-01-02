package testCase;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.BaseTestVariable;
import utils.TestFuncs;

//シナリオテスト 4つ選択（ダイアログ確認と当日受講回数2回含む)
public class TestScenarioCheck4 extends BaseTestVariable{

    public TestScenarioCheck4(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap) {
        super(driver,targetFolderPath,currentFolderKey,imgFileListMap);
        this.firstIdListMap.put("learn-link","学習リンククリック");
        this.firstIdListMap.put("test-start-btn","テスト開始ボタンクリック");
        this.firstIdListMap.put("next-button","次のページへボタンクリック未回答チェック");
        this.secondIdListMap.put("next-button","次のページへボタンクリックOK");
        this.secondIdListMap.put("prev-button","前のページへボタンクリック");
        this.ThirdIdListMap.put("next-button","次のページへボタンクリック「前→次」");
        this.ThirdIdListMap.put("send-answer","回答送信ボタン未回答チェック");
    }

    public void testScenarioCheck4() throws InterruptedException, IOException {
      changeFMData.updateUserFMInfoClear();
      driver.get(FM_TOP_URL);
      Thread.sleep(THREAD_TIME);  // Let the user actually see something!
      TestFuncs testFuncs = new TestFuncs(driver, targetFolderPath,imgFileListMap,currentFolderKey);      for(int i = 0; i<2; i++) {
          testFuncs.makeBrowserScreenShot("トップ画面初期表示");
          //1番目のボタンリストをクリック
          testFuncs.btnLinkClick(firstIdListMap, THREAD_TIME);
          //1番下までスクロール
          testFuncs.underScroll( WINDOW_HEIGHT * 10, 2, "次のページへボタン未回答チェックスクロール", THREAD_TIME);
          ///選択肢クリック1〜5 4つクリック
          testFuncs.checkChioces(1, 4, THREAD_TIME);
          //2つ目のボタンリストをクリック
          testFuncs.btnLinkClick(secondIdListMap, THREAD_TIME);
          //3つ目のボタンリストをクリック
          testFuncs.btnLinkClick(ThirdIdListMap, THREAD_TIME);
          //一番下までスクロール
          testFuncs.underScroll( WINDOW_HEIGHT * 10, 2, "回答送信ボタン未回答チェックNGスクロール", THREAD_TIME);
          //選択肢クリック6〜10 4つクリック
          testFuncs.checkChioces( 6, 4,THREAD_TIME);
          driver.findElement(By.id("send-answer")).click();
          Alert alert = driver.switchTo().alert();
          testFuncs.makeWindowScreenShot("回答ダイアログ");
          Thread.sleep(THREAD_TIME);
          //ダイアログのNGボタンを押下する
          alert.dismiss();
          Thread.sleep(THREAD_TIME);
          testFuncs.makeBrowserScreenShot("回答ダイアログキャンセル");
          Thread.sleep(THREAD_TIME);  // Let the user actually see something
          driver.findElement(By.id("send-answer")).click();
          alert = driver.switchTo().alert();
          Thread.sleep(THREAD_TIME);
          testFuncs.makeWindowScreenShot("回答ダイアログ");
          //ダイアログのOKボタンを押下する
          alert.accept();
          testFuncs.makeBrowserScreenShot("回答ダイアログOKクリック");
          Thread.sleep(THREAD_TIME);
          testFuncs.underScroll( WINDOW_HEIGHT, 8,"合否結果画面スクロール", THREAD_TIME);
          changeFMData.selectFMUserInfo();
          if(i==0)driver.findElement(By.id("retest")).click();
        }
        // 直接打鍵時受講できず、合格2回のメッセージが表示されるか確認。
        driver.get(FM_TOP_URL);
        testFuncs.makeBrowserScreenShot("当日受講回数2回後再読み込み_");
    }
}