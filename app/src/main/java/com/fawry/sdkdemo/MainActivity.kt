package com.fawry.sdkdemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fawry.fawrypay.domain.models.AVLInfo
import com.fawry.fawrypay.domain.models.BillItems
import com.fawry.fawrypay.domain.models.CreatePayRefNoResponse
import com.fawry.fawrypay.domain.models.FawryLaunchModel
import com.fawry.fawrypay.domain.models.FawryPayError
import com.fawry.fawrypay.domain.models.LaunchCustomerModel
import com.fawry.fawrypay.domain.models.LaunchMerchantModel
import com.fawry.fawrypay.utils.AVLCallbacks
import com.fawry.fawrypay.utils.CardManagerCallbacks
import com.fawry.fawrypay.utils.FawrySdkCallbacks
import com.fawry.fawrypay.utils.fawrySdk.LaunchFawrySdk
import com.fawry.fawrypay.utils.fawrySdk.enums.Languages
import com.fawry.fawrypay.utils.fawrySdk.enums.PaymentMethods
import com.fawry.fawrypay.utils.fawrySdk.enums.PaymentStatus


class MainActivity : AppCompatActivity() {

    //Replace all data below with your own data
    var baseUrl = "https://atfawrystaging.atfawry.com/"

    //customer info
    var customerName = "testName"
    var customerProfileId = "1234"
    var customerMobile = "01234567890"
    var customerEmail =
        "test@test.com" //required in saving cards for payment with card tokenization

    //merchant info
    var merchantCode = "siYxylRjSPyg6dz0QH/y9A=="
    var merchantSecretCode = "086f55c1-463b-425a-9342-f75b094c8b3e"

    val beneficiaryWalletNumber = "12345678911"
    val avlValue = 15.00
    val billingAcct = "12345678911"
    val avlInfo = AVLInfo(
        offUsBTC = 11,
        onUsBTC  = 13,
        internationalBANs = arrayListOf("123456", "654321"),
        onUsAvlFees = 7.0,
        offUsAvlFees = 11.0,
        minValue = null,
        maxValue = null,
        beneficiaryName = null,
        avlValue = null,
        billingAcct = billingAcct,
        beneficiaryWalletNumber = beneficiaryWalletNumber,
        avlExtraProperties = null,
        screenTitle = "AVL FLOW",
    )

    val chargeItems = ArrayList<BillItems>()
    val billItem = BillItems(
        itemId = "testId",
        description = "",
        quantity = "1",
        price = "10.00",
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chargeItems.add(billItem)

        val btnAVL = findViewById<Button>(R.id.btn_AVL)
        btnAVL.setOnClickListener {
            startAVL()
        }

        val btnCardManager = findViewById<Button>(R.id.btn_card_manager)
        btnCardManager.setOnClickListener {
            startCardManager()
        }

        val btnAnonymousFlow = findViewById<Button>(R.id.btn_anonymous)
        btnAnonymousFlow.setOnClickListener {
            launchSdk()
        }
    }

    private fun startAVL() {
        LaunchFawrySdk.launchAVL(
            activity = this, _languages = Languages.ENGLISH, _baseUrl = baseUrl,
            _fawryLaunchModel = FawryLaunchModel (
                launchCustomerModel = LaunchCustomerModel(
                    customerName = customerName,
                    customerEmail = customerEmail,
                    customerMobile = customerMobile
                ),
                launchMerchantModel = LaunchMerchantModel(
                    merchantCode = merchantCode,
                    secretCode = merchantSecretCode
                ),
                allow3DPayment = true,
                skipReceipt = false,
                avlInfo = avlInfo,
            ),
            _callback = object : AVLCallbacks {

                override fun onBackClicked() {
                    Toast.makeText(this@MainActivity, "on back clicked", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onCancelClicked() {
                    Toast.makeText(this@MainActivity, "on cancel clicked", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onFailure(error: FawryPayError) {
                    Toast.makeText(this@MainActivity, "on failure $error", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onPaymentCompleted(
                    paymentStatus: PaymentStatus,
                    data: CreatePayRefNoResponse?,
                    error: FawryPayError?
                ) {
                    Toast.makeText(this@MainActivity, "onPaymentCompleted $paymentStatus", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onSuccess(
                    paymentStatus: PaymentStatus,
                    data: CreatePayRefNoResponse?
                ) {
                    Toast.makeText(this@MainActivity, "onSuccess $paymentStatus", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun startCardManager(){
        LaunchFawrySdk.launchCardManagerFlow(
            activity = this, _languages = Languages.ENGLISH, _baseUrl = baseUrl,
            _fawryLaunchModel = FawryLaunchModel(
                launchCustomerModel = LaunchCustomerModel(
                    customerEmail = customerEmail,
                    customerMobile = customerMobile,
                    customerProfileId = customerProfileId
                ),
                launchMerchantModel = LaunchMerchantModel(
                    merchantCode = merchantCode,
                    secretCode = merchantSecretCode,
                ),
            ),
            _callback = object : CardManagerCallbacks {
                override fun onFailure(error: FawryPayError) {
                    Toast.makeText(this@MainActivity, "on failure $error", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun launchSdk() {

        LaunchFawrySdk.launchAnonymousSDK(
            activity = this,
            _languages = Languages.ENGLISH,
            _baseUrl = baseUrl,
            _fawryLaunchModel = FawryLaunchModel(
                launchCustomerModel = LaunchCustomerModel(
                    customerEmail = customerEmail,
                    customerMobile = customerMobile,
                    customerProfileId = customerProfileId
                ),
                launchMerchantModel = LaunchMerchantModel(
                    merchantCode = merchantCode,
                    secretCode = merchantSecretCode,
                    merchantRefNum = "${System.currentTimeMillis()}"
                ),
                allowVoucher = true,
                allow3DPayment = true,
                chargeItems = chargeItems,
                skipReceipt = false,
                payWithCardToken = true,
                paymentMethods = PaymentMethods.ALL,
                enableTokenization = false
            ),
            _callback = object : FawrySdkCallbacks {
                override fun onSuccess(
                    paymentStatus: PaymentStatus,
                    data: CreatePayRefNoResponse?
                ) {
                    Toast.makeText(this@MainActivity, "on success ${paymentStatus}", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onPaymentCompleted(
                    paymentStatus: PaymentStatus,
                    data: CreatePayRefNoResponse?,
                    error: FawryPayError?
                ) {
                    Toast.makeText(this@MainActivity, "onPaymentCompleted ${paymentStatus}", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onFailure(error: FawryPayError) {
                    Toast.makeText(this@MainActivity, "on failure $error", Toast.LENGTH_LONG).show()
                }
            })
    }

}