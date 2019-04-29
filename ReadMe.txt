该工具为报文发起工具。可以模拟客户端发起交易请求。

增加一个交易配置时，需要配置3个配置文件，其中一个可选。
spring/applicationContext-dataSet.xml   必选配置文件。配置测试数据
spring/applicationContext-tran.xml      必选配置文件。配置交易信息以及通信接出信息。支持tcp和http协议
spring/applicationContext-case.xml      可选配置文件，该配置文件用来将多个测试交易组成一个测试场景。可以一次发起该场景下的所有交易。

请求报文配置：
req/模块名/交易名    配置请求报文信息。每个交易一个。其中模块名和交易名需要和applicationContext-tran.xml 配置文件中保持一致。
              请求报文中通过${字段}标记来使用applicationContext-dataSet.xml中配置的测试数据。另外可以使用random(长度)来生成指定长度的随机字符串。该随机字符串由字母和数字组成。
              
发起报文可以通过如下url发起：
http://ip:9999/tran?id=SA021

发起场景可以通过如下url发起
http://ip:9999/case?caseid=ysf

工程需要jdk1.8

