package santi.secretapp.utils;

import android.content.Context;
import android.content.Intent;

import santi.secretapp.R;

/**
 * Created by santi.mo on 16-5-19.
 */
public class ShareToApp{

    public void shareMsg(Context context) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");
        intent.putExtra("sms_body",context.getString(R.string.share_text).toString());
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share_subject).toString());
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_text).toString());
        intent.putExtra(Intent.EXTRA_TITLE, context.getString(R.string.share_title).toString());
        String shareapptitle =  context.getString(R.string.share_app_title).toString();
        context.startActivity(Intent.createChooser(intent, shareapptitle));
    }
}
