# Activity是什么？

1、Activity

Activity是一个为用户提供一个用于任务交互的画面的程序组件。例如，拨打电话，拍照，发邮件和查看地图。

每一个activity都被分配一个窗口。在这个窗口里，你可以绘制用户交互的内容。 这个窗口通常占满屏幕，但也有可能比屏幕小，并且浮在其它窗口的上面。

一个应用程序通常由多个activity组成，它们彼此保持弱的绑定状态。典型的，当一个activity在一个应用程序内被指定为主activity, 那么当程序第一次启动时，它将第一个展现在用户面前。为了展现不同的内容，每一个activity可以启动另外一个activity。 每当一个新的activity被启动，那么之前的Activity将被停止。但系统将会把它压入一个栈（“back stack”即后退栈）,当一个新的activity启动时，新的activity将被 放到栈顶并获得用户焦点。后台栈遵循后进先出的栈机制。所以当用户完成当前页面并按下返回按钮时，它将被pop出栈（并销毁），之前的activity将被恢复。

当一个activity因为另一个activity的启动而被停止，那么其生命周期中的回调方法，将会以状态改变的形式被调用。 activity通过它自身状态的改变可以收到多个回调方法。当系统创建，停止，恢复，销毁它的时候。并且每个回调方法都给你做相应处理工作的机会。 例如，当停止的时候，你的activity应当释放比较大的对象，例如网络连接，数据连接。当你的activity恢复时，你可以请求必须的资源并恢复一些被打断的动作。 这些状态事务的处理就构成了activity的生命周期。

二、Activity的生命周期

1、activity生命周期

![](/assets/activity_lifecycle.png)

2、activity生命周期回调方法说明

| 方法 | 描述 | 之后可否被杀死? | 下一个方法 |
| :--- | :--- | :--- | :--- |
| onCreate\(\) | activity第一次被创建时调用。在这里你应该完成所有常见的静态设置工作——创建view、绑定list数据等等。 本方法传入一个包含了该activity前一个状态的Bundle对象（如果之前已捕获了状态的话，详见后面的保存Activity状态）。下一个回调方法总是onStart\(\)。 | 否 | onStart\(\) |
| onRestart\(\) | activity被停止后、又再次被启动之前调用。下一个回调方法总是onStart\(\) | 否 | onStart\(\) |
| onStart\(\) | activity要显示给用户之前调用。如果activity进入前台，则下一个回调方法是onResume\(\)；如果进入隐藏状态，则下一个回调方法是onStop\(\)。 | 否 | onResume\(\) 或 onStop\(\) |
| onResume\(\) | activity开始与用户交互之前调用。这时activity是在activity栈的顶端，用户可以向其中输入。下一个回调方法总是onPause\(\)。 | 否 | onPause\(\) |
| onPause\(\) | 当系统准备启动另一个正在恢复的activity时调用。这个方法通常用于把未保存的改动提交为永久数据、停止动画播放、以及其它可能消耗CPU的工作等等。 它应该非常迅速地完成工作，因为下一个activity在本方法返回前是不会被恢复运行的。如果activity返回前台，则下一个回调方法是onResume\(\)；如果进入用户不可见状态，则下一个是onStop\(\) | 可以 | onResume\(\) 或 onStop\(\) |
| onStop\(\) | 当activity不再对用户可见时调用。原因可能是它即将被销毁、或者其它activity（已有或新建的）被恢复运行并要覆盖本activity。如果activity还会回来与用户交互，则下一个回调方法是onRestart\(\)；如果这个activity即将消失，则下一个回调方法是onDestroy\(\) | 可以 | onRestart\(\) 或 onDestroy\(\) |
| onDestroy\(\) | 在本activity被销毁前调用。这是activity收到的最后一个调用。 可能是因为activity完成了工作（有些人在这里调用finish\(\)）， 也可能是因为系统为了腾出空间而临时销毁activity的本实例。 可以利用isFinishing\(\) 方法来区分这两种情况。 | 可以 | 无 |

