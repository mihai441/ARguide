package com.ARGuide.androidarsceneform;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ARGuide.MainMenu.Models.Cale;
import com.ARGuide.MainMenu.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Trackable;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class MainARActivity extends AppCompatActivity implements Scene.OnUpdateListener {

    ArFragment arFragment;
    TransformableNode arrowNode;
    MagneticSensor magneticSensor;
    float currentDegrees;
    Cale cale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armain);

        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            JSONObject jsonobject = null;
            try {
                String directieJson = extras.getString("directie");
                jsonobject = new JSONObject(directieJson);
                Cale calePrimita = new Cale(jsonobject.getString("denumire"), jsonobject.getString("cod"), jsonobject.getString("codQR"), jsonobject.getInt("etaj"), jsonobject.getInt("directie"));
                cale = calePrimita;
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    arFragment.getArSceneView().getScene().setOnUpdateListener(MainARActivity.this);
                    //addObject(Uri.parse("arrow.sfb"));
                }
            });

            magneticSensor = new MagneticSensor(this, cale.getDirectie());
            arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneForm);
        }
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

    private void addObject(Uri parse) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        Point point = getScreenCenter();
        if (frame != null) {
            List<HitResult> hits = frame.hitTest((float) point.x, (float) point.y);

            for (int i = 0; i < hits.size(); i++) {
                Trackable trackable = hits.get(i).getTrackable();
                if (trackable instanceof Plane && ((Plane) trackable).isPoseInPolygon(hits.get(i).getHitPose())) {
                    placeObject(arFragment, hits.get(i).createAnchor(), parse);
                }
            }
        }
    }

    private final void placeObject(final ArFragment fragment, final Anchor createAnchor, Uri model) {
        ModelRenderable.builder().setSource(fragment.getContext(), model).build().thenAccept((new Consumer() {
            // $FF: synthetic method
            // $FF: bridge method
            public void accept(Object var1) {
                this.accept((ModelRenderable) var1);
            }

            public final void accept(ModelRenderable it) {
                if (it != null)
                    MainARActivity.this.addNode(arFragment, createAnchor, it);
            }
        })).exceptionally((new Function() {
            public Object apply(Object var1) {
                return this.apply((Throwable) var1);
            }

            @Nullable
            public final Void apply(Throwable it) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainARActivity.this);
                builder.setMessage(it.getMessage()).setTitle("error!");
                AlertDialog dialog = builder.create();
                dialog.show();
                return null;
            }
        }));
    }

    private void addNode(ArFragment fragment, Anchor createAnchor, ModelRenderable renderable) {

        AnchorNode anchorNode = new AnchorNode(createAnchor);
        TransformableNode transformableNode = new TransformableNode(fragment.getTransformationSystem());
        transformableNode.setRenderable(renderable);
        transformableNode.setParent(anchorNode);
        fragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
        arrowNode = transformableNode;
        currentDegrees = magneticSensor.getmCurrentDegree();
        Quaternion q1 = transformableNode.getLocalRotation();
        Log.d("destination : ", Float.toString(currentDegrees));
        Log.d("q1.x : ", Float.toString(q1.x));
        Quaternion q2 = Quaternion.axisAngle(new Vector3(1f, 0f, 0f), currentDegrees);
        transformableNode.setLocalRotation(Quaternion.multiply(q1, q2));
    }

    private Point getScreenCenter() {
        View vw = findViewById(android.R.id.content);
        return new Point(vw.getWidth() / 2, vw.getHeight() / 2);
    }

    @Override
    public void onUpdate(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        if (arFragment.getArSceneView().getScene() != null) {
            if (frame != null) {
                if (arrowNode != null) {
                    arFragment.getArSceneView().getScene().removeChild(arrowNode.getParent());
                    arrowNode = null;
                }

                addObject(Uri.parse("arrow.sfb"));
            }
        }
    }

}
