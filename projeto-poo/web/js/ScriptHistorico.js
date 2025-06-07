// js/scriptHistorico.js

document.addEventListener('DOMContentLoaded', function() {
    carregarHistoricoOrcamentos(1); // Carrega a primeira página ao carregar a página
});

const itensPorPagina = 3;
let historicoTotal = [];
let paginaAtual = 1;

async function carregarHistoricoOrcamentos(pagina) {
    paginaAtual = pagina;
    const listaOrcamentos = document.getElementById('lista-de-orcamentos');
    const mensagemVazio = document.getElementById('mensagem-vazio');
    const paginacaoContainer = document.getElementById('paginacao-historico');

    listaOrcamentos.innerHTML = '';
    mensagemVazio.style.display = 'none';
    paginacaoContainer.innerHTML = '';

    // Simulação de dados de orçamentos
    const historicoOrcamentosSimulado = [
        {
            budget_id: 'orcamento-hist-1',
            nome_orcamento: 'Orçamento Web Design',
            data_criacao: '2025-05-15T14:30:00Z',
            detalhes: { valor_total: 2500.00, observacao: 'Criação de website responsivo com 5 páginas.', cliente: 'Empresa ABC', itens: 'Design da página inicial, 5 páginas internas, Otimização SEO básica', status: 'Concluído', vendedor: 'João Silva' }
            
        },
        {
            budget_id: 'orcamento-hist-2',
            nome_orcamento: 'Orçamento Marketing Digital',
            data_criacao: '2025-05-10T09:00:00Z',
            detalhes: { valor_total: 1200.50, observacao: 'Campanha de marketing de conteúdo para 3 meses.', cliente: 'Loja XYZ', itens: 'Criação de 10 posts para blog, Gerenciamento de redes sociais (1 plataforma)', status: 'Em andamento', vendedor: 'Maria Oliveira' }
        },
        {
            budget_id: 'orcamento-hist-3',
            nome_orcamento: 'Consultoria de TI',
            data_criacao: '2025-05-05T16:45:00Z',
            detalhes: { valor_total: 800.00, observacao: 'Análise de infraestrutura de rede e segurança.', cliente: 'Startup Tech', itens: 'Diagnóstico de rede, Relatório de vulnerabilidades, Recomendações de melhoria', status: 'Aguardando aprovação', vendedor: 'Carlos Souza' }
        },
        {
            budget_id: 'orcamento-hist-4',
            nome_orcamento: 'Desenvolvimento Mobile App',
            data_criacao: '2025-04-28T10:15:00Z',
            detalhes: { valor_total: 7500.00, observacao: 'Criação de aplicativo para iOS e Android.', cliente: 'Food Delivery Ltda.', itens: 'Design UI/UX, Desenvolvimento de backend, Integração com API de pagamentos', status: 'Em desenvolvimento', vendedor: 'Ana Paula' }
        },
        {
            budget_id: 'orcamento-hist-5',
            nome_orcamento: 'Manutenção de Servidores',
            data_criacao: '2025-04-20T11:00:00Z',
            detalhes: { valor_total: 600.00, observacao: 'Manutenção preventiva mensal de 2 servidores.', cliente: 'DataCenter Solutions', itens: 'Verificação de logs, Atualização de software, Otimização de desempenho', status: 'Concluído', vendedor: 'Roberto Costa' }
        },
        {
            budget_id: 'orcamento-hist-6',
            nome_orcamento: 'Criação de Conteúdo',
            data_criacao: '2025-04-12T14:00:00Z',
            detalhes: { valor_total: 950.00, observacao: 'Produção de 5 artigos para blog e 10 posts para redes sociais.', cliente: 'Blog de Viagens', itens: 'Pesquisa de palavras-chave, Escrita e revisão, Otimização para SEO', status: 'Aguardando revisão', vendedor: 'Fernanda Lima' }
        }
    ];

    historicoTotal = historicoOrcamentosSimulado;

    const startIndex = (pagina - 1) * itensPorPagina;
    const endIndex = startIndex + itensPorPagina;
    const orcamentosPagina = historicoTotal.slice(startIndex, endIndex);

    if (orcamentosPagina.length > 0) {
        orcamentosPagina.forEach(orcamento => {
            const listItem = criarItemOrcamento(orcamento);
            listaOrcamentos.appendChild(listItem);
        });
        renderizarPaginacao(historicoTotal.length, itensPorPagina, pagina);
    } else if (pagina === 1) {
        mensagemVazio.style.display = 'block';
    }
}

