package com.gurpster.facapemobile.view.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.data.source.remote.ApiService;
import com.gurpster.facapemobile.util.FileUtils;
import com.gurpster.facapemobile.util.RetrofitServiceFactory;
import com.necistudio.vigerpdf.VigerPDF;
import com.necistudio.vigerpdf.adapter.VigerAdapter;
import com.necistudio.vigerpdf.manage.OnResultListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DebtWebViewActivity extends AppCompatActivity {

    private ArrayList<Bitmap> itemData;
    private VigerAdapter adapter;
    private VigerPDF vigerPDF;
    private String fileDir;

    private MenuItem activeMenuItem;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_web_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.viewPager);
        vigerPDF = new VigerPDF(this);

        itemData = new ArrayList<>();
        adapter = new VigerAdapter(getApplicationContext(), itemData);
        viewPager.setAdapter(adapter);

        Intent i = getIntent();

        if (i != null) {
            getSupportActionBar().setTitle(i.getStringExtra("debtTitle"));
            retrofitDownloadFile(i.getStringExtra("debtUrl"));
        }

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        progress = new ProgressDialog(this);
        progress.setTitle("Carregando");
        progress.setMessage("Aguarde um instante...");
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_debt, menu);
        activeMenuItem = menu.findItem(R.id.save_pdf);
        activeMenuItem.setEnabled(false);
        activeMenuItem.setIcon(R.drawable.ic_floppy_disk_disabled);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_pdf) {
            final String outputPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "facape_boleto.pdf";
            messageDownload("Baixando arquivo...");
            if (FileUtils.moveFile(this, fileDir, outputPath)) messageDownload("Donwload completo.");
            return true;
        }
        return false;
    }

    private void retrofitDownloadFile(String fileUrl) {
        ApiService apiService = RetrofitServiceFactory.createService(ApiService.class);

        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrlSync(fileUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    writeResponseBodyToDisk(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Facape Mobile error", "DebtWebView " + t.getMessage());
            }
        });
    }

    private void messageDownload(String message) {
        Toast.makeText(DebtWebViewActivity.this, message, //To notify the Client that the file is being downloaded
                Toast.LENGTH_SHORT).show();
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {

        String fileName = "facape_boleto.pdf";

        try {

            File futureStudioIconFile = new File(this.getExternalFilesDir(null), fileName);
            fileDir = futureStudioIconFile.getAbsolutePath();

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[1024];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();
                fromFile(fileDir);
                Log.d("WebView", "Done!");

                activeMenuItem.setEnabled(true);
                activeMenuItem.setIcon(R.drawable.ic_floppy_disk);
                return true;
            } catch (IOException e) {
                Log.e("WebView", e.getMessage());
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            Log.e("WebView", e.getMessage());
            return false;
        }
    }

    private void fromFile(String path) {

        itemData.clear();
        adapter.notifyDataSetChanged();
        File file = new File(path);
        vigerPDF.cancle();

        vigerPDF.initFromFile(file, new OnResultListener() {
            @Override
            public void resultData(Bitmap bitmap) {
                itemData.add(bitmap);
                adapter.notifyDataSetChanged();
                if(progress.isShowing())
                    progress.dismiss();
            }

            @Override
            public void progressData(int i) {

            }

            @Override
            public void failed(Throwable throwable) {

            }
        });
    }

    private void fromNetwork(String endpoint) {
        itemData.clear();
        adapter.notifyDataSetChanged();
        vigerPDF.cancle();
        vigerPDF.initFromNetwork(endpoint, new OnResultListener() {
            @Override
            public void resultData(Bitmap data) {
                Log.e("data", "run");
                itemData.add(data);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void progressData(int progress) {
                Log.e("data", "" + progress);
            }

            @Override
            public void failed(Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vigerPDF != null) vigerPDF.cancle();
    }
}
