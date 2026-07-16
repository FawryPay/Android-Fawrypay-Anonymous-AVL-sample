# FawryPay Anonymous + AVL Android SDK

Accept popular payment methods with a single client-side implementation. This SDK combines **Anonymous Payment** and **AVL** flows in one package.

## Before You Start

Use this integration if you want your Android application to:

- Accept cards and other payment methods
- Save and display cards for reuse
- Run AVL (Account Value Load) transfers
- Manage saved cards through the Card Manager flow

Make sure you have an active FawryPay account, or [create an account](https://atfawry.fawrystaging.com/merchant/register).

### Anonymous Flow Preview

<img src="https://github.com/user-attachments/assets/c42a82ae-335e-497a-9b8b-094a6414ac85" width="250" height="400"/>
<img src="https://github.com/user-attachments/assets/d26fc52a-f063-4d4c-898b-e581778a86f2" width="250" height="400"/>
<img src="https://github.com/user-attachments/assets/0c719905-630b-4ce1-a25a-a5639d12c1fb" width="250" height="400"/>

### AVL Flow Preview

<img width="300" alt="AVL flow preview" src="https://github.com/user-attachments/assets/c683ae4e-0b47-4ab4-865e-9a3dd2cf7a5c" />

[Download](https://github.com/FawryPay/Android-Fawrypay-Anonymous-AVL-sample) and test our sample application.

### How It Works

<img src="https://github.com/FawryPay/Android-Fawrypay-Anonymous-sample/blob/master/Docs/4.jpg" width="900"/>

This guide walks you through the Android SDK integration steps:

1. Installing FawryPay SDK
2. Initialize and launch the **Anonymous** payment flow
3. Initialize and launch the **AVL** flow
4. Initialize and launch the **Card Manager** flow
5. Override the SDK colors, logo, and fonts
6. Handle payment callbacks and inform your client of the result

---

## Step 1: Installing FawryPay SDK

Follow these steps in order to integrate the FawryPay Android SDK into your application.

1. Add the following repository to your root `settings.gradle` (or root `build.gradle` repositories block):

```groovy
repositories {
    ...
    jcenter()
    maven { url 'https://nexusmobile.fawrystaging.com:2597/repository/maven-public/' }
}
```

2. Add the following dependency to your app `build.gradle`:

```groovy
dependencies {
    ...
    implementation 'com.fawry.fawrypay:anonymous-avl:2.0.0'
}
```

3. Add the following to your `AndroidManifest.xml`:

```xml
<application
    ...
    tools:replace="android:allowBackup" />
```

4. Finally, add the following property to your `gradle.properties`:

```properties
android.enableJetifier=true
```

---

## Step 2: Initialize Anonymous Payment Flow

1. Create instances of:

- `LaunchCustomerModel`
- `LaunchMerchantModel`
- `ChargeItemsParamsModel` / `BillItems`
- `FawryLaunchModel`

and pass the required parameters (required and optional parameters are listed below).

<img width="338" height="363" src="https://github.com/user-attachments/assets/61236adb-2cbf-4426-b5bb-06cca2ce4338" />

### LaunchCustomerModel

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| customerName | String | optional | - | Name Name |
| customerEmail | String | optional | - | email@email.com |
| customerMobile | String | optional | - | +0100000000 |
| customerProfileId | String | optional | Mandatory when paying with saved cards | 1234 |

### LaunchMerchantModel

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| merchantCode | String | required | Merchant ID provided during FawryPay account setup | +/IPO2sghiethhN6tMC== |
| merchantRefNum | String | required | Merchant transaction reference number (random 10 alphanumeric digits) | A1YU7MKI09 |
| secretCode | String | required | Provided by support | 4b8jw3j2-8gjhfrc-4wc4-scde-453dek3d |

### ChargeItemsParamsModel (`ArrayList<BillItems>`)

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| itemId | String | required | - | 3w8io |
| description | String | optional | - | This is description |
| price | String | required | - | 200.00 |
| quantity | String | required | - | 1 |
| chargeItemAccounts | ArrayList\<ChargeItemAccount> | optional | Used in split payment | - |

### ChargeItemAccount

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| accountCode | String | required | - | - |
| amount | Double | required | - | - |

### FawryLaunchModel (Anonymous)

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| launchCustomerModel | LaunchCustomerModel | optional | Customer information | - |
| launchMerchantModel | LaunchMerchantModel | required | Merchant information | - |
| chargeItems | ArrayList\<BillItems> | required | Array of items the user will buy; must be of type `BillItems` / `ChargeItemsParamsModel` | - |
| paymentSignature | String | optional | Create your own signature by concatenating the following elements in the same order and hashing the result using **SHA-256**: `merchantCode + merchantRefNum + customerProfileId` (if exists, otherwise insert `""`) `+ itemId + quantity + Price` (in two-decimal format like `10.00`) `+ Secure hash key`. If the order contains multiple items, the list will be **sorted** by `itemId` and concatenated one by one, for example: `itemId1 + Item1quantity + Item1price + itemId2 + Item2quantity + Item2price` | - |
| tokenizationSignature | String | optional | Create your own signature by concatenating the following elements in the same order and hashing the result using **SHA-256**: `merchantCode + customerProfileId` (if exists, otherwise insert `""`) `+ Secure hash key` | - |
| allowVoucher | Boolean | optional — default `false` | `true` if your account supports voucher code | - |
| payWithCardToken | Boolean | required | If `true`, the user pays with a card token (one of the saved cards, or adds a new card to be saved). If `false`, the user pays with card details without saving | - |
| allow3DPayment | Boolean | optional — default `false` | Set to `true` to allow 3D Secure payment | - |
| skipReceipt | Boolean | optional — default `false` | Skip the receipt screen after payment | - |
| authCaptureMode | Boolean | optional — default `false` | Depends on refund configuration: `true` when refund is enabled, `false` when refund is disabled | false |
| paymentMethods | PaymentMethods | optional — default `PaymentMethods.ALL` | Show only a specific payment method if needed | `.ALL` / `.PAY_AT_FAWRY` / `.CARD` / `.WALLET` |
| enableTokenization | Boolean | optional — default `false` | Enable card tokenization behavior | - |

**Notes:**

- You can pass either a signature or a secure key (in which case the SDK creates the signature internally). If both parameters are passed, the secure key is ignored and the signature is used.

### Calling Mode — Anonymous Payment

Call `LaunchFawrySdk.launchAnonymousSDK`:

<img width="704" height="732" alt="launchAnonymousSDK example" src="https://github.com/user-attachments/assets/c55e0533-514c-4644-98b0-fd3ae59720df" />

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| activity | Activity | required | The activity that will be the starting point of the SDK | - |
| \_fawryLaunchModel | FawryLaunchModel | required | Info required to launch the SDK | See models above |
| \_baseUrl | String | required | Provided by the support team. Use the staging URL for testing and switch to production to go live | `https://atfawry.fawrystaging.com` (staging)<br/>`https://atfawry.com` (production) |
| \_languages | Languages | required | SDK language; affects the SDK interface language | `Languages.ENGLISH` |
| \_callback | FawrySdkCallbacks | required | Callbacks to receive payment transaction responses | - |

### Anonymous Payment Flows

There are two payment flows:

1. **Payment with card details** — the SDK collects card number, CVV, and expiry date on the payment screen, then handles the payment.
2. **Payment with card token** — two screens: a card manager screen (add, delete, retrieve cards linked to `customerProfileId`), then the normal payment screen.

Choose the flow using the `payWithCardToken` flag in `FawryLaunchModel`:

- If `payWithCardToken` is `true`, you must pass `customerProfileId` so the SDK can load that customer's saved cards.
- If the profile has no saved cards and the user chooses to pay with a credit card, the SDK shows a card-details bottom sheet with an option to save the card or pay without saving.
- To manage cards (add, delete, or review saved cards) without opening the payment screen, use `launchCardManagerFlow` as described in [Step 4](#step-4-initialize-card-manager-flow).

---

## Step 3: Initialize AVL Flow

1. Create instances of:

- `LaunchCustomerModel`
- `LaunchMerchantModel`
- `AVLInfo`
- `FawryLaunchModel`

and pass the required parameters (required and optional parameters are listed below).

<img width="876" height="616" alt="AVL launch example" src="https://github.com/user-attachments/assets/e7a43d87-650d-4d6d-b1e7-bd5c41f053cc" />

### LaunchCustomerModel

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| customerName | String | optional | - | Name Name |
| customerEmail | String | optional | - | email@email.com |
| customerMobile | String | optional | - | +0100000000 |
| customerProfileId | String | optional | Required when using card tokenization | 1234 |

### AVLInfo

> **Note:** For `Double` fields (`onUsAvlFees`, `offUsAvlFees`, `avlValue`), use a maximum of 2 digits after the decimal point. Values with more than 2 decimal places are trimmed to 2 digits.

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| offUsBTC | Int | mandatory | - | 4433 |
| onUsBTC | Int | mandatory | - | 3344 |
| internationalBANs | ArrayList\<String> | mandatory | BANs related to the bank to use `onUsBTC` | `arrayListOf("512345", "5506900")` |
| onUsAvlFees | Double | mandatory | Max 2 decimal places | 5.0 |
| offUsAvlFees | Double | mandatory | Max 2 decimal places | 7.0 |
| minValue | Double | optional | - | - |
| maxValue | Double | optional | - | - |
| avlValue | Double | mandatory | Max 2 decimal places | 7.00 |
| beneficiaryWalletNumber | String | mandatory | - | `"01234567890"` |
| billingAcct | String | mandatory | - | `"01234567890"` |
| beneficiaryName | String | optional | - | `"test"` |
| avlAmountDataType | AVLAmountDataType | optional | Specifies which data type should be accepted for the AVL amount | `AVLAmountDataType.DOUBLE` |
| shouldShowBeneficiaryName | Boolean | optional | - | true |
| shouldShowReasonOfTransfer | Boolean | optional | - | true |
| reasonOfTransfer | String | optional | - | `"deposit to wallet"` |
| screenTitle | String | optional | Change the screen title of the AVL flow | `"AVL flow"` |
| avlExtraProperties | AVLExtraProperties | optional | - | - |

### AVLExtraProperties

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| firstName | String | optional | - | - |
| middleName | String | optional | - | - |
| lastName | String | optional | - | - |
| street | String | optional | - | - |
| street2 | String | optional | - | - |
| city | String | optional | - | - |
| postCodeZip | String | optional | - | - |
| stateProvinceCode | String | optional | - | - |
| country | String | optional | - | - |

### LaunchMerchantModel

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| merchantCode | String | required | Merchant ID provided during FawryPay account setup | +/IPO2sghiethhN6tMC== |
| secretCode | String | required | Provided by support | 4b8jw3j2-8gjhfrc-4wc4-scde-453dek3d |

### FawryLaunchModel (AVL)

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| launchCustomerModel | LaunchCustomerModel | optional | Customer information | - |
| launchMerchantModel | LaunchMerchantModel | required | Merchant information | - |
| allow3DPayment | Boolean | optional — default `false` | Set to `true` to allow 3D Secure payment | - |
| skipReceipt | Boolean | optional — default `false` | Skip the receipt after payment | - |
| avlInfo | AVLInfo | mandatory | AVL configuration | - |
| apiPath | String | optional | - | `"fawrypay-api/api/"` |
| authCaptureMode | Boolean | optional | - | false |
| payWithCardToken | Boolean | optional | - | false |

### Calling Mode — AVL Payment

Call `LaunchFawrySdk.launchAVL`:

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| activity | Activity | required | The activity that will be the starting point of the SDK | - |
| \_fawryLaunchModel | FawryLaunchModel | required | Info required to launch the SDK | See models above |
| \_baseUrl | String | required | Provided by the support team. Use the staging URL for testing and switch to production to go live | `https://atfawry.fawrystaging.com` (staging)<br/>`https://atfawry.com` (production) |
| \_languages | Languages | required | SDK language; affects the SDK interface language | `Languages.ENGLISH` |
| \_callback | AVLCallbacks | required | Callbacks to receive payment transaction responses | - |

---

## Step 4: Initialize Card Manager Flow

Use this flow to add, delete, or review saved cards without opening a payment screen.

1. Create instances of:

- `LaunchCustomerModel`
- `LaunchMerchantModel`
- `FawryLaunchModel`

### LaunchCustomerModel

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| customerName | String | optional | - | Name Name |
| customerEmail | String | optional | - | email@email.com |
| customerMobile | String | optional | - | +0100000000 |
| customerProfileId | String | required | Profile ID used to load/manage saved cards | 1234 |

### LaunchMerchantModel

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| merchantCode | String | required | Merchant ID provided during FawryPay account setup | provided merchantCode |
| secretCode | String | required | Provided by support | provided merchant secret key |

### FawryLaunchModel (Card Manager)

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| launchCustomerModel | LaunchCustomerModel | optional | Customer information | - |
| launchMerchantModel | LaunchMerchantModel | required | Merchant information | - |

### Calling Mode — Card Manager

Call `LaunchFawrySdk.launchCardManagerFlow`:

<img width="940" height="695" alt="launchCardManagerFlow example" src="https://github.com/user-attachments/assets/fb3dd298-8ae4-4756-a874-cee016788b4b" />

| PARAMETER | TYPE | REQUIRED | DESCRIPTION | EXAMPLE |
|---|---|---|---|---|
| activity | Activity | required | The activity that will be the starting point of the SDK | - |
| \_fawryLaunchModel | FawryLaunchModel | required | Info required to launch the SDK | See models above |
| \_baseUrl | String | required | Provided by the support team. Use the staging URL for testing and switch to production to go live | `https://atfawry.fawrystaging.com` (staging)<br/>`https://atfawry.com` (production) |
| \_languages | Languages | required | SDK language; affects the SDK interface language | `Languages.ENGLISH` |
| \_callback | CardManagerCallbacks | required | Callbacks to receive responses from the flow | - |

---

## Step 5: Override the SDK Colors

To change SDK colors, use the color resource ID you want to override, then define a color with the **same ID** in your host app `colors.xml` with your preferred value.

### Anonymous / Payment Screen Colors

<img width="300" height="500" alt="Anonymous colors" src="https://github.com/user-attachments/assets/88980065-3329-4713-a932-db9d69bb0cdb" />

Changing these colors updates the main screen in the payment fragment. Main color IDs:

- `fawrypay_white`
- `fawrypay_enabled_button_color`
- `fawrypay_text_payment_methods_color`
- `fawrypay_screen_header_text_color`
- `fawrypay_grey_text_color`
- `fawrypay_screen_background_color`

### AVL Screen Colors

<img width="300" alt="AVL colors" src="https://github.com/user-attachments/assets/352ea784-d3e8-4a14-ad09-706b2d5dd896" />

Changing these colors updates the main AVL screen. Example:

```xml
<color name="fawrypay_screen_header_text_color">#053F5C</color>
<color name="fawrypay_amount_card_background_color">#EFF7FA</color>
<color name="fawrypay_labels_color">#003247</color>
<color name="fawrypay_screen_background_color">#FFFFFF</color>
<color name="fawrypay_dimmed_button_color">#DDDFDF</color>
<color name="fawrypay_enabled_button_color">#016891</color>
```

### Logo and Fonts

- **Logo:** add a PNG file named `fawrypay_logo.png` under your app `drawable` package.
- **Fonts:** add font files in your package using these names:

```text
fawrypay_cairo_semi_bold
fawrypay_fawry_pro_bold
fawrypay_cairo_bold
```

---

## Step 6: Callbacks

### `launchAnonymousSDK` (`FawrySdkCallbacks`)

There are 3 callbacks:

1. **`onPaymentCompleted(paymentStatus: PaymentStatus, data: CreatePayRefNoResponse?, error: FawryPayError?)`**
   - Called when the payment response is received, whether success or failure.

2. **`onSuccess(paymentStatus: PaymentStatus, data: CreatePayRefNoResponse?)`**
   - If the receipt is enabled: called after closing the receipt when payment succeeds.
   - If the receipt is disabled: called when the payment screen finishes and payment succeeds.

3. **`onFailure(error: FawryPayError)`**
   - If the receipt is enabled: called after clicking the done button on the receipt when payment failed.
   - If the receipt is disabled: called when the payment screen finishes and payment failed.

### `launchAVL` (`AVLCallbacks`)

There are 5 callbacks:

1. **`onSuccess(paymentStatus: PaymentStatus, data: CreatePayRefNoResponse?)`**
   - Called after a successful payment flow ends.

2. **`onPaymentCompleted(paymentStatus: PaymentStatus, data: CreatePayRefNoResponse?, error: FawryPayError?)`**
   - Called after receiving the payment response, either success or failure.

3. **`onFailure(error: FawryPayError)`**
   - Called after a failed payment flow ends.

4. **`onBackClicked()`**
   - Called when the user presses back to close the flow.

5. **`onCancelClicked()`**
   - Called when the user cancels the flow.

### `launchCardManagerFlow` (`CardManagerCallbacks`)

There are 2 callbacks:

1. **`onFailure(error: FawryPayError)`**
   - Called if initialization of the flow fails.

2. **`onSuccess(message: String)`**
   - Not used in this flow.
