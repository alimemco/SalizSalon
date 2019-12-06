/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.alirnp.salizsalon.Test;


import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alirnp.salizsalon.MyApplication;
import com.alirnp.salizsalon.NestedJson.Item;
import com.alirnp.salizsalon.NestedJson.SalizResponse;
import com.alirnp.salizsalon.Utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExampleDataProviderFragment extends Fragment {
    private AbstractDataProvider mDataProvider;
    private Callback<SalizResponse> callback = new Callback<SalizResponse>() {
        @Override
        public void onResponse(Call<SalizResponse> call, Response<SalizResponse> response) {

            if (response.isSuccessful()) {
                if (response.body() != null) {
                    List<Item> items = response.body().getResult().get(0).getItems();

                    //  mDataProvider = new ExampleDataProvider(items);
                    //   setRetainInstance(true);

                } else {
                    //  adapter.setNotFound();
                }
            }
        }

        @Override
        public void onFailure(Call<SalizResponse> call, Throwable t) {
            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            // adapter.setNotFound();

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // keep the mDataProvider instance
        // mDataProvider = new ExampleDataProvider();


        getBanners();

        // mDataProvider = new ExampleDataProvider(list);

    }

    public AbstractDataProvider getDataProvider() {
        return mDataProvider;
    }

    private void getBanners() {

        MyApplication.getApi()
                .manage(Constants.BANNERS, Constants.TOKEN)
                .enqueue(callback);
    }

}
