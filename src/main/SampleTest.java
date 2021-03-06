package main;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import testCase.TestAttendanceDayCount;
import testCase.TestChangeChoiceCount;
import testCase.TestChoiceCheck;
import testCase.TestMaxQuestionCount;
import testCase.TestNoCheck;
import testCase.TestPassPattern;
import testCase.TestScreenOperation;
import utils.BaseTestVariable;
import utils.TestFuncs;

public class SampleTest extends BaseTestVariable{
    private String shapingToday;

    //テストクラス
    private TestScreenOperation test1;
    private TestNoCheck test2;
    private TestAttendanceDayCount test3;
    private TestPassPattern test4;
    private TestChoiceCheck test5;
    private TestChangeChoiceCount test6;
    private TestMaxQuestionCount test7;



    // コンストラクタ フォルダ作成して、準備をする。
    public SampleTest() {
        //IE用の設定
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "IE");
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
        System.setProperty("webdriver.chrome.driver", "./exe/chromedriver");
        //System.setProperty("webdriver.ie.driver", "./exe/IEDriverServer.exe");
        driver = new ChromeDriver();
        //driver = new InternetExplorerDriver(capabilities);
        driver.manage().window().setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        TestFuncs testFuncs = new TestFuncs(folderList);
        this.shapingToday = testFuncs.makeFolder();
    }

    @Test
    public void TestSelenium() throws InterruptedException, IOException {
        String currentFolderKey = "";
        //driver.get(FM_TOP_URL);
        ((JavascriptExecutor) driver).executeScript(String.format("window.open('%s','fm','location=0','menubar=0','status=0,toolbar=0,scrollbars=1,width=%d,height=%d,directories=0,resizable=0');", FM_TOP_URL,WINDOW_WIDTH,WINDOW_HEIGHT));
        driver.switchTo().window("fm");
        //シナリオテスト 4つ選択（ダイアログ確認と当日受講回数2回含む)
        currentFolderKey = "test_screen_operation";
        targetFolderPath = shapingToday + "/" + folderList.get(currentFolderKey) + "/";
        test1 = new TestScreenOperation(driver,targetFolderPath,currentFolderKey, imgFileListMap);
        test1.execute();
        currentFolderKey = "test_no_check";
        test2 = new TestNoCheck(driver,targetFolderPath,currentFolderKey, imgFileListMap);
        test2.execute();
        currentFolderKey = "test_attendance_day_count";
        test3 = new TestAttendanceDayCount(driver,targetFolderPath,currentFolderKey, imgFileListMap);
        test3.execute();
        currentFolderKey  ="test_pass_pattern";
        // 正解パターン確認テスト
        targetFolderPath = shapingToday + "/" + folderList.get(currentFolderKey) + "/";
        test4= new TestPassPattern(driver,targetFolderPath,currentFolderKey, imgFileListMap);
        test4.execute();

        //選択肢を1つから3つ選択のテスト
        for(int i=1;i<4;i++) {
            currentFolderKey = "test_check" + i;
            targetFolderPath = shapingToday + "/" + folderList.get(currentFolderKey) + "/";
            test5 = new TestChoiceCheck(driver,targetFolderPath,currentFolderKey, imgFileListMap, i);
            test5.execute();
        }

        //問題1、2、3、5択に変更テスト
        for(int i = 1;i < 6; i++) {
            if(i == 4) continue;
            currentFolderKey = "test_change_choice" + i;
            targetFolderPath = shapingToday + "/" + folderList.get(currentFolderKey) + "/";
            test6= new TestChangeChoiceCount(driver,targetFolderPath,currentFolderKey,imgFileListMap, i);
            test6.execute();
        }
        // 問題最大格納時表示確認
        currentFolderKey = "test_max_question_cnt";
        test7 = new TestMaxQuestionCount(driver,targetFolderPath,currentFolderKey,imgFileListMap);
        test7.execute();

        driver.quit();
        outputExcelPicture();
    }
    // エクセル作成用クラス
    public void outputExcelPicture() {
        // 1つあたりの画像の行数と列数
        int pictureRows = 23;
        int pictureCols = 14;
        // Exlcel作成
        XSSFWorkbook workbook = new XSSFWorkbook();
        // フォルダリストがなくなるまで繰り返す。
        for (Map.Entry<String, String> folderEntry: folderList.entrySet()) {
            // フォルダ名に対応するシートを作成し、0列目の幅を2048とする。
            Sheet sheet =workbook.createSheet(folderEntry.getValue());
            sheet.setColumnWidth(0,2048);
            // iが画像ファイルのタイトルと空行含めた現在の画像の貼り付け位置。jが空行とタイトルをつけるための変数。
            int i=0;
            int j=1;
            // フォルダリストごとの画像ファイルがなくなるまで繰り返す。
            for(String currentImgFile : imgFileListMap.get(folderEntry.getKey())) {
                try {
                    // 画像ファイル生成
                    File imgFile = new File(currentImgFile);
                    ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
                    BufferedImage img = ImageIO.read(imgFile);
                    ImageIO.write(img,"png",byteArrayOutput);
                    Drawing drawing = sheet.createDrawingPatriarch();
                    // シートに、画像を貼り付け。位置→列数：0列目から現在の貼り付け位置画像の列数。行数：i*現在の貼り付け位置画像の行数+jから(i+1) * pictureRows + j
                    ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, i * pictureRows + j, pictureCols, (i + 1) * pictureRows + j);
                    int picIndex = workbook.addPicture(byteArrayOutput.toByteArray(), Workbook.PICTURE_TYPE_PNG);
                    Picture pic = drawing.createPicture(anchor, picIndex);
                    // pic.resize(Math.floor(220 / (double) img.getHeight() * 100) / 100);
                    pic.resize(1.0);
                    // 画像タイトルを設定。画像ファイル名のフォルダの部分と時間以降の箇所をカットし、設定。位置は、i*現在の貼り付け位置画像の行数-1
                    Cell cell = sheet.createRow(i * pictureRows + j - 1).createCell(0);
                    String currentImgFIleTitle = currentImgFile;
                    currentImgFIleTitle = currentImgFIleTitle.replaceAll(".+[0-9]{8}/.+/", "");
                    currentImgFIleTitle = currentImgFIleTitle.replaceAll("_?[0-9]{1,2}時[0-9]{1,2}分[0-9]{1,2}秒.png", "");
                    cell.setCellValue(currentImgFIleTitle);
                 // 画像ファイルの繰り返しごとにiは1、jは3インクリメント。（jが3なのは、空行をあけるため。
                 i++;
                 j+=3;
                } catch (IOException e) {
                    // TODO 自動生成された catch ブロック
                    e.printStackTrace();
                }
            }
        }
        FileOutputStream out = null;

        try {
            out = new FileOutputStream("./book/sampleTest.xlsx");
            workbook.write(out);
            out.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}