#  新建说明书SDK账号
应用名称
coworkapp
AppKey
278c9861d187a
App Secret
238d1d44c589ec0b5a9919919c179ec2

# server API 说明
IP设置：IP = 127.0.0.1:8080
用户登录： 
http://127.0.0.1:8080/YfriendService/DoGetUser?user=jim&password=123&action=login
用户查找：
http://127.0.0.1:8080/YfriendService/DoGetUser?user=75FFCD29EB7F88087AAE06878BC78C26&action=search
获取信息类别： 
http://127.0.0.1:8080/YfriendService/DoGetType

#  项目需要修改IP的地方

1. xmpptool中的host地址
2. 在Android项目修改
3. 



----------------------------------------------
update 
2018.08.27
1. 用户身份，分为角色进入不用的页面（普通用户（提交问题、授权后可以旁听），专家、主持人、秘书（专业讨论））
2. 提问UI


2018.09.13
1. 主题列表更新。实现主题的内容展示， 主题的启停。
   主题内容分拆。
2. 细化用户信息编辑
3. 细化消息内容发送
4. 细化在线研讨
