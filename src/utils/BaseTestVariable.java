package utils;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

//基幹変数クラス
public class BaseTestVariable {
    // Webdriver
    protected WebDriver driver;
    // スクリーンショットを保存する対象のフォルダパス
    protected String targetFolderPath;
    // 現在のフォルダーキー
    protected String currentFolderKey;
    // ファイル
    protected FileWriter file;
    // ファイルに記載
    protected PrintWriter pw;

    // 選択肢のチェック回数や選択肢の個数を変動させる変数
    protected int checkCount;
    // テスト用にテーブルを書き換えるクラスのインスタンス
    protected changeForeignMoneyData changeFMData = new changeForeignMoneyData();
    // 問題を書き換えるようインスタンス
    protected ChangeSelectQuestionAnswerData questionData = new ChangeSelectQuestionAnswerData();
    // 期間情報を書き換えるようインスタンス
    protected ChangeSelectPeriodData periodData = new ChangeSelectPeriodData();
    // ユーザ情報を書き換えるようインスタンス
    protected ChangeSelectUserData userData = new ChangeSelectUserData();

    // 受講履歴を取得するインスタンス
    protected SelectForeignHistoryData foreignHistoryData = new SelectForeignHistoryData();

    // 1回目に押下するIDのリスト
    protected Map<String,String> firstIdListMap = new LinkedHashMap<String,String>();
    // 2回目に押下するIDのリスト
    protected Map<String,String>  secondIdListMap = new LinkedHashMap<String,String>();
    // 3回目に押下数するIDのリスト
    protected  Map<String,String>  ThirdIdListMap = new LinkedHashMap<String,String>();
    // 外貨資格更新トップ画面のURL
    protected final static String FM_TOP_URL = "http://192.168.3.4:8888/";
    // SELENIUM起動したときの画面サイズ　幅と高さ
    protected final static int WINDOW_WIDTH = 1280;
    protected final static int WINDOW_HEIGHT = 660;
    // Thread.sleepした際の中断時間
    protected final static int THREAD_TIME = 50;
    // 基幹となる画像フォルダを格納するパス
    protected final static String BASE_FOLDER_PATH = "./png/";
    //フォルダリストどんどん追加していく。
    protected final static Map<String,String> folderList = new HashMap<String,String>(){
        {
            put("test_scenario_check_4", "シナリオ兼全問題全て選択テスト");
            put("test_check1", "全問題1つずつ選択テスト");
            put("test_check2", "全問題2つずつ選択テスト");
            put("test_check3", "全問題3つずつ選択テスト");
            put("test_change_choice2", "問題選択肢2択に変更テスト");
            put("test_change_choice3", "問題選択肢3択に変更テスト");
            put("test_change_choice5", "問題選択肢5択に変更テスト");
            put("test_pass_pattern_answer_random", "正解パターン(2回目で正解、1回目で正解)と選択肢毎回ランダム確認テスト");
        }
    };
    // 画像ファイルのリスト キーがフォルダリストのキーと同様　値が画像ファイルがリストでファイルようにする。
    protected Map<String, List<String>> imgFileListMap = new LinkedHashMap<String,List<String>>();

    //引数なしのコンストラクタ
    public BaseTestVariable() {
        for (String folderKey: folderList.keySet()) {
            imgFileListMap.put(folderKey,new ArrayList<String>());
        }
    }
    //引数4個コンストラクタ
    public BaseTestVariable(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap) {
        this.driver = driver;
        this.targetFolderPath = targetFolderPath;
        this.currentFolderKey = currentFolderKey;
        this.imgFileListMap = imgFileListMap;
    }
    //引数5個のコンストラクタ
    public BaseTestVariable(WebDriver driver, String targetFolderPath,String currentFolderKey,Map<String, List<String>> imgFileListMap,int checkCount) {
        this.driver = driver;
        this.targetFolderPath = targetFolderPath;
        this.imgFileListMap = imgFileListMap;
        this.currentFolderKey = currentFolderKey;
        this.checkCount = checkCount;
    }
}
