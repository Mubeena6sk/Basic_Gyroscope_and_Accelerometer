# Basic_Gyroscope_and_Accelerometer
###Android implementation of Gyroscope and Accelerometer sensors

Example implementation that retrieves the accelerometers or gyroscope readings inside an Android Activity and displays the X, Y, Z axes values in TextViews

#####Step-by-Step Instructions

1.Import SensorManager object into your project.
2.Implement OnSensorChange interface to your Android Activity.
3.Create an AccelerometerSensor object in your Activity.
4.In the onResume and onPause methods of the Android Activity call the registerSensor and the unregisterSensor methods (from the SensorManger object) respectively
Retrieve accelerometer or gyroscope values(X, Y, Z) from the onSensorChange method of your Activity.

Reference: https://developer.android.com/reference/android/hardware/SensorManager
