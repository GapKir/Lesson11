package com.example.lesson11.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.lesson11.R;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SingersService {
    private List<Singer> singers = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public SingersService() {
        singers.add(Singers.taylorSwift);
        singers.add(Singers.sza);
        singers.add(Singers.beatles);
        singers.add(Singers.tateMcRae);
        singers.add(Singers.jungKook);
        singers.add(Singers.dojaCat);
        singers.add(Singers.morganWallen);
    }

    public List<Singer> getSingers(){
        return singers;
    }
}
