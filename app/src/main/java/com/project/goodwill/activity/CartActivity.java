package com.project.goodwill.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.CardInfo;
import com.google.android.gms.wallet.CardRequirements;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.project.goodwill.modules.DbClass;
import com.project.goodwill.modules.Product;
import com.project.goodwill.adapters.ProductAdapter;
import com.project.goodwill.R;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    ArrayList<Product> products;
    ListView productList;
    Card card = new Card("4242-4242-4242-4242", 12, 2019, "123");
    int likeCost ;
    static int  count = 0;
    int value;
    TextView price;
    EditText text;
    private PaymentsClient mPaymentsClient;
    private View mGooglePayButton;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 42;

    private void clear (View v){
        PetActivity.clearcart();
        Intent intent = getIntent();
        intent.removeExtra("products");
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
    private void back_cart(View v){
        Intent i = new Intent(getApplicationContext(),PetActivity.class);
        i.putExtra("products",products);
        startActivity(i);
        finish();
    }
    private void pay(View v){
        Intent i = new Intent(getApplicationContext(),PayActivity.class);
        startActivity(i);
        finish();
    }
    private void clickPlus(View v){
        ListView lv = findViewById(R.id.productList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ((TextView) view.findViewById(R.id.nameView)).getText().toString();
                for (Product p : products) {
                    if (p.getName().equals(name)) {
                        int qtt = p.getCount();
                        p.setCount(qtt + 1);
                        getPrice();
                        break;
                    }
                }
            }
        });
    }
    private void clickMinus(View v){
        ListView lv = findViewById(R.id.productList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ((TextView) view.findViewById(R.id.nameView)).getText().toString();
                for (Product p : products) {
                    if (p.getName().equals(name)) {
                        int qtt = p.getCount();
                        if (qtt == 0) {
                            getPrice();
                            break;
                        }
                        p.setCount(qtt - 1);
                        getPrice();
                        break;
                    }
                }
            }
        });
    }

    private int getCount(){
       int qtt = 0;
        for (Product p: products
             ) {
            qtt += p.getCount();
        }
        return qtt;
    }
    private void getPrice(){
        if(likeCost != 0) {
            count = getCount();
            Log.d("jopa", Integer.toString(likeCost));
            value = count * likeCost;
            Log.d("jopa", Integer.toString(value));
            Log.d("jopa", Integer.toString(count));
            String str;
            if(Locale.getDefault().getLanguage().equals("uk") ||Locale.getDefault().getLanguage().equals("ru")) {
                str = "Усього: " + value + " uah";
            }else{
                str = "Total: " + value + " uah";
            }

            price.setText(str);
            price.invalidate();
        }
        else {
            likeCost = DbClass.getLikeCost();
            getPrice();
        }

    }
    @Override
    protected void onStart(){
        super.onStart();
        Bundle extras = getIntent().getExtras();
        likeCost = (int)extras.get("likecost");
        if(extras.get("products") != null) {
            products =(ArrayList)extras.get("products");
            if(products.size() == 0){
                findViewById(R.id.empty).setVisibility(View.VISIBLE);
                findViewById(R.id.scroll).setVisibility(View.INVISIBLE);
            }
            else {
                findViewById(R.id.empty).setVisibility(View.INVISIBLE);
                findViewById(R.id.scroll).setVisibility(View.VISIBLE);
                productList = (ListView) findViewById(R.id.productList);
                setListViewHeightBasedOnChildren(productList);
                ProductAdapter adapter = new ProductAdapter(this, R.layout.list_item, products);
                productList.setAdapter(adapter);
                productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String str = (String) parent.getItemAtPosition(position);
//                String countView = ((TextView) view.findViewById(R.id.countView)).getText().toString();
//                int qtt = Integer.parseInt(countView.split(" ")[0]);
                        Log.d("curr qtt", str);
                    }
                });
                getPrice();
            }
        } else{
            findViewById(R.id.empty).setVisibility(View.VISIBLE);
            findViewById(R.id.scroll).setVisibility(View.INVISIBLE);
        }


    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        price = (TextView) findViewById(R.id.price);
        mPaymentsClient =
                Wallet.getPaymentsClient(
                        this,
                        new Wallet.WalletOptions.Builder()
                                .setEnvironment(WalletConstants.ENVIRONMENT_PRODUCTION)
                                .build());
        findViewById(R.id.googlepay).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PaymentDataRequest request = createPaymentDataRequest();
                        if (request != null) {
                            AutoResolveHelper.resolveTask(
                                    mPaymentsClient.loadPaymentData(request),
                                    CartActivity.this,
                                    LOAD_PAYMENT_DATA_REQUEST_CODE);
                            // LOAD_PAYMENT_DATA_REQUEST_CODE is a constant integer of your choice,
                            // similar to what you would use in startActivityForResult
                        }
                    }
                });
