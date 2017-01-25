package com.example.admin.teacounter;

import android.app.Activity;

import Core.Size;
import Core.A;
import Core.Read;
import Core.Write;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener
{

    /*--------------------------------varibales  --- nfc------------------------------------------*/
    Tag mytag;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    /*---------------------------------Gui variables------------------------------------------------------*/
    A a1;
    Read r1;
    Write w1;
    Size s1;
    TextView tv,tv1,tv2,tv3;
    Button b1,b2,b3,ifb1;
    Spinner sp1;
    LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nfcAdapter=NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[]{tagDetected};

        ifb1=new Button(MainActivity.this);
        ifb1.setOnClickListener(this);
        ifb1.setId(View.generateViewId());
        ll=(LinearLayout)findViewById(R.id.ll);
        tv=(TextView)findViewById(R.id.tv);
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        b3=(Button)findViewById(R.id.b3);
        sp1=(Spinner)findViewById(R.id.s1);
        a1=new A();
        r1=new Read();
        w1=new Write();
        s1=new Size();
        String status=(a1.check_nfc((NfcManager) this.getSystemService(NFC_SERVICE)));
        Log.e("stat",status);
        if(status.equals("NFC DISABLED"))
        {
            tv.setText(status);
            Toast.makeText(MainActivity.this, "Nfc Unavailable", Toast.LENGTH_LONG).show();

        }




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sz = s1.tag_size(mytag) - s1.written_tag_size(mytag);
                String sizetag = "Tag has " + sz + "bytes empty.";
                String sdata = (sp1.getSelectedItem()).toString();
                Log.e("spinnerdata",sdata);
                if (sdata.equals(null)) {
                    Toast.makeText(MainActivity.this, "PLZ SELECT COUNTER", Toast.LENGTH_LONG).show();
                } else
                {
                    int data1 = (r1.read_getmethod(new Intent(), mytag));
                if (data1==0)
                {
                    String data = sdata;
                    String return_data = w1.write_data( data, mytag);
                    Toast.makeText(MainActivity.this,"New DATA WRITTEN"+return_data,Toast.LENGTH_LONG).show();
                }
                else
                {
                    int x = Integer.parseInt(sdata) + data1;
                    String data = x + "";
                    String return_data = w1.write_data( data, mytag);
                    Toast.makeText(MainActivity.this," DATA WRITTEN"+return_data,Toast.LENGTH_LONG).show();
                }
            }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (w1.clear(mytag))

                {
                    Toast.makeText(MainActivity.this, "TAG CLEARED", Toast.LENGTH_LONG).show();
                } else
                {
                    Toast.makeText(MainActivity.this, "TAG NOT CLEARED", Toast.LENGTH_LONG).show();
                }
            }
        });














        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int d=(r1.read_getmethod(new Intent(), mytag));
                if(d==0  )
                {
                    Toast.makeText(MainActivity.this,"TAG IS EMPTY",Toast.LENGTH_SHORT).show();
                }
                else

                Log.e("empty1",d+"");
                {
                    tv2.setText(d+"");
                    tv2.setTextSize(100);
                    tv2.setTextColor((getResources().getColor(R.color.colorPrimary)));
                }
            }
        });
    }





    @Override
    public void onClick(View v)
    {
        try
        {
            Toast.makeText(this, "0" + ifb1.getId() + "     " + v.getId(), Toast.LENGTH_LONG).show();
            tv1.setText(w1.make_ndef(getIntent()));
            tv1.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        catch (Exception e)
        {
            Log.e("error_249",e.getMessage());
        }
    }







    protected void onNewIntent(Intent intent)
    {
        try
        {

            mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Toast.makeText(this, mytag.toString(), Toast.LENGTH_LONG).show();
            String tech[]=mytag.getTechList();
            String t="";
            for (int i=0;i<tech.length;i++)
            {
                t=t+tech[i]+"\n";

                if(tech[i].equals("android.nfc.tech.NdefFormatable"))
                {

                    ifb1.setText("MAKE NDEF");
                    ll.addView(ifb1);
                }
                else{
                    ll.removeView(ifb1);
                }
            }
            Log.e("tagtech", t);
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage()+"................",Toast.LENGTH_LONG).show();
        }
        // tv1.setText(r1.read_arrymethod(new Intent(), Ndef.get(mytag)));
    }


    @Override
    public void onPause()
    {
        super.onPause();

    }

    @Override
    public void onResume()
    {   super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters,
                null);


    }


}
