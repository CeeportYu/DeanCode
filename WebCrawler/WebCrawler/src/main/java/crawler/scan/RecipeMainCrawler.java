package crawler.scan;
/*
Author:Dean Yu
Title:愛料理網站爬蟲程式(爬蟲主程式)
Date:2021/6/1
*/
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeMainCrawler {
    public ArrayList<String> scanMain(String recipeURL, int pageNum, int recipeCount) {

        //0.創建SQL語句ArrayList
        ArrayList<String> insertSQLAll = new ArrayList();
        ArrayList<String> sqlMat = new ArrayList();
        ArrayList<String> sqlStep = new ArrayList();

        //1.取得URL，取得DOM操作物件
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(String.format("%s", recipeURL)), 100000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("食譜爬蟲開始:");
        System.out.printf("正在掃描第%d頁，第%d道食譜%n", pageNum, recipeCount);

        //2.取得食譜名稱(RecTitle)
        String RecTitle = doc.select("#recipe-name").text();
        System.out.println(String.format("1.食譜名稱:%s", RecTitle));
        String recTitle = String.format("\'%s\'",RecTitle);    //----格式化SQL語句參數

        //3.取得食譜ID(RecId)
        String href = doc.select("[rel=canonical]").attr("href");
        String RecId = href.substring(25);
        System.out.println(String.format("2.食譜ID:%s", RecId));
        int recId =Integer.valueOf(RecId);     //----格式化SQL語句參數

        //3.食譜圖片URL(RecPic)
        String RecPic = doc.select("[class=glightbox ratio-container ratio-container-4-3]").attr("href");
        System.out.println(String.format("3.食譜圖片URL:%s", RecPic));
        String recPic = String.format("\'%s\'",RecPic);   //----格式化SQL語句參數

        //4.食譜文字簡介(RecText)
        String RecText = doc.select("[class=description]>p").text();
        System.out.println(String.format("4.食譜簡介文字:%s", RecText));
        //----格式化SQL語句參數
        RecText = RecText.replace("'","");
        String recText  = String.format("\'%s\'",RecText);
//      System.out.printf("測試:%s",recText);


        //5.食譜烹飪時間(RecTime)
        String RecTime = "";
        try {
            RecTime = doc.select("[class=time-info info-block] span").first().text();
        } catch (NullPointerException e) {
//            System.out.println("這道菜沒有製作時間");
            RecTime = "0";
        } finally {
            System.out.println(String.format("5.食譜烹飪時間:%s", RecTime + "分鐘"));
        }
        int recTime= Integer.valueOf(RecTime);   //----格式化SQL語句參數

        //6.食譜份數(RecNumb)
        String RecNumb = "";
        try {
            RecNumb = doc.select("[class=servings-info info-block] span").first().text();
        } catch (NullPointerException e) {
            System.out.println("這道食譜沒有份數");
            RecNumb = "0";
        } finally {
            System.out.println(String.format("6.食譜可供份數:%s", RecNumb + "人份"));
        }
        int recNum = Integer.valueOf(RecNumb);    //----格式化SQL語句參數

        //7.食譜關鍵字(RecTag)
        String RecTag = doc.select("[class=recipe-related-keyword-item-link-name]").text();
        System.out.println(String.format("7.食譜關鍵字:%s", RecTag));
        //----格式化SQL語句參數:產生JSON格式關鍵字
        String[] recTagSplit = RecTag.split("\\s+");
        JSONObject tagJson = new JSONObject();
        JSONArray tagArray = new JSONArray();
        for(int i=0;i<recTagSplit.length;i++){
            tagArray.add(recTagSplit[i]);
        }
        tagJson.put("Rectag",tagArray);
        String jsonTag = String.format("\'%s\'",tagJson);
//      System.out.println(jsonTag);


        //8.食譜按讚數(RecLiked)
        String RecLiked = doc.select(".stat-left .stat-content").text();
        System.out.println(String.format("8.食譜按讚數:%s", RecLiked));
        //----格式化SQL語句
        String splitLike = RecLiked.replaceAll("[^0-9]", "");
        int recLike= Integer.valueOf(splitLike);


        //9.食譜創建時間(RecCreated)
        String RecCreated = doc.select("[class=recipe-detail-meta-item]").attr("datetime");
        System.out.println(String.format("9.食譜創建時間:%s", RecCreated));
        //----格式化SQL語句
        String recCreated = String.format("\'%s\'",RecCreated);


        //10.食譜瀏覽數(RecViews)
        String RecViews = doc.select("[class=recipe-detail-meta-item]").next().text();
        System.out.println(String.format("10.食譜瀏覽人數:%s", RecViews));
        //----格式化SQL語句
        int recViews;
        String views = RecViews.replaceAll("[^0-9 .]", "");
        views = views.replaceAll(" ","");
        if(views.contains(".")){
            double doubleType = Double.valueOf(views);
            int x;
            doubleType = doubleType * 10000;
            x = (int)doubleType;
            recViews = x;
//          System.out.println(recViews);
        }else {
            recViews = Integer.valueOf(views);
//          System.out.println(recViews);
        }

        //11.& 12.食譜食材與醬料
        List testEmpty = doc.select("[class=group group-0] a").eachText();
        List MateriaLName = doc.select("[class^=group group-] a").eachText();
        List MateriaLUnit = doc.select("[class^=group group-] .ingredient-unit").eachText();


        if (testEmpty.isEmpty() == true) {
//            System.out.println("Group0為空的狀況----------------------------");
            List matGpName = doc.select("[class^=group group-] .group-name").eachText();
//            System.out.println(matGpName);
            System.out.println("11.食譜食材與份量:");
            for(int i = 0;i < matGpName.size();i++){
                String gpForm1 =String.format("[class^=group group-%s] a",i+1);
                String gpForm2 =String.format("[class^=group group-%s] .ingredient-unit",i+1);
                //--------------------------------------------------
                String MateriaLGroup = (String) matGpName.get(i);
                MateriaLName = doc.select(gpForm1).eachText();
                MateriaLUnit = doc.select(gpForm2).eachText();
                System.out.printf("[%s]\n",MateriaLGroup);

                //生成SQL語句
                for(int z = 0;z < MateriaLName.size();z++){
                    //列印每筆食材
                    System.out.printf("%s %s\n",MateriaLName.get(z),MateriaLUnit.get(z));
                    //設定SQL參數
                    String gp = String.format("\'%s\'",MateriaLGroup);
                    String MtName = String.format("\'%s\'",MateriaLName.get(z));
                    String MtUnit = String.format("\'%s\'",MateriaLUnit.get(z));
                    //產生SQL語句--------
                    String insertSQLMat = String.format("INSERT INTO recipe_material(`RecId`, `Gp`, `MateriaLName`, `UnitNum`) VALUES(%d,%s,%s,%s)",recId,gp,MtName,MtUnit);
//                  System.out.printf("產生SQL語句(Mat):%s\n",insertSQLMat);
                    sqlMat.add(insertSQLMat);
                }
            }

        }
        else{
//            System.out.println("Group0不為空的狀況----------------------------");
            List matGpName = doc.select("[class^=group group-] .group-name").eachText();
//            System.out.println(matGpName);
            matGpName.add(0,"主食材");
//            System.out.println(matGpName);
            System.out.println("11.食譜食材與份量:");
            for(int i = 0;i < matGpName.size();i++){
                String gpForm1 =String.format("[class^=group group-%s] a",i);
                String gpForm2 =String.format("[class^=group group-%s] .ingredient-unit",i);
                //--------------------------------------------------
                String MateriaLGroup = (String) matGpName.get(i);
                MateriaLName = doc.select(gpForm1).eachText();
                MateriaLUnit = doc.select(gpForm2).eachText();
                System.out.printf("[%s]\n",MateriaLGroup);

                //生成SQL語句
                for(int z = 0;z<MateriaLName.size();z++){
                    //列印每筆食材
                    System.out.printf("%s %s\n",MateriaLName.get(z),MateriaLUnit.get(z));
                    //設定SQL參數
                    String gp = String.format("\'%s\'",MateriaLGroup);
                    String MtName = String.format("\'%s\'",MateriaLName.get(z));
                    String MtUnit = String.format("\'%s\'",MateriaLUnit.get(z));
                    //產生SQL語句--------
                    String insertSQLMat = String.format("INSERT INTO recipe_material(`RecId`, `Gp`, `MateriaLName`, `UnitNum`) VALUES(%d,%s,%s,%s)",recId,gp,MtName,MtUnit);
//                  System.out.printf("產生SQL語句(Mat):%s\n",insertSQLMat);
                    sqlMat.add(insertSQLMat);
                }
            }
        }

        //13.食譜步驟(Step)與內容(StepText)與圖片(StepPic)
        List recStep = null;
        List stepContent = null;
        List stepPic = null;

        stepPic = doc.select(".recipe-details-step-item a").eachAttr("href");
        recStep = doc.select(".recipe-details-step-item").eachAttr("id");
        stepContent = doc.select(".recipe-step-description-content").eachText();

        System.out.println("13.食譜步驟與內文:"+String.format("總共掃描到%d筆步驟，以及%d筆步驟內文。",recStep.size(),stepContent.size()));

        String Step ="";
        String StepPic ="";
        String StepCon ="";

        for(int i=0;i<recStep.size();i++){
            Step = String.format("\'%s\'",recStep.get(i)).replaceAll("[^0-9]","");
            try{
                StepCon = String.format("\'%s\'",stepContent.get(i));
            }catch (IndexOutOfBoundsException e){
                StepCon ="\'空\'";
                System.out.printf("%s \n",recStep.get(i) +"此步驟沒有內容");
            }
            try{
                  StepPic = String.format("\'%s\'",stepPic.get(i));
                  System.out.println("食譜步驟圖片URL:" + stepPic.get(i));

            }catch (IndexOutOfBoundsException e){
                StepPic ="\'空\'";
                }
            System.out.printf("第%d步:%s\n",i+1,StepCon);
            //生成SQL語句
            String insertSQLStep = String.format("INSERT INTO recipe_step(`RecId`, `Step`, `StepPic`, `StepText`) VALUES(%d,%s,%s,%s)",recId,Step,StepPic,StepCon);
//          System.out.println(insertSQLStep);
            sqlStep.add(insertSQLStep);

        }

        //14.生成SQL(recipe_main)
        //額外參數設定
        int recCategory = 2; //種類食譜家常菜2
        int recCal = (int)Math.floor(Math.random()*600)+100;//隨機卡路里
        int userId = (int)Math.floor(Math.random()*19)+1;;//發布者ID
        String insertSQL = String.format("insert into recipe_main(RecId, RecCategory, RecTitle, RecPic, RecText, RecTime, RecNum, RecCal, RecTag, UserId, RecLiked, RecCreated, RecViews) values(%d,%d,%s,%s,%s,%d,%d,%d,%s,%d,%d,%s,%d)"
                ,recId,recCategory,recTitle,recPic,recText,recTime,recNum,recCal,jsonTag,userId,recLike,recCreated,recViews);
//      System.out.printf("測試SQL語句樣式:%s\n",insertSQL);

        //15.將三個表所有語句放入同一個ArrayList中(準備丟給JDBC去執行)，需按照順序!
        //(1)拼接SQL(recipe_main)
        insertSQLAll.add(insertSQL);
        //(1)拼接SQL(recipe_material)
        insertSQLAll.addAll(sqlMat);
        //(1)拼接SQL(recipe_step)
        insertSQLAll.addAll(sqlStep);

        //16.執行結束，返回最終ArrayList
        System.out.printf("------------------------------------%n");
        return insertSQLAll;

     }
  }

