
# 

# **FawryPay-AVL android SDK**

Accept popular payment methods with a single client-side implementation.

## **Before You Start**

Use this integration if you want your Android application to:

-   Accept cards and other payment methods.
-   Save and display cards for reuse.

Make sure you have an active FawryPay account, or [**create an account**](https://atfawry.fawrystaging.com/merchant/register).


### **How Android SDK Looks Like**
<img width="300" alt="shared image (4)" src="https://github.com/user-attachments/assets/c683ae4e-0b47-4ab4-865e-9a3dd2cf7a5c" />

[**Download**](https://drive.google.com/drive/folders/1T__IkSKiM21zHcpeGfKF0xTQNCE6YtKk) and test our sample application.
------------------------------------------------------------------------
### **How it works**
<img src="https://github.com/FawryPay/Android-Fawrypay-AVL-sample/blob/master/Docs/3.png" width="900"/>


On this page, we will walk you through the Android SDK integration steps:

1.  InstallingFawryPaySDK.
2.  Initialize and Configure FawryPayAndroidSDK.
3.  Override the SDK colors.
4.   Return payment processing information and inform your client of the payment result.

## **Step 1: Installing FawryPaySDK**
This document illustrates how our gateway can be integrated into your Android application in simple and easy steps. Please follow the steps in order to integrate the FawryPay Android SDK in your application.


1.  Add the following repository to your (root) `build.gradle`
<!-- -->

    repositories { ...
    jcenter()
    maven { url 'https://nexusmobile.fawrystaging.com:2597/repository/maven-public/' } 
    }
2. And add the following to your (app) `build.gradle`
<!-- -->

    dependencies {
    ...
    implementation 'com.fawry.fawrypay:avl:2.0.1' 
    }

3. Add the following to your `Manifest.xml`
<!-- -->

    <application
    ...
    tools:replace="android:allowBackup" />

4. Finally add the following property to your `build.properties`
<!-- -->

    android.enableJetifier=true

## **Step 2: Initialize AVL FLOW**
1. Create an instance of
    - LaunchCustomerModel
    - LaunchMerchantModel
    - FawryLaunchModel

and pass the required parameters (Required and optional parameters are determined below).
<img width="876" height="616" alt="image" src="https://github.com/user-attachments/assets/e7a43d87-650d-4d6d-b1e7-bd5c41f053cc" />


LaunchCustomerModel
| **PARAMETER**     | **TYPE** | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                        |
|---------------|---------------|---------------|---------------|---------------|
| customerName      | String   | optional     | \-                                              | Name Name                                          |
| customerEmail     | String   | optional     | \-                                              | [email\@email.com](mailto:email@email.com)         |
| customerMobile    | String   | optional     | \-                                              | +0100000000                                        |
| customerProfileId | String   | optional (required in case of using card tokenization)    | \-                                              | 1234                                               |

AVLInfo
| **PARAMETER**     | **TYPE** | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                          |
|---------------|---------------|---------------|---------------|---------------|
| offUsBTC| Int      | mandatory    | \-                                                | 4433                                              |
| onUsBTC| Int      | mandatory    | \-                                                | 3344                                              |
| internationalBANs| ArrayList [String] | mandatory    |BANs related to the bank to use the onUsBTC| arrayListOf("51234 5","5506900          |                                                |
| onUsAvlFees      | Double (should be maximum 2 digits after the decimal point, if the value is more than 2 digits after the decimal point it will be trimmed to 2 digits after the decimal point)  | mandatory    | \-                                                | 5.0                                               |
| offUsAvlFees     | Double (should be maximum 2 digits after the decimal point, if the value is more than 2 digits after the decimal point it will be trimmed to 2 digits after the decimal point)  | mandatory    | \-                                                | 7.0                                               |
| minValue | Double   | optional | \-                                                |                                              |
| maxValue | Double | optional | \-                                                |
avlValue          | Double (should be maximum 2 digits after the decimal point, if the value is more than 2 digits after the decimal point it will be trimmed to 2 digits after the decimal point)                   | mandatory    | \-                                               | 7.00                            |
beneficiaryWalletNumber| String                   | mandatory    | \-                                              | “01234567890”                   |
|  billingAcct        | String                   | mandatory    | \-                                               | “01234567890”                   | 
 beneficiaryName| String                   | optional | \-                                               | “test”                   |
|  avlAmountDataType| AVLAmountDataType| optional | \It specifies which data type should be accepted in the avl amount                                               | AVLAmountDataType.DOUBLE                  |
|  shouldShowBeneficiaryName| Boolean| optional | \-                                               | true                   |
|  shouldShowReasonOfTransfer| Boolean| optional | \-                                               | true                   |
|  reasonOfTransfer| String                   | optional | \-                                               | “deposit to wallet”                   |
|  screenTitle| String                   | optional | \you can use this attribute to change the screen title of avl flow                                               | “AVL flow”                   |
|  avlExtraProperties| AVLExtraProperties| optional | \-                                               | \-                   |
| 
AVLExtraProperties
| **PARAMETER**  | **TYPE** | **REQUIRED** | **DESCRIPTION**                                                                                                                                                                | **EXAMPLE** 
 |---------------|---------------|---------------|---------------|---------------|
| firstName| String   | optional | \-     | \- |
| middleName| String   | optional | \-                                                                                                                            | \-              |                       |
| lastName| String   | optional | \-     | \- |
| street| String   | optional | \-     | \- |
| street2| String   | optional | \-                                                                                                                            | \-              |                       |
| city| String   | optional | \-     | \- |
| postCodeZip| String   | optional | \-     | \- |
| stateProvinceCode| String   | optional | \-                                                                                                                            | \-              |                       |
| country| String   | optional | \-     | \- |
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
| avlInfo            | AVLInfo                  | mandatory    | \-                                               | \-                   |
| apiPath            | String                   | optional     | \-                                               | "fawrypay-a pi/api/"                     |
| authCaptureMode| Boolean                                     | optional     | \-                                               | false                                          |
| payWithCardToken| Boolean                  | optional     | \-                                               | false                     |


2.  Calling Mode:
     -  Payment Mode: Call launchAVL from LaunchFawrySdk.launchAVL

| **PARAMETER**     | **TYPE** | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                        |
|---------------|---------------|---------------|---------------|---------------|
| activity          | Activity | required     | The activity which will be the starting point of the SDK.| \-                              |
| _fawryLaunch Model| FawryLaunchModel| required     | Has info that needed to launch the SDK| Example in step 3             |
| _baseUrl          | String   | required     | Provided by the support team. Use staging URL for testing and switch for production to go live.| (https://atfawry.fawrystaging.com) (staging)        (https://atfawry.com) (production)|
| _languages        | Languages| required     | SDK language which will affect SDK's interface languages.|Languages.ENGLISH|
| _callback| AVLCallbacks| required     | callbacks to receive the responses for the payment transaction |\-|

## **Step 3: Initialize card manager flow**
1. Create an instance of
    - LaunchCustomerModel
    - LaunchMerchantModel
    - FawryLaunchModel

LaunchCustomerModel
| **PARAMETER**     | **TYPE** | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                        |
|---------------|---------------|---------------|---------------|---------------|
| customerName      | String   | optional     | \-                                              | Name Name                                          |
| customerEmail     | String   | optional     | \-                                              | [email\@email.com](mailto:email@email.com)         |
| customerMobile    | String   | optional     | \-                                              | +0100000000                                        |
| customerProfileId | String   | required | \-                                              | 1234                                               |


LaunchMerchantModel
| **PARAMETER**  | **TYPE** | **REQUIRED** | **DESCRIPTION**                                                                                                                                                                | **EXAMPLE**                         |
|---------------|---------------|---------------|---------------|---------------|
| merchantCode   | String   | required     | Merchant ID provided during FawryPay account setup.                                                                                                                            | provided merchantCode               |
| secretCode     | String   | required     | provided by support     | provided merchant secret key |


FawryLaunchModel
| **PARAMETER**     |          **TYPE**        | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                         |
|-------------------|--------------------------|--------------|---------------|---------------|
| launchCustomerModel| LaunchCustomerModel      | optional     | Customer information.                             | \-                              |
| launchMerchantModel| LaunchMerchantModel      | required     | Merchant information.                             | \-                              |




2.  Calling Mode:
     -  Payment Mode: Call launchCardManagerFlow from LaunchFawrySdk.launchCardManagerFlow

| **PARAMETER**     | **TYPE** | **REQUIRED** | **DESCRIPTION**                                 | **EXAMPLE**                                        |
|---------------|---------------|---------------|---------------|---------------|
| activity          | Activity | required     | The activity which will be the starting point of the SDK.| \-                              |
| _fawryLaunch Model| FawryLaunchModel| required     | Has info that needed to launch the SDK| Example in step 3             |
| _baseUrl          | String   | required     | Provided by the support team. Use staging URL for testing and switch for production to go live.| (https://atfawry.fawrystaging.com) (staging)        (https://atfawry.com) (production)|
| _languages        | Languages| required     | SDK language which will affect SDK's interface languages.|Languages.ENGLISH|
| _callback| CardManagerCallbacks| required     | callbacks to receive the responses from the flow |\-|



## **Step 4: Override the SDK colors**
If you want to change colors: -
You need to know the ID of the color you want to change then add a color in your colors file in the host app with the same id but with the value you want
<img width="300" alt="AVL colors" src="https://github.com/user-attachments/assets/352ea784-d3e8-4a14-ad09-706b2d5dd896" />



If you changed these colors it will change the main screen colors.
For example:
<!-- -->
    <color name="fawrypay_screen_header_text_color">#053F5C</color> 
    <color name="fawrypay_amount_card_background_color">#EFF7FA</color>
    <color name="fawrypay_labels_color">#003247</color>
    <color name="fawrypay_screen_background_color">#FFFFFF</color>
    <color name="fawrypay_dimmed_button_color">#DDDFDF</color>
    <color name="fawrypay_enabled_button_color">#016891</color>

and for logo you can add a png file in the drawable package and name it fawrypay_logo.png and for fonts you can add fonts in your package with the names:
<!-- -->
      fawrypay_cairo_semi_bold
      fawrypay_fawry_pro_bold
      fawrypay_cairo_bold
      fawrypay_fawry_pro_bold



## **Step 5: Callbacks Explanation:**
  There are 5 callbacks:
  1. **onSuccess(  
    paymentStatus: PaymentStatus,  
    data: CreatePayRefNoResponse?  
) { }**
        -    called after the ending of success payment flow
  2. **onPaymentCompleted(  
    paymentStatus: PaymentStatus,  
    data: CreatePayRefNoResponse?,  
    error: FawryPayError?  
) {}**
       -    called after receiving the payment response either success or failure

  3. **onFailure(error: FawryPayError) {}**    
        -   called after the ending of failure payment flow

  4. **onBackClicked() {}**
       -    called when the user press back to close flow
  5. **onCancelClicked() {}**
        -   called when user cancel the flow




