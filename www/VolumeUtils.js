function VolumeUtils() {}

var VolumeUtils = {
  setVolume: function(value, successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, "VolumeUtils", "setVolume", [value]);
  },

  getVolume: function(successCallback, failureCallback) {
    return cordova.exec(successCallback, failureCallback, "VolumeUtils", "getVolume", []);
  }
};

module.exports = VolumeUtils;
