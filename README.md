PhoneGap/Android - _Modified_
================
[cm] Changes ---------------
<pre>
* New GPS handler added; Old one left in (in case they fix it).
* Licence is in Licence.java as it was getting on my wick (also at the bottom of this file)
* Depreciated PhoneGap GEO handler; screw it.
* New functions 
** CM-GEO (locator.*) stored in MyLocation.java
** On starting the application the class will try to get GPS or Network location based on interval of 60seconds/100meters (you can change this)
** locator.getLatitude() locator.getLongitude() get last known location
** locator.forceUpdate() - clears the stored lat/lng response and re-requests it (then use above to collect)
** locator.setDistanceInterval(100)  - default is 100 meters
** locator.setTimeInterval(60000)  - default is 60seconds (60000ms) 
** locator.stopUpdates() - stops auto-update of location
** locator.resumeUpdates() - resumes auto-update
** locator.getGeoVersion() - Version of my additional class (used mostly so I can ensure my own app has updated to the latest JAR file)
* Corrected missing semicolons on functions in javascript files.
* Added some more demo/tests to index.html
** Depreciated: PhoneGap GEO as it doesnt currently work. 
** If they fix it, this fork becomes useless(ish)

TODO: still need to add tests for setD/setT/stopU/resumeU

-- New MyLocation.java and DroidGap changes by Chris McKee <pcdevils@gmail.com> --
</pre>
-----------------------------


PhoneGap/Android is an Android application library that allows for PhoneGap based projects to be built for the Android Platform. PhoneGap based applications are, at the core, an application written with web technology: HTML, CSS and JavaScript. 

Pre-requisites
--------------
- Java JDK 1.5
- Android SDK Package [http://developer.android.com](http://developer.android.com)
- Apache ANT
- Ruby

Recommended
-----------
- Eclipse (Great for debugging and extending PhoneGap/Android with your own Java plugins.)

Getting Started with PhoneGap/Android
--------------------------------------

1. From the root of this repo run the following command to generate a new PhoneGap/Android app:

<pre>    
    ./droidgap [android_sdk_path] [name] [package_name] [www] [path]

    android_sdk_path ... The path to your Android SDK install.
    name ............... The name of your application.
    package_name ....... The name of your package (For example: com.nitobi.demo)
    www ................ The path to your www folder. (Wherein your HTML, CSS and JS app is.)
    path ............... The path to generate the application.
</pre>

Thats it!

Importing a PhoneGap/Android app into Eclipse
---------------------------------------------

1. File > New > Project...
2. Android > Android Project
3. Create project from existing source (point to the generated app). This should import the project into Eclipse. Almost done!
4. Right click on libs/phonegap.jar and add to build path.
5. Right click on the project root: Run as > Run Configurations
6. Click on the Target tab and select Manual (this way you can choose the emulator or device to build to).

For more info see
-----------------
- [http://docs.phonegap.com](http://docs.phonegap.com)
- [http://wiki.phonegap.com](http://wiki.phonegap.com)

Licence
-------

	/* License (MIT)
	 * Copyright (c) 2008 Nitobi
	 * website: http://phonegap.com
	 * Permission is hereby granted, free of charge, to any person obtaining
	 * a copy of this software and associated documentation files (the
	 * Software), to deal in the Software without restriction, including
	 * without limitation the rights to use, copy, modify, merge, publish,
	 * distribute, sublicense, and/or sell copies of the Software, and to
	 * permit persons to whom the Software is furnished to do so, subject to
	 * the following conditions:
	 *
	 * The above copyright notice and this permission notice shall be
	 * included in all copies or substantial portions of the Software.
	 * 
	 * THE SOFTWARE IS PROVIDED AS IS, WITHOUT WARRANTY OF ANY KIND,
	 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
	 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
	 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
	 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
	 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
	 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
	 */