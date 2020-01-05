package MoneyConverterJSON;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewer extends WebViewClient {
    @Override
    public void onPageFinished(WebView webView, String url) {
        webView.loadUrl ("javascript: document.getElementByClassName ('frame fm-middle');");
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;
    }

    // Для старых устройств
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public void prepareWebPage(WebView webView){
        int coordinateX = 0;
        int coordinateY = 1050;
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setBuiltInZoomControls(true);

        webView.scrollTo(coordinateX,coordinateY);
    }

    public static void getGraphic(WebView webView, String currencyCode, String currencyResultCode){

        currencyCode = currencyCode.toLowerCase();
        currencyResultCode = currencyResultCode.toLowerCase();

        webView.loadUrl("http://www.floatrates.com/chart/" + currencyCode + "/" + currencyResultCode + "/");
    }
}