//        possiblyShowGooglePayButton();


    }
    private void isReadyToPay() {
        IsReadyToPayRequest request = IsReadyToPayRequest.newBuilder()
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                .build();
        Task<Boolean> task = mPaymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(
                new OnCompleteListener<Boolean>() {
                    public void onComplete(Task<Boolean> task) {
                        try {
                            boolean result =
                                    task.getResult(ApiException.class);
                            if(result == true) {
                                mGooglePayButton = findViewById(R.id.googlepay);
                                mGooglePayButton.setVisibility(View.VISIBLE);
                                //show Google as payment option
                            } else {
                                mGooglePayButton = findViewById(R.id.googlepay);
                                mGooglePayButton.setVisibility(View.INVISIBLE);
                                //hide Google as payment option
                            }
                        } catch (ApiException exception) { }
                    }
                });
    }
    private PaymentMethodTokenizationParameters createTokenizationParameters() {
        return PaymentMethodTokenizationParameters.newBuilder()
                .setPaymentMethodTokenizationType(WalletConstants.PAYMENT_METHOD_TOKENIZATION_TYPE_PAYMENT_GATEWAY)
                .addParameter("gateway", "Wayforpay")
                .addParameter("gatewayMerchantId", "499379b6059e8983dbb2f61cb2320f1e6fa5282f")
                .build();
    }
    private PaymentDataRequest createPaymentDataRequest() {
        PaymentDataRequest.Builder request =
                PaymentDataRequest.newBuilder()
                        .setTransactionInfo(
                                TransactionInfo.newBuilder()
                                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                                        .setTotalPrice(Integer.toString(value))
                                        .setCurrencyCode("UAH")
                                        .build())
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                        .setCardRequirements(
                                CardRequirements.newBuilder()
                                        .addAllowedCardNetworks(Arrays.asList(
                                                WalletConstants.CARD_NETWORK_AMEX,
                                                WalletConstants.CARD_NETWORK_VISA,
                                                WalletConstants.CARD_NETWORK_MASTERCARD))
                                        .build());

        request.setPaymentMethodTokenizationParameters(createTokenizationParameters());
        return request.build();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        // You can get some data on the user's card, such as the brand and last 4 digits
                        CardInfo info = paymentData.getCardInfo();
                        // You can also pull the user address from the PaymentData object.
                        UserAddress address = paymentData.getShippingAddress();
                        // This is the raw JSON string version of your Stripe token.
                        String rawToken = paymentData.getPaymentMethodToken().getToken();

                        // Now that you have a Stripe token object, charge that by using the id
                        Token stripeToken = Token.fromString(rawToken);
//                        if (stripeToken != null) {
//                            // This chargeToken function is a call to your own server, which should then connect
//                            // to Stripe's API to finish the charge.
//                            Log.d("Got token",stripeToken.getId());
//                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        // Log the status for debugging
                        assert status != null;
//                        Log.d("Status",status.getStatusMessage());
                        // Generally there is no need to show an error to
                        // the user as the Google Payment API will do that
                        break;
                    default:
                        // Do nothing.
                }
                break; // Breaks the case LOAD_PAYMENT_DATA_REQUEST_CODE
            // Handle any other startActivityForResult calls you may have made.
            default:
                // Do nothing.
        }
    }

}
