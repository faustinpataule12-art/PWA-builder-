package com.nps.pwa.studio;

  import android.annotation.SuppressLint;
  import android.app.Activity;
  import android.app.AlertDialog;
  import android.os.Bundle;
  import android.webkit.WebSettings;
  import android.webkit.WebView;
  import android.webkit.WebViewClient;
  import android.webkit.WebChromeClient;

  public class MainActivity extends Activity {
      private WebView webView;

      @SuppressLint("SetJavaScriptEnabled")
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          webView = new WebView(this);
          setContentView(webView);
          WebSettings s = webView.getSettings();
          s.setJavaScriptEnabled(true);
          s.setDomStorageEnabled(true);
          s.setDatabaseEnabled(true);
          s.setAllowFileAccess(true);
          s.setAllowFileAccessFromFileURLs(true);
          s.setAllowUniversalAccessFromFileURLs(true);
          s.setBuiltInZoomControls(false);
          s.setSupportZoom(false);
          s.setLoadWithOverviewMode(true);
          s.setUseWideViewPort(true);
          webView.setWebViewClient(new WebViewClient());
          webView.setWebChromeClient(new WebChromeClient());
          webView.loadUrl("file:///android_asset/www/index.html");
      }

      @Override
      public void onBackPressed() {
          if (webView.canGoBack()) {
              webView.goBack();
              return;
          }
          // Lire la préférence "confirmation avant quitter" depuis localStorage HTML
          webView.evaluateJavascript(
              "(function(){ try{ return localStorage.getItem('nps_quit_confirm'); }catch(e){ return null; } })()",
              value -> {
                  // value est "null", "\"1\"", "\"0\"" ou null
                  boolean showConfirm = !"\"0\"".equals(value);
                  if (showConfirm) {
                      new AlertDialog.Builder(MainActivity.this)
                          .setTitle("Quitter l'application")
                          .setMessage("Voulez-vous vraiment quitter PWA-APK Studio ?")
                          .setPositiveButton("Quitter", (d, w) -> finish())
                          .setNegativeButton("Annuler", null)
                          .show();
                  } else {
                      finish();
                  }
              }
          );
      }
  }
