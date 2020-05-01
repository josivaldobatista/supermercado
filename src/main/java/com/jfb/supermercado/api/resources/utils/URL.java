package com.jfb.supermercado.api.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Essa classe serve para converter a strings de números que virão da URL em um
 * List de inteiros.
 */

public class URL {

    public static String decoderParam(String s) {
        try {
            return URLDecoder.decode(s, "UTF8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static List<Integer> decoderIntList(String s) {
        String[] vet = s.split(",");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < vet.length; i++) {
            list.add(Integer.parseInt(vet[i]));
        }
        return list;
        // A linha abaixo faz a mesma coisa do codigo acima.
        // return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
    }
}