package fishy.sample.lanchatsample;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    TextView tvIp;
    TextView tvState;
    ImageView iconSetting;
    TextView tvContent;
    EditText editContent;
    Button btnSend;
    AlertDialog dialogIp;
    EditText editIp;
    ClientThread clientThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ConnectConfig.tempIp = TempIpInfo.readRecentIp();
        EventBus.getDefault().register(this);
        new ServerThread().start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateText(MsgEvent msgEvent) {
        tvContent.append(msgEvent.getMsg() + "\n");
    }

    void init() {
        tvIp = (TextView) findViewById(R.id.tvIp);
        tvState = (TextView) findViewById(R.id.tvState);
        tvContent = (TextView) findViewById(R.id.tvContent);
        iconSetting = (ImageView) findViewById(R.id.iconSetting);
        editContent = (EditText) findViewById(R.id.editContent);
        btnSend = (Button) findViewById(R.id.btnSend);

        tvIp.setText(ConnectConfig.getLocalIp());
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ConnectConfig.tempIp)) {
                    toast("ip为null");
                } else {
//                    new ClientThread().start();
                    if(clientThread==null){
                        clientThread=new ClientThread();
                        clientThread.start();
                    }else{
                        clientThread.sendMsg();
                    }
                }
            }
        });
        iconSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIpDialog();
            }
        });
    }

    void toast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    void createIpDialog() {
        if (dialogIp == null) {
            editIp = new EditText(MainActivity.this);
            editIp.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            dialogIp=new AlertDialog.Builder(this)
                    .setTitle("setIp")
                    .setView(editIp)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TempIpInfo.recordRecentIp(editIp.getText().toString());
                        }
                    })
                    .create();
        }
        editIp.setText(ConnectConfig.tempIp);
        dialogIp.show();
    }
}
