package com.jfb.supermercado.api.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfb.supermercado.api.domain.Cliente;
import com.jfb.supermercado.api.domain.ItemPedido;
import com.jfb.supermercado.api.domain.PagamentoComBoleto;
import com.jfb.supermercado.api.domain.Pedido;
import com.jfb.supermercado.api.domain.enums.EstadoPagamento;
import com.jfb.supermercado.api.repositories.ItemPedidoRepository;
import com.jfb.supermercado.api.repositories.PagamentoRepository;
import com.jfb.supermercado.api.repositories.PedidoRepository;
import com.jfb.supermercado.api.security.UserSS;
import com.jfb.supermercado.api.services.exceptions.AuthorizationException;
import com.jfb.supermercado.api.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private EmailService emailService;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objecto não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    /**
     * Obs.: No caso de um pagamento com boleto, vai precisar configurar a 
     * data de vencimento, e caso reais, seria gerado uma chamada a um webservice
     * (que gera um boleto). Então para fins didáticos, eu criei uma classe chamada
     * BoletoService para simular uma data de vencimento para 7 dias.
     */
    @Transactional
	public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if(obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for(ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        System.out.println(obj);
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }
    
    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);

	}

}