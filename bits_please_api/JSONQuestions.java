package bits_please_api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONQuestions {
    private final JSONObject jsonObject;

    public JSONQuestions(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public List<String> getQuestionUUIDs() throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("question_uuids");
        return jsonArrayToList(jsonArray);
    }

    public List<String> getQuestions() throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("questions");
        return jsonArrayToList(jsonArray);
    }

    public List<String> getAnswers() throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("answers");
        return jsonArrayToList(jsonArray);
    }

    public List<String> getCategories() throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("categories");
        return jsonArrayToList(jsonArray);
    }

    public List<String> getFourthMultipleChoiceAnswers() throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("choice4s");
        return jsonArrayToList(jsonArray);
    }

    public List<String> getThirdMultipleChoiceAnswers() throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("choice3s");
        return jsonArrayToList(jsonArray);
    }

    public List<String> getSecondMultipleChoiceAnswers() throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("choice2s");
        return jsonArrayToList(jsonArray);
    }

    public List<String> getFirstMultipleChoiceAnswers() throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("choice1s");
        return jsonArrayToList(jsonArray);
    }

    public List<Object> getDifficulties() throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("difficulties");
        return jsonArray.toList();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        List<String> questions = getQuestions();
        sb.append("[\n");
        for (int i = 0; i < questions.size(); i++){
            sb.append("\t\""+questions.get(i)+"\"\n");
        }
        sb.append("]");
        return sb.toString();
    }

    public int getSize(){
        return getQuestionUUIDs().size();
    }

    private List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
