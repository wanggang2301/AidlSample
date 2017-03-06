package com.wg.aidlsample;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//

/**
 * aidl客户端
 * 备注：aidl文件的客户端和服务器端 包名必须相同
 */

public class MainActivity extends Activity {

    private ICat catService;

    private Button get;

    EditText color, weight;


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            catService = ICat.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            catService = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get = (Button) findViewById(R.id.get);
        color = (EditText) findViewById(R.id.color);
        weight = (EditText) findViewById(R.id.weight);

        Intent intent = new Intent();
        intent.setAction("com.wg.aidl.action.AIDL_SERVICE");
        bindService(intent, conn, Service.BIND_AUTO_CREATE);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    color.setText(catService.getColor());
                    weight.setText(catService.getWeight() + "");
                } catch (RemoteException e) {
                    e.printStackTrace();

                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unbindService(conn);
    }
}