“之后可否被杀死？”列指明了系统是否可以在这个方法返回之后的任意时刻杀掉这个activity的宿主进程， 而不再执行其它流程上的activity代码。 有三个方法是标为“可以”：（ onPause\(\)、 onStop\(\)、 和onDestroy\(\)）。 因为onPause\(\)是三个方法中的第一个， 一旦activity被创建， onPause\(\) 就是进程可以被杀死之前最后一个能确保被调用的方法 ——如果系统在某种紧急情况下必须回收内存，则 onStop\(\) 和onDestroy\(\) 可能就不会被调用了。因此，你应该使用 onPause\(\) 来把至关重要的需长期保存的数据写入存储器（比如用户所编辑的内容）。 不过，应该对必须通过 onPause\(\) 方法进行保存的信息有所选择，因为该方法中所有的阻塞操作都会让切换到下一个activity的停滞，并使用户感觉到迟缓。

**“之后可否被杀死？”**列中标为“否”的方法，在它们被调用时的那一刻起，就会保护本activity的宿主进程不被杀掉。 因此，只有在 onPause\(\) 方法返回时至 onResume\(\) 方法被调用时之间，activity才会被杀掉。直到 onPause\(\) 再次被调用并返回时，activity都不会再次允许被杀死。

3、管理Activity的生命周期

通过实现回调方法来管理你的activity的生命周期，对于开发一个健壮而又灵活的应用程序而言是至关重要的。 与其它activity的关联性、自身的任务和后退栈（back stack）直接影响着一个activity的生命周期。

activity可能处于三种基本的状态：

Resumed

activity在屏幕的前台并且拥有用户的焦点。（这个状态有时也被叫做“running”。）

Paused

另一个activity在前台并拥有焦点，但是本activity还是可见的。 也就是说，另外一个activity覆盖在本activity的上面，并且那个activity是部分透明的或没有覆盖整个屏幕。 一个paused的activity是完全存活的（Activity 对象仍然保留在内存里，它保持着所有的状态和成员信息，并且保持与window manager的联接），但在系统内存严重不足的情况下它能被杀死。

Stopped

本activity被其它的activity完全遮挡住了（本activity目前在后台）。 一个stopped的activity也仍然是存活的（Activity 对象仍然保留在内存中，它保持着所有的状态和成员信息，但是不再与window manager联接了）。 但是，对于用户而言它已经不再可见了，并且当其它地方需要内存时它将会被杀死。

如果activity被paused或stopped了，则系统可以从内存中删除它，通过请求finish（调用它的 finish\(\) 方法）或者直接杀死它的进程。 当这个activity被再次启动时（在被finish或者kill后），它必须被完全重建。

2、实现生命周期回调方法

当一个activity在上述描述的状态之间转换时，它将通过各种回调方法来获得通知。 所有的回调方法都是hooks，当activity状态发生改变时你都可以 重写这些方法来执行对应的工作。 以下的activity提纲包含了所有基本的生命周期方法：

```
public class ExampleActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The activity is being created.
    }
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
}
```

总体来讲，这些方法定义了一个activity的完整的生命周期。 通过实现这些方法，你可以监控activity生命周期中三个嵌套的循环：

