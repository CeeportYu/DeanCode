package crawler.JDBC;
/*
Author:Dean Yu
Title:愛料理網站爬蟲程式(JDBC:連接資料庫新增數據)
Date:2021/6/1
*/
import java.sql.*;
import java.util.ArrayList;

public class CrawlerJDBC {
    public static void insertData(ArrayList<String> insertSQL){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //0.連接JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/recipeweb?serverTimezone=CST","root","root");
            stmt = conn.createStatement();

            //2.執行SQL語句(接收爬蟲傳過來的ArrayList<所有SQL語句>)
            int totalCount = 0;
            for(String everySQL : insertSQL){
                int insertNum = stmt.executeUpdate(everySQL);
                System.out.printf("成功導入%d筆數據\n",insertNum);
                totalCount += insertNum;
            }
            System.out.printf("JDBC執行完畢\n總計導入%d筆數據\n",totalCount);


        //3.例外處理與關閉JDBC

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if(rs != null){
                try {
                    rs.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(stmt != null){
                try {
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }
            if(conn != null){
                try {
                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }

        }


    }

}
