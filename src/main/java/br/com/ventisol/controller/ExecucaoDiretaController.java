package br.com.ventisol.controller;

import br.com.ventisol.hub2b.model.anuncio.AnuncioPreco;
import br.com.ventisol.hub2b.model.anuncio.AnuncioStatus;
import br.com.ventisol.hub2b.model.estoque.Estoque;
import br.com.ventisol.hub2b.model.login.LoginResponse;
import br.com.ventisol.hub2b.model.pedido.Pedido;
import br.com.ventisol.hub2b.model.pedido.PedidoStatus;
import br.com.ventisol.hub2b.model.pedido.PedidosResponse;
import br.com.ventisol.hub2b.model.pedido.PedidosVisaoGeral;
import br.com.ventisol.hub2b.model.produto.ProdutoData;
import br.com.ventisol.hub2b.model.produto.ProdutoList;
import br.com.ventisol.hub2b.model.produto.ProdutoSku;
import br.com.ventisol.hub2b.server.AnuncioServer;
import br.com.ventisol.hub2b.server.CategoriaServer;
import br.com.ventisol.hub2b.server.EstoqueServer;
import br.com.ventisol.hub2b.server.LoginServer;
import br.com.ventisol.hub2b.server.PedidoServer;
import br.com.ventisol.hub2b.server.ProdutosServer;
import br.com.ventisol.sankhya.model.dao.AnuncioDao;
import br.com.ventisol.sankhya.model.dao.CategoriaDao;
import br.com.ventisol.sankhya.model.dao.ClienteDao;
import br.com.ventisol.sankhya.model.dao.EstoqueDao;
import br.com.ventisol.sankhya.model.dao.PedidoDao;
import br.com.ventisol.sankhya.model.dao.ItemDao;
import br.com.ventisol.sankhya.model.dao.ProdutoDao;
import br.com.ventisol.sankhya.model.vo.SankhyaEstoque;
import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.sankhya.model.vo.SankhyaAnuncio;
import br.com.ventisol.sankhya.model.vo.SankhyaCategoria;
import br.com.ventisol.sankhya.model.vo.SankhyaCliente;
import br.com.ventisol.sankhya.model.vo.SankhyaFaturar;
import br.com.ventisol.sankhya.model.vo.SankhyaItem;
import br.com.ventisol.sankhya.model.vo.SankhyaPedido;
import br.com.ventisol.sankhya.model.vo.SankhyaProduto;
import br.com.ventisol.util.Dicionario;
import br.com.ventisol.util.Logs;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecucaoDiretaController {

    private Connection connection;
    private Long proximoLogin;
    private PedidosVisaoGeral pedidosVisaoGeral;
    private ProdutoData produtosComErros;
    private ProdutoData produtosPendentes;
    private Integer quantidadeDePedidosCancelados;
    private Integer tempPagina;
    private LoginResponse login;

    public ExecucaoDiretaController() {

    }

    public void executar() {
        try {
            this.criandoConexao();
            realizarLoginHubConta1();
            this.rotinas();
        } catch (SQLException ex) {
            Logger.getLogger(ExecucaoDiretaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Erro ao finalizar conexão!");
            }
        }
    }

    private void rotinas() throws SQLException {
        Logs.inicializarIntegracao();
        try {
            pedidosVisaoGeral = buscarVisaoGeral();
            exportarEstoque();
            exportarPedidosCancelados();
            IntegrarPedidosCancelados();
            exportarFaturamentoPendente();
            exportarFaturamentosPendentesManuais();
            exportarCategoriaNaoProcessados();
            exportarProdutosNaoProcessados();
//          exportarVariedadesProdutosNaoProcessados();
            exportarAnunciosNaoProcessados();
            importarAnunciosProcessados();
            if (pedidosVisaoGeral.getApproved() > 0) {
                // Usado uma conexão temporária para caso der erro, não entrar nenhum pedido, e assim alertar o E-commerce...
                Connection connectionTempPedidos = ConexaoOracle.ObterConexao();
                try {
                    integrarPedidos(connection);
                    connection.commit();
                } catch (SQLException sql) {
                    Logs.gerarLog(Logs.TITULO_ERRO, sql.getMessage(), Logs.COLOR_ERRO);
                    connectionTempPedidos.rollback();
                    connectionTempPedidos.close();
                }
            }
            integrarStatusDosProdutosComErro();
//            integrarStatusDosProdutosSicronizados();
            integrarSkuLojista();
        } catch (SQLException sql) {
            Logger.getLogger(CategoriaDao.class
                    .getName()).log(Level.SEVERE, null, sql);
            Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao Integrar: " + sql.getMessage(), Logs.COLOR_ERRO);
            if (connection != null) {
                connection.rollback();
                connection.close();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ExecucaoDiretaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Logs.finalizarIntegracao();
        }
    }

    public LoginResponse realizarLoginHubConta1() {
//        Logs.inicializarLog("Iniciando Login", "");
        Date data = new Date(System.currentTimeMillis());
        if (proximoLogin == null) {
            this.login = LoginServer.realizarLoginConta1();
            proximoLogin = data.getTime() + (login.getExpires_in() * 950);
        } else {
            if (data.getTime() >= proximoLogin) {
                this.login = LoginServer.realizarLoginConta1();
                proximoLogin = data.getTime() + (login.getExpires_in() * 950);
            } else {
//                Logs.gerarLog(Logs.TITULO_SUCESSO, "Login ainda está válido!", Logs.COLOR_SUCESSO);
            }
        }
//        Logs.finalizandoLog("Finalizando Login");
        return this.login;
    }


    private PedidosVisaoGeral buscarVisaoGeral() {
        Logs.inicializarLog("Buscando Visao Geral", "");
        pedidosVisaoGeral = PedidoServer.buscarVisaoGeral(realizarLoginHubConta1());
        Logs.gerarLog(Logs.TITULO_SUCESSO, pedidosVisaoGeral.toString(), Logs.COLOR_SUCESSO);
        Logs.finalizandoLog("Finalizando Visão Geral");
        return pedidosVisaoGeral;
    }

    private void exportarFaturamentoPendente() throws SQLException, InterruptedException {
        List<Thread> threads = new ArrayList<>();
        Logs.inicializarLog("Buscando Faturamentos Pendentes", "");
        try {
            PedidoDao pedidoDao = new PedidoDao(connection);
            List<SankhyaFaturar> faturamentos = pedidoDao.buscarFaturamentosPendentes();
            faturamentos.stream().map((sankhyaFaturar) -> new Thread() {
                @Override
                public void run() {
                    try {
                        if (PedidoServer.faturarNota(Dicionario.ConverterFaturamento.sankhyaParaHub(sankhyaFaturar), sankhyaFaturar.getPedidoId(), login)) {
                            pedidoDao.marcarComoFaturado(sankhyaFaturar);
                        }
                    } catch (SQLException ex) {
                        System.out.println("ERROR" + 2);
                    }
                }
            }).forEachOrdered((thread) -> {
                threads.add(thread);
            });
            threads.forEach((t) -> {
                try {
                    t.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExecucaoDiretaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                t.start();
            });
            //Espera que todas parem
            for (Thread t : threads) {
                t.join();
            }
            connection.commit();
            Logs.finalizandoLog("Finalizando Faturamentos Pendentes!");
        } catch (NumberFormatException e) {
            Logs.gerarLog(Logs.TITULO_ERRO, "Error: " + e.getMessage(), Logs.COLOR_ERRO);
        }
    }

    private void exportarFaturamentosPendentesManuais() throws SQLException, InterruptedException {
        Logs.inicializarLog("Buscando Faturamentos Manuais Pendentes", "");
        List<Thread> threads = new ArrayList<>();
        try {
            PedidoDao pedidoDao = new PedidoDao(connection);
            List<SankhyaFaturar> faturamentos = pedidoDao.buscarFaturamentosManuais();
            faturamentos.stream().map((sankhyaFaturar) -> new Thread() {
                @Override
                public void run() {
                    try {
                        if (PedidoServer.faturarNota(Dicionario.ConverterFaturamento.sankhyaParaHub(sankhyaFaturar), sankhyaFaturar.getPedidoId(), login)) {
                            pedidoDao.marcarManualComoFaturado(sankhyaFaturar);
                        }
                    } catch (SQLException ex) {
                        System.out.println("Erro Thread: ");
                    }
                }
            }).forEachOrdered((thread) -> {
                threads.add(thread);
            });
            threads.forEach((t) -> {
                try {
                    t.sleep(500);
                    t.start();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExecucaoDiretaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            //Espera que todas parem
            for (Thread t : threads) {
                t.join();
            }
            connection.commit();
            Logs.finalizandoLog("Finalizando Faturamentos Pendentes!");
        } catch (NumberFormatException e) {
            Logs.gerarLog(Logs.TITULO_ERRO, "Error: " + e.getMessage(), Logs.COLOR_ERRO);
        }
    }

    private void exportarProdutosNaoProcessados() throws SQLException {
        List<Thread> threads = new ArrayList<>();
        try {
            Logs.inicializarLog("Buscando Produtos Não Processados", "");
            ProdutoDao produtoDao = new ProdutoDao(connection);
            List<SankhyaProduto> sankhyaProdutos = produtoDao.buscarSankhyaProdutosNaoProcessados();
            Logs.gerarLog(Logs.TITULO_AVISO, "Todos os Produtos, foram Buscados com Sucesso!", Logs.COLOR_AVISO);
            sankhyaProdutos.forEach((sankhyaProduto) -> {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("NumPro: " + sankhyaProduto.getNumPro());
                            if (ProdutosServer.criarOuAtualizarProduto(Dicionario.ConverterProduto.sankhyaParaHub(sankhyaProduto), login)) {
                                Logs.gerarLog(Logs.TITULO_SUCESSO, "Produto: " + sankhyaProduto.getNumPro() + " Criado/Atualizazdo com Sucesso!", Logs.COLOR_SUCESSO);
                                if (produtoDao.marcarComoProcessado(sankhyaProduto) > 0) {
                                    Logs.gerarLog(Logs.TITULO_SUCESSO, "Produto: " + sankhyaProduto.getNumPro() + " Marcado como Processado!", Logs.COLOR_SUCESSO);
                                }
                            }
                            interrupt();
                        } catch (SQLException ex) {
                            System.out.println("THREAD" + ex.getMessage());
                            interrupt();
                        }
                    }
                };
                threads.add(thread);
            });
            for (Thread t : threads) {
                try {
                    t.sleep(500);
                    t.start();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExecucaoDiretaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //Espera que todas parem
            for (Thread t : threads) {
                t.join();
            }
            connection.commit();
            Logs.finalizandoLog("Finalizando Produtos Não Processados!");
        } catch (NumberFormatException e) {
            Logs.gerarLog(Logs.TITULO_ERRO, "Error: " + e.getMessage(), Logs.COLOR_ERRO);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExecucaoDiretaController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exportarCategoriaNaoProcessados() throws SQLException {
        try {
            Logs.inicializarLog("Buscando Categorias Não Processadas", "");
            CategoriaDao categoriaDao = new CategoriaDao(connection);
            List<SankhyaCategoria> categorias = categoriaDao.buscarSankhyaCategoriasNaoProcessados();
            Logs.gerarLog(Logs.TITULO_AVISO, "Todos as Categorias, foram Buscadas com Sucesso!", Logs.COLOR_AVISO);
            for (SankhyaCategoria sankhyaCategoria : categorias) {
                if (CategoriaServer.inserirOuAlterarCategoria(Dicionario.ConverterCategoria.sankhyaParaHub(sankhyaCategoria), login)) {
                    Logs.gerarLog(Logs.TITULO_SUCESSO, "Categoria: " + sankhyaCategoria.getNumCat() + " Criado/Atualizazdo com Sucesso!", Logs.COLOR_SUCESSO);
                    if (categoriaDao.marcarComoProcessado(sankhyaCategoria) > 0) {
                        Logs.gerarLog(Logs.TITULO_SUCESSO, "Categoria: " + sankhyaCategoria.getNumCat() + " Marcado como Processado!", Logs.COLOR_SUCESSO);
                    }
                }
            }
            connection.commit();
            Logs.finalizandoLog("Finalizando Categoria Não Processadas!");
        } catch (NumberFormatException e) {
            Logs.gerarLog(Logs.TITULO_ERRO, "Error: " + e.getMessage(), Logs.COLOR_ERRO);
        }
    }

    private void exportarEstoque() throws SQLException {
        List<Thread> threads = new ArrayList<>();
        try {
            Logs.inicializarLog("Buscando Estoque Não Processados", "");
            EstoqueDao estoqueDao = new EstoqueDao(connection);
            List<SankhyaEstoque> estoques = estoqueDao.buscarEstoqueNaoProcessados();
            Logs.gerarLog(Logs.TITULO_AVISO, "Todos os Estoque, foram Buscados com Sucesso!", Logs.COLOR_AVISO);
            estoques.stream().map((sankhyaEstoque) -> {
                Estoque estoque = Dicionario.ConverterEstoque.sankhyaParaHub(sankhyaEstoque);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            if (EstoqueServer.atualizarEstoque(estoque, login)) {
                                Logs.gerarLog(Logs.TITULO_SUCESSO, "Produto: " + estoque.getItemId() + " foi atualizado com o estoque: " + estoque.getQuantity(), Logs.COLOR_SUCESSO);
                                if (estoqueDao.marcarComoProcessado(sankhyaEstoque) > 0) {
                                    Logs.gerarLog(Logs.TITULO_SUCESSO, "Produto: " + sankhyaEstoque.getCodProd() + " marcado como processado;", Logs.COLOR_SUCESSO);
                                }
                            }
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                };
                return thread;
            }).forEachOrdered((thread) -> {
                threads.add(thread);
            });
            threads.forEach((t) -> {
                try {
                    t.sleep(500);
                    t.start();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExecucaoDiretaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            //Espera que todas parem
            for (Thread t : threads) {
                t.join();
            }
            connection.commit();
            Logs.finalizandoLog("Finalizando Estoque Não Processados!");
        } catch (NumberFormatException e) {
            Logs.gerarLog(Logs.TITULO_ERRO, "Error: " + e.getMessage(), Logs.COLOR_ERRO);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExecucaoDiretaController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exportarAnunciosNaoProcessados() throws SQLException {
        List<Thread> threads = new ArrayList<>();
        try {
            Logs.inicializarLog("Buscando Anúncios Não Processados", "");
            AnuncioDao anuncioDao = new AnuncioDao(connection);
            List<SankhyaAnuncio> sankhyaAnunciosProdutos = anuncioDao.buscarAnunciosDeProdutosPendentes();
            Logs.gerarLog(Logs.TITULO_AVISO, "Todos os Anúncios de Produtos, foram Buscados com Sucesso!", Logs.COLOR_AVISO);
            sankhyaAnunciosProdutos.stream().map((sankhyaAnuncio) -> {
                AnuncioPreco anuncioPreco = Dicionario.ConverterAnuncio.Preco.sankhyaParaHub(sankhyaAnuncio);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            if (AnuncioServer.criarOuAtualizarPrecoAnuncio(anuncioPreco, login)) {
                                if (sankhyaAnuncio.getStatus() == 1 || sankhyaAnuncio.getStatus() == 2 || sankhyaAnuncio.getStatus() == 5) {
                                    if (AnuncioServer.atualizarStatus(new AnuncioStatus(sankhyaAnuncio.getSku(), sankhyaAnuncio.getCanalDeVenda(), sankhyaAnuncio.getStatus()), login)) {
                                        Logs.gerarLog(Logs.TITULO_SUCESSO, "Produto: " + anuncioPreco.getItemId() + " com status alterado!", Logs.COLOR_SUCESSO);
                                        AnuncioDao anuncioDao = new AnuncioDao(connection);
                                        anuncioDao.marcarAnuncioComoSicronizando(sankhyaAnuncio);
                                        Logs.gerarLog(Logs.TITULO_SUCESSO, "Produto: " + anuncioPreco.getItemId() + " foi atualizado com o preço: " + anuncioPreco.getPrice() + " para o Canal de Venda: " + anuncioPreco.getSalesChannel(), Logs.COLOR_SUCESSO);
                                    } else {
//                                        Logs.gerarLog(Logs.TITULO_AVISO, "Produto: " + anuncioPreco.getItemId() + " não foi atualizado com o preço: " + anuncioPreco.getPrice() + " para o Canal de Venda: " + anuncioPreco.getSalesChannel(), Logs.COLOR_AVISO);
                                        // Marca anúncio com erro diretamente ao cadastrar, removido para ser analisado em um novo módulo
//                                        anuncioDao.marcarAnuncioComErro(sankhyaAnuncio);
                                    }
                                }
                            }
                        } catch (SQLException ex) {
                        }
                    }
                };
                return thread;
            }).forEachOrdered((thread) -> {
                threads.add(thread);
            });
            for (Thread t : threads) {
                try {
                    t.sleep(500);
                    t.start();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ExecucaoDiretaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //Espera que todas parem
            for (Thread t : threads) {
                t.join();
            }
            connection.commit();
            Logs.finalizandoLog("Finalizando Anúncios Não Processados!");
        } catch (NumberFormatException e) {
            Logs.gerarLog(Logs.TITULO_ERRO, "Error: " + e.getMessage(), Logs.COLOR_ERRO);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExecucaoDiretaController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void integrarPedidos(Connection connectionTemp) throws SQLException {
        Logs.inicializarLog("Integrando Pedidos Aprovados", "");
        tempPagina = 0;
        PedidoDao pedidoDao = new PedidoDao(connectionTemp);
        while (Math.ceil(pedidosVisaoGeral.getApproved()) >= tempPagina) {
            PedidosResponse buscarPedidosAprovados = PedidoServer.buscarPedidosAprovados(realizarLoginHubConta1(), tempPagina);
            List<Pedido> pedidos = buscarPedidosAprovados.getResponse();
            for (Pedido pedido : pedidos) {
                if (!pedidoDao.buscarExistenciaDoPedido(pedido.getReference().getId())) {
                    Logs.gerarLog(Logs.TITULO_AVISO, "Começando Integração do Pedido: " + pedido.getReference().getId(), Logs.COLOR_AVISO);
                    SankhyaCliente sankhyaCliente = integrarClientePedido(pedido, connectionTemp);
                    if (sankhyaCliente != null) {
                        SankhyaPedido sankhyaPedido = pedidoDao.inserirPedido(Dicionario.ConverterPedido.hubParaSankhya(pedido, sankhyaCliente.getNumcli()), connection);
                        integrarProdutosPedido(sankhyaPedido, pedido, connectionTemp);
                        Logs.gerarLog(Logs.TITULO_SUCESSO, "Integração do Pedido Finalizada, Pedido Gerado: " + sankhyaPedido.getNumPed(), Logs.COLOR_SUCESSO);
                    }
                } else {
//                    Logs.gerarLog(Logs.TITULO_SUCESSO, "Pedido: " + pedido.getReference().getId() + ", já existente!", Logs.COLOR_SUCESSO);
                }
            }
            tempPagina = tempPagina + 100;
        }
        Logs.finalizandoLog("Finalizando Integração de Pedidos");
    }

    private void integrarProdutosPedido(SankhyaPedido sankhyaPedido, Pedido pedido, Connection connectionTemp) throws SQLException {
        Logs.gerarLog(Logs.TITULO_AVISO, "Começando Integração dos Produtos", Logs.COLOR_AVISO);
        ItemDao itemDao = new ItemDao(connectionTemp);
        List<SankhyaItem> sankhyaItems = Dicionario.ConverterItem.hubParaSankhya(pedido, sankhyaPedido.getNumPed());
        for (SankhyaItem sankhyaItem : sankhyaItems) {
            itemDao.inserirItem(sankhyaItem);
        }
        Logs.gerarLog(Logs.TITULO_AVISO, "Finalizando Integração do Produtos", Logs.COLOR_AVISO);
    }

    public void IntegrarPedidosCancelados() throws SQLException, InterruptedException {
        List<Thread> threads = new ArrayList<>();
        Logs.inicializarLog("Integrando Pedidos Cancelados", "");
        tempPagina = 0;
        PedidoDao pedidoDao = new PedidoDao(connection);
        quantidadeDePedidosCancelados = pedidoDao.buscarQuantidadeDePedidosCancelados();
        if (pedidosVisaoGeral.getCanceled() != quantidadeDePedidosCancelados) {
            while ((pedidosVisaoGeral.getCanceled()) >= tempPagina) {
                PedidosResponse pedidosCancelados = PedidoServer.buscarPedidosCancelados(realizarLoginHubConta1(), tempPagina);
                List<Pedido> pedidos = pedidosCancelados.getResponse();
                pedidos.stream().map((Pedido pedido) -> new Thread() {
                    @Override
                    public void run() {
                        try {
                            if (!pedidoDao.verificarSePedidoEstaCancelado(pedido.getReference().getId())) {
                                Logs.gerarLog(Logs.TITULO_AVISO, "Cancelando Pedido número: " + pedido.getReference().getId(), Logs.COLOR_AVISO);
                                if (!pedidoDao.buscarExistenciaDoPedido(pedido.getReference().getId())) {
                                    Logs.gerarLog(Logs.TITULO_AVISO, "Começando Integração do Pedido: " + pedido.getReference().getId(), Logs.COLOR_AVISO);
                                    SankhyaCliente sankhyaCliente = integrarClientePedido(pedido, connection);
                                    if (sankhyaCliente != null) {
                                        SankhyaPedido sankhyaPedido = pedidoDao.inserirPedido(Dicionario.ConverterPedido.hubParaSankhya(pedido, sankhyaCliente.getNumcli()), connection);
                                        integrarProdutosPedido(sankhyaPedido, pedido, connection);
                                        connection.commit();
                                        Logs.gerarLog(Logs.TITULO_SUCESSO, "Integração do Pedido Finalizada, Pedido Gerado: " + sankhyaPedido.getNumPed(), Logs.COLOR_SUCESSO);
                                    } else {
                                        Logs.gerarLog(Logs.TITULO_ERRO, "Integração do Pedido Finalizada, Não foi possível gerar o Cliente! Pedido: "+pedido.getReference().getId(), Logs.COLOR_ERRO);
                                    }
                                }
                                pedidoDao.marcarComoCancelado(Dicionario.ConverterPedido.hubParaSankhya(pedido, null));
                                quantidadeDePedidosCancelados += 1;
                                Logs.gerarLog(Logs.TITULO_SUCESSO, "Pedido Cancelado!", Logs.COLOR_SUCESSO);
                            } else {
                                // Removido o aviso de que já está cancelado!
//                                Logs.gerarLog(Logs.TITULO_AVISO, "Pedido: " + pedido.getReference().getId() + ", já está cancelado!", Logs.COLOR_AVISO);
                            }
                        } catch (SQLException ex) {
                            System.out.println("Error");
                        }
                    }
                }).forEachOrdered((thread) -> {
                    threads.add(thread);
                });
                tempPagina += 100;
            }
        }
        threads.forEach((t) -> {
                t.start();
        });
        //Espera que todas parem
        for (Thread t : threads) {
            t.join();
        }
        connection.commit();
        Logs.finalizandoLog("Finalizando Integração de Pedidos Cancelados");
    }

    private void exportarPedidosCancelados() throws SQLException, InterruptedException {
        List<Thread> threads = new ArrayList<>();
        Logs.inicializarLog("Iniciando Exportação de Pedidos Cancelados", "");
        PedidoDao pedidoDao = new PedidoDao(connection);
        for (PedidoStatus pedidoStatus : pedidoDao.buscarCancelamentosPendentes()) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    Logs.gerarLog(Logs.TITULO_AVISO, "Cancelando pedido: " + pedidoStatus.getPedidoId(), Logs.COLOR_AVISO);
                    if (PedidoServer.alterarStatus(login, pedidoStatus)) {
                        try {
                            SankhyaPedido sankhyaPedido = new SankhyaPedido();
                            sankhyaPedido.setPedidoId(new BigDecimal(pedidoStatus.getPedidoId()));
                            pedidoDao.marcarComoCancelado(sankhyaPedido);
                        } catch (SQLException ex) {
                            Logger.getLogger(ExecucaoDiretaController.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            threads.add(thread);
        }
        Logs.gerarLog(Logs.TITULO_AVISO, "Todos os Pedidos foram Resgatados com Sucesso!", Logs.COLOR_AVISO);
        threads.forEach((t) -> {
            try {
                t.sleep(500);
                t.start();
            } catch (InterruptedException ex) {
                Logger.getLogger(ExecucaoDiretaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //Espera que todas parem
        for (Thread t : threads) {
            t.join();
        }
        connection.commit();
        Logs.finalizandoLog("Finalizando Exportação de Pedidos Cancelados");
    }

    private void exportarVariedadesProdutosNaoProcessados() throws SQLException {
        try {
            Logs.inicializarLog("Buscando Variedades Não Processadas", "");
            ProdutoDao produtoDao = new ProdutoDao(connection);
            List<SankhyaProduto> sankhyaProdutos = produtoDao.buscarSankhyaVariedadesDeProdutosNaoProcessados();
            Logs.gerarLog(Logs.TITULO_AVISO, "Todas as Variedades, foram Buscados com Sucesso!", Logs.COLOR_AVISO);
            for (SankhyaProduto sankhyaProduto : sankhyaProdutos) {
                if (ProdutosServer.criarOuAtualizarProduto(Dicionario.ConverterProduto.sankhyaParaHub(sankhyaProduto), login)) {
                    Logs.gerarLog(Logs.TITULO_SUCESSO, "Variedade: " + sankhyaProduto.getNumPro() + " Criado/Atualizada com Sucesso!", Logs.COLOR_SUCESSO);
                    if (produtoDao.marcarVariedadeComoProcessada(sankhyaProduto) > 0) {
                        Logs.gerarLog(Logs.TITULO_SUCESSO, "Variedade: " + sankhyaProduto.getNumPro() + " Marcada como Processada!", Logs.COLOR_SUCESSO);
                    }
                }
            }
            connection.commit();
            Logs.finalizandoLog("Finalizando Variedades Não Processadas!");
        } catch (NumberFormatException e) {
            Logs.gerarLog(Logs.TITULO_ERRO, "Error: " + e.getMessage(), Logs.COLOR_ERRO);
        }
    }

    private void integrarStatusDosProdutosComErro() throws SQLException, InterruptedException {
        List<Thread> threads = new ArrayList<>();
        Logs.inicializarLog("Iniciando Integração do Status com Erro em Anúncios", "");
        ProdutoDao produtoDao = new ProdutoDao(connection);
        tempPagina = 0;
        produtosComErros = ProdutosServer.buscarProdutosComErros(login, tempPagina);
        if (produtosComErros != null) {
            while (produtosComErros.getData().getPaging().getPages() >= tempPagina) {
                Integer pagina = tempPagina;
                Logs.gerarLog(Logs.TITULO_AVISO, "Todos os Produtos foram Resgatados!! Página: " + pagina, Logs.COLOR_AVISO);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        if (pagina != 1) {
                            produtosComErros = ProdutosServer.buscarProdutosComErros(login, pagina);
                        }
                        if (produtosComErros != null) {
                            if (produtosComErros.getData() != null) {
                                produtosComErros.getData().getList().forEach((produto) -> {
                                    try {
                                        produtoDao.alterarStatusDosProdutosParaErro(
                                                produto.getSku(),
                                                produto.getSkuLojista(),
                                                produto.getSalesChannel(),
                                                produto.getStatusMessage());
                                    } catch (SQLException ex) {
                                        System.out.println("Erro Thread: ");
                                    }
                                });
                            }
                        }
                    }
                };
                threads.add(thread);
                tempPagina += 100;
            }
        }
        threads.forEach((t) -> {
            try {
                t.sleep(500);
                t.start();
            } catch (InterruptedException ex) {
                Logger.getLogger(ExecucaoDiretaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //Espera que todas parem
        for (Thread t : threads) {
            t.join();
        }
        connection.commit();
        Logs.finalizandoLog("Finalizando Integração de Status com Erro");
    }

    private void integrarStatusDosProdutosSicronizados() throws SQLException {
        List<Thread> threads = new ArrayList<>();
        Logs.inicializarLog("Iniciando Integração de Status Sicronizados", "");
        ProdutoDao produtoDao = new ProdutoDao(connection);
        tempPagina = 0;
        produtosPendentes = ProdutosServer.buscarProdutosPendentes(login, tempPagina);
        while (produtosPendentes.getData().getPaging().getPages() >= tempPagina) {
            Integer pagina = tempPagina;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    if (tempPagina != 1) {
                        produtosPendentes = ProdutosServer.buscarProdutosPendentes(login, pagina);
                    }
                    if (produtosPendentes.getData() != null) {
                        ProdutoList produtos = produtosPendentes.getData();
                        try {
                            produtoDao.alterarStatusDosProdutosParaSicronizado(produtos.getList());
                        } catch (SQLException ex) {
                            System.out.println("Erro Thead");
                        }
                    }
                }
            };
            threads.add(thread);
            tempPagina += 100;
        }
        threads.forEach((t) -> {
            try {
                t.sleep(500);
                t.start();
            } catch (InterruptedException ex) {
                Logger.getLogger(ExecucaoDiretaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //Espera que todas parem
        threads.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                System.out.println("Erro Thread");
            }
        });
        connection.commit();
        Logs.finalizandoLog("Finalizando Integração de Status Sicronizados");
    }

    private SankhyaCliente integrarClientePedido(Pedido pedido, Connection connection) throws SQLException {
        ClienteDao clienteDao = new ClienteDao(connection);
        Logs.gerarLog(Logs.TITULO_AVISO, "Integrando Cliente: " + pedido.getCustomer().getName(), Logs.COLOR_AVISO);
        SankhyaCliente hubParaSankhya = Dicionario.ConverterCliente.hubParaSankhya(pedido);
        if (hubParaSankhya == null) {
            return null;
        }
        SankhyaCliente sankhyaCliente = clienteDao.inserir(hubParaSankhya);
        Logs.gerarLog(Logs.TITULO_AVISO, "Integração do Cliente Finalizada", Logs.COLOR_AVISO);
        connection.commit();
        return sankhyaCliente;
    }

    private void integrarSkuLojista() throws SQLException {
        List<Thread> threads = new ArrayList<>();
        Logs.inicializarLog("Iniciando Integração do Sku Lojista", "");
        ProdutoDao produtoDao = new ProdutoDao(connection);
        List<SankhyaProduto> produtosSemSkuLojista = produtoDao.buscarSankhyaProdutosSemSkuLojista();
        Logs.gerarLog(Logs.TITULO_AVISO, "Todos os produtos foram Resgatados!", Logs.COLOR_AVISO);
        produtosSemSkuLojista.stream().map((sankhyaProduto) -> new Thread() {
            @Override
            public void run() {
                ProdutoData produto = ProdutosServer.buscarProduto(login, sankhyaProduto.getSku(), sankhyaProduto.getSalesChannel());
                try {
                    if (produto != null) {
                        if (produto.getData().getList().get(0).getMarketplaceSKU() != null) {
                            if (produto.getData().getList().get(0).getMarketplaceSKU().length() > 1) {
                                produtoDao.alterarSkuLojista(sankhyaProduto.getNumPro(), sankhyaProduto.getSalesChannel(), produto.getData().getList().get(0).getMarketplaceSKU());
                                Logs.gerarLog(Logs.TITULO_SUCESSO, "SKU do Lojista do Produto " + sankhyaProduto.getSku() + " e Canal de Venda " + sankhyaProduto.getSalesChannel() + " atualizado para " + produto.getData().getList().get(0).getMarketplaceSKU() + "!", Logs.COLOR_SUCESSO);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao buscar SKU do Lojista do Produto: " + sankhyaProduto.getSku() + "erro: " + ex.getMessage(), Logs.COLOR_ERRO);
                }
            }
        }).forEachOrdered((thread) -> {
            threads.add(thread);
        });
        threads.forEach((t) -> {
            t.start();
        });
        //Espera que todas parem
        threads.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                System.out.println("Erro Thread");
            }
        });

        connection.commit();
        Logs.finalizandoLog("Finalizando Integração de Status Sicronizados");
    }

    private void criandoConexao() throws SQLException {
        connection = ConexaoOracle.ObterConexao();
        connection.setAutoCommit(false);
    }

    private void importarAnunciosProcessados() throws SQLException {
        List<Thread> threads = new ArrayList<>();
        Logs.inicializarLog("Iniciando Integração dos Status de Anúncios Importados", "");
        ProdutoDao produtoDao = new ProdutoDao(connection);
        List<SankhyaProduto> produtosSicronizando = produtoDao.buscarSankhyaProdutosSicronizando();
        produtosSicronizando.stream().map((sankhyaProduto) -> new Thread() {
            @Override
            public void run() {
                ProdutoData produto = ProdutosServer.buscarProduto(login, sankhyaProduto.getSku(), sankhyaProduto.getSalesChannel());
                try {
                    if (produto != null) {
                        for (ProdutoSku produtoTemp : produto.getData().getList()) {
                            if (produtoTemp.getSalesChannel() == sankhyaProduto.getSalesChannel()) {
                                if (produtoTemp.getStatus().toLowerCase().equals("Synchronized".toLowerCase())) {
                                    produtoDao.alterarStatusDosProdutoParaSicronizado(produtoTemp);
                                    Logs.gerarLog(Logs.TITULO_SUCESSO, "Produto marcado como processado, Produto " + sankhyaProduto.getNumPro() + " e Canal de Venda " + sankhyaProduto.getSalesChannel() + "!", Logs.COLOR_SUCESSO);
                                }
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logs.gerarLog(Logs.TITULO_SUCESSO, "Erro ao atualizar Status do Anúncio para Processado, produto: " + sankhyaProduto.getNumPro() + " e Canal de Venda " + sankhyaProduto.getSalesChannel() + "! Error: " + ex.getMessage(), Logs.COLOR_SUCESSO);
                }
            }
        }).forEachOrdered((thread) -> {
            threads.add(thread);
        });
        threads.forEach((t) -> {
            t.start();
        });
        //Espera que todas parem
        threads.forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                System.out.println("Erro Thread");
            }
        });
        connection.commit();
        Logs.finalizandoLog("Finalizando Integração de Status Sicronizados");
    }

}
