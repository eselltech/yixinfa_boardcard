package com.esell.boardcard.imp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.esell.controller.ICallback;
import com.esell.controller.PowerOnOffMode;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private final ControllerImp controllerImp = new ControllerImp();
    /**
     * 声音控制
     */
    private SeekBar.OnSeekBarChangeListener onVolumeChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    controllerImp.setVolume(getApplicationContext(), progress, new ICallback() {
                        @Override
                        public void onFinish(boolean success, String msg) {
                            Log.i(TAG, "onFinish (line 21): " + msg);
                        }
                    });
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };
    /**
     * 亮度控制
     */
    private SeekBar.OnSeekBarChangeListener onBrightnessChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    controllerImp.setBrightness(getApplicationContext(), progress, new ICallback() {
                        @Override
                        public void onFinish(boolean success, String msg) {
                            Log.i(TAG, "onFinish (line 21): " + msg);
                        }
                    });
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((SeekBar) findViewById(R.id.volumeSeekBar)).setOnSeekBarChangeListener(onVolumeChangeListener);
        ((SeekBar) findViewById(R.id.brightnessSeekBar)).setOnSeekBarChangeListener(onBrightnessChangeListener);
    }

    public void shutdownClick(View view) {
        controllerImp.shutdown(getApplicationContext(), new ICallback() {
            @Override
            public void onFinish(boolean success, String msg) {
                Log.i(TAG, "onFinish (line 21): " + msg);
            }
        });
    }

    public void rebootClick(View view) {
        controllerImp.reboot(getApplicationContext(), new ICallback() {
            @Override
            public void onFinish(boolean success, String msg) {
                Log.i(TAG, "onFinish (line 21): " + msg);
            }
        });
    }

    public void powerOnOffClick(View view) {
        /*获取当前时间*/
        Calendar calendar = Calendar.getInstance();
        /*+两分*/
        calendar.add(Calendar.MINUTE, 2);
        /*关机时间*/
        Calendar offCalendar = Calendar.getInstance();
        offCalendar.setTimeInMillis(calendar.getTimeInMillis());
        /*+五分*/
        calendar.add(Calendar.MINUTE, 5);
        /*开机时间*/
        Calendar onCalendar = Calendar.getInstance();
        onCalendar.setTimeInMillis(calendar.getTimeInMillis());
        controllerImp.setPowerOnOff(getApplicationContext(), PowerOnOffMode.EVERYDAY, offCalendar
                , onCalendar, new ICallback() {
                    @Override
                    public void onFinish(boolean success, String msg) {
                        Log.i(TAG, "onFinish (line 21): " + msg);
                    }
                });
    }

    public void cancelClick(View view) {
        controllerImp.cancelPowerOnOff(getApplicationContext(), new ICallback() {
            @Override
            public void onFinish(boolean success, String msg) {
                Log.i(TAG, "onFinish (line 21): " + msg);
            }
        });
    }

    public void screenshotClick(View view) {
        File externalCacheDir = getExternalCacheDir();
        File file = new File(externalCacheDir, "screenshot.jpg");
        controllerImp.screenshot(getApplicationContext(), file.getAbsolutePath(), new ICallback() {
            @Override
            public void onFinish(boolean success, String msg) {
                Log.i(TAG, "onFinish (line 21): " + msg);
            }
        });
    }
}
