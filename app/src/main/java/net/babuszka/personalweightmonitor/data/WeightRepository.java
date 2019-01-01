package net.babuszka.personalweightmonitor.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import net.babuszka.personalweightmonitor.data.db.WeightDao;
import net.babuszka.personalweightmonitor.data.db.WeightDatabase;
import net.babuszka.personalweightmonitor.data.model.Weight;

import java.util.List;


public class WeightRepository {

    private WeightDao weightDao;
    private LiveData<List<Weight>> allWeight;

    public WeightRepository(Application application) {
        WeightDatabase database = WeightDatabase.getInstance(application);
        weightDao = database.weigthDao();
        allWeight = weightDao.getAllWeight();
    }

    public void insert(Weight weight) {
        new InsertWeightAsyncTask(weightDao).execute(weight);
    }

    public void update(Weight weight) {
        new UpdateWeightAsyncTask(weightDao).execute(weight);
    }

    public void delete(Weight weight) {
        new DeleteWeightAsyncTask(weightDao).execute(weight);
    }

    public LiveData<List<Weight>> getAllWeight() {
        return allWeight;
    }




    private static class InsertWeightAsyncTask extends AsyncTask<Weight, Void, Void> {

        private WeightDao weightDao;

        private InsertWeightAsyncTask(WeightDao weightDao) {
            this.weightDao = weightDao;
        }

        @Override
        protected Void doInBackground(Weight... weights) {
            weightDao.insert(weights[0]);
            return null;
        }
    }

    private static class UpdateWeightAsyncTask extends AsyncTask<Weight, Void, Void> {

        private WeightDao weightDao;

        private UpdateWeightAsyncTask(WeightDao weightDao) {
            this.weightDao = weightDao;
        }

        @Override
        protected Void doInBackground(Weight... weights) {
            weightDao.update(weights[0]);
            return null;
        }
    }

    private static class DeleteWeightAsyncTask extends AsyncTask<Weight, Void, Void> {

        private WeightDao weightDao;

        private DeleteWeightAsyncTask(WeightDao weightDao) {
            this.weightDao = weightDao;
        }

        @Override
        protected Void doInBackground(Weight... weights) {
            weightDao.delete(weights[0]);
            return null;
        }
    }

}
