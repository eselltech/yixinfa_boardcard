# yixinfa_boardcard

> 以下是易信发的远程控制sdk使用说明（详细可参考例子）
---
##### 第一步
创建你的项目，applicationId必须是com.esell.boardcard.imp（也就是包名），不然易信发app找不到插件，加载不了句柄，也就无法远程控制。

##### 第二步
引入类库
```
dependencies {
    implementation 'com.android.support:appcompat-v7:28.0.0'
}
```

##### 第三步
在com.esell.boardcard.imp包中创建一个名为ControllerImp的类（类名一定要是这个），并继承DefControllerImp，然后根据需要重写自己想要的方法。
具体可以查看例子。

---

下面是具体方法说明：

下面的每个方法如有ICallback返回都应该回调

void onFinish(boolean success, String msg);
//success为true是成功，false是失败，msg是描述

###### 还有注意的就是，默认实现的都不是一定可以控制的，要自行测试

方法名|方法说明|注意事项
:--|:--:|--:
setVolume(Context context, int volume, ICallback callback)|设置设备声音|volume声音范围0-100，如果你的板卡没有必须的api可以用默认实现的，也就是不需要重写该方法。
setBrightness(Context context, int brightness, ICallback callback)|设置设备亮度|brightness亮度范围0-255，如果你的板卡没有必须的api可以用默认实现的，也就是不需要重写该方法。
reboot(Context context, ICallback callback)|设置设备立即重启|如果你的板卡没有必须的api可以用默认实现的，也就是不需要重写该方法。
shutdown(Context context, ICallback callback)|设置设备立即关机|如果你的板卡没有必须的api可以用默认实现的，也就是不需要重写该方法。
setPowerOnOff(Context context, int mode, Calendar offTime, Calendar onTime,ICallback callback)|设置设备定时开关机|offTime关机时间，onTime开机时间，该方法没有提供默认实现，如要这个功能，必须自己重写该方法。关机时间和开机时间是一定成对出现的，关机时间和开机时间都是最早的时间。
cancelPowerOnOff(Context context, ICallback callback)|取消设备开关机时间|设置定时开关机时，会默认调用此方法一次，调用顺序是先调用取消开关机，再设置开关机。当所有定时开关机时间都删除的时候，也会调用此方法。主要是为了避免设置冲突。易信发本体会帮我们管理时间设置的。
screenshot(final Context context, final String filePath, final ICallback callback)|远程截屏（保存路径：sd卡目录/yixinfa/screenshot.jpg）|filePath截图的绝对路径，这个是写死的，你截到的图片必须在这个目录下，不然上传不了到后台，如果你的板卡没有必须的api可以用默认实现的，也就是不需要重写该方法。
firmwareUpdate(Context context, String filePath, ICallback callback)|固件升级|filePath固件的绝对路径。这个方法没有默认实现。如果要该功能，请复写。
firmwareReset(Context context, ICallback callback)|固件重置|这个方法没有默认实现。如果要该功能，请复写。

注：还有部分扩展功能，必须要易信发本体实现了才可用，目前sdk里面用到的，都是实现的（具体作用可以阅读javadoc）




