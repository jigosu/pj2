
Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main1_1','Blooming Notebook','Blooming Notebook','Notebook','$20.00','/img/main/bestseller/main1_1.JPG','S');
Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main1_2','Overthinker Notebook','Overthinker Notebook','Notebook','$20.50','/img/main/bestseller/main1_2.JPG','S');
Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main1_3','Friendship Notebook','Friendship Notebook','Notebook','$9.00','/img/main/bestseller/main1_3.JPG','S');
Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main1_4','School Notebook','School Notebook','Notebook','$8.00','/img/main/bestseller/main1_4.JPG','S');
Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main1_Test1','TSchool Notebook','TSchool Notebook','Notebook','$8.00','/img/main/bestseller/main1_4.JPG','S');


Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main2_1','Oh Baby Cards','Oh Baby Cards','Greeting Cards','$3.00','/img/main/bestseller/main2_1.JPG','S');
Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main2_2','Birthday Love','Birthday Love','Greeting Cards','$3.00','/img/main/bestseller/main2_2.JPG','S');
Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main2_3','Black Boy Joy Birthday Card','Black Boy Joy Birthday Card','Greeting Cards','$3.00','/img/main/bestseller/main2_3.JPG','S');


Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main3_1','When Life Hands You Lemons Gift Bag','When Life Hands You Lemons Gift Bag','Wrapping Paper/Bags','$12.00','/img/main/bestseller/main3_1.JPG','S');
Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main3_2','I am Every Woman Gift Bags','I am Every Woman Gift Bags','Wrapping Paper/Bags','$12.00','/img/main/bestseller/main3_2.JPG','S');


Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main4_1','Hello! Sticky Notes','Hello! Sticky Notes','Sticky Notes','$3.00','/img/main/bestseller/main4_1.JPG','S');
Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main4_2','Me Time Sticky Notes','Me Time Sticky Notes','Sticky Notes','$3.00','/img/main/bestseller/main4_2.JPG','S');
Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main4_3','Werk Sticky Notes','Werk Sticky Notes','Sticky Notes','$3.00','/img/main/bestseller/main4_3.JPG','S');
Insert into stocks(mainshow,id,name,category,price,imgsrc,showorhide) values ('main4_4','Goal Getter Sticky Notes','Goal Getter Sticky Notes','Sticky Notes','$3.00','/img/main/bestseller/main4_4.JPG','S');


Insert into site_user(username,password,email) values('admin','$2a$10$9ngWr3mjs.ndVxl3uPBOJewZ8YrvwWrskxg9OB150TxlTLusK089G','abc@gallup.co.kr');
Insert into site_user(username,password,email) values('user','$2a$10$9ngWr3mjs.ndVxl3uPBOJewZ8YrvwWrskxg9OB150TxlTLusK089G','abdc@gallup.co.kr');
Insert into site_user(username,password,email) values('1111','$2a$10$uvhzt3PpwotmfN6.Kl3uteChG03x7GXMd1A3dq1z6nHIxb3ClznxK','abdcd@gallup.co.kr');





Insert into purchase(ORDERDATETIME,USER_ID,DEPOSIT,ORDERID,TOTALPRICE ) values('2025-01-06 11:01:10.436109','3','0','11112025-01-06T11:01:10.436108800','3.0');
Insert into purchase(ORDERDATETIME,USER_ID,DEPOSIT,ORDERID,TOTALPRICE ) values('2025-01-06 13:19:08.901384','3','0','11112025-01-06T13:19:08.901384300','3.0');
Insert into purchase(ORDERDATETIME,USER_ID,DEPOSIT,ORDERID,TOTALPRICE ) values('2025-01-06 13:19:22.799564','3','4','11112025-01-06T13:19:22.799563700','15.0');
Insert into purchase(ORDERDATETIME,USER_ID,DEPOSIT,ORDERID,TOTALPRICE ) values('2025-01-06 13:30:13.340885','2','4','user2025-01-06T13:30:13.340884700','12.0');

Insert into PURCHASEDETAILS(COUNT,PURCHASE_ID,STOCK_NUM,PRICE) values(1,'1','12','3.0');
Insert into PURCHASEDETAILS(COUNT,PURCHASE_ID,STOCK_NUM,PRICE) values(1,'2','12','3.0');
Insert into PURCHASEDETAILS(COUNT,PURCHASE_ID,STOCK_NUM,PRICE) values(1,'3','13','3.0');
Insert into PURCHASEDETAILS(COUNT,PURCHASE_ID,STOCK_NUM,PRICE) values(1,'3','10','12.0');
Insert into PURCHASEDETAILS(COUNT,PURCHASE_ID,STOCK_NUM,PRICE,STATUSDATETIME,STATUS) values(1,'4','3','9.0','2025-01-10 10:42:34.955017','Refunded');
Insert into PURCHASEDETAILS(COUNT,PURCHASE_ID,STOCK_NUM,PRICE,STATUSDATETIME,STATUS) values(1,'4','6','3.0','2025-01-10 10:42:34.955017','');


Insert into REFUND(REFUNDDATETIME,ORDERID,STATUS) values('2025-01-10 10:42:34.953017','user2025-01-06T13:30:13.340884700','1')
