package utils;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestFuncs{
    private static final String BASE_FOLDER_PATH ="./png/";
    private static final SimpleDateFormat SDF = new SimpleDateFormat("HH時mm分ss秒");
    private WebDriver driver;
    private String folderPath;
    private Map<String, List<String>> imgFileListMap;
    private String currentFolderKey;

    private String addPath;
    private Map<String,String> folderList;
    Calendar cal;

    //引数1個(フォルダリスト)コンストラクタ フォルダを作成時インスタンス時に使用。
    public TestFuncs( Map<String,String> folderList ) {
        this.folderList = folderList;
    }
    // 引数2個(WebDriverと対象のフォルダパスと画像フォルダーのマップと現在のフォルダパス) コンストラクタ 各種テストクラスでインスタンス化する時に使用
    public TestFuncs(WebDriver driver, String targetFolderPath,Map<String, List<String>> imgFileListMap,String currentFolderKey) {
        this.driver = driver;
        this.folderPath = BASE_FOLDER_PATH+targetFolderPath;
        this.imgFileListMap = imgFileListMap;
        this.currentFolderKey = currentFolderKey;
    }

    //フォルダ作成
    public String makeFolder() {
        cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String shapingToday = sdf.format(cal.getTime());
        String folder = BASE_FOLDER_PATH + shapingToday;
        if (!Files.exists(Paths.get(folder,""))) {
            new File(folder).mkdir();
        }
        for(String currentFolder : folderList.values()) {
            folder = BASE_FOLDER_PATH + shapingToday + "/" + currentFolder;
            if (!Files.exists(Paths.get(folder,""))) {
                new File(folder).mkdir();
            }
        }
        return shapingToday;
    }
    //ウインドウのスクショ撮影
    public void makeWindowScreenShot(String fileName) throws IOException {
        cal = Calendar.getInstance();
        try {
            // キャプチャの範囲
            Rectangle bounds = new Rectangle(0, 0, 1280, 800);
            // これで画面キャプチャ
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(bounds);
            addPath = folderPath+fileName +  SDF.format(cal.getTime())+ ".png";
            ImageIO.write(image, "png", new File(addPath));
            imgFileListMap.get(currentFolderKey).add(addPath);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    // ボタンリンク押下まとめ
    public  void btnLinkClick(Map<String,String> idListMap,int THREAD_TIME) throws InterruptedException, IOException {
        for(Map.Entry<String, String> idValueSet : idListMap.entrySet()) {
            Thread.sleep(THREAD_TIME);
            driver.findElement(By.id(idValueSet.getKey())).click();
            Thread.sleep(THREAD_TIME);  // Let the user actually see something!
            makeBrowserScreenShot(idValueSet.getValue());
        }
    }
    //選択肢のチェックをつける。
    public  void checkChioces(int startCheck, int checkCount,int THREAD_TIME) throws InterruptedException, IOException {
        for(int i =startCheck; i<startCheck + 5; i++) {
            for(int j=0; j<checkCount; j++) {
                driver.findElement(By.id(String.format("%d-%d", i,j))).click();
                Thread.sleep(THREAD_TIME);
            }
            makeBrowserScreenShot(i+"問目の選択肢クリック_");
        }
    }
    //下スクロール
    public  void underScroll(int WINDOW_HEIGHT,int repeatCount,String fileName,int THREAD_TIME) throws InterruptedException, IOException {
        for(int i =1; i<repeatCount; i++) {
            String scrollTo = String.format("scroll(0,%d);",i * WINDOW_HEIGHT);
            ((JavascriptExecutor) driver).executeScript(scrollTo);
            makeBrowserScreenShot(fileName+"_"+i + "_");
            Thread.sleep(THREAD_TIME);
        }
    }
    //ブラウザのスクリーンショットを撮る
    public void makeBrowserScreenShot(String fileName) throws IOException {
        cal = Calendar.getInstance();
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        addPath =folderPath + fileName + SDF.format(cal.getTime())+ ".png";
        Files.copy(file.toPath(), new File(addPath).toPath());
        imgFileListMap.get(currentFolderKey).add(addPath);
    }
}
