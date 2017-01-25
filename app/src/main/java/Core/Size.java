package Core;

import android.nfc.Tag;
import android.nfc.tech.Ndef;

import java.io.IOException;

/**
 * Created by Admin on 28-07-2016.
 */
public class Size {
    public int tag_size(Tag tag) {
        try

        {   Ndef ndef=Ndef.get(tag);
            ndef.connect();
            int ts= ndef.getMaxSize();
            ndef.close();
            return  ts;
        }

        catch(IOException io)
        {
            return (Integer.parseInt(io.getMessage()));
        }

        catch(Exception e)
        {
            return (Integer.parseInt(e.getMessage()));
        }

    }


    public int written_tag_size(Tag tag) {
        try

        {   Ndef ndef=Ndef.get(tag);
            ndef.connect();
            int wts= ndef.getNdefMessage().toByteArray().length;
            ndef.close();
            return  wts;
        }

        catch(IOException io)
        {
            return (Integer.parseInt(io.getMessage()));
        }

        catch(Exception e)
        {
            return (Integer.parseInt(e.getMessage()));
        }

    }
}