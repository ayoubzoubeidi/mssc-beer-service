package com.maz.brewery.model.events;

import com.maz.brewery.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -4691435634242525808L;

    private BeerDto beerDto;
}
