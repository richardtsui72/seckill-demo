## 頁面說明

  前後端結合項目，兩種處理頁面方式，二者比較可以看 static 與 templates 下的 orderDetail 頁面
  
  1.前端頁面在 template 下，通過 controller 來回訪問，並 model.add 添加數據。h:text="${goods.goodsName}" 區數據，不可直接訪問
  
  2.在 static 下的頁面可直接訪問，並在頁面加載時ajax請求返回json數據，$("#goodsName").text(goods.goodsName);根據id注入資料。（原來前後端分離）
  
## 環境搭建

需要安裝配置Mysql、Redis、RabbitMQ

**Mysql:** 建表語句 sqldoc/創建t_user.sqldoc/創建t_user

**Redis:** 本機安裝，或遠端linux伺服器直接安裝，不安裝專案起不來。

**RabbitMQ：** 遠端linux伺服器直接安裝，不安裝專案起不來。


## 注意事項

1.在這個專案中核心就是秒殺的實現：不能超賣、不能重複搶
  
  *不能重複搶透過唯一索引實現，預設建表時沒有添加，壓測可以把用戶加少點商品多一點就可以復現重複購買

2.優化不過就是把資料庫的重複訪問，能放到redis就放到redis；而如果訪問redis太多了就再加一層內存標記  

3.redis和mysql要么都在遠程，要么都在本地，否則可能會出現redis緩存優化了但QPS沒提升