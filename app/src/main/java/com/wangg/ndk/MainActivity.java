package com.wangg.ndk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidParameterException;

public class MainActivity extends AppCompatActivity {
    SerialControl com;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        com = new SerialControl();
        //串行端口终端
        com.setPort("/dev/ttyUSB8");
        //波特率
        com.setBaudRate("9600");
        openComPort(com);
    }

    public void onClick(View view){
        com.sendHex("xxxx");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeComPort(com);
    }

    private void ShowMessage(String sMsg)
    {
        Toast.makeText(this, sMsg, Toast.LENGTH_SHORT).show();
    }
    private class SerialControl extends SerialHelper{
        public SerialControl(){
        }

        @Override
        protected void onDataReceived(final ComBean ComRecData)
        {
        }
    }
    //----------------------------------------------------关闭串口
    private void closeComPort(SerialHelper ComPort){
        if (ComPort!=null){
            ComPort.stopSend();
            ComPort.close();
        }
    }
    //----------------------------------------------------开启串口
    private void openComPort(SerialHelper ComPort){
        try
        {
            ComPort.open();
        } catch (SecurityException e) {
            ShowMessage("打开串口失败:没有串口读/写权限!");
        } catch (IOException e) {
            ShowMessage("打开串口失败:未知错误!");
        } catch (InvalidParameterException e) {
            ShowMessage("打开串口失败:参数错误!");
        }
    }
}
