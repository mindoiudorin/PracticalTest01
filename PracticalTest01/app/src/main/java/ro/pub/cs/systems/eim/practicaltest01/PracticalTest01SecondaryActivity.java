package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        if(intent != null && intent.getExtras().containsKey("noClicks")) {
            TextView leftTextView = (TextView) findViewById(R.id.no_clicks_textView);
            leftTextView.setText(intent.getStringExtra("noClicks"));
        }
        findViewById(R.id.ok_button).setOnClickListener(button_click_listener);
        findViewById(R.id.cancel_button).setOnClickListener(button_click_listener);
    }

    private ButtonClickListener button_click_listener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ok_button:
                    setResult(RESULT_OK);
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED);
                    break;
            }
            finish();
        }
    }

}
