package com.yyang2.demo_contactstest;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView contactsView;
    ArrayAdapter<String> adapter;
    List<String> contactsList = new ArrayList<String>();

//    在onCreate()方法中，我们首先获取了ListView 控件的实例，并给它设置好了适配器，
//    然后就去调用readContacts()方法。下面重点看下readContacts()方法，可以看到，这里使用
//    了ContentResolver 的query()方法来查询系统的联系人数据。不过传入的Uri 参数怎么有些奇
//    怪啊， 为什么没有调用Uri.parse() 方法去解析一个内容URI 字符串呢？ 这是因为
//    ContactsContract.CommonDataKinds.Phone类已经帮我们做好了封装，提供了一个CONTENT_URI
//    常量，而这个常量就是使用Uri.parse()方法解析出来的结果。接着我们对Cursor 对象进行遍
//    历， 将联系人姓名和手机号这些数据逐个取出， 联系人姓名这一列对应的常量是
//    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME，联系人手机号这一列对应的常
//    量是ContactsContract.CommonDataKinds.Phone.NUMBER。两个数据都取出之后，将它们进
//    行拼接，并且中间加上换行符，然后将拼接后的数据添加到ListView 里。最后千万不要忘记
//    将Cursor 对象关闭掉。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactsView = (ListView) findViewById(R.id.contacts_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList);
        contactsView.setAdapter(adapter);
        readContacts();
    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            // query contacts data
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()) {
                // get contact name
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                //get contact phone number
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactsList.add(displayName + "\n" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
