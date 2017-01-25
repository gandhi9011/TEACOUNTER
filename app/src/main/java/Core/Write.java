package Core;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Admin on 26-07-2016.
 */
public class Write
{

    public boolean clear(Tag tag)
    {

        try {
            Ndef ndef=Ndef.get(tag);
            Log.e("---27",""+ndef);
            ndef.connect();
            Log.e("---29", "" + ndef);
            ndef.writeNdefMessage(new NdefMessage(new NdefRecord(NdefRecord.TNF_EMPTY, null, null, null)));
            Log.e("clear end", ndef.getMaxSize() + "");
            ndef.close();
            return true;
        }
        catch(Exception e)
        {
            Log.d("clear","clear_error"+e.getMessage());
            return false;
        }
    }











    public String  write_data(String data,Tag tag)
    {


        try
        {     Ndef ndef=Ndef.get(tag);


                NdefRecord ndefRecord = NdefRecord.createTextRecord("en", data);
                NdefMessage ndefMessage = new NdefMessage(ndefRecord);

  /*--------------Checking what it is....NDEF or NdefFormatable ---------------------------------------------*/

                if (ndef != (null))
                {
                    Log.d("rrrrrr", ndef + "");
                    ndef.connect();
                    ndef.writeNdefMessage(ndefMessage);
                    ndef.close();
                    return "true";
                } else

                {
                    ndef.close();
                    return "ERROR";
                }


            }







                catch (IOException e)
                {
                    return e.getMessage();
                }

                catch (Exception e)
                {
                    return e.getMessage();
                }

            }

        public String make_ndef(Intent intent)
        {
            try {

                String s = "";
                Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

                s = data.length + "\n\n  " + data[0].toString();
                Log.e("eeeee", s);

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
                catch (Exception e)
                {
                    Log.e("ndfform6", "ccccccccccc"+e.getMessage());
                    return e.getMessage();
                }

            }

            catch (Exception e)
            {
                Log.e("ndfform6", "ccccccccccc"+e.getMessage());
                return e.getMessage();
            }

        }




    }

