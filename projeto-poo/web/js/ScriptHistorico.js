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

    // Simulação de dados conforme as tabelas do banco (produtos e servicos), sem o campo id
    const historicoProdutos = [
        {
            tipo: 'produto',
            nome: 'Cadeira Gamer',
            materiais: 'Aço, Espuma, Tecido',
            custo: 350.00,
            lucro: 40.0
        },
        {
            tipo: 'produto',
            nome: 'Mesa Escritório',
            materiais: 'Madeira, Parafusos',
            custo: 200.00,
            lucro: 30.0
        }
    ];
    const historicoServicos = [
        {
            tipo: 'servico',
            descricao: 'Desenvolvimento de Site',
            horas: '20',
            valor_hora: 80.00,
            custos_extras: 150.00
        },
        {
            tipo: 'servico',
            descricao: 'Consultoria Financeira',
            horas: '10',
            valor_hora: 120.00,
            custos_extras: 0.00
        }
    ];

    // Junta os dois tipos para exibir no histórico
    historicoTotal = [...historicoProdutos, ...historicoServicos];

    const startIndex = (pagina - 1) * itensPorPagina;
    const endIndex = startIndex + itensPorPagina;
    const orcamentosPagina = historicoTotal.slice(startIndex, endIndex);

    if (orcamentosPagina.length > 0) {
        orcamentosPagina.forEach((orcamento, idx) => {
            const uniqueId = `${orcamento.tipo}-${pagina}-${idx}`;
            const listItem = criarItemOrcamento(orcamento, uniqueId);
            listaOrcamentos.appendChild(listItem);
        });
        adicionarListenersVisualizar();
        renderizarPaginacao(historicoTotal.length, itensPorPagina, pagina);
    } else if (pagina === 1) {
        mensagemVazio.style.display = 'block';
    }
}

function criarItemOrcamento(orcamento, uniqueId) {
    const listItem = document.createElement('li');
    listItem.classList.add('item-orcamento');

    if (orcamento.tipo === 'produto') {
        listItem.innerHTML = `
            <div class="nome-orcamento"><strong>Produto:</strong> ${orcamento.nome}</div>
            <div class="acoes-orcamento">
                <button type="button" class="btn-visualizar" data-uid="${uniqueId}">Visualizar</button>
            </div>
            <div class="detalhes-expandidos" id="detalhes-${uniqueId}">
                <p><strong>Materiais:</strong> ${orcamento.materiais}</p>
                <p><strong>Custo:</strong> R$ ${orcamento.custo.toFixed(2)}</p>
                <p><strong>Lucro (%):</strong> ${orcamento.lucro}</p>
            </div>
        `;
    } else if (orcamento.tipo === 'servico') {
        listItem.innerHTML = `
            <div class="nome-orcamento"><strong>Serviço:</strong> ${orcamento.descricao}</div>
            <div class="acoes-orcamento">
                <button type="button" class="btn-visualizar" data-uid="${uniqueId}">Visualizar</button>
            </div>
            <div class="detalhes-expandidos" id="detalhes-${uniqueId}">
                <p><strong>Horas:</strong> ${orcamento.horas}</p>
                <p><strong>Valor Hora:</strong> R$ ${orcamento.valor_hora.toFixed(2)}</p>
                <p><strong>Custos Extras:</strong> R$ ${orcamento.custos_extras.toFixed(2)}</p>
            </div>
        `;
    }
    // Garante que os detalhes começam ocultos
    setTimeout(() => {
        const detalhes = listItem.querySelector('.detalhes-expandidos');
        if (detalhes) detalhes.style.display = 'none';
    }, 0);
    return listItem;
}

// Adiciona o event listener após renderizar os itens
function adicionarListenersVisualizar() {
    const botoes = document.querySelectorAll('.btn-visualizar');
    botoes.forEach(btn => {
        btn.addEventListener('click', function() {
            const uid = this.getAttribute('data-uid');
            const detalhes = document.getElementById(`detalhes-${uid}`);
            if (!detalhes) return;
            // Fecha todos os outros
            document.querySelectorAll('.detalhes-expandidos').forEach(div => {
                if (div !== detalhes) div.style.display = 'none';
            });
            // Alterna o clicado
            detalhes.style.display = detalhes.style.display === 'block' ? 'none' : 'block';
        });
    });
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