* activity的完整生存期会在 onCreate\(\) 调用和 onDestroy\(\) 调用之间发生。 activity应该在 onCreate\(\) 方法里完成所有“全局global”状态的设置（比如定义layout）， 而在onDestroy\(\) 方法里释放所有占用的资源。 例如，如果你的activity有一个后台运行的线程，用于从网络下载数据，那么你应该在 onCreate\(\) 方法里创建这个线程并且在 onDestroy\(\) 方法里停止这个线程。
* activity的可见生存期会在 onStart\(\) 调用和 onStop\(\) 调用之间发生。在这期间，用户可在屏幕上看见这个activity并可与之交互。 例如，当一个新的activity启动后调用了 onStop\(\) 方法，则这个activity就无法被看见了。 在这两个方法之间，你可以管理那些显示activity所需的资源。例如，你可以在 onStart\(\) 方法里注册一个 BroadcastReceiver 用于监控影响用户界面的改动。并且当用户不再看到你的显示内容时，在 onStop\(\) 方法里注销掉它。 系统会在activity的整个生存期内多次调用 onStart\(\) 和onStop\(\)， 因为activity可能会在显示和隐藏之间不断地来回切换。
* activity的前台生存期会在 onResume\(\) 调用和 onPause\(\) 之间发生。在这期间，activity是位于屏幕上所有其它的activity之前，并且拥有用户的输入焦点。 activity可以频繁地进入和退出前台——例如， 当设备进入休眠时或者弹出一个对话框时， onPause\(\) 就会被调用。因为这个状态可能会经常发生转换，为了避免切换迟缓引起的用户等待，这两个方法中的代码应该相当地轻量化。

4、保存activity的状态

当一个activity被paused或者stopped时，activity的状态可以被保存。 的确如此，因为 Activity 对象在paused或者stopped时仍然被保留在内存之中——它所有的成员信息和当前状态都仍然存活。 这样用户在activity里所作的改动全都还保存着，所以当activity返回到前台时（当它“resume“），那些改动仍然有效。

不过，如果系统是为了回收内存而销毁activity，则这个 Activity 对象就会被销毁，这样系统就无法简单地resume一下就能还原完整状态的activity。 如果用户要返回到这个activity的话，系统必须重新创建这个Activity 对象。可是用户并不知道系统是先销毁activity再重新创建了它的，所以，他很可能希望activity完全保持原样。 这种情况下，你可以保证activity状态的相关重要信息都由另一个回调方法保存下来了，此方法让你能保存activity状态的相关信息： onSaveInstanceState\(\)。

在activity变得很容易被销毁之前，系统会调用 onSaveInstanceState\(\)方法。 调用时系统会传入一个Bundle对象， 你可以利用 putString\(\) 之类的方法，以键值对的方式来把activity状态信息保存到该Bundle对象中。 然后，如果系统杀掉了你的application进程并且用户又返回到你的activity，系统就会重建activity并将这个 Bundle 传入onCreate\(\) 和onRestoreInstanceState\(\) 中，你就可以从 Bundle 中解析出已保存信息并恢复activity状态。如果没有储存状态信息，那么传入的 Bundle 将为null（当activity第一次被创建时就是如此）。

![](/assets/restore_instance.png)

activity状态完整地返回给用户的两种方式：被销毁destroyed后再被重建，而且必须恢复了之前保存的状态；或是被停止stopped后再被恢复resumed，状态都完整保留着。

**注意：**activity被销毁之前，并不能确保每次都会调用 onSaveInstanceState\(\) ，因为存在那些不必保存状态的情况（比如用户使用BACK键离开了你的activity，因为用户明显是关了这个activity）。 如果系统要调用 onSaveInstanceState\(\) 方法，那么它通常会在 onStop\(\) 方法之前并且可能是在 onPause\(\) 之前调用。

不过，即使你没有实现 onSaveInstanceState\(\) 方法，有些activity状态还是会通过 Activity 类缺省实现的onSaveInstanceState\(\) 方法保存下来。特别的是，缺省为layout中的每个 View 实现了调用相应的onSaveInstanceState\(\) 方法，这允许每一个view提供自己需被保存的信息。 几乎Android框架下所有的widget都会在适当的时候实现该方法，这样，任何UI上可见的变化都会自动保存下来，并在activity重建后自动恢复。 例如，EditText widget会保存所有用户已经输入的文本， CheckBoxwidget 也会保存是否被选中。你所要做的工作仅仅是为每一个你想要保存其状态的widget提供一个唯一的ID（就是 android:id 属性）。如果这个widget没有ID的话，系统是无法保存它们的状态的。

