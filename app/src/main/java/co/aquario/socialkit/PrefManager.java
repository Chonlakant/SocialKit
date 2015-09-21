package co.aquario.socialkit;

import android.content.SharedPreferences;

import com.tale.prettysharedpreferences.BooleanEditor;
import com.tale.prettysharedpreferences.DoubleEditor;
import com.tale.prettysharedpreferences.FloatEditor;
import com.tale.prettysharedpreferences.IntegerEditor;
import com.tale.prettysharedpreferences.LongEditor;
import com.tale.prettysharedpreferences.PrettySharedPreferences;
import com.tale.prettysharedpreferences.StringEditor;

/**
 * Created by TALE on 10/28/2014.
 */
public class PrefManager extends PrettySharedPreferences<PrefManager> {

    public PrefManager(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    public StringEditor<PrefManager> userName() {
        return getStringEditor("userName");
    }
    public StringEditor<PrefManager> passWord() {
        return getStringEditor("passWord");
    }
    public StringEditor<PrefManager> confirmpassWord() {
        return getStringEditor("confirmpassWord");
    }
    public BooleanEditor<PrefManager> isLogin() {
        return getBooleanEditor("isLogin");
    }

    public StringEditor<PrefManager> string(String key) {
        return getStringEditor(key);
    }
}
