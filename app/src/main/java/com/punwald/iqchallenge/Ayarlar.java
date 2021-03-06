package com.punwald.iqchallenge;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Ayarlar extends AppCompatActivity {

    Button iletisimB;
    SeekBar sesB;
    AudioManager sesYöneticisi;
    TextView sesText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        Intent intent=getIntent();

        sesB= (SeekBar) findViewById(R.id.ses);
        iletisimB= (Button) findViewById(R.id.iletisim);
        sesText= (TextView) findViewById(R.id.sesText);

        iletisimB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent email=new Intent(Intent.ACTION_SEND);
                email.setType("message/rfc822");
                email.putExtra(Intent.EXTRA_EMAIL,"punwald@gmail.com");

                try {
                    startActivity(Intent.createChooser(email,"E-mail Uygulaması Seçiniz!"));
                }catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(),"E-mail gönderecek uygulama bulunamadı!",Toast.LENGTH_LONG).show();
                }

            }
        });

        sesYöneticisi= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        sesAyarla(sesB,AudioManager.STREAM_MUSIC);
    }

    private void sesAyarla(SeekBar bar,final int stream){

        bar.setMax(sesYöneticisi.getStreamMaxVolume(stream));
        bar.setProgress(sesYöneticisi.getStreamVolume(stream));

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sesYöneticisi.setStreamVolume(stream,progress,R.raw.backsound);
                sesText.setText("Ses Seviyesi: "+(progress*6.666666666666667));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}

