package ir.proglovving.fitapp.data_models;

import android.os.SystemClock;

import java.util.List;
import java.util.Random;

public class TipsRequestModel {
    // TODO: 11/11/20 modifying api and deleting this redundant codes
    private List<Tip> results;

    public List<Tip> getResults() {
        return results;
    }

    public Tip getTip(){
        return results.get(new Random(SystemClock.currentThreadTimeMillis()).nextInt(results.size()));
    }
}
