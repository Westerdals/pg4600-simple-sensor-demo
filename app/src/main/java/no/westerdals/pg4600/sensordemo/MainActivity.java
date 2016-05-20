package no.westerdals.pg4600.sensordemo;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView payloadTextView = (TextView) findViewById(R.id.payloadTextView);

        String action = getIntent().getAction();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);

            MifareUltralight ultralight = MifareUltralight.get(tag);

            try {
                ultralight.connect();
                byte[] payload = ultralight.readPages(8);
                payloadTextView.setText(new String(payload));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                try {
                    ultralight.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    Toast.makeText(this, "Couldn't close", Toast.LENGTH_SHORT).show();
                }
            }
        }

        Toast.makeText(this, "onCreate done", Toast.LENGTH_SHORT).show();

    }
}
