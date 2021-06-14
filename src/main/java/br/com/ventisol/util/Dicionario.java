package br.com.ventisol.util;

import br.com.ventisol.hub2b.model.anuncio.AnuncioPreco;
import br.com.ventisol.hub2b.model.categoria.Categoria;
import br.com.ventisol.hub2b.model.estoque.Estoque;
import br.com.ventisol.hub2b.model.pedido.Pedido;
import br.com.ventisol.hub2b.model.pedido.PedidoFaturar;
import br.com.ventisol.hub2b.model.pedido.PedidoProduto;
import br.com.ventisol.hub2b.model.produto.Produto;
import br.com.ventisol.hub2b.model.produto.ProdutoEspecificacoes;
import br.com.ventisol.hub2b.model.produto.Produtos;
import br.com.ventisol.sankhya.model.vo.SankhyaEstoque;
import br.com.ventisol.sankhya.model.vo.SankhyaAnuncio;
import br.com.ventisol.sankhya.model.vo.SankhyaCategoria;
import br.com.ventisol.sankhya.model.vo.SankhyaCliente;
import br.com.ventisol.sankhya.model.vo.SankhyaEspecificacao;
import br.com.ventisol.sankhya.model.vo.SankhyaFaturar;
import br.com.ventisol.sankhya.model.vo.SankhyaItem;
import br.com.ventisol.sankhya.model.vo.SankhyaPedido;
import br.com.ventisol.sankhya.model.vo.SankhyaProduto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Dicionario {

    public static class ConverterCategoria {

        public static Categoria sankhyaParaHub(SankhyaCategoria sankhyaCategoria) {
            try {
                Categoria categoria = new Categoria();
                categoria.setCode(sankhyaCategoria.getCodHub());
                categoria.setName(sankhyaCategoria.getNome());
                categoria.setParentCode(sankhyaCategoria.getCodHubPai());
                return categoria;
            } catch (Exception e) {
                Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao Converter Categoria para Hub!", Logs.COLOR_ERRO);
            }
            return null;
        }

        public static SankhyaCategoria hubParaSankhya(Categoria categoria) {
            try {
                SankhyaCategoria sankhyaCategoria = new SankhyaCategoria();
                sankhyaCategoria.setCodHub(categoria.getCode());
                sankhyaCategoria.setNome(categoria.getName());
                sankhyaCategoria.setCodHubPai(categoria.getParentCode());
                return sankhyaCategoria;
            } catch (Exception e) {
                Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao Converter Categoria para Hub!", Logs.COLOR_ERRO);
            }
            return null;
        }
    }

    public static class ConverterProduto {

        public static Produto sankhyaParaHub(SankhyaProduto sankhyaProduto) {
            try {
                Produto produto = new Produto();
                produto.setBrand(sankhyaProduto.getMarca().getNome());
                produto.setCategory(sankhyaProduto.getCategoria().getNome());
                produto.setDescription(sankhyaProduto.getDescricaoLonga());
                produto.setSourceDescription(sankhyaProduto.getDescricaoLonga());
                produto.setDimensionUnit(sankhyaProduto.getUnidade());
                produto.setEan13(sankhyaProduto.getEan());
                produto.setHeight_m(sankhyaProduto.getAltura());
                produto.setLength_m(sankhyaProduto.getProfundidade());
                produto.setWidth_m(sankhyaProduto.getLargura());
                produto.setVideoURL(sankhyaProduto.getVideo());
                produto.setIdTypeCondition(1); // 1- Significa que é produto novos!
                produto.setName(sankhyaProduto.getDescricao());
                produto.setNcm(sankhyaProduto.getNcm().toString());
//                produto.setPriceBase(sankhyaProduto.getPreco());
//                produto.setPriceSale(sankhyaProduto.getPreco());
                produto.setProductType(sankhyaProduto.getTipoProduto());
                produto.setSalesChannel(sankhyaProduto.getSalesChannel());
                produto.setSku(sankhyaProduto.getSku());
                produto.setParentSKU(sankhyaProduto.getProdutoPai().getSku());
                produto.setSourceId(sankhyaProduto.getSkuLojista());
                produto.setStatus(sankhyaProduto.getStatusIntegracao());
                produto.setStatusMessage(sankhyaProduto.getRetorno());
                produto.setStock(sankhyaProduto.getEstoque());
                produto.setVideoURL(sankhyaProduto.getVideo());
                produto.setWarrantyMonths(sankhyaProduto.getGarantia());
                produto.setWeightKg(sankhyaProduto.getPeso());
//                List<ProdutoImagem> produtosImagens = new ArrayList<>();
//                for (SankhyaImagem sankhyaImagem : sankhyaProduto.getImages()) {
//                    ProdutoImagem produtoImagem = new ProdutoImagem();
//                    produtoImagem.setUrl(sankhyaImagem.getUrl());
//                    produtosImagens.add(produtoImagem);
//                }
//                produto.setImage(produtosImagens);
//                produto.setProductURL(sankhyaProduto.getVideo());
//                produto.setHandlingTime(handlingTime);
//                produto.setMarketplaceId(marketplaceId);
//                produto.setMarketplaceSKU(marketplaceSKU);
                if(sankhyaProduto.getEstoque() != null){
                    produto.setStock(sankhyaProduto.getEstoque());
                }else{
                    produto.setStock(0);
                }                
                if(sankhyaProduto.getEspecificacoes() != null){
                    produto.setSpecifications(ConverterEspecificacoes.sankhyaParaHub(sankhyaProduto.getEspecificacoes()));
                }
                return produto;
            } catch (Exception e) {
                Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao Produto " + sankhyaProduto.getSku() + " para Hub!(" + e. getMessage() + ")", Logs.COLOR_ERRO);
            }
            return null;
        }

        public static Produtos sankhyaParaHub(List<SankhyaProduto> sankhyaProdutos) {
            Produtos produtos = new Produtos();
            List<Produto> produto = new ArrayList<>();
            for (SankhyaProduto sankhyaProduto : sankhyaProdutos) {
                produto.add(sankhyaParaHub(sankhyaProduto));
            }
            produtos.getData().setList(produto);
            return produtos;
        }

        public static SankhyaProduto hubParaSankhya(Produto produto) {
            SankhyaProduto sankhyaProduto = new SankhyaProduto();
            sankhyaProduto.getMarca().setNome(produto.getBrand());
            sankhyaProduto.getCategoria().setCodHub(produto.getCategory());
            sankhyaProduto.setDescricaoLonga(produto.getDescription());
            sankhyaProduto.setUnidade(produto.getDimensionUnit());
            sankhyaProduto.setAltura(produto.getHeight());
            sankhyaProduto.setProfundidade(produto.getLength());
            sankhyaProduto.setLargura(produto.getWidth());
            sankhyaProduto.setVideo(produto.getVideoURL());
            sankhyaProduto.setDescricao(produto.getName());
            sankhyaProduto.setPreco(produto.getPriceBase()); // DESNECESSÁRIO!
            sankhyaProduto.setPreco(produto.getPriceSale());
            sankhyaProduto.setTipoProduto(produto.getProductType());
//                sankhyaProduto.setProductURL(produto.getVideo());
            sankhyaProduto.setSalesChannel(produto.getSalesChannel());
            sankhyaProduto.setSku(produto.getSku());
            sankhyaProduto.getProdutoPai().setSku(produto.getParentSKU());
//            sankhyaProduto.setSkuLojista(produto.getMarketplaceSKU());
            sankhyaProduto.setStatusIntegracao(produto.getStatus());
            sankhyaProduto.setRetorno(produto.getStatusMessage());
            sankhyaProduto.setEstoque(produto.getStock());
            sankhyaProduto.setGarantia(produto.getWarrantyMonths());
            sankhyaProduto.setPeso(produto.getWeightKg());
            if (produto.getStatus().equals("Synchronized")) {
                sankhyaProduto.setCodStatus(3);
            } else {
                sankhyaProduto.setCodStatus(1);
            }
//            List<ProdutoImagem> produtosImagens = new ArrayList<>();
//            for (SankhyaImagem sankhyaImagem : sankhyaProduto.getImages()) {
//                ProdutoImagem produtoImagem = new ProdutoImagem();
//                produtoImagem.setUrl(sankhyaImagem.getUrl());
//                produtosImagens.add(produtoImagem);
//            }
//        produto.setImage(produtosImagens);
            sankhyaProduto.setEspecificacoes((ConverterEspecificacoes.hubParaSankhya(produto.getSpecifications())));
            return sankhyaProduto;
        }

    }

    public static class ConverterEspecificacoes {

        public static ProdutoEspecificacoes sankhyaParaHub(SankhyaEspecificacao sankhyaEspecificacao) {
            ProdutoEspecificacoes produtoEspecificacoes = new ProdutoEspecificacoes();
            produtoEspecificacoes.setName(sankhyaEspecificacao.getNome());
            produtoEspecificacoes.setType(sankhyaEspecificacao.getTipo());
            produtoEspecificacoes.setValue(sankhyaEspecificacao.getValor());
            return produtoEspecificacoes;
        }

        public static List<ProdutoEspecificacoes> sankhyaParaHub(List<SankhyaEspecificacao> sankhyaEspecificacaos) {
            List<ProdutoEspecificacoes> produtosEspecificacoes = new ArrayList<>();
            for (SankhyaEspecificacao sankhyaEspecificao : sankhyaEspecificacaos) {
                produtosEspecificacoes.add(sankhyaParaHub(sankhyaEspecificao));
            }
            return produtosEspecificacoes;
        }

        private static List<SankhyaEspecificacao> hubParaSankhya(List<ProdutoEspecificacoes> specifications) {
            List<SankhyaEspecificacao> sankhyaEspecificacaos = new ArrayList<>();
            for (ProdutoEspecificacoes produtoEspecificacoes : specifications) {
                sankhyaEspecificacaos.add(hubParaSankhya(produtoEspecificacoes));
            }
            return sankhyaEspecificacaos;
        }

        public static SankhyaEspecificacao hubParaSankhya(ProdutoEspecificacoes specifications) {
            SankhyaEspecificacao sankhyaEspecificacao = new SankhyaEspecificacao();
            sankhyaEspecificacao.setNome(specifications.getName());
            sankhyaEspecificacao.setTipo(specifications.getType());
            sankhyaEspecificacao.setValor(specifications.getValue());
            return sankhyaEspecificacao;
        }
    }

    public static class ConverterAnuncio {

        public static class Preco {

            public static AnuncioPreco sankhyaParaHub(SankhyaAnuncio sankhyaAnuncio) {
                AnuncioPreco anuncioPreco = new AnuncioPreco();
                try {
                    anuncioPreco.setItemId(sankhyaAnuncio.getSku());
                    anuncioPreco.setListPrice(sankhyaAnuncio.getVlrPrecoAnterior());
                    anuncioPreco.setPrice(sankhyaAnuncio.getVlrPreco());
                    anuncioPreco.setSalesChannel(sankhyaAnuncio.getCanalDeVenda());
                    anuncioPreco.setValidFrom(Converter.converterDataParaInstant(sankhyaAnuncio.getDtInicial()));
                    anuncioPreco.setValidTo(Converter.converterDataParaInstant(sankhyaAnuncio.getDtFinal()));
                    return anuncioPreco;
                } catch (Exception e) {
                    Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao Converter Anuncio para Preço Hub! " + anuncioPreco.toString(), Logs.COLOR_ERRO);
                }
                return null;
            }

        }
    }

    public static class ConverterPedido {

        public static SankhyaPedido hubParaSankhya(Pedido pedidoHub, BigDecimal codigoDoCliente) {
            try {

                SankhyaPedido sankhyaPedido = new SankhyaPedido();
                sankhyaPedido.setPedidoId(pedidoHub.getReference().getId());
                sankhyaPedido.setDataVenda(pedidoHub.getPayment().getPurchaseDate());
                sankhyaPedido.setValorTotal(pedidoHub.getPayment().getTotalAmount());
                sankhyaPedido.setPagamentoValor(pedidoHub.getPayment().getTotalAmountPlusShipping());
                sankhyaPedido.setValorFrete(pedidoHub.getShipping().getPrice());
                sankhyaPedido.setValorDesconto(pedidoHub.getPayment().getTotalDiscount());
                sankhyaPedido.setObservacoes(pedidoHub.getPayment().getAddress().getAdditionalInfo());
                sankhyaPedido.setObservacoesLoja(pedidoHub.getShipping().getAddress().getAdditionalInfo());
                sankhyaPedido.setStatusId(pedidoHub.getStatus().getStatus());
                sankhyaPedido.setOrigemPedido(pedidoHub.getReference().getStore());
                if (pedidoHub.getReference().getVirtual() == null) {
                    sankhyaPedido.setCodigoPedidoExterno(pedidoHub.getReference().getSource());
                } else {
                    sankhyaPedido.setCodigoPedidoExterno(pedidoHub.getReference().getVirtual());
                    sankhyaPedido.setCarrinho(pedidoHub.getReference().getSource());
                }

                sankhyaPedido.setNumCli(codigoDoCliente);
                sankhyaPedido.setFreteCodEnvio(null);
                sankhyaPedido.setFormaPagamentoId(pedidoHub.getPayment().getMethod());
                sankhyaPedido.setFormaEntregaId(null);
                sankhyaPedido.setDtPrevisao(pedidoHub.getShipping().getEstimatedDeliveryDate());
                sankhyaPedido.setProtocolo(null);
                sankhyaPedido.setEntregaNome(pedidoHub.getCustomer().getName());
                sankhyaPedido.setEntregaDocumento(pedidoHub.getCustomer().getDocumentNumber());
                sankhyaPedido.setEntregaEmail(pedidoHub.getCustomer().getEmail());
                sankhyaPedido.setEntregaTelefone(pedidoHub.getCustomer().getTelephone());
                sankhyaPedido.setEntregaComplementoEndereco(pedidoHub.getShipping().getAddress().getStreet());
                sankhyaPedido.setEntregaNumero(pedidoHub.getShipping().getAddress().getNumber());
                sankhyaPedido.setEntregaLogradouro(pedidoHub.getShipping().getAddress().getAddress());
                sankhyaPedido.setEntregaBairro(pedidoHub.getShipping().getAddress().getNeighborhood());
                sankhyaPedido.setEntregaCidade(pedidoHub.getShipping().getAddress().getCity());
                sankhyaPedido.setEntregaEstado(pedidoHub.getShipping().getAddress().getState());
                sankhyaPedido.setEntregaPais(pedidoHub.getShipping().getAddress().getCountry());
                sankhyaPedido.setEntregaCep(pedidoHub.getShipping().getAddress().getZipCode());
                sankhyaPedido.setFormaEntregaAlias(pedidoHub.getShipping().getService());
                sankhyaPedido.setAtivo("S");
                sankhyaPedido.setProcessado("N");
                sankhyaPedido.setCartaoQuantidadeParcelas(pedidoHub.getPayment().getInstallments());
                sankhyaPedido.setFormaDePagamentoAlias(pedidoHub.getPayment().getMethod());
                return sankhyaPedido;
            } catch (Exception e) {
                Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao Converter Pedido Hub para o Sankhya!", Logs.COLOR_ERRO);
            }
            return null;
        }
    }

    public static class ConverterFaturamento {

        public static PedidoFaturar sankhyaParaHub(SankhyaFaturar sankhyaFaturar) {
            try {
                PedidoFaturar pedidoFaturar = new PedidoFaturar();
                pedidoFaturar.setSeries(sankhyaFaturar.getSerieNota());
                pedidoFaturar.setKey(sankhyaFaturar.getChaveNfe());
                pedidoFaturar.setNumber(sankhyaFaturar.getNumProtoc());
                pedidoFaturar.setTotalAmount(sankhyaFaturar.getVlrNota());
                pedidoFaturar.setIssueDate(Converter.converterDataParaInstant(sankhyaFaturar.getDtFatur()));
                return pedidoFaturar;
            } catch (Exception e) {
                Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao Converter Faturamento do Sankhya para o Hub!", Logs.COLOR_ERRO);
            }

            return null;
        }

    }

    public static class ConverterCliente {

        public static SankhyaCliente hubParaSankhya(Pedido pedidohub) {
            try {
                SankhyaCliente sankhyaCliente = new SankhyaCliente();
                sankhyaCliente.setClienteId(null);
                sankhyaCliente.setNome(pedidohub.getCustomer().getName());
                sankhyaCliente.setEmail(pedidohub.getCustomer().getEmail());
                sankhyaCliente.setCpf(pedidohub.getCustomer().getDocumentNumber());
                sankhyaCliente.setRg(null);
                sankhyaCliente.setBairro(pedidohub.getShipping().getAddress().getNeighborhood());
                sankhyaCliente.setLogradouro(pedidohub.getShipping().getAddress().getAddress());
                sankhyaCliente.setNumero(pedidohub.getShipping().getAddress().getNumber());
                sankhyaCliente.setComplemento(pedidohub.getShipping().getAddress().getAdditionalInfo());
                sankhyaCliente.setCep(pedidohub.getShipping().getAddress().getZipCode());
                sankhyaCliente.setCidade(pedidohub.getShipping().getAddress().getCity());
                sankhyaCliente.setEstado(pedidohub.getShipping().getAddress().getState());
                sankhyaCliente.setPais(pedidohub.getShipping().getAddress().getCountry());

                if (pedidohub.getCustomer().getTelephone() != null) {
                    sankhyaCliente.setTelefone(pedidohub.getCustomer().getTelephone());
                } else {
                    sankhyaCliente.setTelefone(pedidohub.getCustomer().getMobileNumber());
                }
                if (sankhyaCliente.getCpf().length() > 11) {
                    sankhyaCliente.setTipoCliente("J");
                } else {
                    sankhyaCliente.setTipoCliente("F");
                }

                sankhyaCliente.setAtivo("S");
                sankhyaCliente.setProcessado("N");
                return sankhyaCliente;
            } catch (Exception e) {
                Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao Converter Cliente do Hub para Parceiro no Sankhya!", Logs.COLOR_ERRO);
            }
            return null;
        }

    }

    public static class ConverterItem {

        public static List<SankhyaItem> hubParaSankhya(Pedido pedidohub, BigDecimal numPed) {
            try {
                List<SankhyaItem> sankhyaItems = new ArrayList<>();
                for (PedidoProduto produto : pedidohub.getProducts()) {
                    SankhyaItem sankhyaItem = new SankhyaItem();
                    sankhyaItem.setNumPed(numPed);
                    sankhyaItem.setPrecoUnitario(produto.getPrice());
                    sankhyaItem.setPrecoBase(produto.getPrice());
                    sankhyaItem.setQuantidade(produto.getQuantity());
                    sankhyaItem.setDescontoItem(produto.getDiscount());
                    sankhyaItem.setReferencia(produto.getSku());
                    sankhyaItems.add(sankhyaItem);
                }
                return sankhyaItems;
            } catch (Exception e) {
                Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao Converter Item do Hub para o Pedido do Sankhya!", Logs.COLOR_ERRO);
            }
            return null;
        }

    }

    public static class ConverterEstoque {

        public static Estoque sankhyaParaHub(SankhyaEstoque sankhyaEstoque) {
            try {
                Estoque estoque = new Estoque();
                estoque.setCrossDocking(0);
                estoque.setItemId(sankhyaEstoque.getSku());
                estoque.setQuantity(sankhyaEstoque.getEstoque());
                estoque.setWareHouseId(null);
                return estoque;
            } catch (Exception e) {
                Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao Converter Movimentacao para Estoque Hub!", Logs.COLOR_ERRO);
            }
            return null;
        }

    }
}
