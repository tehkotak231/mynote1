package com.ipin.mynote.Model;

// 10120164, M HASBI FADILAH, IF4

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

public class CatatanId {
    @Exclude
    public String CatatanId;

    public <T extends CatatanId> T withId(@NonNull final String id){
        this.CatatanId = id;
        return (T) this;
    }

}
