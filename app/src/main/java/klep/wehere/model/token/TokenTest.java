package klep.wehere.model.token;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by klep.io on 10.03.16.
 */
public class TokenTest implements JsonDeserializer<TokenTest> {


    @Override
    public TokenTest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Log.d("aaaaaaaaaa",json.getAsString());
        return null;
    }
}
