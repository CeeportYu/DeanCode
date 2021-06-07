package test.jsoup;


import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

public class JDBCTEST {
    @Test
    public void Go(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //0.連接JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/recipeweb?serverTimezone=CST","root","root");
            stmt = conn.createStatement();

            //1.宣告必要連接變數(main)
            //RecId, RecCategory, RecTitle, RecPic, RecText, RecTime, RecNum, RecCal, RecTag, UserId, RecLiked, RecCreated, RecViews


            //2.執行SQL語句
            String insertSQL02 = " RecLiked, RecCreated, RecViews) values(351107,5,'自製薄皮pizza- “瑪格麗特”篇\uD83D\uDE0B','https://tokyo-kitchen.icook.network/uploads/recipe/cover/351107/9e5fae31b9ab88df.jpg','自製薄皮pizza- “瑪格麗特”篇 女兒說跟餐廳的一樣好吃\uD83D\uDE18 這個餅皮很厲害，可依各人喜好換成不同食材(配料), 加韓式烤肉也非常好吃，大家可以試看看哦 ^^ Carols Home/卡蘿愛料理: http://carolhuang2020.pixnet.net/blog',20,4,625,'[\"pizza 韓式烤肉 瑪格麗特 乳酪絲 餅皮 麵糰 自製\"]',1,1,'2020-09-07',13)";
            String insertSQL03 = "INSERT INTO recipe_material(`RecId`, `Gp`, `MateriaLName`, `UnitNum`) VALUES(375521,'雞腿2片','雞腿','2片')";

            int insertNum = stmt.executeUpdate(insertSQL03);
            System.out.printf("成功導入%d筆數據",insertNum);






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
