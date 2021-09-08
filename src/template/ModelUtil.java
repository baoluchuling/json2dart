package template;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import java.util.*;

class Default {
    public String integer = "0";
    public String string = "''";
    public String bool = "false";
    public String array = "[]";
    public String object = "null";
}

public class ModelUtil {

    public static Default defaultValue = new Default();

    public static String toClassString(String classname, String json) {
        return toModel(reprocess(classname, process(classname, (JSONObject) JSONValue.parse(json))));
    }

    private static String toModel(Map<String, Map<String, Map<String, String>>> json) {
        StringBuilder string = new StringBuilder();

        for (String className : json.keySet()) {
            string.append("class ").append(className).append(" {\n");
            Map<String, Map<String, String>> value = json.get(className);
            Iterator<String> var5 = value.keySet().iterator();

            String variableName;
            Map<String, String> variableType;
            String type;
            String clas;
            while (var5.hasNext()) {
                variableName = var5.next();
                variableType = value.get(variableName);
                type = variableType.get("type");
                clas = variableType.get("name");
                if (type.equals("array")) {
                    string.append("    ").append("List<").append(clas).append(">").append("? ").append(variableName).append(";\n");
                } else {
                    string.append("    ").append(clas).append("? ").append(variableName).append(";\n");
                }
            }

            string.append("\n");
            string.append("    ").append(className).append(".fromJson(Map<String, dynamic> json) {\n");
            var5 = value.keySet().iterator();

            while (var5.hasNext()) {
                variableName = var5.next();
                variableType = value.get(variableName);
                type = variableType.get("type");
                clas = variableType.get("name");
                if (type.equals("array")) {
                    string
                            .append("        ")
                            .append(variableName)
                            .append(" = ")
                            .append("json['")
                            .append(variableName)
                            .append("'] == null ? ")
                            .append(ModelUtil.defaultValue.array)
                            .append(" : ")
                            .append("List<")
                            .append(clas)
                            .append(">.from((json['")
                            .append(variableName)
                            .append("']).map((e) => ")
                            .append(clas)
                            .append(".fromJson(e)).toList());\n");
                } else if (type.equals("map")) {
                    string
                            .append("        ")
                            .append(variableName)
                            .append(" = ")
                            .append("json['")
                            .append(variableName)
                            .append("'] == null ? ")
                            .append(ModelUtil.defaultValue.object)
                            .append(" : ")
                            .append(clas)
                            .append(".fromJson(json['")
                            .append(variableName)
                            .append("']")
                            .append(");\n");
                } else if (type.equals("int")) {
                    string
                            .append("        ")
                            .append(variableName)
                            .append(" = ")
                            .append("json['")
                            .append(variableName)
                            .append("'] == null ? ")
                            .append(ModelUtil.defaultValue.integer)
                            .append(" : ")
                            .append("json['")
                            .append(variableName)
                            .append("'];\n");
                } else if (type.equals("string")) {
                    string
                            .append("        ")
                            .append(variableName)
                            .append(" = ")
                            .append("json['")
                            .append(variableName)
                            .append("'] == null ? ")
                            .append(ModelUtil.defaultValue.string)
                            .append(" : ")
                            .append("json['")
                            .append(variableName)
                            .append("'];\n");
                } else if (type.equals("bool")) {
                    string
                            .append("        ")
                            .append(variableName)
                            .append(" = ")
                            .append("json['")
                            .append(variableName)
                            .append("'] == null ? ")
                            .append(ModelUtil.defaultValue.bool)
                            .append(" : ")
                            .append("json['")
                            .append(variableName)
                            .append("'];\n");
                }
            }

            string.append("    ").append("}\n");
            string.append("}\n");
            string.append("\n");
        }

        return string.toString();
    }

    private static Map<String, Map<String, Map<String, String>>> process(String modelName, JSONObject object) {
        Map<String, Map<String, Map<String, String>>> res = new LinkedHashMap<>();
        Map<String, Map<String, String>> tmpMap = new LinkedHashMap<>();

        for (String key : object.keySet()) {
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                Map<String, String> tm = new LinkedHashMap<>();
                tm.put("type", "array");
                tm.put("name", capture(key));
                tmpMap.put(key, tm);
                res.putAll(process(capture(key), (JSONObject) ((JSONArray) value).get(0)));
            } else if (value instanceof JSONObject) {
                Map<String, String> tm = new LinkedHashMap<>();
                tm.put("type", "map");
                tm.put("name", capture(key));
                tmpMap.put(key, tm);
                res.putAll(process(capture(key), (JSONObject) value));
            } else if (value instanceof Integer) {
                Map<String, String> tm = new LinkedHashMap<>();
                tm.put("type", "int");
                tm.put("name", "int");
                tmpMap.put(key, tm);
            } else if (value instanceof String) {
                Map<String, String> tm = new LinkedHashMap<>();
                tm.put("type", "string");
                tm.put("name", "String");
                tmpMap.put(key, tm);
            } else if (value instanceof Boolean) {
                Map<String, String> tm = new LinkedHashMap<>();
                tm.put("type", "bool");
                tm.put("name", "bool");
                tmpMap.put(key, tm);
            }
        }

        res.put(modelName, tmpMap);
        return res;
    }

    private static Map<String, Map<String, Map<String, String>>> reprocess(String modelName, Map<String, Map<String, Map<String, String>>> res) {
        Map<String, String> willRemove = new LinkedHashMap<>();

        for (String key1 : res.keySet()) {
            if (willRemove.containsKey(key1)) {
                continue;
            }

            for (String key2 : res.keySet()) {
                if (willRemove.containsKey(key2)) {
                    continue;
                }

                if (!key1.equals(key2)) {
                    boolean isEqual = Arrays.equals((res.get(key1)).keySet().toArray(), (res.get(key2)).keySet().toArray());
                    if (isEqual) {
                        willRemove.put(key2, key1);
                    }
                }
            }
        }

        res.entrySet().removeIf((e) -> willRemove.containsKey(e.getKey()));

        for (String key3 : res.keySet()) {
            Map<String, Map<String, String>> value3 = res.get(key3);

            for (String key4 : value3.keySet()) {
                Map<String, String> value4 = value3.get(key4);
                String value4Class = value4.get("name");
                if (willRemove.containsKey(value4Class)) {
                    value4.replace("name", willRemove.get(value4Class));
                }
            }
        }

        Map<String, Map<String, Map<String, String>>> reres = new LinkedHashMap<>();
        reres.put(modelName, res.remove(modelName));
        reres.putAll(res);

        return reres;
    }

    private static String capture(String t) {
        char[] c = t.toCharArray();
        c[0] = (char)(c[0] - 32);
        return String.valueOf(c);
    }
}
