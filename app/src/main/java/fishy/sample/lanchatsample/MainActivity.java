package fishy.sample.lanchatsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvIp;
    TextView tvState;
    ImageView iconSetting;
    TextView tvContent;
    EditText editContent;
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    void init(){
        tvIp= (TextView) findViewById(R.id.tvIp);
        tvState= (TextView) findViewById(R.id.tvState);
        tvContent= (TextView) findViewById(R.id.tvContent);
        iconSetting= (ImageView) findViewById(R.id.iconSetting);
        editContent= (EditText) findViewById(R.id.editContent);
        btnSend= (Button) findViewById(R.id.btnSend);
    }
}
