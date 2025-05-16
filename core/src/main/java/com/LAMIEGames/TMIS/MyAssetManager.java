package com.LAMIEGames.TMIS;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;

//public class MyAssetManager extends AssetManager {
//    private FreeTypeFontGenerator fontGenerator;
//    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
//    private BitmapFont[] fonts;
//    public final AssetManager manager = new AssetManager();
//    private Skin skin;
//
//    // Textures
//    public final String mapAtlas = "data/map/map.png";
//    public final String xanaAtlas = "data/player/xana/player_xana.png";
//
//    public void loadImages(){
//        manager.load(mapAtlas, TextureAtlas.class);
//        manager.load(xanaAtlas, TextureAtlas.class);
//    }
//
//    //todo: как-нибудь потом добавить сюда свои скины (вроде добавила хз) ((разобраться с кнопками))
//
//    public void initializeSkin() {
//        // Инициализация генератора шрифтов
//        final ObjectMap<String, Object> resources = new ObjectMap<String, Object>();
//        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fontTMIS.ttf"));
//        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        fontParameter.minFilter = Texture.TextureFilter.Linear;
//        fontParameter.magFilter = Texture.TextureFilter.Linear;
//
//        // Создание шрифтов разных размеров
//        final int[] sizesToCreate = {16, 20, 26, 32};
//        fonts = new BitmapFont[sizesToCreate.length];
//
//        for (int size: sizesToCreate) {
//            fontParameter.size = size;
//            resources.put("font_" + size, fontGenerator.generateFont(fontParameter));
//        }
//
//        fontGenerator.dispose();
//
//        // load skin
//        final SkinLoader.SkinParameter skinParameter = new SkinLoader.SkinParameter("ui/hud.atlas", resources);
//        manager.load("ui/hud.json", Skin.class, skinParameter);
//        manager.finishLoading();
//        skin = manager.get("ui/hud.json", Skin.class);
//    }
//
//    public Skin getSkin() {
//        return skin;
//    }
//
//}
