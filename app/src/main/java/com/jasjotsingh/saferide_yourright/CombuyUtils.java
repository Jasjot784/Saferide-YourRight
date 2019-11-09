package com.jasjotsingh.saferide_yourright;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CombuyUtils {

    public static List<CombuyLocal> obtenerCercanos(Location ubicacionActual, int nlocales){

        List<CombuyLocal> lista=new CombuyRetrofit().getListLocal();

        Log.v("UTILS","ENTRA ACA?");
        int count=0;

        Location aux= new Location("");

        List<CombuyLocal> retorno = new ArrayList<CombuyLocal>();


        for(CombuyLocal i: lista){
            Log.v("UTILS","ASIGNANDO DISTANCIA A LOCALES");
            aux.setLatitude(i.getLatitud());
            aux.setLongitude(i.getLongitud());
            i.setDistancia(ubicacionActual.distanceTo(aux));
            aux.reset();
        }
        while(nlocales>count){
            Log.v("UTILS","OBTENIENDO N LOCALES MAS CERCANOS");
            retorno.add(obtenerMasCercano(lista));
            lista.remove(obtenerMasCercano(lista));
            count++;
        }
        return retorno;
    }

    public static CombuyLocal obtenerMasCercano(List<CombuyLocal> locales){
        Log.v("CERCANO","OBTENIENDO LOCAL MAS CERCANO");
        CombuyLocal localCercano= locales.get(0);
        for(CombuyLocal p: locales){

            if(p.getDistancia()<localCercano.getDistancia()){
                localCercano=p;
            }
        }
        return localCercano;
    }


    public static String test(Location ubicacion){
        return ubicacion.toString();
    }

}
