package com.jfb.supermercado.api.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.jfb.supermercado.api.domain.PagamentoComBoleto;

/**
 * Essa classe serve apenas para fins didáticos pois em uma
 * implementação real eu teria que buscar a data de vencimento
 * pela chamada de um webservice que gera um boleto.
 */
@Service
public class BoletoService {

    public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(instanteDoPedido);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        pagto.setDataVencimento(cal.getTime());
    }
}