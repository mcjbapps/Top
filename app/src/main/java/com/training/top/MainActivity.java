package com.training.top;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.containerMain)
    CoordinatorLayout containerMain;

    private ArtistaAdapter adapter;

//  Comprobando que funciona nuestro control de versiones.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configToolbar();
        configAdapter();
        configRecyclerView();

   //     generateArtist();

    }

    private void generateArtist() {
        String[] nombres = {"Rachel", "Mary Elizabeth", "Jessica", "Gal"};
        String[] apellidos = {"McAdams", "Winstead", "Chastain", "Gadot"};
        long[] nacimientos = {280132352000L, 470469600000L, 228031200000L, 483667200000L};
        String[] lugares = {"Canadá", "USA", "USA", "Israel"};
        short[] estaturas = {163, 173, 163, 178};
        String[] notas = {"Rachel Anne McAdams was born on November 17, 1978 in London, Ontario, Canada, to Sandra Kay (Gale), a nurse, and Lance Frederick McAdams, a truck driver and furniture mover. She is of English, Welsh, Irish, and Scottish descent. Rachel became involved with acting as a teenager and by the age of 13 was performing in Shakespearean productions in summer theater camp; she went on to graduate with honors with a BFA degree in Theater from York University. After her debut in an episode of Disney's The Famous Jett Jackson (1998), she co-starred in the Canadian TV series Slings and Arrows (2003), a comedy-drama about the trials and travails of a Shakespearean theater group, and won a Gemini award for her performance in 2003.",
                "Mary Elizabeth Winstead is an actress known for her versatile work in a variety of film and television projects. Possibly most known for her role as Ramona Flowers in Scott Pilgrim vs. los ex de la chica de sus sueños (2010), she has also starred in critically acclaimed independent films such as Smashed (2012), for which she received an Independent Spirit Award nomination, as well as genre fare like Destino final 3 (2006) and Quentin Tarantino's A prueba de muerte (2007).",
                "Jessica Michelle Chastain was born in Sacramento, California, and was raised in a middle-class household in a northern California suburb. Her mother, Jerri Chastain, is a vegan chef whose family is originally from Kansas, and her stepfather is a fireman. She discovered dance at the age of nine and was in a dance troupe by age thirteen. She began performing in Shakespearean productions all over the Bay area.",
                "Gal Gadot is an Israeli actress, singer, martial artist, and model. She was born in Rosh Ha'ayin, Israel, to a Jewish family. Her parents are Irit, a teacher, and Michael, an engineer, who is a sixth-generation Israeli. She served in the IDF for two years, and won the Miss Israel title in 2004"};
        String[] fotos = {"https://m.media-amazon.com/images/M/MV5BMTY5ODcxMDU4NV5BMl5BanBnXkFtZTcwMjAzNjQyNQ@@._V1_UY1200_CR99,0,630,1200_AL_.jpg",
                "https://i.pinimg.com/originals/33/ac/fc/33acfc84e006c38f1132075bddd28ced.jpg",
                "https://aws.revistavanityfair.es/prod/designs/v1/assets/785x589/141436.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Gal_Gadot_cropped_lighting_corrected_2b.jpg/519px-Gal_Gadot_cropped_lighting_corrected_2b.jpg"};

        for (int i = 0; i < 4; i++) {
            Artista artista = new Artista(nombres[i], apellidos[i], nacimientos[i], lugares[i],
                    estaturas[i], notas[i], i + 1, fotos[i]);
     //       adapter.add(artista);
            try {
                artista.save();
                Log.i("DBFlow", "Inserción correcta de datos");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("DBFlow", "Error al insertar datos");
            }
        }
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    private void configAdapter() {
        adapter = new ArtistaAdapter(new ArrayList<Artista>(), this);

    }

    private void configRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setList(getArtistasFromDB());
    }

    private List<Artista> getArtistasFromDB() {
        DatabaseWrapper databaseWrapper = null;
        return SQLite
                .select()
                .from(Artista.class)
                .orderBy(Artista_Table.orden, true)
                .queryList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Métodos implementados por la interface OnItemClickListener
    @Override
    public void onItemClick(Artista artista) {
  /*      sArtista.setId(artista.getId());
        sArtista.setNombre(artista.getNombre());
        sArtista.setApellidos(artista.getApellidos());
        sArtista.setFechaNacimiento(artista.getFechaNacimiento());
        sArtista.setEstatura(artista.getEstatura());
        sArtista.setLugarNacimiento(artista.getLugarNacimiento());
        sArtista.setOrden(artista.getOrden());
        sArtista.setNotas(artista.getNotas());
        sArtista.setFotoUrl(artista.getFotoUrl());
  */

        Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
        intent.putExtra(Artista.ID, artista.getId());
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(final Artista artista) {
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(60);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.main_dialogDelete_title)
                .setMessage(String.format(Locale.ROOT, getString(R.string.main_dialogDelete_message), artista.getNombreCompleto()))
                .setPositiveButton(R.string.label_dialog_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            artista.delete();
                            adapter.remove(artista);
                            showMessage(R.string.main_message_delete_success);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showMessage(R.string.main_message_delete_fail);
                        }
                    }
                })
                .setNegativeButton(R.string.label_dialog_cancel, null);
        builder.show();

    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode ==1) {
            adapter.add(sArtista);

        }

    }
*/


    @OnClick(R.id.fab)
    public void addArtist() {
        Intent intent = new Intent(MainActivity.this, AddArtistActivity.class);
        intent.putExtra(Artista.ORDEN, adapter.getItemCount() + 1);
        startActivityForResult(intent, 1);
    }

    private void showMessage(int resource) {
        Snackbar.make(containerMain, resource, Snackbar.LENGTH_SHORT).show();

    }

}