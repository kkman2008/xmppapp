# 1、 protocol test
      http://127.0.0.1:8080/YfriendService/DoGetUser?user=jim&password=123&action=login
      所有的IP和Port，现在的程序可配置的。

# 2、 一些code说明与备注
    原资讯list，默认部分为newsFragment
    1. newsFragment里面含有viewpager
    2. viewpager关联的是fragment_title.xml
        [viewpager的adapter类是， MainFragmentPagerAdapter.java]
    3. fragment_title.xml中的list调用的是listview_item.xml
       [listview的adapter类是TitleListViewAdapter]
#3 单元测试取问题列表 
    http://localhost:8080/YfriendService/DoGetQuestion?action=get_question_list&type=-1
    http://localhost:8080/YfriendService/DoGetSeminar?action=get_seminartopic_list&type=-1
    http://localhost:8080/YfriendService/DoGetSeminar?action=get_seminar_discuss_list&discusstype=327e2fb7-2c3c-4d64-bbc5-0f00ae80d2bd
    http://localhost:8080/YfriendService/DoGetSeminarArragement?action=get_seminar_attendee&themesubjectid=0931ce4b-41e5-4592-985d-074abb220781
    
      -1 全部，0 未开始，1进行中，2已结束

-----
 extension日志
  1.   - [x] 用户问题，附件问题
  2.  研讨主题，与研讨组关系
  3.  研讨过程，用户问题状态问题
  4.  开始研讨无响应问题


      
    