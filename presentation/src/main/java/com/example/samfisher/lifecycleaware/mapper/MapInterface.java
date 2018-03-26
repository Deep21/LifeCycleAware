package com.example.samfisher.lifecycleaware.mapper;

import java.util.List;

/**
 * Created by Samfisher on 25/01/2018.
 */

interface MapInterface<I, O> {

  List<O> mapToList(List<I> i);

  O mapTo(I i);

}
