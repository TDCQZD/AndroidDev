Activity如何使用？

1、自定义方法继承Activity或AppCompatActivity;

2、在配置文件中声明activity

```
<manifest ... >
  <application ... >
      <activity android:name=".ExampleActivity" />
      ...
  </application ... >
  ...
</manifest >
```

3、使用[`intent-filter`](http://www.android-doc.com/guide/topics/manifest/intent-filter-element.html)

[`<activity>`](http://www.android-doc.com/guide/topics/manifest/activity-element.html)也可以用很多[`<intent-filter>`](http://www.android-doc.com/guide/topics/manifest/intent-filter-element.html)来指定其他的组件怎样激活它。

当你使用Android SDK tools来创建一个程序，主activity将会自动包含一个被分类为"launcher"的intent filter，如下:

```
<activity android:name=".ExampleActivity" android:icon="@drawable/app_icon">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

[`<action>`](http://www.android-doc.com/guide/topics/manifest/action-element.html)元素指定程序的入口。

[`<category>`](http://www.android-doc.com/guide/topics/manifest/category-element.html)指出该activity应该被列如系统的启动器\(launcher\)\(允许用户启动它\)

如果你想要你的程序更加独立，并不想让其他程序访问你的activity,那么你就不必声明intent filter,只有一个activity 应该有"main"action和"launcher"分类，例如上述例子。你不想公开的activity应该不包含任何intent filter.但你可以使用明确 的intent来启动它们（显式调用）。然而，如果你想要你的activity响应其他程序（或当前程序）的隐式intent,你必须为activity定义额外的intent filter（隐式调用）。 每一个你想响应的intent,都必须包含一个[`<intent-filter>`](http://www.android-doc.com/guide/topics/manifest/intent-filter-element.html)，并包含一个[`<action>`](http://www.android-doc.com/guide/topics/manifest/action-element.html)元素，另外，可以包含一个[`<category>`](http://www.android-doc.com/guide/topics/manifest/category-element.html)也可以包含一个[`<data>`](http://www.android-doc.com/guide/topics/manifest/data-element.html)元素。 这些元素指定了intent 的类型。

4、Intent启动Activity

简单地启动一个已知的activity, 通过创建一个明确的intent。这个intent指定了activity的类名。

```
Intent intent = new Intent(this, SignInActivity.class);
startActivity(intent);
```

然而，你的程序可能想要展示某些动作，例如发邮件，短信，微博，或者使用你activity中的数据。 这时候，你就不应该使用自己的activity来做这些工作。你应该调用系统中其他程序提供的响应功能。 这是intent真正体现其价值的地方。你可以创建一个描述了响应动作的intent,然后系统来为你挑选完成任务的程序。 如果有多个选择，系统会提示用户进行选择。例如你想让用户发邮件，你可以创建下面的intent：

```
Intent intent = new Intent(Intent.ACTION_SEND);
intent.putExtra(Intent.EXTRA_EMAIL, recipientArray);
startActivity(intent);
```

[`EXTRA_EMAIL`](http://www.android-doc.com/reference/android/content/Intent.html#EXTRA_EMAIL)是一个邮件intent中添加的额外字符串数组，它指定了邮件该发给哪些邮件地址。当一个邮件程序响应了这个intent, 它将读取这些地址，并把他们放置到邮件表单的被发送人栏。这时，邮件程序被启动。当用户完成了发送操作，你的activity会被恢复。

Intent 传递参数 Bundle

```
    Intent intent=new Intent(this,SecondActivity.class);  
           intent.putExtra("nv_name","小龙女");  
           Bundle bundle=new Bundle();  
           bundle.putString("nan_name","杨过");  
           intent.putExtra("bundle",bundle);  
           startActivity(intent);  


       Intent intent = getIntent();  
            textView1.setText(intent.getStringExtra("nv_name"));  
            Bundle bundleExtra = intent.getBundleExtra("bundle");  
            textView2.setText(bundleExtra.getString("nan_name"));
```

5、通过startActivityForResult方法来得到Activity的回传值

你想要启动一个activity,并从这个activty获得一个结果。 这时，要通过[`startActivityForResult()`](http://www.android-doc.com/reference/android/app/Activity.html#startActivityForResult%28android.content.Intent, int%29)\(取代[`startActivity()`](http://www.android-doc.com/reference/android/app/Activity.html#startActivity%28android.content.Intent%29)\) 来启动activity。 然后通过实现[`onActivityResult()`](http://www.android-doc.com/reference/android/app/Activity.html#onActivityResult%28int, int, android.content.Intent%29)回调方法来获得返回后的结果。 当这个后续的activity被关闭，它将发送一个[`Intent`](http://www.android-doc.com/reference/android/content/Intent.html)给[`onActivityResult()`](http://www.android-doc.com/reference/android/app/Activity.html#onActivityResult%28int, int, android.content.Intent%29)方法。

例如，你可能想要取一个联系人的信息。下面介绍怎么创建intent并处理结果:

```
private void pickContact() {
    // Create an intent to "pick" a contact, as defined by the content provider URI
    Intent intent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
    startActivityForResult(intent, PICK_CONTACT_REQUEST);
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // If the request went well (OK) and the request was PICK_CONTACT_REQUEST
    if (resultCode == Activity.RESULT_OK && requestCode == PICK_CONTACT_REQUEST) {
        // Perform a query to the contact's content provider for the contact's name
        Cursor cursor = getContentResolver().query(data.getData(),
        new String[] {Contacts.DISPLAY_NAME}, null, null, null);
        if (cursor.moveToFirst()) { // True if the cursor is not empty
            int columnIndex = cursor.getColumnIndex(Contacts.DISPLAY_NAME);
            String name = cursor.getString(columnIndex);
            // Do something with the selected contact's name...
        }
    }
}
```

这个例子展示了使用[`onActivityResult()`](http://www.android-doc.com/reference/android/app/Activity.html#onActivityResult%28int, int, android.content.Intent%29)来获取结果的基本方法。 第一步要判断请求是否被成功响应，通过判断

`resultCode`是不是[`RESULT_OK`](http://www.android-doc.com/reference/android/app/Activity.html#RESULT_OK)， 然后判断这个响应是不是针对相应的请求 ，此时只要判断`requestCode`和发送时提供的第二个参数[`startActivityForResult()`](http://www.android-doc.com/reference/android/app/Activity.html#startActivityForResult%28android.content.Intent, int%29)是否相匹配。 最后，查询[`Intent`](http://www.android-doc.com/reference/android/content/Intent.html)中的data信息。 \(`data`参数\)。

6、关闭Activity

你可以通过调用[`finish()`](http://www.android-doc.com/reference/android/app/Activity.html#finish%28%29)来终止activity。 你也可以调用[`finishActivity()`](http://www.android-doc.com/reference/android/app/Activity.html#finishActivity%28int%29)来终止你之前启动了的一个独立activity。

7、Activity启动方式

