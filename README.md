# whatsapp-messager-sender
Sending a message using facebook messenger and whatsapp for a particular number/userid, with a predefined
message.


USAGE
-----
Add module.aar library (app/libs/) in your project 

```
api project(':module')
```

JAVA
-----

```java

// WhatsApp, in the message view, for a particular phone number. 
// Entering a phone number, start with the country code, for example, 447700900000
MessageUtil.send(mContext, "447700900000", MessageUtil.App.WHATSAPP);

// WhatsApp, in the message view, for a particular phone number, with a predefined message.
// Entering a phone number, start with the country code, for example, 447700900000
MessageUtil.send(mContext, "Hello World", "447700900000", MessageUtil.App.WHATSAPP);

// Facebook Messenger, in the message view, for a particular
Facebook userId.
MessageUtil.send(mContext, "4", MessageUtil.App.MESSENGER); //Facebook userId
```
LICENCE
-----

Whatsapp Messager Sender by [Marcelo Korjenioski] is licensed under a [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
