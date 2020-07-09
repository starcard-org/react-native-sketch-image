package com.starcard.sketchimageeditor;

import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;

public class SketchImageEditorModule extends ReactContextBaseJavaModule {
    SketchImageEditorModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "ImageEditorModule";
    }

    @ReactMethod
    public void transferToBase64(final int tag, final String type, final boolean transparent, 
        final boolean includeImage, final boolean includeText, final boolean cropToImageSize, final Callback callback){
        try {
            final ReactApplicationContext context = getReactApplicationContext();
            UIManagerModule uiManager = context.getNativeModule(UIManagerModule.class);
            uiManager.addUIBlock(new UIBlock() {
                public void execute(NativeViewHierarchyManager nvhm) {
                    SketchImageEditor view = (SketchImageEditor) nvhm.resolveView(tag);
                    String base64 = view.getBase64(type, transparent, includeImage, includeText, cropToImageSize);
                    callback.invoke(null, base64);
                }
            });
        } catch (Exception e) {
            callback.invoke(e.getMessage(), null);
        }
    }
}