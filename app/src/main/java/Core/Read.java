package Core;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Admin on 25-07-2016.
 */
public class Read {
    public String read_cachemethod(Intent intent, Tag tag) {

        String s = "";
        try {

            Ndef ndef=Ndef.get(tag);
            ndef.connect();
            NdefMessage ndefMessage = ndef.getCachedNdefMessage();
            NdefRecord[] ndefRecords = ndefMessage.getRecords();

            for (int j = 0; j < ndefRecords.length; j++) {
                if (ndefRecords[j].getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                        Arrays.equals(ndefRecords[j].getType(), NdefRecord.RTD_TEXT)) {
                    byte[] payload = ndefRecords[j].getPayload();
                    String textEncoding;
                    if ((payload[0] & 128) == 0) textEncoding = "UTF-8";
                    else textEncoding = "UTF-16";
                    //String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                    int langCodeLen = payload[0] & 0077;

                    s += ("\n\nRecoed" + j + ":\n\"" +
                            new String(payload, langCodeLen + 1,
                                    payload.length - langCodeLen - 1, textEncoding) +
                            "\"");
                }
            }
            ndef.close();
            return s;
        } catch (IOException io) {
            return io.getMessage();
        }
    }


    public int read_getmethod(Intent intent, Tag tag) {

        String s = "";

        try

        {   Ndef ndef=Ndef.get(tag);
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            if (ndefMessage != null) {
                NdefRecord[] ndefRecords = ndefMessage.getRecords();

                for (int j = 0; j < ndefRecords.length; j++) {
                    if (ndefRecords[j].getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                            Arrays.equals(ndefRecords[j].getType(), NdefRecord.RTD_TEXT)) {
                        byte[] payload = ndefRecords[j].getPayload();
                        String textEncoding;
                        if ((payload[0] & 128) == 0) textEncoding = "UTF-8";
                        else textEncoding = "UTF-16";
                        //String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                        int langCodeLen = payload[0] & 0077;

                        s += (
                                new String(payload, langCodeLen + 1,
                                        payload.length - langCodeLen - 1, textEncoding) );
                    }
                }
                ndef.close();
                Log.e("eeeeeeeeeeeeee",s);
                if(s.equals(""))
                {
                    return 0;
                }
                return Integer.parseInt(s);
            }
            else
            {
                return 0;
            }
        }
            catch(IOException io)
            {
                Log.e("io_catch",io.getMessage());
                return  0;
        }
        catch(FormatException fe)
        {
            Log.e("fe_catch",fe.getMessage());
            return  0;
        }


    }

    public String read_arrymethod(Intent intent) {

        String s = "";
        Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        s = data.length + "\n\n  " + data[0].toString();
        Log.e("eeeee",s);

        try {
        if (data != null) {

                for (int i = 0; i < data.length; i++) {
                    NdefRecord[] recs = ((NdefMessage) data[i]).getRecords();
                    for (int j = 0; j < recs.length; j++) {
                        if (recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                                Arrays.equals(recs[j].getType(), NdefRecord.RTD_TEXT)) {

                            byte[] payload = recs[j].getPayload();
                            String textEncoding;
                            if ((payload[0] & 128) == 0) textEncoding = "UTF-8";
                            else textEncoding = "UTF-16";
                            //String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                            int langCodeLen = payload[0] & 0077;

                            s += ("\n\nNdefMessage[" + i + "], NdefRecord[" + j + "]:\n\"" +
                                    new String(payload, langCodeLen + 1,
                                            payload.length - langCodeLen - 1, textEncoding) +
                                    "\"");
                        }
                    }
                }
                return s;
            }
            else return "NO DATA";
        }
        catch (Exception e) {
                return e.getMessage();
            }


        }




    }
