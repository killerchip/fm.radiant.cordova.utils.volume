package fm.radiant.cordova.utils.volume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.Log;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.util.Log;
import android.view.KeyEvent;

public class RemoteControlReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("radiant.fm.fm", "BUTON PRESSED BUTON PRESSED BUTON PRESSED BUTON PRESSED");

        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
            KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);

            Log.d("radiant.fm.fm", "BUTON PRESSED BUTON PRESSED BUTON PRESSED BUTON PRESSED");

            if (KeyEvent.KEYCODE_MEDIA_PLAY == event.getKeyCode()) {
            }
        }
    }
}