package com.boomsya.storagechooser;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;

import com.boomsya.storagechooser.fragments.ChooserDialogFragment;
import com.boomsya.storagechooser.models.Config;


public class StorageChooser {

    public static Dialog dialog;

    public static Config sConfig;
    private Activity chooserActivity;

    public static OnSelectListener onSelectListener;

    public static final String NONE = "none";
    public static final String DIRECTORY_CHOOSER = "dir";
    public static final String FILE_PICKER = "file";

    /**
     * basic constructor of StorageChooser
     * @param config to use with dialog window addition
     */
    public StorageChooser(Activity activity, Config config) {
        setsConfig(config);
        setChooserActivity(activity);
    }

    /**
     * blank constructor of StorageChooser
     * no params used only for internal library use
     */
    public StorageChooser() {
    }

    public interface OnSelectListener {

        void onSelect(String path);
    }

    /**
     * show() shows the storage chooser dialog
     */
    public void show() {
        init();
    }

    /**
     * init() creates the storage chooser dialog
     */
    private void init() {
        dialog = getStorageChooserDialog(getChooserActivity());
        ChooserDialogFragment chooserDialogFragment = new ChooserDialogFragment();
        chooserDialogFragment.show(sConfig.getFragmentManager(),"storagechooser_dialog");
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        StorageChooser.onSelectListener = onSelectListener;
    }

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    public static Config getsConfig() {
        return sConfig;
    }

    public static void setsConfig(Config sConfig) {
        StorageChooser.sConfig = sConfig;
    }

    private Activity getChooserActivity() {
        return chooserActivity;
    }

    private void setChooserActivity(Activity chooserActivity) {
        this.chooserActivity = chooserActivity;
    }

    private static Dialog getStorageChooserDialog(Activity activity) {
        return new Dialog(activity, R.style.DialogTheme);
    }
    
    public static class Builder {

        private Activity mActivity;
        private boolean mActionSave = false;
        private boolean mShowMemoryBar = false;
        private boolean mShowFreeSpace = true;
        private boolean mAllowCustomPath = false;
        private boolean mAllowAddFolder = false;
        private boolean mShowHidden = false;
        private boolean mSkipOverview = false;
        private boolean mApplyMemoryThreshold = false;
        private String type;

        Config devConfig;

        public Builder() {
            devConfig =  new Config();
        }

        public Builder withActivity(Activity activity) {
            mActivity = activity;
            return this;
        }

        public Builder withFragmentManager(FragmentManager fragmentManager) {
            devConfig.setFragmentManager(fragmentManager);
            return this;
        }

        public Builder withMemoryBar(boolean memoryBarBoolean) {
            mShowMemoryBar = memoryBarBoolean;
            return this;
        }

        public Builder setShowFreeSpace(boolean showFreeSpaceBoolean) {
            mShowFreeSpace = showFreeSpaceBoolean;
            return this;
        }

        public Builder withPredefinedPath(String path) {
            devConfig.setPredefinedPath(path);
            return this;
        }

        public Builder applyMemoryThreshold(boolean applyThreshold) {
            mApplyMemoryThreshold = applyThreshold;
            return this;
        }

        public Builder withThreshold(int size, String suffix) {
            devConfig.setMemoryThreshold(size);
            devConfig.setThresholdSuffix(suffix);
            return this;
        }

        public Builder withPreference(SharedPreferences sharedPreferences) {
            devConfig.setPreference(sharedPreferences);
            return this;
        }

        public Builder actionSave(boolean save) {
            mActionSave = save;
            return this;
        }

        public Builder setDialogTitle(String title) {
            devConfig.setDialogTitle(title);
            return this;
        }

        public Builder setInternalStorageText(String storageNameText) {
            devConfig.setInternalStorageText(storageNameText);
            return this;
        }

        public Builder setCreateLabelText(String createLabelText) {
            devConfig.setCreateLabelText(createLabelText);
            return this;
        }

        public  Builder allowCustomPath(boolean allowCustomPath) {
            mAllowCustomPath = allowCustomPath;
            return this;
        }

        public Builder allowAddFolder(boolean addFolder) {
            mAllowAddFolder = addFolder;
            return this;
        }

        public Builder showHidden(boolean showHiddenFolders) {
            mShowHidden = showHiddenFolders;
            return this;
        }

        public Builder setType(String action) {
            type = action;
            return this;
        }

        public Builder skipOverview(boolean skip, String primaryPath) {
            mSkipOverview = skip;
            devConfig.setPrimaryPath(primaryPath);
            return this;
        }

        public Builder skipOverview(boolean skip) {
            mSkipOverview = skip;

            return this;
        }

        public StorageChooser build() {
            devConfig.setActionSave(mActionSave);
            devConfig.setShowMemoryBar(mShowMemoryBar);
            devConfig.setAllowCustomPath(mAllowCustomPath);
            devConfig.setAllowAddFolder(mAllowAddFolder);
            devConfig.setShowHidden(mShowHidden);
            devConfig.setSkipOverview(mSkipOverview);
            devConfig.setApplyThreshold(mApplyMemoryThreshold);
            devConfig.setShowFreeSpace(mShowFreeSpace);

            if(type == null) {
                devConfig.setSecondaryAction(StorageChooser.NONE);
            } else {
                devConfig.setSecondaryAction(type);
            }

            return new StorageChooser(mActivity, devConfig);
        }
    }
}