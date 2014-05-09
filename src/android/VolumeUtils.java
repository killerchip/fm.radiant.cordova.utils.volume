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
import android.util.Log;

public class VolumeUtils extends CordovaPlugin {
  private static final String TAG = "VolumeUtils";

  private Context context;
  private AudioManager manager;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    context = cordova.getActivity().getApplicationContext();
    manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    boolean actionState = true;

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
