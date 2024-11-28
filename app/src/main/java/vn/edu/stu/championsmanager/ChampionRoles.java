package vn.edu.stu.championsmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import vn.edu.stu.championsmanager.adapter.RoleAdapter;
import vn.edu.stu.championsmanager.models.Role;

public class ChampionRoles extends AppCompatActivity {

    public static final String DB_NAME = "dbchamp.sqlite";
    public static final String DB_PATH_SUFFIX = "/databases/";

    private Toolbar toolbar;
    private ListView lvRole;
    private ArrayAdapter<Role> adapter;
    private Button btnAddRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_champion_roles);

        copyDBFromAssets();
        addControls();
        addEvents();
        setupToolbar();
        loadRolesData();
    }

    private void copyDBFromAssets() {
        File dbFile = getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            File dbDir = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!dbDir.exists()) {
                dbDir.mkdir();
            }
            try {
                InputStream is = getAssets().open(DB_NAME);
                String outputFilePath = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DB_NAME;
                OutputStream os = new FileOutputStream(outputFilePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (Exception e) {
                Log.e("Error",e.toString());
            }
        }
    }

    private void addControls() {
        lvRole =findViewById(R.id.lvRole);
        btnAddRole = findViewById(R.id.btnAddRole);
//        adapter = new RoleAdapter(ChampionRoles.this, R.layout.item_roles,roleList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvRole.setAdapter(adapter);
    }

    private void addEvents() {
    }

    private void loadRolesData() {
        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        Cursor cursor = database.rawQuery("SELECT * FROM roles", null);
        adapter.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            adapter.add(new Role(id, name));
        }
        cursor.close();
        database.close();
        adapter.notifyDataSetChanged();
    }


    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.roleslist));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_championslist) {
            startActivity(new Intent(this, ChampionsList.class));
        } else if (id == R.id.action_championroles) {
            startActivity(new Intent(this, ChampionRoles.class));
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, About.class));
        } else if (id == R.id.action_logout) {
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}