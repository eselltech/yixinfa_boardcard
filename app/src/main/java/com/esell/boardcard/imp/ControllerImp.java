package com.esell.boardcard.imp;

import android.content.Context;
import android.content.Intent;

import com.esell.controller.DefControllerImp;
import com.esell.controller.ICallback;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

/**
 * 康凯斯 雍慧A64
 * 主板-BOARD：exdroid,
 * 系统启动程序版本号-BOOTLOADER：unknown,
 * 系统定制商-BRAND：Allwinner,
 * 设置参数-DEVICE：YoungFeel,
 * 显示屏参数-DISPLAY：YF_XXXG_1920x1080_NO_GMS_8801S_20181109,
 * 硬件识别码-FINGERPRINT：Allwinner/tulip_ads/tulip-ads:6.0.1/MMB29M/20181109:eng/test-keys,
 * 硬件名称-HARDWARE：sun50iw1p1,
 * HOST:yf-System-Product-Name,
 * 修订版本列表-ID：MMB29M,
 * 硬件制造商-MANUFACTURER：Allwinner,
 * 版本-MODEL：YF_XXXG,
 * 硬件序列号-SERIAL：74007890870c40150acf,
 * 手机制造商-PRODUCT：tulip_ads,
 * 描述Build的标签-TAGS：test-keys,
 * TIME:1541748093000,
 * builder类型-TYPE：eng,
 * USER:eleven,
 * CPU ABI:armeabi-v7a,
 * VERSION.RELEASE:6.0.1,
 * VERSION.SDK_INT:23,
 * MAC_ADDRESS:18:bb:26:6e:3f:5e,
 * NET_TYPE:wifi
 * 注意 类名 包名不能变
 *
 * @author NiuLei
 * @date 2019/3/1 16:31
 */
public class ControllerImp extends DefControllerImp {

    @Override
    public void reboot(Context context, ICallback callback) {
        exec("reboot", callback);
    }

    @Override
    public void shutdown(Context context, ICallback callback) {
        exec("reboot -p", callback);
    }

    @Override
    public void setPowerOnOff(Context context, int mode, Calendar offTime, Calendar onTime,
                              ICallback callback) {
        try {
            Intent intent = new Intent("android.56iq.intent.action.setpoweronoff");
            int[] onArray = calendar2IntArray(onTime);
            int[] offArray = calendar2IntArray(offTime);
            intent.putExtra("timeon", onArray);
            intent.putExtra("timeoff", offArray);
            intent.putExtra("enable", true); //使能开关机功能，设为false,则为关闭
            context.sendBroadcast(intent);
            callback.onFinish(true,
                    "定时开关机成功 on : " + Arrays.toString(onArray) + ",off : " + Arrays.toString(offArray));
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFinish(false, "定时开关机失败 " + e.toString());
        }
    }

    @Override
    public void cancelPowerOnOff(Context context, ICallback callback) {
        try {
            Intent intent = new Intent("android.56iq.intent.action.setpoweronoff");
            intent.putExtra("timeon", new int[]{0, 0, 0, 0, 0});
            intent.putExtra("timeoff", new int[]{0, 0, 0, 0, 0});
            intent.putExtra("enable", false); //使能开关机功能，设为false,则为关闭
            context.sendBroadcast(intent);
            callback.onFinish(true, "取消定时开关机成功");
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFinish(false, "取消定时开关机失败 " + e.toString());
        }
    }

    /**
     * 命令执行
     *
     * @param command  命令
     * @param callback 回调
     */
    private void exec(String command, ICallback callback) {
        DataOutputStream out = null;
        try {
            Process process = Runtime.getRuntime().exec("su");
            out = new DataOutputStream(process.getOutputStream());
            out.writeBytes(command + " \n");
            out.writeBytes("exit\n");
            out.flush();
            callback.onFinish(true, command + " success");
        } catch (IOException e) {
            e.printStackTrace();
            callback.onFinish(false, command + " failed " + e.toString());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

    /**
     * 时间转int数组
     *
     * @param calendar
     * @return
     */
    int[] calendar2IntArray(Calendar calendar) {
        return new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)};
    }
}
