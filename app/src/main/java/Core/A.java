package Core;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.util.Log;

import java.io.IOException;


/**
 * Created by Admin on 25-07-2016.
 */
public class A
{
Tag mytag;


public String  check_nfc(NfcManager nfcManager)
 {
     NfcAdapter nfcAdapter=nfcManager.getDefaultAdapter();
     if (nfcAdapter != null)
     {
        if(!nfcAdapter.isEnabled())
        {
            return "NFC DISABLED";
        }
         else
        {
            return "Nfc Available";
        }
     } else
     {

         return "NO NFC";
     }
 }



        public int Tagsize (Tag tag)
            {
            try
            {   Ndef ndef=Ndef.get(tag);
                ndef.connect();
                int i= ndef.getMaxSize();
                ndef.close();
                return i;
            }
                catch (IOException io)
                {
                    return Integer.parseInt(io.getMessage());
                }
            }

         public String Taginfo (Tag tag)
    {

       try
        {   Ndef ndef=Ndef.get(tag);
            ndef.connect();
            String t="";
            for (int i=0;i<ndef.getTag().getTechList().length;i++)
            {
                Log.e("loop","hello");
                t=t+ndef.getTag().getTechList()[i]+"\n";
                Log.e("loop",t);
            }
            String a=ndef.getType();
            ndef.close();
            return "\n"+a+"\n"+t;

        }
        catch (IOException io)
        {
            return io.getMessage();
        }
    }


}
