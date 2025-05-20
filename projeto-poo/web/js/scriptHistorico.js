// scriptHistorico.js

document.addEventListener('DOMContentLoaded', function() {
    carregarHistoricoOrcamentos();
});

async function carregarHistoricoOrcamentos() {
    const listaOrcamentos = document.getElementById('lista-de-orcamentos');
    const mensagemVazio = document.getElementById('mensagem-vazio');

    // Limpa a lista de orçamentos existente
    listaOrcamentos.innerHTML = '';
    mensagemVazio.style.display = 'none'; // Esconde a mensagem de vazio inicialmente

    // Simulação de dados de orçamentos vindos da sua API (substitua pela chamada real)
    const historicoOrcamentos = [
        {
            budget_id: 'orcamento-hist-1',
            nome_orcamento: 'Orçamento Web Design',
            data_criacao: '2025-05-15T14:30:00Z',
            detalhes: {
                valor_total: 2500.00,
                observacao: 'Criação de website responsivo.',
                cliente: 'Empresa ABC',
                itens: 'Design da página inicial, 5 páginas internas, Otimização SEO básica',
                status: 'Concluído',
                vendedor: 'João Silva'
            }
        },
        {
            budget_id: 'orcamento-hist-2',
            nome_orcamento: 'Orçamento Marketing Digital',
            data_criacao: '2025-05-10T09:00:00Z',
            detalhes: {
                valor_total: 1200.50,
                observacao: 'Campanha de marketing de conteúdo para 3 meses.',
                cliente: 'Loja XYZ',
                itens: 'Criação de 10 posts para blog, Gerenciamento de redes sociais (1 plataforma)',
                status: 'Em andamento',
                vendedor: 'Maria Oliveira'
            }
        }
    ];

    if (historicoOrcamentos && historicoOrcamentos.length > 0) {
        historicoOrcamentos.forEach(orcamento => {
            const listItem = criarItemOrcamento(orcamento);
            listaOrcamentos.appendChild(listItem);
        });
    } else {
        mensagemVazio.style.display = 'block';
    }
}

function criarItemOrcamento(orcamento) {
    const listItem = document.createElement('li');
    listItem.classList.add('item-orcamento');

    const infoDiv = document.createElement('div');
    infoDiv.classList.add('info-orcamento');
    infoDiv.innerHTML = `
        <div class="nome-orcamento">${orcamento.nome_orcamento || 'Orçamento sem nome'}</div>
        <div class="data-criacao">Criado em: ${formatarData(orcamento.data_criacao)}</div>
    `;

    const actionsDiv = document.createElement('div');
    actionsDiv.classList.add('acoes-orcamento');
    actionsDiv.innerHTML = `
        <button onclick="toggleDetalhes('${orcamento.budget_id}')">Visualizar</button>
        <button onclick="excluirOrcamento('${orcamento.budget_id}')">Excluir</button>
    `;

    const detalhesDiv = document.createElement('div');
    detalhesDiv.classList.add('detalhes-expandidos');
    detalhesDiv.id = `detalhes-${orcamento.budget_id}`;
    detalhesDiv.innerHTML = `
        <p><strong>Valor Total:</strong> R$ ${orcamento.detalhes ? orcamento.detalhes.valor_total.toFixed(2) : 'N/A'}</p>
        <p><strong>Observação:</strong> ${orcamento.detalhes ? orcamento.detalhes.observacao : 'N/A'}</p>
        <p><strong>Cliente:</strong> ${orcamento.detalhes ? orcamento.detalhes.cliente : 'N/A'}</p>
        <p><strong>Itens:</strong> ${orcamento.detalhes ? orcamento.detalhes.itens : 'N/A'}</p>
        <p><strong>Status:</strong> ${orcamento.detalhes ? orcamento.detalhes.status : 'N/A'}</p>
        <p><strong>Vendedor:</strong> ${orcamento.detalhes ? orcamento.detalhes.vendedor : 'N/A'}</p>
    `;

    listItem.appendChild(infoDiv);
    listItem.appendChild(actionsDiv);
    listItem.appendChild(detalhesDiv);

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
            const detalhesDiv = document.getElementById(`detalhes-${orcamentoId}`);
            if (detalhesDiv) {
                detalhesDiv.style.display = detalhesDiv.style.display === 'none' ? 'block' : 'none';
            }
        }

        function excluirOrcamento(orcamentoId) {
            const botaoExcluir = event.target; // Obtém o elemento do botão que foi clicado
            const itemOrcamento = botaoExcluir.closest('.item-orcamento'); // Encontra o ancestral mais próximo com a classe 'item-orcamento'

            if (itemOrcamento && confirm(`Tem certeza que deseja excluir o orçamento "${itemOrcamento.querySelector('.nome-orcamento').textContent}"?`)) {
                itemOrcamento.remove(); // Remove o elemento 'li' (item do orçamento) do DOM
                alert(`Orçamento "${itemOrcamento.querySelector('.nome-orcamento').textContent}" excluído.`);
                // Se você tiver uma mensagem de "Nenhum orçamento encontrado", pode verificar
                // se a lista ficou vazia e exibir a mensagem novamente, se necessário.
                const listaOrcamentos = document.getElementById('lista-de-orcamentos');
                if (listaOrcamentos.children.length === 0) {
                    const mensagemVazio = document.getElementById('mensagem-vazio');
                    if (mensagemVazio) {
                        mensagemVazio.style.display = 'block';
                    }
                }
            }
        }
