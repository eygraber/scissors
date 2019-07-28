/*
 * Copyright (C) 2015 Lyft, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lyft.android.scissors;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;

class CropViewExtensions {

    static void pickUsing(Activity activity, int requestCode) {
        activity.startActivityForResult(
                createChooserIntent(),
                requestCode);
    }

    static void pickUsing(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(
                createChooserIntent(),
                requestCode);
    }

    private static Intent createChooserIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        return Intent.createChooser(intent, null);
    }

    static BitmapLoader resolveBitmapLoader(CropView cropView) {
        return GlideBitmapLoader.createUsing(cropView);
    }

    static Rect computeTargetSize(int sourceWidth, int sourceHeight, int viewportWidth, int viewportHeight) {

        if (sourceWidth == viewportWidth && sourceHeight == viewportHeight) {
            return new Rect(0, 0, viewportWidth, viewportHeight); // Fail fast for when source matches exactly on viewport
        }

        float scale;
        if (sourceWidth * viewportHeight > viewportWidth * sourceHeight) {
            scale = (float) viewportHeight / (float) sourceHeight;
        } else {
            scale = (float) viewportWidth / (float) sourceWidth;
        }
        final int recommendedWidth = (int) ((sourceWidth * scale) + 0.5f);
        final int recommendedHeight = (int) ((sourceHeight * scale) + 0.5f);
        return new Rect(0, 0, recommendedWidth, recommendedHeight);
    }
}
