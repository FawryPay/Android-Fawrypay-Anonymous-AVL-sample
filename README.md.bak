

# 

# **FawryPay-AVL android SDK**

Accept popular payment methods with a single client-side implementation.

## **Before You Start**

Use this integration if you want your Android application to:

-   Accept cards and other payment methods.
-   Save and display cards for reuse.

Make sure you have an active FawryPay account, or [**create an account**](https://atfawry.fawrystaging.com/merchant/register).


### **How Android SDK Looks Like**
<img src="https://github.com/FawryPay/Android-Fawrypay-AVL-sample/blob/master/Docs/1.png" width="300"/>    <img src="https://github.com/FawryPay/Android-Fawrypay-AVL-sample/blob/master/Docs/2.png" width="300"/>

[**Download**](https://drive.google.com/drive/folders/1T__IkSKiM21zHcpeGfKF0xTQNCE6YtKk) and test our sample application.
------------------------------------------------------------------------
### **How it works**
<img src="https://github.com/FawryPay/Android-Fawrypay-AVL-sample/blob/master/Docs/3.png" width="900"/>


On this page, we will walk you through the Android SDK integration steps:

1.  InstallingFawryPaySDK.
2.  InitializeandConfigureFawryPayAndroidSDK.
3.  Override the SDK colors.
4.   Return payment processing information and inform your client of the payment result.

## **Step 1: Installing FawryPaySDK**
This document illustrates how our gateway can be integrated into your Android application in simple and easy steps. Please follow the steps in order to integrate the FawryPay Android SDK in your application.


1.  Add the following repository to your (root) `build.gradle`
<!-- -->

    repositories { ...
    jcenter()
    maven { url 'https://nexus.mobile.fawry.io/repository/maven-public/' } 
    }
2. And add the following to your (app) `build.gradle`
<!-- -->

    dependencies {
    ...
    implementation 'com.fawry.fawrypay:avl:0.1.4' 
    }

3. Add the following to your `Manifest.xml`
<!-- -->

    <application
    ...
    tools:replace="android:allowBackup" />

4. Finally add the following property to your `build.properties`
<!-- -->

    android.enableJetifier=true

## **Step 2: Initialize AVL Android SDK**
1. Create an instance of
    - LaunchCustomerModel
    - LaunchMerchantModel
    - FawryLaunchModel

and pass the required parameters (Required and optional parameters are determined below).
![](https://github.com/FawryPay/Android-Fawrypay-AVL-sample/blob/master/Docs/4.png)


LaunchCustomerModel
| **PARAMETER**     | **TYPE** | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                        |
|---------------|---------------|---------------|---------------|---------------|
| customerName      | String   | optional     | \-                                              | Name Name                                          |
| customerEmail     | String   | optional     | \-                                              | [email\@email.com](mailto:email@email.com)         |
| customerMobile    | String   | optional     | \-                                              | +0100000000                                        |
| customerProfileId | String   | optional     | \-                                              | 1234                                               |

AVLInfo
| **PARAMETER**     | **TYPE** | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                          |
|---------------|---------------|---------------|---------------|---------------|
| billTypeCodeWith Fees| Int      | mandatory    | \-                                                | 4433                                              |
| billTypeCodeWith outFees| Int      | mandatory    | \-                                                | 3344                                              |
| internationalBANs| ArrayList [String] | mandatory    |BANs related to the bank to use the billTypeCodeWithoutFees| arrayListOf("51234 5","5506900          |
| BANValidationSize| Int      | mandatory    | \-                                                | 6                                                 |
| onUsAvlFees      | Double (should be maximum 2 digits after the decimal point, if the value is more than 2 digits after the decimal point it will be trimmed to 2 digits after the decimal point)  | mandatory    | \-                                                | 5.0                                               |
| offUsAvlFees     | Double (should be maximum 2 digits after the decimal point, if the value is more than 2 digits after the decimal point it will be trimmed to 2 digits after the decimal point)  | mandatory    | \-                                                | 7.0                                               |
| minValue | Double   | optional | \-                                                |                                              |
| maxValue | Double | optional | \-                                                |    
LaunchMerchantModel
| **PARAMETER**  | **TYPE** | **REQUIRED** | **DESCRIPTION**                                                                                                                                                                | **EXAMPLE**                         |
|---------------|---------------|---------------|---------------|---------------|
| merchantCode   | String   | required     | Merchant ID provided during FawryPay account setup.                                                                                                                            | +/IPO2sghiethhN6tMC==               |                       |
| secretCode     | String   | required     | provided by support     | 4b8jw3j2-8gjhfrc-4wc4-scde-453dek3d |


FawryLaunchModel
| **PARAMETER**     |          **TYPE**        | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                         |
|-------------------|--------------------------|--------------|---------------|---------------|
| launchCustomerModel| LaunchCustomerModel      | optional     | Customer information.                             | \-                              |
| launchMerchantModel| LaunchMerchantModel      | required     | Merchant information.                             | \-                              |
| allow3DPayment     | Boolean                  | optional - default value = false| to allow 3D secure payment make it “true”| \-                              |
| skipReceipt        | Boolean                  | optional - default value = false| to skip receipt after payment trial| \-                              |
| skipLogin          | Boolean                  |optional - default value = false| to skip login screen in which we take email and mobile| \-                              |
| beneficiaryWalletNumber| String                   | mandatory    | \-                                              | “01234567890”                   |
| avlValue          | Double (should be maximum 2 digits after the decimal point, if the value is more than 2 digits after the decimal point it will be trimmed to 2 digits after the decimal point)                   | mandatory    | \-                                               | 7.00                            |
| billingAcct        | String                   | mandatory    | \-                                               | “01234567890”                   |
| avlInfo            | AVLInfo                  | mandatory    | \-                                               | “01234567890”                   |
| apiPath            | String                   | optional     | \-                                               | "fawrypay-a pi/api/"            |
 screenTitle | String                   | optional     | \-                                               | -           |
 showZeroFeesView | Boolean                   | mandatory - default value = false     | \-                                               | -           |


2.  Calling Mode:
     -  Payment Mode: Call launchAVL from CalllaunchAVLfromFawrySdk.launchAVL

| **PARAMETER**     | **TYPE** | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                        |
|---------------|---------------|---------------|---------------|---------------|
| activity          | Activity | required     | The activity which will be the starting point of the SDK.| \-                              |
| _fawryLaunch Model| FawryLaunchModel| required     | Has info that needed to launch the SDK| Example in step 3             |
| _baseUrl          | String   | required     | Provided by the support team. Use staging URL for testing and switch for production to go live.| (https://atfawry.fawrystaging.com) (staging)        (https://atfawry.com) (production)|
| _languages        | String   | required     | SDK language which will affect SDK's interface languages.|FawrySdk.Langua ges.ENGLISH|

## **Step 3: Initialize screenless credit card payment**
1. Create an instance of
    - LaunchCustomerModel
    - LaunchMerchantModel
    - FawryLaunchModel
    - CardDetailsModel

LaunchCustomerModel
| **PARAMETER**     | **TYPE** | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                        |
|---------------|---------------|---------------|---------------|---------------|
| customerName      | String   | optional     | \-                                              | Name Name                                          |
| customerEmail     | String   | optional     | \-                                              | [email\@email.com](mailto:email@email.com)         |
| customerMobile    | String   | optional     | \-                                              | +0100000000                                        |
| customerProfileId | String   | optional     | \-                                              | 1234                                               |


LaunchMerchantModel
| **PARAMETER**  | **TYPE** | **REQUIRED** | **DESCRIPTION**                                                                                                                                                                | **EXAMPLE**                         |
|---------------|---------------|---------------|---------------|---------------|
| merchantCode   | String   | required     | Merchant ID provided during FawryPay account setup.                                                                                                                            | provided merchantCode               |
| merchantRefNum | String   | required     | Merchant's transaction reference number is random 10 alphanumeric digits. You can call FrameworkHelper.shared?.getMerchantReferenceNumber() to generate it rather than pass it. | A1YU7MKI09                          |
| secretCode     | String   | required     | provided by support     | provided merchant secret key |


FawryLaunchModel
| **PARAMETER**     |          **TYPE**        | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                         |
|-------------------|--------------------------|--------------|---------------|---------------|
| launchCustomerModel| LaunchCustomerModel      | optional     | Customer information.                             | \-                              |
| launchMerchantModel| LaunchMerchantModel      | required     | Merchant information.                             | \-                              |
| allow3DPayment     | Boolean                  | optional - default value = false| to allow 3D secure payment make it “true”| \-                              |
| skipReceipt        | Boolean                  | optional - default value = false| to skip receipt after payment trial| \-                              |
| skipLogin          | Boolean                  |optional - default value = false| to skip login screen in which we take email and mobile| \-                              |               |            |
| apiPath            | String                   | optional     | \-                                               | "fawrypay-api/api/"            |\-                              |
| authCaptureMode| Boolean                  |optional - default value = false| -|
| chargeItems| ArrayList< PayableItem > |required | -|



CardDetailsModel
| **PARAMETER**     |          **TYPE**        | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                         |
|-------------------|--------------------------|--------------|---------------|---------------|
| cardNumber| String | required     | 16 digits card number                             | \-                              |
| cardHolderName| String | required     | name on the card                             | \-                              |
| cardExpiryYear| String |required     | card expiry year| \-                              |
| cardExpiryMonth| String | required     | card expiry month| \-                              |                            |               |            |
| cvv| String                   | required     |3 digits on the back of the card|-


## **Step 4: Override the SDK colors**
If you want to change colors: -
You need to know the ID of the color you want to change then add a color in your colors file in the host app with the same id but with the value you want


<img src="https://github.com/FawryPay/Android-Fawrypay-AVL-sample/blob/master/Docs/5.png" width="300"/>      <img src="https://github.com/FawryPay/Android-Fawrypay-AVL-sample/blob/master/Docs/6.png" width="300"/>


If you changed these colors it will change the main screen color in the payment fragment I made it black and red as an example in the host app, but you can change it to whatever you want.
For example:
<!-- -->
    <color name="fawry_blue">#FF000000</color> 
    <color name="fawry_yellow">#F44336</color> 
    <color name="fawry_green">#FFEB3B</color> 
    <color name="dim_text_color">#FFFFFFFF</color> 
    <color name="fawry_black">#FFFFFFFF</color> 
    <color name="fawry_white">#FFFFFFFF</color>
    <color name="fawry_background">#809199</color>

and for logo you can add a png file in the drawable package and name it fawry_pay_logo.png and for fonts you can add fonts in your package with the names:
<!-- -->
      fawry_bold_font
      fawry_regular_font

<img src="https://github.com/FawryPay/Android-Fawrypay-AVL-sample/blob/master/Docs/7.png" width="300"/> 


## **Step 5: Callbacks Explanation:**
  There are 5 callbacks:
  1. **onInit() { }**
        -    called before launching the flow successfully.
  2. **onPreLaunch(onPreLaunch: FawryPreLaunch) { onPreLaunch.onContinue() }**
       -    called when the flow is launched.

  3. **onFailure(error: String, closeAction: CloseAction?) { }**    
        -   if the payment didn't pass.
        -   if you enabled the receipt, this callback will be called after clicking the done button in the receipt.
        -   if you disabled the receipt, this callback will be called upon the finish of the payment screen and the failure of the payment.
        -   closeAction is an optional enum param which has 2 values BACK, CANCEL , and it is used to specify which button is clicked in the payment screen to close the screen.

  4. **onPaymentCompleted(msg:String,data:Any?){}**
       -    will be called only whether the payment passed or not. And it's called upon receiving the response of the payment either success or fail.
  5. **onSuccess(msg:String,data:Any?){}**
        -   if the payment passed.
        -   if you enabled the receipt, this callback will be called after clicking the done button in the receipt.
        -   if you disabled the receipt, this callback will be called upon the finish of the payment screen and the success of the payment.