通过把android:saveEnabled 设置为"false",或者调用 setSaveEnabled\(\) 方法，你也可以显式地阻止layout中的某个view保存状态。 通常不应该禁用保存，不过假如你需要恢复activity UI的各个不同的状态，也许可以这么做。

尽管缺省实现的 onSaveInstanceState\(\) 方法会保存activity UI的有用信息，你仍然需要覆盖它来存入更多的信息。 例如，你可能需要保存在activity生命周期中改变的成员变量值（可能是关于UI恢复的值，但是默认情况下，存放这些UI状态的成员变量值是不会被恢复的）。

因为默认实现的 onSaveInstanceState\(\) 方法已经帮你保存了一些UI的状态，所以如果你重写此方法是为了保存更多的状态信息，那么在执行自己的代码之前应该确保先调用一次父类的 onSaveInstanceState\(\) 方法。同理，如果重写 onRestoreInstanceState\(\) 的话，也应该调用一次父类的该方法，这样缺省的代码就能正常恢复view的状态了。

**注意：**因为 onSaveInstanceState\(\) 并不保证每次都会被调用，所以你应该只用它来记录activity的一些临时状态信息（UI的状态）——千万不要用它来保存那些需要长久保存的数据。 替代方案是，你应该在用户离开activity的时候利用 onPause\(\) 来保存永久性数据（比如那些需要存入数据库里的数据）。

一个检测应用程序状态恢复能力的好方法就是旋转设备，使得屏幕方向发生改变。 当屏幕的方向改变时，因为要换用符合实际屏幕参数的资源，系统会销毁并重建这个activity。 正因如此，你的activity能够在被重建时完整地恢复状态是非常重要的，因为用户会在使用应用程序时会频繁地旋转屏幕。

5、设备配置改动后的处理

设备的某些配置可能会在运行时发生变化（比如屏幕方向、键盘可用性以及语言）。 当发生这些变化时，Android会重建这个运行中的activity（系统会调用 onDestroy\(\) ，然后再马上调用 onCreate\(\) ）。这种设计有助于应用程序适用新的参数配置，通过把你预置的可替换资源（比如对应各种屏幕方向和尺寸的layout）自动重新装载进入应用程序的方式来实现。

如果你采取了适当的设计，让activity能够正确地处理这些因为屏幕方向而引起的重启，并能如上所述地恢复activity状态， 那么你的应用程序将对生命周期中其它的意外事件更具适应能力。

处理这类重启的最佳方式，就是利用 onSaveInstanceState\(\) 和onRestoreInstanceState\(\) （或者 onCreate\(\) ）进行状态的保存和恢复，如上所述。

6、多activity的合作

当activity启动另一个activity时，它俩生命周期的状态都会发生转换。 第一个activity paused并stopped（尽管它也可能不会被stopped，如果它仍然后台可见的话），而另一个activity是被created。 如果这两个activity共用了保存在磁盘或其它地方的数据，那么请明白：在第二个activity被created之前，第一个activity还没有完全被stopped，这点非常重要。 或多或少，第二个activity的启动进程与第一个activity的关闭进程在时间上会发生重叠。

生命周期回调方法的顺序是很明确的，特别是两个activity位于同一个进程中、一个启动另一个的时候。 下面就是Aactivity A启动Activity B时的操作顺序：

1. Activity A的 onPause\(\)方法，如果活动后台不可见的话，onStop\(\)方法同样运行，否则不运行。
2. Activity B的 onCreate\(\) ，onStart\(\) 和onResume\(\) 方法依次运行。（Activity B现在获得用户焦点。）

以上预设的生命周期回调方法顺序使你能够对一个activity启动另一个activity时的转换信息进行管理。 例如，如果第一个activity停止时你须写入数据库以便后续的activity可以读取数据，那么你应该在 onPause\(\) 方法而不是 onStop\(\) 方法里写入数据库。

