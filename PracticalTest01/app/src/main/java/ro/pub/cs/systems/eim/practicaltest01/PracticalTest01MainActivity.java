package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    boolean serviceStatus = false;

    EditText leftTextView = null;
    EditText rightTextView = null;

    private ButtonClickListener button_click_listener = new ButtonClickListener();
    private myBroadcastReceiver broadcastReceiver = new myBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*if( savedInstanceState  != null) {
            if (savedInstanceState.containsKey("leftNo")) {
                EditText leftTextView = (EditText) findViewById(R.id.left_textView);
                leftTextView.setText(savedInstanceState.getString("leftNo", "13"));
            }
            if (savedInstanceState.containsKey("rightNo")) {
                EditText rightTextView = (EditText) findViewById(R.id.right_textView);
                rightTextView.setText(savedInstanceState.getString("rightNo", "13"));
            }
        }*/

        leftTextView = (EditText) findViewById(R.id.left_textView);
        rightTextView = (EditText) findViewById(R.id.right_textView);

        findViewById(R.id.letf_button).setOnClickListener(button_click_listener);
        findViewById(R.id.right_button).setOnClickListener(button_click_listener);
        findViewById(R.id.secod_activity_button).setOnClickListener(button_click_listener);

        intentFilter.addAction(Intent.ACTION_SEND);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //super.onSaveInstanceState(savedInstanceState);
        Log.d("onSaveInstanceState", " AHAAAA ");
        savedInstanceState.putString("leftNo", leftTextView.getText().toString());
        savedInstanceState.putString("rightNo",rightTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
        Log.d("onRestoreInstanceState", " AHAAAA ");
        if (savedInstanceState.containsKey("leftNo")) {
            Log.d("onRestoreInstanceState", " \"leftNo\", \"13\" ");
            leftTextView.setText(savedInstanceState.getString("leftNo", "13"));
        }
        if (savedInstanceState.containsKey("rightNo")) {
            rightTextView.setText(savedInstanceState.getString("rightNo", "13"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT_FIRST_USER) {
            Toast.makeText(this, "result code : " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume () {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause () {
        unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy () {
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int noClicksL = Integer.parseInt(leftTextView.getText().toString());
            int noClicksR = Integer.parseInt(rightTextView.getText().toString());

            int totalNoClicks = noClicksL + noClicksR;

            switch (view.getId()) {
                case R.id.letf_button:

                    noClicksL++;
                    leftTextView.setText(Integer.toString(noClicksL));
                    break;
                case R.id.right_button:

                    noClicksR++;
                    rightTextView.setText(String.valueOf(noClicksR));
                    break;
                case R.id.secod_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    intent.putExtra("noClicks", Integer.toString(totalNoClicks));
                    startActivityForResult(intent, RESULT_FIRST_USER);
                    break;
            }
            if (totalNoClicks > 13 && !serviceStatus) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("first_number", noClicksL);
                intent.putExtra("second_number", noClicksR);
                startService(intent);
                serviceStatus = true;
            }
        }
    }

    private class myBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[BroadcastReceiver]", intent.getStringExtra("message"));
        }
    }
}
