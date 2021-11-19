package com.example.testapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MultiplicationService extends Service {
    //empty constructor provided by android
    public MultiplicationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // throw new UnsupportedOperationException("Not yet implemented");


        return myBinder;

    }

    MultiplyInterface.Stub myBinder = new MultiplyInterface.Stub() {
        @Override
        public int multiplyTwoValues(int first, int second) throws RemoteException {

            return first * second;

        }
    };

}



