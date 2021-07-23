package com.applibgroup.diffreproject.slice;

import com.applibgroup.diffre.DiffreView;
import com.applibgroup.diffreproject.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Slider;

public class MainAbilitySlice extends AbilitySlice {

    Slider seekbar;
    DiffreView diffreViewApi1;
    DiffreView diffreViewApi19;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        seekbar = (Slider) findComponentById(ResourceTable.Id_seekBar);
        // NOTE Implementation was not completed
        diffreViewApi1 = (DiffreView) findComponentById(ResourceTable.Id_fillShapeViewApi1);
        // NOTE Implementation has not yet begun
        //diffreViewApi19 = (DiffreView) findComponentById(ResourceTable.Id_fillShapeViewApi19);

        if (diffreViewApi1 != null){
            float progress;
            if (seekbar != null)
                progress = seekbar.getProgress();
            else
                progress = 60F;
            diffreViewApi1.setProgress(progress / 100F);
        }
        if (diffreViewApi19 != null && seekbar != null){
            diffreViewApi19.setProgress(seekbar.getProgress() / 100F);
        }
        if (seekbar != null)
        {
            seekbar.setValueChangedListener(new Slider.ValueChangedListener() {
                @Override
                public void onProgressUpdated(Slider seekBar, int progress, boolean fromUser) {
                    diffreViewApi1.setProgress(progress / 100F);

                    if (diffreViewApi19 != null) {
                        diffreViewApi19.setProgress(progress / 100F);
                    }
                }

                @Override
                public void onTouchStart(Slider seekBar) {

                }

                @Override
                public void onTouchEnd(Slider seekBar) {

                }
            });
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
