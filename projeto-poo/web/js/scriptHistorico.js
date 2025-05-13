document.addEventListener('DOMContentLoaded', function() {
    adicionarNovosOrcamentos();
});

function adicionarNovosOrcamentos() {
    const listaOrcamentos = document.getElementById('lista-de-orcamentos');

    const novosOrcamentos = [
        {
            budget_id: 'orcamento-4',
            nome_orcamento: 'Novo Orçamento #4 (Dinâmico)',
            data_criacao: '2025-05-02T11:00:00Z',
            detalhes: {
                valor_total: 550.75,
                observacao: 'Este foi adicionado pelo script.',
                cliente: 'Cliente Dinâmico 1',
                itens: 'Serviço A, Serviço B',
                status: 'Em análise',
                vendedor: 'Vendedor 4'
            }
        },
        {
            budget_id: 'orcamento-5',
            nome_orcamento: 'Novo Orçamento #5 (Dinâmico)',
            data_criacao: '2025-05-02T14:45:00Z',
            detalhes: {
                valor_total: 1200.00,
                observacao: 'Outro orçamento dinâmico.',
                cliente: 'Cliente Dinâmico 2',
                itens: 'Produto X, Produto Y, Produto Z',
                status: 'Aprovado',
                vendedor: 'Vendedor 1'
            }
        }
        // Você pode adicionar mais orçamentos fictícios aqui
    ];

    novosOrcamentos.forEach(orcamento => {
        const listItem = document.createElement('li');
        listItem.classList.add('item-orcamento');

        const infoDiv = document.createElement('div');
        infoDiv.classList.add('info-orcamento');
        infoDiv.innerHTML = `
            <div class="nome-orcamento">${orcamento.nome_orcamento}</div>
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
            <p><strong>Valor Total:</strong> R$ ${orcamento.detalhes.valor_total.toFixed(2)}</p>
            <p><strong>Observação:</strong> ${orcamento.detalhes.observacao}</p>
            <p><strong>Cliente:</strong> ${orcamento.detalhes.cliente}</p>
            <p><strong>Itens:</strong> ${orcamento.detalhes.itens}</p>
            <p><strong>Status:</strong> ${orcamento.detalhes.status}</p>
            <p><strong>Vendedor:</strong> ${orcamento.detalhes.vendedor}</p>
        `;

        listItem.appendChild(infoDiv);
        listItem.appendChild(actionsDiv);
        listItem.appendChild(detalhesDiv);

        listaOrcamentos.appendChild(listItem);
    });
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

// As funções toggleDetalhes e excluirOrcamento podem permanecer no script principal (no <script> dentro do HTML)
// ou você pode movê-las para este arquivo scriptHistorico.js, dependendo da sua preferência de organização.
// Se você as mover para cá, certifique-se de que o HTML ainda consiga chamá-las corretamente.