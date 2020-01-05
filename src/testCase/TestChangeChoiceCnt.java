package testCase;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.BaseTestVariable;
import utils.TestFuncs;

public class TestChangeChoiceCnt extends BaseTestVariable{

    public TestChangeChoiceCnt(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap, int checkCount) {
        super(driver,targetFolderPath,currentFolderKey,imgFileListMap,checkCount);
        //1つ目の押下するIDのリスト追加
        this.firstIdListMap.put("learn-link","学習リンククリック");
        this.firstIdListMap.put("test-start-btn","テスト開始ボタンクリック");
        //2つ目の押下するIDのリスト追加
        this.secondIdListMap.put("next-button","次のページへボタンクリック");
    }
    //2択、3択、5択を切り替える。
    public void testChangeChoiceCnt() throws InterruptedException, IOException {
          changeFMData.updateUserFMInfoClear();
          switch(checkCount) {
              case 2:
                  changeFMData.UpdateQuestion2();
                  break;
              case 3:
                  changeFMData.UpdateQuestion3();
                  break;
              case 5:
                  changeFMData.UpdateQuestion5();
                  break;
          }
          driver.navigate().refresh();
          Thread.sleep(THREAD_TIME);  // Let the user actually see something!
          System.out.println(currentFolderKey);
          TestFuncs testFuncs = new TestFuncs(driver, targetFolderPath,imgFileListMap,currentFolderKey);
          testFuncs.makeBrowserScreenShot("トップ画面初期表示");
          //1番目のボタンリストをクリック
          testFuncs.btnLinkClick( firstIdListMap, THREAD_TIME);
          ///選択肢クリック1〜5 選択肢の数クリック
          testFuncs.checkChioces(1, checkCount, THREAD_TIME);
          //2つ目のボタンリストをクリック
          testFuncs.btnLinkClick(secondIdListMap, THREAD_TIME);
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
          changeFMData.UpdateQuestionDefault();
    }
}