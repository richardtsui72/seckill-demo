## 頁面說明
  前後端結合項目，兩種處理頁面方式，二者比較可以看 static 與 templates 下的 orderDetail 頁面
  
  1.前端頁面在 template 下，通過 controller 來回訪問，並 model.add 添加數據。h:text="${goods.goodsName}" 區數據，不可直接訪問
  2.在 static 下的頁面可直接訪問，並在頁面加載時ajax請求返回json數據，$("#goodsName").text(goods.goodsName);根據id注入資料。（原來前後端分離）
  
## 環境搭建

需要安裝配置Mysql、Redis、RabbitMQ

**Mysql:** 建表語句 sqldoc/創建t_user.sqldoc/創建t_user
