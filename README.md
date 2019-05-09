
* SpringBoot + MQTT

[参考](https://www.jianshu.com/p/8b0291e8ee02)

```
 Qos(Quality of Server)

 QoS = 0
    对于qos1而言，对于client而言，有且仅发一次publish包
    对于broker而言，有且仅发一次publish
    简而言之，就是仅发一次包，是否收到完全不管，适合那些不是很重要的数据。

    1. Publisher ---msg---> Broker ---msg----> Subscriber
    2. Publisher Delete Msg

 QoS = 1
    对于qos0而言，这个交互就是多了一次ack的作用
    但是会有个问题，尽管我们可以通过确认来保证一定收到客户端或服务器的message
    但是我们却不能保证message仅有一次
    也就是当client没收到service的puback或者service没有收到client的puback
    那么就会一直发送publisher

    1.Publisher ---Store(msg)---msg---> Broker
    2.Broker ---Store(msg)---msg---> Subscriber
    3.Broker ---PuBack---> Publisher.Delete(msg)
    4.Subscriber ---PuBack---> Broker.Delete(msg)
    5.若其中一个PuBack不存在,一直发送msg

 Qos = 2
    对于qos1而言，qos2可以实现仅仅接受一次message，其主要原理(对于publisher而言)，
    publisher和broker进行了缓存，其中publisher缓存了message和msgID
    而broker缓存了msgID，两方都做记录所以可以保证消息不重复
    但是由于记录是需要删除的，这个删除流程同样多了一倍

    1.Publisher ---Store(msg)---msg---> Broker
    2.Broker ---Store(msg)---PubRec---> Publisher (Borker存储并通知Publisher收到消息)
    3.Publisher ---PubRel---> Broker ---> msg ---> Subscriber (Publisher告知Borker可以下一步了)
    4.Broker ---PubCom---Publisher.Delete(msg) (Broker通知Publisher完成传输,Publisher删除存储)
    5.Subscriber ---Store(msg)--- PubRec----> Borker (Subscriber通知Borker已经接受到消息了)
    6.Borker --- PubRel ---> Subscriber.Notify(msg) (告诉Subscriber你可以做下一步了,Subscriber响应消息)
    7.Subscriber ---PubCom ----> Borker.Delete(msg),Subscriber.Delete(msg) (Subscriber告诉Broker我完成响应了,Borker和Subscriber都删除消息)

```