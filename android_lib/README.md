#Android lib
##Description
Android lib helps in user authentication based on mobile number, GCM registration and notification management.

##Integration
clone the library into the same folder as your project.

**1)** Add below code into your settings.gradle file

    include ':android_lib'
    project(':android_lib').projectDir = new File(settingsDir, '../android_lib')

**2)** Add below code into your build.gradle file

    dependencies {
        compile project(':android_lib')
    }

example:

	buildscript {
	    repositories {
	        jcenter()
	    }
	    dependencies {
	        classpath 'com.android.tools.build:gradle:1.0.1'
	    }
	}
	apply plugin: 'com.android.application'
	android {
	    repositories {
	        jcenter()
	    }
	    dependencies {
	        compile 'com.google.android.gms:play-services-base:6.5.87'
	        compile 'com.google.android.gms:play-services-location:6.5.87'
	    }
	    compileSdkVersion 21
	    buildToolsVersion "21.1.2"
	    sourceSets {
	        main {
	            manifest.srcFile 'AndroidManifest.xml'
	            java.srcDirs = ['src']
	            res.srcDirs = ['res']
	        }
	    }
	    lintOptions {
	        disable 'InvalidPackage'
	    }
	}

	dependencies {
	    compile project(':android_lib')
	}

If everything goes fine you should be able to see android_lib folder along with your project
and a build.gradle(Module:android_lib) will also be created

##Usage

###Authentication
**1)**  Add below code in your activity onCreate

    String BASE_URL = "http://example.com/api/v1"
    Authentication.start(this, BASE_URL);

**Important points to note :** here the library will send the entered mobile number to the server in below url format. BASE_URL is appended with "/login_create".

    http://example.com/api/v1/login_create?phone_no=9711223344

You have to implement server side to receive the phone_no number, generate an OTP save it temporarily, for validation, and return the ***pass_key*** in json format

**example:** 

    { "pass_key": "Dear Member, your access token"}  

the library will scan all the incoming new sms in the user mobile and checks if the message starts with the pass_key. if a match is found then **last 6 characters** are taken as the OTP. Hence make sure that the message starts with the "pass_key" and ends (*last 6 characters*) with OTP. Message template should look something like this.

*Dear Member, your access token for something something is: 123456*

**2)** After retrieving the OTP library sends it back to the server in below url format

    http://example.com/api/v1/validate_otp?otp=123456&phone_no=9711223344

return json format should be

    {"success": true //Boolean
     "auth_token": "Akjfs_HaLaQsjbdb" //String}

**3)** Add below code in your activity to fetch the auth_token

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String authToken = Authentication.getAuthResult(requestCode, resultCode, data);
        if (authToken != null) {
            //some code..
        }else{
            Toast.makeText(YourActivity.this,"authentication failure",Toast.LENGTH_LONG).show();
        }
    }

###GCM Registration
**1)** Add the below code into you activity

    GCMRegisterUtils.registerGCM(YourActivity.this, GOOGLE_PROJECT_ID, BASE_URL, authToken);

* *GOOGLE_PROJECT_ID* = the ID you recieved after registering in google console. check the link * https://developer.android.com/google/gcm/client.html

* *BASE_URL* = your url where the registration id will be send. The library will append it with "/receive". example:

*     http://example.com/api/v1/register?regId=QyThuU-somelongstring-&auth_token=Akjfs_HaLaQsjbdb
* *authToken* = the user authentication token you received after login


###GCM Notification

**1)** Create a class which extends application and implements GCMApp.

	public class MyApplication extends Application implements GCMApp {

	    @Override
	    public void onCreate() {
	        super.onCreate();
	    }

	    @Override
	    public void onGCMMessage(Bundle extras) {
                 //some code with the extras received
	    }

	    @Override
	    public void styleNotification(NotificationCompat.Builder mBuilder,Bundle extras) {
                 //default notification style will be used when left empty
	    }
	}

**styleNotification**(NotificationCompat.Builder mBuilder,Bundle extras) = the intent will be directly passed to the notification intent service. But defaut notification style can be changed using the Builder. There is two types of default notification. 

*a)* start activity on click of the notification. for this set "type" = "NTN" and "activity" = "com.example.your_activity_name". RoR example below

     options = {data: {type: "NTN", title: "YourAppName", activity: "com.example.your_activity", message: "xyz"}}


*b)* redirect to a url on click of notification. for this set "type" = "NTN" and "url" = "http://your_url.com". make sure that "activity" is not set. RoR example below

    options = {data: {type: "NTN", app_name: "YourAppName", url: "http://your_url.com", message: "xyz"}}
***
**onGCMMessage(Bundle extras)** = If not "type" is not "NTN" then extras will be sent to the this methode where user can choose to show notification or update database or what ever.
