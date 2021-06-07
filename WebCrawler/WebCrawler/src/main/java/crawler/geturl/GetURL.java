package crawler.geturl;
/*
Author:Dean Yu
Title:愛料理網站爬蟲程式(取得URL與關鍵字，程式執行開始點!)
Date:2021/6/1
*/
import crawler.JDBC.CrawlerJDBC;
import crawler.scan.RecipeMainCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/*
備註:MySQL編碼需要是UTF-8mb4，說發生編碼錯誤可進入MySQL執行SQL語句更改MySQL編碼:
ALTER TABLE recipe_material CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE recipe_step CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE recipe_main CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
alter table recipe_main convert to character set utf8mb4 collate utf8mb4_bin;
 */
public class GetURL {
    public static void main(String[] args) {
        try {
            GetURL.getURL();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  void getURL() throws Exception{
        //0.關鍵字設定
        int recipeNumTotal = 0;
        String keyWord = "家常菜";
        ArrayList<String> insertSQLMain = new ArrayList();

        //1.總頁數設定(i)，呼叫爬蟲程式
        for(int i = 1; i<= 13 ; i++){
            Document doc = Jsoup.parse(new URL("https://icook.tw/search/"+ keyWord +String.format("/?page=%d",i)),90000000);
            System.out.printf("搜尋食譜關鍵字:%s%n進入第%d頁...%n",keyWord,i);
            List searchURL = doc.select("[class=browse-recipe-card]").eachAttr("data-recipe-id");
            for(Object eachURI : searchURL){
                String fullURL = "https://icook.tw/recipes/"+eachURI;
                int recipeCount = searchURL.indexOf(eachURI) + 1;
                System.out.println(String.format("準備掃描第%d道食譜%n網址:%s%n",recipeCount,fullURL));
                //創建爬蟲物件，調用方法執行，取得每一頁的返回值ArrayList<每一頁的SQL語句>
                RecipeMainCrawler mainCrawler = new RecipeMainCrawler();
                ArrayList<String> sqlArrayList = mainCrawler.scanMain(fullURL,i,recipeCount);
                //將每一頁的返回值ArrayList<每一頁的SQL語句>進行拼接
                insertSQLMain.addAll(sqlArrayList);
            }
            recipeNumTotal += searchURL.size();
            System.out.printf("第%d頁掃描結束!\n",i);
        }
        System.out.printf("全數掃描完畢，共有%d道食譜成功掃描!\n",recipeNumTotal);

        //2.將收集完成的ArrayList<全部的SQL語句>丟到JDBC執行!
        System.out.printf("總共收集到%s條SQL語句\n",insertSQLMain.size());
        System.out.println("開始導入資料庫...");
        CrawlerJDBC.insertData(insertSQLMain);
    }
}
