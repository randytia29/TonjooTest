package com.example.randytia.tonjootest;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager;

import android.app.LoaderManager.LoaderCallbacks;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class TonjooActivity extends AppCompatActivity implements LoaderCallbacks<List<Tonjoo>> {

    private static final String TONJOO_REQUEST_URL = "https://private-anon-24c484fc39-recruitment.apiary-mock.com/api/contacts";

    private static final int TONJOO_LOADER_ID = 1;

    private TonjooAdapter adapter;

    private TextView mEmptyStateTextView;

    private Button buttonLogout;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView tonjooListView = (ListView) findViewById(R.id.list);

        firebaseAuth = FirebaseAuth.getInstance();

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        tonjooListView.setEmptyView(mEmptyStateTextView);

        // Create a new {@link ArrayAdapter} of tonjoos
        adapter = new TonjooAdapter(this, new ArrayList<Tonjoo>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        tonjooListView.setAdapter(adapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Ambil referensi ke LoaderManager untuk berinteraksi dengan loader.
            LoaderManager loaderManager = getLoaderManager();

            // Inisialisasi loader. Masukkan konstanta int ID yang dinyatakan di atas dan masukkan
            // null untuk bundle-nya. Masukkan aktivitas ini untuk parameter LoaderCallbacks (yang valid
            // karena aktivitas ini mengimplementasikan antarmuka LoaderCallbacks).
            loaderManager.initLoader(TONJOO_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(TonjooActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public Loader<List<Tonjoo>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(TONJOO_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        return new TonjooLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Tonjoo>> loader, List<Tonjoo> tonjoos) {
        // Sembunyikan indikator loading sebab datanya sudah dimuat
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Atur teks status kosong agar menampilkan "Tidak ditemukan gempa".
        mEmptyStateTextView.setText(R.string.no_contact);

        // Bersihkan adapter dari data gempa sebelumnya
        adapter.clear();

        // Jika terdapat daftar yang valid dari {@link Tonjoo}s, maka tambahkan ke adapter
        // Ini akan memicu pembaruan ListView.
        if (tonjoos != null && !tonjoos.isEmpty()) {
            adapter.addAll(tonjoos);
        }

        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Tonjoo>> loader) {
        adapter.clear();
    }
}
