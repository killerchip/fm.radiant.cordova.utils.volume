package fm.radiant.cordova.utils.volume;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.util.Log;
import android.content.ComponentName;

import fm.radiant.cordova.utils.volume.RemoteControlReceiver;

public class VolumeUtils extends CordovaPlugin {
  private static final String TAG = "VolumeUtils";

  private Context context;
  private AudioManager manager;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    context = cordova.getActivity().getApplicationContext();
    manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

    manager.registerMediaButtonEventReceiver(new ComponentName("fm.radiant.cordova.utils.volume", RemoteControlReceiver.class.getName()));
//
//
    //OnAudioFocusChangeListener afChangeListener = new OnAudioFocusChangeListener() {
    //  public void onAudioFocusChange(int focusChange) {
    //    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
    //      // Pause playback
    //    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
    //      // Resume playback
    //       manager.registerMediaButtonEventReceiver(new ComponentName(context.getPackageName(), RemoteControlReceiver.class.getName()));
    //    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
    //      // Stop playback
    //       manager.unregisterMediaButtonEventReceiver(new ComponentName(context.getPackageName(), RemoteControlReceiver.class.getName()));
    //    }
    //  }
    //};
  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    boolean actionState = true;
    manager.registerMediaButtonEventReceiver(new ComponentName(context.getPackageName(), RemoteControlReceiver.class));

    if ("setVolume".equals(action)) {

      try {
        int volume = getVolumeToSet(args.getInt(0));
        manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        callbackContext.success();
      } catch (Exception e) {
        Log.e(TAG, "Error setting volume: " + e.getMessage(), e);
        actionState = false;
      }
    }else if("getVolume".equals("getVolume")){
        int currVol = getCurrentVolume();
        callbackContext.success((int)currVol);
        Log.d(TAG, "Volume: " + currVol);
    }else{
      actionState = false;
    }
    return actionState;
  }

  private int getVolumeToSet(int percent){
    int volLevel;
    int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    volLevel = Math.round((percent * maxVolume) / 100);

    return volLevel;
  }

  private int getCurrentVolume(){
    try{
      int volLevel;
      int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
      int currSystemVol = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
      volLevel = Math.round((currSystemVol * 100) / maxVolume);

      return volLevel;
    }catch (Exception e) {
      Log.e(TAG, "Get volume error: " + e.getMessage(), e);
      return 1;
    }
  }
}