function criarItemOrcamento(orcamento) {
    const listItem = document.createElement('li');
    listItem.classList.add('item-orcamento');

    listItem.innerHTML = `
        <div class="nome-orcamento">${orcamento.nome_orcamento || 'Orçamento sem nome'}</div>
        <div class="data-criacao">Criado em: ${formatarData(orcamento.data_criacao)}</div>
        <div class="acoes-orcamento">
            <button onclick="toggleDetalhes('${orcamento.budget_id}')">Visualizar</button>
            <button onclick="excluirOrcamento('${orcamento.budget_id}')">Excluir</button>
        </div>
        <div class="detalhes-expandidos" id="detalhes-${orcamento.budget_id}">
            <p><strong>Valor Total:</strong> R$ ${orcamento.detalhes ? orcamento.detalhes.valor_total.toFixed(2) : 'N/A'}</p>
            <p><strong>Observação:</strong> ${orcamento.detalhes ? orcamento.detalhes.observacao : 'N/A'}</p>
            <p><strong>Cliente:</strong> ${orcamento.detalhes ? orcamento.detalhes.cliente : 'N/A'}</p>
            <p><strong>Itens:</strong> ${orcamento.detalhes ? orcamento.detalhes.itens : 'N/A'}</p>
            <p><strong>Status:</strong> ${orcamento.detalhes ? orcamento.detalhes.status : 'N/A'}</p>
            <p><strong>Vendedor:</strong> ${orcamento.detalhes ? orcamento.detalhes.vendedor : 'N/A'}</p>
        </div>
    `;

    return listItem;
}

function formatarData(dataISO) {
    const data = new Date(dataISO);
    const dia = String(data.getDate()).padStart(2, '0');
    const mes = String(data.getMonth() + 1).padStart(2, '0');
    const ano = data.getFullYear();
    const hora = String(data.getHours()).padStart(2, '0');
    const minuto = String(data.getMinutes()).padStart(2, '0');
    return `${dia}/${mes}/${ano} ${hora}:${minuto}`;
}

function toggleDetalhes(orcamentoId) {
    // Esconde todos os outros elementos de detalhes
    const todosDetalhes = document.querySelectorAll('.detalhes-expandidos');
    todosDetalhes.forEach(detalhe => {
        if (detalhe.id !== `detalhes-${orcamentoId}`) {
            detalhe.style.display = 'none';
        }
    });

    // Depois, mostra ou oculta o detalhe do orçamento clicado
    const detalhesDiv = document.getElementById(`detalhes-${orcamentoId}`);
    if (detalhesDiv) {
        detalhesDiv.style.display = detalhesDiv.style.display === 'none' ? 'block' : 'none';
    }
}

function excluirOrcamento(orcamentoId) {
    if (!confirm("Tem certeza que deseja excluir este orçamento?")) return;

    fetch('/projeto-poo/ExcluirOrcamento', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'id=' + encodeURIComponent(orcamentoId)
    })
    .then(res => res.json())
    .then(data => {
        if (data.sucesso) {
            alert('Orçamento excluído com sucesso.');
            carregarHistoricoOrcamentos(paginaAtual);
        } else {
            alert('Erro: ' + (data.erro || 'Tente novamente.'));
        }
    });
}

function removerMarcaDagua(id) {
    fetch('/projeto-poo/RemoverMarcaDagua', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'id=' + encodeURIComponent(id)
    })
    .then(res => res.json())
    .then(data => {
        if (data.sucesso) {
            alert('Pagamento confirmado! Você pode baixar o PDF sem marca d\'água.');
            location.reload();
        } else {
            alert('Erro: ' + (data.erro || 'Tente novamente.'));
        }
    });
}

function renderizarPaginacao(totalItens, itensPorPagina, paginaAtual) {
    const totalPaginas = Math.ceil(totalItens / itensPorPagina);
    const paginacaoContainer = document.getElementById('paginacao-historico');
    paginacaoContainer.innerHTML = '';

    if (totalPaginas > 1) {
        for (let i = 1; i <= totalPaginas; i++) {
            const botaoPagina = document.createElement('button');
            botaoPagina.innerText = i;
            botaoPagina.classList.add('btn', 'btn-sm', 'mx-1');
            if (i === paginaAtual) {
                botaoPagina.classList.add('btn-primary');
            } else {
                botaoPagina.classList.add('btn-outline-primary');
            }
            botaoPagina.addEventListener('click', () => carregarHistoricoOrcamentos(i));
            paginacaoContainer.appendChild(botaoPagina);
        }
    }
}