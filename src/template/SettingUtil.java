package template;

import com.intellij.ide.util.PropertiesComponent;

public class SettingUtil {
    static String
            key_integer = "key_integer",
            key_bool = "key_bool",
            key_string = "key_string",
            key_array = "key_array",
            key_object = "key_object";

    public static void storeSetting() {
        PropertiesComponent.getInstance().setValue(key_integer, ModelUtil.defaultValue.integer);
        PropertiesComponent.getInstance().setValue(key_bool, ModelUtil.defaultValue.bool);
        PropertiesComponent.getInstance().setValue(key_string, ModelUtil.defaultValue.string);
        PropertiesComponent.getInstance().setValue(key_array, ModelUtil.defaultValue.array);
        PropertiesComponent.getInstance().setValue(key_object, ModelUtil.defaultValue.object);
    }

    public static void fetchSetting() {
        ModelUtil.defaultValue.integer = PropertiesComponent.getInstance().getValue(key_integer, "0");
        ModelUtil.defaultValue.bool = PropertiesComponent.getInstance().getValue(key_bool, "false");
        ModelUtil.defaultValue.string = PropertiesComponent.getInstance().getValue(key_string, "''");
        ModelUtil.defaultValue.array = PropertiesComponent.getInstance().getValue(key_array, "[]");
        ModelUtil.defaultValue.object = PropertiesComponent.getInstance().getValue(key_object, "null");
    }
}
