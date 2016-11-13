package com.google.android.stardroid.layers;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import com.google.android.stardroid.R;
import com.google.android.stardroid.base.TimeConstants;
import com.google.android.stardroid.control.AstronomerModel;
//import com.google.android.stardroid.renderer.RendererObjectManager;
import com.google.android.stardroid.renderer.RendererObjectManager;
import com.google.android.stardroid.source.AbstractAstronomicalSource;
import com.google.android.stardroid.source.AstronomicalSource;
import com.google.android.stardroid.source.ImageSource;
import com.google.android.stardroid.source.Sources;
import com.google.android.stardroid.source.TextSource;
import com.google.android.stardroid.source.impl.ImageSourceImpl;
import com.google.android.stardroid.source.impl.TextSourceImpl;
import com.google.android.stardroid.units.GeocentricCoordinates;
import com.google.android.stardroid.units.Vector3;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

public class NewTransientLayer extends AbstractSourceLayer {
    List<Transient> transients = new ArrayList<>();

    public static class Transient {
        private int nameId;
        private GeocentricCoordinates coordinates;
//        private int ra;
//        private int dec;
//        private int mag;

        public Transient(String name, int nameId, GeocentricCoordinates coordinates) {
            this.nameId = nameId;
            this.coordinates = coordinates;
//            this.ra = ra;
//            this.dec = dec;
//            this.mag = mag;
        }
    }

    private final AstronomerModel model;

    public NewTransientLayer(AstronomerModel model, Resources resources) {
        super(resources, true);
        this.model = model;
        initializeTransients();
    }

    private void initializeTransients() {
        System.out.println("DEBUG ADDED TRANSIENT");
        transients.add(new Transient("Test1", R.string.testTransient, GeocentricCoordinates.getInstance(233, -53)));
        transients.add(new Transient("Test2", R.string.testTransient1, GeocentricCoordinates.getInstance(170, -63)));
        transients.add(new Transient("Test1", R.string.testTransient2, GeocentricCoordinates.getInstance(74, -73)));
        transients.add(new Transient("Test1", R.string.testTransient3, GeocentricCoordinates.getInstance(236, 11)));
        transients.add(new Transient("Test1", R.string.testTransient4, GeocentricCoordinates.getInstance(168, 90)));
        transients.add(new Transient("Test1", R.string.testTransient5, GeocentricCoordinates.getInstance(69, 58)));
        transients.add(new Transient("Test1", R.string.testTransient6, GeocentricCoordinates.getInstance(1, -36)));
        transients.add(new Transient("Test1", R.string.testTransient7, GeocentricCoordinates.getInstance(138, 75)));
        transients.add(new Transient("Test1", R.string.testTransient8, GeocentricCoordinates.getInstance(178, -12)));
        transients.add(new Transient("Test1", R.string.testTransient9, GeocentricCoordinates.getInstance(48, -51)));
        transients.add(new Transient("Test1", R.string.testTransient10, GeocentricCoordinates.getInstance(254, 63)));
        transients.add(new Transient("Test1", R.string.testTransient11, GeocentricCoordinates.getInstance(217, 70)));
        transients.add(new Transient("Test1", R.string.testTransient12, GeocentricCoordinates.getInstance(132, -58)));
        transients.add(new Transient("Test1", R.string.testTransient13, GeocentricCoordinates.getInstance(222, 41)));
        transients.add(new Transient("Test1", R.string.testTransient14, GeocentricCoordinates.getInstance(57, -26)));
    }

    protected void initializeAstroSources(ArrayList<AstronomicalSource> sources) {
        for (Transient t : transients) {
            System.out.println("DEBUG ADDED TRANSIENT TO SOURCES");
            sources.add(new TransientSource(model, t, getResources()));
        }
    }

    @Override
    public int getLayerDepthOrder() {
        return 100;
    }

    @Override
    public String getPreferenceId() {
        return "source_provider.2";
    }

    @Override
    public String getLayerName() {
        return "Transients Layer";
    }

    @Override
    protected int getLayerNameId() {
        return R.string.show_transient_pref;
    }

    private static class TransientSource extends AbstractAstronomicalSource {
        private static final int LABEL_COLOR = 0xf67e81;
        private static final Vector3 UP = new Vector3(0.0f, 1.0f, 0.0f);
        private static final float SCALE_FACTOR = 0.03f;
        private static final long UPDATE_FREQ_MS = 1L * TimeConstants.MILLISECONDS_PER_DAY;

        private final List<ImageSource> imageSources = new ArrayList<>();
//        private final List<TextSource> labelSources = new ArrayList<>();

        private long lastUpdateTimeMs = 0L;
        private final AstronomerModel model;
        private final Transient t;
        private String name;
        private ImageSourceImpl theImage;
        private TextSource label;
        private List<String> searchNames = new ArrayList<>();

        public TransientSource(AstronomerModel model, Transient t, Resources resources) {
            this.model = model;
            this.t = t;
            this.name = resources.getString(t.nameId);
            theImage = new ImageSourceImpl(t.coordinates, resources, R.drawable.transient_image, UP, SCALE_FACTOR);
            imageSources.add(theImage);
//            label = new TextSourceImpl(t.coordinates, name, LABEL_COLOR);
//            labelSources.add(label);
        }

        @Override
        public Sources initialize(){
            return this;
        }

        @Override
        public EnumSet<RendererObjectManager.UpdateType> update() {
            EnumSet<RendererObjectManager.UpdateType> updateTypes = EnumSet.noneOf(RendererObjectManager.UpdateType.class);
            if (Math.abs(model.getTime().getTime() - lastUpdateTimeMs) > UPDATE_FREQ_MS) {
                updateTypes.add(RendererObjectManager.UpdateType.Reset);
            }
            return updateTypes;
        }

        @Override
        public List<? extends  ImageSource> getImages(){
            return imageSources;
        }

//        @Override
//        public List<? extends TextSource> getLabels(){
//            return labelSources;
//        }
    }
}